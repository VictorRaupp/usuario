package com.victor.usuario.controller;

import com.victor.usuario.infrastructure.exceptions.ConflictException;
import com.victor.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.victor.usuario.infrastructure.exceptions.UnathorizedExcepetion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExeceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundExecption(ResourceNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler (ConflictException.class)
    public ResponseEntity<String> handleConflictExeption(ConflictException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler (UnathorizedExcepetion.class)
    public ResponseEntity<String> handleUnathorizedException(UnathorizedExcepetion ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
