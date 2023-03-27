package com.example.kkaddak.api.dto;


import com.example.kkaddak.core.entity.Song;
import com.example.kkaddak.core.entity.SongStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerSongResDto {

    private String songId;
    private String songTitle;
    private String coverPath;
    private SongStatus songStatus;
    private String songPath;
    private String genre;
    private List<String> moods = new ArrayList<>();
    private Long uploadDate;
    private String nickname;
    private Boolean isLike = false;
    private List<Integer> combination = new ArrayList<>();

    @Builder
    public OwnerSongResDto(Song song) {
        this.songId = song.getSongUuid().toString();
        this.songTitle = song.getTitle();
        this.songStatus = song.getSongStatus();
        this.coverPath = song.getCoverPath();
    }
}
