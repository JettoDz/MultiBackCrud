package com.jettodz.crud.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jettodz.crud.dtos.LanguageRecord;

public final class LanguageRecordJson {

    private LanguageRecordJson() {
    }

    public static class Serializer extends StdSerializer<LanguageRecord> {
        public Serializer() {
            super(LanguageRecord.class);
        }

        @Override
        public void serialize(LanguageRecord value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeObjectField("id", value.id());
            gen.writeObjectField("code", value.code());
            gen.writeObjectField("name", value.name());
            gen.writeEndObject();
        }
    }

    public static class Deserializer extends StdDeserializer<LanguageRecord> {
        public Deserializer() {
            super(LanguageRecord.class);
        }

        @Override
        public LanguageRecord deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            return new LanguageRecord(JsonNodes.integer(node, "id"), JsonNodes.text(node, "code"), JsonNodes.text(node, "name"));
        }
    }

}
