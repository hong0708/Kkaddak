package com.example.kkaddak.api.service.impl;

import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.SongReqDto;
import com.example.kkaddak.api.dto.SongResDto;
import com.example.kkaddak.api.service.NFTService;
import com.example.kkaddak.api.service.SongService;
import com.example.kkaddak.core.entity.*;
import com.example.kkaddak.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;

    private final LikeListRepository likeListRepository;

    private final PlayListRepository playListRepository;

    private final NFTService nftService;

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String s3Bucket;

    @Value("${aws.s3.base-url}")
    private String s3BaseUrl;

    private final MoodRepository moodRepository;

    private final SearchRepository searchRepository;

    @Override
    public DataResDto<?> uploadSong(SongReqDto songReqDto, Member member) throws IOException {
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

            Song song = Song.builder()
                    .title(songReqDto.getSongTitle())
                    .songPath(songFileUrl)
                    .coverPath(coverFileUrl)
                    .genre(songReqDto.getGenre())
                    .moods(savedMood)
                    .member(member)
                    .songStatus(SongStatus.PROCEEDING)
                    .build();

            Song savedSong = songRepository.save(song);

            SongResDto songResDto = new SongResDto(savedSong);
            return DataResDto.builder().data("").data(songResDto)
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

            boolean like = likeListRepository.existsByMemberAndSong(member, song);
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

            SongResDto songResDto = new SongResDto(song, like);
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
                boolean like = likeListRepository.existsByMemberAndSong(member, song);
                songResDtoList.add(new SongResDto(song, like));
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
                boolean like = likeListRepository.existsByMemberAndSong(member, song);
                songResDtoList.add(new SongResDto(song, like));
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
                boolean like = likeListRepository.existsByMemberAndSong(member, likes.getSong());
                songResDtoList.add(new SongResDto(likes.getSong(), like));
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
                boolean like = likeListRepository.existsByMemberAndSong(member, plays.getSong());
                songResDtoList.add(new SongResDto(plays.getSong(), like));
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
                boolean like = likeListRepository.existsByMemberAndSong(member, song);
                songResDtoList.add(new SongResDto(song, like));
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
                boolean like = likeListRepository.existsByMemberAndSong(member, song);
                songResDtoList.add(new SongResDto(song, like));
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
                boolean like = likeListRepository.existsByMemberAndSong(member, song);
                songResDtoList.add(new SongResDto(song, like));
            }

            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("인기 음악 리스트가 정상적으로 출력되었습니다.").build();
        } catch (Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }
}