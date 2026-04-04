package com.sea.api.controller.docs;

import org.springframework.http.ResponseEntity;

import com.sea.api.dto.request.PhoneRequestDTO;
import com.sea.api.dto.response.PhoneResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

@Tag(name = "Phone", description = "Endpoints relacionados a telefones dos clientes")
public interface PhoneControllerDocs {
    
    @Operation(summary = "Adiciona um telefone a um cliente", description = "Adiciona um novo telefone a um cliente específico pelo seu ID, utilizando os dados fornecidos no corpo da requisição",
        responses = { 
            @ApiResponse(responseCode = "200", description = "Telefone adicionado com sucesso",  
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PhoneResponseDTO.class))), 
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"), 
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado, verifique o ID fornecido"), 
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor") 
        }    
    )
    public ResponseEntity<PhoneResponseDTO> addPhoneToClient(Long id, PhoneRequestDTO phone);
    
    @Operation(summary = "Atualiza um telefone por ID", description = "Atualiza os dados de um telefone específico pelo seu ID, utilizando os dados fornecidos no corpo da requisição",
        responses = { 
            @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso",  
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PhoneResponseDTO.class))), 
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"), 
            @ApiResponse(responseCode = "404", description = "Telefone não encontrado, verifique o ID fornecido"), 
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor") 
        }    
    )
    public ResponseEntity<PhoneResponseDTO> updatePhone(Long id, PhoneRequestDTO phone);
    
    @Operation(summary = "Deleta um telefone por ID", description = "Deleta um telefone específico pelo seu ID", 
        responses = { 
            @ApiResponse(responseCode = "204", description = "Telefone deletado com sucesso"), 
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"), 
            @ApiResponse(responseCode = "404", description = "Telefone não encontrado, verifique o ID fornecido"), 
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor") 
        }    
    )
    public ResponseEntity<PhoneResponseDTO> deletePhone(Long id);
}
