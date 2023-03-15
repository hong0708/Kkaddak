package com.example.kkaddak.api.dto;


import lombok.*;

@ToString
@Getter
public class BaseResDto {

    private int statusCode;
    private String statusMessage;

    public BaseResDto(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
