package com.phan.spring_gram_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex, WebRequest request) {
        UsernameAlreadyExistsResponse exceptionResponse = new UsernameAlreadyExistsResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleAliasAlreadyExists(AliasAlreadyExistsException ex, WebRequest request) {
        AliasAlreadyExistsResponse exceptionResponse = new AliasAlreadyExistsResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handlePostNotFound(PostNotFoundException ex, WebRequest request) {
        PostNotFoundExceptionResponse exceptionResponse = new PostNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleCommentNotFound(CommentNotFoundException ex, WebRequest request) {
        CommentNotFoundExceptionResponse exceptionResponse = new CommentNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleBadRequest(BadRequestException ex, WebRequest request) {
        BadRequestExceptionResponse exceptionResponse = new BadRequestExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


}
