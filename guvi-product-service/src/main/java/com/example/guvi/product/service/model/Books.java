package com.example.guvi.product.service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "Books")

public class Books {
    @Id
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "price")
    private Double price;
    @Column(name = "stock")
    private Integer stock;
}
