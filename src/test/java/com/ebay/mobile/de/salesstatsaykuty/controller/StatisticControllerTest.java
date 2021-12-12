package com.ebay.mobile.de.salesstatsaykuty.controller;

import com.ebay.mobile.de.salesstatsaykuty.model.Statistic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StatisticControllerTest {

    @Autowired
    private StatisticController statisticController;

    @Test
    void get() {
        Statistic expected = new Statistic("0.0", "0.0");
        Statistic response = statisticController.get();
        assertEquals(expected.getAverageAmountPerOrder(), response.getAverageAmountPerOrder());
        assertEquals(expected.getTotalSalesAmount(), response.getTotalSalesAmount());
    }
}