package com.example.guvi.user.service.exception;

import com.example.guvi.user.service.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
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

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDto handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ErrorResponseDto.builder()
                .error(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
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

    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleUserExistException(
            UserExistException ex) {

        return ErrorResponseDto.builder()
                .error(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDenied(
            AccessDeniedException ex) {

        ErrorResponseDto error = ErrorResponseDto.builder()
                .timeStamp(LocalDateTime.now())
                .error("AccessDeniedException")
                .message("Access Denied")
                .statusCode(HttpStatus.FORBIDDEN.value())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(error);
    }


    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto handleAuthorizationDeniedException(
            AuthorizationDeniedException ex) {

        return ErrorResponseDto.builder()
                .error(ex.getClass().getSimpleName())
                .message("Access Denied")
                .statusCode(HttpStatus.FORBIDDEN.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
