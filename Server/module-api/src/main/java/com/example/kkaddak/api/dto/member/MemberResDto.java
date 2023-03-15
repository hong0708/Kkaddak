package com.example.kkaddak.api.dto.member;

import com.example.kkaddak.core.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;


@Getter
public class MemberResDto {
    @ApiModelProperty(example = "status 상태 코드")
    private int statusCode;
    @ApiModelProperty(example = "status 상태 메세지")
    private String statusMessage;
    @ApiModelProperty(example = "member 식별 아이디")
    private String memberId;
    @ApiModelProperty(example = "email")
    private String email;
    @ApiModelProperty(example = "프로필 이미지 경로")
    private String profilePath;
    @ApiModelProperty(example = "닉네임")
    private String nickname;
    @ApiModelProperty(example = "유저 타입(회원 / 관리자")
    private String memberType;

    @Builder
    public MemberResDto(Member member, int statusCode, String statusMessage) {
        this.memberId = member.getUuid().toString();
        this.email = member.getEmail();
        this.profilePath = member.getProfilePath();
        this.nickname = member.getNickname();
        this.memberType = member.getMemberType().getParam();
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
    public void setStatusCode(int statusCode){
        this.statusCode = statusCode;
    }
    public void setStatusMessage(String message) { this.statusMessage = message; }
}
