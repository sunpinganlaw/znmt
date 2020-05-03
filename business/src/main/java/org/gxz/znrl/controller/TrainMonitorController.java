package org.gxz.znrl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.util.JacksonMapper;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.entity.*;
import org.gxz.znrl.service.IMonitorService;
import org.gxz.znrl.service.ITrainMonitorService;
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
import java.util.List;

/**
 * Created by xieyt on 2014/12/1.
 */

@Controller
@RequestMapping("/business/trainMonitor")
@SuppressWarnings("unchecked")
public class TrainMonitorController extends BaseAction {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public ITrainMonitorService trainMonitorService;

    //菜单跳转到称重监控页面
    @RequestMapping(value="/weight/list", method= RequestMethod.GET)
    public String gotoWeight() {
        return "/monitor/trainWeight";
    }

    //菜单跳转到采样监控页面
    @RequestMapping(value = "/sample/list", method = RequestMethod.GET)
    public String gotoTrainSample() {
        return "/monitor/trainSample";
    }

    //菜单跳转到制样页面
    @RequestMapping(value="/makesample/list", method= RequestMethod.GET)
    public String gotoMakeSample() {
        return "/monitor/trainMakesample";
    }

    //菜单跳转到全厂总览页面
    @RequestMapping(value="/overview/list", method= RequestMethod.GET)
    public String gotoOverview() {
        return "/monitor/trainOverview";
    }

    //菜单跳转到全厂总览页面-NHScada
    @RequestMapping(value="/overview/listNHScada", method= RequestMethod.GET)
    public String gotoOverviewNHScada() {
        return "/monitor/overviewNHScada";
    }

    //翻车机页面
    @RequestMapping(value="/cardumper/list", method= RequestMethod.GET)
    public String gotoCardumper() {
        return "/monitor/carDumper";
    }


    //菜单跳转到全厂总览页面(汽车火车综合,丰城用)
    @RequestMapping(value="/trainAndCarOverview/list", method= RequestMethod.GET)
    public String gotoTrainAndCarOverview() {
        return "/monitor/trainAndCarOverview";
    }

    //菜单跳转到采样监控页面(丰城用)
    @RequestMapping(value = "/sampleFc/list", method = RequestMethod.GET)
    public String gotoTrainSampleFc() {
        return "/monitor/sample";
    }

    //菜单跳转到制样监控页面(火车,丰城用)
    @RequestMapping(value="/makesampleFc/list", method= RequestMethod.GET)
    public String gotoMakeSampleFc() {
        return "/monitor/makesample";
    }

    //菜单跳转到制样监控页面(汽车,丰城用)
    @RequestMapping(value="/makesampleFc/list4car", method= RequestMethod.GET)
    public String gotoMakeSampleFc_car() {
        return "/monitor/makesample4car";
    }

    //菜单跳转到采样监控页面(九江用)
    @RequestMapping(value = "/sampleFcSy/list", method = RequestMethod.GET)
    public String gotoTrainSampleFcSy() {
        return "/monitor/sySample";
    }

    //查询称重监控明细信息
    @RequestMapping(value="/weightList")
    @ResponseBody
    public GridModel qryWeightList(){
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity weightEntity = new TrainWeightEntity();
        //设置分页信息并计算记录开始和结束值
        weightEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = trainMonitorService.qryWeightList((TrainWeightEntity)weightEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //翻车机信息
    @RequestMapping(value="/carDumperList")
    @ResponseBody
    public GridModel qryCarDumperList(){
        BaseEntity weightEntity = new TrainWeightEntity();
        weightEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = trainMonitorService.qryCarDumperList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询采样监控明细信息
    @RequestMapping(value = "/sampleList")
    @ResponseBody
    public GridModel qryTrainSampleList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity sampleEntity = new TrainWeightRptEntity();
        //设置分页信息并计算记录开始和结束值
        sampleEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = trainMonitorService.qrySampleList((TrainWeightRptEntity) sampleEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询制样结果动态信息
    @RequestMapping(value="/samplingList")
    @ResponseBody
    public GridModel qrySampling(){
        //查询
        GridModel gridModel = null;
        try {
            gridModel = trainMonitorService.qrySampling();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //提交控制设备命令
    @RequestMapping(value="/commitCtrlCmd")
    @ResponseBody
    @Transactional
    public Result commitCtrlCmd(){
        Result result = new Result();
        try {
            //获取界面输入参数
            CtrlEntity ctrlEntity = SearchForm(CtrlEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            ctrlEntity.setOpCode(opCode);

            //插入设备控制表
            trainMonitorService.commitCtrlCmd(ctrlEntity);

            result.setMsg("控制设备命令提交成功!");
            result.setSuccessful(true);
        } catch (Exception e) {
            result.setMsg("控制设备命令提交异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }

        return result;
    }

    //今日来煤信息汇总查询
    @RequestMapping(value="/todayArrivedCoalList")
    @ResponseBody
    public GridModel qryTodayArrivedCoal(){
        //查询
        GridModel gridModel = null;
        try {
            gridModel = trainMonitorService.qryTodayArrivedCoal();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //燃料指标
    @RequestMapping(value="/qryFuelIndicator")
    @ResponseBody
    public GridModel qryFuelIndicator(){
        //查询
        GridModel gridModel = null;
        try {
            gridModel = trainMonitorService.qryFuelIndicator();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询今日汽车动态信息
    @RequestMapping(value="/carDynamicList")
    @ResponseBody
    public GridModel qryCarDynamic(){
        //查询
        GridModel gridModel = null;
        try {
            gridModel = trainMonitorService.qryCarDynamic();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //火车过衡统计
    @RequestMapping(value="/qryTrainOveriew")
    @ResponseBody
    public GridModel qryTrainOveriew(){
        //查询
        GridModel gridModel = null;
        try {
            gridModel = trainMonitorService.qryTrainOveriew();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value="/qryTrainComeIn")
    @ResponseBody
    public GridModel qryTrainComeIn(){
        //查询
        GridModel gridModel = null;
        try {
            gridModel = trainMonitorService.qryTrainComeIn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //菜单跳转到入炉收集器监控页面(北仑使用)
    @RequestMapping(value="/inBoilerSample/list", method= RequestMethod.GET)
    public String gotoInBoilerSample() {
        return "/monitor/inBoilerSample";
    }

    //菜单跳转到入炉收集器监控页面(蚌埠使用)
    @RequestMapping(value="/inBoilerCY/list", method= RequestMethod.GET)
    public String gotoInBoilerCY() {
        return "/monitor/inBoilerCY";
    }
}
