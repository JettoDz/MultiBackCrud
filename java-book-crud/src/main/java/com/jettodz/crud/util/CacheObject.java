package com.jettodz.crud.util;

import java.time.Duration;
import java.time.Instant;

public class CacheObject<REQ, T> {

    private REQ request;
    private Response<T> response;
    private long expiration;
    private Status status;

    public CacheObject(REQ request) {
        this.request = request;
        this.expiration = Instant.now().plus(Duration.ofDays(1)).toEpochMilli();
        this.status = Status.IN_PROGRESS;
    }

    public boolean isExpired() {
        return Instant.now().toEpochMilli() > expiration;
    }

    public REQ getRequest() {
        return request;
    }

    public Response<T> getResponse() {
        return response;
    }

    public void setResponse(Response<T> response) {
        this.response = response;
    }

    public long getExpiration() {
        return expiration;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override 
    public int hashCode() {
        return request != null ? request.hashCode() : 0;
    }

}
