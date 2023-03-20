package com.example.kkaddak.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class SongReqDto {
    @ApiModelProperty(
            value = "음악 제목",
            required = true,
            dataType = "String"
    )
    @NotNull(message = "음악 제목은 필수입니다.")
    String songTitle;

    @ApiModelProperty(
            value = "음악 파일",
            required = true,
            dataType = "MultipartFile"
    )
    @NotNull(message = "음악 파일은 필수입니다.")
    MultipartFile songFile;

    @ApiModelProperty(
            value = "음악 커버 이미지 파일",
            required = true,
            dataType = "MultipartFile"
    )
    @NotNull(message = "음악 커버 이미지 파일은 필수입니다.")
    MultipartFile coverFile;

    @ApiModelProperty(
            value = "음악 장르",
            required = true,
            dataType = "String"
    )
    @NotNull(message = "음악 장르는 필수입니다.")
    String genre;

    @ApiModelProperty(
            value = "음악 분위기",
            required = true,
            dataType = "String"
    )
    @NotNull(message = "음악 분위기는 필수입니다.")
    String mood;

    @Builder
    public SongReqDto(String songTitle, MultipartFile songFile, MultipartFile coverFile, String genre, String mood) {
        if (songFile.isEmpty() || coverFile.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어 있습니다.");
        }

        this.songTitle = songTitle;
        this.songFile = songFile;
        this.coverFile = coverFile;
        this.genre = genre;
        this.mood = mood;
    }
}
