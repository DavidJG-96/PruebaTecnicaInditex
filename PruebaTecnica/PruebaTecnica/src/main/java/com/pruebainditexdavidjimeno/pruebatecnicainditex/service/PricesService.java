package com.pruebainditexdavidjimeno.pruebatecnicainditex.service;

import com.pruebainditexdavidjimeno.pruebatecnicainditex.dto.PricesDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface PricesService {
    PricesDto getPrice(LocalDateTime date, Integer productId, Integer brandId);
}
