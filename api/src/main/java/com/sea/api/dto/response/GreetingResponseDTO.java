package com.sea.api.dto.response;

public class GreetingResponseDTO {
    
    private String version;

    private String message;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
