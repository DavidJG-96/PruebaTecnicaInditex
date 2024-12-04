package com.pruebainditexdavidjimeno.pruebatecnicainditex.infraestructure.adapters.inbound.rest;

import com.pruebainditexdavidjimeno.pruebatecnicainditex.domain.model.Price;
import com.pruebainditexdavidjimeno.pruebatecnicainditex.domain.port.input.PricesService;
import com.pruebainditexdavidjimeno.pruebatecnicainditex.infraestructure.adapters.exception.InvalidDateException;
import com.pruebainditexdavidjimeno.pruebatecnicainditex.infraestructure.adapters.inbound.rest.dto.PricesDto;
import com.pruebainditexdavidjimeno.pruebatecnicainditex.application.service.PricesServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
public class ShopController {
    private final PricesService pricesService;

    public ShopController(PricesServiceImpl pricesServiceImpl) {
        this.pricesService = pricesServiceImpl;
    }

    @GetMapping("prices/product-price")
    public ResponseEntity<PricesDto> getPrices(
            @RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @RequestParam("productId") Integer productId,
            @RequestParam("brandId") Integer brandId
    ) {
        try {
            if (applicationDate == null || applicationDate.isAfter(LocalDateTime.now())) {
                throw new InvalidDateException(String.format("Invalid date format: %s", applicationDate));
            }

            log.info("[ShopController] Request received: date: {}, productId: {}, brandId: {}.",
                    applicationDate, productId, brandId);

            Price response = pricesService.getPrice(applicationDate, productId, brandId);

            log.info("[ShopController] Response received: productId: {}, brandId: {}, priceList: {}, startDate: {}, " +
                            "endDate: {}, price: {}.",
                    response.getProductId(), response.getBrandId(), response.getPriceList(), response.getStartDate(),
                    response.getEndDate(), response.getProductPrice());

            PricesDto pricesDto = PricesDto.builder()
                    .productId(response.getProductId())
                    .brandId(response.getBrandId())
                    .priceList(response.getPriceList())
                    .startDate(response.getStartDate())
                    .endDate(response.getEndDate())
                    .price(response.getProductPrice())
                    .build();

            return ResponseEntity.ok(pricesDto);
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        } catch (InvalidDateException e) {
            //TODO return better messages
            return ResponseEntity.badRequest().build();
        }
    }
}
