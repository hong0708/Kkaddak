package com.example.kkaddak.api.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SocialSignupReqDto {
    private String email;

    @Builder
    public SocialSignupReqDto(String email) {
        this.email = email;
    }
}
