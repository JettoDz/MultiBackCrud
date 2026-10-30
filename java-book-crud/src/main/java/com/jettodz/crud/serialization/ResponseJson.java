package com.jettodz.crud.serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jettodz.crud.util.Response;

public final class ResponseJson {

    private ResponseJson() {
    }

    @SuppressWarnings("rawtypes")
    public static class Serializer extends StdSerializer<Response> {
        public Serializer() {
            super(Response.class);
        }

        @Override
        public void serialize(Response value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            provider.defaultSerializeField("data", value.getData(), gen);
            gen.writeObjectField("message", value.getMessage());
            gen.writeNumberField("timestamp", value.getTimestamp());
            gen.writeEndObject();
        }
    }

    @SuppressWarnings("rawtypes")
    public static class Deserializer extends StdDeserializer<Response> implements ContextualDeserializer {
        private final JavaType elementType;

        public Deserializer() {
            this(null);
        }

        private Deserializer(JavaType elementType) {
            super(Response.class);
            this.elementType = elementType;
        }

        @Override
        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
            JavaType contextual = ctxt.getContextualType();
            if (contextual == null && property != null) {
                contextual = property.getType();
            }
            return new Deserializer(TypeResolver.typeArgument(contextual, 0));
        }

        @Override
        public Response deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);

            List<Object> data = null;
            if (node.hasNonNull("data")) {
                JavaType type = elementType != null ? elementType : ctxt.constructType(Object.class);
                data = new ArrayList<>();
                for (JsonNode item : node.get("data")) {
                    data.add(item.isNull() ? null : ctxt.readTreeAsValue(item, type));
                }
            }

            long timestamp = node.hasNonNull("timestamp") ? node.get("timestamp").asLong() : System.currentTimeMillis();
            return new Response<>(data, JsonNodes.text(node, "message"), timestamp);
        }
    }

}
