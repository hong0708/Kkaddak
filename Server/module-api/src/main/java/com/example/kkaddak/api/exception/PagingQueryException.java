package com.example.kkaddak.api.exception;

import lombok.Getter;

@Getter
public class PagingQueryException extends Exception{
    public PagingQueryException(String message){
        super(message);
    }

}
