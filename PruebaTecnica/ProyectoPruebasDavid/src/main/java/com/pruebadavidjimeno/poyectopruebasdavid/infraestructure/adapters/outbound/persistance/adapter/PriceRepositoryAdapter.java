package com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.outbound.persistance.adapter;

import com.pruebadavidjimeno.poyectopruebasdavid.domain.model.Price;
import com.pruebadavidjimeno.poyectopruebasdavid.domain.port.output.PriceRepositoryPort;
import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.outbound.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepositoryPort {
    private final PriceRepository priceRepository;

    @Override
    public Flux<Price> findByBrandIdAndProductIdAndDateRange(LocalDateTime applicationDate, Integer brandId, Integer productId) {
        return priceRepository.findByBrandIdAndProductIdAndDateRange(applicationDate, brandId, productId)
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
                        .build());
    }

    @Override
    public Mono<Void> addPrice(Price price) {
        return priceRepository.insertPrice(
                price.getBrandId(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPriceList(),
                price.getProductId(),
                price.getPriority(),
                price.getProductPrice(),
                price.getCurrency()
        );
    }
    //SAVE OPTION IF NEEDED
//    @Override
//    public Mono<Void> addPrice(Price price) {
//        PriceEntity entity = PriceEntity.builder()
//                .brandId(price.getBrandId())
//                .startDate(price.getStartDate())
//                .endDate(price.getEndDate())
//                .priceList(price.getPriceList())
//                .productId(price.getProductId())
//                .priority(price.getPriority())
//                .productPrice(price.getProductPrice())
//                .currency(price.getCurrency())
//                .build();
//
//        return priceRepository.save(entity).then();
//    }
}
