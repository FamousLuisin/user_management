package com.sea.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundAddressException extends RuntimeException{
    
    public NotFoundAddressException(Long id){
        super("address with ID " + id + " not found");
    }
}
