package com.example.guvi.product.service.service;

import com.example.guvi.product.service.dto.BooksResponseDto;
import com.example.guvi.product.service.exception.DuplicateResourceException;
import com.example.guvi.product.service.exception.ResourceNotFoundException;
import com.example.guvi.product.service.model.Books;
import com.example.guvi.product.service.repository.BookRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class BookService {
    
    private final BookRepository bookRepository;

    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }


    public Books getBookById(Integer id) {

        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book with id " + id + " not found"));
    }

    public Books createBook(BooksResponseDto booksResponseDto) {

        if (bookRepository.existsById(booksResponseDto.getId())) {
            throw new DuplicateResourceException(
                    "Book already exists with id: " + booksResponseDto.getId());
        }

        Books book = Books.builder()
                .id(booksResponseDto.getId())
                .title(booksResponseDto.getTitle())
                .author(booksResponseDto.getAuthor())
                .price(booksResponseDto.getPrice())
                .stock(booksResponseDto.getStock()).
                build();
        return bookRepository.save(book);
    }

    public Books updateBookById(Integer id, BooksResponseDto booksResponseDto) {
        Books book = bookRepository.findById(id).orElse(null);

        if (book == null) {
            return null;
        }

        book.setTitle(booksResponseDto.getTitle());
        book.setAuthor(booksResponseDto.getAuthor());
        book.setPrice(booksResponseDto.getPrice());
        book.setStock(booksResponseDto.getStock());

        return bookRepository.save(book);
    }

    public boolean deleteBookById(Integer id) {
        Books book = bookRepository.findById(id).orElse(null);

        if (book == null) {
            return false;
        }

        bookRepository.delete(book);
        return true;
    }

    public Books reduceStock(Integer id, Integer quantity) {

        Books book = bookRepository.findById(id).orElseThrow();
        book.setStock(book.getStock() - quantity);

        System.out.println("Reducing stock for book " + id + " by " + quantity);
        System.out.println("Current stock for book " + id + " is" + book.getStock());

        return bookRepository.save(book);
    }
}
