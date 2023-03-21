package com.example.kkaddak.api.dto.member;

import com.example.kkaddak.core.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileResDto {

    private String memberId;
    private String nickname;
    private String email;
    private String profilepath;
    private String account;
    private boolean isMine;

    @Builder
    public ProfileResDto(Member member, boolean isMine) {
        this.memberId = member.getUuid().toString();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.profilepath = member.getProfilePath();
        this.account = member.getAccount();
        this.isMine = isMine;
    }
}
