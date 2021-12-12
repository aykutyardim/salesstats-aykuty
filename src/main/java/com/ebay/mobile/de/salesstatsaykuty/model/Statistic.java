package com.ebay.mobile.de.salesstatsaykuty.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Statistic {

    @JsonProperty(value = "total_sales_amount")
    private String totalSalesAmount;

    @JsonProperty(value = "average_amount_per_order")
    private String averageAmountPerOrder;
}
