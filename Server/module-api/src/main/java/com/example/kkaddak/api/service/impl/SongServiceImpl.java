package com.example.kkaddak.api.service.impl;

import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.SongReqDto;
import com.example.kkaddak.api.dto.SongResDto;
import com.example.kkaddak.api.dto.member.MemberResDto;
import com.example.kkaddak.api.service.SongService;
import com.example.kkaddak.core.entity.LikeList;
import com.example.kkaddak.api.utils.AmazonS3Client;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.entity.PlayList;
import com.example.kkaddak.core.entity.Song;
import com.example.kkaddak.core.repository.LikeListRepository;
import com.example.kkaddak.core.repository.MemberRepository;
import com.example.kkaddak.core.repository.PlayListRepository;
import com.example.kkaddak.core.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;

    private final LikeListRepository likeListRepository;

    private final PlayListRepository playListRepository;

    private final AmazonS3Client amazonS3Client;
    @Override
    public DataResDto<?> uploadSong(SongReqDto songReqDto, Member member) throws IOException {
        try {
            // AmazonS3Client 인스턴스 생성
            AmazonS3Client amazonS3Client = new AmazonS3Client("region", "accessKeyId", "secretAccessKey", "bucketName");

            // 파일 업로드하고 S3 URL 받아오기
            String songUrl = amazonS3Client.uploadFile("songs", songReqDto.getSongFile().getOriginalFilename(), songReqDto.getSongFile().getInputStream());
            String coverUrl = amazonS3Client.uploadFile("covers", songReqDto.getCoverFile().getOriginalFilename(), songReqDto.getCoverFile().getInputStream());

            Song song = Song.builder()
                    .title(songReqDto.getSongTitle())
                    .songPath(songUrl)
                    .coverPath(coverUrl)
                    .genre(songReqDto.getGenre())
                    .mood(songReqDto.getMood())
                    .member(member)
                    .build();

            Song savedSong = songRepository.save(song);

            SongResDto songResDto = new SongResDto(savedSong);
            return DataResDto.builder().data(songResDto)
                    .statusMessage("음악 정보가 정상적으로 출력되었습니다.").statusCode(200).build();
        } catch(IllegalArgumentException e) {
            return DataResDto.builder().statusCode(400).statusMessage(e.getMessage()).build();
        } catch(Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> getSong(Integer songId) {
        try {
            Song song = songRepository.findById(songId)
                    .orElseThrow(() -> new IllegalArgumentException("값이 존재하지 않습니다"));
            SongResDto songResDto = new SongResDto(song);
            return DataResDto.builder().data(songResDto)
                    .statusMessage("음악 정보가 정상적으로 출력되었습니다.").statusCode(200).build();
        }
        catch(IllegalArgumentException e){
            return DataResDto.builder().statusCode(400).statusMessage("음악 songId가 올바르지 않습니다.").build();
        }

    }

    @Override
    public DataResDto<?> getAllSong() {
        try {
            List<Song> songList = songRepository.findAll();
            if (songList == null || songList.isEmpty()) {
                return DataResDto.builder().data(Collections.emptyList())
                        .statusMessage("음악 리스트 정보가 정상적으로 출력되었습니다.").statusCode(200).build();
            }
            List<SongResDto> songResDtoList = songList.stream().map(song -> new SongResDto(song)).collect(Collectors.toList());
            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("음악 리스트 정보가 정상적으로 출력되었습니다.").statusCode(200).build();
        } catch(Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> getLatestSong() {
        try {
            List<Song> songList = songRepository.findTop10ByOrderByUploadedAtDesc();
            if (songList == null || songList.isEmpty()) {
                return DataResDto.builder().data(Collections.emptyList())
                        .statusMessage("음악 최신 리스트 정보가 정상적으로 출력되었습니다.").statusCode(200).build();
            }
            List<SongResDto> songResDtoList = songList.stream().map(song -> new SongResDto(song)).collect(Collectors.toList());
            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("음악 최신 리스트 정보가 정상적으로 출력되었습니다.").statusCode(200).build();
        } catch(Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> clickLikeSong(Member member, Integer songId) {
        try {
            Song song = songRepository.findById(songId)
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
            return DataResDto.builder().statusCode(200).statusMessage(message).statusCode(200).build();
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
                        .statusMessage("음악 좋아요 리스트가 정상적으로 출력되었습니다.").statusCode(200).build();
            }
            List<SongResDto> songResDtoList = likeList.stream().map(likes -> new SongResDto(likes.getSong())).collect(Collectors.toList());
            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("음악 좋아요 리스트가 정상적으로 출력되었습니다.").statusCode(200).build();
        } catch (Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> addMyPlayList(Member member, Integer songId) {
        try {
            Song song = songRepository.findById(songId)
                    .orElseThrow(() -> new IllegalArgumentException("음악 songId가 올바르지 않습니다."));

            boolean checkValue = playListRepository.existsByMemberAndSong(member, song);
            if (!checkValue) {
                PlayList playList = PlayList.builder()
                        .member(member)
                        .song(song)
                        .build();
                playListRepository.save(playList);
            } else {
                throw new IllegalArgumentException("이미 나의 플레이 리스트에 존재하는 음악 입니다.");
            }

            return DataResDto.builder().statusCode(200).statusMessage("음악을 플레이리스트에 추가하였습니다.").statusCode(200).build();
        } catch (IllegalArgumentException e) {
            return DataResDto.builder().statusCode(400).statusMessage(e.getMessage()).build();
        } catch (Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> deleteMyPlayList(Member member, Integer songId) {
        try {
            Song song = songRepository.findById(songId)
                    .orElseThrow(() -> new IllegalArgumentException(""));

            boolean checkValue = playListRepository.existsByMemberAndSong(member, song);
            if (checkValue) {

                PlayList playList = playListRepository.findByMemberAndSong(member, song)
                        .orElseThrow(() -> new IllegalArgumentException(""));

                playListRepository.delete(playList);
            } else {
                throw new NullPointerException();
            }

            return DataResDto.builder().statusCode(200).statusMessage("음악을 플레이리스트에서 제거하였습니다").statusCode(200).build();
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
                        .statusMessage("음악 정보가 정상적으로 출력되었습니다.").statusCode(200).build();
            }
            List<SongResDto> songResDtoList = playList.stream().map(play -> new SongResDto(play.getSong())).collect(Collectors.toList());
            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("음악 정보가 정상적으로 출력되었습니다.").statusCode(200).build();
        } catch (Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }
}