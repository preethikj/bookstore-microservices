# BookStore Microservices

A Spring Boot Microservices-based Book Store application demonstrating Service Discovery, API Gateway routing, inter-service communication using OpenFeign, custom validations, and global exception handling.

## Architecture

```text
                +----------------+
                |    Client      |
                +-------+--------+
                        |
                        v
                +----------------+
                |  API Gateway   |
                +-------+--------+
                        |
        +---------------+---------------+
        |                               |
        v                               v
+---------------+             +----------------+
| Product       |             | Order          |
| Service       |             | Service        |
+-------+-------+             +--------+-------+
        |                              |
        +--------------+---------------+
                       |
                       v
              +------------------+
              | Eureka Server    |
              | Service Discovery|
              +------------------+
```

---

## Services

### Eureka Service Discovery

The Eureka Server acts as the Service Registry.

Responsibilities:

* Registers all microservices
* Maintains service instances
* Enables dynamic service discovery
* Eliminates hardcoded service URLs

Registered Services:

* API Gateway
* Product Service
* Order Service

Example:

Instead of calling:

```text
http://localhost:8081/books/1
```

Order Service can use:

```java
@FeignClient(name = "guvi-product-service")
```

Eureka automatically resolves the service location.

---

### API Gateway

Acts as the single entry point for clients.

Responsibilities:

* Request routing
* Service discovery integration
* Centralized access point
* Future support for JWT Authentication

Example Routes:

```text
/api/books/**
/api/orders/**
```

---

### Product Service

Manages book inventory.

Features:

* Create Book
* Update Book
* Delete Book
* Get Book By Id
* Get All Books
* Reduce Stock

Endpoints:

```http
GET    /books
GET    /books/{id}
POST   /books
PUT    /books/{id}
DELETE /books/{id}
PUT    /books/{id}/stock
```

---

### Order Service

Manages customer orders.

Features:

* Create Order
* Get Order By Id
* Get All Orders
* Stock Validation
* Product Validation using Feign Client

Endpoints:

```http
GET    /orders
GET    /orders/{id}
POST   /orders
```

---

## Inter-Service Communication

The Order Service communicates with Product Service using OpenFeign.

Example:

```java
@FeignClient(name = "guvi-product-service")
public interface BookClient {

    @GetMapping("/books/{id}")
    BookResponseDto getBookById(@PathVariable Integer id);

    @PutMapping("/books/{id}/stock")
    BookResponseDto reduceStock(
            @PathVariable Integer id,
            @RequestParam Integer quantity);
}
```

Workflow:

1. User creates an order.
2. Order Service validates the book.
3. Order Service checks stock availability.
4. Product Service reduces stock.
5. Order is saved successfully.

---

## Validation

### Bean Validation

```java
@NotNull
@Min(1)
private Integer quantity;
```

### Custom Validation

```java
@BookExists
private Long bookId;
```

Ensures the referenced book exists before an order is created.

---

## Exception Handling

Global exception handling is implemented using:

```java
@RestControllerAdvice
```

Handled Exceptions:

* ResourceNotFoundException
* DuplicateResourceException
* InsufficientStockException
* MethodArgumentNotValidException
* Generic Exception

Standard Error Response:

```json
{
  "timeStamp": "2026-06-10T20:00:00",
  "error": "ResourceNotFoundException",
  "message": "Book not found",
  "statusCode": 404
}
```

---

## Technology Stack

* Java 21
* Spring Boot
* Spring Cloud
* Spring Data JPA
* Spring Validation
* Spring Cloud OpenFeign
* Netflix Eureka
* API Gateway
* MySQL
* Maven

---

## Learning Objectives

This project demonstrates:

* Microservices Architecture
* Service Discovery using Eureka
* API Gateway Routing
* OpenFeign Communication
* Global Exception Handling
* REST API Development
* Spring Boot Best Practices
* Git Monorepo Management

```
```
