package com.jettodz.crud.serialization;

import java.io.IOException;

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
import com.jettodz.crud.util.CacheObject;
import com.jettodz.crud.util.Response;
import com.jettodz.crud.util.Status;

public final class CacheObjectJson {

    private CacheObjectJson() {
    }

    @SuppressWarnings("rawtypes")
    public static class Serializer extends StdSerializer<CacheObject> {
        public Serializer() {
            super(CacheObject.class);
        }

        @Override
        public void serialize(CacheObject value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            // Lets data be restored when the target types are not known at read time
            String dataType = null;
            if (value.getResponse() != null && value.getResponse().getData() != null) {
                for (Object item : value.getResponse().getData()) {
                    if (item != null) {
                        dataType = TypeResolver.nameOf(item);
                        break;
                    }
                }
            }
            gen.writeObjectField("dataType", dataType);
            gen.writeObjectField("requestHash", value.getRequestHash());
            provider.defaultSerializeField("response", value.getResponse(), gen);
            gen.writeObjectField("status", value.getStatus() != null ? value.getStatus().name() : null);
            gen.writeEndObject();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static class Deserializer extends StdDeserializer<CacheObject> implements ContextualDeserializer {
        private final JavaType dataType;

        public Deserializer() {
            this(null);
        }

        private Deserializer(JavaType dataType) {
            super(CacheObject.class);
            this.dataType = dataType;
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
        public CacheObject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);

            String requestHash = node.hasNonNull("requestHash") ? node.get("requestHash").asText() : null;

            Response response = null;
            if (node.hasNonNull("response")) {
                JavaType data = dataType != null ? dataType : TypeResolver.fromName(ctxt, JsonNodes.text(node, "dataType"));
                JavaType responseType = ctxt.getTypeFactory().constructParametricType(Response.class, data);
                response = ctxt.readTreeAsValue(node.get("response"), responseType);
            }

            Status status = node.hasNonNull("status") ? Status.valueOf(node.get("status").asText()) : null;
            return new CacheObject(requestHash, response, status);
        }
    }

}
