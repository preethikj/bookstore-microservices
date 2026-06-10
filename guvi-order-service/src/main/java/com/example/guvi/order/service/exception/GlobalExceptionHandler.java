package com.example.guvi.order.service.exception;

import com.example.guvi.order.service.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleNullPointerException(NullPointerException ex) {

        return ErrorResponseDto.builder()
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .build();

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleException(Exception ex) {

        return ErrorResponseDto.builder()
                .error(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleDuplicateResourceException(
            DuplicateResourceException ex) {

        return ErrorResponseDto.builder()
                .error(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleInsufficientStockException(
            InsufficientStockException ex) {

        return ErrorResponseDto.builder()
                .error(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleOrderNotFoundException(OrderNotFoundException ex) {
        return ErrorResponseDto.builder()
                .error(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleValidationException(
            MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return ErrorResponseDto.builder()
                .error("Validation Failed")
                .message(errorMessage)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }

}
