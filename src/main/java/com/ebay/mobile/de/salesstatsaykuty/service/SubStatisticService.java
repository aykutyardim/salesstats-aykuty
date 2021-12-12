package com.ebay.mobile.de.salesstatsaykuty.service;

import com.ebay.mobile.de.salesstatsaykuty.service.dto.SubStatisticDto;

import java.util.Calendar;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface SubStatisticService {

    SubStatisticDto getLastMinute(ConcurrentLinkedQueue<SubStatisticDto> subStatisticDtos, Calendar calendar);
    void update(SubStatisticDto subStatisticDto, double amount, Calendar calendar);
}
