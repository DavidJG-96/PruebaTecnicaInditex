package com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.inbound.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ResponsePricesDto {
    private final Integer productId;
    private final Integer brandId;
    private final Integer priceList;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Integer priority;
    private final Double price;
    private final String curr;
}
