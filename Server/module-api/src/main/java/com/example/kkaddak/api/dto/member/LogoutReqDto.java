package com.example.kkaddak.api.dto.member;


import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogoutReqDto {


    @ApiModelProperty(example = "access_token")
    private String atk;

    @ApiModelProperty(example = "refresh_token")
    private String rtk;

    @Builder
    public LogoutReqDto(String atk, String rtk) {
        this.atk = atk;
        this.rtk = rtk;
    }
}
