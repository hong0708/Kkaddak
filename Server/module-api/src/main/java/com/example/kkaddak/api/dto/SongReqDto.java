package com.example.kkaddak.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SongReqDto {
    @ApiModelProperty(
            value = "음악 제목",
            required = true,
            dataType = "String"
    )
    String songTitle;
    @ApiModelProperty(
            value = "음악 파일 경로",
            required = true,
            dataType = "String"
    )
    String songPath;
    @ApiModelProperty(
            value = "음악 커버 이미지 경로",
            required = true,
            dataType = "String"
    )
    String coverPath;
    @ApiModelProperty(
            value = "음악 장르",
            required = true,
            dataType = "String"
    )
    String genre;
    @ApiModelProperty(
            value = "음악 분위기",
            required = true,
            dataType = "List"
    )
    List<String> moods;

    @Builder
    public SongReqDto(String songTitle, String songPath, String coverPath, String genre, List<String> moods) {
        this.songTitle = songTitle;
        this.songPath = songPath;
        this.coverPath = coverPath;
        this.genre = genre;
        this.moods = moods;
    }
}
