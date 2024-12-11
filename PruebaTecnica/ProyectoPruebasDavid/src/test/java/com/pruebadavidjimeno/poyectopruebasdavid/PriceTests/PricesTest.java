package com.pruebadavidjimeno.poyectopruebasdavid.PriceTests;

import java.time.LocalDateTime;

import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.inbound.rest.dto.RequestPricesDto;
import com.pruebadavidjimeno.poyectopruebasdavid.infraestructure.adapters.inbound.rest.dto.ResponsePricesDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class PricesTest {
    @Autowired
    private WebTestClient webTestClient;


    @Test
    void whenRequestingPriceAt10AMOn14June_thenReturnsPriceWithHighestPriority() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");
        LocalDateTime expectedStartDate = LocalDateTime.parse("2020-06-14T00:00");
        LocalDateTime expectedEndDate = LocalDateTime.parse("2020-12-31T23:59:59");

        ResponsePricesDto price = webTestClient.get()
                .uri("/prices/product-price?date=" + applicationDate + "&productId=35455&brandId=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponsePricesDto.class)
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

        ResponsePricesDto price = webTestClient.get()
                .uri("/prices/product-price?date=" + applicationDate + "&productId=35455&brandId=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponsePricesDto.class)
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

        ResponsePricesDto price = webTestClient.get()
                .uri("/prices/product-price?date=" + applicationDate + "&productId=35455&brandId=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponsePricesDto.class)
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

        ResponsePricesDto price = webTestClient.get()
                .uri("/prices/product-price?date=" + applicationDate + "&productId=35455&brandId=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponsePricesDto.class)
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

        ResponsePricesDto price = webTestClient.get()
                .uri("/prices/product-price?date=" + applicationDate + "&productId=35455&brandId=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponsePricesDto.class)
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
                .uri("/prices/product-price?date=" + applicationDate + "&productId=89321&brandId=2")
                .exchange()
                .expectStatus().isNotFound();
    }


    @Test
    void whenRequestedPriceIsNotValid_thenReturnsBadRequest() {
        LocalDateTime applicationDate = LocalDateTime.parse("2045-06-16T21:00:00");

        webTestClient.get()
                .uri("/prices/product-price?date=" + applicationDate + "&productId=89321&brandId=2")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void whenAddingValidPrice_thenReturnsCreatedStatus() {
        RequestPricesDto requestDto = new RequestPricesDto();
        requestDto.setBrandId(1);
        requestDto.setStartDate(LocalDateTime.parse("2024-01-01T00:00:00"));
        requestDto.setEndDate(LocalDateTime.parse("2024-12-31T23:59:59"));
        requestDto.setPriceList(5);
        requestDto.setProductId(12345);
        requestDto.setPriority(0);
        requestDto.setPrice(50.99);
        requestDto.setCurr("EUR");

        webTestClient.post()
                .uri("/prices/product-price")
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void whenAddingInvalidPrice_thenReturnsBadRequestStatus() {
        RequestPricesDto requestDto = new RequestPricesDto();
        requestDto.setBrandId(null);
        requestDto.setStartDate(LocalDateTime.parse("2024-01-01T00:00:00"));
        requestDto.setEndDate(LocalDateTime.parse("2024-12-31T23:59:59"));
        requestDto.setPriceList(5);
        requestDto.setProductId(null);
        requestDto.setPriority(0);
        requestDto.setPrice(50.99);
        requestDto.setCurr("EUR");

        webTestClient.post()
                .uri("/prices/product-price")
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

}
