package com.sea.api.exception.handler;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import com.sea.api.exception.CepNotFoundException;
import com.sea.api.exception.ExceptionResponse;
import com.sea.api.exception.NotFoundAddressException;
import com.sea.api.exception.NotFoundClientException;
import com.sea.api.exception.NotFoundEmailException;
import com.sea.api.exception.NotFoundPhoneException;

@ControllerAdvice
@RestController
public class ExceptionResponseHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> globalExceptionHandler(Exception ex, WebRequest request){
        logger.error("Erro interno [{}]: {}", request.getDescription(false), ex.getMessage(), ex);

        ExceptionResponse response = new ExceptionResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.name(), 
            ex.getMessage(), 
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            request.getDescription(false),
            LocalDateTime.now()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public final ResponseEntity<ExceptionResponse> responseStatusExceptionHandler(ResponseStatusException ex, WebRequest request){
        logger.warn("ResponseStatusException [{}]: {}", request.getDescription(false), ex.getReason());

        ExceptionResponse response = new ExceptionResponse(
            ex.getStatus().name(), 
            ex.getReason(), 
            ex.getStatus().value(), 
            request.getDescription(false), 
            LocalDateTime.now()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ExceptionResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex, WebRequest request){
        String message = ex.getBindingResult().getAllErrors()
            .stream()
            .map(e -> e.getDefaultMessage())
            .findFirst()
            .orElse("Validation error");

        logger.warn("Erro de validação [{}]: {}", request.getDescription(false), message);
        
        ExceptionResponse response = new ExceptionResponse(
            HttpStatus.BAD_REQUEST.name(), 
            message, 
            HttpStatus.BAD_REQUEST.value(), 
            request.getDescription(false),
            LocalDateTime.now()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler({
        NotFoundClientException.class,
        NotFoundAddressException.class,
        NotFoundPhoneException.class,
        NotFoundEmailException.class,
        UsernameNotFoundException.class,
        CepNotFoundException.class
    })
    public final ResponseEntity<ExceptionResponse> notFoundHandler(Exception ex, WebRequest request){

        logger.warn("Recurso não encontrado [{}]: {}", request.getDescription(false), ex.getMessage());

        ExceptionResponse response = new ExceptionResponse(
            HttpStatus.NOT_FOUND.name(), 
            ex.getMessage(), 
            HttpStatus.NOT_FOUND.value(), 
            request.getDescription(false),
            LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ExceptionResponse> httpMessageNotReadableExceptionHandler(Exception ex, WebRequest request){  
        logger.warn("Erro de parsing JSON [{}]: {}", request.getDescription(false), ex.getMessage());
        
        ExceptionResponse response = new ExceptionResponse(
            HttpStatus.BAD_REQUEST.name(), 
            "Dados inválidos", 
            HttpStatus.BAD_REQUEST.value(), 
            request.getDescription(false),
            LocalDateTime.now()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<ExceptionResponse> authenticationExceptionHandler(Exception ex, WebRequest request){   
        logger.warn("Falha de autenticação [{}]: {}", request.getDescription(false), ex.getMessage());

        ExceptionResponse response = new ExceptionResponse(
            HttpStatus.UNAUTHORIZED.name(), 
            ex.getMessage(), 
            HttpStatus.UNAUTHORIZED.value(), 
            request.getDescription(false),
            LocalDateTime.now()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ExceptionResponse> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex, WebRequest request){
        logger.warn("Violação de integridade dos dados [{}]: {}", request.getDescription(false), ex.getMessage());

        ExceptionResponse response = new ExceptionResponse(
            HttpStatus.CONFLICT.name(), 
            "Violação de integridade dos dados: " + ex.getMostSpecificCause().getMessage().split("\n")[0], 
            HttpStatus.CONFLICT.value(), 
            request.getDescription(false),
            LocalDateTime.now()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
