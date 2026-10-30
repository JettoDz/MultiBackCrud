package com.jettodz.crud.serialization;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;

final class TypeResolver {

    private static final String ALLOWED_PREFIX = "com.jettodz.crud.";

    private TypeResolver() {
    }

    /** Returns the n-th type argument of the contextual type, or null when it is missing or unbound (Object). */
    static JavaType typeArgument(JavaType contextual, int index) {
        if (contextual == null || contextual.containedTypeCount() <= index) {
            return null;
        }
        JavaType argument = contextual.containedType(index);
        return argument == null || argument.hasRawClass(Object.class) ? null : argument;
    }

    /** Resolves a type name embedded in the JSON; only project classes are accepted. */
    static JavaType fromName(DeserializationContext ctxt, String name) throws IOException {
        if (name == null) {
            return ctxt.constructType(Object.class);
        }
        if (!name.startsWith(ALLOWED_PREFIX)) {
            throw new IOException("Type not allowed for deserialization: " + name);
        }
        try {
            return ctxt.constructType(Class.forName(name));
        } catch (ClassNotFoundException e) {
            throw new IOException("Unknown type: " + name, e);
        }
    }

    static String nameOf(Object value) {
        return value == null ? null : value.getClass().getName();
    }

}
