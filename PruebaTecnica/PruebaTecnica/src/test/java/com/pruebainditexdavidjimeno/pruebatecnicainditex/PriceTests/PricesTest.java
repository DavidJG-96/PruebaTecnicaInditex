package com.pruebainditexdavidjimeno.pruebatecnicainditex.PriceTests;

import java.time.LocalDateTime;

import com.pruebainditexdavidjimeno.pruebatecnicainditex.infraestructure.adapters.exception.InvalidDateException;
import com.pruebainditexdavidjimeno.pruebatecnicainditex.infraestructure.adapters.inbound.rest.ShopController;
import com.pruebainditexdavidjimeno.pruebatecnicainditex.infraestructure.adapters.inbound.rest.dto.PricesDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PricesTest {

    @Autowired
    private ShopController shopController;

    @Test
    void whenRequestingPriceAt10AMOn14June_thenReturnsPriceWithHighestPriority() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");
        LocalDateTime expectedStartDate = LocalDateTime.parse("2020-06-14T00:00");
        LocalDateTime expectedEndDate = LocalDateTime.parse("2020-12-31T23:59:59");

        ResponseEntity<PricesDto> response = shopController.getPrices(applicationDate, 35455, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PricesDto price = response.getBody();
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
        ResponseEntity<PricesDto> response = shopController.getPrices(applicationDate, 35455, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PricesDto price = response.getBody();
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
        ResponseEntity<PricesDto> response = shopController.getPrices(applicationDate, 35455, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PricesDto price = response.getBody();
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
        ResponseEntity<PricesDto> response = shopController.getPrices(applicationDate, 35455, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PricesDto price = response.getBody();
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
        ResponseEntity<PricesDto> response = shopController.getPrices(applicationDate, 35455, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PricesDto price = response.getBody();
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
        ResponseEntity<PricesDto> response = shopController.getPrices(applicationDate, 89321, 2);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void whenRequestedPriceIsNotValid_thenReturnsBadRequest() {
        LocalDateTime applicationDate = LocalDateTime.parse("2045-06-16T21:00:00");
        ResponseEntity<PricesDto> response = shopController.getPrices(applicationDate, 89321, 2);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
