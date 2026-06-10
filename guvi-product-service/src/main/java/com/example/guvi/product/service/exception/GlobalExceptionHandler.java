package com.example.guvi.product.service.exception;

import com.example.guvi.product.service.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleDuplicateResourceException(
            DuplicateResourceException ex) {

        return ErrorResponseDto.builder()
                .error(ex.getClass().getSimpleName())
                .expMessage(ex.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleResourceNotFoundException(
            ResourceNotFoundException ex) {

        return ErrorResponseDto.builder()
                .error(ex.getClass().getSimpleName())
                .expMessage(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleNullPointerException(
            NullPointerException ex) {

        return ErrorResponseDto.builder()
                .error(ex.getClass().getSimpleName())
                .expMessage(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleException(Exception ex) {

        return ErrorResponseDto.builder()
                .error(ex.getClass().getSimpleName())
                .expMessage(ex.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
