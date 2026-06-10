package com.example.guvi.order.service.dto.response;

import com.example.guvi.order.service.customAnnotation.BookExists;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
    private String id;

    @NotNull(message = "User id is mandatory")
    private Long userId;
    @NotNull(message = "Book id is mandatory")
    @BookExists(message = "Book does not exist. Verify the book id")
    private Long bookId;
    @NotNull(message = "Quantity cant be empty")
    @Min(value = 1, message = "Quantity cant be less than 1.")
    private Integer quantity;
    private Double totalPrice;
}
