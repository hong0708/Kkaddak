package com.example.kkaddak.api.dto.pay;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ExchangeReqDto {
    private String receiptId;

    @Builder
    public ExchangeReqDto(String receiptId) {
        this.receiptId = receiptId;
    }
}
