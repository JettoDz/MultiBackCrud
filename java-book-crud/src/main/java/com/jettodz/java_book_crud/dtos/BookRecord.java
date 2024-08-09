package com.jettodz.java_book_crud.dtos;

import java.time.LocalDate;
import java.util.List;

public record BookRecord(Integer id, String title, String isbn, LanguageRecord language, Integer numberOfPages, LocalDate publicationDate, List<AuthorRecord> authors, PublisherRecord publisher) {
}
