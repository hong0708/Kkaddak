package com.example.kkaddak.api.dto.market;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NFTThumbnailReqDto {
    private String nftImageUrl;

    @Builder
    public NFTThumbnailReqDto(String nftImageUrl) {
        this.nftImageUrl = nftImageUrl;
    }
}
