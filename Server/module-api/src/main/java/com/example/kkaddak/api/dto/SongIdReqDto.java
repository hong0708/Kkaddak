package com.example.kkaddak.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SongIdReqDto {
    @ApiModelProperty(
            value = "음악 UUID값",
            required = true,
            dataType = "String"
    )
    private String songId;
    @Builder
    public SongIdReqDto(String songId) {
        this.songId = songId;
    }
}
