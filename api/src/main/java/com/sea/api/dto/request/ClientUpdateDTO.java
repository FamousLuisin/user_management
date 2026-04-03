package com.sea.api.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sea.api.validation.cpf.Cpf;

public class ClientUpdateDTO {

    @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters long")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Cpf(message = "CPF invalid")
    private String cpf;

    public ClientUpdateDTO() {
    }

    public ClientUpdateDTO(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
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

}
