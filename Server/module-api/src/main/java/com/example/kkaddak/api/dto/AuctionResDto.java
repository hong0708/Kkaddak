package com.example.kkaddak.api.dto;

import com.example.kkaddak.core.entity.Auction;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionResDto {

    private int auctionId;
    private String nftId;
    private String creatorName;
    private String songTitle;
    private String nftImagePath;
    private String expiredDate;
    private Double bidStartPrice;
    private Boolean isClose;

    @Builder
    public AuctionResDto(Auction auction) {
        this.auctionId = auction.getId();
        this.nftId = auction.getNftId();
        this.nftImagePath = auction.getNftImagePath();
        this.creatorName = auction.getCreatorName();;
        this.songTitle = auction.getSongTitle();;
        this.expiredDate = auction.getExpiredDate().toString();
        this.bidStartPrice = auction.getBidStartPrice();
        this.isClose = auction.getIsClose() ;
    }
}
