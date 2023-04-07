package com.example.kkaddak.api.dto.member;


import com.example.kkaddak.api.utils.EmptyMultiPartFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileReqDto {
    @ApiModelProperty(
            value = "닉네임",
            required = true,
            dataType = "String"
    )
    private String nickname;

    @ApiModelProperty(
            value = "업로드할 이미지 파일(max-file-size: 10MB / max-request-size: 10MB)",
            required = true,
            dataType = "png/ jpeg 등 이미지 MultipartFile"
    )
    private MultipartFile profileImg;

    @Builder
    public ProfileReqDto(String nickname, Object profileImg) {
        this.nickname = nickname;

        EmptyMultiPartFile emptyFile = new EmptyMultiPartFile();
        this.profileImg = !Objects.isNull(profileImg) ? (MultipartFile) profileImg : emptyFile.create();
    }
}
