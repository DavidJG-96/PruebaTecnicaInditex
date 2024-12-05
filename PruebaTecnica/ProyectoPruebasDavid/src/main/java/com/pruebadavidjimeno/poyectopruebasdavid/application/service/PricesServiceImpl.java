package com.pruebadavidjimeno.poyectopruebasdavid.application.service;

import com.pruebadavidjimeno.poyectopruebasdavid.domain.model.Price;
import com.pruebadavidjimeno.poyectopruebasdavid.domain.port.output.PriceRepositoryPort;
import com.pruebadavidjimeno.poyectopruebasdavid.domain.port.input.PricesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.management.InstanceNotFoundException;
import java.time.LocalDateTime;
import java.util.Comparator;

@Slf4j
@Service
@RequiredArgsConstructor
public class PricesServiceImpl implements PricesService {
    private final PriceRepositoryPort priceRepositoryPort;

    @Override
    public Mono<Price> getPrice(LocalDateTime date, Integer productId, Integer brandId) {
        log.info("[PricesService] Searching prices with the given data: date {}, productId {}, brandId {}.",
                date, productId, brandId);
        return priceRepositoryPort.findByBrandIdAndProductIdAndDateRange(date, brandId, productId) // Devuelve un Flux<Price>
                .switchIfEmpty(Mono.error(new InstanceNotFoundException("No price has been found with the data provided.")))
                .reduce((price1, price2) ->
                        Comparator.comparingInt(Price::getPriority).compare(price1, price2) > 0 ? price1 : price2)
                .doOnError(e -> log.warn("[PricesService] Error finding price: {}", e.getMessage()))
                .doOnSuccess(price -> log.info("[PricesService] Price found: {}", price));

    }
}
