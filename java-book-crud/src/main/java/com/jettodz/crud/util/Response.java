package com.jettodz.crud.util;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jettodz.crud.serialization.ResponseJson;

@JsonSerialize(using = ResponseJson.Serializer.class)
@JsonDeserialize(using = ResponseJson.Deserializer.class)
public class Response<T> {
    private List<T> data;
    private String message;
    private long timestamp;

    public Response(List<T> data, String message) {
        this.data = data;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public Response(List<T> data, String message, long timestamp) {
        this.data = data;
        this.message = message;
        this.timestamp = timestamp;
    }

    public List<T> getData() {
        return data;
    }
    
    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format(
            "{\"data\": \"%s\", \"message\": \"%s\", \"timestamp\": %d}",
            data != null ? data.toString() : "null",
            message != null ? message : "null",
            timestamp
        );
    }

}
