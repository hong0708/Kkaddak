package com.example.kkaddak.core.entity;

import com.example.kkaddak.core.dto.AuctionReqDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@ToString(callSuper = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Member seller;
    private String nftId;
    private String creatorName;
    private String songTitle;
    private String nftImagePath;
    private LocalDate expiredDate;
    private Double bidStartPrice;
    private Boolean isClose;
    private Double highestBidPrice;

    @Builder
    public Auction(Member seller, AuctionReqDto auction) {
        this.seller = seller;
        this.nftId = auction.getNftId();
        this.creatorName = auction.getCreatorName();
        this.songTitle = auction.getSongTitle();
        this.nftImagePath = auction.getNftImagePath();
        this.expiredDate = auction.getExpiredDate();
        this.bidStartPrice = auction.getBidStartPrice();
        this.isClose = false;
    }

    public void setFinalPrice(Double finalPrice){
        this.highestBidPrice = finalPrice;
    }
}
