package com.example.kkaddak.api.dto.member;

import com.example.kkaddak.api.utils.EmptyMultiPartFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditProfileReqDto {

    private String nickname;
    private MultipartFile profileImg;
    private Boolean isUpdating;

    @Builder
    public EditProfileReqDto(String nickname, MultipartFile profileImg, Boolean isUpdating) {
        this.nickname = nickname;
        this.isUpdating = isUpdating;
        EmptyMultiPartFile emptyFile = new EmptyMultiPartFile();
        this.profileImg = profileImg != null ? (MultipartFile) profileImg : emptyFile.create();
    }
}
