package com.example.guvi.order.service.dto.response;

import lombok.Data;

@Data
public class BookResponseDto {
    private Integer id;
    private String title;
    private Double price;
    private Integer stock;
}
