package org.gxz.znrl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.util.JacksonMapper;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.entity.*;
import org.gxz.znrl.service.ILaboryService;
import org.gxz.znrl.service.IStatisticsService;
import org.gxz.znrl.shiro.ShiroUser;
import org.gxz.znrl.viewModel.GridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xieyt on 16-9-27.
 * 统计分析后台controller入口
 */

@Controller
@RequestMapping("/business/statistics")
@SuppressWarnings("unchecked")
public class StatisticsController extends BaseAction {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public IStatisticsService statisticsService;

    //化验结果原始数据页面
    @RequestMapping(value="/demo", method= RequestMethod.GET)
    public String gotoDemoPage() {
        return "/statistics/demo";
    }

    //自动化工作量统计报表页面
    @RequestMapping(value="/autoWorkLoadStatistics", method= RequestMethod.GET)
    public String gotoAutoWorkLoadStatisticsPage() {
        return "/statistics/autoWorkLoadStatistics";
    }

    //设备投运率报表报表页面
    @RequestMapping(value="/autoWorkRateStatistics", method= RequestMethod.GET)
    public String gotAutoWorkRateStatisticsPage() {
        return "/statistics/autoWorkRateStatistics";
    }

    //检斤检质率统计报表页面
    @RequestMapping(value="/checkRateStatistics", method= RequestMethod.GET)
    public String gotocheckRateStatisticsPage() {
        return "/statistics/checkRateStatistics";
    }

    //检斤检质率统计报表页面
    @RequestMapping(value="/equipmentCheckRateStatistics", method= RequestMethod.GET)
    public String gotoEquipmentCheckRateStatisticsPage() {
        return "/statistics/equipmentCheckRateStatistics";
    }

    //存查样抽检统计报表页面
    @RequestMapping(value="/sampleCheckStatistics", method= RequestMethod.GET)
    public String gotoSampleCheckStatisticsPage() {
        return "/statistics/sampleCheckStatistics";
    }

    //入厂入炉热值统计报表页面
    @RequestMapping(value="/coalInAndBurnHotStatistics", method= RequestMethod.GET)
    public String gotoCoalInAndBurnHotStatisticsPage() {
        return "/statistics/coalInAndBurnHotStatistics";
    }

    //入厂来煤量统计报表页面
    @RequestMapping(value="/coalInStatistics", method= RequestMethod.GET)
    public String gotoCoalInStatisticsPage() {
        return "/statistics/coalInStatistics";
    }

    //入炉上煤量统计报表页面
    @RequestMapping(value="/coalBurnStatistics", method= RequestMethod.GET)
    public String gotoCoalBurnStatisticsPage() {
        return "/statistics/coalBurnStatistics";
    }

    //统计分析查询
    @RequestMapping(value="/qryStatisticsData")
    @ResponseBody
    public List qryStatisticsData(){
        StatisticsEntity statisticsEntity = SearchForm(StatisticsEntity.class);

        List<StatisticsEntity> list = null;
        try {
            list = statisticsService.qryStatisticsData(statisticsEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //月度综合统计报表
    @RequestMapping(value="/multipleStatistics", method= RequestMethod.GET)
    public String gotoMultipleStatisticsPage() {
        return "/statistics/multipleStatistics";
    }

    //月度综合统计报表
    @RequestMapping(value="/multipleStatisticsReport", method= RequestMethod.GET)
    public String gotoMultipleStatisticsReportPage() {
        return "/statistics/multipleStatisticsReport";
    }

    //统计分析查询
    @RequestMapping(value="/qryStatisticsReportData")
    @ResponseBody
    public List qryStatisticsReportData(){
        StatisticsReportEntity statisticsEntity = SearchForm(StatisticsReportEntity.class);

        List<StatisticsReportEntity> list = null;
        try {
            list = statisticsService.qryStatisticsReportData(statisticsEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
