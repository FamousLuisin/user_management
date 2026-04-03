package com.sea.api.dto.request;

import javax.validation.constraints.Email;

public class EmailRequestDTO {
    
    @Email
    private String email;

    public EmailRequestDTO() {
    }

    public EmailRequestDTO(@Email String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
