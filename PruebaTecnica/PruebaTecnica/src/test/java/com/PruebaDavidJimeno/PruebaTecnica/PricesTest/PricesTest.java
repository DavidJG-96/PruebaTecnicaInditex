package com.PruebaDavidJimeno.PruebaTecnica.PricesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.PruebaDavidJimeno.PruebaTecnica.controller.ShopController;
import com.PruebaDavidJimeno.PruebaTecnica.dto.PricesDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class PricesTest {

    @Autowired
    private ShopController shopController;

    private LocalDateTime parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(dateString, formatter);
    }

    @Test
    void whenRequestingPriceAt10AMOn14June_thenReturnsPriceWithHighestPriority() {
        LocalDateTime applicationDate = parseDate("2020-06-14T10:00:00");
        ResponseEntity<PricesDto> response = shopController.getPrices(applicationDate, 35455, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PricesDto price = response.getBody();
        assertNotNull(price);
        assertEquals(35.50, price.getPrice());
        assertEquals(1, price.getPriceList());
        assertEquals(35455, price.getProductId());
        assertEquals(1, price.getBrandId());
    }

    @Test
    void whenRequestingPriceAt4PMOn14June_thenReturnsPriceWithHighestPriority() {
        LocalDateTime applicationDate = parseDate("2020-06-14T16:00:00");
        ResponseEntity<PricesDto> response = shopController.getPrices(applicationDate, 35455, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PricesDto price = response.getBody();
        assertNotNull(price);
        assertEquals(25.45, price.getPrice());
        assertEquals(2, price.getPriceList());
        assertEquals(35455, price.getProductId());
        assertEquals(1, price.getBrandId());
    }

    @Test
    void whenRequestingPriceAt9PMOn14June_thenReturnsPriceWithHighestPriority() {
        LocalDateTime applicationDate = parseDate("2020-06-14T21:00:00");
        ResponseEntity<PricesDto> response = shopController.getPrices(applicationDate, 35455, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PricesDto price = response.getBody();
        assertNotNull(price);
        assertEquals(35.50, price.getPrice());
        assertEquals(1, price.getPriceList());
        assertEquals(35455, price.getProductId());
        assertEquals(1, price.getBrandId());
    }

    @Test
    void whenRequestingPriceAt10AMOn15June_thenReturnsPriceWithHighestPriority() {
        LocalDateTime applicationDate = parseDate("2020-06-15T10:00:00");
        ResponseEntity<PricesDto> response = shopController.getPrices(applicationDate, 35455, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PricesDto price = response.getBody();
        assertNotNull(price);
        assertEquals(30.50, price.getPrice());
        assertEquals(3, price.getPriceList());
        assertEquals(35455, price.getProductId());
        assertEquals(1, price.getBrandId());
    }

    @Test
    void whenRequestingPriceAt9PMOn16June_thenReturnsPriceWithHighestPriority() {
        LocalDateTime applicationDate = parseDate("2020-06-16T21:00:00");
        ResponseEntity<PricesDto> response = shopController.getPrices(applicationDate, 35455, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PricesDto price = response.getBody();
        assertNotNull(price);
        assertEquals(38.95, price.getPrice());
        assertEquals(4, price.getPriceList());
        assertEquals(35455, price.getProductId());
        assertEquals(1, price.getBrandId());
    }
}
