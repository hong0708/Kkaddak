package com.example.kkaddak.core.exception;

import lombok.Getter;

@Getter
public class IllegalMemberTypeException extends RuntimeException{
    public IllegalMemberTypeException(String message) {
        super(message);
    }
}
