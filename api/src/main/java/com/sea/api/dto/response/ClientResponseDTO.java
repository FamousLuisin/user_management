package com.sea.api.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sea.api.serializer.CpfSerializer;

public class ClientResponseDTO {
    
    private Long id;

    private String name;

    @JsonSerialize(using = CpfSerializer.class)
    private String cpf;

    private AddressResponseDTO address;

    public AddressResponseDTO getAddress() {
        return address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setAddress(AddressResponseDTO address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

}
