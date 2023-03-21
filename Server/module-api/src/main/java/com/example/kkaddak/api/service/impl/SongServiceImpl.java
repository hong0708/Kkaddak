package com.example.kkaddak.api.service.impl;

import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.SongReqDto;
import com.example.kkaddak.api.dto.SongResDto;
import com.example.kkaddak.api.dto.member.MemberResDto;
import com.example.kkaddak.api.service.SongService;
import com.example.kkaddak.core.entity.*;
import com.example.kkaddak.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;

    private final LikeListRepository likeListRepository;

    private final PlayListRepository playListRepository;

    private final MoodRepository moodRepository;

    @Override
    public DataResDto<?> uploadSong(SongReqDto songReqDto, Member member) throws IOException {
        try {
            if (songReqDto.getSongTitle() == null || songReqDto.getSongPath() == null ||
                    songReqDto.getCoverPath() == null || songReqDto.getGenre() == null || songReqDto.getMoods() == null) {
                throw new IllegalArgumentException("songReqDto값이 정확하지 않습니다");
            }

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
                    .songPath(songReqDto.getSongPath())
                    .coverPath(songReqDto.getCoverPath())
                    .genre(songReqDto.getGenre())
                    .moods(savedMood)
                    .member(member)
                    .build();

            Song savedSong = songRepository.save(song);

            SongResDto songResDto = new SongResDto(savedSong);
            return DataResDto.builder().data(songResDto)
                    .statusMessage("음악 정보가 정상적으로 출력되었습니다.").build();
        } catch(IllegalArgumentException e) {
            return DataResDto.builder().statusCode(400).statusMessage(e.getMessage()).build();
        } catch(Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> getSong(UUID songUuid, Member member) {
        try {
            Song song = songRepository.findBySongUuid(songUuid)
                    .orElseThrow(() -> new IllegalArgumentException("값이 존재하지 않습니다"));

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
    public DataResDto<?> getAllSong() {
        try {
            List<Song> songList = songRepository.findAll();
            if (songList == null || songList.isEmpty()) {
                return DataResDto.builder().data(Collections.emptyList())
                        .statusMessage("음악 리스트 정보가 정상적으로 출력되었습니다.").build();
            }
            List<SongResDto> songResDtoList = songList.stream().map(song -> new SongResDto(song)).collect(Collectors.toList());
            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("음악 리스트 정보가 정상적으로 출력되었습니다.").build();
        } catch(Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> getLatestSong() {
        try {
            List<Song> songList = songRepository.findTop5ByOrderByUploadedAtDesc();
            if (songList == null || songList.isEmpty()) {
                return DataResDto.builder().data(Collections.emptyList())
                        .statusMessage("음악 최신 리스트 정보가 정상적으로 출력되었습니다.").build();
            }
            List<SongResDto> songResDtoList = songList.stream().map(song -> new SongResDto(song)).collect(Collectors.toList());
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
            return DataResDto.builder().statusMessage(message).build();
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
            List<SongResDto> songResDtoList = likeList.stream().map(likes -> new SongResDto(likes.getSong())).collect(Collectors.toList());
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
            List<SongResDto> songResDtoList = playList.stream().map(play -> new SongResDto(play.getSong())).collect(Collectors.toList());
            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("음악 정보가 정상적으로 출력되었습니다.").build();
        } catch (Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }

    @Override
    public DataResDto<?> getSearchList(Map<String, String> param) {
        try {

            return DataResDto.builder().data("d")
                    .statusMessage("음악 정보가 정상적으로 출력되었습니다.").build();
        } catch (Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }
}