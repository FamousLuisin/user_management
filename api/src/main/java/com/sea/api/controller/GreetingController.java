package com.sea.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sea.api.dto.GreetingDto;

@RestController
@RequestMapping(path = "/api/greeting")
public class GreetingController {
    
    @GetMapping
    public ResponseEntity<GreetingDto> Greeting(){
        GreetingDto greeting = new GreetingDto();
        greeting.setMessage("Bem vindo ao User Management");
        greeting.setVersion("1.0.0");

        return ResponseEntity.ok(greeting);
    }
}
