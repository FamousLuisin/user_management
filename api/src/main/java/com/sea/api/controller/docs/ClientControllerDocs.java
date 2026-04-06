package com.sea.api.controller.docs;

import org.springframework.http.ResponseEntity;

import com.sea.api.dto.request.ClientRequestDTO;
import com.sea.api.dto.request.ClientUpdateDTO;
import com.sea.api.dto.response.ClientResponseDTO;
import com.sea.api.dto.response.PageResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Clientes", description = "Endpoints relacionados a operações de clientes")
public interface ClientControllerDocs {
    
    @Operation(summary = "Cria um novo cliente", description = "Cria um novo cliente com os dados fornecidos no corpo da requisição",
        responses = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }   
    )
    public ResponseEntity<ClientResponseDTO> createClient(ClientRequestDTO entity);
    
    @Operation(summary = "Busca todos os clientes", description = "Busca todos os clientes cadastrados, com suporte a paginação e ordenação",
        responses = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso", 
                content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClientResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os parâmetros de paginação e ordenação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }   
    )
    public ResponseEntity<PageResponseDTO<ClientResponseDTO>> findAllClients(Integer page, Integer size, String direction);

    @Operation(summary = "Busca um cliente por ID", description = "Busca um cliente específico pelo seu ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado, verifique o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }   
    )
    public ResponseEntity<ClientResponseDTO> findClientById(Long id);

    @Operation(summary = "Busca um cliente por CPF", description = "Busca um cliente específico pelo seu CPF",
        responses = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado, verifique o CPF fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }   
    )
    public ResponseEntity<ClientResponseDTO> findClientByCpf(String cpf);

    @Operation(summary = "Busca clientes por UF", description = "Busca todos os clientes de uma determinada unidade federativa",
        responses = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso", 
                content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClientResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }   
    )
    public ResponseEntity<PageResponseDTO<ClientResponseDTO>> findClientsByUf(
        String uf, Integer page, Integer size
    );

    @Operation(summary = "Atualiza um cliente por ID", description = "Atualiza os dados de um cliente específico pelo seu ID, utilizando os dados fornecidos no corpo da requisição",
        responses = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado, verifique o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }   
    )
    public ResponseEntity<ClientResponseDTO> updateClientById(Long id, ClientUpdateDTO request);

    @Operation(summary = "Deleta um cliente por ID", description = "Deleta um cliente específico pelo seu ID",
        responses = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados enviados"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado, verifique o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }   
    )
    public ResponseEntity<ClientResponseDTO> deleteClientById(Long id);
}
