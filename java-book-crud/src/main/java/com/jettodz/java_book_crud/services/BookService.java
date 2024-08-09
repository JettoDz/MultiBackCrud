package com.jettodz.java_book_crud.services;

import com.jettodz.java_book_crud.dtos.BookRecord;
import com.jettodz.java_book_crud.entities.Book;

public interface BookService {

    public String getBooks();

    public BookRecord getBookById(Integer id);

    public void createBook();

    public void updateBook();

    public void deleteBook();

}
