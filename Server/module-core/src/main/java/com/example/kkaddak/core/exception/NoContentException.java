package com.example.kkaddak.core.exception;

import lombok.Getter;

@Getter
public class NoContentException extends Exception{
    public NoContentException(String message){
        super(message);
    }

}
