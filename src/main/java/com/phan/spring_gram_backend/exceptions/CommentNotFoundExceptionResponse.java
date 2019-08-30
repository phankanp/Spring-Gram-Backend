package com.phan.spring_gram_backend.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentNotFoundExceptionResponse {

    private String commentNotFound;

    public CommentNotFoundExceptionResponse(String commentNotFound) {
        this.commentNotFound = commentNotFound;
    }
}
