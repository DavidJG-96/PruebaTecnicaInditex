package com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.inbound.rest;

import com.pruebadavidjimeno.poyectopruebasdavid.domain.model.Price;
import com.pruebadavidjimeno.poyectopruebasdavid.domain.port.input.PricesService;
import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.exception.InvalidDateException;
import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.exception.PriceNotValidException;
import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.inbound.rest.dto.RequestPricesDto;
import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.inbound.rest.dto.ResponsePricesDto;
import com.pruebadavidjimeno.poyectopruebasdavid.application.service.PricesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.management.InstanceNotFoundException;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/prices")
public class ShopController {
    private final PricesService pricesService;

    public ShopController(PricesServiceImpl pricesServiceImpl) {
        this.pricesService = pricesServiceImpl;
    }

    @GetMapping("/product-price")
    public Mono<ResponsePricesDto> getPrices(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @RequestParam("productId") Integer productId,
            @RequestParam("brandId") Integer brandId
    ) {

        if (Objects.isNull(applicationDate) || applicationDate.isAfter(LocalDateTime.now())) {
            return Mono.error(new InvalidDateException(String.format("Invalid date format: %s", applicationDate)));
        }

        log.info("[ShopController] Request received: date: {}, productId: {}, brandId: {}.",
                applicationDate, productId, brandId);

        return pricesService.getPrice(applicationDate, productId, brandId)
                .map(price -> ResponsePricesDto.builder()
                        .productId(price.getProductId())
                        .brandId(price.getBrandId())
                        .priceList(price.getPriceList())
                        .startDate(price.getStartDate())
                        .endDate(price.getEndDate())
                        .price(price.getProductPrice())
                        .build())
                .onErrorResume(InstanceNotFoundException.class, ex ->
                        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage())));
    }

    //void since we don't need a response apart from the 201
    @PostMapping("/product-price")
    public Mono<ResponseEntity<Object>> addPrice(@RequestBody RequestPricesDto requestDto) {

        Price priceToSave = Price.builder()
                .brandId(requestDto.getBrandId())
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .priceList(requestDto.getPriceList())
                .productId(requestDto.getProductId())
                .priority(requestDto.getPriority())
                .productPrice(requestDto.getPrice())
                .currency(requestDto.getCurr())
                .build();

        return pricesService.addPrice(priceToSave)
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()))
                .onErrorResume(e -> {
                    if (e instanceof PriceNotValidException) {
                        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }
}
