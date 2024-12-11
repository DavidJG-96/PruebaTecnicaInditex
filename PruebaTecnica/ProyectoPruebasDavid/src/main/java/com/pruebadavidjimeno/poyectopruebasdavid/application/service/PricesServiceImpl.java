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
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PricesServiceImpl implements PricesService {
    private final PriceRepositoryPort priceRepositoryPort;

    @Override
    public Mono<Price> getPrice(LocalDateTime date, Integer productId, Integer brandId) {
        log.info("[PricesService] Searching prices with the given data: date {}, productId {}, brandId {}.",
                date, productId, brandId);
        return priceRepositoryPort.findByBrandIdAndProductIdAndDateRange(date, brandId, productId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No price has been found with the data provided.")))
                .reduce((price1, price2) ->
                        Comparator.comparingInt(Price::getPriority).compare(price1, price2) > 0
                                ? price1
                                : price2)
                .doOnError(e -> log.warn("[PricesService] Error finding price: {}", e.getMessage()))
                .doOnSuccess(price -> log.info("[PricesService] Price found: {}", price));

    }

    @Override
    public Mono<Void> addPrice(Price price) {
        return validatePrice(price).flatMap(valid -> priceRepositoryPort
                .addPrice(price))
                .onErrorResume(e -> Mono.error(new PriceNotValidException(HttpStatus.BAD_REQUEST, e.getMessage())));
    }

    private Mono<Price> validatePrice(Price price) {
        if (Objects.isNull(price)) {
            return Mono.error(new PriceNotValidException(HttpStatus.BAD_REQUEST, "Price object cannot be null"));
        }
        if (Objects.isNull(price.getProductId()) || price.getProductId() <= 0) {
            return Mono.error(new PriceNotValidException(HttpStatus.BAD_REQUEST, "Product ID is invalid"));
        }
        if (Objects.isNull(price.getBrandId()) || price.getBrandId() <= 0) {
            return Mono.error(new PriceNotValidException(HttpStatus.BAD_REQUEST, "Brand ID is invalid"));
        }
        if (Objects.isNull(price.getStartDate())) {
            return Mono.error(new PriceNotValidException(HttpStatus.BAD_REQUEST, "Start date cannot be null"));
        }
        if (Objects.isNull(price.getEndDate())) {
            return Mono.error(new PriceNotValidException(HttpStatus.BAD_REQUEST, "End date cannot be null"));
        }
        if (Objects.isNull(price.getProductPrice()) || price.getProductPrice() <= 0) {
            return Mono.error(new PriceNotValidException(HttpStatus.BAD_REQUEST, "Product price is invalid"));
        }
        if (Objects.isNull(price.getCurrency()) || price.getCurrency().isEmpty()) {
            return Mono.error(new PriceNotValidException(HttpStatus.BAD_REQUEST, "Currency cannot be empty"));
        }
        return Mono.just(price);
    }
}
