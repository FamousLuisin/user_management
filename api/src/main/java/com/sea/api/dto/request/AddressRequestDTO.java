package com.sea.api.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressRequestDTO {
    
    @NotBlank(message = "logradouro não pode estar em branco")
    private String logradouro;

    @NotBlank(message = "bairro não pode estar em branco")
    private String bairro;

    @NotBlank(message = "Cidade não pode estar em branco")
    @JsonProperty(value = "cidade")
    @JsonAlias("localidade")
    private String cidade;

    @Size(min = 2, max = 2, message = "UF deve ter apenas 2 letras")
    private String uf;

    private String complemento;

    public AddressRequestDTO() {
    }

    public AddressRequestDTO(String logradouro, String bairro, String cidade, String uf, String complemento) {
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.complemento = complemento;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
