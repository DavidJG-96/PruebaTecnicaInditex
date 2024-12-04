package com.pruebainditexdavidjimeno.pruebatecnicainditex.domain.port.input;

import com.pruebainditexdavidjimeno.pruebatecnicainditex.domain.model.Price;

import java.time.LocalDateTime;

public interface PricesService {
    Price getPrice(LocalDateTime date, Integer productId, Integer brandId);
}
