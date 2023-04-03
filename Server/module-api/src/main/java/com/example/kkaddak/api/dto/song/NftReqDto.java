package com.example.kkaddak.api.dto.song;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class NftReqDto {
    @ApiModelProperty(
            value = "음악 ID",
            required = true,
            dataType = "String"
    )
    @NotNull(message = "음악 ID는 필수입니다.")
    UUID songUUID;

    @ApiModelProperty(
            value = "NFT 이미지 파일",
            required = true,
            dataType = "MultipartFile"
    )
    @NotNull(message = "NFT 이미지 파일은 필수입니다.")
    MultipartFile nftImage;


    @Builder
    public NftReqDto(UUID songUUID, MultipartFile nftImage) {
        this.songUUID = songUUID;
        this.nftImage = nftImage;
    }
}
