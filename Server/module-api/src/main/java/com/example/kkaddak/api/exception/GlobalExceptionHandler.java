package com.example.kkaddak.api.exception;

import com.example.kkaddak.api.dto.ExceptionResDto;
import com.example.kkaddak.core.exception.IllegalMemberTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResDto> handle(BadRequestException e){
        ExceptionResDto response = new ExceptionResDto(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ExceptionHandler(IllegalMemberTypeException.class)
    public ResponseEntity<ExceptionResDto> handle(IllegalMemberTypeException e){
        ExceptionResDto response = new ExceptionResDto(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
