package com.example.kkaddak.api.dto;


import lombok.*;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseResDto {

    private int statusCode;
    private String statusMessage;

    public BaseResDto(int statusCode, String statusMessage) {
        this.statusCode = statusCode == 0 ? 200 : statusCode;
        this.statusMessage = statusMessage;
    }
}
