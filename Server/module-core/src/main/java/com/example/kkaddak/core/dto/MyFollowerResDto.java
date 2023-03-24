package com.example.kkaddak.core.dto;

import com.example.kkaddak.core.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyFollowerResDto {

    private int followerId;
    private String followerUuid;
    private String profilePath;
    private String nickname;
    private Boolean isFollowing;

    @Builder
    public MyFollowerResDto(Member member, Integer isFollowing) {
        this.followerId = member.getId();
        this.followerUuid = member.getUuid().toString();
        this.profilePath = member.getProfilePath();
        this.nickname = member.getNickname();
        this.isFollowing = !Objects.equals(isFollowing, 0);
    }
}
