package com.jettodz.crud.dtos;

import com.jettodz.crud.entities.Author;

public record AuthorRecord(Integer id, String name) {

    public AuthorRecord(Author source) {
        this(source.getId(), source.getName());
    }

}
