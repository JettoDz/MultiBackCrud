package com.jettodz.crud.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jettodz.crud.dtos.AuthorRecord;
import com.jettodz.crud.dtos.BookRecord;
import com.jettodz.crud.dtos.LanguageRecord;
import com.jettodz.crud.dtos.PublisherRecord;
import com.jettodz.crud.dtos.requests.BookRequest;
import com.jettodz.crud.util.CacheObject;
import com.jettodz.crud.util.Fingerprinter;
import com.jettodz.crud.util.Response;
import com.jettodz.crud.util.Status;

class CacheObjectJsonTest {

    private final ObjectMapper mapper = new ObjectMapper();

    private CacheObject<BookRecord> sample() throws Exception {
        BookRequest request = new BookRequest();
        request.setTitle("Dune");
        request.setIsbn("123");
        request.setLanguageId(1);
        request.setNumberOfPages(500);
        request.setPublicationDate("1965-08-01");
        request.setAuthorIds(List.of(1, 2));
        request.setPublisherId(3);

        BookRecord book = new BookRecord(9, "Dune", "123", new LanguageRecord(1, "en", "English"), 500,
            LocalDate.of(1965, 8, 1), List.of(new AuthorRecord(1, "A"), new AuthorRecord(2, "B")), new PublisherRecord(3, "P"));

        CacheObject<BookRecord> cache = new CacheObject<>(Fingerprinter.forString(mapper.writeValueAsString(request)));
        cache.setResponse(new Response<>(List.of(book), "ok"));
        cache.setStatus(Status.SUCCESS);
        return cache;
    }

    @Test
    void roundTripWithTypedReference() throws Exception {
        CacheObject<BookRecord> original = sample();
        String json = mapper.writeValueAsString(original);
        CacheObject<BookRecord> restored = mapper.readValue(json, new TypeReference<>() { });

        assertEquals(json, mapper.writeValueAsString(restored));
        assertEquals(original.getResponse().getData(), restored.getResponse().getData());
        assertEquals(Status.SUCCESS, restored.getStatus());
        assertEquals(original.getRequestHash(), restored.getRequestHash());
    }

    @Test
    void roundTripWithRawClass() throws Exception {
        String json = mapper.writeValueAsString(sample());
        CacheObject<?> restored = mapper.readValue(json, CacheObject.class);

        assertInstanceOf(String.class, restored.getRequestHash());
        assertInstanceOf(BookRecord.class, restored.getResponse().getData().get(0));
        assertEquals(json, mapper.writeValueAsString(restored));
    }

    @Test
    void inProgressWithoutResponse() throws Exception {
        CacheObject<BookRecord> original = new CacheObject<>(Fingerprinter.forString(mapper.writeValueAsString(new BookRequest())));
        CacheObject<BookRecord> restored = mapper.readValue(mapper.writeValueAsString(original), new TypeReference<>() { });

        assertNull(restored.getResponse());
        assertEquals(Status.IN_PROGRESS, restored.getStatus());
    }
}
