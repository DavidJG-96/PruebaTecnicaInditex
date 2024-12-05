package com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.inbound.rest;

import com.pruebadavidjimeno.poyectopruebasdavid.domain.port.input.PricesService;
import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.exception.InvalidDateException;
import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.inbound.rest.dto.PricesDto;
import com.pruebadavidjimeno.poyectopruebasdavid.application.service.PricesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

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
            @RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @RequestParam("productId") Integer productId,
            @RequestParam("brandId") Integer brandId
    ) {

        if (applicationDate == null || applicationDate.isAfter(LocalDateTime.now())) {
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
                .onErrorMap(ClassNotFoundException.class, ex -> {
                    log.error("[ShopController] Entity not found: {}", ex.getMessage());
                    return new RuntimeException("Price not found for given parameters", ex);
                });
    }
}
