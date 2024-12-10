package com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.outbound.repository;

import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.outbound.persistance.entity.PriceEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface PriceRepository extends ReactiveCrudRepository<PriceEntity, Integer> {

    @Query("SELECT * FROM PRICES p WHERE p.BRAND_ID = :brandId " +
            "AND p.PRODUCT_ID = :productId " +
            "AND :applicationDate BETWEEN p.START_DATE AND p.END_DATE")
    Flux<PriceEntity> findByBrandIdAndProductIdAndDateRange(@Param("applicationDate") LocalDateTime applicationDate,
                                                            @Param("brandId") Integer brandId,
                                                            @Param("productId") Integer productId);


    @Query("INSERT INTO PRICES (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR) " +
            "VALUES (:brandId, :startDate, :endDate, :priceList, :productId, :priority, :price, :curr)")
    Mono<Void> insertPrice(@Param("brandId") Integer brandId,
                           @Param("startDate") LocalDateTime startDate,
                           @Param("endDate") LocalDateTime endDate,
                           @Param("priceList") Integer priceList,
                           @Param("productId") Integer productId,
                           @Param("priority") Integer priority,
                           @Param("price") Double price,
                           @Param("curr") String currency);

}


