package com.sea.api.controller.docs;

import org.springframework.http.ResponseEntity;

import com.sea.api.dto.request.LoginUserDTO;
import com.sea.api.dto.request.RegisterUserDTO;
import com.sea.api.dto.response.JwtResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth", description = "Endpoints relacionados à autenticação e registro de usuários.")
public interface AuthControllerDocs {
    
    @Operation(summary = "Autenticar usuário", description = "Endpoint para autenticar um usuário e obter um token JWT.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
        }
    )
    public ResponseEntity<JwtResponseDTO> login(LoginUserDTO request);

    @Operation(summary = "Registrar usuário", description = "Endpoint para registrar um novo usuário.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
        }
    )
    public ResponseEntity<JwtResponseDTO> register(RegisterUserDTO request);
}
