package com.jettodz.java_book_crud.util.exceptions;
import com.jettodz.java_book_crud.dtos.AuthorRecord;
import com.jettodz.java_book_crud.dtos.LanguageRecord;
import com.jettodz.java_book_crud.dtos.PublisherRecord;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.jettodz.java_book_crud.dtos.BookRecord;
import com.jettodz.java_book_crud.entities.Book;

import java.util.stream.Stream;

@Component
public class BookToBookRecordConverter implements Converter<Book, BookRecord> {

    @Override
    public BookRecord convert(Book book) {
        return new BookRecord(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                new LanguageRecord(book.getLanguage()),
                book.getNumberOfPages(),
                book.getPublicationDate(),
                book.getAuthors().stream().map(AuthorRecord::new).toList(),
                new PublisherRecord(book.getPublisher())
        );
    }
}