package com.sea.api.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sea.api.serializer.PhoneSerializer;

public class PhoneResponseDTO {

    private Long id;

    @JsonSerialize(using = PhoneSerializer.class)
    private String number;

    private String type;

    public PhoneResponseDTO() {
    }

    public PhoneResponseDTO(Long id, String number, String type) {
        this.id = id;
        this.number = number;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
