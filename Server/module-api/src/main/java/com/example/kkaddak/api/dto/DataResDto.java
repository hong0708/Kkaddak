package com.example.kkaddak.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class DataResDto<T> extends BaseResDto {
    private T data;

    @Builder
    public DataResDto(int statusCode, String statusMessage, T data) {
        super(statusCode, statusMessage);
        this.data = data;
    }
}
