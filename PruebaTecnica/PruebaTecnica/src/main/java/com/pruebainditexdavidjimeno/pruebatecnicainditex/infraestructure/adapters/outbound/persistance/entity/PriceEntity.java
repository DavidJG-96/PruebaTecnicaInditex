package com.pruebainditexdavidjimeno.pruebatecnicainditex.infraestructure.adapters.outbound.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDateTime;

@Getter
@Entity
@EntityScan
@NoArgsConstructor
@Table(name = "PRICES")
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PRODUCT_ID")
    private Integer productId;

    @Column(name = "BRAND_ID")
    private Integer brandId;

    @Column(name = "PRICE_LIST")
    private Integer priceList;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "PRICE")
    private Double productPrice;

    @Column(name = "CURR")
    private String currency;

    @Column(name = "PRIORITY")
    private Integer priority;
}
