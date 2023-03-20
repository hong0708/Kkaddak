package com.example.kkaddak.api.dto.auction;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionAllReqDto {
    private Long lastId;
    private Long limit;

    @Builder
    public AuctionAllReqDto(Long lastId, Long limit) {
        this.lastId = lastId;
        this.limit = limit;
    }
}
