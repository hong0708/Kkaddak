package com.example.kkaddak.core.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionConditionReqDto {

    private int limit;
    private int lastId;
    private boolean onlySelling;

    @Builder
    public AuctionConditionReqDto(int limit, int lastId, boolean onlySelling) {
        this.limit = limit;
        this.lastId = lastId;
        this.onlySelling = onlySelling;
    }
}
