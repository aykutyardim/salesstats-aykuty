package com.ebay.mobile.de.salesstatsaykuty.service.impl;

import com.ebay.mobile.de.salesstatsaykuty.SalesstatsAykutyApplication;
import com.ebay.mobile.de.salesstatsaykuty.service.SubStatisticService;
import com.ebay.mobile.de.salesstatsaykuty.service.dto.StatisticDto;
import com.ebay.mobile.de.salesstatsaykuty.service.dto.SubStatisticDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("statisticTest")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SalesstatsAykutyApplication.class)
class StatisticServiceImplTest {

    @Autowired
    private StatisticServiceImpl service;

    @Autowired
    private SubStatisticService subStatisticService;

    private SubStatisticDto finalSubStatisticDto;
    private SubStatisticDto subStatisticDto;
    private ConcurrentLinkedQueue<SubStatisticDto> subStatisticDtos;
    private Calendar calendar;
    private double amount;

    @BeforeEach
    void setUp() {
        this.amount = 10.00;
        this.calendar = Calendar.getInstance();

        this.subStatisticDto = new SubStatisticDto(this.amount, 1, calendar);
        this.finalSubStatisticDto = new SubStatisticDto(this.amount, 1, calendar);
        this.subStatisticDtos = new ConcurrentLinkedQueue<SubStatisticDto>();
        this.subStatisticDtos.add(subStatisticDto);

        service.finalSubStatisticDto = this.finalSubStatisticDto;
        service.subStatisticDtos = this.subStatisticDtos;
    }

    @Test
    void shouldGet() {

        // run
        StatisticDto expected = new StatisticDto(amount, amount);
        StatisticDto actual = service.get();

        // test
        assertEquals(expected.getAverageAmountPerOrder(), actual.getAverageAmountPerOrder());
    }

    @Test
    void shouldNotUpdateFinalSubStatisticDtoWhenNotExpired() {

        // when
        when(subStatisticService.isExpired(subStatisticDto, calendar)).thenReturn(false);
        doNothing().when(subStatisticService).update(subStatisticDto, 0.0, 0, calendar);
        doNothing().when(subStatisticService).update(finalSubStatisticDto, 0.0, 0, calendar);

        // run
        service.expire(calendar);

        // test
        verify(subStatisticService, times(0)).update(finalSubStatisticDto, 0.0, 0, calendar);
    }

    @Test
    void shouldUpdateFinalSubStatisticDtoWhenExpired() {

        // when
        when(subStatisticService.isExpired(subStatisticDto, calendar)).thenReturn(true);
        doNothing().when(subStatisticService).update(subStatisticDto, 0.0, 0, calendar);
        doNothing().when(subStatisticService).update(finalSubStatisticDto, 0.0, 0, calendar);

        // run
        service.expire(calendar);

        // test
        verify(subStatisticService, times(1)).update(finalSubStatisticDto, 0.0, 0, calendar);
    }

    @Test
    void shouldUpdateQueueWhenSoldIfNotMatch(){

        // given
        Calendar saleTime = Calendar.getInstance();
        saleTime.setTimeInMillis(calendar.getTimeInMillis() + 1000);

        // when
        when(subStatisticService.create(amount, saleTime)).thenReturn(subStatisticDto);
        doNothing().when(subStatisticService).update(finalSubStatisticDto, 20.00, 2, saleTime);

        // run
        service.updateWhenSold(amount, saleTime);

        // test
        assertEquals(2, service.subStatisticDtos.size());
    }

    @Test
    void shouldUpdateFinalSubStatisticDtoWhenSoldIfNotMatch(){

        // given
        Calendar saleTime = Calendar.getInstance();
        saleTime.setTimeInMillis(calendar.getTimeInMillis() + 1000);

        // when
        when(subStatisticService.create(amount, saleTime)).thenReturn(subStatisticDto);
        doNothing().when(subStatisticService).update(finalSubStatisticDto, 20.00, 2, saleTime);

        // run
        service.updateWhenSold(amount, saleTime);

        // test
        verify(subStatisticService, times(1)).update(finalSubStatisticDto, 20.00, 2, saleTime);
    }

