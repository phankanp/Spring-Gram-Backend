package com.phan.spring_gram_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AliasAlreadyExistsException extends RuntimeException {

    public AliasAlreadyExistsException(String message) {
        super(message);
    }
}
