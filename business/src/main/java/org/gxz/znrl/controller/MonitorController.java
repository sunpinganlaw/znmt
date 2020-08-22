package org.gxz.znrl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.util.JacksonMapper;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.entity.*;
import org.gxz.znrl.service.CommonService;
import org.gxz.znrl.service.IMonitorService;
import org.gxz.znrl.service.IRptService;
import org.gxz.znrl.shiro.CaptchaUsernamePasswordToken;
import org.gxz.znrl.shiro.ShiroUser;
import org.gxz.znrl.socket.TcpClient;
import org.gxz.znrl.util.AESTool;
import org.gxz.znrl.util.Constant;
import org.gxz.znrl.util.MD5Tool;
import org.gxz.znrl.util.UtilTool;
import org.gxz.znrl.util.qrcode.ZXingCode;
import org.gxz.znrl.viewModel.GridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * Created by xieyt on 2014/12/1.
 */

@Controller
@RequestMapping("/business/monitor")
@SuppressWarnings("unchecked")
public class MonitorController extends BaseAction {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public IMonitorService monitorService;

    @Autowired
    public IRptService rptService;

    @Autowired
    public CommonService commonService;


    //菜单跳转到称重监控页面
//    @RequestMapping(value="/weight/list", method= RequestMethod.GET)
//    public String gotoWeight() {
//        return "/monitor/weight";
//    }
    /**
     * 页面跳转到大屏监控
     * @return
     */
    @RequestMapping(value="/datamax", method= RequestMethod.GET)
    public String gotoDataMax() {
        return "/datamax/index";
    }

    @RequestMapping(value = "/weight/list", method = RequestMethod.GET)
    public ModelAndView gotoWeight(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/monitor/weight");
        mav.addObject("heavyNo", request.getParameter("heavyNo"));
        return mav;
    }

    //菜单跳转到机械采样监控页面
    @RequestMapping(value = "/sample/list", method = RequestMethod.GET)
    public String gotoSample() {
        return "/monitor/jxSample";
    }

    //菜单跳转到全厂总览页面
    @RequestMapping(value = "/overview/list", method = RequestMethod.GET)
    public String gotoOverview() {
        return "/monitor/overview";
    }

    //菜单跳转到全厂总览页面(船)
    @RequestMapping(value = "/shipOverview/list", method = RequestMethod.GET)
    public String gotoShipOverview() {
        return "/monitor/shipOverview";
    }

    //菜单跳转到制样页面
    @RequestMapping(value = "/makesample/list", method = RequestMethod.GET)
    public String gotoMakeSample() {
        return "/monitor/makesample";
    }

    //菜单跳转到存查样柜页面
    @RequestMapping(value = "/storesample/list", method = RequestMethod.GET)
    public String gotoStoreSample() {
        return "/monitor/storesample";
    }

    //菜单跳转到气动存查样柜页面
    @RequestMapping(value = "/autostoresample/list", method = RequestMethod.GET)
    public String gotoAutoStoreSample() {
        return "/monitor/autoStoreSample";
    }

    //菜单跳转到气动存查样柜页面
    @RequestMapping(value = "/autostoresampleNew/list", method = RequestMethod.GET)
    public String gotoAutoStoreSampleNew() {
        return "/monitor/autoStoreSampleNew";
    }

    //菜单跳转到火车入厂登记页面
    @RequestMapping(value = "/register/page", method = RequestMethod.GET)
    public String gotoTrainRegisterPage() {
        return "/monitor/trainRegister";
    }



    @RequestMapping(value = "/transrecord/page", method = RequestMethod.GET)
    public String gotoTransRecordPage() {
        return "/monitor/trainRecord";
    }

    @RequestMapping(value = "/transmstRegister/page", method = RequestMethod.GET)
    public String gototransRegisterPage() {
        return "/monitor/transmstRegister";
    }


    @RequestMapping(value = "/transmstRegisterBoxCar/page", method = RequestMethod.GET)
    public String transmstRegisterBoxCar() {
        return "/monitor/transmstRegisterBoxCar";
    }

    //菜单跳转到船运登记页面
    @RequestMapping(value = "/shipRegister/page", method = RequestMethod.GET)
    public String gotoShipRegisterPage() {
        return "/monitor/shipRegister";
    }

    //菜单跳转到汽车入厂登记注册页面
    @RequestMapping(value = "/register/carpage", method = RequestMethod.GET)
    public String gotoCarRegisterPage() {
        return "/monitor/carRegister";
    }

    //菜单跳转到汽车矿卡登记注册页面
    @RequestMapping(value = "/register/mineregiste", method = RequestMethod.GET)
    public String gotoMineRegisterPage() {
        return "/monitor/mineRegister";
    }

    //跳转到摄像头页面
    @RequestMapping(value = "/record/monitorCamera", method = RequestMethod.GET)
    public String gotoMonitorCamera() {
        return "/monitor/monitorCamera";
    }

    //菜单跳转到汽车来煤入厂记录页面
    @RequestMapping(value = "/record/cartransrecord", method = RequestMethod.GET)
    public String gotoCarTransRecord() {
        return "/monitor/carTransRecord";
    }

    //菜单跳转到汽车来煤入厂记录页面
    @RequestMapping(value = "/record/boxtransrecord", method = RequestMethod.GET)
    public String gotoBoxTransRecord() {
        return "/monitor/boxTransRecord";
    }

    //菜单跳转到汽车来煤记录新增页面
    @RequestMapping(value = "/record/addCarTransRec", method = RequestMethod.GET)
    public String gotoAddCarTransRecord() {
        return "/monitor/addCarTransRecord";
    }

    //菜单跳转到汽车来煤记录新增页面
    @RequestMapping(value = "/record/updateBatchNo", method = RequestMethod.GET)
    public String gotoUpdateBatchNo() {
        return "/monitor/updateBatchInfo";
    }

    //菜单跳转到汽车来煤汽车违规界面
    @RequestMapping(value = "/record/addViolaCarRec", method = RequestMethod.GET)
    public String gotoAddViolaCarRecord() {
        return "/monitor/addViolaCarRecord";
    }

    //菜单跳转到火车批了录入大票页面
    @RequestMapping(value = "/batchTrainDPRecord", method = RequestMethod.GET)
    public String gotoBatchTrainDPRecord() {
        return "/monitor/batchTrainDPRecord";
    }


    //菜单跳转到火车对位调序页面
    @RequestMapping(value = "/adjustTrainOrder", method = RequestMethod.GET)
    public String gotoAdjustTrainOrder() {
        return "/monitor/adjustTrainOrder";
    }

    //菜单跳转到火车车厢重组页面
    @RequestMapping(value = "/gotoTrainRebuild", method = RequestMethod.GET)
    public String gotoTrainRebuild() {
        return "/monitor/trainRebuild";
    }

    //菜单跳转到汽车车厢重组页面
    @RequestMapping(value = "/gotoCarRebuild", method = RequestMethod.GET)
    public String gotoCarRebuild() {
        return "/monitor/carRebuild";
    }

    //菜单跳转到新增火车页面
    @RequestMapping(value = "/gotoAddNewTrain", method = RequestMethod.GET)
    public String gotoAddNewTrain() {
        return "/monitor/addNewTrain";
    }


    @RequestMapping(value = "/gotoAddNewTrain4BoxCar", method = RequestMethod.GET)
    public String gotoAddNewTrain4BoxCar() {
        return "/monitor/addNewTrain4BoxCar";
    }

    //菜单跳转到称重监控页面
    @RequestMapping(value = "/areaPart/list", method = RequestMethod.GET)
    public String gotoAreaPart() {
        return "/monitor/areaPart";
    }

