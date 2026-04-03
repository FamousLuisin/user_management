package com.sea.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sea.api.dto.request.PhoneRequestDTO;
import com.sea.api.dto.response.PhoneResponseDTO;
import com.sea.api.service.PhoneService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping(path = "/api/phone")
public class PhoneController {
    
    @Autowired
    private PhoneService phoneService;

    @PostMapping("/client/{id}")
    public ResponseEntity<PhoneResponseDTO> addPhoneToClient(@PathVariable("id") Long id, @RequestBody @Valid PhoneRequestDTO phone) {
        PhoneResponseDTO response = phoneService.addPhone(id, phone);
        
        return ResponseEntity.ok(response);
    }
    

    @PutMapping(path = "/{id}")
    public ResponseEntity<PhoneResponseDTO> updatePhone(@PathVariable("id") Long id, @RequestBody @Valid PhoneRequestDTO phone) {
        PhoneResponseDTO response = phoneService.updatePhone(id, phone);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<PhoneResponseDTO> deletePhone(@PathVariable("id") Long id) {
        phoneService.deletePhone(id);
        
        return ResponseEntity.noContent().build();
    }
}
