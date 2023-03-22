package com.example.kkaddak.api.dto.member;

import com.example.kkaddak.core.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileResDto {

    private String memberId;
    private String nickname;
    private String email;
    private String profilepath;
    private String account;
    private boolean isMine;
    private int myFollowers;
    private int myFolloings;
    private int mySongs;

    @Builder
    public ProfileResDto(Member member, boolean isMine, int myFollowers, int myFollowings, int mySongs) {
        this.memberId = member.getUuid().toString();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.profilepath = member.getProfilePath();
        this.account = member.getAccount();
        this.isMine = isMine;
        this.myFollowers = myFollowers;
        this.myFolloings = myFollowings;
        this.mySongs = mySongs;
    }
}
