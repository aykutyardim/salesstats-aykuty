package com.ebay.mobile.de.salesstatsaykuty.controller;

import com.ebay.mobile.de.salesstatsaykuty.mapper.StatisticMapper;
import com.ebay.mobile.de.salesstatsaykuty.model.Statistic;
import com.ebay.mobile.de.salesstatsaykuty.service.StatisticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticController {

    protected StatisticService service;
    protected StatisticMapper mapper;

    public StatisticController(StatisticService service, StatisticMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Statistic get(){
        return this.mapper.dtoToEntity(this.service.getAll());
    }

}
