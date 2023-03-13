package com.example.kkaddak.api.utils;

import lombok.Getter;

@Getter

public enum ErrorMessageEnum {
    NON_LOGIN("토큰이 업습니다."),
    EXPIRED_TOKEN("토큰 만료되었습니다."),
    INVALID_TOKEN("토큰 서명이 유효하지 않습니다.");


    private String message;

    ErrorMessageEnum(String message) {
        this.message = message;
    }
}
