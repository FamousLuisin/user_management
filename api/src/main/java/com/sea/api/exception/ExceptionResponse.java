package com.sea.api.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {
    
    private String code;
    private String message;
    private Integer status;
    private String uri;
    private LocalDateTime timestamp;
    
    public ExceptionResponse(String code, String message, Integer status, String uri, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.uri = uri;
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
