package com.ebay.mobile.de.salesstatsaykuty.service;

import com.ebay.mobile.de.salesstatsaykuty.service.dto.StatisticDto;
import com.ebay.mobile.de.salesstatsaykuty.service.dto.SubStatisticDto;

import java.util.Calendar;

public interface StatisticService {
    void expire(Calendar calendar);
    StatisticDto get();
    void updateWhenExpired(SubStatisticDto subStatisticDto);
    void updateWhenSold(double amount, Calendar calendar);
}
