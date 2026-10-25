package com.jettodz.crud.controllers;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.jettodz.crud.dtos.BookRecord;
import com.jettodz.crud.dtos.requests.BookRequest;
import com.jettodz.crud.services.BookService;
import com.jettodz.crud.util.CacheObject;
import com.jettodz.crud.util.Response;
import com.jettodz.crud.util.Status;
import com.jettodz.crud.util.exceptions.ErrorMessages;

@RestController
public class BookController {

    private final BookService bookService;

    private static Hashtable<UUID, CacheObject<BookRequest, BookRecord>> idempotencyCache = new Hashtable<>();

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<String> getBooks() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping(value = "/books/{id}")
    public ResponseEntity<BookRecord> getBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping(value = "/books", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response<BookRecord>> createBook(@RequestBody BookRequest bookRequest, @RequestHeader("Idempotency-Key") UUID correlationUuid) {
        ResponseEntity<Response<BookRecord>> responseObject = null;
        var content = idempotencyCache.get(correlationUuid);
        if (content != null) {
            if (content.hashCode() == bookRequest.hashCode()) {
                responseObject = content.getStatus() == Status.IN_PROGRESS ?
                    // If the request hasn't been completed yet, return 409.
                    ResponseEntity.status(HttpStatus.CONFLICT).body(new Response<>(Collections.emptyList(), ErrorMessages.IDEMPOTENCY_KEY_CONFLICT)) :
                    // Otherwise, return the cached response.(Should the expiration be extended? Should we retry if failed?)
                    ResponseEntity.status(HttpStatus.OK).body(content.getResponse());   
            } else {
                // If the UUID exists but the request is different, return 422. Redirect to idempotency doc (Pending)
                responseObject = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new Response<>(Collections.emptyList(), ErrorMessages.IDEMPOTENCY_KEY_CORRUPTION));
            }
            
        } else {
            content = new CacheObject<BookRequest, BookRecord>(bookRequest);
            List<BookRecord> data = null;
            try {
                data = bookService.createBook(bookRequest);
                if (data != null && !data.isEmpty()) {
                    content.setStatus(Status.SUCCESS);
                    content.setResponse(new Response<BookRecord>(data, "Book created successfully"));
                    idempotencyCache.put(correlationUuid, content);
                    responseObject = ResponseEntity.status(HttpStatus.OK).body(new Response<>(data, "Book created successfully"));
                } else {
                    content.setStatus(Status.FAILURE);
                    content.setResponse(new Response<BookRecord>(Collections.emptyList(), "Failed to create book"));
                    responseObject = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(Collections.emptyList(), "Failed to create book"));
                }
            } catch (Exception e) {
                // add logger
                content.setStatus(Status.FAILURE);
                content.setResponse(new Response<BookRecord>(Collections.emptyList(), "Failed to create book"));
                responseObject = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(Collections.emptyList(), "Failed to create book"));
            }
        }
        return responseObject;
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Integer id, @RequestBody BookRequest bookRequest) {
        return ResponseEntity.ok("Hello World");
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id) { 
        return ResponseEntity.ok("Hello World"); 
    }

}
