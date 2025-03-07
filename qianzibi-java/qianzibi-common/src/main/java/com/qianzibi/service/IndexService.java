package com.qianzibi.service;

import com.qianzibi.entity.dto.StatisticsDataDto;
import com.qianzibi.entity.dto.StatisticsDataWeekDto;

import java.util.List;

public interface IndexService {
    List<StatisticsDataDto> getAllData();

    StatisticsDataWeekDto getAppWeekData();

    StatisticsDataWeekDto getContentWeekData();

}
