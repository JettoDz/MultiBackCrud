package com.jettodz.crud.services;

import java.util.List;

import com.jettodz.crud.dtos.BookRecord;
import com.jettodz.crud.dtos.requests.BookRequest;

public interface BookService {

    public List<BookRecord> getBooks();

    public BookRecord getBookById(Integer id);

    public List<BookRecord> createBook(BookRequest request);
    
    public List<BookRecord> updateBook(BookRequest request);

    public List<BookRecord> deleteBook(BookRequest request);

}
