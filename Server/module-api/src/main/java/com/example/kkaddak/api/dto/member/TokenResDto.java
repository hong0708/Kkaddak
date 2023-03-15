package com.example.kkaddak.api.dto.member;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResDto {
    @ApiModelProperty(example = "status 상태 코드")
    private int statusCode;
    @ApiModelProperty(example = "status 상태 메세지")
    private String statusMessage;
    @ApiModelProperty(example = "access_token")
    private String accessToken;
    @ApiModelProperty(example = "refresh_token")
    private String refreshToken;
    @ApiModelProperty(example = "Bearer(토큰 접두어)")
    private String tokenType;
    @ApiModelProperty(example = "false(boolean type)")
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
