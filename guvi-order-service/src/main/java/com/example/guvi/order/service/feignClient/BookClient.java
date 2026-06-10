package com.example.guvi.order.service.feignClient;

import com.example.guvi.order.service.dto.response.BookResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "guvi-product-service")
public interface BookClient {

    @GetMapping("/books/{id}")
    BookResponseDto getBookById(@PathVariable Integer id);

    @PutMapping("/books/{id}/stock")
    BookResponseDto reduceStock(
            @PathVariable Integer id,
            @RequestParam Integer quantity);
}
