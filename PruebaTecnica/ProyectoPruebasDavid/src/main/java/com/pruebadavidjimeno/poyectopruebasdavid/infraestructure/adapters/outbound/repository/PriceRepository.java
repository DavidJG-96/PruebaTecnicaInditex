package com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.outbound.repository;

import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.outbound.persistance.entity.PriceEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface PriceRepository extends ReactiveCrudRepository<PriceEntity, Integer> {

    @Query("SELECT * FROM PRICES p WHERE p.BRAND_ID = :brandId " +
            "AND p.PRODUCT_ID = :productId " +
            "AND :applicationDate BETWEEN p.START_DATE AND p.END_DATE")
    Flux<PriceEntity> findByBrandIdAndProductIdAndDateRange(@Param("applicationDate") LocalDateTime applicationDate,
                                                            @Param("brandId") Integer brandId,
                                                            @Param("productId") Integer productId);
}


