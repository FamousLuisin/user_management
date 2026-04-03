package com.sea.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundEmailException extends RuntimeException{
    
    public NotFoundEmailException(Long id){
        super("email with ID " + id + " not found");
    }
}
