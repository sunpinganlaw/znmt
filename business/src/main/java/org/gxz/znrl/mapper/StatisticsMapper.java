package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xieyt on 16-9-27.
 */

@Repository
public interface StatisticsMapper {

    /**
     * 统计分析查询
     */
    public List<StatisticsEntity> qryStatisticsData(StatisticsEntity statisticsEntity);

    public List<StatisticsReportEntity> qryStatisticsReportData(StatisticsReportEntity statisticsReportEntity);

}
