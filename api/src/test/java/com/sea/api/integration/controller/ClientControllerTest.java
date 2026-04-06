package com.sea.api.integration.controller;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sea.api.dto.request.ClientRequestDTO;
import com.sea.api.dto.request.EmailRequestDTO;
import com.sea.api.dto.request.LoginUserDTO;
import com.sea.api.dto.request.PhoneRequestDTO;
import com.sea.api.dto.response.ClientResponseDTO;
import com.sea.api.dto.response.JwtResponseDTO;
import com.sea.api.dto.response.PageResponseDTO;
import com.sea.api.integration.AbstractIntegrationTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static ClientRequestDTO clientRequest;
    private static String token;

    @BeforeAll
    static void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        clientRequest = new ClientRequestDTO();
        setSpecification();
        mockClientRequest();
    }

    @Test
    @Order(1)
    void logIn() throws JsonProcessingException, JsonMappingException{
        LoginUserDTO loginRequest = new LoginUserDTO();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("admin");

        String content = given()
            .headers("Origin", "http://localhost:8080")
            .basePath("/auth/login")
            .port(8888)
            .filter(new RequestLoggingFilter(LogDetail.ALL))
            .filter(new ResponseLoggingFilter(LogDetail.ALL))
            .contentType("application/json")
            .body(loginRequest)
            .when()
                .post()
            .then()
                .statusCode(200)
            .extract()
                .body()
                    .asString();
        
        JwtResponseDTO jwtResponse = objectMapper.readValue(content, JwtResponseDTO.class);
        token = jwtResponse.getToken();

        System.out.println(token);

        assertNotNull(jwtResponse);
        assertNotNull(jwtResponse.getToken());
    }

    @Test
    @Order(2)
    void testCreateClient() throws JsonProcessingException, JsonMappingException{

        String content = given(specification)
            .contentType("application/json")
            .header("Authorization", String.format("Bearer %s", token))
            .body(clientRequest)
            .when()
                .post()
            .then()
                .statusCode(201)
            .extract()
                .body()
                    .asString();
        
        ClientResponseDTO response = objectMapper.readValue(content, ClientResponseDTO.class);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getAddress());
        assertNotNull(response.getPhones());
        assertNotNull(response.getEmails());
    }

    @Test
    @Order(3)
    void testFindAllClients() throws JsonProcessingException, JsonMappingException {
        String content = given(specification)
            .contentType("application/json")
            .header("Authorization", String.format("Bearer %s", token))
            .when()
                .get()
            .then()
                .statusCode(200)
            .extract()
                .body()
                    .asString();

        PageResponseDTO<ClientResponseDTO> pageResponse = objectMapper.readValue(content, new TypeReference<PageResponseDTO<ClientResponseDTO>>() {});

        List<ClientResponseDTO> clients = pageResponse.getContent();

        assertNotNull(pageResponse);
        assertNotNull(pageResponse.getContent());
        assert(pageResponse.getContent().size() > 0);
        assertNotNull(pageResponse.getContent().get(0).getId());
        assertNotNull(pageResponse.getContent().get(0).getAddress());
        assertNotNull(clients.get(0).getPhones());
        assertNotNull(clients.get(0).getEmails());
    }

    @Test
    @Order(4)
    void testFindClientById() throws JsonProcessingException, JsonMappingException{
        String content = given(specification)
            .contentType("application/json")
            .pathParam("id", 1)
            .header("Authorization", String.format("Bearer %s", token))
            .when()
                .get("/{id}")
            .then()
                .statusCode(200)
            .extract()
                .body()
                    .asString();

        ClientResponseDTO response = objectMapper.readValue(content, ClientResponseDTO.class);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getAddress());
        assertNotNull(response.getPhones());
        assertNotNull(response.getEmails());

        assertTrue(response.getId().equals(1L));
    }

    @Test
    @Order(5)
    void testUpdateClientById() throws JsonProcessingException, JsonMappingException{

        clientRequest.setCpf("36713796001");

        String content = given(specification)
            .contentType("application/json")
            .pathParam("id", 1)
            .header("Authorization", String.format("Bearer %s", token))
            .body(clientRequest)
            .when()
                .put("/{id}")
            .then()
                .statusCode(200)
            .extract()
                .body()
                    .asString();
        
        ClientResponseDTO response = objectMapper.readValue(content, ClientResponseDTO.class);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getAddress());
        assertNotNull(response.getPhones());
        assertNotNull(response.getEmails());

        assertTrue(response.getCpf().equals("367.137.960-01"));
    }

    @Test
    @Order(6)
    void testDeleteClientById() {
        given(specification)
            .contentType("application/json")
            .pathParam("id", 1)
            .header("Authorization", String.format("Bearer %s", token))
            .when()
                .delete("/{id}")
            .then()
                .statusCode(204);
    }

    private static void setSpecification(){
        specification = new  RequestSpecBuilder()
            .addHeader("Origin", "http://localhost:8080")
            .setBasePath("/api/client")
            .setPort(8888)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();
    }

    private static void mockClientRequest(){
        clientRequest.setName("John Doe");
        clientRequest.setEmails(Arrays.asList(new EmailRequestDTO("john.doe@example.com")));
        clientRequest.setPhones(Arrays.asList(new PhoneRequestDTO("61982148739", "CELULAR")));
        clientRequest.setCep("54774105");
        clientRequest.setCpf("59234979052");
    }
}
