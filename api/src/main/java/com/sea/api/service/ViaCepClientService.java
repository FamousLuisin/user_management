package com.sea.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sea.api.dto.request.AddressRequestDTO;

@Service
public class ViaCepClientService {
    
    public AddressRequestDTO viaCepClient(String cep){
        String uri = "http://viacep.com.br/ws/" + cep + "/json";
        RestTemplate restTemplate = new RestTemplate();

        AddressRequestDTO result = restTemplate.getForObject(uri, AddressRequestDTO.class);
        return result;
    }
}
