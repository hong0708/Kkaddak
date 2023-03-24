package com.example.kkaddak.core.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyFollowConditionDto {

    private int limit;
    private int lastId;

    @Builder
    public MyFollowConditionDto(int limit, int lastId) {
        this.limit = limit;
        this.lastId = lastId;
    }
}
