package com.example.kkaddak.api.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
