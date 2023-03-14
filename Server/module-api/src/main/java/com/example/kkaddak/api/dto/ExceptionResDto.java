package com.example.kkaddak.api.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ExceptionResDto {

    private String message;

    @Builder
    public ExceptionResDto(String message) {
        this.message = message;
    }
}
