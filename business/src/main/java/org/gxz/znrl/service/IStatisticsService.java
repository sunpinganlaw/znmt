package org.gxz.znrl.service;

import org.gxz.znrl.entity.*;
import org.gxz.znrl.viewModel.GridModel;

import java.util.List;

/**
 * Created by xieyt on 16-9-27.
 */
public interface IStatisticsService {
    /**
     * 统计分析查询
     */
    public List<StatisticsEntity> qryStatisticsData(StatisticsEntity statisticsEntity);

    public List<StatisticsReportEntity> qryStatisticsReportData(StatisticsReportEntity statisticsReportEntity);

}
