package com.sea.api.controller.docs;

import org.springframework.http.ResponseEntity;

import com.sea.api.dto.request.AddressRequestDTO;
import com.sea.api.dto.request.CepRequestDTO;
import com.sea.api.dto.response.AddressResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Tag(name = "Address", description = "Endpoints relacionados a endereços")
public interface AddressControllerDocs {
    
    @Operation(summary = "Busca um endereço por ID", description = "Busca um endereço específico pelo seu ID", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso",  
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressResponseDTO.class))), 
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"), 
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado, verifique o ID fornecido"), 
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor") 
        }    
    )
    public ResponseEntity<AddressResponseDTO> findAddressById(Long id);

    @Operation(summary = "Busca um endereço por ID do cliente", description = "Busca um endereço específico pelo ID do cliente", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso",  
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressResponseDTO.class))), 
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"), 
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado, verifique o ID do cliente fornecido"), 
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor") 
        }    
    )
    public ResponseEntity<AddressResponseDTO> findAddressByClientId(Long clientId);
    
    @Operation(summary = "Atualiza um endereço por ID", description = "Atualiza os dados de um endereço específico pelo seu ID, utilizando os dados fornecidos no corpo da requisição", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso",  
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressResponseDTO.class))), 
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"), 
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado, verifique o ID fornecido"), 
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor") 
        }    
    )
    public ResponseEntity<AddressResponseDTO> updateAddress(Long id, AddressRequestDTO request);

    @Operation(summary = "Atualiza o CEP de um endereço por ID", description = "Atualiza o CEP de um endereço específico pelo seu ID, utilizando os dados fornecidos no corpo da requisição", 
        responses = {
            @ApiResponse(responseCode = "200", description = "CEP atualizado com sucesso",  
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressResponseDTO.class))), 
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"), 
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado, verifique o ID fornecido"), 
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor") 
        }    
    )
    public ResponseEntity<AddressResponseDTO> updateCep(Long id, CepRequestDTO request);
}
