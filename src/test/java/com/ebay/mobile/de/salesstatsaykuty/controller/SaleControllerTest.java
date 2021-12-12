package com.ebay.mobile.de.salesstatsaykuty.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SaleControllerTest {

    @Autowired
    private SaleController saleController;

    @Test
    void createWithParsable() {
        ResponseEntity expected = ResponseEntity.status(HttpStatus.ACCEPTED).build();
        ResponseEntity response = saleController.create("10.00");
        assertEquals(expected, response);
    }

    @Test
    void createWithNonParsable() {
        ResponseEntity expected = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        ResponseEntity response = saleController.create("NonParsable");
        assertEquals(expected, response);
    }
}