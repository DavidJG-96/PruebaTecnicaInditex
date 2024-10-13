package com.pruebainditexdavidjimeno.pruebatecnicainditex.domain.port.output;


import com.pruebainditexdavidjimeno.pruebatecnicainditex.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepositoryPort {
    List<Price> findByBrandIdAndProductIdAndDateRange(LocalDateTime applicationDate, Integer brandId, Integer productId);
}
