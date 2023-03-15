package com.example.kkaddak.api.service.impl;

import com.example.kkaddak.api.dto.SongReqDto;
import com.example.kkaddak.api.dto.SongResDto;
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

@RequiredArgsConstructor
@Service
public class SongServiceImpl implements SongService {
    @Autowired
    SongRepository songRepository;

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Song uploadSong(SongReqDto songReqDto, UUID memberUuid) throws IOException {
        Member member = memberRepository.findByUuid(memberUuid)
                .orElseThrow(() -> new IllegalArgumentException("값이 존재하지 않습니다"));

        Song song = Song.builder()
                .title(songReqDto.getSongTitle())
                .songPath(songReqDto.getSongPath())
                .coverPath(songReqDto.getCoverPath())
                .genre(songReqDto.getGenre())
                .mood(songReqDto.getMood())
                .member(member)
                .build();

        return songRepository.save(song);
    }

    @Override
    public Song getSong(UUID songUuid) {
        Song song = songRepository.findBySongUuid(songUuid)
                .orElseThrow(() -> new IllegalArgumentException("값이 존재하지 않습니다"));

        return song;
    }

    @Override
    public List<Song> getAllSong() {
        List<Song> songList = songRepository.findAll();
        if (songList == null) {
            return Collections.emptyList();
        }
        return songList;
    }

    @Override
    public List<Song> getLatestSong() {
        List<Song> songList = songRepository.findTop10ByOrderByUploadedAtDesc();
        if (songList == null || songList.isEmpty()) {
            return Collections.emptyList();
        }

        return songList;
    }
}