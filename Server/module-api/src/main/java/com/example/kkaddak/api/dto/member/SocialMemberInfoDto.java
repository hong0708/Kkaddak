package com.example.kkaddak.api.dto.member;


import lombok.Getter;

@Getter
public class SocialMemberInfoDto {
    private String email;

    public SocialMemberInfoDto(String email) {
        this.email = email;
    }

}
