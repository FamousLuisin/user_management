package com.sea.api.dto.request;

import com.sea.api.validation.phone.Phone;

@Phone
public class PhoneRequestDTO {
    
    private String number;

    private String type;

    public PhoneRequestDTO() {
    }

    public PhoneRequestDTO(String number, String type) {
        this.number = number;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
