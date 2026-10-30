package com.jettodz.crud.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jettodz.crud.entities.Publisher;
import com.jettodz.crud.serialization.PublisherRecordJson;

@JsonSerialize(using = PublisherRecordJson.Serializer.class)
@JsonDeserialize(using = PublisherRecordJson.Deserializer.class)
public record PublisherRecord(Integer id, String name) {

    public PublisherRecord(Publisher source) {
        this(source.getId(), source.getName());
    }
}
