package org.controllibrary.utils.res.api;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ResponseHTTP<T> {
    private T data;
    private String message;
    private String traceId;
    private boolean success;
    private OffsetDateTime timestamp;

    public ResponseHTTP(T data, String message, boolean success, OffsetDateTime timestamp) {
        this.data = data;
        this.message = message;
        this.traceId = UUID.randomUUID().toString();
        this.success = success;
        this.timestamp = timestamp;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}