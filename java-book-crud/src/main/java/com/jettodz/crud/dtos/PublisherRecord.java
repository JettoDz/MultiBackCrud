package com.jettodz.crud.dtos;

import com.jettodz.crud.entities.Publisher;

public record PublisherRecord(Integer id, String name) {

    public PublisherRecord(Publisher source) {
        this(source.getId(), source.getName());
    }
}
