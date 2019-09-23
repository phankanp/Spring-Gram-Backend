package com.phan.spring_gram_backend.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestExceptionResponse {

    private String badRequest;

    public BadRequestExceptionResponse(String badRequest) {
        this.badRequest = badRequest;
    }
}
