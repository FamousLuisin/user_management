package com.sea.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtResponseDTO {
    
    private String token;
    
    @JsonIgnore
    private String refreshToken;

    public JwtResponseDTO(){}

    public JwtResponseDTO(String token) {
        this.token = token;
    }

    public JwtResponseDTO(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
