package com.example.kkaddak.core.entity;

import com.example.kkaddak.core.dto.MarketReqDto;
import lombok.*;

import javax.persistence.*;

@Entity
@ToString(callSuper = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Market extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Member seller;
    private String nftId;
    private String creatorName;
    private String songTitle;
    private String nftImagePath;
    private Double price;
    private Boolean isClose;

    @Builder
    public Market(Member seller, MarketReqDto market) {
        this.seller = seller;
        this.nftId = market.getNftId();
        this.creatorName = market.getCreatorName();
        this.songTitle = market.getSongTitle();
        this.nftImagePath = market.getNftImagePath();
        this.price = market.getPrice();
        this.isClose = false;
    }

    public void closeMarket(){
        this.isClose = true;
    }
}
