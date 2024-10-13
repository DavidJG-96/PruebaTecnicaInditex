package com.pruebainditexdavidjimeno.pruebatecnicainditex.infraestructure.adapters.outbound.repository;

import com.pruebainditexdavidjimeno.pruebatecnicainditex.infraestructure.adapters.outbound.persistance.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Integer> {

    @Query("SELECT p FROM PriceEntity p WHERE p.brandId = :brandId " +
            "AND p.productId = :productId " +
            "AND :applicationDate BETWEEN p.startDate AND p.endDate")
    List<PriceEntity> findByBrandIdAndProductIdAndDateRange(@Param("applicationDate") LocalDateTime applicationDate,
                                                            @Param("brandId") Integer brandId,
                                                            @Param("productId") Integer productId);
}


