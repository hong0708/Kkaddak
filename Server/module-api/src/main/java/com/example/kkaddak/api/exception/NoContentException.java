package com.example.kkaddak.api.exception;

import lombok.Getter;

@Getter
public class NoContentException extends Exception{
    public NoContentException(String message){
        super(message);
    }

}
