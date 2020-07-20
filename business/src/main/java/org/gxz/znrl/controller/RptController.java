package org.gxz.znrl.controller;

import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.util.JacksonMapper;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.entity.*;
import org.gxz.znrl.service.IRptService;
import org.gxz.znrl.shiro.SecurityUtils;
import org.gxz.znrl.shiro.ShiroUser;
import org.gxz.znrl.socket.WebSocketServer;
import org.gxz.znrl.viewModel.GridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2014/5/9.
 */

@Controller
@RequestMapping("/business/report")
@SuppressWarnings("unchecked")
public class RptController extends BaseAction {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public IRptService rptService;

    @RequestMapping(value = "/weightRpt", method = RequestMethod.GET)
    public String gotoWeightRpt() {
        return "/report/weightRpt";
    }

    @RequestMapping(value = "/weightRptPrint", method = RequestMethod.GET)
    public String gotoWeightRptPrint() {
        return "/report/weightRptPrint";
    }

    //大武口 厂内倒运煤需要打印当日计量明细
    @RequestMapping(value = "/gotoPrintCarsWeightPage4DailyCN/{beginTime}")
    @ResponseBody
    public ModelAndView gotoPrintCarsWeightPage4DailyCN(@PathVariable String beginTime) {
        ModelAndView mav = new ModelAndView("/report/weightRptPrintList4DailyCN");
        mav.addObject("beginTime", beginTime);

        return mav;
    }

    //宣威 跳转到 一天的案批次进行统计打印的页面 xxs20180330
    @RequestMapping(value = "/weightRptPrint4CarBatch", method = RequestMethod.GET)
    public String gotoWeightRptPrint4CarBatch() {
        return "/report/weightRptPrint4CarBatch";
    }

    @RequestMapping(value = "/trainWeightRpt", method = RequestMethod.GET)
    public String gotoTrainWeightRpt() {
        return "/report/trainWeightRpt";
    }

    @RequestMapping(value = "/shipWeightRpt", method = RequestMethod.GET)
    public String gotoShipWeightRpt() {
        return "/report/shipWeightRpt";
    }

    @RequestMapping(value = "/sampleRpt", method = RequestMethod.GET)
    public String gotoSampleRpt() {
        return "/report/sampleRpt";
    }

    //车辆批次合并查询页面基本同-采样结果查询
    @RequestMapping(value = "/mergeCarBatch", method = RequestMethod.GET)
    public String mergeCarBatch() {
        return "/report/mergeCarBatch";
    }

    @RequestMapping(value = "/samplingRpt", method = RequestMethod.GET)
    public String gotoSamplingRpt() {
        return "/report/samplingRpt";
    }

    @RequestMapping(value = "/samplingRpt4CCY", method = RequestMethod.GET)
    public String gotoSamplingRpt4CCY() {
        return "/report/samplingRpt4CCY";
    }

    @RequestMapping(value = "/gotoSamplingResRec", method = RequestMethod.GET)
    public String gotoSamplingResRec() {
        return "/report/editSamplingResRecord";
    }

    @RequestMapping(value = "/gotoPrintSamplingResRec", method = RequestMethod.GET)
    public String gotoPrintSamplingResRec() {
        return "/report/printSamplingResRecord";
    }

    @RequestMapping(value = "/samplingRptView", method = RequestMethod.GET)
    public String gotoSamplingRptView() {
        return "/report/samplingRptView";
    }

    //称重清单，copy谢 begin
    @RequestMapping(value = "/weightList", method = RequestMethod.GET)
    public String gotoWeightList() {
        return "/report/weightList";
    }

    //称重清单，copy谢 begin
    @RequestMapping(value = "/weightListKd", method = RequestMethod.GET)
    public String gotoWeightListKd() {
        return "/report/weightListKd";
    }


    @RequestMapping(value = "/sampleUsedRateRpt", method = RequestMethod.GET)
    public String gotoSampleUsedRateRpt() {
        return "/report/sampleUsedRateRpt";
    }


    //单独录入票重页面 宣威
    @RequestMapping(value = "/weightListTickQty", method = RequestMethod.GET)
    public String gotoWeightListTickQty() {
        return "/report/weightListTickQty";
    }


    //扣吨窗口
    @RequestMapping(value = "/kdQtyWin")
    public String gotoKdQtyWin() {
        return "/report/kdQty";
    }

    //打印火车计量单
    @RequestMapping(value = "/gotoPrintTrainsWeight", method = RequestMethod.GET)
    public String gotoPrintTrainsWeight() {
        return "/report/printTrainsWeight";
    }

    //系统编码解码
    @RequestMapping(value = "/desCode", method = RequestMethod.GET)
    public String gotoDesCode() {
        return "/report/desCode";
    }

    //系统编码解码 分样单4zj xxs20170816
    @RequestMapping(value = "/desCodeFyd", method = RequestMethod.GET)
    public String gotoDesCodeFyd() {
        return "/report/desCodeFyd";
    }


