package com.example.kkaddak.api.dto.member;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TokenResDto {

    @ApiModelProperty(example = "access_token")
    private String accessToken;
    @ApiModelProperty(example = "refresh_token")
    private String refreshToken;
    @ApiModelProperty(example = "Bearer(토큰 접두어)")
    private String tokenType;
    @ApiModelProperty(example = "false(boolean type)")
    private Boolean isExist;

    @Builder
    public TokenResDto(String accessToken, String refreshToken, Boolean isExist) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
        this.refreshToken = refreshToken;
        this.isExist = isExist;
    }
}
