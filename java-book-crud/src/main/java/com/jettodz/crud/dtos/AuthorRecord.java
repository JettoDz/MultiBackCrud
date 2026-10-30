package com.jettodz.crud.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jettodz.crud.entities.Author;
import com.jettodz.crud.serialization.AuthorRecordJson;

@JsonSerialize(using = AuthorRecordJson.Serializer.class)
@JsonDeserialize(using = AuthorRecordJson.Deserializer.class)
public record AuthorRecord(Integer id, String name) {

    public AuthorRecord(Author source) {
        this(source.getId(), source.getName());
    }

}
