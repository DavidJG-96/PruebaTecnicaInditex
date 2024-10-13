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
                .map(entity -> new Price(entity.getId(), entity.getProductId(), entity.getBrandId(),
                        entity.getPriceList(), entity.getStartDate(),
                        entity.getEndDate(), entity.getProductPrice(),
                        entity.getCurrency(), entity.getPriority()))
                .toList();
    }
}
