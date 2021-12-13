package com.ebay.mobile.de.salesstatsaykuty.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@AllArgsConstructor
public class SubStatisticDto {

    private Double totalAmount;
    private Integer orderCount;
    private Calendar lastUpdate;

    // Constructor for statistic service init
    public SubStatisticDto(Calendar calendar) {
        this.totalAmount = 0.0;
        this.orderCount = 0;
        this.lastUpdate = calendar;
    }

    // Constructor for post sale
    public SubStatisticDto(Double totalAmount, Calendar calendar) {
        this.totalAmount = totalAmount;
        this.orderCount = 1;
        this.lastUpdate = calendar;
    }
}
