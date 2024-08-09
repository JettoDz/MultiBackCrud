package com.jettodz.java_book_crud.controllers;

import com.jettodz.java_book_crud.dtos.BookRecord;
import com.jettodz.java_book_crud.entities.Book;
import com.jettodz.java_book_crud.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<String> getBooks() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookRecord> getBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping("/books")
    public ResponseEntity<String> createBook() {
        return ResponseEntity.ok("Hello World");
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<String> updateBook() {
        return ResponseEntity.ok("Hello World");
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook() { return ResponseEntity.ok("Hello World"); }

}
