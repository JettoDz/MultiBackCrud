package com.jettodz.crud.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jettodz.crud.dtos.requests.BookRequest;

class FingerprinterTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void producesKnownSha256Vector() {
        assertEquals("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", Fingerprinter.forString("abc"));
    }

    @Test
    void isDeterministic() {
        assertEquals(Fingerprinter.forString("same"), Fingerprinter.forString("same"));
        assertNotEquals(Fingerprinter.forString("a"), Fingerprinter.forString("b"));
    }

    @Test
    void equalBookRequestsShareFingerprint() throws Exception {
        assertEquals(hash(request("Dune", List.of(1, 2))), hash(request("Dune", List.of(1, 2))));
    }

    @Test
    void differentBookRequestsHaveDifferentFingerprints() throws Exception {
        assertNotEquals(hash(request("Dune", List.of(1, 2))), hash(request("Foundation", List.of(1, 2))));
        // authorIds order is significant; callers are expected to send them sorted.
        assertNotEquals(hash(request("Dune", List.of(1, 2))), hash(request("Dune", List.of(2, 1))));
    }

    private String hash(BookRequest request) throws Exception {
        return Fingerprinter.forString(mapper.writeValueAsString(request));
    }

    private static BookRequest request(String title, List<Integer> authorIds) {
        BookRequest request = new BookRequest();
        request.setTitle(title);
        request.setIsbn("123");
        request.setLanguageId(1);
        request.setNumberOfPages(300);
        request.setPublicationDate("2020-01-01");
        request.setAuthorIds(authorIds);
        request.setPublisherId(1);
        return request;
    }
}
