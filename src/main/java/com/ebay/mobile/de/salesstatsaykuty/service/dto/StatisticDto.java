package com.ebay.mobile.de.salesstatsaykuty.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDto {

    private Double totalSalesAmount;
    private Double averageAmountPerOrder;
}