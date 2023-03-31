package com.example.kkaddak.api.dto.market;


import com.example.kkaddak.core.entity.Market;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.kkaddak.api.service.impl.MusicNFTContractWrapper.SaleHistory;
import static com.example.kkaddak.api.service.impl.MusicNFTContractWrapper.SaleInfo;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarketDetailDto {
    private String nftImageUrl;
    private String creatorNickname;
    private String songTitle;
    private List<History> saleHistoryList;
    private Boolean isSelling;
    private BigInteger price;
    private Boolean isLike;
    private String sellerNickname;
    private String sellerAccount;

    @Builder
    public MarketDetailDto(Market market, SaleInfo saleInfo, List histories, Boolean isLike, String sellerNickname, String sellerAccount) {
        this.nftImageUrl = market.getNftImagePath();
        this.creatorNickname = market.getCreatorName();
        this.songTitle = market.getSongTitle();
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
        private String timestamp;

        @Builder
        public History(BigInteger price, BigInteger timestamp) {
            this.price = price;
            this.timestamp = convertType(timestamp);
        }

        private String convertType(BigInteger timestamp){
            return LocalDateTime.ofEpochSecond(timestamp.longValue(), 0, ZoneOffset.of("+09:00")).toString();
        }
    }
}