    //跳转到打印计量单
    @RequestMapping(value = "/gotoPrintTrainsWeightPage/{batchNo}")
    @ResponseBody
    public ModelAndView gotoPrintTrainsWeightPage(@PathVariable String batchNo) {
        ModelAndView mav = new ModelAndView("/report/printTrainsWeightList");
        mav.addObject("batchNo", batchNo);
        return mav;
    }


    //跳转到打印计量单
    @RequestMapping(value = "/gotoPrintCarsWeightPage/{batchNo}")
    @ResponseBody
    public ModelAndView gotoPrintCarsWeightPage(@PathVariable String batchNo) {
        ModelAndView mav = new ModelAndView("/report/weightRptPrintList");
        mav.addObject("batchNo", batchNo);

        return mav;
    }

    //跳转到打印计量单
    @RequestMapping(value = "/gotoPrintCarsWeightPage4Daily/{beginTime}")
    @ResponseBody
    public ModelAndView gotoPrintCarsWeightPage4Daily(@PathVariable String beginTime) {
        ModelAndView mav = new ModelAndView("/report/weightRptPrintList4Daily");
        mav.addObject("beginTime", beginTime);

        return mav;
    }

    //跳转到打印火车计量单  宣威 2018/07/10
    @RequestMapping(value = "/gotoPrintTransWeightPage4Daily/{beginTime}")
    @ResponseBody
    public ModelAndView gotoPrintTransWeightPage4Daily(@PathVariable String beginTime) {
        ModelAndView mav = new ModelAndView("/report/weightRptPrintTrainList4Daily");
        mav.addObject("beginTime", beginTime);
        return mav;
    }


    /**
     * 跳转到打印计量单月报for hpn
     * 哈平南
     *
     * @author zhoulu
     * 2017-06-01 13：26
     */
    @RequestMapping(value = "/gotoPrintCarsWeightPageSum")
    @ResponseBody
    public ModelAndView gotoPrintCarsWeightPageSum(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/report/weightRptPrintListSum");
        String beginTime = request.getParameter("beginTime") == null ? "" : request.getParameter("beginTime");
        String endTime = request.getParameter("endTime") == null ? "" : request.getParameter("endTime");
        String venNo = request.getParameter("venNo") == null ? "" : request.getParameter("venNo");
        mav.addObject("beginTime", beginTime);
        mav.addObject("endTime", endTime);
        mav.addObject("venNo", venNo);
        return mav;
    }

    /**
     * 直接调用打印汽车过卡记录
     * 码头
     *
     * @author zhoulu
     * 2017-06-01 13：26
     */
    @RequestMapping(value = "/gotoPrintCarsWeightPagemt")
    @ResponseBody
    public ModelAndView gotoPrintCarsWeightPagemt(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/report/weightRptPrintList");
        String beginTime = request.getParameter("beginTime") == null ? "" : request.getParameter("beginTime");
        String endTime = request.getParameter("endTime") == null ? "" : request.getParameter("endTime");
        String venNo = request.getParameter("venNo") == null ? "" : request.getParameter("venNo");
        String carId= request.getParameter("carId") == null ? "" : request.getParameter("carId");
        String shipId= request.getParameter("shipId") == null ? "" : request.getParameter("shipId");
        String customerNo= request.getParameter("customerNo") == null ? "" : request.getParameter("customerNo");
        String dateType= request.getParameter("dateType") == null ? "" : request.getParameter("dateType");
        String tickNo= request.getParameter("tickNo") == null ? "" : request.getParameter("tickNo");
        mav.addObject("beginTime", beginTime);
        mav.addObject("endTime", endTime);
        mav.addObject("venNo", venNo);
        mav.addObject("carId", carId);
        mav.addObject("shipId", shipId);
        mav.addObject("customerNo", customerNo);
        mav.addObject("dateType", dateType);
        mav.addObject("tickNo", tickNo);
        return mav;
    }
    //查询汽车来煤信息
    @RequestMapping(value = "/gotoPrintCarsWeightPagemtnew")
    @ResponseBody
    public ModelAndView gotoPrintCarsWeightPagemtnew() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity carTransRecordEntity = SearchForm(CarTransRecordEntity.class);
        ModelAndView mav = new ModelAndView("/report/weightRptPrintListSum");

