package com.sea.api.dto.request;

import com.sea.api.validation.cep.Cep;

public class CepRequestDTO {
    
    @Cep
    private String cep;

    public CepRequestDTO() {
    }

    public CepRequestDTO(String cep) {
        this.cep = cep;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
