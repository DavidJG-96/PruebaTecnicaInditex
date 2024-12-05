package com.pruebadavidjimeno.poyectopruebasdavid.domain.port.output;


import com.pruebadavidjimeno.poyectopruebasdavid.domain.model.Price;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface PriceRepositoryPort {
    Flux<Price> findByBrandIdAndProductIdAndDateRange(LocalDateTime applicationDate, Integer brandId, Integer productId);
}
