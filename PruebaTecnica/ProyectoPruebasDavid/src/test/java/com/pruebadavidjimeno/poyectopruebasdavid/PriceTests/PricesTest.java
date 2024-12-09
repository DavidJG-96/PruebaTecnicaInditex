package com.pruebadavidjimeno.poyectopruebasdavid.PriceTests;

import java.time.LocalDateTime;

import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.inbound.rest.ShopController;
import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.inbound.rest.dto.PricesDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureWebTestClient
class PricesTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ShopController shopController;


    @Test
    void whenRequestingPriceAt10AMOn14June_thenReturnsPriceWithHighestPriority() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");
        LocalDateTime expectedStartDate = LocalDateTime.parse("2020-06-14T00:00");
        LocalDateTime expectedEndDate = LocalDateTime.parse("2020-12-31T23:59:59");

        PricesDto price = webTestClient.get()
                .uri("/prices?date=" + applicationDate + "&productId=35455&brandId=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PricesDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(price);
        assertEquals(35.50, price.getPrice());
        assertEquals(1, price.getPriceList());
        assertEquals(35455, price.getProductId());
        assertEquals(1, price.getBrandId());
        assertEquals(expectedStartDate, price.getStartDate());
        assertEquals(expectedEndDate, price.getEndDate());
    }

    @Test
    void whenRequestingPriceAt4PMOn14June_thenReturnsPriceWithHighestPriority() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T16:00:00");
        LocalDateTime expectedStartDate = LocalDateTime.parse("2020-06-14T15:00");
        LocalDateTime expectedEndDate = LocalDateTime.parse("2020-06-14T18:30");

        PricesDto price = webTestClient.get()
                .uri("/prices?date=" + applicationDate + "&productId=35455&brandId=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PricesDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(price);
        assertEquals(25.45, price.getPrice());
        assertEquals(2, price.getPriceList());
        assertEquals(35455, price.getProductId());
        assertEquals(1, price.getBrandId());
        assertEquals(expectedStartDate, price.getStartDate());
        assertEquals(expectedEndDate, price.getEndDate());
    }

    @Test
    void whenRequestingPriceAt9PMOn14June_thenReturnsPriceWithHighestPriority() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T21:00:00");
        LocalDateTime expectedStartDate = LocalDateTime.parse("2020-06-14T00:00");
        LocalDateTime expectedEndDate = LocalDateTime.parse("2020-12-31T23:59:59");

        PricesDto price = webTestClient.get()
                .uri("/prices?date=" + applicationDate + "&productId=35455&brandId=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PricesDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(price);
        assertEquals(35.50, price.getPrice());
        assertEquals(1, price.getPriceList());
        assertEquals(35455, price.getProductId());
        assertEquals(1, price.getBrandId());
        assertEquals(expectedStartDate, price.getStartDate());
        assertEquals(expectedEndDate, price.getEndDate());
    }


    @Test
    void whenRequestingPriceAt10AMOn15June_thenReturnsPriceWithHighestPriority() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-15T10:00:00");
        LocalDateTime expectedStartDate = LocalDateTime.parse("2020-06-15T00:00");
        LocalDateTime expectedEndDate = LocalDateTime.parse("2020-06-15T11:00");

        PricesDto price = webTestClient.get()
                .uri("/prices?date=" + applicationDate + "&productId=35455&brandId=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PricesDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(price);
        assertEquals(30.50, price.getPrice());
        assertEquals(3, price.getPriceList());
        assertEquals(35455, price.getProductId());
        assertEquals(1, price.getBrandId());
        assertEquals(expectedStartDate, price.getStartDate());
        assertEquals(expectedEndDate, price.getEndDate());
    }


    @Test
    void whenRequestingPriceAt9PMOn16June_thenReturnsPriceWithHighestPriority() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-16T21:00:00");
        LocalDateTime expectedStartDate = LocalDateTime.parse("2020-06-15T16:00");
        LocalDateTime expectedEndDate = LocalDateTime.parse("2020-12-31T23:59:59");

        PricesDto price = webTestClient.get()
                .uri("/prices?date=" + applicationDate + "&productId=35455&brandId=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PricesDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(price);
        assertEquals(38.95, price.getPrice());
        assertEquals(4, price.getPriceList());
        assertEquals(35455, price.getProductId());
        assertEquals(1, price.getBrandId());
        assertEquals(expectedStartDate, price.getStartDate());
        assertEquals(expectedEndDate, price.getEndDate());
    }


    @Test
    void whenRequestedPriceIsNotFound_thenReturns404NotFound() {
        LocalDateTime applicationDate = LocalDateTime.parse("2000-06-16T21:00:00");

        webTestClient.get()
                .uri("/prices?date=" + applicationDate + "&productId=89321&brandId=2")
                .exchange()
                .expectStatus().isNotFound();
    }


    @Test
    void whenRequestedPriceIsNotValid_thenReturnsBadRequest() {
        LocalDateTime applicationDate = LocalDateTime.parse("2045-06-16T21:00:00");

        webTestClient.get()
                .uri("/prices?date=" + applicationDate + "&productId=89321&brandId=2")
                .exchange()
                .expectStatus().isBadRequest();
    }
}
