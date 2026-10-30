package com.jettodz.crud.serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jettodz.crud.dtos.requests.BookRequest;

public final class BookRequestJson {

    private BookRequestJson() {
    }

    public static class Serializer extends StdSerializer<BookRequest> {
        public Serializer() {
            super(BookRequest.class);
        }

        @Override
        public void serialize(BookRequest value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeObjectField("title", value.getTitle());
            gen.writeObjectField("isbn", value.getIsbn());
            gen.writeObjectField("languageId", value.getLanguageId());
            gen.writeObjectField("numberOfPages", value.getNumberOfPages());
            gen.writeObjectField("publicationDate", value.getPublicationDate() != null ? value.getPublicationDate().toString() : null);
            provider.defaultSerializeField("authorIds", value.getAuthorIds(), gen);
            gen.writeObjectField("publisherId", value.getPublisherId());
            gen.writeEndObject();
        }
    }

    public static class Deserializer extends StdDeserializer<BookRequest> {
        public Deserializer() {
            super(BookRequest.class);
        }

        @Override
        public BookRequest deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            BookRequest request = new BookRequest();
            request.setTitle(JsonNodes.text(node, "title"));
            request.setIsbn(JsonNodes.text(node, "isbn"));
            request.setLanguageId(JsonNodes.integer(node, "languageId"));
            request.setNumberOfPages(JsonNodes.integer(node, "numberOfPages"));
            if (node.hasNonNull("publicationDate")) {
                request.setPublicationDate(node.get("publicationDate").asText());
            }
            if (node.hasNonNull("authorIds")) {
                List<Integer> authorIds = new ArrayList<>();
                for (JsonNode id : node.get("authorIds")) {
                    authorIds.add(id.asInt());
                }
                request.setAuthorIds(authorIds);
            }
            request.setPublisherId(JsonNodes.integer(node, "publisherId"));
            return request;
        }
    }

}
