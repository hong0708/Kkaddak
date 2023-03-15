package com.example.kkaddak.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
            dataType = "String"
    )
    String mood;

    @Builder
    public SongReqDto(String songTitle, String songPath, String coverPath, String genre, String mood) {
        this.songTitle = songTitle;
        this.songPath = songPath;
        this.coverPath = coverPath;
        this.genre = genre;
        this.mood = mood;
    }
}
