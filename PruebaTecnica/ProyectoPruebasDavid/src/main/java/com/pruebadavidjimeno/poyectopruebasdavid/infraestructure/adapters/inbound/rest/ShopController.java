package com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.inbound.rest;

import com.pruebadavidjimeno.poyectopruebasdavid.domain.port.input.PricesService;
import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.exception.InvalidDateException;
import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.inbound.rest.dto.PricesDto;
import com.pruebadavidjimeno.poyectopruebasdavid.application.service.PricesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public Mono<PricesDto> getPrices(
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
                .map(price -> PricesDto.builder()
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
}
