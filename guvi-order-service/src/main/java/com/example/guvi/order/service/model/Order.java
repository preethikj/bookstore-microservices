package com.example.guvi.order.service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Order {
    @Id
    private String id;
    private Long userId;
    private Long bookId;
    private Integer quantity;
    private Double totalPrice;
}
