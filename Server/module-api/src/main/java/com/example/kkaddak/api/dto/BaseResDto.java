package com.example.kkaddak.api.dto;


import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseResDto {

    private int statusCode;
    private String statusMessage;

    public BaseResDto(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
