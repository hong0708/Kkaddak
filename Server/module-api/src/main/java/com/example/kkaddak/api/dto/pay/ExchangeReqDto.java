package com.example.kkaddak.api.dto.pay;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeReqDto {
    private String receiptId;

    @Builder
    public ExchangeReqDto(String receiptId) {
        this.receiptId = receiptId;
    }
}