        return mav;
    }


    //页面跳转到采样机集样罐记录表（织金）
    @RequestMapping(value = "/sampleBarrelRpt", method = RequestMethod.GET)
    public String gotoSampleBarrelRpt() {
        return "/report/sampleBarrelRecord";
    }

    //修改
    @RequestMapping(value = "/kdQty/mod")
    @ResponseBody
    public Result editKdQty(@ModelAttribute KdQtyEntity kdQtyEntity) {
        Result result = new Result();
        try {
            float i = Float.parseFloat(kdQtyEntity.getKsQty());
            float j = Float.parseFloat(kdQtyEntity.getKgQty());
            kdQtyEntity.setKdQty(String.valueOf(i + j));
            rptService.modifyKdQty(kdQtyEntity);
            result.setSuccessful(true);
            result.setMsg("更新重量信息成功！");
        } catch (RuntimeException e) {
            result.setSuccessful(false);
            result.setMsg("更新重量信息失败！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/measureData/mod")//源于editKdQty
    @ResponseBody
    public Result editMeasureData(@ModelAttribute MeasureDataEntity measureDataEntity) {
        Result result = new Result();
        try {
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            measureDataEntity.setOpCode(opCode);
            rptService.modifyMeasureData(measureDataEntity);
            if (measureDataEntity.getResult() != null && "1".equals(measureDataEntity.getResult())) {
                result.setSuccessful(true);
                result.setMsg(measureDataEntity.getResultInfo());
            } else {
                result.setSuccessful(false);
                result.setMsg(measureDataEntity.getResultInfo());
            }
        } catch (RuntimeException e) {
            result.setSuccessful(false);
            result.setMsg("更新计量信息失败！");
            e.printStackTrace();
        }
        return result;
    }

    //织金扣吨操作比较复杂，专门用于记录处理扣吨操作。
    @RequestMapping(value = "/kdOperate")
    @ResponseBody
    public Result kdOperate(@ModelAttribute MeasureDataEntity measureDataEntity) {
        Result result = new Result();
        try {
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            measureDataEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(measureDataEntity);
            measureDataEntity.setJsonStr(json);

            rptService.kdOperate(measureDataEntity);
            if (measureDataEntity.getResCode() != null && "0".equals(measureDataEntity.getResCode())) {
                result.setSuccessful(true);
                result.setMsg("操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg(measureDataEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("扣吨操作失败！");
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/kdQty/mod/{recordNo}")
    @ResponseBody
    public ModelAndView qryKdQty(@PathVariable String recordNo) {
        ModelAndView mav = new ModelAndView("/report/kdQty");

        try {
            TruckFuelUnloadEntity truckFuelUnloadEntity = new TruckFuelUnloadEntity();

            //查询汽车重量信息
            TruckFuelEntranceRecordEntity truckFuelEntranceRecordEntity = new TruckFuelEntranceRecordEntity();
            truckFuelEntranceRecordEntity.setRecordNo(recordNo);
            truckFuelEntranceRecordEntity = rptService.qryTruckFuelEntranceRecord(truckFuelEntranceRecordEntity);
            //整合
            truckFuelUnloadEntity.setMzQty(truckFuelEntranceRecordEntity.getMzQty());
            truckFuelUnloadEntity.setPzQty(truckFuelEntranceRecordEntity.getPzQty());
            truckFuelUnloadEntity.setTickQty(truckFuelEntranceRecordEntity.getTickQty());
            truckFuelUnloadEntity.setKdQty(truckFuelEntranceRecordEntity.getKdQty());
            truckFuelUnloadEntity.setCarId(truckFuelEntranceRecordEntity.getCarId());
            truckFuelUnloadEntity.setKdBak(truckFuelEntranceRecordEntity.getKdBak());
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(truckFuelUnloadEntity);
            mav.addObject("message", "success");
            mav.addObject("truckFuelUnloadEntity", json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }

    //宣威 单独汽车票重录入页面
    @RequestMapping(value = "/tickQty/mod/{recordNo}")
    @ResponseBody
    public ModelAndView qryTickQty(@PathVariable String recordNo) {
        ModelAndView mav = new ModelAndView("/report/tickQty");

        try {
            TruckFuelUnloadEntity truckFuelUnloadEntity = new TruckFuelUnloadEntity();

            //查询汽车重量信息
            TruckFuelEntranceRecordEntity truckFuelEntranceRecordEntity = new TruckFuelEntranceRecordEntity();
            truckFuelEntranceRecordEntity.setRecordNo(recordNo);
            truckFuelEntranceRecordEntity = rptService.qryTruckFuelEntranceRecord(truckFuelEntranceRecordEntity);
            //整合
            truckFuelUnloadEntity.setMzQty(truckFuelEntranceRecordEntity.getMzQty());
            truckFuelUnloadEntity.setPzQty(truckFuelEntranceRecordEntity.getPzQty());
            truckFuelUnloadEntity.setTickQty(truckFuelEntranceRecordEntity.getTickQty());
            truckFuelUnloadEntity.setKdQty(truckFuelEntranceRecordEntity.getKdQty());
            truckFuelUnloadEntity.setCarId(truckFuelEntranceRecordEntity.getCarId());
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(truckFuelUnloadEntity);
            mav.addObject("message", "success");
            mav.addObject("truckFuelUnloadEntity", json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }

    //织金单独扣吨页面
    @RequestMapping(value = "/kdQty/kd/{recordNo}")
    @ResponseBody
    public ModelAndView qryKdQtyOnly(@PathVariable String recordNo) {
        ModelAndView mav = new ModelAndView("/report/kdQtyOnly");

        try {
            TruckFuelUnloadEntity truckFuelUnloadEntity = new TruckFuelUnloadEntity();

            //查询汽车重量信息
            TruckFuelEntranceRecordEntity truckFuelEntranceRecordEntity = new TruckFuelEntranceRecordEntity();
            truckFuelEntranceRecordEntity.setRecordNo(recordNo);
            truckFuelEntranceRecordEntity = rptService.qryTruckFuelEntranceRecord(truckFuelEntranceRecordEntity);
            //整合
            truckFuelUnloadEntity.setMzQty(truckFuelEntranceRecordEntity.getMzQty());
            truckFuelUnloadEntity.setPzQty(truckFuelEntranceRecordEntity.getPzQty());
            truckFuelUnloadEntity.setTickQty(truckFuelEntranceRecordEntity.getTickQty());
            truckFuelUnloadEntity.setKdQty(truckFuelEntranceRecordEntity.getKdQty());
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(truckFuelUnloadEntity);
            mav.addObject("message", "success");
            mav.addObject("truckFuelUnloadEntity", json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value = "/weightLists")
    @ResponseBody
    public GridModel qryWeightList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity weightRptEntity = SearchForm(WeightRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        weightRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryWeightList((WeightRptEntity) weightRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }
    //称重清单，copy谢 end

    @RequestMapping(value = "/weightRptList")
    @ResponseBody
    public GridModel qryWeightRptList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity weightRptEntity = SearchForm(WeightRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        weightRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //用于前台页面列排序功能
        if (null != getOrder()) {
            weightRptEntity.setOrder(getOrder());
            weightRptEntity.setSort(getSort());
        }

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryWeightRptList((WeightRptEntity) weightRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //宣威提的 汽车每天做的批次报表 xxs20180329
    @RequestMapping(value = "/weightRpt4CarBatch", method = RequestMethod.GET)
    public String gotoWeightRpt4CarBatch() {
        return "/report/weightRpt4CarBatch";
    }

    //宣威 要增加按批次统计的页面 xxs20180329
    @RequestMapping(value = "/weightRptList4CarBatch")
    @ResponseBody
    public GridModel qryWeightRptList4CarBatch() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity weightRptEntity = SearchForm(WeightRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        weightRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //用于前台页面列排序功能
        if (null != getOrder()) {
            weightRptEntity.setOrder(getOrder());
            weightRptEntity.setSort(getSort());
        }

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryWeightRptList4CarBatch((WeightRptEntity) weightRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //宣威 要增加火车按批次统计的页面 zl20180710
    @RequestMapping(value = "/weightRptList4TrainBatch")
    @ResponseBody
    public GridModel qryWeightRptList4TrainBatch() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity weightRptEntity = SearchForm(WeightRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        weightRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //用于前台页面列排序功能
        if (null != getOrder()) {
            weightRptEntity.setOrder(getOrder());
            weightRptEntity.setSort(getSort());
        }

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryWeightRptList4TrainBatch((WeightRptEntity) weightRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    @RequestMapping(value = "/weightRptListNoPage")
    @ResponseBody
    public GridModel qryWeightRptListNoPage() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity weightRptEntity = SearchForm(WeightRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        weightRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryWeightRptList((WeightRptEntity) weightRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //哈平南 汽车按批次进行车数统计的月报
    @RequestMapping(value = "/weightRptSumListNoPage")
    @ResponseBody
    public GridModel qryWeightRptSumListNoPage() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity weightRptEntity = SearchForm(WeightRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        weightRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryWeightRptSumList((WeightRptEntity) weightRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    @RequestMapping(value = "/trainWeightRptList")
    @ResponseBody
    public GridModel qryTrainWeightRptList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity weightRptEntity = SearchForm(WeightRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        weightRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryTrainWeightRptList((WeightRptEntity) weightRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    @RequestMapping(value = "/weightSummaryRpt")
    @ResponseBody
    public WeightRptEntity qryWeightSummaryRpt() {
        //获取界面输入参数
        WeightRptEntity weightRptEntity = SearchForm(WeightRptEntity.class);

        //查询
        WeightRptEntity weightSummaryEntity = null;
        try {
            weightSummaryEntity = rptService.qryWeightSummaryRpt(weightRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weightSummaryEntity;
    }

    @RequestMapping(value = "/trainWeightSummaryRpt")
    @ResponseBody
    public WeightRptEntity qryTrainWeightSummaryRpt() {
        //获取界面输入参数
        WeightRptEntity weightRptEntity = SearchForm(WeightRptEntity.class);

        //查询
        WeightRptEntity weightSummaryEntity = null;
        try {
            weightSummaryEntity = rptService.qryTrainWeightSummaryRpt(weightRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weightSummaryEntity;
    }

    @RequestMapping(value = "/qrySampleRptList")
    @ResponseBody
    public GridModel qrySampleRptList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity sampleRptEntity = SearchForm(SampleRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        sampleRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySampleRptList((SampleRptEntity) sampleRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value = "/updateSampleCnt")
    @ResponseBody
    public void updateSampleCnt() {
        SampleRptEntity sampleRptEntity = SearchForm(SampleRptEntity.class);
        sampleRptEntity.setOpCode(SecurityUtils.getLoginUser().getUsername());
        rptService.updateSampleCnt((SampleRptEntity) sampleRptEntity);
    }

    //查询汽车就地备份数据
    @RequestMapping(value = "/qryCarWeightLocal")
    @ResponseBody
    public GridModel qryCarWeightLocal() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity sampleRptEntity = SearchForm(SampleRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        sampleRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryCarWeightLocal((SampleRptEntity) sampleRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    @RequestMapping(value = "/qrySampleRptList4BB")
    @ResponseBody
    public GridModel qrySampleRptList4BB() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity sampleRptEntity = SearchForm(SampleRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        sampleRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySampleRptList4BB((SampleRptEntity) sampleRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value = "/qrySampleRptList4Hf")
    @ResponseBody
    public GridModel qrySampleRptList4Hf() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity sampleRptEntity = SearchForm(SampleRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        sampleRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySampleRptList4Hf((SampleRptEntity) sampleRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value = "/sampleDetailView/{sampleCode}")
    @ResponseBody
    public ModelAndView qrySampleDetailView(@PathVariable String sampleCode) {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        ModelAndView mav = new ModelAndView("/monitor/sampleDetailView");
        SampleRptEntity sampleRptEntity = new SampleRptEntity();
        sampleRptEntity.setSampleCode(sampleCode);
        GridModel gridModel = null;
        try {
            //查询
            sampleRptEntity.setPageRowIndex(getPageIndex(), getPageSize());
            gridModel = rptService.qrySampleRptList(sampleRptEntity);
            if (gridModel != null) {

                SampleRptEntity qryData = (SampleRptEntity) gridModel.getRows().get(0);

                ObjectMapper objectMapper = JacksonMapper.getInstance();
                String json = objectMapper.writeValueAsString(qryData);

                //获取对象转换成json字符串送到页面
                mav.addObject("message", "success");
                mav.addObject("sampleRptEntity", json);
            }
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }


    @RequestMapping(value = "/qryMergeCarBatchList")
    @ResponseBody
    public GridModel qryMergeCarBatchList() {
        BaseEntity batchNoInfoEntity = SearchForm(BatchNoInfoEntity.class);
        batchNoInfoEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryMergeCarBatchList((BatchNoInfoEntity) batchNoInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    @RequestMapping(value = "/qrySamplingRptList")
    @ResponseBody
    public GridModel qrySamplingRptList() {

        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySamplingRptList((SamplingRptEntity) samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    @RequestMapping(value = "/qrySamplingRptList4ZJ")
    @ResponseBody
    public GridModel qrySamplingRptList4ZJ() {

        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySamplingRptList4ZJ((SamplingRptEntity) samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    @RequestMapping(value = "/qrySamplingRptList4BB")
    @ResponseBody
    public GridModel qrySamplingRptList4BB() {

        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySamplingRptList4BB((SamplingRptEntity) samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value = "/qrySamplingRptList4CCY")
    @ResponseBody
    public GridModel qrySamplingRptList4CCY() {

        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySamplingRptList4CCY((SamplingRptEntity) samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //煤样信息跟踪-查询制样信息记录
    @RequestMapping(value = "/samplingDetailView/{samplingCode}")
    @ResponseBody
    public ModelAndView qrySamplingDetailInfo(@PathVariable String samplingCode) {
        ModelAndView mav = new ModelAndView("/monitor/samplingDetailView");
        SamplingRptEntity samplingRptEntity = new SamplingRptEntity();
        samplingRptEntity.setSamplingCode(samplingCode);
        GridModel gridModel = null;
        try {
            //查询
            samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());
            gridModel = rptService.qrySamplingRptList(samplingRptEntity);
            if (gridModel != null) {
                SamplingRptEntity qryData = (SamplingRptEntity) gridModel.getRows().get(0);

                ObjectMapper objectMapper = JacksonMapper.getInstance();
                String json = objectMapper.writeValueAsString(qryData);

                //获取对象转换成json字符串送到页面
                mav.addObject("message", "success");
                mav.addObject("samplingRptEntity", json);
            }
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value = "/qryKpiValue")
    @ResponseBody
    public Result qryKpiValue() {

        Result result = new Result();
        String jsondata = null;
        try {
            jsondata = rptService.qryKpiValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        result.setSuccessful(true);
        result.setData(jsondata);

        return result;
    }

    @RequestMapping(value = "/qrySamplingRptListView/{samplingCode}")
    @ResponseBody
    public GridModel qrySamplingRptListView(@PathVariable String samplingCode) {
        SamplingRptEntity samplingRptEntity = new SamplingRptEntity();
        samplingRptEntity.setSamplingCode(samplingCode);
        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySamplingRptListView(samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //websoceket测试
    @RequestMapping(value = "/wstest", method = RequestMethod.GET)
    public String gotoWsTestPage() {
        return "/report/page";
    }

    //上传燃料MIS
    @RequestMapping(value = "/gotoUploadToMis", method = RequestMethod.GET)
    public String gotoUpLoadToMis() {
        return "/report/uploadToMis";
    }

    //人工采样
    @RequestMapping(value = "/gotoSampleResRec", method = RequestMethod.GET)
    public String gotoSampleResRec() {
        return "/report/editSampleResRecord";
    }

    //打印汽车计量单时要放开打一点暂定500
    @RequestMapping(value = "/weightRptListNoPageUnlimited")
    @ResponseBody
    public GridModel qryWeightRptListNoPageUnlimited() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity weightRptEntity = SearchForm(WeightRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        weightRptEntity.setPageRowIndex(getPageIndex(), 500);

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryWeightRptList((WeightRptEntity) weightRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value = "/qrySampleBarrelRptList")
    @ResponseBody
    public GridModel qrySampleBarrelRptList() {

        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity sampleRptEntity = SearchForm(SampleRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        sampleRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySampleBarrelRptList((SampleRptEntity) sampleRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    //查询打印采样报告详情
    @RequestMapping(value = "/qrySampleReport/{sampleCode}")
    @ResponseBody
    public ModelAndView qrySampleReport(@PathVariable String sampleCode) {
        ModelAndView mav = new ModelAndView("/report/takeSampleReport");
        String resString = null;
        try {
            resString = rptService.qrySampleReport(sampleCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.addObject("sampleReportData", resString);
        return mav;
    }

    //查询制样报告详情
    @RequestMapping(value = "/qrySamplingReport/{samplingCode}")
    @ResponseBody
    public ModelAndView qrySamplingReport(@PathVariable String samplingCode) {
        ModelAndView mav = new ModelAndView("/report/samplingReport");
        System.out.println(samplingCode);
        String resString = null;
        try {
            resString = rptService.qrySamplingReport(samplingCode);
            System.out.println(resString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.addObject("samplingReportData", resString);
        return mav;
    }

    //制样结果查询，谏壁使用
    @RequestMapping(value = "/qrySamplingRptList4JB")
    @ResponseBody
    public GridModel qrySamplingRptList4JB() {

        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySamplingRptList4JB((SamplingRptEntity) samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询制样结果，聊城使用
    @RequestMapping(value = "/qrySamplingRptList4LC")
    @ResponseBody
    public GridModel qrySamplingRptList4LC() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);
        //设置分页信息并计算记录开始和结束值
        samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySamplingRptList4LC((SamplingRptEntity) samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询制样结果
    @RequestMapping(value = "/qrySamplingRptList4HF")
    @ResponseBody
    public GridModel qrySamplingRptList4HF() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);
        //设置分页信息并计算记录开始和结束值
        samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySamplingRptList4HF((SamplingRptEntity) samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //菜单跳转到修改争议批次
    @RequestMapping(value = "/setArguementFlag", method = RequestMethod.GET)
    public String gotoSetArguementFlag() {
        return "/report/setArguementFlag";
    }


    //提交控制设备命令
    @RequestMapping(value = "/setArguementBatch")
    @ResponseBody
    public Result commitCtrlCmd() {
        Result result = new Result();
        try {
            //获取界面输入参数
            SamplingRptEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            samplingRptEntity.setOpCode(opCode);

            rptService.setArguementBatch(samplingRptEntity);
            //设置返回结果
            if (samplingRptEntity.getResCode() != null && samplingRptEntity.getResCode().equals("0")) {
                result.setMsg("操作争议批次成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作争议批次失败:" + samplingRptEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作争议批次异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }

        return result;
    }

    //人工制样记录 乐东提出的新需求 xxs 20180106
    @RequestMapping(value = "/manualSamplingRecord", method = RequestMethod.GET)
    public String gotoManualSamplingRecord() {
        return "/report/manualSamplingRecord";
    }

    //查询人工制样记录 xxs 20180106
    @RequestMapping(value = "/qryManualSamplingList")
    @ResponseBody
    public GridModel qryManualSamplingList() {

        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity manualSamplingRecordEntity = SearchForm(ManualSamplingRecordEntity.class);

        //设置分页信息并计算记录开始和结束值
        manualSamplingRecordEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = rptService.qryManualSamplingList((ManualSamplingRecordEntity) manualSamplingRecordEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //人工制样记录 编辑 xxs 20180106
    @RequestMapping(value = "/gotoManualSamplingRec", method = RequestMethod.GET)
    public String gotoManualSamplingRec() {
        return "/report/editManualSamplingRecord";
    }


    //人工制样记录 编辑 xxs 20180106
    @RequestMapping(value = "/gotoManualSamplingShowPicture", method = RequestMethod.GET)
    public String gotoManualSamplingShowPicture() {
        return "/report/ManualSamplingShowPicture";
    }

    //人工制样记录 编辑 xxs 20180106
    @RequestMapping(value = "/manualSamplingRecInfo/add")
    @ResponseBody
    public Result addManualSamplingRecInfo(@ModelAttribute ManualSamplingRecordEntity manualSamplingRecordEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            manualSamplingRecordEntity.setOpCode(opCode);
            manualSamplingRecordEntity.setDoActionTag("ADD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(manualSamplingRecordEntity);
            manualSamplingRecordEntity.setJsonString(json);

            rptService.addManualSamplingRecInfo(manualSamplingRecordEntity);

            //设置返回结果
            if (manualSamplingRecordEntity.getResCode() != null && manualSamplingRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("新增人工制样记录成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("新增人工制样记录失败！" + manualSamplingRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("新增人工制样记录异常！");
            e.printStackTrace();
        }
        return result;
    }


    //人工制样记录 编辑 xxs 20180106
    @RequestMapping(value = "/manualSamplingRecInfo/mod")
    @ResponseBody
    public Result modManualSamplingRecInfo(@ModelAttribute ManualSamplingRecordEntity manualSamplingRecordEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            manualSamplingRecordEntity.setOpCode(opCode);
            manualSamplingRecordEntity.setDoActionTag("MOD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(manualSamplingRecordEntity);
            manualSamplingRecordEntity.setJsonString(json);
            rptService.addManualSamplingRecInfo(manualSamplingRecordEntity);
            //设置返回结果
            if (manualSamplingRecordEntity.getResCode() != null && manualSamplingRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("修改人工制样记录成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("修改人工制样记录失败！" + manualSamplingRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改人工制样记录异常！");
            e.printStackTrace();
        }
        return result;
    }


    //人工采样记录 乐东提出的新需求 xxs 20180302
    @RequestMapping(value = "/manualSampleRecord", method = RequestMethod.GET)
    public String gotoManualSampleRecord() {
        return "/report/manualSampleRecord";
    }

    //查询人工采样记录 xxs 20180302
    @RequestMapping(value = "/qryManualSampleList")
    @ResponseBody
    public GridModel qryManualSampleList() {

        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity manualSampleRecordEntity = SearchForm(ManualSampleRecordEntity.class);

        //设置分页信息并计算记录开始和结束值
        manualSampleRecordEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = rptService.qryManualSampleList((ManualSampleRecordEntity) manualSampleRecordEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //人工采样记录 编辑 xxs 20180302
    @RequestMapping(value = "/gotoManualSampleRec", method = RequestMethod.GET)
    public String gotoManualSampleRec() {
        return "/report/editManualSampleRecord";
    }

    //人工采样记录 编辑 xxs 20180302
    @RequestMapping(value = "/manualSampleRecInfo/add")
    @ResponseBody
    public Result addManualSampleRecInfo(@ModelAttribute ManualSampleRecordEntity manualSampleRecordEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            manualSampleRecordEntity.setOpCode(opCode);
            manualSampleRecordEntity.setDoActionTag("ADD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(manualSampleRecordEntity);
            manualSampleRecordEntity.setJsonString(json);

            rptService.addManualSampleRecInfo(manualSampleRecordEntity);

            //设置返回结果
            if (manualSampleRecordEntity.getResCode() != null && manualSampleRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("新增人工采样记录成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("新增人工采样记录失败！" + manualSampleRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("新增人工采样记录异常！");
            e.printStackTrace();
        }
        return result;
    }


    //人工采样记录 编辑 xxs 20180302
    @RequestMapping(value = "/manualSampleRecInfo/mod")
    @ResponseBody
    public Result modManualSampleRecInfo(@ModelAttribute ManualSampleRecordEntity manualSampleRecordEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            manualSampleRecordEntity.setOpCode(opCode);
            manualSampleRecordEntity.setDoActionTag("MOD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(manualSampleRecordEntity);
            manualSampleRecordEntity.setJsonString(json);
            rptService.addManualSampleRecInfo(manualSampleRecordEntity);
            //设置返回结果
            if (manualSampleRecordEntity.getResCode() != null && manualSampleRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("修改人工采样记录成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("修改人工采样记录失败！" + manualSampleRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改人工采样记录异常！");
            e.printStackTrace();
        }
        return result;
    }

    //织金 新增汽车厂内倒运煤计量报表  xxs 20180306
    @RequestMapping(value = "/weightRptCn", method = RequestMethod.GET)
    public String gotoWeightRptCn() {
        return "/report/weightRptCn";
    }

    //查询抽查批次
    @RequestMapping(value = "/qryCompareBatchInfoList")
    @ResponseBody
    public GridModel qryCompareBatchInfoList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        CompareBatchInfoEntity compareBatchInfoEntity = SearchForm(CompareBatchInfoEntity.class);
//        samplingRptEntity.setBatchNoType("5");
        //设置分页信息并计算记录开始和结束值
        compareBatchInfoEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryCompareBatchInfoList(compareBatchInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询批次
    @RequestMapping(value = "/qryBatchInfoList")
    @ResponseBody
    public GridModel qryBatchInfoList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        SamplingRptEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);
//        samplingRptEntity.setBatchNoType("5");
        //设置分页信息并计算记录开始和结束值
        samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryBatchInfoList(samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //大武口先提的 厂内倒运的车辆只过重磅 不分批 单独做个统计报表 xxs20180609
    @RequestMapping(value = "/weightRptList4CN")
    @ResponseBody
    public GridModel qryWeightRptList4CN() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity weightRptEntity = SearchForm(WeightRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        weightRptEntity.setPageRowIndex(getPageIndex(), 500);

        //用于前台页面列排序功能
        if (null != getOrder()) {
            weightRptEntity.setOrder(getOrder());
            weightRptEntity.setSort(getSort());
        }

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryWeightRptList4CN((WeightRptEntity) weightRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }
    @RequestMapping(value="/weightRptList4CNOther")
    @ResponseBody
    public GridModel qryWeightRptList4CNOther(){
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity weightRptEntity = SearchForm(WeightRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        weightRptEntity.setPageRowIndex(getPageIndex(), 500);

        //用于前台页面列排序功能
        if(null!=getOrder()){
            weightRptEntity.setOrder(getOrder());
            weightRptEntity.setSort(getSort());
        }

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryWeightRptList4CNOther((WeightRptEntity)weightRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //大武口先提的 厂内倒运的车辆只过重磅 不分批 单独做个统计报表 xxs20180609
    @RequestMapping(value = "/weightSummaryRpt4CN")
    @ResponseBody
    public WeightRptEntity qryWeightSummaryRpt4CN() {
        //获取界面输入参数
        WeightRptEntity weightRptEntity = SearchForm(WeightRptEntity.class);

        //查询
        WeightRptEntity weightSummaryEntity = null;
        try {
            weightSummaryEntity = rptService.qryWeightSummaryRpt4CN(weightRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weightSummaryEntity;
    }

    @RequestMapping(value = "/qryAutoRateRptList")
    @ResponseBody
    public GridModel qryAutoRateRptList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity sampleRptEntity = SearchForm(SampleRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        sampleRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryAutoRateRptList((SampleRptEntity) sampleRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value = "/sampleRpt4zy", method = RequestMethod.GET)
    public String gotoSampleRpt4zy() {
        return "/report/sampleRpt4zy";
    }

    //泉州抽查批次需要一张单独的报表
    @RequestMapping(value = "/checkBatchRpt", method = RequestMethod.GET)
    public String gotoCheckBatchRpt() {
        return "/report/checkBatchRpt";
    }

    //九江  采制化解码中 汽车批次其实是倒运的船运煤 需要带上船名 xxs20181210
    @RequestMapping(value = "/qrySamplingRptList4JJ")
    @ResponseBody
    public GridModel qrySamplingRptList4JJ() {

        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySamplingRptList4JJ((SamplingRptEntity) samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    //泉州抽查批次需要一张单独的报表
    @RequestMapping(value = "/qryCheckBatchRptList")
    @ResponseBody
    public GridModel qryCheckBatchRptList() {

        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = rptService.qryCheckBatchRptList((SamplingRptEntity) samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    @RequestMapping(value = "/qryBeltWeightRptList")
    @ResponseBody
    public GridModel qryBeltWeightRptList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity beltWeightEntity = SearchForm(BeltWeightEntity.class);
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qryBeltWeightRptList((BeltWeightEntity) beltWeightEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value = "/qrySampleDetailRptList")
    @ResponseBody
    public GridModel qrySampleDetailRptList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity sampleRptEntity = SearchForm(SampleRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        sampleRptEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySampleDetailRptList((SampleRptEntity) sampleRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询采样方案评价
    @RequestMapping(value = "/qrySampleEvaluation")
    @ResponseBody
    public List<Map<String, Object>> qryFlyAsh() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("qryBeginDt", request.getParameter("qryBeginDt"));
        paramMap.put("qryEndDt", request.getParameter("qryEndDt"));
        paramMap.put("qryBatchType", request.getParameter("qryBatchType"));
        List<Map<String, Object>> gridModel = null;
        try {
            gridModel = rptService.qrySampleEvaluation(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    @RequestMapping(value = "/qrySampleRptList4JB")
    @ResponseBody
    public GridModel qrySampleRptList4JB() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity sampleRptEntity = SearchForm(SampleRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        sampleRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySampleRptList4JB((SampleRptEntity) sampleRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value = "/qrySamplingRptList4KC")
    @ResponseBody
    public GridModel qrySamplingRptList4KC() {

        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);

        //设置分页信息并计算记录开始和结束值
        samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySamplingRptList4KC((SamplingRptEntity) samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value = "/gotoCarWeightLocalRpt", method = RequestMethod.GET)
    public String gotoCarWeightLocalRpt() {
        return "/report/carWeightLocalList";
    }

    @RequestMapping(value = "/gotoUploadRateRpt", method = RequestMethod.GET)
    public String gotoUploadRateRpt() {
        return "/report/uploadRate";
    }

    //衡丰，查询投运率记录
    @RequestMapping(value = "/qryUploadRateRptList")
    @ResponseBody
    public GridModel qryUploadRateRptList() {

        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity uploadRateEntity = SearchForm(UploadRateEntity.class);

        //设置分页信息并计算记录开始和结束值
        uploadRateEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = rptService.qryUploadRateRptList((UploadRateEntity) uploadRateEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

}
