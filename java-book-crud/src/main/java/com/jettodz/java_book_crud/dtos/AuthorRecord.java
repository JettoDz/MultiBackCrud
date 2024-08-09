package com.jettodz.java_book_crud.dtos;

import com.jettodz.java_book_crud.entities.Author;

public record AuthorRecord(Integer id, String name) {

    public AuthorRecord(Author source) {
        this(source.getId(), source.getName());
    }

}
