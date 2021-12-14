package com.ebay.mobile.de.salesstatsaykuty.service.impl;

import com.ebay.mobile.de.salesstatsaykuty.SalesstatsAykutyApplication;
import com.ebay.mobile.de.salesstatsaykuty.service.dto.SubStatisticDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SalesstatsAykutyApplication.class)
class SubStatisticServiceImplTest {

    @Autowired
    private SubStatisticServiceImpl subStatisticService;

    private Calendar calendar;
    private double amount;
    private SubStatisticDto subStatisticDto;

    @BeforeEach
    void setUp() {
        this.amount = 10.00;
        this.calendar = Calendar.getInstance();
        this.subStatisticDto = new SubStatisticDto(amount, calendar);
    }

    @Test
    void shouldCreate() {

        // run
        SubStatisticDto expected = subStatisticDto;
        SubStatisticDto actual = subStatisticService.create(amount, calendar);

        // test
        assertEquals(expected.getOrderCount(), actual.getOrderCount());
        assertEquals(expected.getTotalAmount(), actual.getTotalAmount());
        assertEquals(expected.getLastUpdate(), actual.getLastUpdate());
    }

    @Test
    void shouldExpire() {

        // given
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(calendar.getTimeInMillis() + 1000 * 60);

        // run
        boolean actual = subStatisticService.isExpired(subStatisticDto, now);

        // test
        assertEquals(true, actual);
    }

    @Test
    void shouldNotExpire() {

        // run
        boolean actual = subStatisticService.isExpired(subStatisticDto, calendar);

        // test
        assertEquals(false, actual);
    }

    @Test
    void shouldUpdate() {

        // given
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(calendar.getTimeInMillis() + 1000 * 60);

        // run
        SubStatisticDto expected = new SubStatisticDto(20.00, 2, now);
        subStatisticService.update(subStatisticDto, 20.0, 2, now);

        // test
        assertEquals(expected.getOrderCount(), subStatisticDto.getOrderCount());
        assertEquals(expected.getTotalAmount(), subStatisticDto.getTotalAmount());
        assertEquals(expected.getLastUpdate(), subStatisticDto.getLastUpdate());
    }
}