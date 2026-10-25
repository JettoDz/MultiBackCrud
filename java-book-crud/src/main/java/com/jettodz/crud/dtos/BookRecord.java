package com.jettodz.crud.dtos;
import java.time.LocalDate;
import java.util.List;

import com.jettodz.crud.entities.Book;

public record BookRecord(Integer id, String title, String isbn, LanguageRecord language, Integer numberOfPages, LocalDate publicationDate, List<AuthorRecord> authors, PublisherRecord publisher) {

    public BookRecord (Book source) {
        this(source.getId(), source.getTitle(), source.getIsbn(), new LanguageRecord(source.getLanguage()), source.getNumberOfPages(), source.getPublicationDate(), source.getAuthors().stream().map(AuthorRecord::new).toList(), new PublisherRecord(source.getPublisher()));
    }

}
