package com.jettodz.crud.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jettodz.crud.dtos.PublisherRecord;

public final class PublisherRecordJson {

    private PublisherRecordJson() {
    }

    public static class Serializer extends StdSerializer<PublisherRecord> {
        public Serializer() {
            super(PublisherRecord.class);
        }

        @Override
        public void serialize(PublisherRecord value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeObjectField("id", value.id());
            gen.writeObjectField("name", value.name());
            gen.writeEndObject();
        }
    }

    public static class Deserializer extends StdDeserializer<PublisherRecord> {
        public Deserializer() {
            super(PublisherRecord.class);
        }

        @Override
        public PublisherRecord deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            return new PublisherRecord(JsonNodes.integer(node, "id"), JsonNodes.text(node, "name"));
        }
    }

}