    @Test
    void shouldExpireFinalSubStatisticDtoWhenSoldIfMatchAndExpire(){

        // given
        Calendar saleTime = Calendar.getInstance();
        saleTime.setTimeInMillis(calendar.getTimeInMillis() + 1000 * 60);

        // when
        when(subStatisticService.isExpired(subStatisticDto, saleTime)).thenReturn(true);
        doNothing().when(subStatisticService).update(subStatisticDto, 0.0, 0, calendar);
        doNothing().when(subStatisticService).update(finalSubStatisticDto, 0.0, 0, calendar);
        doNothing().when(subStatisticService).update(subStatisticDto, 20.00, 2, saleTime);
        doNothing().when(subStatisticService).update(finalSubStatisticDto, 20.00, 2, saleTime);

        // run
        service.updateWhenSold(amount, saleTime);

        // test
        verify(subStatisticService, times(1)).update(finalSubStatisticDto, 0.0, 0, calendar);
    }

    @Test
    void shouldNotExpireFinalSubStatisticDtoWhenSoldIfMatchAndNotExpire(){

        // when
        when(subStatisticService.isExpired(subStatisticDto, calendar)).thenReturn(false);
        doNothing().when(subStatisticService).update(subStatisticDto, 0.0, 0, calendar);
        doNothing().when(subStatisticService).update(finalSubStatisticDto, 0.0, 0, calendar);
        doNothing().when(subStatisticService).update(subStatisticDto, 20.00, 2, calendar);
        doNothing().when(subStatisticService).update(finalSubStatisticDto, 20.00, 2, calendar);

        // run
        service.updateWhenSold(amount, calendar);

        // test
        verify(subStatisticService, times(0)).update(finalSubStatisticDto, 0.0, 0, calendar);
    }

    @Test
    void shouldUpdateFinalSubStatisticDtoWhenSoldIfMatchAndExpire(){

        // given
        Calendar saleTime = Calendar.getInstance();
        saleTime.setTimeInMillis(calendar.getTimeInMillis() + 1000 * 60);

        // when
        when(subStatisticService.isExpired(subStatisticDto, saleTime)).thenReturn(true);
        doNothing().when(subStatisticService).update(subStatisticDto, 0.0, 0, calendar);
        doNothing().when(subStatisticService).update(finalSubStatisticDto, 0.0, 0, calendar);
        doNothing().when(subStatisticService).update(subStatisticDto, 20.00, 2, saleTime);
        doNothing().when(subStatisticService).update(finalSubStatisticDto, 20.00, 2, saleTime);

        // run
        service.updateWhenSold(amount, saleTime);

        // test
        verify(subStatisticService, times(1)).update(finalSubStatisticDto, 20.0, 2, saleTime);
    }

    @Test
    void shouldUpdateFinalSubStatisticDtoWhenSoldIfMatchAndNotExpire(){

        // given
        Calendar saleTime = Calendar.getInstance();
        saleTime.setTimeInMillis(calendar.getTimeInMillis() + 1000 * 60);

        // when
        when(subStatisticService.isExpired(subStatisticDto, saleTime)).thenReturn(false);
        doNothing().when(subStatisticService).update(subStatisticDto, 0.0, 0, calendar);
        doNothing().when(subStatisticService).update(finalSubStatisticDto, 0.0, 0, calendar);
        doNothing().when(subStatisticService).update(subStatisticDto, 20.00, 2, saleTime);
        doNothing().when(subStatisticService).update(finalSubStatisticDto, 20.00, 2, saleTime);

        // run
        service.updateWhenSold(amount, saleTime);

        // test
        verify(subStatisticService, times(1)).update(finalSubStatisticDto, 20.0, 2, saleTime);
    }

}