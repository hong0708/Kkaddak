package com.example.kkaddak.api.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DataResDto<T> extends BaseResDto {
    private T data;

    @Builder
    public DataResDto(int statusCode, String statusMessage, T data) {
        super(statusCode, statusMessage);
        this.data = data;
    }
}
