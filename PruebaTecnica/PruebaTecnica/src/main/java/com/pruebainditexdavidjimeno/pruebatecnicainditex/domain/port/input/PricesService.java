package com.pruebainditexdavidjimeno.pruebatecnicainditex.domain.port.input;

import com.pruebainditexdavidjimeno.pruebatecnicainditex.dto.PricesDto;

import java.time.LocalDateTime;

public interface PricesService {
    PricesDto getPrice(LocalDateTime date, Integer productId, Integer brandId);
}
