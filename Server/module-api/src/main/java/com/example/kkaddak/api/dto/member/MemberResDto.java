package com.example.kkaddak.api.dto.member;

import com.example.kkaddak.core.entity.Member;
import lombok.Builder;
import lombok.Getter;


@Getter
public class MemberResDto {

    private int statusCode;
    private String statusMessage;
    private String uuid;
    private String email;
    private String profilePath;
    private String nickname;
    private String memberType;

    @Builder
    public MemberResDto(Member member, int statusCode, String statusMessage) {
        this.uuid = member.getUuid().toString();
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
