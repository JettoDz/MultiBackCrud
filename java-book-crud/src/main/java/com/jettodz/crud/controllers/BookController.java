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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jettodz.crud.daos.CacheDao;
import com.jettodz.crud.dtos.BookRecord;
import com.jettodz.crud.dtos.requests.BookRequest;
import com.jettodz.crud.services.BookService;
import com.jettodz.crud.util.CacheObject;
import com.jettodz.crud.util.Fingerprinter;
import com.jettodz.crud.util.Response;
import com.jettodz.crud.util.Status;
import com.jettodz.crud.util.exceptions.ErrorMessages;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final CacheDao cacheDao;
    private final ObjectMapper mapper;

    public BookController(BookService bookService, CacheDao cacheDao) {
        this.bookService = bookService;
        this.cacheDao = cacheDao;
        this.mapper = new ObjectMapper();
    }

    @GetMapping("/")
    public ResponseEntity<String> getBooks() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookRecord> getBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response<BookRecord>> createBook(@RequestBody BookRequest bookRequest, @RequestHeader("Idempotency-Key") UUID correlationUuid) 
            throws JsonProcessingException {
        ResponseEntity<Response<BookRecord>> responseObject = null;
        var requestHash = Fingerprinter.forString(mapper.writeValueAsString(bookRequest));
        // REDIS implementation
        // Try to win the key in the idempotency cache. 
        CacheObject<BookRecord> content = new CacheObject<>(requestHash);
        var firstHit = cacheDao.setIdempotent(correlationUuid.toString(), mapper.writeValueAsString(content));
        if (!firstHit) {
            try {
                // NOTE (found by the AI assistant during review): getIdempotent can return null if the key expires
                // or is deleted (e.g. after a failed attempt) between the SET NX above and this GET. readValue(null)
                // then throws, and the catch below turns it into a generic 500. It "successfully fails": the client
                // gets an error instead of a corrupt replay, but the cause is misleading.
                // Proper handling: check for null explicitly and either retry setIdempotent once or return 409,
                // so the client retries with the same Idempotency-Key.
                content = mapper.readValue(cacheDao.getIdempotent(correlationUuid.toString()), new TypeReference<CacheObject<BookRecord>>(){});
                if (content.getRequestHash().equals(requestHash)) {
                    responseObject = content.getStatus() == Status.IN_PROGRESS ?
                        // If the request is still in progress, return 409 Conflict.
                        ResponseEntity.status(HttpStatus.CONFLICT).body(new Response<>(Collections.emptyList(), ErrorMessages.IDEMPOTENCY_KEY_CONFLICT)) :
                        // Otherwise, return the cached response.
                        ResponseEntity.status(HttpStatus.OK).body(content.getResponse());
                } else {
                    // If the request hash doesn't match the cached request, return 422.
                    responseObject = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new Response<>(Collections.emptyList(), ErrorMessages.IDEMPOTENCY_KEY_CORRUPTION));
                }
            } catch (Exception e) {
                // add logging
                e.printStackTrace();
                responseObject = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new Response<>(Collections.emptyList(), "Book request recieved; status retrieval failed.\n Correlation ID: " + correlationUuid.toString()));
            }
        } else {
            try {
                var data = bookService.createBook(bookRequest);
                if (data != null && !data.isEmpty()) {
                    content.setStatus(Status.SUCCESS);
                    content.setResponse(new Response<BookRecord>(data, "Book created successfully"));
                    responseObject = ResponseEntity.status(HttpStatus.OK).body(content.getResponse());
                } else {
                    content.setStatus(Status.FAILURE);
                    content.setResponse(new Response<BookRecord>(Collections.emptyList(), "Failed to create book"));
                    responseObject = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(content.getResponse());
                }
                if (content.getStatus() == Status.FAILURE) {
                    // Failures are not cached, so the client can retry with the same key.
                    cacheDao.deleteIdempotent(correlationUuid.toString());
                } else {
                    cacheDao.updateIdempotent(correlationUuid.toString(), mapper.writeValueAsString(content));
                }
            } catch (Exception e) {
                e.printStackTrace();
                cacheDao.deleteIdempotent(correlationUuid.toString());
                responseObject = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new Response<>(Collections.emptyList(), "Book request recieved; processing failed.\n Correlation ID: " + correlationUuid.toString()));
            }
        }
        return responseObject;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Integer id, @RequestBody BookRequest bookRequest) {
        return ResponseEntity.ok("Hello World");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id) { 
        return ResponseEntity.ok("Hello World"); 
    }

}
