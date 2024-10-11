package com.PruebaDavidJimeno.PruebaTecnica.service;

import com.PruebaDavidJimeno.PruebaTecnica.dto.PricesDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface PricesService {
    PricesDto getPrice(LocalDateTime date, Integer productId, Integer brandId);
}
