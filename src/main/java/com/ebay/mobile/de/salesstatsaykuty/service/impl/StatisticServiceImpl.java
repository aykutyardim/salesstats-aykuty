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

    @Autowired
    private SubStatisticService subStatisticService;

    protected ConcurrentLinkedQueue<SubStatisticDto> subStatisticDtos;

    public StatisticServiceImpl() {
        this.subStatisticDtos = new ConcurrentLinkedQueue<SubStatisticDto>();
    }

    @Override
    public StatisticDto getAll(){
        SubStatisticDto subStatisticDto = subStatisticService.getLastMinute(this.subStatisticDtos, Calendar.getInstance());
        Double average = subStatisticDto.getTotalAmount() / subStatisticDto.getOrderCount();
        return new StatisticDto(subStatisticDto.getTotalAmount(), Double.isNaN(average) ? 0.0 : average);
    }

    @Override
    public void update(double amount, Calendar calendar){

        // Get existing subStatistic by second index
        SubStatisticDto subStatisticDto = this.subStatisticDtos.stream()
                .filter(currentSubStatisticDto -> currentSubStatisticDto.getLastUpdate().get(Calendar.SECOND) == calendar.get(Calendar.SECOND))
                .findFirst()
                .orElse(null);

        // Update existing subStatisticDto
        if (subStatisticDto != null){
            this.subStatisticService.update(subStatisticDto, amount, calendar);
        }

        // Create and add subStatisticDto
        else{
            this.subStatisticDtos.add(new SubStatisticDto(amount, calendar));
        }
    }
}
