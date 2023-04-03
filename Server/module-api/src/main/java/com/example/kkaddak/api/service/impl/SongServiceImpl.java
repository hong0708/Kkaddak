package com.example.kkaddak.api.service.impl;

import com.example.kkaddak.api.dto.*;
import com.example.kkaddak.api.dto.song.*;
import com.example.kkaddak.api.exception.NotFoundException;
import com.example.kkaddak.api.service.NFTService;
import com.example.kkaddak.api.service.SongService;
import com.example.kkaddak.api.utils.DynamicTimeWarping;
import com.example.kkaddak.api.utils.FeatureExtractor;
import com.example.kkaddak.core.entity.*;
import com.example.kkaddak.core.repository.*;
import com.example.kkaddak.core.utils.ErrorMessageEnum;
import com.example.kkaddak.core.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    private final LikeListRepository likeListRepository;

    private final PlayListRepository playListRepository;

    private final NFTService nftService;

    private final S3Client s3Client;

    private static final Logger logger = LogManager.getLogger(ImageUtil.class);

    @Value("${aws.s3.bucket}")
    private String s3Bucket;

    @Value("${aws.s3.base-url}")
    private String s3BaseUrl;

    @Value("${custom.path.upload-images}")
    private String tempFilePath;

    private final MoodRepository moodRepository;

    private final SearchRepository searchRepository;

    @Override
    public DataResDto<?> uploadSong(SongReqDto songReqDto, Member member) {
        try {
            // 음악 파일 저장
            MultipartFile songFile = songReqDto.getSongFile();
            String songFileName = songFile.getOriginalFilename();
            String songFileKey = "songs/" + songFileName;
            String songFileUrl = s3BaseUrl + songFileKey;

            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(s3Bucket)
                    .key(songFileKey)
                    .build(), RequestBody.fromInputStream(songFile.getInputStream(), songFile.getSize()));

            // 커버 이미지 파일 저장
            MultipartFile coverFile = songReqDto.getCoverFile();
            String coverFileName = coverFile.getOriginalFilename();
            String coverFileKey = "covers/" + coverFileName;
            String coverFileUrl = s3BaseUrl + coverFileKey;

            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(s3Bucket)
                    .key(coverFileKey)
                    .build(), RequestBody.fromInputStream(coverFile.getInputStream(), coverFile.getSize()));

            // DB에 저장
            Mood mood;
            if (songReqDto.getMoods().size() == 1) {
                mood = Mood.builder().mood1(songReqDto.getMoods().get(0)).
                        mood2("").mood3("").build();
            } else if (songReqDto.getMoods().size() == 2) {
                mood = Mood.builder().mood1(songReqDto.getMoods().get(0)).
                        mood2(songReqDto.getMoods().get(1)).mood3("").build();
            } else {
                mood = Mood.builder().mood1(songReqDto.getMoods().get(0)).
                        mood2(songReqDto.getMoods().get(1)).mood3(songReqDto.getMoods().get(2)).build();
            }
            Mood savedMood = moodRepository.save(mood);

            String combination = "";
            Boolean isDup = false;

            do {
                combination = nftService.generateCombination();
                isDup = songRepository.existsByCombination(combination);
            } while (isDup);

            Song song = Song.builder()
                    .title(songReqDto.getSongTitle())
                    .songPath(songFileUrl)
                    .coverPath(coverFileUrl)
                    .genre(songReqDto.getGenre())
                    .moods(savedMood)
                    .member(member)
                    .combination(combination)
                    .songStatus(SongStatus.PROCEEDING)
                    .build();

            List<Song> songs = songRepository.findAll();

            File uploadedMp3File = getAmazonObject(song.getSongPath());
            float[][] mfcc1 = FeatureExtractor.extractMFCC(uploadedMp3File);
            // thread로 for문 순회하면서 유사도 검사
            CompletableFuture.runAsync(() -> {
                int cnt = 0;
                // upload된 파일 읽어들이기
                for (Song s : songs) {
                    cnt++;
                    try {
                        // S3에서 MP3 파일을 가져오기
                        File sFile = getAmazonObject(s.getSongPath());
                        float[][] mfcc2 = FeatureExtractor.extractMFCC(sFile);
                        double similarity = DynamicTimeWarping.calculateDistance(mfcc1, mfcc2);

                        logger.info("Similarity : " + similarity);
                        if (similarity <= 10) {
                            song.setSongStatus(SongStatus.REJECT);
                            songRepository.save(song);
                            return;
                        }
                    } catch (UnsupportedAudioFileException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                logger.info("cnt : " + cnt);
                song.setSongStatus(SongStatus.APPROVE);
                songRepository.save(song);
            }).exceptionally(ex -> {
                // 예외가 발생한 경우 로깅
                logger.info("CompletableFuture 작업 중 오류 발생: " + ex.getMessage());
                ex.printStackTrace();
                return null;
            });;

            Song savedSong = songRepository.save(song);

            // convert combination string to list
            List<Integer> combList = new ArrayList<>();
            String[] combArr = combination.split(",");

            for (String s : combArr) {
                combList.add(Integer.parseInt(s));
            }

            SongResDto songResDto = SongResDto.builder()
                    .song(savedSong)
                    .songStatus(song.getSongStatus())
                    .combination(combList).build();
            return DataResDto.builder().data(songResDto)
                    .statusMessage("음악 정보가 정상적으로 출력되었습니다.").build();
        } catch(IllegalArgumentException e) {
            return DataResDto.builder().statusCode(400).statusMessage(e.getMessage()).build();
        } catch(Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러 : " + e.getMessage()).build();
        }
    }

    @Override
    public DataResDto<?> deleteSong(Member member, UUID songUuid) {
        try {
            Song song = songRepository.findByMemberAndSongUuid(member, songUuid)
                    .orElseThrow(() -> new IllegalArgumentException("값이 존재하지 않습니다"));

            PlayList playList = playListRepository.findByMemberAndSong(member, song)
                    .orElse(null);

            LikeList likeList = likeListRepository.findByMemberAndSong(member, song)
                    .orElse(null);

            if (likeList != null) {
                likeListRepository.delete(likeList);
            }
            if (playList != null) {
                playListRepository.delete(playList);
            }
            songRepository.delete(song);

            return DataResDto.builder().statusMessage("음악이 정상적으로 삭제되었습니다.").build();
        } catch(IllegalArgumentException e) {
            return DataResDto.builder().statusCode(400).statusMessage("음악 songId가 올바르지 않습니다.").build();
        } catch(Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> getSong(UUID songUuid, Member member) {
        try {
            Song song = songRepository.findBySongUuid(songUuid)
                    .orElseThrow(() -> new IllegalArgumentException("값이 존재하지 않습니다"));

            song.setViews(song.getViews() + 1L);

            songRepository.save(song);

            boolean isLike = likeListRepository.existsByMemberAndSong(member, song);
            boolean checkValue = playListRepository.existsByMemberAndSong(member, song);

            if (checkValue) {
                PlayList playList = playListRepository.findByMemberAndSong(member, song)
                        .orElseThrow(() -> new IllegalArgumentException(""));
                playListRepository.delete(playList);
            }
            PlayList playList = PlayList.builder()
                    .member(member)
                    .song(song)
                    .build();
            playListRepository.save(playList);

            SongResDto songResDto = SongResDto.builder()
                    .song(song)
                    .isLike(isLike)
                    .isSubscribe(followRepository.existsByFollowerAndFollowing(member, song.getMember()))
                    .build();
            return DataResDto.builder().data(songResDto)
                    .statusMessage("음악 정보가 정상적으로 출력되었습니다.").build();
        }
        catch(IllegalArgumentException e){
            return DataResDto.builder().statusCode(400).statusMessage("음악 songId가 올바르지 않습니다.").build();
        }

    }

    @Override
    public DataResDto<?> getAllSong(Member member) {
        try {
            List<Song> songList = songRepository.findAll();
            if (songList == null || songList.isEmpty()) {
                return DataResDto.builder().data(Collections.emptyList())
                        .statusMessage("음악 리스트 정보가 정상적으로 출력되었습니다.").build();
            }

            List<SongResDto> songResDtoList = new ArrayList<>();
            for (Song song: songList) {
                boolean isLike = likeListRepository.existsByMemberAndSong(member, song);
                songResDtoList.add(SongResDto.builder().song(song).isLike(isLike).build());
            }

            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("음악 리스트 정보가 정상적으로 출력되었습니다.").build();
        } catch(Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> getLatestSong(Member member) {
        try {
            List<Song> songList = songRepository.findTop5ByOrderByUploadDateDesc();
            if (songList == null || songList.isEmpty()) {
                return DataResDto.builder().data(Collections.emptyList())
                        .statusMessage("음악 최신 리스트 정보가 정상적으로 출력되었습니다.").build();
            }

            List<SongResDto> songResDtoList = new ArrayList<>();
            for (Song song: songList) {
                boolean isLike = likeListRepository.existsByMemberAndSong(member, song);
                songResDtoList.add(SongResDto.builder().song(song).isLike(isLike).build());
            }

            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("음악 최신 리스트 정보가 정상적으로 출력되었습니다.").build();
        } catch(Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> clickLikeSong(Member member, UUID songUuid) {
        try {
            Song song = songRepository.findBySongUuid(songUuid)
                    .orElseThrow(() -> new IllegalArgumentException(""));

            String message = "";

            boolean checkValue = likeListRepository.existsByMemberAndSong(member, song);
            if (checkValue) {
                message = "음악 좋아요를 취소했습니다.";

                LikeList likeList = likeListRepository.findByMemberAndSong(member, song)
                        .orElseThrow(() -> new IllegalArgumentException(""));

                likeListRepository.delete(likeList);
            } else {
                message = "음악 좋아요를 선택했습니다.";
                LikeList likeList = LikeList.builder()
                        .member(member)
                        .song(song)
                        .build();
                likeListRepository.save(likeList);
            }
            return DataResDto.builder().data(!checkValue).statusMessage(message).build();
        } catch (IllegalArgumentException e) {
            return DataResDto.builder().statusCode(400).statusMessage("음악 songId가 올바르지 않습니다.").build();
        } catch (Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> getLikeList(Member member) {
        try {
            List<LikeList> likeList = likeListRepository.findByMember(member);
            if (likeList == null || likeList.isEmpty()) {
                return DataResDto.builder().data(Collections.emptyList())
                        .statusMessage("음악 좋아요 리스트가 정상적으로 출력되었습니다.").build();
            }

            List<SongResDto> songResDtoList = new ArrayList<>();
            for (LikeList likes: likeList) {
                boolean isLike = likeListRepository.existsByMemberAndSong(member, likes.getSong());
                songResDtoList.add(SongResDto.builder().song(likes.getSong()).isLike(isLike).build());
            }

            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("음악 좋아요 리스트가 정상적으로 출력되었습니다.").build();
        } catch (Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> deleteMyPlayList(Member member, UUID songUuid) {
        try {
            Song song = songRepository.findBySongUuid(songUuid)
                    .orElseThrow(() -> new IllegalArgumentException(""));

            boolean checkValue = playListRepository.existsByMemberAndSong(member, song);
            if (checkValue) {

                PlayList playList = playListRepository.findByMemberAndSong(member, song)
                        .orElseThrow(() -> new IllegalArgumentException(""));

                playListRepository.delete(playList);
            } else {
                throw new NullPointerException();
            }

            return DataResDto.builder().statusMessage("음악을 플레이리스트에서 제거하였습니다").build();
        } catch (IllegalArgumentException e) {
            return DataResDto.builder().statusCode(400).statusMessage("음악 songId가 올바르지 않습니다.").build();
        } catch (NullPointerException e) {
            return DataResDto.builder().statusCode(406).statusMessage("유저 플레이 리스트에 song이 존재 하지 않습니다").build();
        } catch (Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> getPlayList(Member member) {
        try {
            List<PlayList> playList = playListRepository.findByMemberOrderByAddedDateDesc(member);
            if (playList == null || playList.isEmpty()) {
                return DataResDto.builder().data(Collections.emptyList())
                        .statusMessage("음악 정보가 정상적으로 출력되었습니다.").build();
            }

            List<SongResDto> songResDtoList = new ArrayList<>();
            for (PlayList plays: playList) {
                boolean isLike = likeListRepository.existsByMemberAndSong(member, plays.getSong());
                Boolean isSubscribe = followRepository.existsByFollowerAndFollowing(member, plays.getSong().getMember());
                songResDtoList.add(
                        SongResDto.builder()
                                .song(plays.getSong())
                                .isLike(isLike)
                                .isSubscribe(isSubscribe)
                                .build());
            }
            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("음악 정보가 정상적으로 출력되었습니다.").build();
        } catch (Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> getSearchList(Member member, String keyWord, String genre) {
        try {
            List<Song> songList = searchRepository.searchSong(keyWord, genre);

            List<SongResDto> songResDtoList = new ArrayList<>();
            for (Song song: songList) {
                boolean isLike = likeListRepository.existsByMemberAndSong(member, song);
                songResDtoList.add(SongResDto.builder().song(song).isLike(isLike).build());
            }

            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("음악 검색 정보가 정상적으로 출력되었습니다.").build();
        } catch (Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> getSongByCreator(Member member) {
        try {
            List<Song> createSongList = songRepository.findByMember(member);
            if (createSongList == null || createSongList.isEmpty()) {
                return DataResDto.builder().data(Collections.emptyList())
                        .statusMessage("생성 음악 리스트가 정상적으로 출력되었습니다.").build();
            }

            List<SongResDto> songResDtoList = new ArrayList<>();
            for (Song song: createSongList) {
                boolean isLike = likeListRepository.existsByMemberAndSong(member, song);
                songResDtoList.add(SongResDto.builder().song(song).isLike(isLike).build());
            }

            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("생성 음악 리스트가 정상적으로 출력되었습니다.").build();
        } catch (Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> getPopularityList(Member member) {
        try {
            List<Song> songList = songRepository.findTop12ByOrderByViewsDesc();
            if (songList == null || songList.isEmpty()) {
                return DataResDto.builder().data(Collections.emptyList())
                        .statusMessage("인기 음악 리스트가 정상적으로 출력되었습니다.").build();
            }

            List<SongResDto> songResDtoList = new ArrayList<>();
            for (Song song: songList) {
                boolean isLike = likeListRepository.existsByMemberAndSong(member, song);
                songResDtoList.add(SongResDto.builder().song(song).isLike(isLike).build());
            }

            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("인기 음악 리스트가 정상적으로 출력되었습니다.").build();
        } catch (Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> getMemberSongs(Member member, String nickname) {
        Member profileOwner = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException(ErrorMessageEnum.USER_NOT_EXIST.getMessage()));
        List<OwnerSongResDto> ownerSongs = songRepository.findByMemberOrderByUploadDateDesc(profileOwner)
                .stream().map(song -> OwnerSongResDto.builder().song(song).build())
                .collect(Collectors.toList());
        return DataResDto.builder()
                .statusMessage(String.format("%s 님의 작품 목록입니다.", nickname))
                .data(ownerSongs).build();
    }

    @Override
    public DataResDto<?> uploadNFTImage(NftReqDto nftReqDto) throws IOException {
        // DB에서 대상 Song 가져오기
        Song song = songRepository.findBySongUuid(nftReqDto.getSongUUID())
                .orElseThrow(() -> new NotFoundException(ErrorMessageEnum.SONG_NOT_EXIST.getMessage()));

        // S3에 NFT image파일 업로드
        MultipartFile nftImage = nftReqDto.getNftImage();
        String nftImageName = nftImage.getOriginalFilename();
        String nftFileKey = "nfts/" + nftImageName;
        String nftFileUrl = s3BaseUrl + nftFileKey;

        try {
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(s3Bucket)
                    .key(nftFileKey)
                    .build(), RequestBody.fromInputStream(nftImage.getInputStream(), nftImage.getSize()));
        } catch (IOException e) {
            throw new IOException(ErrorMessageEnum.S3_UPLOAD_ERROR.getMessage(), e.getCause());
        }

        song.setNftImagePath(nftFileUrl);
        songRepository.save(song);

        Map<String, String> resData = new HashMap<>();
        resData.put("nftImageUrl", nftFileUrl);
        return DataResDto.builder()
                .data(resData)
                .statusMessage("NFT 이미지가 정상적으로 업로드되었습니다.")
                .build();
    }

    @Override
    public DataResDto<?> changeSongStatus(StateChangeReqDto stateChangeReqDto) {
        Song song = songRepository.findBySongUuid(stateChangeReqDto.getSongUUID())
                .orElseThrow(() -> new NotFoundException(ErrorMessageEnum.SONG_NOT_EXIST.getMessage()));

        song.setSongStatus(stateChangeReqDto.getSongStatus());
        Song savedSong = songRepository.save(song);

        Boolean res = false;

        if (savedSong.getSongStatus().equals(stateChangeReqDto.getSongStatus()))
            res = true;

        return DataResDto.builder()
                .statusMessage("음악 상태가 정상적으로 변경되었습니다.")
                .data(res)
                .build();
    }

    private File getAmazonObject(String songPath) {
        File file = null;
        try {
            GetObjectRequest objectRequest = GetObjectRequest.builder().bucket(s3Bucket).key(songPath.substring(s3BaseUrl.length())).build();

            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
            byte[] data = objectBytes.asByteArray();

            // Write the data to a local file.
            file = new File(tempFilePath + "/tmp.mp3");
            OutputStream os = new FileOutputStream(file);
            os.write(data);
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }

        return file;
    }
}