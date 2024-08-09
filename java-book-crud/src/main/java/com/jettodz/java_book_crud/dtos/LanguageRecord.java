package com.jettodz.java_book_crud.dtos;

import com.jettodz.java_book_crud.entities.Language;

public record LanguageRecord(Integer id, String code, String name) {

    public LanguageRecord(Language source) {
        this(source.getId(), source.getCode(), source.getName());
    }

}
