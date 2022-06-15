package com.ebay.mobile.de.salesstatsaykuty.service.impl;

import com.ebay.mobile.de.salesstatsaykuty.service.SubStatisticService;
import com.ebay.mobile.de.salesstatsaykuty.service.dto.SubStatisticDto;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class SubStatisticServiceImpl implements SubStatisticService {

    @Override
    public SubStatisticDto create(double amount, Calendar lastUpdate){
        return new SubStatisticDto(amount, lastUpdate);
    }

    @Override
    public boolean isExpired(SubStatisticDto subStatisticDto, Calendar calendar){
        return calendar.getTimeInMillis() - subStatisticDto.getLastUpdate().getTimeInMillis() > 1000 * 59;
    }

    @Override
    public void update(SubStatisticDto subStatisticDto, double amount, int orderCount, Calendar lastUpdate){
        subStatisticDto.setTotalAmount(amount);
        subStatisticDto.setOrderCount(orderCount);
        subStatisticDto.setLastUpdate(lastUpdate);
    }
}