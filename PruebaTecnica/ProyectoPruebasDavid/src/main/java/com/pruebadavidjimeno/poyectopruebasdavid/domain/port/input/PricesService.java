package com.pruebadavidjimeno.poyectopruebasdavid.domain.port.input;

import com.pruebadavidjimeno.poyectopruebasdavid.domain.model.Price;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PricesService {
    Mono<Price> getPrice(LocalDateTime date, Integer productId, Integer brandId);

    Mono<Void> addPrice(Price price);
}
