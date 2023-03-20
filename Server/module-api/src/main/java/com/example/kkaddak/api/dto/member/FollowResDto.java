package com.example.kkaddak.api.dto.member;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowResDto {
    private Long myFollowers;
    private Long myFollwings;

    @Builder
    public FollowResDto(Long myFollowers, Long myFollwings) {
        this.myFollowers = myFollowers;
        this.myFollwings = myFollwings;
    }
}
