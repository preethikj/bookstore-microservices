package com.example.guvi.product.service.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BooksResponseDto {

    @NotNull(message = "Book id is mandatory")
    private Integer id;

    @NotNull(message = "Title is mandatory")
    private String title;

    @NotNull(message = "Author is mandatory")
    private String author;

    @NotNull(message = "Price is mandatory")
    @Min(value = 1, message = "Price cant be less than 0")
    private Double price;

    @NotNull(message = "Stock can not be 0")
    @Min(value = 1, message = "Atleast 1 quantity is mandatory")
    public Integer stock;
}
