package com.example.kkaddak.api.dto.member;


import com.example.kkaddak.api.utils.EmptyMultiPartFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupReqDto {

    private String nickname;
    private MultipartFile profileImg;

    @Builder
    public SignupReqDto(String nickname, String memberType, Object profileImg) {
        this.nickname = nickname;

        EmptyMultiPartFile emptyFile = new EmptyMultiPartFile();
        this.profileImg = !Objects.isNull(profileImg) ? (MultipartFile) profileImg : emptyFile.create();
    }
}
