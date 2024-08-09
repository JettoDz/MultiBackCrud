package com.jettodz.java_book_crud.dtos;

import com.jettodz.java_book_crud.entities.Publisher;

public record PublisherRecord(Integer id, String name) {

    public PublisherRecord(Publisher source) {
        this(source.getId(), source.getName());
    }
}
