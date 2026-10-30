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
import com.jettodz.crud.dtos.AuthorRecord;
import com.jettodz.crud.dtos.BookRecord;
import com.jettodz.crud.dtos.LanguageRecord;
import com.jettodz.crud.dtos.PublisherRecord;

public final class BookRecordJson {

    private BookRecordJson() {
    }

    public static class Serializer extends StdSerializer<BookRecord> {
        public Serializer() {
            super(BookRecord.class);
        }

        @Override
        public void serialize(BookRecord value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeObjectField("id", value.id());
            gen.writeObjectField("title", value.title());
            gen.writeObjectField("isbn", value.isbn());
            provider.defaultSerializeField("language", value.language(), gen);
            gen.writeObjectField("numberOfPages", value.numberOfPages());
            gen.writeObjectField("publicationDate", value.publicationDate() != null ? value.publicationDate().toString() : null);
            provider.defaultSerializeField("authors", value.authors(), gen);
            provider.defaultSerializeField("publisher", value.publisher(), gen);
            gen.writeEndObject();
        }
    }

    public static class Deserializer extends StdDeserializer<BookRecord> {
        public Deserializer() {
            super(BookRecord.class);
        }

        @Override
        public BookRecord deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);

            LanguageRecord language = node.hasNonNull("language")
                ? ctxt.readTreeAsValue(node.get("language"), LanguageRecord.class) : null;
            PublisherRecord publisher = node.hasNonNull("publisher")
                ? ctxt.readTreeAsValue(node.get("publisher"), PublisherRecord.class) : null;

            List<AuthorRecord> authors = null;
            if (node.hasNonNull("authors")) {
                authors = new ArrayList<>();
                for (JsonNode author : node.get("authors")) {
                    authors.add(ctxt.readTreeAsValue(author, AuthorRecord.class));
                }
            }

            return new BookRecord(
                JsonNodes.integer(node, "id"),
                JsonNodes.text(node, "title"),
                JsonNodes.text(node, "isbn"),
                language,
                JsonNodes.integer(node, "numberOfPages"),
                JsonNodes.date(node, "publicationDate"),
                authors,
                publisher);
        }
    }

}
