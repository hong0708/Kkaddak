package com.example.kkaddak.api.dto.market;

import com.example.kkaddak.core.entity.Market;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarketResDto {

    private int marketId;
    private String nftId;
    private String creatorName;
    private String songTitle;
    private String nftImagePath;
    private String createdAt;
    private Double price;
    private Boolean isClose;

    @Builder
    public MarketResDto(Market market) {
        this.marketId = market.getId();
        this.nftId = market.getNftId();
        this.nftImagePath = market.getNftImagePath();
        this.creatorName = market.getCreatorName();
        this.songTitle = market.getSongTitle();
        this.createdAt = market.getCreatedAt().toString();
        this.price = market.getPrice();
        this.isClose = market.getIsClose() ;
    }
}
