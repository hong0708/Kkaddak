package com.example.kkaddak.api.dto.market;


import lombok.Builder;
import lombok.Getter;

@Getter
public class NFTThumbnailReqDto {
    private String nftImageUrl;

    @Builder
    public NFTThumbnailReqDto(String nftImageUrl) {
        this.nftImageUrl = nftImageUrl;
    }
}
