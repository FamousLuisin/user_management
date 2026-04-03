package com.sea.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sea.api.dto.request.AddressRequestDTO;
import com.sea.api.dto.request.CepRequestDTO;
import com.sea.api.dto.response.AddressResponseDTO;
import com.sea.api.service.AddressService;

@RestController
@RequestMapping(path = "/api/address")
public class AddresController {

    @Autowired
    private AddressService addressService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<AddressResponseDTO> findAddressById(@PathVariable("id") Long id){
        AddressResponseDTO response = addressService.findAddressById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/client/{id}")
    public ResponseEntity<AddressResponseDTO> findAddressByClientId(@PathVariable("id") Long clientId){
        AddressResponseDTO response = addressService.findAddressByClientId(clientId);

        return ResponseEntity.ok(response);
    }
    
    @PutMapping(path = "/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable("id") Long id, @RequestBody @Valid AddressRequestDTO request){
        AddressResponseDTO response = addressService.updateAddress(id, request);

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}/cep")
    public ResponseEntity<AddressResponseDTO> updateCep(@PathVariable("id") Long id, @RequestBody @Valid CepRequestDTO request){
        AddressResponseDTO response = addressService.updateCep(id, request);

        return ResponseEntity.ok(response);
    }
}
