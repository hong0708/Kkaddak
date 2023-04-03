package com.example.kkaddak.api.service;


import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.song.NftReqDto;
import com.example.kkaddak.api.dto.song.SongReqDto;
import com.example.kkaddak.api.dto.song.StateChangeReqDto;
import com.example.kkaddak.core.entity.Member;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public interface SongService {
    DataResDto<?> uploadSong(SongReqDto songReqDto, Member member) throws IOException;

    DataResDto<?> deleteSong(Member member, UUID songUuid);

    DataResDto<?> getSong(UUID songUuid, Member member);

    DataResDto<?> getAllSong(Member member);

    DataResDto<?> getLatestSong(Member member);

    DataResDto<?> clickLikeSong(Member member, UUID songUuid);

    DataResDto<?> getLikeList(Member member);

    DataResDto<?> deleteMyPlayList(Member member, UUID songUuid);

    DataResDto<?> getPlayList(Member member);

    DataResDto<?> getSearchList(Member member, String keyWord, String genre);

    DataResDto<?> getSongByCreator(Member member);

    DataResDto<?> getPopularityList(Member member);

    DataResDto<?> getMemberSongs(Member member, String nickname);

    DataResDto<?> uploadNFTImage(NftReqDto nftReqDto) throws IOException;

    DataResDto<?> changeSongStatus(StateChangeReqDto stateChangeReqDto);
}
