package com.ebay.mobile.de.salesstatsaykuty.service.impl;

import com.ebay.mobile.de.salesstatsaykuty.service.StatisticService;
import com.ebay.mobile.de.salesstatsaykuty.service.SubStatisticService;
import com.ebay.mobile.de.salesstatsaykuty.service.dto.StatisticDto;
import com.ebay.mobile.de.salesstatsaykuty.service.dto.SubStatisticDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class StatisticServiceImpl implements StatisticService {

    private SubStatisticService subStatisticService;
    ConcurrentLinkedQueue<SubStatisticDto> subStatisticDtos;
    SubStatisticDto finalSubStatisticDto;

    @Autowired
    public StatisticServiceImpl(SubStatisticService subStatisticService) {
        this.subStatisticDtos = new ConcurrentLinkedQueue<SubStatisticDto>();
        this.finalSubStatisticDto = new SubStatisticDto(Calendar.getInstance());
        this.subStatisticService = subStatisticService;
    }

    @Override
    public void expire(Calendar calendar){
        this.subStatisticDtos.stream().forEach(subStatisticDto -> {
            if (subStatisticDto.getTotalAmount() > 0.0 && subStatisticService.isExpired(subStatisticDto, calendar)){
                this.updateWhenExpired(subStatisticDto);
            }
        });
    }

    @Override
    public StatisticDto get(){
        double average = finalSubStatisticDto.getTotalAmount() / finalSubStatisticDto.getOrderCount();
        return new StatisticDto(finalSubStatisticDto.getTotalAmount(), Double.isNaN(average) ? 0.0 : average);
    }

    @Override
    public void updateWhenExpired(SubStatisticDto subStatisticDto){
        // update final sub statistic
        subStatisticService.update(
                finalSubStatisticDto,
                finalSubStatisticDto.getTotalAmount() - subStatisticDto.getTotalAmount(),
                finalSubStatisticDto.getOrderCount() - subStatisticDto.getOrderCount(),
                finalSubStatisticDto.getLastUpdate());
        // update sub statistic in queue
        subStatisticService.update(subStatisticDto, 0.0, 0, subStatisticDto.getLastUpdate());

    }

    @Override
    public void updateWhenSold(double amount, Calendar calendar){

        // Get existing sub statistic if matches with second
        SubStatisticDto subStatisticDto = this.subStatisticDtos.stream()
                .filter(currentSubStatisticDto -> currentSubStatisticDto.getLastUpdate().get(Calendar.SECOND) == calendar.get(Calendar.SECOND))
                .findFirst()
                .orElse(null);

        // Create new sub statistic and add queue if no match with second
        if (subStatisticDto == null){
            subStatisticDto = subStatisticService.create(amount, calendar);
            this.subStatisticDtos.add(subStatisticDto);
        }
        else{
            // Check existing sub statistic
            if (subStatisticService.isExpired(subStatisticDto, calendar)){
                this.updateWhenExpired(subStatisticDto);
            }
            // Update sub statistic
            subStatisticService.update(
                    subStatisticDto,
                    subStatisticDto.getTotalAmount() + amount,
                    subStatisticDto.getOrderCount() + 1,
                    calendar);
        }
        // update final sub statistic
        subStatisticService.update(
                finalSubStatisticDto,
                finalSubStatisticDto.getTotalAmount() + amount,
                finalSubStatisticDto.getOrderCount() + 1,
                calendar);
    }
}
