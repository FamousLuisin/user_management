package com.sea.api.unit.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sea.api.dto.request.ClientRequestDTO;
import com.sea.api.mock.MockClient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class ClienteValidationTest {
    
    private MockClient mockClient;

    @BeforeEach
    public void setUp(){
        this.mockClient = new MockClient();
    }

    @Test
    public void testValidateInvalidClientCpf(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ClientRequestDTO clientRequest = mockClient.withCpf("123.456.789-00").buildClientRequestDTO();

        Set<ConstraintViolation<ClientRequestDTO>> violations = validator.validate(clientRequest);
        assertFalse(violations.isEmpty());
        assertFalse(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpf") && v.getMessage().equals("CPF inválido")));
    }

    @Test
    public void testValidateInvalidClientName(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ClientRequestDTO clientRequest = mockClient.withName("     ").buildClientRequestDTO();

        Set<ConstraintViolation<ClientRequestDTO>> violations = validator.validate(clientRequest);
        assertFalse(violations.isEmpty());
        assertFalse(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name") && v.getMessage().equals("O nome do cliente é obrigatório")));
    }

    @Test
    public void testValidateValidClientCpf(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ClientRequestDTO clientRequest = mockClient.withCpf("462.404.381-20").buildClientRequestDTO();

        Set<ConstraintViolation<ClientRequestDTO>> violations = validator.validate(clientRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidateValidClientName(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ClientRequestDTO clientRequest = mockClient.withName("luis").buildClientRequestDTO();

        Set<ConstraintViolation<ClientRequestDTO>> violations = validator.validate(clientRequest);
        assertTrue(violations.isEmpty());
    }
}
