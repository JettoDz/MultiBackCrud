package com.jettodz.crud.services.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jettodz.crud.daos.repositories.BookRepository;
import com.jettodz.crud.dtos.BookRecord;
import com.jettodz.crud.dtos.requests.BookRequest;
import com.jettodz.crud.entities.Book;
import com.jettodz.crud.services.AuthorService;
import com.jettodz.crud.services.BookService;
import com.jettodz.crud.services.LanguageService;
import com.jettodz.crud.services.PublisherService;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final LanguageService languageService;
    private final AuthorService authorService;
    private final PublisherService publisherService;

    public BookServiceImpl(BookRepository repository, LanguageService languageService, AuthorService authorService, PublisherService publisherService) {
        this.repository = repository;
        this.languageService = languageService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    @Override
    public List<BookRecord> getBooks() {
        return null;
    }

    @Override
    public BookRecord getBookById(Integer id) {
        return new BookRecord(repository.findBookById(id));
    }

    @Override
    public List<BookRecord> createBook(BookRequest request) {
        var language = languageService.getLanguageById(request.getLanguageId());
        var authors = authorService.getAuthorsByIds(request.getAuthorIds());
        var publisher = publisherService.getPublisherById(request.getPublisherId());
        var book = new Book(request, language, authors, publisher);
        book = repository.save(book);
        return List.of(new BookRecord(book));
    }

    @Override
    public List<BookRecord> updateBook(BookRequest request) {
        return Collections.emptyList();
    }

    @Override
    public List<BookRecord> deleteBook(BookRequest request) {
        return Collections.emptyList();
    }
}
