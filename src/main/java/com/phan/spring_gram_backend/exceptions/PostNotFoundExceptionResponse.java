package com.phan.spring_gram_backend.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostNotFoundExceptionResponse {

    private String postNotFound;

    public PostNotFoundExceptionResponse(String postNotFound) {
        this.postNotFound = postNotFound;
    }
}
