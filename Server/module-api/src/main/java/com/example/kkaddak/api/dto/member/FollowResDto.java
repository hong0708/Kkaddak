package com.example.kkaddak.api.dto.member;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowResDto {
    private int myFollowers;
    private int myFollwings;

    @Builder
    public FollowResDto(int myFollowers, int myFollwings) {
        this.myFollowers = myFollowers;
        this.myFollwings = myFollwings;
    }
}
