package com.example.guvi.user.service.exception;

import com.example.guvi.user.service.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. REUSABLE BUILDER METHOD
    private ErrorResponseDto buildErrorResponse(Exception ex, HttpStatus status, String customMessage) {
        String message = (customMessage != null) ? customMessage : ex.getMessage();
        return ErrorResponseDto.builder()
                .error(ex.getClass().getSimpleName())
                .message(message)
                .statusCode(status.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    // 2. GROUPING BY STATUS CODES OR SPECIFIC LOGIC
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponseDto> handleNullPointerException(NullPointerException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildErrorResponse(exception, HttpStatus.BAD_REQUEST, null));
    }

    @ExceptionHandler({DuplicateResourceException.class, UserExistException.class}) // Grouped together.
    public ResponseEntity<ErrorResponseDto> handleConflictExceptions(Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildErrorResponse(exception, HttpStatus.CONFLICT, null));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCredentialsException(InvalidCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(buildErrorResponse(exception, HttpStatus.UNAUTHORIZED, null));
    }

    @ExceptionHandler({AccessDeniedException.class, AuthorizationDeniedException.class}) // Security Grouped
    public ResponseEntity<ErrorResponseDto> handleAccessDenied(Exception exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(buildErrorResponse(exception, HttpStatus.FORBIDDEN, "Access Denied"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException exception) {
        //String validationMessage = exception.getBindingResult().getFieldError().getDefaultMessage();
        String validationMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        // We override the error string here for better clarity
        ErrorResponseDto error = ErrorResponseDto.builder()
                .error("Validation Failed")
                .message(validationMessages)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, null));
    }
}