package com.pruebainditexdavidjimeno.pruebatecnicainditex.service.serviceimpl;

import com.pruebainditexdavidjimeno.pruebatecnicainditex.dto.PricesDto;
import com.pruebainditexdavidjimeno.pruebatecnicainditex.model.Price;
import com.pruebainditexdavidjimeno.pruebatecnicainditex.repository.PriceRepository;
import com.pruebainditexdavidjimeno.pruebatecnicainditex.service.PricesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PricesServiceImpl implements PricesService {
    private final PriceRepository priceRepository;

    @Override
    public PricesDto getPrice(LocalDateTime date, Integer productId, Integer brandId) {
        log.info("[PricesService] Searching prices with the given data: date {}, productId {}, brandId {}.",
                date, productId, brandId);
        final List<Price> productPrice = priceRepository.findByBrandIdAndProductIdAndDateRange(date, brandId, productId);

        if (productPrice.isEmpty()) {
            log.warn("[PricesService] No price has been found with the data provided: date {}, productId {}, " +
                    "brandId {}.", date, productId, brandId);
            throw new EntityNotFoundException("No price has been found with the data provided.");
        }

        return productPrice.stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .map(price -> PricesDto.builder()
                        .productId(price.getProductId())
                        .brandId(price.getBrandId())
                        .priceList(price.getPriceList())
                        .startDate(price.getStartDate())
                        .endDate(price.getEndDate())
                        .price(price.getProductPrice())
                        .build())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Error searching price, no price found for the specified date and criteria."));
    }
}
