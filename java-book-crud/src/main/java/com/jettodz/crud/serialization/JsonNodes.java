package com.jettodz.crud.serialization;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.JsonNode;

final class JsonNodes {

    private JsonNodes() {
    }

    static Integer integer(JsonNode node, String field) {
        return node.hasNonNull(field) ? node.get(field).asInt() : null;
    }

    static String text(JsonNode node, String field) {
        return node.hasNonNull(field) ? node.get(field).asText() : null;
    }

    static LocalDate date(JsonNode node, String field) {
        return node.hasNonNull(field) ? LocalDate.parse(node.get(field).asText()) : null;
    }

}
