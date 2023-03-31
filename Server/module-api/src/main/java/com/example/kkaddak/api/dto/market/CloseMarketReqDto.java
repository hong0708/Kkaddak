package com.example.kkaddak.api.dto.market;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CloseMarketReqDto {
    private Integer marketId;

    @Builder
    public CloseMarketReqDto(Integer marketId) {
        this.marketId = marketId;
    }
}
