package com.pruebadavidjimeno.poyectopruebasdavid.application.service;

import com.pruebadavidjimeno.poyectopruebasdavid.domain.model.Price;
import com.pruebadavidjimeno.poyectopruebasdavid.domain.port.output.PriceRepositoryPort;
import com.pruebadavidjimeno.poyectopruebasdavid.domain.port.input.PricesService;
import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.exception.PriceNotValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

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
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No price has been found with the data provided.")))
                .reduce((price1, price2) ->
                        Comparator.comparingInt(Price::getPriority).compare(price1, price2) > 0 ? price1 : price2)
                .doOnError(e -> log.warn("[PricesService] Error finding price: {}", e.getMessage()))
                .doOnSuccess(price -> log.info("[PricesService] Price found: {}", price));

    }

    //TODO REVISIT ADD PRICE TO CHANGE HOW THE VALIDATION WORKS
    @Override
    public Mono<Void> addPrice(Price price) {
        if (price.getProductId() == null || price.getProductId() <= 0 ||
                price.getBrandId() == null || price.getBrandId() <= 0 ||
                price.getStartDate() == null || price.getEndDate() == null ||
                price.getProductPrice() == null || price.getProductPrice() <= 0 ||
                price.getCurrency() == null || price.getCurrency().isEmpty()) {

            return Mono.error(new PriceNotValidException(HttpStatus.BAD_REQUEST, "Invalid price data"));
        }
        return priceRepositoryPort.addPrice(price);
    }
}
