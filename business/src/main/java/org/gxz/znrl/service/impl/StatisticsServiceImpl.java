package org.gxz.znrl.service.impl;

import org.gxz.znrl.entity.*;
import org.gxz.znrl.mapper.StatisticsMapper;
import org.gxz.znrl.service.IStatisticsService;
import org.gxz.znrl.viewModel.GridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xieyt on 16-9-27.
 */
@Service("statisticsService")
@Transactional
@SuppressWarnings("unchecked")
public class StatisticsServiceImpl implements IStatisticsService {

    @Autowired
    public StatisticsMapper statisticsMapper;

    /**
     * 统计分析查询
     */
    public List<StatisticsEntity> qryStatisticsData(StatisticsEntity statisticsEntity){
        //查询本次页面的结果集
        List<StatisticsEntity> list = statisticsMapper.qryStatisticsData(statisticsEntity);

        return list;
    }

    public List<StatisticsReportEntity> qryStatisticsReportData(StatisticsReportEntity statisticsReportEntity){
        List<StatisticsReportEntity> list = statisticsMapper.qryStatisticsReportData(statisticsReportEntity);

        return list;
    }

}
