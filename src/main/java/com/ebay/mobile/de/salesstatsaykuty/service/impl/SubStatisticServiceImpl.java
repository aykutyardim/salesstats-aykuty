package com.ebay.mobile.de.salesstatsaykuty.service.impl;

import com.ebay.mobile.de.salesstatsaykuty.service.SubStatisticService;
import com.ebay.mobile.de.salesstatsaykuty.service.dto.SubStatisticDto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class SubStatisticServiceImpl implements SubStatisticService {


    @Override
    public SubStatisticDto getLastMinute(ConcurrentLinkedQueue<SubStatisticDto> subStatisticDtos, Calendar calendar) {

        // Total subStatistic for last min
        SubStatisticDto lastMinuteSubStatisticDto = new SubStatisticDto(calendar);

        // Filter and Calculate last min
        subStatisticDtos.stream().forEach(subStatisticDto -> {
            if(calendar.getTimeInMillis() - subStatisticDto.getLastUpdate().getTimeInMillis() < 1000 * 60){
                lastMinuteSubStatisticDto.setTotalAmount(lastMinuteSubStatisticDto.getTotalAmount() + subStatisticDto.getTotalAmount());
                lastMinuteSubStatisticDto.setOrderCount(lastMinuteSubStatisticDto.getOrderCount() + subStatisticDto.getOrderCount());
            }
        });

        // Return last min
        return lastMinuteSubStatisticDto;
    }

    @Override
    public void update(SubStatisticDto subStatisticDto, double amount, Calendar calendar){

        if (calendar.getTimeInMillis() - subStatisticDto.getLastUpdate().getTimeInMillis() < 1000){
            // Increase existing total amount and order count
            subStatisticDto.setTotalAmount(subStatisticDto.getTotalAmount() + amount);
            subStatisticDto.setOrderCount(subStatisticDto.getOrderCount() + 1);
        }
        else{
            // Expire amount and order count
            subStatisticDto.setTotalAmount(amount);
            subStatisticDto.setOrderCount(1);
        }
        // Update Last Update Time
        subStatisticDto.setLastUpdate(calendar);
    }
}