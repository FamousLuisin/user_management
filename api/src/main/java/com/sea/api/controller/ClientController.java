package com.sea.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sea.api.dto.request.ClientRequestDTO;
import com.sea.api.dto.request.ClientUpdateDTO;
import com.sea.api.dto.response.ClientResponseDTO;
import com.sea.api.service.ClientService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;
    
    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody @Valid ClientRequestDTO entity) {
        ClientResponseDTO response = clientService.createClient(entity);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> findAllClients(
        @RequestParam(name = "page", defaultValue = "0") Integer page,
        @RequestParam(name = "size", defaultValue = "10") Integer size,
        @RequestParam(name = "direction", defaultValue = "ASC") String direction
    ){

        Direction sort = direction.equals("DESC") ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, "name"));
        
        List<ClientResponseDTO> response = clientService.findAllClients(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ClientResponseDTO> findClientById(@PathVariable(name = "id") Long id){
        ClientResponseDTO response = clientService.findClientById(id);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ClientResponseDTO> updateClientById(
        @PathVariable(name = "id") Long id,
        @RequestBody @Valid ClientUpdateDTO request
    ){
        ClientResponseDTO response = clientService.updateClientById(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ClientResponseDTO> deleteClientById(@PathVariable(name = "id") Long id){
        clientService.deleteClientById(id);

        return ResponseEntity.noContent().build();
    }
}
