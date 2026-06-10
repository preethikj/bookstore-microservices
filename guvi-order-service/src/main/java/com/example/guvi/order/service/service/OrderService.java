package com.example.guvi.order.service.service;

import com.example.guvi.order.service.dto.response.BookResponseDto;
import com.example.guvi.order.service.dto.response.OrderResponseDto;
import com.example.guvi.order.service.exception.InsufficientStockException;
import com.example.guvi.order.service.exception.OrderNotFoundException;
import com.example.guvi.order.service.feignClient.BookClient;
import com.example.guvi.order.service.model.Order;
import com.example.guvi.order.service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookClient bookClient;

    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::mapToDto)
                .toList();
    }

    public OrderResponseDto getOrderById(String id) {

        Order order = orderRepository.findById(id).orElseThrow(() ->
                        new OrderNotFoundException(id));
        return mapToDto(order);
    }

    /*public OrderResponseDto createOrder(OrderResponseDto orderResponseDto) {

        Order order = mapToOrderEntity(orderResponseDto);
        Order savedOrder = orderRepository.save(order);
        return mapToDto(savedOrder);
    }*/

    public OrderResponseDto createOrder(OrderResponseDto orderResponseDto) {

        //In-order to create order, we have to first check whether the book is in stock.
        //1. So, we take the bookId from order response dto.
        //2. Then we have to get the book response dto from the Product service(BookClient) - Feign Client.
        //3. Then check whether the mentioned book is in stock from the bookResponse dto.
        //4. If there is adequate stock reduce the book count in bookClient and place order.
        //5. If there is no adequate stock then throw InsuffientStockException.
        BookResponseDto book = bookClient.getBookById(orderResponseDto.getBookId().intValue());

        if (book.getStock() < orderResponseDto.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock");
        }

        bookClient.reduceStock(book.getId(), orderResponseDto.getQuantity());

        Order order = Order.builder()
                .userId(orderResponseDto.getUserId())
                .bookId(orderResponseDto.getBookId())
                .quantity(orderResponseDto.getQuantity())
                .totalPrice(book.getPrice() * orderResponseDto.getQuantity())
                .build();

        Order savedOrder = orderRepository.save(order);
        return mapToDto(savedOrder);
    }

    private Order mapToOrderEntity(OrderResponseDto orderResponseDto) {

        return Order.builder()
                .id(orderResponseDto.getId())
                .userId(orderResponseDto.getUserId())
                .bookId(orderResponseDto.getBookId())
                .quantity(orderResponseDto.getQuantity())
                .totalPrice(orderResponseDto.getTotalPrice())
                .build();

    }

    private OrderResponseDto mapToDto(Order order) {

        OrderResponseDto dto = new OrderResponseDto();

        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setBookId(order.getBookId());
        dto.setQuantity(order.getQuantity());
        dto.setTotalPrice(order.getTotalPrice());
        return dto;
    }
}
