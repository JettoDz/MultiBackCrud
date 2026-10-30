package com.jettodz.crud.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jettodz.crud.entities.Language;
import com.jettodz.crud.serialization.LanguageRecordJson;

@JsonSerialize(using = LanguageRecordJson.Serializer.class)
@JsonDeserialize(using = LanguageRecordJson.Deserializer.class)
public record LanguageRecord(Integer id, String code, String name) {

    public LanguageRecord(Language source) {
        this(source.getId(), source.getCode(), source.getName());
    }

}
