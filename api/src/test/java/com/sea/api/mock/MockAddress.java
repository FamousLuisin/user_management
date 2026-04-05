package com.sea.api.mock;

import com.sea.api.dto.request.AddressRequestDTO;
import com.sea.api.dto.request.CepRequestDTO;
import com.sea.api.dto.response.AddressResponseDTO;
import com.sea.api.model.Address;

public class MockAddress {
    
    private Long id = 1L;

    private String logradouro = "Praça da Sé";

    private String bairro = "Sé";

    private String cidade = "São Paulo";

    private String uf = "SP";

    private String cep = "01001-000";

    private String complemento = "";

    public MockAddress withLogradouro(String logradouro){
        this.logradouro = logradouro;
        return this;
    }

    public MockAddress withBairro(String bairro){
        this.bairro = bairro;
        return this;
    }

    public MockAddress withCidade(String cidade){
        this.cidade = cidade;
        return this;
    }

    public MockAddress withUf(String uf){
        this.uf = uf;
        return this;
    }

    public MockAddress withComplemento(String complemento){
        this.complemento = complemento;
        return this;
    }

    public MockAddress withCep(String cep){
        this.cep = cep;
        return this;
    }

    public AddressRequestDTO builAddressRequestDTO(){
        return new AddressRequestDTO(logradouro, bairro, cidade, uf, complemento);
    }

    public AddressResponseDTO buildAddressResponseDTO(){
        return new AddressResponseDTO(id, bairro, logradouro, bairro, cidade, uf, complemento);
    }

    public CepRequestDTO builCepRequestDTO(){
        return new CepRequestDTO(cep);
    }

    public Address buildAddress(){
        return new Address(id, cep, logradouro, bairro, cidade, uf, complemento);
    }
}
