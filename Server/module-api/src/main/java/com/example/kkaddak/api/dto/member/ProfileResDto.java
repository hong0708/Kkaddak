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
    private Boolean isMine;
    private Boolean isFollowing;
    private int myFollowers;
    private int myFollowings;
    private int mySongs;

    @Builder
    public ProfileResDto(Member member, Boolean isMine, Boolean isFollowing, int myFollowers, int myFollowings, int mySongs) {
        this.memberId = member.getUuid().toString();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.profilepath = member.getProfilePath();
        this.account = member.getAccount();
        this.isMine = isMine;
        this.isFollowing = isFollowing;
        this.myFollowers = myFollowers;
        this.myFollowings = myFollowings;
        this.mySongs = mySongs;
    }
}
