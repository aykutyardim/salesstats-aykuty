package com.ebay.mobile.de.salesstatsaykuty.service.impl;

import com.ebay.mobile.de.salesstatsaykuty.service.SaleService;
import com.ebay.mobile.de.salesstatsaykuty.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private StatisticService statisticService;

    @Override
    @Async
    public void save(double salesAmount) {
        statisticService.update(salesAmount, Calendar.getInstance());
    }
}
