package com.sea.api.integration.cors;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sea.api.dto.request.LoginUserDTO;
import com.sea.api.dto.response.JwtResponseDTO;
import com.sea.api.integration.AbstractIntegrationTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CorsTest extends AbstractIntegrationTest {
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Test
    @Order(1)
    void logInWithValidCors() {
        RequestSpecification specification = new  RequestSpecBuilder()
            .addHeader("Origin", "http://localhost:8080")
            .setBasePath("/auth/login")
            .setPort(8888)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();


        LoginUserDTO loginRequest = new LoginUserDTO();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("admin");

        String content = given(specification)
            .contentType("application/json")
            .body(loginRequest)
            .when()
                .post()
            .then()
                .statusCode(200)
            .extract()
                .body()
                    .asString();
        
        JwtResponseDTO jwtResponse = objectMapper.convertValue(content, JwtResponseDTO.class);
        
        assertNotNull(jwtResponse);
        assertNotNull(jwtResponse.getToken());
    }

    @Test
    @Order(1)
    void logInWithInvalidCors() {
        RequestSpecification specification = new  RequestSpecBuilder()
            .addHeader("Origin", "http://localhost:8089")
            .setBasePath("/auth/login")
            .setPort(8888)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

        LoginUserDTO loginRequest = new LoginUserDTO();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("admin");

        String content = given(specification)
            .contentType("application/json")
            .body(loginRequest)
            .when()
                .post()
            .then()
                .statusCode(403)
            .extract()
                .body()
                    .asString();
        
        assertTrue(content.contains("CORS"));
    }
}
