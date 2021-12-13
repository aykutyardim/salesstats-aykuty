package com.ebay.mobile.de.salesstatsaykuty.config;

import com.ebay.mobile.de.salesstatsaykuty.task.ExpireStatisticsTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulingConfiguration {

    @Bean
    public ExpireStatisticsTask expireStatisticsTask(){
        return new ExpireStatisticsTask();
    }
}