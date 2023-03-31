package com.example.kkaddak.api.dto.market;


import com.example.kkaddak.api.service.impl.MusicNFTContractWrapper;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.kkaddak.api.service.impl.MusicNFTContractWrapper.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarketDetailDto {
    private String nftImageUrl;
    private String coverImageUrl;
    private String creatorNickname;
    private BigInteger createdDate;
    private String trackTitle;
    private String combination;
    private List<History> saleHistoryList;
    private Boolean isSelling;
    private BigInteger price;
    private Boolean isLike;
    private String sellerNickname;
    private String sellerAccount;

    @Builder
    public MarketDetailDto(MusicNFTData nft, SaleInfo saleInfo, List histories, Boolean isLike, String sellerNickname, String sellerAccount) {
        this.nftImageUrl = nft.nftImageUrl;
        this.coverImageUrl = nft.coverImageUrl;
        this.creatorNickname = nft.creatorNickname;
        this.createdDate = nft.createdDate;
        this.trackTitle = nft.trackTitle;
        this.combination = nft.combination;
        this.isSelling = saleInfo.isSelling;
        this.price = saleInfo.price;
        this.isLike = isLike;
        this.sellerNickname = sellerNickname;
        this.sellerAccount = sellerAccount;
        this.saleHistoryList = (List<History>) histories.stream()
                .map(info -> History.builder()
                        .price(((SaleHistory)info).price)
                        .timestamp(((SaleHistory)info).timestamp)
                        .build())
                .collect(Collectors.toList());
    }

    @Getter
    private static class History{
        private BigInteger price;
        private BigInteger timestamp;

        @Builder
        public History(BigInteger price, BigInteger timestamp) {
            this.price = price;
            this.timestamp = timestamp;
        }
    }
}
