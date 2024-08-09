package com.jettodz.java_book_crud.services.impl;

import com.jettodz.java_book_crud.dtos.BookRecord;
import com.jettodz.java_book_crud.entities.Book;
import com.jettodz.java_book_crud.repositories.BookRepository;
import com.jettodz.java_book_crud.services.BookService;
import com.jettodz.java_book_crud.util.exceptions.BookToBookRecordConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final BookToBookRecordConverter converter;

    @Override
    public String getBooks() {
        return null;
    }

    @Override
    public BookRecord getBookById(Integer id) {
        return converter.convert(repository.findBookById(id));
    }

    @Override
    public void createBook() {
//        return null;
    }

    @Override
    public void updateBook() {
//        return null;
    }

    @Override
    public void deleteBook() {
//        return null;
    }
}
