package com.example.kkaddak.api.dto.member;


import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResDto {
    private int statusCode;
    private String statusMessage;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Boolean isExist;

    @Builder
    public TokenResDto(int statusCode,String statusMessage, String accessToken, String refreshToken, Boolean isExist) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
        this.refreshToken = refreshToken;
        this.isExist = isExist;
    }

    public void setStatusCode(int statusCode){
        this.statusCode = statusCode;
    }
    public void setStatusMessage(String message){
        this.statusMessage = message;
    }
}
