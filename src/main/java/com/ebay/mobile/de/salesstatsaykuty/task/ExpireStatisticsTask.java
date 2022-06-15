package com.ebay.mobile.de.salesstatsaykuty.task;

import com.ebay.mobile.de.salesstatsaykuty.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;

public class ExpireStatisticsTask {

    @Autowired
    private StatisticService statisticService;

    @Scheduled(fixedDelay = 1000)
    public void expireStatistics(){
        statisticService.expire(Calendar.getInstance());
    }
}
