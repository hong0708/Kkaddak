package com.example.kkaddak.api.dto.song;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @NotEmpty(message = "음악 제목은 필수입니다.")
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
    @NotNull(message = "커버 이미지 파일은 필수입니다.")
    MultipartFile coverFile;

    @ApiModelProperty(
            value = "음악 장르",
            required = true,
            dataType = "String"
    )
    @NotEmpty(message = "장르는 필수입니다.")
    String genre;
    @ApiModelProperty(
            value = "음악 분위기",
            required = true,
            dataType = "List"
    )
    @NotEmpty(message = "분위기는 필수입니다.")
    List<String> moods;

    @Builder
    public SongReqDto(String songTitle, MultipartFile songFile, MultipartFile coverFile, String genre, List<String> moods) {
        this.songTitle = songTitle;
        this.songFile = songFile;
        this.coverFile = coverFile;
        this.genre = genre;
        this.moods = moods;
    }
}
