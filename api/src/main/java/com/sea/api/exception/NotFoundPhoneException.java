package com.sea.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundPhoneException extends RuntimeException{
    
    public NotFoundPhoneException(Long id){
        super("client with ID " + id + " not found");
    }
}
