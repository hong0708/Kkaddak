package com.example.kkaddak.api.service;


import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.SongReqDto;
import com.example.kkaddak.api.dto.SongResDto;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.entity.Song;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface SongService {
    DataResDto<?> uploadSong(SongReqDto songReqDto, Member member) throws IOException;

    DataResDto<?> getSong(UUID songUuid, Member member);

    DataResDto<?> getAllSong();

    DataResDto<?> getLatestSong();

    DataResDto<?> clickLikeSong(Member member, UUID songUuid);

    DataResDto<?> getLikeList(Member member);

    DataResDto<?> deleteMyPlayList(Member member, UUID songUuid);

    DataResDto<?> getPlayList(Member member);

    DataResDto<?> getSearchList(Map<String, String> param);
}
