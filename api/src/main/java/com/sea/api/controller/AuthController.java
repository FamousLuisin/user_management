package com.sea.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sea.api.controller.docs.AuthControllerDocs;
import com.sea.api.dto.request.LoginUserDTO;
import com.sea.api.dto.request.RegisterUserDTO;
import com.sea.api.dto.response.JwtResponseDTO;
import com.sea.api.service.AuthService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {

    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody @Valid LoginUserDTO request) {
        JwtResponseDTO jwt = authService.login(request);
        
        return ResponseEntity.ok(jwt);
    }
    
    @PostMapping("/register")
    public ResponseEntity<JwtResponseDTO> register(@RequestBody @Valid RegisterUserDTO request) {
        JwtResponseDTO jwt = authService.register(request);
        
        return ResponseEntity.ok(jwt);
    }
}
