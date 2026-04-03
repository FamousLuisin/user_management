package com.sea.api.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import com.sea.api.exception.ExceptionResponse;
import com.sea.api.exception.NotFoundAddressException;
import com.sea.api.exception.NotFoundClientException;

@ControllerAdvice
@RestController
public class ExceptionResponseHandler {
    
    @ExceptionHandler(ResponseStatusException.class)
    public final ResponseEntity<ExceptionResponse> responseStatusExceptionHandler(ResponseStatusException ex, WebRequest request){
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
        String message = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(e -> e.getDefaultMessage())
            .findFirst()
            .orElse("Validation error");
        
        ExceptionResponse response = new ExceptionResponse(
            HttpStatus.BAD_REQUEST.name(), 
            message, 
            HttpStatus.BAD_REQUEST.value(), 
            request.getDescription(false),
            LocalDateTime.now()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(NotFoundClientException.class)
    public final ResponseEntity<ExceptionResponse> notFoundClientExceptionHandler(Exception ex, WebRequest request){   
        ExceptionResponse response = new ExceptionResponse(
            HttpStatus.NOT_FOUND.name(), 
            ex.getMessage(), 
            HttpStatus.NOT_FOUND.value(), 
            request.getDescription(false),
            LocalDateTime.now()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(NotFoundAddressException.class)
    public final ResponseEntity<ExceptionResponse> notFoundAddressExceptionHandler(Exception ex, WebRequest request){   
        ExceptionResponse response = new ExceptionResponse(
            HttpStatus.NOT_FOUND.name(), 
            ex.getMessage(), 
            HttpStatus.NOT_FOUND.value(), 
            request.getDescription(false),
            LocalDateTime.now()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
