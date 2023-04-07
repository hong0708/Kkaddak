package com.example.kkaddak.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class JwtErrorMessageDto {
    private String message;
    private HttpStatus status;
}
