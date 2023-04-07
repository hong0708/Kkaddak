package com.example.kkaddak.core.dto;


import com.example.kkaddak.core.entity.Market;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarketConditionResDto {
    private int marketId;
    private String nftId;
    private String creatorName;
    private String songTitle;
    private String nftImagePath;
    private String createdAt;
    private Double price;
    private Boolean isClose;
    private Long cntLikeMarket;
    private Boolean isLike;

    @Builder
    public MarketConditionResDto(Market market, Long cntLikeMarket, Boolean isLike) {
        this.marketId = market.getId();
        this.nftId = market.getNftId();
        this.nftImagePath = market.getNftImagePath();
        this.creatorName = market.getCreatorName();
        this.songTitle = market.getSongTitle();
        this.createdAt = market.getCreatedAt().toString();
        this.price = market.getPrice();
        this.isClose = market.getIsClose();
        this.cntLikeMarket = cntLikeMarket;
        this.isLike = isLike;
    }
}