    //菜单跳转到煤场信息页面
    @RequestMapping(value = "/gotoMcInfo/{tag}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoMcInfo(@PathVariable String tag) {
        ModelAndView mav = new ModelAndView("/monitor/mcInfo");
        try {
            mav.addObject("mcInfo", tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }

    //菜单跳转到汽车违规信息页面
    @RequestMapping(value = "/carManage/dualViola", method = RequestMethod.GET)
    public String gotoCarDualViolaPage() {
        return "/monitor/carDualViola";
    }

    //设备实时信息
    @RequestMapping(value = "/deviceBroad/list", method = RequestMethod.GET)
    public ModelAndView gotoDeviceBroadList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/monitor/deviceBroadList");
        mav.addObject("deviceBroadPri", request.getParameter("deviceBroadPri"));
        return mav;
    }

    //设备故障报警信息
    @RequestMapping(value = "/deviceErr/list", method = RequestMethod.GET)
    public String gotoDeviceErrList() {
        return "/monitor/deviceErrList";
    }

    //首页设备故障/实时信息总览
    @RequestMapping(value = "/deviceInfo/deviceInfoOverview", method = RequestMethod.GET)
    public String gotoDeviceInfoOverview() {
        return "/monitor/deviceInfoOverview";
    }

    //人工采样页面
    @RequestMapping(value = "/mansample", method = RequestMethod.GET)
    public String gotoManSample() {
        return "/monitor/manualSample";
    }

    //皮带秤计量信息查询
    @RequestMapping(value = "/BeltBalance", method = RequestMethod.GET)
    public String gotoBeltBalance() {
        return "/monitor/beltBalanceList";
    }

    //人工制样页面
    @RequestMapping(value = "/manSampling", method = RequestMethod.GET)
    public String gotoManSampling() {
        return "/monitor/manualSampling";
    }


    //存查样历史
    @RequestMapping(value = "/cabinetOpRec/list", method = RequestMethod.GET)
    public String gotoCabinetOpRecList() {
        return "/monitor/cabinetOpRecList";
    }

    //存查样历史
    @RequestMapping(value = "/cabinetApplyRec/list", method = RequestMethod.GET)
    public String gotoCabinetApplyRecList() {
        return "/monitor/cabinetApplyRecList";
    }

    //远光存查样柜数据查询 wangz 2016-10-09 增加从数据库中将WSURI常量设置给前台使用
//    @RequestMapping(value="/ygcabinet/list", method= RequestMethod.GET)
//    public String gotoygcabinet() {
//        return "/monitor/ygCabinet";
//    }
    @RequestMapping(value = "/ygcabinet/list", method = RequestMethod.GET)
    public ModelAndView gotoygcabinet() {
        ModelAndView mav = new ModelAndView("/monitor/ygCabinet");
        mav.addObject("wsUri", Constant.getConstVal("WSURI"));
        return mav;
    }

    //织金项目开元存查样柜数据查询
    @RequestMapping(value = "/kycabinet/list", method = RequestMethod.GET)
    public String gotokycabinet() {
        return "/monitor/kyCabinet";
    }

    //船运编辑页面
    @RequestMapping(value = "/editShipRecInfo", method = RequestMethod.GET)
    public String gotoEditShipRecInfo() {
        return "/monitor/editShipTransRecord";
    }

    //设置当前船
    @RequestMapping(value = "/setCurrentShipPage", method = RequestMethod.GET)
    public String gotoSetCurrentShipPage() {
        return "/monitor/setCurrentShipPage";
    }

    //设置卸煤完成
    @RequestMapping(value = "/finishUnload", method = RequestMethod.GET)
    public String gotoFinishUnloadPage() {
        return "/monitor/setFinishUnload";
    }

    //设置船批次相关信息
    @RequestMapping(value = "/editShipBatch", method = RequestMethod.GET)
    public String gotoEditShipBatchPage() {
        return "/monitor/setShipBatch";
    }

    //选择入炉的日期
    @RequestMapping(value = "/selectRLDate", method = RequestMethod.GET)
    public String gotoSelectRLDate() {
        return "/labory/selectRLDate";
    }

    //查询操作日志
    @RequestMapping(value = "/opLogRecord", method = RequestMethod.GET)
    public String gotoLogInfoQry() {
        return "/monitor/opLogRecord";
    }

    //泉州选择入炉审批的日期
    @RequestMapping(value = "/SelectRLapprDate", method = RequestMethod.GET)
    public String gotoSelectRLapprDate() {
        return "/labory/selectRLapprDate";
    }

    //选择船
    @RequestMapping(value = "/selectShipPage", method = RequestMethod.GET)
    public String gotoSelectShipPage() {
        return "/labory/selectReportShip";
    }

    //选择船
    @RequestMapping(value = "/selectShipPage4Appr", method = RequestMethod.GET)
    public String gotoSelectShipPage4Appr() {
        return "/labory/selectReportShip4Appr";
    }

    //船资料编辑页面
    @RequestMapping(value = "/editShipInfo", method = RequestMethod.GET)
    public String gotoEditShipInfo() {
        return "/monitor/editShipInfo";
    }

    //船运货物编辑页面
    @RequestMapping(value = "/editShipCargoRecInfo", method = RequestMethod.GET)
    public String gotoEditShipCargoRecInfo() {
        return "/monitor/editShipCargoRecord";
    }

    //船运煤分批详情查询页面
    @RequestMapping(value = "/viewShipBatchInfo", method = RequestMethod.GET)
    public String gotoViewShipBatchInfo() {
        return "/monitor/viewShipBatchDetail";
    }

    //船资料维护页面
    @RequestMapping(value = "/shipInfo/page", method = RequestMethod.GET)
    public String gotoShipInfo() {
        return "/monitor/shipInfo";
    }

    //虚拟批次信息查询
    @RequestMapping(value = "/virtualBatchInfo", method = RequestMethod.GET)
    public String gotoVirtualBatchInfo() {
        return "/monitor/virtualBatchInfo";
    }

    //虚拟批次信息编辑
    @RequestMapping(value = "/addVirtualBatchInfo", method = RequestMethod.GET)
    public String gotoaAddVirtualBatchInfo() {
        return "/monitor/addVirtualBatchInfo";
    }

    //入炉批次信息查询
    @RequestMapping(value = "/furnaceBatchInfo", method = RequestMethod.GET)
    public String gotoFurnaceBatchInfo() {
        return "/monitor/furnaceBatchInfo";
    }

    //入炉批次信息编辑
    @RequestMapping(value = "/addFurnaceBatchInfo", method = RequestMethod.GET)
    public String gotoaAddFurnaceBatchInfo() {
        return "/monitor/addFurnaceBatchInfo";
    }

    //火车来煤采样管理
    @RequestMapping(value = "/trainForSample", method = RequestMethod.GET)
    public String gotoTrainForSample() {
        return "/monitor/trainForSample";
    }


    //入炉采样
    @RequestMapping(value = "/sampleFurnace/list", method = RequestMethod.GET)
    public String gotoTrainSampleFc() {
        return "/monitor/sampleFurnace";
    }

    //沈西电厂门禁系统
    @RequestMapping(value = "/accessControl", method = RequestMethod.GET)
    public String gotoAccessControl() {
        return "/monitor/accessControlSystem";
    }

    //煤样追踪查询
    @RequestMapping(value = "/viewSampleDetail", method = RequestMethod.GET)
    public String gotoViewSampleDetail() {
        return "/monitor/viewSampleDetail";
    }

    //煤样延期查询
    @RequestMapping(value = "/cabinetDelayRec", method = RequestMethod.GET)
    public String gotoCabinetDelayRec() {
        return "/monitor/cabinetDelayInfo";
    }

    //九江入炉煤皮带数据查询
    @RequestMapping(value = "/beltWeightRec", method = RequestMethod.GET)
    public String gotoBeltWeightRec() {
        return "/monitor/beltWeightView";
    }

    //九江-船运皮带秤批次计量 信息查询
    //liuzh 2020-02-12
    @RequestMapping(value = "/shipBatchQtyInfo", method = RequestMethod.GET)
    public String gotoShipBatchQtyInfo() {
        return "/monitor/shipBatchQtyInfo";
    }

    //蚌埠新增水运煤船
    @RequestMapping(value = "/shipCargo", method = RequestMethod.GET)
    public String gotoShipCargo() {
        return "/monitor/shipCargo";
    }

    //查询称重监控明细信息
    @RequestMapping(value = "/weightList")
    @ResponseBody
    public GridModel qryWeightList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity weightEntity = new WeightEntity();
        //设置分页信息并计算记录开始和结束值
        weightEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryWeightList((WeightEntity) weightEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询采样监控明细信息
    @RequestMapping(value = "/sampleList")
    @ResponseBody
    public GridModel qrySampleList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity sampleEntity = new WeightRptEntity();
        //设置分页信息并计算记录开始和结束值
        sampleEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qrySampleList((WeightRptEntity) sampleEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //今日来煤信息汇总查询
    @RequestMapping(value = "/todayArrivedCoalList")
    @ResponseBody
    public GridModel qryTodayArrivedCoal() {
        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryTodayArrivedCoal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }


    //操作日志查询
    @RequestMapping(value = "/qryOpLogInfo")
    @ResponseBody
    public GridModel qryOpLogInfo() {

        BaseEntity logOpRecEntity = SearchForm(LogOpRecEntity.class);
        logOpRecEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryOpLogInfo((LogOpRecEntity) logOpRecEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //前台操作日志
    @RequestMapping(value = "/qryOpLogInfoXW")
    @ResponseBody
    public GridModel qryOpLogInfoXW() {

        LogOpRecEntity logOpRecEntity = SearchForm(LogOpRecEntity.class);
        logOpRecEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryOpLogInfoXW(logOpRecEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询今日汽车动态信息
    @RequestMapping(value = "/carDynamicList")
    @ResponseBody
    public GridModel qryCarDynamic() {
        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCarDynamic();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }


    //查询制样结果动态信息
    @RequestMapping(value = "/samplingList")
    @ResponseBody
    public GridModel qrySampling() {
        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qrySampling();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }


    //查询设备动态信息
    @RequestMapping(value = "/deviceBroadList/{deviceBroadPri}")
    @ResponseBody
    public GridModel qryDeviceBroad(@PathVariable String deviceBroadPri) {
        DeviceBroadEntity deviceBroadEntity = new DeviceBroadEntity();
        deviceBroadEntity.setDeviceBroadPri(deviceBroadPri);
        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryDeviceBroad(deviceBroadEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询设备动态信息
    @RequestMapping(value = "/deviceBroadListView")
    @ResponseBody
    public GridModel qryDeviceBroadView() {

        BaseEntity deviceBroadEntity = SearchForm(DeviceBroadEntity.class);
        deviceBroadEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryDeviceBroadView((DeviceBroadEntity) deviceBroadEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询故障报警信息
    @RequestMapping(value = "/deviceErrList")
    @ResponseBody
    public GridModel qryDeviceErr() {
        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryDeviceErr();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询故障报警信息
    @RequestMapping(value = "/deviceErrListView")
    @ResponseBody
    public GridModel qryDeviceErrView() {
        BaseEntity deviceErrEntity = SearchForm(DeviceErrEntity.class);
        deviceErrEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryDeviceErrView((DeviceErrEntity) deviceErrEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询故障报警信息
    @RequestMapping(value = "/deviceErrLists")
    @ResponseBody
    public List qryDeviceErrList() {
        List list = null;
        //查询
        try {
            list = monitorService.qryDeviceErrList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    //查看故障报警信息详情
    @RequestMapping(value = "/deviceErrDetail/{recId}")
    @ResponseBody
    public ModelAndView qryDeviceErrDetail(@PathVariable String recId) {
        ModelAndView mav = new ModelAndView("/monitor/deviceErrDetail");

        try {
            //查询
            DeviceBroadEntity deviceEntity = monitorService.qryDeviceErrDetail(recId);

            //获取对象转换成json字符串送到页面
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(deviceEntity);

            mav.addObject("message", "success");
            mav.addObject("deviceEntity", json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }


    //查看故障报警信息详情
    @RequestMapping(value = "/emergencyWarn")
    @ResponseBody
    public ModelAndView emergencyWarn() {
        ModelAndView mav = new ModelAndView("/management/index/emergencyWarn");

        return mav;
    }


    //提交控制设备命令
    @RequestMapping(value = "/commitCtrlCmd")
    @ResponseBody
    @Transactional
    public Result commitCtrlCmd() {
        Result result = new Result();
        try {
            //获取界面输入参数
            CtrlEntity ctrlEntity = SearchForm(CtrlEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            ctrlEntity.setOpCode(opCode);

            //插入设备控制表
            monitorService.commitCtrlCmd(ctrlEntity);
            //设置返回结果
            if (ctrlEntity.getResCode() != null && ctrlEntity.getResCode().equals("0")) {
                result.setMsg("控制设备命令提交成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("控制设备命令提交失败:" + ctrlEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("控制设备命令提交异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }

        return result;
    }

    //提交控制设备命令,大开入炉的一键启动
    @RequestMapping(value = "/commitCtrlCmdDKRL")
    @ResponseBody
    @Transactional
    public Result commitCtrlCmdDKRL() {
        Result result = new Result();
        try {
            //获取界面输入参数
            CtrlEntity ctrlEntity = SearchForm(CtrlEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            ctrlEntity.setOpCode(opCode);

            //插入设备控制表
            monitorService.commitCtrlCmd(ctrlEntity);
            //设置返回结果
            if (ctrlEntity.getResCode() != null && ctrlEntity.getResCode().equals("0")) {
                result.setMsg("提交处理成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("提交处理返回:" + ctrlEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("提交处理异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }

        return result;
    }

    //提交控制设备命令
    @RequestMapping(value = "/commitCtrlCmd2")
    @ResponseBody
    @Transactional
    public Result commitCtrlCmd2() {
        Result result = new Result();
        try {
            //获取界面输入参数
            CtrlEntity ctrlEntity = SearchForm(CtrlEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            ctrlEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(ctrlEntity);
            ctrlEntity.setJsonString(json);
            //插入设备控制表
            monitorService.commitCtrlCmd2(ctrlEntity);
            //设置返回结果
            if (ctrlEntity.getResCode() != null && ctrlEntity.getResCode().equals("0")) {
                result.setMsg("控制设备命令提交成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("控制设备命令提交失败:" + ctrlEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("控制设备命令提交异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }

        return result;
    }

    //提交控制设备命令
    @RequestMapping(value = "/setCurrentShip")
    @ResponseBody
    @Transactional
    public Result setCurrentShip() {
        Result result = new Result();
        try {
            //获取界面输入参数
            ShipEntity shipEntity = SearchForm(ShipEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            shipEntity.setOpCode(opCode);

            //插入设备控制表
            monitorService.setCurrentShip(shipEntity);
            //设置返回结果
            if (shipEntity.getResCode() != null && shipEntity.getResCode().equals("0")) {
                result.setMsg("设置当前卸船煤成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("设置当前卸船煤失败:" + shipEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("设置当前卸船煤异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }

        return result;
    }

    //设置多艘船
    @RequestMapping(value = "/setMultCurrentShip")
    @ResponseBody
    @Transactional
    public Result setMultCurrentShip() {
        Result result = new Result();
        try {
            ShipEntity shipEntity = SearchForm(ShipEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            shipEntity.setOpCode(shiroUser.getUser().getId().toString());


            monitorService.setMultCurrentShip(shipEntity);
            //设置返回结果
            if (result.isSuccessful()) {
                result.setMsg("设置当前卸船煤成功！");
            }
        } catch (RuntimeException e) {
            result.setMsg("设置当前卸船煤失败:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //提交设备报警信息处理确认
    @RequestMapping(value = "/deviceErrDeal")
    @ResponseBody
    @Transactional
    public Result deviceErrDeal() {
        Result result = new Result();
        try {
            //获取界面输入参数
            DeviceBroadEntity deviceBroadEntity = SearchForm(DeviceBroadEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            deviceBroadEntity.setOpCode(opCode);

            //插入设备控制表
            monitorService.deviceErrDeal(deviceBroadEntity);

            result.setMsg("故障报警信息处理成功!");
            result.setSuccessful(true);
        } catch (Exception e) {
            result.setMsg("故障报警信息处理异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }

        return result;
    }


    //强制结束批次
    @RequestMapping(value = "/forceUpdateBatch")
    @ResponseBody
    @Transactional
    public Result forceUpdateBatch() {
        Result result = new Result();
        try {
            //获取界面输入参数
            ShipEntity shipEntity = SearchForm(ShipEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            shipEntity.setOpCode(opCode);

            monitorService.forceUpdateBatch(shipEntity);

            result.setMsg("处理成功!");
            result.setSuccessful(true);
        } catch (Exception e) {
            result.setMsg("处理异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //查询存查样柜子信息
    @RequestMapping(value = "/qrySampleBoxesInfo")
    @ResponseBody
    public List<SampleBoxEntity> qrySampleBoxesInfo() {
        List<SampleBoxEntity> list = null;
        try {
            list = monitorService.qrySampleBoxesInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    //查询柜子样包信息
    @RequestMapping(value = "/qrySampleBoxesDetailInfo/{boxNo}")
    @ResponseBody
    public ModelAndView qrySampleBagInfo(@PathVariable String boxNo) {
        ModelAndView mav = new ModelAndView("/monitor/sampleBagInfo");
        try {
            //查询
            List<SampleBoxEntity> list = monitorService.qrySampleBagInfo(boxNo);
            //获取对象转换成json字符串送到页面
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(list);

            mav.addObject("message", "success");
            mav.addObject("bagInfoList", json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            mav.addObject("message", "fail");
        }

        return mav;
    }


    //查询某柜子里的样包信息,用于审批查询使用
    @RequestMapping(value = "/qrySample4GetList")
    @ResponseBody
    public GridModel qrySample4GetList() {
        //获取界面输入参数
        SampleBoxEntity sampleBoxEntity = SearchForm(SampleBoxEntity.class);

        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qrySampleBag4Get(sampleBoxEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    //查询柜子样包信息
    @RequestMapping(value = "/qryAutoSampleDetail/{cabinetInfo}")
    @ResponseBody
    public ModelAndView qryAutoSampleDetail(@PathVariable String cabinetInfo) {
        ModelAndView mav = new ModelAndView("/monitor/autoStoreSampleDetail");

        try {
            String json;
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            ;
            String[] strArr = cabinetInfo.split("-");
            CabinetEntity cabinetEntity = new CabinetEntity();

            //有些地方存样柜名称有汉字前缀，通过路径传过来有乱码，故配置成后台常量
            if (Constant.getConstVal("GG_NAME_PREFIX") != null) {
                cabinetEntity.setGgName(Constant.getConstVal("GG_NAME_PREFIX") + Integer.valueOf(strArr[0]).toString());//柜子
            } else {
                cabinetEntity.setGgName(Integer.valueOf(strArr[0]).toString());//柜子
            }

            cabinetEntity.setCcName(strArr[1] + Integer.valueOf(strArr[2]).toString());//层
            cabinetEntity.setWwName(Integer.valueOf(strArr[3]).toString());//位
            //查询
            List<CabinetEntity> list = monitorService.qryCabinetInfoList(cabinetEntity);
            //获取对象转换成json字符串送到页面
            if (list.size() != 0) {
                json = objectMapper.writeValueAsString(list.get(0));
            } else {
                json = objectMapper.writeValueAsString(new CabinetEntity());
            }


            mav.addObject("message", "success");
            mav.addObject("autoSampleDetail", json);
        } catch (Exception e) {
            e.printStackTrace();
            mav.addObject("message", "fail");
        }

        return mav;
    }


    @RequestMapping(value = "/trainDetailList")
    @ResponseBody
    public GridModel qryTrainDetailList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        RegisterEntity registerEntity = SearchForm(RegisterEntity.class);

        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryTrainDetailList(registerEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value = "/trainDetailListHf")
    @ResponseBody
    public GridModel qryTrainDetailListHf() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        RegisterEntity registerEntity = SearchForm(RegisterEntity.class);

        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryTrainDetailListHf(registerEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //翻车单合计  衡丰新增
    @RequestMapping(value = "/trainDetailListSummary")
    @ResponseBody
    public GridModel qryTrainDetailListSummary() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        RegisterEntity registerEntity = SearchForm(RegisterEntity.class);

        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryTrainDetailListSummary(registerEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value = "/trainDetailListDk")
    @ResponseBody
    public GridModel qryTrainDetailListDk() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        RegisterEntity registerEntity = SearchForm(RegisterEntity.class);

        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryTrainDetailListDk(registerEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value = "/trainDetailListfc")
    @ResponseBody
    public GridModel qryTrainDetailListfc() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        RegisterEntity registerEntity = SearchForm(RegisterEntity.class);

        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryTrainDetailListfc(registerEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    @RequestMapping(value = "/trainBasicList")
    @ResponseBody
    public GridModel qryTrainBasicList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        RegisterEntity registerEntity = SearchForm(RegisterEntity.class);

        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryTrainBasicList(registerEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    //汽车入厂登记 TODO 未完成
    @RequestMapping(value = "/carRegister")
    @ResponseBody
    @Transactional
    public Result carRegister() {
        Result result = new Result();
        try {
            //获取界面输入参数
            CtrlEntity ctrlEntity = SearchForm(CtrlEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            ctrlEntity.setOpCode(opCode);

            //插入设备控制表
            monitorService.commitCtrlCmd(ctrlEntity);

            result.setMsg("汽车入厂登记成功!");
            result.setSuccessful(true);
        } catch (Exception e) {
            result.setMsg("汽车入厂登记异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //火车入厂登记
    @RequestMapping(value = "/trainRegister")
    @ResponseBody
    @Transactional
    public Result trainRegister(HttpServletRequest req) {
        Result result = new Result();

        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String deleteStr = req.getParameter("deleted");
        String insertStr = req.getParameter("inserted");
        String updateStr = req.getParameter("updated");
        String publicStr = req.getParameter("publicInfo");

        try {
            //获取界面输入参数
            RegisterEntity registerEntity = new RegisterEntity();

            registerEntity.setInsertString(insertStr);
            registerEntity.setDeleteString(deleteStr);
            registerEntity.setUpdateString(updateStr);
            registerEntity.setPublicString(publicStr);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            registerEntity.setOpCode(opCode);

            //新增数据
            monitorService.trainRegister(registerEntity);

            //设置返回结果
            if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
                result.setMsg("火车入厂记录登记成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("火车入厂记录登记失败:" + registerEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("火车入厂登记异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //火车大票批量录入
    @RequestMapping(value = "/batchTrainDPRecordOper")
    @ResponseBody
    @Transactional
    public Result batchTrainDPRecordOper(HttpServletRequest req) {
        Result result = new Result();

        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String publicStr = req.getParameter("publicInfo");
        String updateStr = req.getParameter("updateInfo");

        try {
            //获取界面输入参数
            RegisterEntity registerEntity = new RegisterEntity();

            registerEntity.setUpdateString(updateStr);
            registerEntity.setPublicString(publicStr);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            registerEntity.setOpCode(opCode);

            //新增数据
            monitorService.batchTrainDPRecord(registerEntity);

            //设置返回结果
            if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
                result.setMsg("火车大票批量录入成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("火车大票批量录入失败:" + registerEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("火车大票批量录入异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //火车大票批量录入并分批
    @RequestMapping(value = "/batchTrainDPRecordOperAndBatch")
    @ResponseBody
    @Transactional
    public Result batchTrainDPRecordOperAndBatch(HttpServletRequest req) {
        Result result = new Result();

        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String publicStr = req.getParameter("publicInfo");
        String updateStr = req.getParameter("updateInfo");

        try {
            //获取界面输入参数
            RegisterEntity registerEntity = new RegisterEntity();

            registerEntity.setUpdateString(updateStr);
            registerEntity.setPublicString(publicStr);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            registerEntity.setOpCode(opCode);

            //新增数据
            monitorService.batchTrainDPRecordAndBatch(registerEntity);

            //设置返回结果
            if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
                result.setMsg("火车大票批量录入并分批操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("火车大票批量录入并分批操作失败:" + registerEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("火车大票批量录入并分批操作异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //新增火车，可以在原来车次上新增，也可以新增测试
    @RequestMapping(value = "/addNewTrain")
    @ResponseBody
    @Transactional
    public Result addNewTrain(HttpServletRequest req) {
        Result result = new Result();

        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String publicStr = req.getParameter("publicInfo");
        String updateStr = req.getParameter("updateInfo");

        try {
            //获取界面输入参数
            RegisterEntity registerEntity = new RegisterEntity();

            registerEntity.setUpdateString(updateStr);
            registerEntity.setPublicString(publicStr);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            registerEntity.setOpCode(opCode);

            //新增数据
            monitorService.addNewTrain(registerEntity);

            //设置返回结果
            if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
                result.setMsg("绑定车辆信息操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("绑定车辆信息操作失败:" + registerEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("绑定车辆信息操作异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //合并火车车次
    @RequestMapping(value = "/mergeTrain")
    @ResponseBody
    @Transactional
    public Result mergeTrain(HttpServletRequest req) {
        Result result = new Result();

        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String publicStr = req.getParameter("publicInfo");
        String updateStr = req.getParameter("updateInfo");

        try {
            //获取界面输入参数
            RegisterEntity registerEntity = new RegisterEntity();

            registerEntity.setUpdateString(updateStr);
            registerEntity.setPublicString(publicStr);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            registerEntity.setOpCode(opCode);

            //新增数据
            monitorService.mergeTrain(registerEntity);

            //设置返回结果
            if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
                result.setMsg("合并火车车次操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("合并火车车次操作失败:" + registerEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("合并火车车次操作异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //拆分火车车次
    @RequestMapping(value = "/splitTrain")
    @ResponseBody
    @Transactional
    public Result splitTrain(HttpServletRequest req) {
        Result result = new Result();

        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String publicStr = req.getParameter("publicInfo");
        String updateStr = req.getParameter("updateInfo");

        try {
            //获取界面输入参数
            RegisterEntity registerEntity = new RegisterEntity();

            registerEntity.setUpdateString(updateStr);
            registerEntity.setPublicString(publicStr);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            registerEntity.setOpCode(opCode);

            //新增数据
            monitorService.splitTrain(registerEntity);

            //设置返回结果
            if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
                result.setMsg("拆分火车车次操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("拆分火车车次操作失败:" + registerEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("拆分火车车次操作异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //火车车厢调序
    @RequestMapping(value = "/doAdjustTrainOrder")
    @ResponseBody
    @Transactional
    public Result doAdjustTrainOrder(HttpServletRequest req) {
        Result result = new Result();

        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String publicStr = req.getParameter("publicInfo");
        String updateStr = req.getParameter("updateInfo");

        try {
            //获取界面输入参数
            RegisterEntity registerEntity = new RegisterEntity();
            registerEntity.setPublicString(publicStr);
            registerEntity.setUpdateString(updateStr);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            registerEntity.setOpCode(opCode);

            //新增数据
            monitorService.adjustTrainOrder(registerEntity);

            //设置返回结果
            if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
                result.setMsg("火车顺序调整操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("火车顺序调整操作失败:" + registerEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("火车顺序调整操作异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //编辑火车信息（新增，修改）
    @RequestMapping(value = "/editTrainInfo/{trainNo}")
    @ResponseBody
    public ModelAndView editTrainInfo(@PathVariable String trainNo) {
        ModelAndView mav = new ModelAndView("/monitor/editTrainInfo");
        try {
            mav.addObject("message", "success");
            mav.addObject("paramTrainNo", trainNo);
        } catch (Exception e) {
            e.printStackTrace();
            mav.addObject("message", "fail");
        }
        return mav;
    }

    //火车大票信息录入
    @RequestMapping(value = "/editTrainDPInfo/{trainNo}")
    @ResponseBody
    public ModelAndView editTrainDPInfo(@PathVariable String trainNo) {
        ModelAndView mav = new ModelAndView("/monitor/editTrainDPInfo");
        try {
            mav.addObject("message", "success");
            mav.addObject("paramTrainNo", trainNo);
        } catch (Exception e) {
            e.printStackTrace();
            mav.addObject("message", "fail");
        }
        return mav;
    }


    //根据车次号删除火车记录（通过存储过程删除）
    @RequestMapping(value = "/deleteTrainInfo/{trainNo}")
    @ResponseBody
    @Transactional
    public Result deleteTrainInfo(@PathVariable String trainNo) {
        Result result = new Result();
        try {
            //获取界面输入参数
            RegisterEntity registerEntity = new RegisterEntity();

            registerEntity.setTrainNo(trainNo);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            registerEntity.setOpCode(opCode);
            //删除整个车次数据
            monitorService.deleteWholeTrain(registerEntity);
            //设置返回结果
            if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
                result.setMsg("删除车次操作成功！");
                result.setSuccessful(true);
            } else {
                result.setMsg("车次信息删除失败:" + registerEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("车次信息删除异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //删除单个车厢，并进行重新排序
    @RequestMapping(value = "/deleteOneTrain/{recordNo}")
    @ResponseBody
    @Transactional
    public Result deleteOneTrain(@PathVariable String recordNo) {
        Result result = new Result();
        try {
            //获取界面输入参数
            RegisterEntity registerEntity = new RegisterEntity();

            registerEntity.setRecordNo(recordNo);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            registerEntity.setOpCode(opCode);
            //新增数据
            monitorService.deleteOneTrain(registerEntity);
            //设置返回结果
            if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
                result.setMsg("删除操作成功！");
                result.setSuccessful(true);
            } else {
                result.setMsg("火车车厢信息删除失败:" + registerEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("火车车厢信息删除异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //对火车车次的来煤进行分批
    @RequestMapping(value = "/dealTrainBatchInfo/{trainNo}")
    @ResponseBody
    @Transactional
    public Result dealTrainBatchInfo(@PathVariable String trainNo) {
        Result result = new Result();

        try {
            //获取界面输入参数
            RegisterEntity registerEntity = new RegisterEntity();

            registerEntity.setTrainNo(trainNo);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            registerEntity.setOpCode(opCode);

            //新增数据
            monitorService.dealTrainBatchInfo(registerEntity);

            //设置返回结果
            if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
                result.setMsg(registerEntity.getResMsg());
                result.setSuccessful(true);
            } else {
                result.setMsg("火车来煤分批失败:" + registerEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("火车来煤分批异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //编辑火车信息（新增）
    @RequestMapping(value = "/editCarInfo/add")
    @ResponseBody
    public Result addCarInfo(@ModelAttribute CarInfoEntity carInfoEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carInfoEntity.setFstUsrId(opCode);
            carInfoEntity.setBlkLstFlg("0");//新增默认不是黑名单
            monitorService.updateCarInfo(carInfoEntity);
            //新增汽车信息
            monitorService.addCarInfo(carInfoEntity);
            result.setSuccessful(true);
            result.setMsg("新增车辆信息成功！");
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("新增车辆信息失败！");
            e.printStackTrace();
        }
        return result;
    }

    //编辑矿卡信息（新增）
    @RequestMapping(value = "/editMineCardInfo/add")
    @ResponseBody
    public Result addMineCardInfo(@ModelAttribute MineCardDetailEntity mineCardDetailEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            mineCardDetailEntity.setOpCode(opCode);

            //判断矿卡卡号是否被使用
            TableColumnByColumn tableColumnByColumn = new TableColumnByColumn();
            tableColumnByColumn.setShowCol("COUNT(1)");
            tableColumnByColumn.setTableName("MINE_CARD_INFO");
            tableColumnByColumn.setCondCol("MINE_CARD_ID");
            tableColumnByColumn.setCondValue(mineCardDetailEntity.getMineCardId());
            tableColumnByColumn.setCondOperate("=");
            tableColumnByColumn.setCondType("1");
            tableColumnByColumn.setCondCnt(1);
            String tmp = commonService.getTableColumnByColumn(tableColumnByColumn);
            if (tmp != null && Integer.parseInt(tmp) > 0) {
                result.setSuccessful(false);
                result.setMsg("该矿卡编号已经存在，不能重复录入，请确认后录入！");
                return result;
            }
            tableColumnByColumn.setCondCol("VIEW_CARD_ID");
            tableColumnByColumn.setCondValue(mineCardDetailEntity.getViewCardId());
            tmp = commonService.getTableColumnByColumn(tableColumnByColumn);
            if (tmp != null && Integer.parseInt(tmp) > 0) {
                result.setSuccessful(false);
                result.setMsg("该可视卡号已经存在，不能重复录入，请确认后录入！");
                return result;
            }

            //新增汽车信息
            monitorService.addMineCardInfo(mineCardDetailEntity);
            result.setSuccessful(true);
            result.setMsg("新增矿卡信息成功！");
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("新增矿卡信息失败！");
            e.printStackTrace();
        }
        return result;
    }

    //编辑火车信息（新增）
    @RequestMapping(value = "/editMineCardInfo/mod")
    @ResponseBody
    public Result modifyMineCardInfo(@ModelAttribute MineCardDetailEntity mineCardDetailEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            mineCardDetailEntity.setUpdateCode(opCode);

            //新增汽车信息
            monitorService.modifyMineCardInfo(mineCardDetailEntity);
            result.setSuccessful(true);
            result.setMsg("修改车辆信息成功！");
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改车辆信息失败！");
            e.printStackTrace();
        }
        return result;
    }

    //编辑火车信息（新增）
    @RequestMapping(value = "/editCarInfo/mod")
    @ResponseBody
    public Result modifyCarInfo(@ModelAttribute CarInfoEntity carInfoEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carInfoEntity.setLstUsrId(opCode);
            carInfoEntity.setBlkLstFlg("0");//默认不是黑名单

            //新增汽车信息
            monitorService.modifyCarInfo(carInfoEntity);
            result.setSuccessful(true);
            result.setMsg("修改车辆信息成功！");
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改车辆信息失败！");
            e.printStackTrace();
        }
        return result;
    }


    //查询登记注册汽车基本信息
    @RequestMapping(value = "/registeredCarList")
    @ResponseBody
    public GridModel qryRegisteredCarList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity carInfoEntity = SearchForm(CarInfoEntity.class);

        //设置分页信息并计算记录开始和结束值
        carInfoEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryRegisteredCarList((CarInfoEntity) carInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //插入煤样追踪记录-确认煤样接收成功
    @RequestMapping(value = "/confirmSample/{packCode}")
    @ResponseBody
    @Transactional
    public Result confirmSampleRecv(@PathVariable String packCode) {
        Result result = new Result();

        try {
            SampleTraceEntity sampleTraceEntity = new SampleTraceEntity();
            //获取界面输入参数
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            sampleTraceEntity.setOperName(opCode);

            sampleTraceEntity.setPackCode(packCode);
            //新增数据
            monitorService.confirmSampleRecv(sampleTraceEntity);

            //设置返回结果
            if (sampleTraceEntity.getResCode() != null && sampleTraceEntity.getResCode().equals("0")) {
                result.setMsg(sampleTraceEntity.getResMsg());
                result.setSuccessful(true);
            } else {
                result.setMsg("确认煤样瓶信息失败:" + sampleTraceEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("确认煤样瓶信息失败:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //查询注册矿卡基本信息
    @RequestMapping(value = "/registerMineList")
    @ResponseBody
    public GridModel qryRegisterMineList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity mineCardInfoEntity = SearchForm(MineCardInfoEntity.class);

        //设置分页信息并计算记录开始和结束值
        mineCardInfoEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryRegisterMineList((MineCardInfoEntity) mineCardInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    //删除汽车注册记录
    @RequestMapping(value = "/deleteCarRegInfo/{recId}")
    @ResponseBody
    @Transactional
    public Result deleteCarRegInfo(@PathVariable String recId) {
        Result result = new Result();
        try {
            //删除数据
            monitorService.deleteCarRegInfo(recId);

            result.setMsg("数据删除成功!");
            result.setSuccessful(true);
        } catch (Exception e) {
            result.setMsg("数据删除异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //作废汽车注册记录 2017.3.15
    @RequestMapping(value = "/unableCarRegInfo/{recId}")
    @ResponseBody
    @Transactional
    public Result unableCarRegInfo(@PathVariable String recId) {
        Result result = new Result();
        try {
            //获取界面输入参数
            CarInfoEntity carInfoEntity = new CarInfoEntity();
            carInfoEntity.setRecId(recId);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String lstUsrId = shiroUser.getUser().getId().toString();
            carInfoEntity.setLstUsrId(lstUsrId);
            //作废数据
            monitorService.unableCarRegInfo(carInfoEntity);
            //设置返回结果
            if (carInfoEntity.getResCode() != null && carInfoEntity.getResCode().equals("0")) {
                result.setMsg("车卡作废成功！");
                result.setSuccessful(true);
            } else {
                result.setMsg("车卡作废失败:" + carInfoEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("车卡作废异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //删除汽车注册记录
    @RequestMapping(value = "/deleteMineRegInfo/{cardRecId}")
    @ResponseBody
    @Transactional
    public Result deleteMineRegInfo(@PathVariable String cardRecId) {
        Result result = new Result();
        try {
            //删除数据
            monitorService.deleteMineRegInfo(cardRecId);

            result.setMsg("数据删除成功!");
            result.setSuccessful(true);
        } catch (Exception e) {
            result.setMsg("数据删除异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //查看车辆信息详情
    @RequestMapping(value = "/carDetail/{recId}")
    @ResponseBody
    public ModelAndView qryCarDetail(@PathVariable String recId) {
        ModelAndView mav = new ModelAndView("/monitor/carDetail");

        // -1为新增
        if (recId == null || recId.equals("-1")) {
            mav.addObject("message", "success");
            mav.addObject("carInfoEntity", "{'recId':'-1'}");
            return mav;
        }

        try {
            //查询
            CarInfoEntity carInfoEntity = monitorService.qryCarDetail(recId);

            //获取对象转换成json字符串送到页面
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carInfoEntity);

            mav.addObject("message", "success");
            mav.addObject("carInfoEntity", json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }

    //查看车辆信息详情
    @RequestMapping(value = "/mineCardDetail/{cardRecId}")
    @ResponseBody
    public ModelAndView qryMineCardDetail(@PathVariable String cardRecId) {
        ModelAndView mav = new ModelAndView("/monitor/mineCardDetail");

        // -1为新增
        if (cardRecId == null || cardRecId.equals("-1")) {
            mav.addObject("message", "success");
            mav.addObject("mineCardDetailEntity", "{'cardRecId':'-1'}");
            return mav;
        }

        try {
            //查询
            MineCardDetailEntity mineCardDetailEntity = monitorService.qryMineCardDetail(cardRecId);

            //获取对象转换成json字符串送到页面
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(mineCardDetailEntity);

            mav.addObject("message", "success");
            mav.addObject("mineCardDetailEntity", json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }


    //查看车辆信息详情
    @RequestMapping(value = "/mineCardDetailNew/{cardRecId}")
    @ResponseBody
    public ModelAndView qryMineCardDetailNew(@PathVariable String cardRecId) {
        ModelAndView mav = new ModelAndView("/monitor/mineCardDetailNew");

        // -1为新增
        if (cardRecId == null || cardRecId.equals("-1")) {
            mav.addObject("message", "success");
            mav.addObject("mineCardDetailEntity", "{'cardRecId':'-1'}");
            return mav;
        }

        try {
            //查询
            MineCardDetailEntity mineCardDetailEntity = monitorService.qryMineCardDetail(cardRecId);

            //获取对象转换成json字符串送到页面
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(mineCardDetailEntity);

            mav.addObject("message", "success");
            mav.addObject("mineCardDetailEntity", json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }


    //查询当天注册发卡了多少汽车
    @RequestMapping(value = "/qryTodayRegisterCarCnt")
    @ResponseBody
    public Integer qryTodayRegisterCarCnt() {
        Integer i = null;
        try {
            i = monitorService.qryTodayRegisterCarCnt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }


    //查询当天注册发卡了多少汽车
    @RequestMapping(value = "/qryTodayRegisterMineCnt")
    @ResponseBody
    public Integer qryTodayRegisterMineCnt() {
        Integer i = null;
        try {
            i = monitorService.qryTodayRegisterMineCnt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    //查询当天来了多少车煤
    @RequestMapping(value = "/qryTodayTransCarCnt")
    @ResponseBody
    public Integer qryTodayTransCarCnt() {
        Integer i = 0;
        try {
            GridModel gridModel = monitorService.qryCarDynamic();
            if (gridModel != null) {
                List list = gridModel.getRows();
                if (list != null) {
                    i = new Integer(list.size());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }


    @RequestMapping(value = "/focusCar")
    @ResponseBody
    public CarInfoEntity focusCar() {
        //获取界面输入参数
        CarInfoEntity carInfoEntity = SearchForm(CarInfoEntity.class);
        //查询
        CarInfoEntity c = null;
        try {
            c = monitorService.focusCar(carInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    //编辑汽车来煤信息（新增）
    @RequestMapping(value = "/carTransRec/add")
    @ResponseBody
    public Result addCarTransRecord(@ModelAttribute CarTransRecordEntity carTransRecordEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carTransRecordEntity.setOpCode(opCode);
            carTransRecordEntity.setDoActionTag("ADD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);

            carTransRecordEntity.setJsonString(json);

            monitorService.addCarTransRecord(carTransRecordEntity);

            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("新增汽车来煤信息成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("新增汽车来煤信息失败！" + carTransRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("新增汽车来煤信息异常！");
            e.printStackTrace();
        }
        return result;
    }


    //编辑汽车来煤信息（新增）
    @RequestMapping(value = "/carTransRec/mod")
    @ResponseBody
    public Result modCarTransRecord(@ModelAttribute CarTransRecordEntity carTransRecordEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carTransRecordEntity.setOpCode(opCode);
            carTransRecordEntity.setDoActionTag("MOD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);
            carTransRecordEntity.setJsonString(json);
            monitorService.addCarTransRecord(carTransRecordEntity);
            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("修改汽车来煤信息成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("修改汽车来煤信息失败！" + carTransRecordEntity.getResMsg());
            }

        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改汽车来煤信息异常！");
            e.printStackTrace();
        }
        return result;
    }

    //（对已经分批的汽车信息强制更新批次号：库车）
    @RequestMapping(value = "/carTransRec/update")
    @ResponseBody
    public Result updateCarbatchNo(@ModelAttribute CarTransRecordEntity carTransRecordEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carTransRecordEntity.setOpCode(opCode);
            carTransRecordEntity.setDoActionTag("UPDATE");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);
            carTransRecordEntity.setJsonString(json);
            monitorService.addCarTransRecord(carTransRecordEntity);
            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("修改汽车来煤批次成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("修改汽车来煤批次失败！" + carTransRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改汽车来煤批次异常！");
            e.printStackTrace();
        }
        return result;
    }

    //（对已经分批的汽车信息强制更新批次号：丰城）
    @RequestMapping(value = "/carTransRecNew/update")
    @ResponseBody
    public Result updateCarbatchNoNew(@ModelAttribute CarTransRecordEntity carTransRecordEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carTransRecordEntity.setOpCode(opCode);
            carTransRecordEntity.setDoActionTag("UPDATE");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);
            carTransRecordEntity.setJsonString(json);

            monitorService.addCarTransRecordNew(carTransRecordEntity);

            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("修改汽过卡信息成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("修改汽过卡信息失败！" + carTransRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改汽过卡信息异常！");
            e.printStackTrace();
        }
        return result;
    }

    //编辑汽车来煤信息（新增 人工入厂）
    @RequestMapping(value = "/carTransRecNew/add")
    @ResponseBody
    public Result addCarTransRecordNew(@ModelAttribute CarTransRecordEntity carTransRecordEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carTransRecordEntity.setOpCode(opCode);
            carTransRecordEntity.setDoActionTag("ADD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);
            carTransRecordEntity.setJsonString(json);

            monitorService.addCarTransRecordNew(carTransRecordEntity);

            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("新增汽车来煤信息成功！{" + carTransRecordEntity.getResMsg() + "}");
            } else {
                result.setSuccessful(false);
                result.setMsg("新增汽车来煤信息失败！" + carTransRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("新增汽车来煤信息异常！");
            e.printStackTrace();
        }
        return result;
    }

    //补录汽车来煤信息（屏蔽全流程）
    @RequestMapping(value = "/carTransRecNew/addAll")
    @ResponseBody
    public Result addAllCarTransRecordNew(@ModelAttribute CarTransRecordEntity carTransRecordEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carTransRecordEntity.setOpCode(opCode);
            carTransRecordEntity.setDoActionTag("ADDALL");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);
            carTransRecordEntity.setJsonString(json);

            monitorService.addCarTransRecordNew(carTransRecordEntity);

            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("补录汽车来煤信息成功！{" + carTransRecordEntity.getResMsg() + "}");
            } else {
                result.setSuccessful(false);
                result.setMsg("补录汽车来煤信息失败！" + carTransRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("补录汽车来煤信息异常！");
            e.printStackTrace();
        }
        return result;
    }


    //编辑汽车来煤信息（新增）
    @RequestMapping(value = "/carTransRecNew/mod")
    @ResponseBody
    public Result modCarTransRecordNew(@ModelAttribute CarTransRecordEntity carTransRecordEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carTransRecordEntity.setOpCode(opCode);
            carTransRecordEntity.setDoActionTag("MOD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);
            carTransRecordEntity.setJsonString(json);

            monitorService.addCarTransRecordNew(carTransRecordEntity);

            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("修改汽车过卡信息成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("修改汽车过卡信息失败！" + carTransRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改汽车过卡信息异常！");
            e.printStackTrace();
        }
        return result;
    }


    //编辑汽车来煤信息（新增）
    @RequestMapping(value = "/carTransRecNew/print")
    @ResponseBody
    public Result printCarTransRecordNew(@ModelAttribute CarTransRecordEntity carTransRecordEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carTransRecordEntity.setOpCode(opCode);
            carTransRecordEntity.setDoActionTag("PRINT");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);
            carTransRecordEntity.setJsonString(json);

            monitorService.addCarTransRecordNew(carTransRecordEntity);

            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("补打汽车过卡信息成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("补打汽车过卡信息失败！" + carTransRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("补打汽车过卡信息异常！");
            e.printStackTrace();
        }
        return result;
    }




    //编辑汽车来煤信息（删除入厂信息）
    @RequestMapping(value = "/carTransRecNew/del")
    @ResponseBody
    public Result delCarTransRecordNew(@ModelAttribute CarTransRecordEntity carTransRecordEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carTransRecordEntity.setOpCode(opCode);
            carTransRecordEntity.setDoActionTag("DEL");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);
            carTransRecordEntity.setJsonString(json);

            monitorService.addCarTransRecordNew(carTransRecordEntity);

            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("删除汽车来煤信息成功！{" + carTransRecordEntity.getResMsg() + "}");
            } else {
                result.setSuccessful(false);
                result.setMsg("删除汽车来煤信息失败！" + carTransRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("删除汽车来煤信息异常！");
            e.printStackTrace();
        }
        return result;
    }


    //编辑汽车来煤信息（删除入厂信息）
    @RequestMapping(value = "/carTransRecNew/confirmUnload")
    @ResponseBody
    public Result confirmUnload(@ModelAttribute CarTransRecordEntity carTransRecordEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carTransRecordEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);
            carTransRecordEntity.setJsonString(json);

            monitorService.confirmUnload(carTransRecordEntity);

            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("人工确认卸煤成功！{" + carTransRecordEntity.getResMsg() + "}");
            } else {
                result.setSuccessful(false);
                result.setMsg("人工确认卸煤失败！" + carTransRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("人工确认卸煤异常！");
            e.printStackTrace();
        }
        return result;
    }


    //查询汽车来煤信息
    @RequestMapping(value = "/carTransRecordList")
    @ResponseBody
    public GridModel qryCarTransRecordList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity carTransRecordEntity = SearchForm(CarTransRecordEntity.class);

        //设置分页信息并计算记录开始和结束值
        carTransRecordEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //用于前台页面列排序功能
        if (null != getOrder()) {
            carTransRecordEntity.setOrder(getOrder());
            carTransRecordEntity.setSort(getSort());
        }
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCarTransRecordList((CarTransRecordEntity) carTransRecordEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询汽车来煤信息
    @RequestMapping(value = "/carTransRecordListRpt")
    @ResponseBody
    public GridModel carTransRecordListRpt() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity carTransRecordEntity = SearchForm(CarTransRecordEntity.class);

      //设置分页信息并计算记录开始和结束值
        carTransRecordEntity.setPageRowIndex(1, 10000);

        //用于前台页面列排序功能
        if (null != getOrder()) {
            carTransRecordEntity.setOrder(getOrder());
            carTransRecordEntity.setSort(getSort());
        }
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCarTransRecordList((CarTransRecordEntity) carTransRecordEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }




    //查询汽车来煤信息
    @RequestMapping(value = "/TransmstRegistList")
    @ResponseBody
    public GridModel qryTransRecordList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity transmstEntity = SearchForm(TransmstEntity.class);

        //设置分页信息并计算记录开始和结束值
        transmstEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryTransRegisteredList((TransmstEntity) transmstEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    //查询汽车来煤信息4DWK
    @RequestMapping(value = "/carTransRecordList4DWK")
    @ResponseBody
    public GridModel qryCarTransRecordList4DWK() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity carTransRecordEntity = SearchForm(CarTransRecordEntity.class);

        //设置分页信息并计算记录开始和结束值
        carTransRecordEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //用于前台页面列排序功能
        if (null != getOrder()) {
            carTransRecordEntity.setOrder(getOrder());
            carTransRecordEntity.setSort(getSort());
        }
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCarTransRecordList4DWK((CarTransRecordEntity) carTransRecordEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //删除汽车来煤记录
    @RequestMapping(value = "/deleteCarTransRec/{recordNo}")
    @ResponseBody
    @Transactional
    public Result deleteCarTransRec(@PathVariable String recordNo) {
        Result result = new Result();
        CarTransRecordEntity carTransRecordEntity = new CarTransRecordEntity();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carTransRecordEntity.setOpCode(opCode);
            carTransRecordEntity.setDoActionTag("DEL");
            carTransRecordEntity.setRecordNo(recordNo);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);
            carTransRecordEntity.setJsonString(json);

            monitorService.addCarTransRecordNew(carTransRecordEntity);

            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("删除汽车来煤信息成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("删除汽车来煤信息失败！" + carTransRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setMsg("数据删除异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //切换入厂车的通道
    @RequestMapping(value = "/changeChannel/{recordNo}")
    @ResponseBody
    @Transactional
    public Result changeChannel(@PathVariable String recordNo) {
        Result result = new Result();
        CarTransRecordEntity carTransRecordEntity = new CarTransRecordEntity();

        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carTransRecordEntity.setOpCode(opCode);
            carTransRecordEntity.setRecordNo(recordNo);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);
            carTransRecordEntity.setJsonString(json);

            monitorService.changeChannel(carTransRecordEntity);

            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("切换入厂汽车通道成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("切换入厂汽车通道失败！" + carTransRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setMsg("切换入厂汽车通道异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //查询开元气动存查样柜信息
    @RequestMapping(value = "/qryKyCabinetInfoList")
    @ResponseBody
    public GridModel qryKyCabinetInfoList() {

        GridModel gridModel = null;

        CabinetEntity cabinetEntity = SearchForm(CabinetEntity.class);
        cabinetEntity.setPageRowIndex(getPageIndex(), getPageSize());
        try {
            gridModel = monitorService.qryKYCabinetInfoList(cabinetEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询气动存查样柜信息
    @RequestMapping(value = "/qryCabinetInfoList")
    @ResponseBody
    public List<CabinetEntity> qryCabinetInfoList() {
        List<CabinetEntity> list = null;
        try {
            CabinetEntity cabinetEntity = SearchForm(CabinetEntity.class);
            list = monitorService.qryCabinetInfoList(cabinetEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询气动存查样柜信息
    @RequestMapping(value = "/qryCabinetInfoListNew")
    @ResponseBody
    public List<CabinetEntity> qryCabinetInfoListNew() {
        List<CabinetEntity> list = null;
        try {
            CabinetEntity cabinetEntity = SearchForm(CabinetEntity.class);
            list = monitorService.qryCabinetInfoListNew(cabinetEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询气动存查样柜信息
    @RequestMapping(value = "/qryCabinetOpRecList")
    @ResponseBody
    public GridModel qryCabinetOpRecList() {
        CabinetOpRecEntity cabinetOpRecEntity = SearchForm(CabinetOpRecEntity.class);
        cabinetOpRecEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCabinetOpRecList(cabinetOpRecEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    @RequestMapping(value = "/qryCabinetOpRecListMain")
    @ResponseBody
    public GridModel qryCabinetOpRecListMain() {
        CabinetOpRecEntity cabinetOpRecEntity = SearchForm(CabinetOpRecEntity.class);
        cabinetOpRecEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCabinetOpRecListMain(cabinetOpRecEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    @RequestMapping(value = "/qryCabinetOpRecList2")
    @ResponseBody
    public GridModel qryCabinetOpRecList2() {
        CabinetOpRecEntity cabinetOpRecEntity = SearchForm(CabinetOpRecEntity.class);
        cabinetOpRecEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCabinetOpRecList2(cabinetOpRecEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询气动存查样柜取样取样申请记录
    @RequestMapping(value = "/qryCabinetApplyRecList")
    @ResponseBody
    public GridModel qryCabinetApplyRecList() {
        CabinetOpRecEntity cabinetOpRecEntity = SearchForm(CabinetOpRecEntity.class);
        cabinetOpRecEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCabinetApplyRecList(cabinetOpRecEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询气动存查样柜信息-取样、弃样
    @RequestMapping(value = "/qryCabinetSampleList")
    @ResponseBody
    public GridModel qryCabinetSampleList() {
        CabinetEntity cabinetEntity = SearchForm(CabinetEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCabinetSampleList(cabinetEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询气动存查样柜信息-取样、弃样（谏壁-支持分页）
    @RequestMapping(value = "/qryCabinetSampleListPage")
    @ResponseBody
    public GridModel qryCabinetSampleListPage() {
        CabinetEntity cabinetEntity = SearchForm(CabinetEntity.class);
        //设置分页信息并计算记录开始和结束值
        cabinetEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCabinetSampleListPage(cabinetEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询气动存查样柜信息-取样、弃样
    @RequestMapping(value = "/qryCabinetSampleListNew")
    @ResponseBody
    public GridModel qryCabinetSampleListNew() {
        CabinetEntity cabinetEntity = SearchForm(CabinetEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCabinetSampleListNew(cabinetEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //气动存查样柜的 取样弃样页面
    @RequestMapping(value = "/dealSample")
    @ResponseBody
    public ModelAndView dealSample() {
        return new ModelAndView("/monitor/dealSample");
    }

    //电子存查样柜的 取样页面
    @RequestMapping(value = "/processSample")
    @ResponseBody
    public ModelAndView processSample() {
        return new ModelAndView("/monitor/processSample");
    }

    //查看气动样柜审批详情
    @RequestMapping(value = "/viewApprovingDetail")
    @ResponseBody
    public ModelAndView viewApprovingDetail() {
        return new ModelAndView("/monitor/viewApprovingSampleDetail");
    }

    //查看电子样柜审批详情-取样
    @RequestMapping(value = "/viewApprovingEleSampleDetail")
    @ResponseBody
    public ModelAndView viewApprovingEleSampleDetail() {
        return new ModelAndView("/monitor/viewApprovingEleSampleDetail");
    }

    //查看电子样柜审批详情-清样
    @RequestMapping(value = "/viewApprovingEleClearSampleDetail")
    @ResponseBody
    public ModelAndView viewApprovingEleClearSampleDetail() {
        return new ModelAndView("/monitor/viewApprovingEleClearSampleDetail");
    }

    //查看电子样柜审批详情-生成化验报告
    @RequestMapping(value = "/viewApprovingLaboryDataDetail")
    @ResponseBody
    public ModelAndView viewApprovingLaboryDataDetail() {
        return new ModelAndView("/monitor/laboryDataList4Appr");
    }

    //跳转到修改单个火车车厢信息的窗口
    @RequestMapping(value = "/modifyOneTrainInfoWin", method = RequestMethod.GET)
    public String gotoModifyOneTrainInfoWin() {
        return "/monitor/modifyOneTrainInfo";
    }

    //跳转到打印窗口
    @RequestMapping(value = "/barcodePrintWin/{sc}")
    @ResponseBody
    public ModelAndView gotoBarcodePrintWin(@PathVariable String sc) {
        ModelAndView mav = new ModelAndView("/monitor/printBarCode");
        mav.addObject("printSmpleCodeStr", sc);
        return mav;
    }

    //跳转到打印窗口  聊城新增功能，不显示条码下方的数字
    @RequestMapping(value = "/barcodePrintNoHRI/{sc}")
    @ResponseBody
    public ModelAndView gotoBarcodePrintNoHRI(@PathVariable String sc) {
        ModelAndView mav = new ModelAndView("/monitor/printBarCodeNoHRI");
        mav.addObject("printSmpleCodeStr", sc);
        return mav;
    }

    @RequestMapping(value = "/qrcodePrintWin/{sc}")
    @ResponseBody
    public ModelAndView gotoQrcodePrintWin(@PathVariable String sc) {
        ZXingCode zXingCode = new ZXingCode();
        String bathPath = getClass().getResource("/").getFile().toString();
        int i = 0;
        while (i < 3) {
            int lastFirst = bathPath.lastIndexOf('/');
            bathPath = bathPath.substring(0, lastFirst);
            i++;
        }
        String filePath = bathPath + "/styles/images/" + sc + ".jpg";
        zXingCode.createZXingCode(filePath, sc);
        ModelAndView mav = new ModelAndView("/monitor/printQrCode");
        mav.addObject("printSmpleCodeStr", sc);
        return mav;
    }

    //跳转到修改工作模式的窗口
    @RequestMapping(value = "/modifyWorkModeWin", method = RequestMethod.GET)
    public String gotomodifyWorkModeWin() {
        return "/management/index/modifyWorkMode";
    }


    //查询单个车厢详情
    @RequestMapping(value = "/qryOneTrainDetail/{recordNo}")
    @ResponseBody
    public RegisterEntity qryOneTrainDetail(@PathVariable String recordNo) {
        RegisterEntity registerEntity = null;
        try {
            //查询
            registerEntity = monitorService.qryOneTrainDetail(recordNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registerEntity;
    }

    //查询单个车厢详情
    @RequestMapping(value = "/qryOneTrainDetailfc/{recordNo}")
    @ResponseBody
    public RegisterEntity qryOneTrainDetailfc(@PathVariable String recordNo) {
        RegisterEntity registerEntity = null;
        try {
            //查询
            registerEntity = monitorService.qryOneTrainDetailfc(recordNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registerEntity;
    }


    //编辑汽车来煤信息（新增）
    @RequestMapping(value = "/modifyOneTrainInfo")
    @ResponseBody
    public Result modifyOneTrainInfo(@ModelAttribute RegisterEntity registerEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            registerEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(registerEntity);
            registerEntity.setUpdateString(json);

            monitorService.modifyOneTrainInfo(registerEntity);

            //设置返回结果
            if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("修改火车来煤信息成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("修改火车来煤信息失败！" + registerEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改火车来煤信息异常！" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    //查询集样罐的采样编码
    @RequestMapping(value = "/qrySampleCodes/{machineType}/{command}")
    @ResponseBody
    public GridModel qrySampleCodes(@PathVariable String machineType, @PathVariable String command) {
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qrySampleCodes(machineType, command);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询集样罐状的采样编码
    @RequestMapping(value = "/qrySampleCodesByTransType/{machineType}/{command}/{transType}")
    @ResponseBody
    public GridModel qrySampleCodesByTransType(@PathVariable String machineType, @PathVariable String command, @PathVariable String transType) {
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qrySampleCodesByTransType(machineType, command, transType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //跳到选择采样编码后再提交操作命令页面
    @RequestMapping(value = "/gotoSubmitCommandPage")
    @ResponseBody
    public ModelAndView gotoSubmitCommandPage() {
        ModelAndView mav = new ModelAndView("/monitor/submitDeviceCommand");
        try {
            mav.addObject("message", "success");
        } catch (Exception e) {
            e.printStackTrace();
            mav.addObject("message", "fail");
        }
        return mav;
    }

    //查询称重监控明细信息
    @RequestMapping(value = "/areaPartList")
    @ResponseBody
    public List qryAreaPartList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity takeSampleRecEntity = SearchForm(TakeSampleRecEntity.class);
        //设置分页信息并计算记录开始和结束值
        takeSampleRecEntity.setPageRowIndex(getPageIndex(), getPageSize());
        List<AreaPartEntity> returnList = new ArrayList<AreaPartEntity>();
        AreaPartEntity areaPartEntity;
        //Map resultMap = new HashMap<>();
        Map resultMap = new TreeMap<>();
        int num = 0;
        for (int i = 1; i < 19; i++) {
            resultMap.put(i, 0);
        }
        List<TakeSampleRecEntity> list = monitorService.qryTakeSampleRec((TakeSampleRecEntity) takeSampleRecEntity);

        for (TakeSampleRecEntity element : list) {
            String[] splitRes = element.getSampleAreas().split(";");
            if (element != null) {
                for (int i = 0; i < splitRes.length; i++) {
                    num = 0;
                    if (Integer.parseInt(splitRes[i]) != 0) {
                        if (resultMap.containsKey(Integer.parseInt(splitRes[i]))) {
                            num = Integer.parseInt(String.valueOf(resultMap.get(Integer.parseInt(splitRes[i])))) + 1;
                            resultMap.remove(Integer.parseInt(splitRes[i]));
                            resultMap.put(Integer.parseInt(splitRes[i]), num);
                        } else {
                            resultMap.put(Integer.parseInt(splitRes[i]), 1);
                        }
                    }
                }
            }
        }
        /*
        List<Map.Entry<Integer, Integer>> infoIds =
                new ArrayList<Map.Entry<Integer, Integer>>(resultMap.entrySet());
        //排序前
        for (int i = 0; i < infoIds.size(); i++) {
            String id = infoIds.get(i).toString();
            System.out.println("排序前："+id);
        }
        //排序
        Collections.sort(infoIds, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                //return (o2.getValue() - o1.getValue());
                return (o1.getKey()).compareTo(o2.getKey());
            }
        });
        //排序后
        for (int i = 0; i < infoIds.size(); i++) {
            String id = infoIds.get(i).toString();
            System.out.println("排序后："+id);
        }
        */
        Set set = resultMap.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry<Integer, Integer> entry1 = (Map.Entry<Integer, Integer>) i.next();
            //System.out.println(entry1.getKey()+"=="+entry1.getValue());
            areaPartEntity = new AreaPartEntity();

            //排除有些采样机没采的点直接就送个0
            if (!String.valueOf(entry1.getKey()).equals("0")) {
                areaPartEntity.setAreaPart(String.valueOf(entry1.getKey()));
                areaPartEntity.setAreaNum(String.valueOf(entry1.getValue()));
                returnList.add(areaPartEntity);
            }

        }
        return returnList;
    }


    //使用新工作模式  modifyOneTrainInfo
    @RequestMapping(value = "/addNewWorkMode")
    @ResponseBody
    public Result addNewWorkMode() {
        Result result = new Result();
        try {
            WorkModeEntity workModeEntity = SearchForm(WorkModeEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            workModeEntity.setOpCode(opCode);

            monitorService.addNewWorkMode(workModeEntity);

            if (workModeEntity.getResCode() != null && workModeEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("修改系统工作模式成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("修改系统工作模式失败！" + workModeEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改系统工作模式异常！" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    //查询系统工作模式
    @RequestMapping(value = "/qryWorkModeInfo")
    @ResponseBody
    public String qryWorkModeInfo() {
        WorkModeEntity workModeEntity = monitorService.qryWorkModeInfo();
        ObjectMapper objectMapper = JacksonMapper.getInstance();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(workModeEntity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }


    //查询系统工作模式
    @RequestMapping(value = "/qryEmergencyWarnMsg")
    @ResponseBody
    public String qryEmergencyWarnMsg() {
        DeviceBroadEntity entity = SearchForm(DeviceBroadEntity.class);
        String json = "{}";
        try {
            DeviceBroadEntity deviceBroadEntity = monitorService.qryEmergencyWarnMsg(entity);
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            json = objectMapper.writeValueAsString(deviceBroadEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    //查询船舶入厂工作模式（谏壁）
    @RequestMapping(value = "/qryShipSampleWorkMode")
    @ResponseBody
    public String qryShipSampleWorkMode() {
        WorkModeEntity workModeEntity = monitorService.qryShipSampleWorkMode();
        ObjectMapper objectMapper = JacksonMapper.getInstance();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(workModeEntity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    //查询火车人工采样编码信息
    @RequestMapping(value = "/qryManSampleInfo")
    @ResponseBody
    public GridModel qryManSampleInfo() {
        ManualSampleEntity manualSampleEntity = SearchForm(ManualSampleEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryManSampleInfo(manualSampleEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询煤场信息
    @RequestMapping(value = "/qryMcInfo")
    @ResponseBody
    public GridModel qryMcInfo() {
        CoalPileInfoEntity coalPileInfoEntity = SearchForm(CoalPileInfoEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryMcInfo(coalPileInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询火车人工采样编码信息
    @RequestMapping(value = "/qryManInfo")
    @ResponseBody
    public GridModel qryManInfo() {
        ManualSampleEntity manualSampleEntity = SearchForm(ManualSampleEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryManInfo(manualSampleEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询汽车人工采样编码信息
    @RequestMapping(value = "/qryCarManSampleInfo")
    @ResponseBody
    public GridModel qryCarManSampleInfo() {
        ManualSampleEntity manualSampleEntity = SearchForm(ManualSampleEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCarManSampleInfo(manualSampleEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询入炉采样编码信息
    @RequestMapping(value = "/qryLcManSampleInfo")
    @ResponseBody
    public GridModel qryLcManSampleInfo() {
        ManualSampleEntity manualSampleEntity = SearchForm(ManualSampleEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryLcManSampleInfo(manualSampleEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //删除汽车注册记录
    @RequestMapping(value = "/editCarInfo/{recId}")
    @ResponseBody
    @Transactional
    public Result getCard(@PathVariable String recId) {
        Result result = new Result();
        try {
            TcpClient tcpClient = new TcpClient();
            String req = "0A:FF";
            String rsp = tcpClient.send(req);
            if (rsp != null) {
                String[] str = rsp.split(":");
                if (str.length == 3) {
                    result.setMsg(str[1]);
                    result.setSuccessful(true);
                } else {
                    result.setSuccessful(false);
                    result.setMsg("请重试！");
                }
            } else {
                result.setSuccessful(false);
                result.setMsg("请重试！");
            }
        } catch (Exception e) {
            result.setMsg("获取卡号异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //删除汽车注册记录
    @RequestMapping(value = "/getCardBySampling/{recId}")
    @ResponseBody
    @Transactional
    public Result getCardBySampling(@PathVariable String recId) {
        Result result = new Result();
        try {
            TcpClient tcpClient = new TcpClient();
            String req = "0A:FF";
            String rsp = tcpClient.sendSampling(req);
            if (rsp != null) {
                String[] str = rsp.split(":");
                if (str.length == 3) {
                    result.setMsg(str[1]);
                    result.setSuccessful(true);
                } else {
                    result.setSuccessful(false);
                    result.setMsg("请重试！");
                }
            } else {
                result.setSuccessful(false);
                result.setMsg("请重试！");
            }
        } catch (Exception e) {
            result.setMsg("获取卡号异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //跳转到打印翻车单
    @RequestMapping(value = "/printFCList/{trainNo}")
    @ResponseBody
    public ModelAndView printFCList(@PathVariable String trainNo) {
        ModelAndView mav = new ModelAndView("/monitor/printFCList");
        mav.addObject("trainNo", trainNo);
        return mav;
    }

    //跳转到打印翻车单选中部分
    @RequestMapping(value = "/printFCListPart/{trainNo}/{numstr}")
    @ResponseBody
    public ModelAndView printFCListPart(@PathVariable String trainNo, @PathVariable String numstr) {
        ModelAndView mav = new ModelAndView("/monitor/printFCListPart");
        mav.addObject("numstr", numstr);
        mav.addObject("trainNo", trainNo);
        return mav;
    }


    //跳转到打印抽检样采样单
    @RequestMapping(value = "/printCheckSampleList/{trainNo}/{attachBatchNo}")
    @ResponseBody
    public ModelAndView printCheckSampleList(@PathVariable String trainNo, @PathVariable String attachBatchNo) {
        ModelAndView mav = new ModelAndView("/monitor/printCheckSampleList");
        mav.addObject("trainNo", trainNo);
        mav.addObject("attachBatchNo", "1");//0表示没有抽检样  1表示有抽检样
        return mav;
    }

    //获取封装码
    @RequestMapping(value = "/getPackCode/{samplingCode}")
    @ResponseBody
    @Transactional
    public Result getPackCode(@PathVariable String samplingCode) {
        Result result = new Result();
        Map packCodeMap = new HashMap();
        try {
            //获取界面输入参数
            ManualSampleEntity manualSampleEntity = new ManualSampleEntity();

            manualSampleEntity.setSamplingCode(samplingCode);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            manualSampleEntity.setOpCode(opCode);
            //获取人工制样封装码
            monitorService.getPackCode(manualSampleEntity);
            //设置返回结果
            if (manualSampleEntity.getResCode() != null && manualSampleEntity.getResCode().equals("0")) {
                result.setMsg("获取人工制样封装码成功！");
                packCodeMap.put("manPackCode", manualSampleEntity.getManPackCode());
                result.setData(packCodeMap);
                result.setSuccessful(true);
            } else {
                result.setMsg("获取人工制样封装码失败:" + manualSampleEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("获取人工制样封装码异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询化验台账数据
     */
    @RequestMapping(value = "/qryViolaData")
    @ResponseBody
    public GridModel qryViolaData() {
        ViolaEntity violaEntity = SearchForm(ViolaEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryViolaData(violaEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查看车辆信息详情
    @RequestMapping(value = "/viewViolaDetail/{violaRecId}")
    @ResponseBody
    public ModelAndView viewViolaDetail(@PathVariable String violaRecId) {
        ModelAndView mav = new ModelAndView("/monitor/violaDetail");

        try {
            //查询
            ViolaEntity violaEntity = monitorService.viewViolaDetail(violaRecId);

            //获取对象转换成json字符串送到页面
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(violaEntity);

            mav.addObject("message", "success");
            mav.addObject("violaEntity", json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }

    //编辑违规信息
    @RequestMapping(value = "/editViolaDetail")
    @ResponseBody
    public Result editViolaDetail(@ModelAttribute ViolaEntity violaEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            violaEntity.setOpCode(opCode);

            //新增汽车信息
            monitorService.editViolaDetail(violaEntity);
            result.setSuccessful(true);
            result.setMsg("修改车辆信息成功！");
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改车辆信息失败！");
            e.printStackTrace();
        }
        return result;
    }

    //查询远光存查样柜
    @RequestMapping(value = "/qryYGCabinetInfo")
    @ResponseBody
    public GridModel qryYGCabinetInfo() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        YGCabinetEntity yGCabinetEntity = SearchForm(YGCabinetEntity.class);
        GridModel gridModel = monitorService.qryYGCabinetInfo(yGCabinetEntity);
        return gridModel;
    }

    //查询开元存查汇总样柜
    @RequestMapping(value = "/qryKYCabinetSumInfo")
    @ResponseBody
    public Result qryKYCabinetSumInfo() {
        Result result = new Result();
        try {
            CabinetEntity qryData = monitorService.qryKYCabinetSumInfo();
            result.setSuccessful(true);
            result.setMsg("查询成功");
            result.setData(qryData);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccessful(false);
            result.setMsg("查询开元存查样柜汇总接口失败：" + e.getMessage());
        }
        return result;
    }

    //查询远光存查汇总样柜
    @RequestMapping(value = "/qryYGCabinetSumInfo")
    @ResponseBody
    public Result qryYGCabinetSumInfo() {
        Result result = new Result();
        try {
            YGCabinetEntity yg = monitorService.qryYGCabinetSumInfo();
            result.setSuccessful(true);
            result.setMsg("查询成功");
            result.setData(yg);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccessful(false);
            result.setMsg("查询远光存查样柜汇总接口失败：" + e.getMessage());
        }
        return result;
    }

    //查询远光存查汇总样柜
    @RequestMapping(value = "/qryYGCabinetSumInfoExpiredCnt/{typeId}/{comeTag}")
    @ResponseBody
    public Result qryYGCabinetSumInfoExpiredCnt(@PathVariable String typeId, @PathVariable String comeTag) {
        Result result = new Result();
        try {
            YGCabinetEntity yg = monitorService.qryYGCabinetSumInfoExpiredCnt(typeId, comeTag);
            result.setSuccessful(true);
            result.setMsg("查询成功");
            result.setData(yg);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccessful(false);
            result.setMsg("查询远光存查样柜汇总接口失败：" + e.getMessage());
        }
        return result;
    }

    //跳转到修改工作模式的窗口
    @RequestMapping(value = "/ygCabinet4ApprView", method = RequestMethod.GET)
    public String gotoviewYGCabinetApprInfo() {
        return "/monitor/ygCabinet4ApprView";
    }

    @RequestMapping(value = "/validateCard/{cardId}/{cid}")
    @ResponseBody
    public List<CarInfoEntity> validateCard(@PathVariable String cardId, @PathVariable String cid) {
        CarInfoEntity carInfoEntity = new CarInfoEntity();
        carInfoEntity.setCardId(cardId);
        carInfoEntity.setCarId(cid);
        List<CarInfoEntity> list = null;
        try {
            list = monitorService.qryCarByCarIdOrCardId(carInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //合并汽车批次信息
    @RequestMapping(value = "/mergeCarBatchNo")
    @ResponseBody
    @Transactional
    public Result mergeCarBatchNo(HttpServletRequest req) {
        Result result = new Result();

        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String publicStr = req.getParameter("publicInfo");
        String updateStr = req.getParameter("updateInfo");

        try {
            //获取界面输入参数
            RegisterEntity registerEntity = new RegisterEntity();

            registerEntity.setUpdateString(updateStr);
            registerEntity.setPublicString(publicStr);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            registerEntity.setOpCode(opCode);

            //新增数据
            monitorService.mergeCarBatchNo(registerEntity);

            //设置返回结果
            if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
                result.setMsg(registerEntity.getResMsg());
                result.setSuccessful(true);
            } else {
                result.setMsg("车辆批次合并操作失败:" + registerEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("车辆批次合并操作异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //查询船运信息
    @RequestMapping(value = "/qryShipTransInfo")
    @ResponseBody
    public GridModel qryShipTransInfo() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        ShipEntity shipEntity = SearchForm(ShipEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryShipTransInfo(shipEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //蚌埠-查询船运批次信息
    @RequestMapping(value = "/qryShipBatch")
    @ResponseBody
    public GridModel qryShipBatch() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        ShipEntity shipEntity = SearchForm(ShipEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryShipBatch(shipEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询船资料信息
    @RequestMapping(value = "/qryShipInfo")
    @ResponseBody
    public GridModel qryShipInfo() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        ShipEntity shipEntity = SearchForm(ShipEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryShipInfo(shipEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询船运的货运信息
    @RequestMapping(value = "/qryShipCargoInfo")
    @ResponseBody
    public GridModel qryShipCargoInfo() {
        ShipEntity shipEntity = SearchForm(ShipEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryShipCargoInfo(shipEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }


    //查询船运的货运信息
    @RequestMapping(value = "/qryRegistShipCargoInfo")
    @ResponseBody
    public GridModel qryRegistShipCargoInfo() {
        ShipEntity shipEntity = SearchForm(ShipEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryShipCargoInfo(shipEntity);
            List<ShipEntity> RESULT=gridModel.getRows();
            List<ShipEntity> final_RESULT= new ArrayList();
            for(int i=0;i<RESULT.size();i++)
            {
               if(!RESULT.get(i).isBatch.equals("3"))
               {
                   final_RESULT.add(RESULT.get(i));
               }
            }
            gridModel.setRows(final_RESULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }



    @RequestMapping(value = "/qryShipWeightListInfo")
    @ResponseBody
    public GridModel qryShipWeightListInfo() {
        ShipEntity shipEntity = SearchForm(ShipEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryShipWeightListInfo(shipEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }


    //编辑船运信息
    @RequestMapping(value = "/editShipTransRec/{actionTag}")
    @ResponseBody
    public Result editShipTransRec(@ModelAttribute ShipEntity shipEntity, @PathVariable String actionTag) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            shipEntity.setOpCode(opCode);
            shipEntity.setActionTag(actionTag);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(shipEntity);
            shipEntity.setJsonString(json);
            monitorService.editShipTransRec(shipEntity);

            //设置返回结果
            if (shipEntity.getResCode() != null && shipEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("操作失败！" + shipEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作发生异常！");
            e.printStackTrace();
        }
        return result;
    }

    //编辑船信息
    @RequestMapping(value = "/submitEditShipInfo/{actionTag}")
    @ResponseBody
    public Result submitEditShipInfo(@ModelAttribute ShipEntity shipEntity, @PathVariable String actionTag) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            shipEntity.setOpCode(opCode);
            shipEntity.setActionTag(actionTag);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(shipEntity);
            shipEntity.setJsonString(json);
            monitorService.submitEditShipInfo(shipEntity);

            //设置返回结果
            if (shipEntity.getResCode() != null && shipEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("操作失败！" + shipEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作发生异常！");
            e.printStackTrace();
        }
        return result;
    }

    //编辑船运信息
    @RequestMapping(value = "/editShipCargoRec/{actionTag}")
    @ResponseBody
    public Result editShipCargoRec(@ModelAttribute ShipEntity shipEntity, @PathVariable String actionTag) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            shipEntity.setOpCode(opCode);
            shipEntity.setActionTag(actionTag);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(shipEntity);
            shipEntity.setJsonString(json);
            monitorService.editShipCargoRec(shipEntity);

            //设置返回结果
            if (shipEntity.getResCode() != null && shipEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("操作失败！" + shipEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作发生异常！");
            e.printStackTrace();
        }
        return result;
    }


    //编辑船运信息
    @RequestMapping(value = "/finishUnloadRec")
    @ResponseBody
    public Result finishUnloadRec(@ModelAttribute ShipEntity shipEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            shipEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(shipEntity);
            shipEntity.setJsonString(json);
            monitorService.finishUnloadRec(shipEntity);

            //设置返回结果
            if (shipEntity.getResCode() != null && shipEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("操作失败！" + shipEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作发生异常！");
            e.printStackTrace();
        }
        return result;
    }


    //查询船运分批详细信息
    @RequestMapping(value = "/qryShipBatchInfo")
    @ResponseBody
    public GridModel qryShipBatchInfo() {
        BatchNoInfoEntity batchNoInfoEntity = SearchForm(BatchNoInfoEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryShipBatchInfo(batchNoInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }


    //补录人工制样的封装码信息
    @RequestMapping(value = "/editSamplingResultRec")
    @ResponseBody
    public Result editSamplingResultRec(@ModelAttribute EditSamplingRptEntity editSamplingRptEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            editSamplingRptEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(editSamplingRptEntity);

            editSamplingRptEntity.setJsonString(json);
            monitorService.editSamplingResultRec(editSamplingRptEntity);

            //设置返回结果
            if (editSamplingRptEntity.getResCode() != null && editSamplingRptEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("操作失败！" + editSamplingRptEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作发生异常！");
            e.printStackTrace();
        }
        return result;
    }

    //删除汽车注册记录
    @RequestMapping(value = "/getCarCardId/{recId}")
    @ResponseBody
    @Transactional
    public Result getCarCardId(@PathVariable String recId) {
        Result result = new Result();
        try {
            TcpClient tcpClient = new TcpClient();
            String req = "0A:FF";
            String rsp = tcpClient.sendCarCardId(req);
            if (rsp != null) {
                String[] str = rsp.split(":");
                if (str.length == 3) {
                    result.setMsg(str[1]);
                    result.setSuccessful(true);
                } else {
                    result.setSuccessful(false);
                    result.setMsg("请重试！");
                }
            } else {
                result.setSuccessful(false);
                result.setMsg("请重试！");
            }
        } catch (Exception e) {
            result.setMsg("获取卡号异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/qryVirtualBatchInfoList")
    @ResponseBody
    public GridModel qryVirtualBatchInfoList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        SamplingRptEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);
//        samplingRptEntity.setBatchNoType("5");
        //设置分页信息并计算记录开始和结束值
        samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySamplingRptList(samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //系统外批次（新增）
    @RequestMapping(value = "/virtualBatchInfo/add")
    @ResponseBody
    public Result addVirtualBatchInfo(@ModelAttribute SamplingRptEntity samplingRptEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            samplingRptEntity.setOpCode(opCode);
            samplingRptEntity.setDoActionTag("ADD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(samplingRptEntity);
            samplingRptEntity.setJsonString(json);


            monitorService.addVirtualBatchInfoRecord(samplingRptEntity);

            //设置返回结果
            if (samplingRptEntity.getResCode() != null && samplingRptEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("新增系统外批次成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("新增系统外批次失败！" + samplingRptEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("新增系统外批次异常！");
            e.printStackTrace();
        }
        return result;
    }


    //系统外批次（修改）
    @RequestMapping(value = "/virtualBatchInfo/mod")
    @ResponseBody
    public Result modVirtualBatchInfo(@ModelAttribute SamplingRptEntity samplingRptEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            samplingRptEntity.setOpCode(opCode);
            samplingRptEntity.setDoActionTag("MOD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(samplingRptEntity);
            samplingRptEntity.setJsonString(json);

            monitorService.addVirtualBatchInfoRecord(samplingRptEntity);

            //设置返回结果
            if (samplingRptEntity.getResCode() != null && samplingRptEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("修改系统外批次信息成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("修改系统外批次信息失败！" + samplingRptEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改系统外批次信息异常！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/qryFurnaceBatchInfoList")
    @ResponseBody
    public GridModel qryFurnaceBatchInfoList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        SamplingRptEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);
        samplingRptEntity.setBatchNoType("9");
        samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySamplingRptList(samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //系统外批次（新增）
    @RequestMapping(value = "/furnaceBatchInfo/add")
    @ResponseBody
    public Result addFurnaceBatchInfo(@ModelAttribute SamplingRptEntity samplingRptEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            samplingRptEntity.setOpCode(opCode);
            samplingRptEntity.setDoActionTag("ADD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(samplingRptEntity);
            samplingRptEntity.setJsonString(json);


            monitorService.addFurnaceBatchInfoRecord(samplingRptEntity);

            //设置返回结果
            if (samplingRptEntity.getResCode() != null && samplingRptEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("新增入炉批次成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("新增入炉批次失败！" + samplingRptEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("新增入炉批次异常！");
            e.printStackTrace();
        }
        return result;
    }


    //系统外批次（修改）
    @RequestMapping(value = "/furnaceBatchInfo/mod")
    @ResponseBody
    public Result modFurnaceBatchInfo(@ModelAttribute SamplingRptEntity samplingRptEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            samplingRptEntity.setOpCode(opCode);
            samplingRptEntity.setDoActionTag("MOD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(samplingRptEntity);
            samplingRptEntity.setJsonString(json);

            monitorService.addFurnaceBatchInfoRecord(samplingRptEntity);

            //设置返回结果
            if (samplingRptEntity.getResCode() != null && samplingRptEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("修改入炉批次信息成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("修改入炉批次信息失败！" + samplingRptEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改入炉批次信息异常！");
            e.printStackTrace();
        }
        return result;
    }

    //批量删除多余的车厢信息  --by yangff 2016.03.22
    @RequestMapping(value = "/deleteMassTrainBody")
    @ResponseBody
    @Transactional
    public Result deleteMassTrainBody(HttpServletRequest req) {
        Result result = new Result();

        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //获取编辑数据 这里获取到的是json字符串
        String publicStr = req.getParameter("publicInfo");
        String updateStr = req.getParameter("updateInfo");

        try {
            //获取界面输入参数
            RegisterEntity registerEntity = new RegisterEntity();

            registerEntity.setUpdateString(updateStr);
            registerEntity.setPublicString(publicStr);

            //新增数据
            monitorService.deleteMassTrainBody(registerEntity);

            //设置返回结果
            if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("1000")) {
                result.setMsg(registerEntity.getResMsg());
                result.setSuccessful(true);
            } else {
                result.setMsg(registerEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;

    }


    //批量删除多余的车厢信息  --by yangff 2016.03.22
    @RequestMapping(value = "/operateTrains")
    @ResponseBody
    @Transactional
    public Result operateTrains(HttpServletRequest req) {
        Result result = new Result();

        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //获取编辑数据 这里获取到的是json字符串
        String publicStr = req.getParameter("publicInfo");
        String updateStr = req.getParameter("updateInfo");

        try {
            //获取界面输入参数
            RegisterEntity registerEntity = new RegisterEntity();

            registerEntity.setUpdateString(updateStr);
            registerEntity.setPublicString(publicStr);

            //新增数据
            monitorService.operateTrains(registerEntity);

            //设置返回结果
            if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("1000")) {
                result.setMsg(registerEntity.getResMsg());
                result.setSuccessful(true);
            } else {
                result.setMsg(registerEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;

    }







    //汽车抽检样分批
    @RequestMapping(value = "/carAttachBatchForBat")
    @ResponseBody
    @Transactional
    public Result carAttachBatchForBat(HttpServletRequest req) {
        Result result = new Result();

        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String publicStr = req.getParameter("publicInfo");
        String updateStr = req.getParameter("updateInfo");

        try {
            //获取界面输入参数
            RegisterEntity registerEntity = new RegisterEntity();

            registerEntity.setUpdateString(updateStr);
            registerEntity.setPublicString(publicStr);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            registerEntity.setOpCode(opCode);

            //新增数据
            monitorService.carAttachBatchForBat(registerEntity);

            //设置返回结果
            if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
                result.setMsg("抽样底样批次操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("抽样底样批次操作失败:" + registerEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("抽样底样批次操作异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //得到采样机气动信息
    @RequestMapping(value = "/getMakeSampleInfo/{samplingId}/{tag}")
    @ResponseBody
    public String getMakeSampleInfo(@PathVariable String samplingId, @PathVariable String tag) {
        StringBuffer result = new StringBuffer();
        StringBuffer searchHeader = new StringBuffer();
        try {
            if ("1".equals(tag)) {
                searchHeader.append("DECODE(A.BATCH_NO_TYPE, '1', '13mm≤粒度＜100mm', '9', '0mm≤粒度＜6mm') ");
            } else if ("2".equals(tag)) {
                searchHeader.append("'褐煤'");
            }
            TableColumnByColumn tableColumnByColumn = new TableColumnByColumn();
            tableColumnByColumn.setShowCol(searchHeader.toString());
            tableColumnByColumn.setTableName("BATCH_NO_INFO A");
            tableColumnByColumn.setCondCol("A.SAMPLE_CODE");
            tableColumnByColumn.setCondValue(samplingId);
            tableColumnByColumn.setCondOperate("=");
            tableColumnByColumn.setCondType("1");
            tableColumnByColumn.setCondCnt(1);
            result.append(commonService.getTableColumnByColumn(tableColumnByColumn));
        } catch (Exception e) {
            result.append("查询异常");
            e.printStackTrace();
        }
        return result.toString();
    }

    //结束或删除船运批次(存储过程处理)
    @RequestMapping(value = "/compOrDelShipBatchPro")
    @ResponseBody
    @Transactional
    public Result compOrDelShipBatchPro() {
        Result result = new Result();
        try {
            //获取界面输入参数
            ShipEntity shipEntity = SearchForm(ShipEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            shipEntity.setOpCode(opCode);

            //结束或删除船运批次
            monitorService.compOrDelShipBatchPro(shipEntity);
            //设置返回结果
            if (shipEntity.getResCode() != null && shipEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作提交失败:" + shipEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作提交异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }

        return result;
    }


    //编辑卸煤通道信息
    @RequestMapping(value = "/editCarQueue/{actionTag}")
    @ResponseBody
    public Result editCarQueue(@ModelAttribute XmTransSetEntity xmTransSetEntity, @PathVariable String actionTag) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            xmTransSetEntity.setOpCode(opCode);
            xmTransSetEntity.setActionTag(actionTag);

            monitorService.editCarQueue(xmTransSetEntity);
            //设置返回结果
            if (xmTransSetEntity.getResCode() != null && xmTransSetEntity.getResCode().equals("1000")) {
                result.setSuccessful(true);
                result.setMsg("卸煤队列操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("修改卸煤队列失败！" + xmTransSetEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改卸煤队列失败！");
            e.printStackTrace();
        }
        return result;
    }


    //卸煤沟参数查询画面
    @RequestMapping(value = "/xmtrans/list", method = RequestMethod.GET)
    public String gotoxmtrans() {
        return "/monitor/xmTransList";
    }

    //编辑卸煤沟参数
    @RequestMapping(value = "/editxmtrans")
    @ResponseBody
    public ModelAndView gotoeditxmtrans(@PathVariable String detail) {

        ModelAndView mav = new ModelAndView("/monitor/xmTransDetail");
        mav.addObject("XmTransSetEntity", detail);
        logger.error(detail);
        return mav;
    }

    //卸煤沟信息查询
    @RequestMapping(value = "/qryXmTransList")
    @ResponseBody
    public GridModel qryXmTransList() {
        //查询
        GridModel gridModel = null;
        try {
            XmTransSetEntity xmTransSetEntity = null;
            gridModel = monitorService.qryXmTransList(xmTransSetEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查看卸煤沟详细信息
    @RequestMapping(value = "/xmTransDetail/{xmid}")
    @ResponseBody
    public ModelAndView xmTransList(@PathVariable String xmid) {
        ModelAndView mav = new ModelAndView("/monitor/xmTransDetail");
        try {
            GridModel gridModel = null;
            //查询
            XmTransSetEntity xmTransSetEntity_detail = new XmTransSetEntity();
            xmTransSetEntity_detail.setCoalDichcd(xmid);
            gridModel = monitorService.qryXmTransList(xmTransSetEntity_detail);
            XmTransSetEntity xmTransSetEntity = (XmTransSetEntity) gridModel.getRows().get(0);
            //获取对象转换成json字符串送到页面
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(xmTransSetEntity);

            mav.addObject("message", "success");
            mav.addObject("XmTransSetEntity", json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }


    //编辑卸煤通道信息
    @RequestMapping(value = "/editxmTransDetail/{actionTag}")
    @ResponseBody
    public Result editxmTransDetail(@ModelAttribute XmTransSetEntity xmTransSetEntity, @PathVariable String actionTag) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            xmTransSetEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(xmTransSetEntity);
            xmTransSetEntity.setJsonString(json);
            xmTransSetEntity.setActionTag(actionTag);

            monitorService.editxmTransDetail(xmTransSetEntity);
            //设置返回结果
            if (xmTransSetEntity.getResCode() != null && xmTransSetEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("修改卸煤通道信息成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("修改卸煤通道信息失败！" + xmTransSetEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改卸煤通道信息失败！");
            e.printStackTrace();
        }
        return result;
    }

    //皮带秤计量信息查询
    @RequestMapping(value = "/qryBeltBalanceInfo")
    @ResponseBody
    public GridModel qryBeltBalanceInfo() {
        BatchNoInfoEntity beltBalanceEntity = SearchForm(BatchNoInfoEntity.class);
        GridModel gridModel = monitorService.qryBeltBalanceInfo(beltBalanceEntity);
        return gridModel;
    }


    //提交上传燃料MIS的批次（衡丰）
    @RequestMapping(value = "/uploadToMis")
    @ResponseBody
    public Result uploadToMis() {
        Result result = new Result();
        try {
            UploadToMisEntity uploadToMisEntity = SearchForm(UploadToMisEntity.class);
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            uploadToMisEntity.setOpCode(opCode);
            monitorService.uploadToMis(uploadToMisEntity);
            //设置返回结果
            if (uploadToMisEntity.getResCode() != null && uploadToMisEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("确认上传燃料MIS成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("确认上传燃料MIS失败！" + uploadToMisEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("确认上传燃料MIS异常！" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    //提交上传燃料MIS的批次（哈平南远光MIS）
    @RequestMapping(value = "/uploadToMisYg")
    @ResponseBody
    public Result uploadToMisYg() {
        Result result = new Result();
        try {
            UploadToMisEntity uploadToMisEntity = SearchForm(UploadToMisEntity.class);
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            uploadToMisEntity.setOpCode(opCode);
            monitorService.uploadToMisYg(uploadToMisEntity);
            //设置返回结果
            if (uploadToMisEntity.getResCode() != null && uploadToMisEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("确认上传燃料MIS成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("确认上传燃料MIS失败！" + uploadToMisEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("确认上传燃料MIS异常！" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    //编辑汽车违规信息
    @RequestMapping(value = "/addViolateRec")
    @ResponseBody
    public Result addViolateRecord(@ModelAttribute ViolaEntity violaEntity) {
        Result result = new Result();
        try {

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            violaEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(violaEntity);
            violaEntity.setJsonStr(json);


            monitorService.addViolateRecord(violaEntity);
            result.setSuccessful(true);
            result.setMsg("新增汽车违规信息成功！");
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("新增汽车违规信息异常！");
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 蚌埠新增船煤批次
     */
    @RequestMapping(value = "/addNewShipBatch")
    @ResponseBody
    @Transactional
    public Result addNewShipBatch() {
        Result result = new Result();

        try {
            ShipEntity shipEntity = SearchForm(ShipEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            shipEntity.setOpCode(opCode);

            //编辑数据
            monitorService.addNewShipBatch(shipEntity);

            //设置返回结果
            if (shipEntity.getResCode() != null && shipEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:" + shipEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 蚌埠船煤操作批次状态
     */
    @RequestMapping(value = "/operateBatchStatus")
    @ResponseBody
    @Transactional
    public Result operateBatchStatus() {
        Result result = new Result();

        try {
            ShipEntity shipEntity = SearchForm(ShipEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            shipEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(shipEntity);
            shipEntity.setJsonStr(json);

            //编辑数据
            monitorService.operateBatchStatus(shipEntity);

            //设置返回结果
            if (shipEntity.getResCode() != null && shipEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:" + shipEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //补录人工采样信息
    @RequestMapping(value = "/editSampleResultRec")
    @ResponseBody
    public Result editSampleResultRec(@ModelAttribute SampleRptEntity sampleRptEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            sampleRptEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(sampleRptEntity);

            sampleRptEntity.setJsonString(json);

            monitorService.editSampleResultRec(sampleRptEntity);

            //设置返回结果
            if (sampleRptEntity.getResCode() != null && sampleRptEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("操作失败！" + sampleRptEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作发生异常！");
            e.printStackTrace();
        }
        return result;
    }

    //蚌埠校准船运卸煤吨位
    @RequestMapping(value = "/modifyAllNetQty")
    @ResponseBody
    public Result modifyAllNetQty(@ModelAttribute ShipEntity shipEntity) {
        Result result = new Result();
        result.setSuccessful(true);
        result.setMsg("操作成功！");
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            shipEntity.setOpCode(opCode);
            monitorService.modifyAllNetQty(shipEntity);

        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作发生异常！");
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/qryAccessControlInfoList")
    @ResponseBody
    public GridModel qryAccessControlInfoList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        AccessControlEntity accessControlEntity = SearchForm(AccessControlEntity.class);
        accessControlEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryAccessControlInfoList(accessControlEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    @RequestMapping(value = "/qrySampleDetailInfo")
    @ResponseBody
    public GridModel qrySampleDetailInfo() {

        SampleTraceEntity sampleTraceEntity = SearchForm(SampleTraceEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = monitorService.qrySampleDetailList(sampleTraceEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //煤样信息跟踪-存查批次信息
    @RequestMapping(value = "/qrySampleBatchNoInfo")
    @ResponseBody
    public Result qrySampleBatchNoInfo() {
        BatchNoInfoEntity batchNoInfoEntity = SearchForm(BatchNoInfoEntity.class);
        Result result = new Result();
        try {
            BatchNoInfoEntity qryData = monitorService.qrySampleBatchNoInfo(batchNoInfoEntity);
            result.setSuccessful(true);
            result.setMsg("查询成功");
            result.setData(qryData);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccessful(false);
            result.setMsg("查询煤样批次汇总信息失败：" + e.getMessage());
        }
        return result;
    }


    //常州对入炉批次开始结束时间调整
    @RequestMapping(value = "/modifyBatchNoInfo")
    @ResponseBody
    public Result modifyBatchNoInfo(@ModelAttribute SamplingRptEntity samplingRptEntity) {
        Result result = new Result();
        result.setSuccessful(true);
        result.setMsg("操作成功！");
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            samplingRptEntity.setOpCode(opCode);

            monitorService.modifyBatchNoInfo(samplingRptEntity);

            if (samplingRptEntity.getResCode() != null && samplingRptEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("操作失败！" + samplingRptEntity.getResMsg());
            }

        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作发生异常！");
            e.printStackTrace();
        }
        return result;
    }

    //修改批次重量信息 20170207
    @RequestMapping(value = "/editBatchWeightInfo")
    @ResponseBody
    public Result editBatchWeightInfo(@ModelAttribute SampleRptEntity sampleRptEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            sampleRptEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(sampleRptEntity);

            sampleRptEntity.setJsonString(json);

            monitorService.editBatchWeightInfo(sampleRptEntity);

            //设置返回结果
            if (sampleRptEntity.getResCode() != null && sampleRptEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("操作失败！" + sampleRptEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作发生异常！");
            e.printStackTrace();
        }
        return result;
    }

    //编辑汽车来煤信息（新增）
    @RequestMapping(value = "/manualCarBatchInfo")
    @ResponseBody
    public Result manualCarBatchInfo(@ModelAttribute BatchNoInfoEntity batchNoInfoEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            batchNoInfoEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(batchNoInfoEntity);
            batchNoInfoEntity.setJsonString(json);

            monitorService.manualCarBatchInfo(batchNoInfoEntity);

            //设置返回结果
            if (batchNoInfoEntity.getResCode() != null && batchNoInfoEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("处理汽车来煤批次信息成功！{" + batchNoInfoEntity.getResMsg() + "}");
            } else {
                result.setSuccessful(false);
                result.setMsg("处理汽车来煤批次信息失败！" + batchNoInfoEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("处理汽车来煤批次信息异常！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/qryManualBatchInfo")
    @ResponseBody
    public GridModel qryManualBatchInfo() {

        BatchNoInfoEntity batchNoInfoEntity = SearchForm(BatchNoInfoEntity.class);

        batchNoInfoEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryManualCarBatchList(batchNoInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //菜单跳转到汽车锁车页面（给织金使用）
    @RequestMapping(value = "/carManage/lockCarRecord", method = RequestMethod.GET)
    public String gotoLockCarRecord() {
        return "/monitor/carLockRecord";
    }

    //菜单跳转到汽车来煤锁车登记页面（给织金使用）
    @RequestMapping(value = "/record/addLockCarRec", method = RequestMethod.GET)
    public String gotoAddLockCarRecord() {
        return "/monitor/addLockCarRecord";
    }

    //菜单跳转到汽车锁车历史记录查询
    @RequestMapping(value = "/carManage/lockCarRecordList", method = RequestMethod.GET)
    public String gotoLockCarRecordList() {
        return "/monitor/carLockRecordList";
    }

    //编辑锁车页面（给织金使用）
    @RequestMapping(value = "/addLockCarRec")
    @ResponseBody
    public Result addLockCarRecord(@ModelAttribute CarLockInfoEntity carLockInfoEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carLockInfoEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carLockInfoEntity);
            carLockInfoEntity.setJsonStr(json);

            monitorService.addLockCarRecord(carLockInfoEntity);

            //设置返回结果
            if (carLockInfoEntity.getResCode() != null && carLockInfoEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("操作失败！" + carLockInfoEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作失败,请检查！");
            e.printStackTrace();
        }
        return result;
    }

    //查询锁车记录（给织金使用）
    @RequestMapping(value = "/lockCarList")
    @ResponseBody
    public GridModel qryLockCarList() {
        CarLockInfoEntity carLockInfoEntity = SearchForm(CarLockInfoEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryLockCarList(carLockInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }


    //存查样柜提交样品延期清样
    @RequestMapping(value = "/submitCabinetDelay")
    @ResponseBody
    public Result submitToDelay() {
        Result result = new Result();
        try {
            CabinetEntity cabinetEntity = SearchForm(CabinetEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            cabinetEntity.setOpCode(opCode);

            //保存审批结果
            monitorService.submitCabinetDelay(cabinetEntity);

            //设置返回结果
            if (cabinetEntity.getResCode() != null && cabinetEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("提交延期清样成功");
            } else {
                result.setSuccessful(false);
                result.setMsg("提交延期清样失败！" + cabinetEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("提交延期清样异常！");
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/qryCabinetDelayInfo")
    @ResponseBody
    public GridModel qryCabinetDelayRecInfo() {

        CabinetEntity cabinetEntity = SearchForm(CabinetEntity.class);

        cabinetEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCabinetDelayRecList(cabinetEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //写码程序,yangfeifei
    @RequestMapping(value = "/writePackCode/{packCode}")
    @ResponseBody
    @Transactional
    public Result wirtePackCode(@PathVariable String packCode) {
        Result result = new Result();
        try {
            TcpClient tcpClient = new TcpClient();
            String req = "0A:" + packCode + ":FF";
            String rsp = tcpClient.sendSamplingPackCode(req);
            if (rsp != null) {
                String[] str = rsp.split(":");
                if (str.length == 2) {
                    result.setMsg(str[1]);
                    result.setSuccessful(true);
                } else {
                    result.setSuccessful(false);
                    result.setMsg("请重试！");
                }
            } else {
                result.setSuccessful(false);
                result.setMsg("请重试！");
            }
        } catch (Exception e) {
            result.setMsg("芯片码写码异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/massSaveErrInfo")
    @ResponseBody
    public Result massSaveErrInfo() {
        Result result = new Result();
        DeviceErrEntity deviceErrEntity = SearchForm(DeviceErrEntity.class);

        //获取登录操作员的ID
        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        deviceErrEntity.setOpCode(opCode);

        try {
            monitorService.massSaveErrInfo(deviceErrEntity);
            if (deviceErrEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("批量确认故障信息成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("批量确认故障信息失败！");
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("批量确认故障信息失败！");
            e.printStackTrace();
        }
        return result;
    }


    //跳转采样报告录入
    @RequestMapping(value = "/gotoEditSampleReport", method = RequestMethod.GET)
    public String qrySampleReport() {
        return "/report/editSampleReport";
    }

    //跳转制样报告录入
    @RequestMapping(value = "/gotoEditSamplingReport", method = RequestMethod.GET)
    public String qrySamplingReport() {
        return "/report/editSamplingReport";
    }

    //录入采样报告
    @RequestMapping(value = "/editSampleReport")
    @ResponseBody
    public Result editSampleReport() {
        Result result = new Result();
        SampleRptEntity sampleRptEntity = SearchForm(SampleRptEntity.class);
        try {
            monitorService.editSampleReport(sampleRptEntity);
            if (sampleRptEntity.getResCode() != null && sampleRptEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("报告录入操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("报告录入操作失败！" + sampleRptEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作发生异常！");
            e.printStackTrace();
        }
        return result;
    }


    //录入采样报告
    @RequestMapping(value = "/dealEmergencyWarn")
    @ResponseBody
    public Result dealEmergencyWarn() {
        Result result = new Result();
        DeviceBroadEntity deviceBroadEntity = SearchForm(DeviceBroadEntity.class);

        //获取登录操作员的ID
        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        deviceBroadEntity.setOpCode(opCode);

        try {
            monitorService.dealEmergencyWarn(deviceBroadEntity);
            if (deviceBroadEntity.getResCode() != null && deviceBroadEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("处理成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("处理失败！" + deviceBroadEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作发生异常！");
            e.printStackTrace();
        }
        return result;
    }

    //录入制样报告
    @RequestMapping(value = "/editSamplingReport")
    @ResponseBody
    public Result editSamplingReport() {
        Result result = new Result();
        SamplingRptEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);
        try {
            monitorService.editSamplingReport(samplingRptEntity);
            if (samplingRptEntity.getResCode() != null && samplingRptEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("报告录入操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("报告录入操作失败！" + samplingRptEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作发生异常！");
            e.printStackTrace();
        }
        return result;
    }

    //大开单点登录
    @RequestMapping(value = "/ssologin", method = RequestMethod.GET)
    public void gotoSSlogin(HttpServletRequest req, HttpServletResponse resp) {
        doLogin(req, resp);
    }

    //谏壁单点登录
    @RequestMapping(value = "/ssologinJB", method = RequestMethod.GET)
    public void gotoSSloginJB(HttpServletRequest req, HttpServletResponse resp) {
        doLoginJB(req, resp);
    }

    private void doLoginJB(HttpServletRequest req, HttpServletResponse resp) {
        String userNameIn = req.getParameter("username");
        String today = UtilTool.getThisDate();
        String keyString = req.getParameter("key");

        String tokenKey = Constant.getConstVal("SSO_Token"); //约定令牌码
        String md5Str = MD5Tool.encrypt(userNameIn + today + tokenKey);

        System.out.println("input param: username=" + userNameIn + "key=" + keyString);
        boolean isSucc = true;
        if (md5Str.equals(keyString)) {
            try {
                String backurl = Constant.getConstVal("SSO_ReqUrl");
                String qryPwdFromDb = monitorService.qryPwdByUserName(userNameIn);
                String captcha_key = "a";
                Boolean rememberMe = true;
                req.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, captcha_key);
                Subject subject = SecurityUtils.getSubject();
                CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken();
                token.setUsername(userNameIn);
                token.setPassword(qryPwdFromDb.toCharArray());
                token.setCaptcha(captcha_key);
                token.setRememberMe(rememberMe);
                subject.login(token);
                try {
                    req.getRequestDispatcher(backurl).forward(req, resp);
                } catch (Exception e) {
                    System.err.println(userNameIn + "，本地构建session失败！");
                    isSucc = false;
                    e.printStackTrace();
                }
            } catch (Throwable e) {
                isSucc = false;
                e.printStackTrace();
            }
        }

        if (!isSucc) {
            PrintWriter out = null;
            String title = "登录异常";
            String heading1 = "异常提示";
            resp.setContentType("text/html;charset=GB2312");
            try {
                out = resp.getWriter();
                out.print("<HTML><HEAD><TITLE>" + title + "</TITLE>");
                out.print("</HEAD><BODY>");
                out.print(heading1);
                out.println("<h4><p>" + userNameIn + " 登录异常!" + "</h4>");
                out.print("</BODY></HTML>");
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }

    @RequestMapping(value = "/doEncrypt", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> doEncrypt(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String> retMap = new HashMap<>();
        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String userNameIn = shiroUser.getLoginName();
        String today = UtilTool.getThisDate();
        String keyString = Constant.getConstVal("SSO_Token");
        String NHScadaUrl = Constant.getConstVal("NHScada_URL");
        retMap.put("username", userNameIn);
        retMap.put("key", MD5Tool.encrypt(userNameIn + today + keyString));
        retMap.put("NHScadaUrl", NHScadaUrl);
        return retMap;
    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp) {
        String userNameIn = req.getParameter("user");
        String aesString = req.getParameter("token");
        String time = req.getParameter("time");
        String key = Constant.getConstVal("SSO_AESKey");
        int timeBetween = 30;
        try {
            timeBetween = Integer.parseInt(Constant.getConstVal("SSO_Timeout"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String reqCont = AESTool.decrypt(aesString, key);

        System.out.println("userNameIn=" + userNameIn + "aesString=" + aesString + "time=" + time + "key=" + key
                + "reqCont=" + reqCont);
        boolean isSucc = true;
        JSONObject reqParamMap;
        if (reqCont != null) {
            try {
                reqParamMap = JSONObject.parseObject(reqCont);
                if (reqParamMap != null) {
                    String username = reqParamMap.getString("username");
                    String password = reqParamMap.getString("password");
                    String timestamp = reqParamMap.getString("timestamp");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    boolean timeRequire = true;
                    try {
                        Date date = sdf.parse(timestamp);
                        Date now = new Date();
                        //if(Math.abs(date.getTime()-now.getTime()) > 30){
                        if (now.getTime() - date.getTime() > timeBetween * 1000) {
                            System.out.println("the between time succ:" + now.getTime() + "-" + date.getTime() + "-" + (now.getTime() - date.getTime()));
                            timeRequire = false;
                        } else {
                            System.out.println("the between time failure:" + now.getTime() + "-" + date.getTime() + "-" + (now.getTime() - date.getTime()));
                        }
                    } catch (ParseException e) {
                        timeRequire = false;
                        e.printStackTrace();
                    }

                    if (timeRequire) {
                        String backurl = reqParamMap.getString("backurl");
                        System.out.println(username + "," + password + "," + timestamp + "," + backurl);
                        String ladpUrl = Constant.getConstVal("LADP_URL");
                        String domain = Constant.getConstVal("LADP_DOMAIN");
                        String user = username.indexOf(domain) > 0 ? username : domain + username;
                        Hashtable env = new Hashtable();
                        env.put(Context.SECURITY_AUTHENTICATION, "simple");
                        env.put(Context.SECURITY_PRINCIPAL, user);
                        env.put(Context.SECURITY_CREDENTIALS, password);
                        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
                        env.put(Context.PROVIDER_URL, ladpUrl);
                        try {
                            //LdapContext ctx = new InitialLdapContext(env, null);
                            System.out.println("LADP 验证成功！");
                            String qryPwdFromDb = monitorService.qryPwdByUserName(username);
                            String captcha_key = "a";
                            Boolean rememberMe = true;
                            req.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, captcha_key);
                            Subject subject = SecurityUtils.getSubject();
                            CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken();
                            token.setUsername(username);
                            token.setPassword(password.toCharArray());
                            token.setCaptcha(captcha_key);
                            token.setRememberMe(rememberMe);
                            subject.login(token);
                            try {
                                //resp.sendRedirect(backurl);
                                req.getRequestDispatcher(backurl).forward(req, resp);
                            } catch (Exception e) {
                                System.err.println(username + "，本地构建session失败！");
                                isSucc = false;
                                e.printStackTrace();
                            }
                        } catch (Throwable err) {
                            isSucc = false;
                            err.printStackTrace();
                            System.err.println(username + "，LADP 验证失败！");
                        }
                    } else {
                        isSucc = false;
                    }
                }
            } catch (Throwable e) {
                isSucc = false;
                e.printStackTrace();
            }
        }

        if (!isSucc) {
            PrintWriter out = null;
            String title = "登录异常";
            String heading1 = "异常提示";
            resp.setContentType("text/html;charset=GB2312");
            try {
                out = resp.getWriter();
                out.print("<HTML><HEAD><TITLE>" + title + "</TITLE>");
                out.print("</HEAD><BODY>");
                out.print(heading1);
                out.println("<h4><p>" + userNameIn + " 登录异常!" + "</h4>");
                out.print("</BODY></HTML>");
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }

    //九江前台记录皮带秤信息
    @RequestMapping(value = "/saveBeltWeightInfo")
    @ResponseBody
    public void saveBeltWeightInfo() {
        BeltWeightEntity beltWeightEntity = SearchForm(BeltWeightEntity.class);
        try {
            monitorService.saveBeltWeightInfo(beltWeightEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //九江前台记录皮带秤信息
    @RequestMapping(value = "/qryBeltWeightInfo")
    @ResponseBody
    public GridModel qryBeltWeightInfo() {
        BaseEntity beltWeightEntity = SearchForm(BeltWeightEntity.class);
        //设置分页信息并计算记录开始和结束值
        beltWeightEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = new GridModel();

        try {
            gridModel = monitorService.qryBeltWeightInfo((BeltWeightEntity) beltWeightEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //九江-船运皮带秤批次计量 信息查询
    //liuzh 2020-02-12
    @RequestMapping(value = "/qryShipBatchQtyInfo")
    @ResponseBody
    public GridModel qryShipBatchQtyInfo() {
        BaseEntity shipBatchQtyEntity = SearchForm(ShipBatchQtyEntity.class);
        //设置分页信息
        shipBatchQtyEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = new GridModel();

        //查询
        try {
            gridModel = monitorService.qryShipBatchQtyInfo((ShipBatchQtyEntity) shipBatchQtyEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    @RequestMapping(value = "/qryBeltWeightInfo4Qz")
    @ResponseBody
    public GridModel qryBeltWeightInfo4Qz() {
        BaseEntity beltWeightEntity = SearchForm(BeltWeightEntity.class);
        //设置分页信息并计算记录开始和结束值
        GridModel gridModel = new GridModel();
        try {
            gridModel = monitorService.qryBeltWeightRec4Qz((BeltWeightEntity) beltWeightEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询汽车来煤信息
    @RequestMapping(value = "/carTransRecordList4DK")
    @ResponseBody
    public GridModel qryCarTransRecordList4DK() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity carTransRecordEntity = SearchForm(CarTransRecordEntity.class);

        //设置分页信息并计算记录开始和结束值
        carTransRecordEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCarTransRecordList4DK((CarTransRecordEntity) carTransRecordEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //确认分批操作
    @RequestMapping(value = "/doBatch")
    @ResponseBody
    @Transactional
    public Result doBatch(HttpServletRequest req) {
        Result result = new Result();
        CarTransRecordEntity carTransRecordEntity = new CarTransRecordEntity();

        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String updateStr = req.getParameter("updateInfo");

        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carTransRecordEntity.setOpCode(opCode);
            carTransRecordEntity.setJsonString(updateStr);

            monitorService.doBatch(carTransRecordEntity);

            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("分批成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("分批失败！" + carTransRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setMsg("分批操作异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //查询化验编码
    @RequestMapping(value = "/qryManInfo4LC")
    @ResponseBody
    public GridModel qryManInfo4LC() {
        ManualSampleEntity manualSampleEntity = SearchForm(ManualSampleEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryManInfo4LC(manualSampleEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }


    //化验编码打印计数
    @RequestMapping(value = "/addPirntNum")
    @ResponseBody
    public Result addPirntNum(HttpServletRequest req) {
        Result result = new Result();
        result.setSuccessful(true);
        result.setMsg("更新打印计数成功");
        try {
            String laborCode = req.getParameter("laborCode");
            ManualSampleEntity manualSampleEntity = new ManualSampleEntity();
            manualSampleEntity.setLaborCode(laborCode);
            monitorService.addPrintNum(manualSampleEntity);
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作异常！");
            e.printStackTrace();
        }
        return result;
    }


    //聊城查询气动存查样柜信息-取样、弃样
    @RequestMapping(value = "/qryCabinetSampleList4LC")
    @ResponseBody
    public GridModel qryCabinetSampleList4LC() {
        CabinetEntity cabinetEntity = SearchForm(CabinetEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCabinetSampleList4LC(cabinetEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //系统外批次（删除）
    @RequestMapping(value = "/furnaceBatchInfo/del")
    @ResponseBody
    public Result delFurnaceBatchInfo(@ModelAttribute SamplingRptEntity samplingRptEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            samplingRptEntity.setOpCode(opCode);
            samplingRptEntity.setDoActionTag("DEL");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(samplingRptEntity);
            samplingRptEntity.setJsonString(json);

            monitorService.addFurnaceBatchInfoRecord(samplingRptEntity);

            //设置返回结果
            if (samplingRptEntity.getResCode() != null && samplingRptEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("删除入炉批次信息成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("删除入炉批次信息失败！" + samplingRptEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("删除入炉批次信息异常！");
            e.printStackTrace();
        }
        return result;
    }


    //哈平南入炉煤种上百种，所以单独一张入炉煤种表coal_type_rl
    @RequestMapping(value = "/qryFurnaceBatchInfoList4Hpn")
    @ResponseBody
    public GridModel qryFurnaceBatchInfoList4Hpn() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        SamplingRptEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);
        samplingRptEntity.setBatchNoType("9");
        samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySamplingRptList4Hpn(samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //九江轮船皮带计量
    @RequestMapping(value = "/manualControlBeltWeight")
    @ResponseBody
    @Transactional
    public Result manualControlBeltWeight() {
        Result result = new Result();
        try {
            //获取界面输入参数
            ShipEntity shipEntity = SearchForm(ShipEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            shipEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(shipEntity);
            shipEntity.setJsonStr(json);

            monitorService.manualControlBeltWeight(shipEntity);

            result.setMsg("处理成功!");
            result.setSuccessful(true);
        } catch (Exception e) {
            result.setMsg("处理异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //批次抽查信息页面
    @RequestMapping(value = "/compareBatchInfo", method = RequestMethod.GET)
    public String gotoCompareBatchInfo() {
        return "/monitor/compareBatchInfo";
    }

    //新增汽车车卡信息（便于存储过程记录日志） xxs20180427
    @RequestMapping(value = "/editCarInfoNew/ADD")
    @ResponseBody
    public Result addCarInfoNew(@ModelAttribute CarInfoEntity carInfoEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carInfoEntity.setOpCode(opCode);
            carInfoEntity.setBlkLstFlg("0");//新增默认不是黑名单
            carInfoEntity.setDoActionTag("ADD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carInfoEntity);
            carInfoEntity.setJsonString(json);
            //新增车卡信息
            monitorService.addCarInfoNew(carInfoEntity);
            //设置返回结果
            if (carInfoEntity.getResCode() != null && carInfoEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("新增车卡信息成功！{" + carInfoEntity.getResMsg() + "}");
            } else {
                result.setSuccessful(false);
                result.setMsg("新增车卡信息失败！" + carInfoEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("新增车卡信息失败！");
            e.printStackTrace();
        }
        return result;
    }


    //修改汽车车卡信息（便于存储过程记录日志） xxs20180427
    @RequestMapping(value = "/editCarInfoNew/MOD")
    @ResponseBody
    public Result modCarInfoNew(@ModelAttribute CarInfoEntity carInfoEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carInfoEntity.setOpCode(opCode);
            carInfoEntity.setBlkLstFlg("0");
            carInfoEntity.setDoActionTag("MOD");//修改标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carInfoEntity);
            carInfoEntity.setJsonString(json);
            //新增车卡信息
            monitorService.addCarInfoNew(carInfoEntity);
            //设置返回结果
            if (carInfoEntity.getResCode() != null && carInfoEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("修改车卡信息成功！{" + carInfoEntity.getResMsg() + "}");
            } else {
                result.setSuccessful(false);
                result.setMsg("修改车卡信息失败！" + carInfoEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改车卡信息失败！");
            e.printStackTrace();
        }
        return result;
    }

    //删除汽车车卡信息（便于存储过程记录日志） xxs20180427
    @RequestMapping(value = "/deleteCarRegInfoNew/{recId}")
    @ResponseBody
    @Transactional
    public Result deleteCarRegInfoNew(@PathVariable String recId) {
        Result result = new Result();
        try {
            //获取界面输入参数
            CarInfoEntity carInfoEntity = new CarInfoEntity();
            carInfoEntity.setRecId(recId);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carInfoEntity.setOpCode(opCode);
            carInfoEntity.setBlkLstFlg("0");
            carInfoEntity.setDoActionTag("DEL");//修改标识，存储过程用以区分
            carInfoEntity.setRecId(recId);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carInfoEntity);
            carInfoEntity.setJsonString(json);

            //作废数据
            monitorService.addCarInfoNew(carInfoEntity);
            //设置返回结果
            if (carInfoEntity.getResCode() != null && carInfoEntity.getResCode().equals("0")) {
                result.setMsg("车卡删除成功！");
                result.setSuccessful(true);
            } else {
                result.setMsg("车卡删除失败:" + carInfoEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("车卡删除异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //九江电厂皮带称重选时间
    @RequestMapping(value = "/selectDateTime/{sampleCode}/{status}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gotoSelectDateTime(@PathVariable String sampleCode, @PathVariable String status) {
        ModelAndView mav = new ModelAndView("/monitor/selectDateTime");
        try {
            mav.addObject("sampleCode", sampleCode);
            mav.addObject("status", status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }

    //获取煤样瓶封装码
    @RequestMapping(value = "/getBottlePacketCode/{reqMessage}/{reqName}")
    @ResponseBody
    @Transactional
    public Result getBottlePacketCode(@PathVariable String reqMessage, @PathVariable String reqName) {
        Result result = new Result();
        try {
            TcpClient tcpClient = new TcpClient();
            String rsp = tcpClient.getBottlePacketCode(reqMessage, reqName);
            if (rsp != null) {
                String[] str = rsp.split(":");
                if (str.length == 3) {
                    result.setMsg(str[1]);
                    result.setSuccessful(true);
                } else {
                    result.setSuccessful(false);
                    result.setMsg("请重试！");
                }
            } else {
                result.setSuccessful(false);
                result.setMsg("请重试！");
            }
        } catch (Exception e) {
            result.setMsg("获取卡号异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //宣威自动随机取样功能
    @RequestMapping(value = "/autoTakeSample4XW")
    @ResponseBody
    @Transactional
    public Result autoTakeSample4XW() {
        Result result = new Result();
        ApproveEntity approveEntity = SearchForm(ApproveEntity.class);
        try {
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            approveEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(approveEntity);
            approveEntity.setJsonStr(json);

            monitorService.autoTakeSample4XW(approveEntity);
            if (approveEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg(approveEntity.getResMsg());
            } else {
                result.setSuccessful(false);
                result.setMsg("自动随机取样失败:" + approveEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setMsg("自动随机取样异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }

        return result;
    }

    //查询汽车来煤信息4XW
    @RequestMapping(value = "/carTransRecordList4XW")
    @ResponseBody
    public GridModel qryCarTransRecordList4XW() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity carTransRecordEntity = SearchForm(CarTransRecordEntity.class);

        //设置分页信息并计算记录开始和结束值
        carTransRecordEntity.setPageRowIndex(getPageIndex(), getPageSize());

        //用于前台页面列排序功能
        if (null != getOrder()) {
            carTransRecordEntity.setOrder(getOrder());
            carTransRecordEntity.setSort(getSort());
        }
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCarTransRecordList4XW((CarTransRecordEntity) carTransRecordEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //管理员权限账号申请 页面跳转
    @RequestMapping(value = "/sysUserAppr/list", method = RequestMethod.GET)
    public String gotosysUserAppr() {
        return "/monitor/sysUserApprList";
    }

    //管理员权限账号列表 查询
    @RequestMapping(value = "/qrySysUserApprList")
    @ResponseBody
    public GridModel qrySysUserApprList() {
        //查询
        GridModel gridModel = null;
        try {
            ApproveEntity approveEntity = null;
            gridModel = monitorService.qrySysUserApprList(approveEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //提交 管理员权限账号申请信息
    @RequestMapping(value = "/sysUserApprDetail/{userId}")
    @ResponseBody
    public ModelAndView sysUserApprList(@PathVariable String userId) {
        ModelAndView mav = new ModelAndView("/monitor/sysUserApprDetail");
        try {
            GridModel gridModel = null;
            //查询
            ApproveEntity approveEntity_detail = new ApproveEntity();
            approveEntity_detail.setUserId(userId);
            gridModel = monitorService.qrySysUserApprList(approveEntity_detail);
            ApproveEntity approveEntity = (ApproveEntity) gridModel.getRows().get(0);
            //获取对象转换成json字符串送到页面
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(approveEntity);

            mav.addObject("message", "success");
            mav.addObject("ApproveEntity", json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }

    //编码打印记录
    @RequestMapping(value = "/addPirntRec/{printCode}/{printType}")
    @ResponseBody
    public Result addPirntRec(@PathVariable String printCode, @PathVariable String printType, HttpServletRequest req) {
        Result result = new Result();
        result.setSuccessful(true);
        result.setMsg("记录打印计数成功");
        try {
            ManualSampleEntity manualSampleEntity = new ManualSampleEntity();
            manualSampleEntity.setPrintCode(printCode);
            manualSampleEntity.setPrintType(printType);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            manualSampleEntity.setOpCode(opCode);

            monitorService.addPrintRec(manualSampleEntity);
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作异常！");
            e.printStackTrace();
        }
        return result;
    }

    //查询封装码的打印次数 宣威先要的
    @RequestMapping(value = "/qryManInfoPackCode")
    @ResponseBody
    public GridModel qryManInfoPackCode() {
        ManualSampleEntity manualSampleEntity = SearchForm(ManualSampleEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryManInfoPackCode(manualSampleEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询采制化编码的打印次数 宣威先要的
    @RequestMapping(value = "/qryManInfo4XW")
    @ResponseBody
    public GridModel qryManInfo4XW() {
        ManualSampleEntity manualSampleEntity = SearchForm(ManualSampleEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryManInfo4XW(manualSampleEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询汽车批次信息详情
    @RequestMapping(value = "/carBasicList")
    @ResponseBody
    public GridModel qryCarBasicList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        CarTransRecordEntity carTransRecordEntity = SearchForm(CarTransRecordEntity.class);

        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryCarBasicList(carTransRecordEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //合并汽车批次  宣威
    @RequestMapping(value = "/mergeCarBatchs")
    @ResponseBody
    @Transactional
    public Result mergeCarBatchs(HttpServletRequest req) {
        Result result = new Result();

        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String publicStr = req.getParameter("publicInfo");
        String updateStr = req.getParameter("updateInfo");

        try {
            //获取界面输入参数
            CarTransRecordEntity carTransRecordEntity = new CarTransRecordEntity();

            carTransRecordEntity.setUpdateString(updateStr);
            carTransRecordEntity.setPublicString(publicStr);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carTransRecordEntity.setOpCode(opCode);

            //新增数据
            monitorService.mergeCarBatchs(carTransRecordEntity);

            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setMsg("合并汽车批次操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("合并汽车批次操作失败:" + carTransRecordEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("合并汽车批次操作异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //跳转到火车重量修改记录的窗口
    @RequestMapping(value = "/modifyOneTrainRecWin", method = RequestMethod.GET)
    public String gotoModifyOneTrainRecWin() {
        return "/monitor/modifyOneTrainRec";
    }

    //查询火车重量修改记录  聊城
    @RequestMapping(value = "/qryOneTrainHisList")
    @ResponseBody
    public GridModel qryOneTrainHisList() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        RegisterEntity registerEntity = SearchForm(RegisterEntity.class);

        //查询
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryOneTrainHisList(registerEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //库车编辑汽车来煤信息审批
    @RequestMapping(value = "/dealCarTransRecord4KcAppr")
    @ResponseBody
    public Result dealCarTransRecord4KcAppr(@ModelAttribute CarTransRecordEntity carTransRecordEntity) {
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carTransRecordEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);
            carTransRecordEntity.setJsonString(json);

            monitorService.dealCarTransRecord4KcAppr(carTransRecordEntity);

            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("修改汽车来煤信息审批提交成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("修改汽车来煤信息审批提交失败！" + carTransRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改汽车来煤信息审批提交异常！");
            e.printStackTrace();
        }
        return result;
    }


    //提交控制设备命令
    @RequestMapping(value = "/commitHttpCtrlCmd")
    @ResponseBody
    @Transactional
    public Result commitHttpCtrlCmd() {
        Result result = new Result();
        //获取界面输入参数
        CtrlEntity ctrlEntity = SearchForm(CtrlEntity.class);

        //获取登录操作员的ID
        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        ctrlEntity.setOpCode(opCode);

        try {
            TcpClient tcpClient = new TcpClient();
            String rsp = tcpClient.getBottlePacketCode(ctrlEntity.getJsonString(), ctrlEntity.getMachineCode());
            if (rsp != null) {
                String[] str = rsp.split(":");
                if (str.length == 2) {
                    monitorService.commitCtrlCmd(ctrlEntity);
                    result.setMsg("命令发送成功！");
                    result.setSuccessful(true);
                } else {
                    result.setSuccessful(false);
                    result.setMsg("命令发送失败，请重试！");
                }
            } else {
                result.setSuccessful(false);
                result.setMsg("命令发送失败，请重试！");
            }
        } catch (Exception e) {
            result.setMsg("发送设备命令异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //库车版本太特殊 单独搞一个
    @RequestMapping(value = "/qryFurnaceBatchInfoList4KC")
    @ResponseBody
    public GridModel qryFurnaceBatchInfoList4KC() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        SamplingRptEntity samplingRptEntity = SearchForm(SamplingRptEntity.class);
        samplingRptEntity.setBatchNoType("9");
        samplingRptEntity.setPageRowIndex(getPageIndex(), getPageSize());
        GridModel gridModel = null;
        try {
            gridModel = rptService.qrySamplingRptList4KC(samplingRptEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //查询船运的货运信息 乐东
    @RequestMapping(value = "/qryShipCargoInfo4LD")
    @ResponseBody
    public GridModel qryShipCargoInfo4LD() {
        ShipEntity shipEntity = SearchForm(ShipEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = monitorService.qryShipCargoInfo4LD(shipEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //人工采样录入 克拉玛依
    @RequestMapping(value = "/manualSampleKlmy")
    @ResponseBody
    public Map<String, Object> manualSampleKlmy(@RequestParam Map<String, Object> params) {

        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            params.put("opCode", opCode);
            String jsonString = JSON.toJSONString(params);

            Map<String, Object> pkgMap = new HashMap<String, Object>();
            pkgMap.put("jsonString", jsonString);
            pkgMap.put("resCode", "");
            pkgMap.put("resMsg", "");
            monitorService.manualSampleKlmy(pkgMap);
            result.putAll(pkgMap);
        } catch (Exception e) {
            result.put("resCode", "1");
            result.put("resMsg", "修改汽车来煤信息异常！");
            e.getMessage();
        }
        return result;
    }

    //菜单跳转到火车水尺关联页面 大开使用
    @RequestMapping(value = "/realTicket", method = RequestMethod.GET)
    public ModelAndView gotoRealTicket() {
        ModelAndView mav = new ModelAndView("/monitor/realTicket");
        mav.addObject("no", request.getParameter("no"));
        mav.addObject("transtype", request.getParameter("transtype"));
        return mav;
    }

}