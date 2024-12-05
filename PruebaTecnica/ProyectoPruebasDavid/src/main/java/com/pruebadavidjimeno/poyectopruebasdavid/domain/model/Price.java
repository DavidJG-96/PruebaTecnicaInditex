package com.pruebadavidjimeno.poyectopruebasdavid.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class Price {
    private final Integer id;
    private final Integer productId;
    private final Integer brandId;
    private final Integer priceList;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Double productPrice;
    private final String currency;
    private final Integer priority;
}
