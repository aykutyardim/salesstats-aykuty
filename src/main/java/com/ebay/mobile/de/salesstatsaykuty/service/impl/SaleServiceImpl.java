package com.ebay.mobile.de.salesstatsaykuty.service.impl;

import com.ebay.mobile.de.salesstatsaykuty.service.SaleService;
import com.ebay.mobile.de.salesstatsaykuty.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class SaleServiceImpl implements SaleService {

    private StatisticService statisticService;

    @Autowired
    public SaleServiceImpl(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Override
    @Async
    public void save(double salesAmount) {
        statisticService.updateWhenSold(salesAmount, Calendar.getInstance());
    }
}
