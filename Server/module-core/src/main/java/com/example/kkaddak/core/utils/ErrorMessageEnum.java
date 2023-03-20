package com.example.kkaddak.core.utils;


import lombok.Getter;

@Getter
public enum ErrorMessageEnum {
    USER_NOT_EXIST("존재하지 않는 회원입니다"),
    FOLLOW_NOT_EXIST("팔로우하고 있지 않는 유저입니다.");

    private String message;

    ErrorMessageEnum(String message) {
        this.message = message;
    }
}
