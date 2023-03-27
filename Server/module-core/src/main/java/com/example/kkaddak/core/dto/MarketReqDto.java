package com.example.kkaddak.core.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
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
