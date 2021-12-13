package com.ebay.mobile.de.salesstatsaykuty.service;

import com.ebay.mobile.de.salesstatsaykuty.service.dto.SubStatisticDto;

import java.util.Calendar;

public interface SubStatisticService {
    SubStatisticDto create(double amount, Calendar lastUpdate);
    boolean isExpired(SubStatisticDto subStatisticDto, Calendar calendar);
    void update(SubStatisticDto subStatisticDto, double amount, int orderCount, Calendar lastUpdate);
}
