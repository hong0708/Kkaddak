package com.example.kkaddak.api.service.impl;

import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.SongReqDto;
import com.example.kkaddak.api.dto.SongResDto;
import com.example.kkaddak.api.dto.member.MemberResDto;
import com.example.kkaddak.api.service.SongService;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.entity.Song;
import com.example.kkaddak.core.repository.MemberRepository;
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

    private final MemberRepository memberRepository;

    @Override
    public DataResDto<?> uploadSong(SongReqDto songReqDto, UUID memberUuid) throws IOException {
        try {
            Member member = memberRepository.findByUuid(memberUuid)
                    .orElseThrow(() -> new IllegalArgumentException("멤버 id값이 존재하지 않습니다"));

            if (songReqDto.getSongTitle() == null || songReqDto.getSongPath() == null ||
                    songReqDto.getCoverPath() == null || songReqDto.getGenre() == null || songReqDto.getMood() == null) {
                throw new IllegalArgumentException("songReqDto값이 정확하지 않습니다");
            }

            Song song = Song.builder()
                    .title(songReqDto.getSongTitle())
                    .songPath(songReqDto.getSongPath())
                    .coverPath(songReqDto.getCoverPath())
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
            return DataResDto.builder().statusCode(400).statusMessage("음악 Uuid가 올바르지 않습니다.").build();
        }

    }

    @Override
    public DataResDto<?> getAllSong() {
        try {
            List<Song> songList = songRepository.findAll();
            if (songList == null || songList.isEmpty()) {
                return DataResDto.builder().data(Collections.emptyList())
                        .statusMessage("음악 정보가 정상적으로 출력되었습니다.").statusCode(200).build();
            }
            List<SongResDto> songResDtoList = songList.stream().map(song -> new SongResDto(song)).collect(Collectors.toList());
            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("음악 정보가 정상적으로 출력되었습니다.").statusCode(200).build();
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
                        .statusMessage("음악 정보가 정상적으로 출력되었습니다.").statusCode(200).build();
            }
            List<SongResDto> songResDtoList = songList.stream().map(song -> new SongResDto(song)).collect(Collectors.toList());
            return DataResDto.builder().data(songResDtoList)
                    .statusMessage("음악 정보가 정상적으로 출력되었습니다.").statusCode(200).build();
        } catch(Exception e) {
            return DataResDto.builder().statusCode(500).statusMessage("서버 에러").build();
        }
    }
}