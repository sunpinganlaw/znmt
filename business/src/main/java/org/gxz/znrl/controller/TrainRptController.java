package org.gxz.znrl.controller;

import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.entity.BaseEntity;
import org.gxz.znrl.entity.TrainWeightRptEntity;
import org.gxz.znrl.entity.WeightRptEntity;
import org.gxz.znrl.service.IRptService;
import org.gxz.znrl.service.ITrainRptService;
import org.gxz.znrl.viewModel.GridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by admin on 2014/5/9.
 */

@Controller
@RequestMapping("/business/trainReport")
@SuppressWarnings("unchecked")
public class TrainRptController extends BaseAction {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public ITrainRptService trainRptService;

    @RequestMapping(value="/weightRpt", method= RequestMethod.GET)
    public String gotoWeightRpt() {
        return "/report/TrainWeightRpt";
    }

    @RequestMapping(value="/weightRptList")
    @ResponseBody
    public GridModel qryWeightRptList(){
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity weightRptEntity = SearchForm(TrainWeightRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        weightRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = trainRptService.qryWeightRptList((TrainWeightRptEntity)weightRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    @RequestMapping(value="/weightSummaryRpt")
    @ResponseBody
    public TrainWeightRptEntity qryWeightSummaryRpt(){
        //获取界面输入参数
        TrainWeightRptEntity weightRptEntity = SearchForm(TrainWeightRptEntity.class);

        //查询
        TrainWeightRptEntity weightSummaryEntity = null;
        try {
            weightSummaryEntity = trainRptService.qryWeightSummaryRpt(weightRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weightSummaryEntity;
    }

    //websoceket测试
    @RequestMapping(value="/wstest", method= RequestMethod.GET)
    public String gotoWsTestPage() {
        return "/report/page";
    }

}
