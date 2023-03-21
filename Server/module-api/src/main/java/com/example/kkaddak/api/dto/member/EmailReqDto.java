package com.example.kkaddak.api.dto.member;


import lombok.Getter;

@Getter
public class EmailReqDto {
    private String email;


    public EmailReqDto(String email) {
        this.email = email;
    }

}
