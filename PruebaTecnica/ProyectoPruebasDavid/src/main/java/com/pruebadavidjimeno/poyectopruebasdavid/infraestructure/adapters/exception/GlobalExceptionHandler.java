package com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<String> handleInvalidDateException(InvalidDateException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
