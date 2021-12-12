package com.ebay.mobile.de.salesstatsaykuty.mapper;

import com.ebay.mobile.de.salesstatsaykuty.model.Statistic;
import com.ebay.mobile.de.salesstatsaykuty.service.dto.StatisticDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class StatisticMapper {
    public abstract Statistic dtoToEntity(StatisticDto statisticDto);
}