package com.example.guvi.product.service.controller;

import com.example.guvi.product.service.dto.BooksResponseDto;
import com.example.guvi.product.service.model.Books;
import com.example.guvi.product.service.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping("")
    public ResponseEntity<List<Books>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Books> getBookById(@PathVariable Integer id) {
        return  ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping("")
    public ResponseEntity<Books> createBook(@Valid @RequestBody BooksResponseDto booksResponseDto) {

       return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(booksResponseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Books> updateBook(@Valid @PathVariable Integer id, @RequestBody BooksResponseDto booksResponseDto) {
        Books updatedBook = bookService.updateBookById(id, booksResponseDto);

        if (updatedBook == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Integer id) {
        if (bookService.deleteBookById(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    //This method will be called from Order service using Feign client
    @PutMapping("/{id}/stock")
    public ResponseEntity<Books> reduceStock(@PathVariable Integer id, @RequestParam Integer quantity) {

        return ResponseEntity.ok(bookService.reduceStock(id, quantity));

    }

}
