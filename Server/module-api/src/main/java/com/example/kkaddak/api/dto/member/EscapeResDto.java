package com.example.kkaddak.api.dto.member;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EscapeResDto {
    @ApiModelProperty(example = "status 상태 코드")
    private int statusCode;
    @ApiModelProperty(example = "status 상태 메세지")
    private String statusMessage;
    @ApiModelProperty(example = "false(회원가입 과정에서 중도이탈 여부 / boolean type)")
    private Boolean isEscape;

    @Builder
    public EscapeResDto(int statusCode, String statusMessage, Boolean isEscape) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.isEscape = isEscape;
    }
}
