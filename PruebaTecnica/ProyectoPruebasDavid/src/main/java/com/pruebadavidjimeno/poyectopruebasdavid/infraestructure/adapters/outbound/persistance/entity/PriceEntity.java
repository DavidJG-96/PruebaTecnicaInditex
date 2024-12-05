package com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.outbound.persistance.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name = "PRICES")
public class PriceEntity {
    @Id
    @Column("ID")
    private Integer id;
    @Column("PRODUCT_ID")
    private Integer productId;
    @Column("BRAND_ID")
    private Integer brandId;
    @Column("PRICE_LIST")
    private Integer priceList;
    @Column("START_DATE")
    private LocalDateTime startDate;
    @Column("END_DATE")
    private LocalDateTime endDate;
    @Column("PRICE")
    private Double productPrice;
    @Column("CURR")
    private String currency;
    @Column("PRIORITY")
    private Integer priority;
}
