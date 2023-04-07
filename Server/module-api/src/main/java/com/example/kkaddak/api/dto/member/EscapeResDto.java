package com.example.kkaddak.api.dto.member;


import com.example.kkaddak.api.dto.BaseResDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EscapeResDto {

    @ApiModelProperty(example = "false(회원가입 과정에서 중도이탈 여부 / boolean type)")
    private Boolean isEscape;

    @Builder
    public EscapeResDto(Boolean isEscape) {
        this.isEscape = isEscape;
    }
}
