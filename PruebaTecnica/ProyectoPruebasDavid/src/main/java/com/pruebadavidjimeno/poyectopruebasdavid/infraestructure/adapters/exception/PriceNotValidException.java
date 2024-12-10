package com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class PriceNotValidException extends RuntimeException {
    private final HttpStatus status;

    public PriceNotValidException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}

