package com.jettodz.crud.util;

import java.util.List;

public class Response<T> {
    private List<T> data;
    private String message;
    private long timestamp;

    public Response(List<T> data, String message) {
        this.data = data;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
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
}
