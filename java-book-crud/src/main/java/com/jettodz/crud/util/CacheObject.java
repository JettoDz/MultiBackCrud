package com.jettodz.crud.util;

import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jettodz.crud.serialization.CacheObjectJson;

@JsonSerialize(using = CacheObjectJson.Serializer.class)
@JsonDeserialize(using = CacheObjectJson.Deserializer.class)
public class CacheObject<T> {

    private String requestHash;
    private Response<T> response;
    private Status status;

    public CacheObject(String requestHash) {
        this.requestHash = requestHash;
        this.status = Status.IN_PROGRESS;
    }

    public CacheObject(String requestHash, Response<T> response, Status status) {
        this.requestHash = requestHash;
        this.response = response;
        this.status = status;
    }


    public String getRequestHash() {
        return requestHash;
    }

    public Response<T> getResponse() {
        return response;
    }

    public void setResponse(Response<T> response) {
        this.response = response;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override 
    public int hashCode() {
        return Objects.hash(requestHash, status);
    }

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CacheObject<?> other = (CacheObject<?>) obj;
        return Objects.equals(requestHash, other.requestHash) && status == other.status;
    }

    @Override 
    public String toString() {
        return String.format(
            "{\"requestHash\": \"%s\", \"response\": \"%s\", \"status\": \"%s\"}",
            requestHash != null ? requestHash : "null",
            response != null ? response.toString() : "null",
            status != null ? status.toString() : "null"
        );
    }

}
