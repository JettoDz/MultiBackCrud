package com.jettodz.crud.util;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.jettodz.crud.dtos.BookRecord;
import com.jettodz.crud.entities.Book;

@Component
public class BookToBookRecordConverter implements Converter<Book, BookRecord> {

    @Override
    public BookRecord convert(Book book) {
        return new BookRecord(book);
    }
}