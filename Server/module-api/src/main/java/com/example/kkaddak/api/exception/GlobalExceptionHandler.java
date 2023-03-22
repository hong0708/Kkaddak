package com.example.kkaddak.api.exception;

import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.ExceptionResDto;
import com.example.kkaddak.core.exception.IllegalMemberTypeException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.crypto.Data;
import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public DataResDto<?> handle(BadRequestException e){
        return DataResDto.builder().statusCode(400).statusMessage(e.getMessage()).build();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public DataResDto<?> handle(IllegalArgumentException e){
        return DataResDto.builder().statusCode(400).statusMessage(e.getMessage()).build();
    }
    @ExceptionHandler(IllegalMemberTypeException.class)
    public DataResDto<?> handle(IllegalMemberTypeException e){
        return DataResDto.builder().statusCode(400).statusMessage(e.getMessage()).build();
    }
    @ExceptionHandler(NotFoundException.class)
    public DataResDto<?> handle(NotFoundException e){
        return DataResDto.builder().statusCode(404).statusMessage(e.getMessage()).build();
    }
    @ExceptionHandler(NoContentException.class)
    public DataResDto<?> handle(NoContentException e){
        return DataResDto.builder().statusCode(204).statusMessage(e.getMessage()).build();
    }
    @ExceptionHandler(PagingQueryException.class)
    public DataResDto<?> handle(PagingQueryException e){
        return DataResDto.builder().statusCode(400).statusMessage(e.getMessage()).build();
    }
    @ExceptionHandler(IOException.class)
    public DataResDto<?> handle(IOException e){
        return DataResDto.builder().statusCode(500).statusMessage(e.getMessage()).build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public DataResDto<?> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        return DataResDto.builder().statusCode(400).statusMessage(exception.getMessage()).build();
    }
}
