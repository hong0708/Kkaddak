package com.example.kkaddak.api.dto.market;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CloseMarketReqDto {
    private Integer marketId;

    @Builder
    public CloseMarketReqDto(Integer marketId) {
        this.marketId = marketId;
    }
}
