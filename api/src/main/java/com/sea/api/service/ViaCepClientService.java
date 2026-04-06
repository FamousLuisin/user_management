package com.sea.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sea.api.dto.request.AddressRequestDTO;
import com.sea.api.exception.CepNotFoundException;

@Service
public class ViaCepClientService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    public AddressRequestDTO viaCepClient(String cep){
        String uri = "http://viacep.com.br/ws/" + cep + "/json";
        
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                if (jsonNode.has("erro") && jsonNode.get("erro").asBoolean()) {
                    throw new CepNotFoundException("CEP not found: " + cep);
                }

                return objectMapper.treeToValue(jsonNode, AddressRequestDTO.class);
            } else {
                throw new CepNotFoundException("Error fetching CEP data: " + response.getStatusCode());
            }
        } catch (JsonMappingException e) {
            throw new RuntimeException("Error mapping JSON response: " + e.getMessage(), e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON response: " + e.getMessage(), e);
        }   
    }
}
