package com.example.kkaddak.api.dto.member;


import lombok.Builder;
import lombok.Getter;

@Getter
public class EscapeResDto {

    private int statusCode;
    private String statusMessage;
    private Boolean isEscape;

    @Builder
    public EscapeResDto(int statusCode, String statusMessage, Boolean isEscape) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.isEscape = isEscape;
    }
}
