package com.example.kkaddak.api.service;


import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.SongReqDto;
import com.example.kkaddak.api.dto.SongResDto;
import com.example.kkaddak.core.entity.Song;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public interface SongService {
    DataResDto<?> uploadSong(SongReqDto songReqDto, UUID memberUuid) throws IOException;

    DataResDto<?> getSong(Integer songId);

    DataResDto<?> getAllSong();

    DataResDto<?> getLatestSong();
}
