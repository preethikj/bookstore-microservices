package com.example.guvi.order.service.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class ErrorResponseDto {
    private LocalDateTime timeStamp;
    private String error;
    private String message;
    private Integer statusCode;
}
