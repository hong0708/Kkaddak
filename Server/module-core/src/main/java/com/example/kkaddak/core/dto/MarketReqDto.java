package com.example.kkaddak.core.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarketReqDto {

    private String nftId;
    private String creatorName;
    private String nftImagePath;
    private String songTitle;
    private Double price;

    @Builder
    public MarketReqDto(String nftId, String nftImagePath, String creatorName, String songTitle, Double price) {
        this.nftId = nftId;
        this.nftImagePath = nftImagePath;
        this.creatorName = creatorName;
        this.songTitle = songTitle;
        this.price = price;
    }
}
