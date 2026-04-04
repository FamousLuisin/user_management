package com.sea.api.controller.docs;

import org.springframework.http.ResponseEntity;

import com.sea.api.dto.response.GreetingResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Greeting", description = "Endpoints relacionados a saudações")
public interface GreetingControllerDocs {
    
    @Operation(summary = "Endpoint de saudação", description = "Retorna uma mensagem de boas-vindas e a versão da API", 
        responses = { 
            @ApiResponse(responseCode = "200", description = "Saudação retornada com sucesso",  
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = GreetingResponseDTO.class))), 
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor") 
        }    
    )
    public ResponseEntity<GreetingResponseDTO> Greeting();
}
