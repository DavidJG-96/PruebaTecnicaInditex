package com.PruebaDavidJimeno.PruebaTecnica.controller;

import com.PruebaDavidJimeno.PruebaTecnica.dto.PricesDto;
import com.PruebaDavidJimeno.PruebaTecnica.service.serviceimpl.PricesServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
public class ShopController {
    private final PricesServiceImpl pricesServiceImpl;

    public ShopController(PricesServiceImpl pricesServiceImpl) {
        this.pricesServiceImpl = pricesServiceImpl;
    }

    @GetMapping("prices/getProductPrice")
    public ResponseEntity<PricesDto> getPrices(
            @RequestParam("applicationDate") LocalDateTime applicationDate,
            @RequestParam("productId") Integer productId,
            @RequestParam("brandId") Integer brandId
    ) {
        try {
            log.info("[ShopController] Request received: date: {}, productId: {}, brandId: {}.",
                    applicationDate, productId, brandId);

            PricesDto response = pricesServiceImpl.getPrice(applicationDate, productId, brandId);

            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
