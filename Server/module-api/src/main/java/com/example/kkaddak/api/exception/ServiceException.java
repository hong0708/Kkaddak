package com.example.kkaddak.api.exception;

import lombok.Getter;

@Getter
public class ServiceException extends Exception{
    public ServiceException(String message){
        super(message);
    }
}
