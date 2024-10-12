package com.pruebainditexdavidjimeno.pruebatecnicainditex.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PricesDto {
    private final Integer productId;
    private final Integer brandId;
    private final Integer priceList;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Double price;
}
