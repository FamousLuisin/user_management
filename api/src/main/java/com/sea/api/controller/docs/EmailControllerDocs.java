package com.sea.api.controller.docs;

import org.springframework.http.ResponseEntity;

import com.sea.api.dto.request.EmailRequestDTO;
import com.sea.api.dto.response.EmailResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Email", description = "Endpoints relacionados a emails")
public interface EmailControllerDocs {
    
    @Operation(summary = "Adiciona um email a um cliente", description = "Adiciona um novo email a um cliente específico pelo seu ID, utilizando os dados fornecidos no corpo da requisição",
        responses = { 
            @ApiResponse(responseCode = "200", description = "Email adicionado com sucesso",  
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmailResponseDTO.class))), 
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"), 
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado, verifique o ID fornecido"), 
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor") 
        }    
    ) 
    public ResponseEntity<EmailResponseDTO> addEmailToClient(Long id, EmailRequestDTO email);
    

    @Operation(summary = "Atualiza um email por ID", description = "Atualiza os dados de um email específico pelo seu ID, utilizando os dados fornecidos no corpo da requisição",
        responses = { 
            @ApiResponse(responseCode = "200", description = "Email atualizado com sucesso",  
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmailResponseDTO.class))), 
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"), 
            @ApiResponse(responseCode = "404", description = "Email não encontrado, verifique o ID fornecido"), 
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor") 
        }    
    )
    public ResponseEntity<EmailResponseDTO> updateEmail(Long id, EmailRequestDTO email);
    
    @Operation(summary = "Deleta um email por ID", description = "Deleta um email específico pelo seu ID", 
        responses = { 
            @ApiResponse(responseCode = "204", description = "Email deletado com sucesso"), 
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"), 
            @ApiResponse(responseCode = "404", description = "Email não encontrado, verifique o ID fornecido"), 
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor") 
        }    
    )
    public ResponseEntity<EmailResponseDTO> deleteEmail(Long id);
}
