package com.sea.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sea.api.controller.docs.GreetingControllerDocs;
import com.sea.api.dto.response.GreetingResponseDTO;

@RestController
@RequestMapping(path = "/api/greeting")
public class GreetingController implements GreetingControllerDocs {
    
    @GetMapping
    public ResponseEntity<GreetingResponseDTO> Greeting(){
        GreetingResponseDTO greeting = new GreetingResponseDTO();
        greeting.setMessage("Bem vindo ao User Management");
        greeting.setVersion("1.0.0");

        return ResponseEntity.ok(greeting);
    }
}
