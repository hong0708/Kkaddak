package com.example.kkaddak.api.exception;

import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.core.exception.IllegalMemberTypeException;
import com.example.kkaddak.core.exception.NoContentException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public DataResDto<?> handle(BadRequestException e){
        return DataResDto.builder().statusCode(400).statusMessage(e.getMessage()).build();
    }
    @ExceptionHandler(UnauthorizationException.class)
    public DataResDto<?> handle(UnauthorizationException e){
        return DataResDto.builder().statusCode(401).statusMessage(e.getMessage()).build();
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
    @ExceptionHandler(ServiceException.class)
    public DataResDto<?> handle(ServiceException e){
        return DataResDto.builder().statusCode(500).statusMessage(e.getMessage()).build();
    }
    @ExceptionHandler(IOException.class)
    public DataResDto<?> handle(IOException e){
        return DataResDto.builder().statusCode(500).statusMessage(e.getMessage()).build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public DataResDto<?> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        return DataResDto.builder().statusCode(400).statusMessage(exception.getMessage()).build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public DataResDto<?> handleMethodArgumentTypeNotMismatch(MethodArgumentTypeMismatchException exception) {
        return DataResDto.builder().statusCode(400).statusMessage(exception.getMessage()).build();
    }

    @ExceptionHandler(UnsupportedAudioFileException.class)
    public DataResDto<?> handleUnSupportedAudioFileException(UnsupportedAudioFileException exception) {
        return DataResDto.builder().statusCode(400).statusMessage(exception.getMessage()).build();
    }
}
