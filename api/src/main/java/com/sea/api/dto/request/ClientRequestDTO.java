package com.sea.api.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sea.api.validation.cep.Cep;
import com.sea.api.validation.cpf.Cpf;

public class ClientRequestDTO {

    @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters long")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Cpf(message = "CPF invalid")
    private String cpf;

    @Cep
    private String cep;

    @Valid
    @NotNull
    private List<PhoneRequestDTO> phones;

    @Valid
    @NotNull
    private List<EmailRequestDTO> emails;

    public ClientRequestDTO() {
    }

    public ClientRequestDTO(String name, String cpf, String cep, List<PhoneRequestDTO> phones) {
        this.name = name;
        this.cpf = cpf;
        this.cep = cep;
        this.phones = phones;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public List<PhoneRequestDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneRequestDTO> phones) {
        this.phones = phones;
    }

    public List<EmailRequestDTO> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailRequestDTO> emails) {
        this.emails = emails;
    }
}
