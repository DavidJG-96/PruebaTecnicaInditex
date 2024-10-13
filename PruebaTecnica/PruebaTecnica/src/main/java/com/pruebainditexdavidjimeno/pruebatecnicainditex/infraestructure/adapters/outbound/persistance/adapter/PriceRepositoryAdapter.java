package com.pruebainditexdavidjimeno.pruebatecnicainditex.infraestructure.adapters.outbound.persistance.adapter;

import com.pruebainditexdavidjimeno.pruebatecnicainditex.domain.model.Price;
import com.pruebainditexdavidjimeno.pruebatecnicainditex.domain.port.output.PriceRepositoryPort;
import com.pruebainditexdavidjimeno.pruebatecnicainditex.infraestructure.adapters.outbound.persistance.entity.PriceEntity;
import com.pruebainditexdavidjimeno.pruebatecnicainditex.infraestructure.adapters.outbound.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepositoryPort {
    private final PriceRepository priceRepository;

    @Override
    public List<Price> findByBrandIdAndProductIdAndDateRange(LocalDateTime applicationDate, Integer brandId, Integer productId) {
        List<PriceEntity> priceEntities = priceRepository.findByBrandIdAndProductIdAndDateRange(applicationDate, brandId, productId);
        return priceEntities.stream()
                .map(entity -> Price.builder()
                        .id(entity.getId())
                        .productId(entity.getProductId())
                        .brandId(entity.getBrandId())
                        .priceList(entity.getPriceList())
                        .startDate(entity.getStartDate())
                        .endDate(entity.getEndDate())
                        .productPrice(entity.getProductPrice())
                        .currency(entity.getCurrency())
                        .priority(entity.getPriority())
                        .build())
                .toList();
    }
}
