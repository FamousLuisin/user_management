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
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
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

        ResponseCookie cookie = ResponseCookie.from("token", jwt.getRefreshToken())
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(60 * 6000)
            .sameSite("Strict")
            .build();
        
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(jwt);
    }
    
    @PostMapping("/register")
    public ResponseEntity<JwtResponseDTO> register(@RequestBody @Valid RegisterUserDTO request) {
        JwtResponseDTO jwt = authService.register(request);
        
        return ResponseEntity.ok().body(jwt);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDTO> refresh(@CookieValue("token") String token) {
        JwtResponseDTO jwt = authService.refreshToken(token);
        
        return ResponseEntity.ok(jwt);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<JwtResponseDTO> logout(@CookieValue("token") String token){
        authService.logout(token);

        ResponseCookie cookie = ResponseCookie.from("token", "")
            .httpOnly(true)
            .secure(true) 
            .path("/")    
            .maxAge(0)   
            .sameSite("Strict")
            .build();

        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }
}
