package com.example.guvi.order.service.controller;


import com.example.guvi.order.service.dto.response.OrderResponseDto;
import com.example.guvi.order.service.model.Order;
import com.example.guvi.order.service.repository.OrderRepository;
import com.example.guvi.order.service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable String id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping("")
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderResponseDto orderResponseDto) {
        OrderResponseDto createdOrder = orderService.createOrder(orderResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdOrder);
    }



}
