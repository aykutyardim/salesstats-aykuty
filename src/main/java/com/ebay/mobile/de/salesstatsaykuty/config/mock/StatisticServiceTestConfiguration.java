package com.ebay.mobile.de.salesstatsaykuty.config.mock;

import com.ebay.mobile.de.salesstatsaykuty.service.StatisticService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("saleTest")
public class StatisticServiceTestConfiguration {

    @Bean
    @Primary
    public StatisticService statisticService(){
        return Mockito.mock(StatisticService.class);
    }
}
