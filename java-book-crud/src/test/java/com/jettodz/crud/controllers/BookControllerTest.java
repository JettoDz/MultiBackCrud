package com.jettodz.crud.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jettodz.crud.dtos.BookRecord;
import com.jettodz.crud.dtos.requests.BookRequest;
import com.jettodz.crud.services.BookService;
import com.jettodz.crud.util.Response;
import com.jettodz.crud.util.exceptions.ErrorMessages;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    private BookController bookController;

    @BeforeEach
    void setUp() {
        bookController = new BookController(bookService);
    }

    @Test
    void getBookByIdReturnsBookFromService() {
        BookRecord book = bookRecord(17, "Dune");
        when(bookService.getBookById(17)).thenReturn(book);

        ResponseEntity<BookRecord> response = bookController.getBookById(17);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(book, response.getBody());
        verify(bookService).getBookById(17);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    void createBookReturnsCreatedBooksAndSuccessMessage() {
        BookRequest request = bookRequest("Dune");
        UUID idempotencyKey = UUID.randomUUID();
        List<BookRecord> createdBooks = List.of(bookRecord(18, "Dune"));
        when(bookService.createBook(request)).thenReturn(createdBooks);

        ResponseEntity<Response<BookRecord>> response = bookController.createBook(request, idempotencyKey);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertSame(createdBooks, response.getBody().getData());
        assertEquals("Book created successfully", response.getBody().getMessage());
        verify(bookService).createBook(request);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    void createBookReplaysSuccessfulResponseForCachedRequest() {
        BookRequest request = bookRequest("Dune");
        UUID idempotencyKey = UUID.randomUUID();
        when(bookService.createBook(request)).thenReturn(List.of(bookRecord(19, "Dune")));

        ResponseEntity<Response<BookRecord>> initialResponse = bookController.createBook(request, idempotencyKey);
        ResponseEntity<Response<BookRecord>> replayResponse = bookController.createBook(request, idempotencyKey);

        assertEquals(HttpStatus.OK, replayResponse.getStatusCode());
        assertNotNull(initialResponse.getBody());
        assertNotNull(replayResponse.getBody());
        assertEquals(initialResponse.getBody().getData(), replayResponse.getBody().getData());
        assertEquals(initialResponse.getBody().getMessage(), replayResponse.getBody().getMessage());
        verify(bookService).createBook(request);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    void createBookRejectsDifferentRequestForPreviouslyUsedKey() {
        BookRequest initialRequest = bookRequest("Dune");
        BookRequest differentRequest = bookRequest("Foundation");
        UUID idempotencyKey = UUID.randomUUID();
        when(bookService.createBook(initialRequest)).thenReturn(List.of(bookRecord(20, "Dune")));

        bookController.createBook(initialRequest, idempotencyKey);
        ResponseEntity<Response<BookRecord>> response = bookController.createBook(differentRequest, idempotencyKey);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorMessages.IDEMPOTENCY_KEY_CORRUPTION, response.getBody().getMessage());
        assertEquals(List.of(), response.getBody().getData());
        verify(bookService).createBook(initialRequest);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    void createBookReturnsInternalServerErrorWhenServiceReturnsNoBooks() {
        BookRequest request = bookRequest("Dune");
        when(bookService.createBook(request)).thenReturn(List.of());

        ResponseEntity<Response<BookRecord>> response = bookController.createBook(request, UUID.randomUUID());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Failed to create book", response.getBody().getMessage());
        assertEquals(List.of(), response.getBody().getData());
        verify(bookService).createBook(request);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    void createBookReturnsInternalServerErrorWhenServiceThrows() {
        BookRequest request = bookRequest("Dune");
        when(bookService.createBook(request)).thenThrow(new IllegalStateException("service unavailable"));

        ResponseEntity<Response<BookRecord>> response = bookController.createBook(request, UUID.randomUUID());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Failed to create book", response.getBody().getMessage());
        assertEquals(List.of(), response.getBody().getData());
        verify(bookService).createBook(request);
        verifyNoMoreInteractions(bookService);
    }

    private static BookRequest bookRequest(String title) {
        BookRequest request = new BookRequest();
        request.setTitle(title);
        request.setIsbn("978-0-00-000000-0");
        request.setLanguageId(1);
        request.setNumberOfPages(300);
        request.setPublicationDate("2020-01-01");
        request.setAuthorIds(List.of(1));
        request.setPublisherId(1);
        return request;
    }

    private static BookRecord bookRecord(Integer id, String title) {
        return new BookRecord(id, title, "978-0-00-000000-0", null, 300, LocalDate.of(2020, 1, 1), List.of(), null);
    }
}
