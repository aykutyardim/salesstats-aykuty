package com.ebay.mobile.de.salesstatsaykuty.service;

import com.ebay.mobile.de.salesstatsaykuty.service.dto.StatisticDto;

import java.util.Calendar;

public interface StatisticService {

    StatisticDto getAll();
    void update(double amount, Calendar calendar);
}
