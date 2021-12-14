package com.ebay.mobile.de.salesstatsaykuty.config.mock;

import com.ebay.mobile.de.salesstatsaykuty.service.SubStatisticService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("statisticTest")
@Configuration
public class SubStatisticServiceTestConfiguration {

    @Bean
    @Primary
    public SubStatisticService subStatisticService(){
        return Mockito.mock(SubStatisticService.class);
    }
}
