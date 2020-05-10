package org.gxz.znrl.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.ext.beans.HashAdapter;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.util.JacksonMapper;
import org.gxz.znrl.entity.*;
import org.gxz.znrl.service.*;
import org.gxz.znrl.shiro.ShiroUser;
import org.gxz.znrl.viewModel.GridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.gxz.znrl.common.util.Result;
import org.springframework.web.servlet.ModelAndView;
import org.gxz.znrl.common.mybatis.Page;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.text.ParseException;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by admin-rubbissh on 2015/1/1.
 */
@Controller
@RequestMapping("/business/config")
public class ConfigController extends BaseAction {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IConfigService configService;

    @Autowired
    ICarForecastInfoService carForecastInfoService;

    @Autowired
    public IToolService toolService;

    @Autowired
    public CommonService commonService;

    @Autowired
    public IErrorSpecService errorSpecService;

    @Autowired
    public IApproveService approveService;

    private static final String COALTYPELIST = "/config/coalType/list";
    private static final String COALTYPEEDIT = "/config/coalType/coalTypeEdit";
    private static final String COALMINELIST = "/config/coalMine/list";
    private static final String COALMINEEDIT = "/config/coalMine/coalMineEdit";
    private static final String CARRIERINFOLIST = "/config/carrierInfo/list";
    private static final String CARRIERINFOEDIT = "/config/carrierInfo/carrierInfoEdit";
    private static final String VENDORINFOLIST = "/config/vendorInfo/list";
    private static final String VENDORINFOEDIT = "/config/vendorInfo/vendorInfoEdit";
    private static final String CARFORECASTINFOLIST = "/config/carForecastInfo/list";
    private static final String CARPLANINFOLIST = "/config/carPlanInfo/list";
    private static final String ERRORSPECLIST = "/config/errorSpec/list";
    private static final String MACHINWORKMODELIST = "/config/machinWorkMode/list";
    private static final String WORKMODETTPELIST = "/config/workModeType/list";
    private static final String RAILROADCARWEIGHTLIST = "/config/railroadCarWeight/list";
    private static final String RAILROADCARWEIGHTEEDIT = "/config/railroadCarWeight/railroadCarWeightEdit";
    private static final String VENDORMINELIST ="/config/vendorMine/list";


    private static final String GOODINFOLIST = "/config/goodInfo/list";
    private static final String GOODINFOEDIT = "/config/goodInfo/goodInfoEdit";


    private static final String CHANNLEINFOLIST = "/config/channelInfo/list";
    private static final String CHANNLEINFOEDIT = "/config/channelInfo/channelInfoEdit";


    private static final String STOCKINFOLIST = "/config/stockInfo/list";
    private static final String STOCKINFOEDIT = "/config/stockInfo/stockInfoEdit";

    private static final String CUSTOMERINFOLIST = "/config/customerInfo/list";
    private static final String CUSTOMERINFOEDIT = "/config/customerInfo/customerInfoEdit";

    private static final String WORKMODETTPELIST4DWK = "/config/workModeType/list4DWK"; //用于大武口参数配置的权限分离

    //flash测试纵览页面实例
    private static final String FLASHLIST ="/config/flash/list";

    @RequestMapping(value="/flash/list", method= RequestMethod.GET)
    public String flashList(HttpServletRequest request) {
        return FLASHLIST;
    }

    @RequestMapping(value="/coalType/list", method= RequestMethod.GET)
    public String coalTypeList(HttpServletRequest request) {
        return COALTYPELIST;
    }




    @RequestMapping(value="/goodInfo/list", method= RequestMethod.GET)
    public String goodInfoList(HttpServletRequest request) {
        return GOODINFOLIST;
    }

    @RequestMapping(value="/stockInfo/list", method= RequestMethod.GET)
    public String stockInfoList(HttpServletRequest request) {
        return STOCKINFOLIST;
    }

    @RequestMapping(value="/customerInfo/list", method= RequestMethod.GET)
    public String customerInfoList(HttpServletRequest request) {
        return CUSTOMERINFOLIST;
    }

    @RequestMapping(value="/channelInfo/list", method= RequestMethod.GET)
    public String channelInfoList(HttpServletRequest request) {
        return CHANNLEINFOLIST;
    }

    @RequestMapping(value="/coalMine/list", method= RequestMethod.GET)
    public String coalMineList(HttpServletRequest request) {
        return COALMINELIST;
    }

    @RequestMapping(value="/carrierInfo/list", method= RequestMethod.GET)
    public String carrierInfoList(HttpServletRequest request) {
        return CARRIERINFOLIST;
    }

    @RequestMapping(value="/vendorInfo/list", method= RequestMethod.GET)
    public String vendorInfoList(HttpServletRequest request) {
        return VENDORINFOLIST;
    }

    //来煤预报配置界面
    @RequestMapping(value="/carForecastInfo/list", method= RequestMethod.GET)
    public String carForecastInfo(HttpServletRequest request) {
        return CARFORECASTINFOLIST;
    }

    //来煤计划
    @RequestMapping(value="/carPlanInfo/list", method= RequestMethod.GET)
    public String carPlanInfo(HttpServletRequest request) {
        return CARPLANINFOLIST;
    }

    //故障定义
    @RequestMapping(value="/errorSpec/list", method= RequestMethod.GET)
    public String errorSpec(HttpServletRequest request) {
        return ERRORSPECLIST;
    }

    //工作模式
    @RequestMapping(value="/machinWorkMode/list", method= RequestMethod.GET)
    public String machinWorkMode(HttpServletRequest request) {
        return MACHINWORKMODELIST;
    }

    //车厢重量维护
    @RequestMapping(value="/railroadCarWeight/list", method= RequestMethod.GET)
    public String railroadCarWeight(HttpServletRequest request) {
        return RAILROADCARWEIGHTLIST;
    }

    //工作模式
    @RequestMapping(value="/vendorMine/list", method= RequestMethod.GET)
    public String vendorMine(HttpServletRequest request) {
        return VENDORMINELIST;
    }

    //工作模式new
//    @RequestMapping(value="/workModeType/list", method= RequestMethod.GET)
//    public String WorkModeTypeList(HttpServletRequest request) {
//        return WORKMODETTPELIST;
//    }

    //审批人配置
    @RequestMapping(value="/apprStaff/list", method= RequestMethod.GET)
    public String gotoApprStaff(HttpServletRequest request) {
        return "/config/approve/apprStaffConfig";
    }

    @RequestMapping(value="/apprStaff/modify", method= RequestMethod.GET)
    public String gotoApprStaffModify(HttpServletRequest request) {
        return "/config/approve/apprNodeModify";
    }

    @RequestMapping(value="/apprStaff/add", method= RequestMethod.GET)
    public String gotoApprStaffAdd(HttpServletRequest request) {
        return "/config/approve/apprNodeAdd";
    }

    @RequestMapping(value="/dictionary/list", method= RequestMethod.GET)
    public String dictionaryList(HttpServletRequest request) {
        return "/config/dictionary/list";
    }

    @RequestMapping(value="/dictionary/edit", method= RequestMethod.GET)
    public String dictionaryEdit(HttpServletRequest request) {
        return "/config/dictionary/dictionaryInfoEdit";
    }

    //-----------------------------------------------------------------------------------------------------MachinWorkModeEntity_start----------------------------------------------------------------------
    @RequestMapping(value="/machinWorkMode/listShow")
    @ResponseBody
    public GridModel machinWorkModeListShow() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity machinWorkModeEntity = SearchForm(MachinWorkModeEntity.class);
        //设置分页信息并计算记录开始和结束值
        machinWorkModeEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = configService.qryMachinWorkMode((MachinWorkModeEntity) machinWorkModeEntity);
        return gridModel;
    }


    @RequestMapping(value="/machinWorkMode/mod/{modeRecId}")
    @ResponseBody
    public ModelAndView qryKdQty(@PathVariable String modeRecId){
        ModelAndView mav = new ModelAndView("/config/machinWorkMode/edit");
        try {
            //车辆卸煤
            MachinWorkModeEntity machinWorkModeEntity = new MachinWorkModeEntity();
            machinWorkModeEntity.setModeRecId(modeRecId);
            machinWorkModeEntity = configService.qryMachinWorkModeById(machinWorkModeEntity);
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json =objectMapper.writeValueAsString(machinWorkModeEntity);
            mav.addObject("message", "success");
            mav.addObject("machinWorkModeEntity",json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/machinWorkMode/mod")
    @ResponseBody
    public Result editMeasureData(@ModelAttribute MachinWorkModeEntity machinWorkModeEntity){
        Result result = new Result();
        try {
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            machinWorkModeEntity.setUpdateCode(opCode);
            configService.modifyMachinWorkMode(machinWorkModeEntity);
            result.setSuccessful(true);
            result.setMsg("更新数据成功！");
        } catch (RuntimeException e) {
            result.setSuccessful(false);
            result.setMsg("更新数据失败！");
            e.printStackTrace();
        }
        return result;
    }
    //-----------------------------------------------------------------------------------------------------errorSpecEntity_start----------------------------------------------------------------------

    @RequestMapping(value="/errorSpec/listShow")
    @ResponseBody
    public GridModel errorSpecListShow() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity errorSpecEntity = SearchForm(ErrorSpecEntity.class);
        //设置分页信息并计算记录开始和结束值
        errorSpecEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = errorSpecService.qryErrorSpecList((ErrorSpecEntity) errorSpecEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    @RequestMapping(value="/qryErrorSpecList/{machineCode}/{flowId}")
    @ResponseBody
    public List<ErrorSpecEntity> qryErrorSpecList(@PathVariable String machineCode,@PathVariable String flowId) {
        ErrorSpecEntity errorSpecEntity = new ErrorSpecEntity();
        errorSpecEntity.setMachinCode(machineCode);
        errorSpecEntity.setFlowId(flowId);
        List<ErrorSpecEntity> list = null;
        try {
            list = errorSpecService.qryErrorSpecAsList(errorSpecEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //查看故障信息详情
    @RequestMapping(value="/errorSpec/errorSpecDetail/{errorSpecId}")
    @ResponseBody
    public ModelAndView qryErrorSpecDetail(@PathVariable String errorSpecId){
        ModelAndView mav = new ModelAndView("/config/errorSpec/errorSpecEdit");
        // -1为新增
        if(errorSpecId == null || errorSpecId.equals("-1")){
            mav.addObject("message", "success");
            mav.addObject("errorSpecEntity","{'errorSpecId':''}");
            return mav;
        }
        ErrorSpecEntity errorSpecEntityTmp = new ErrorSpecEntity();
        errorSpecEntityTmp.setErrorSpecId(errorSpecId);
        try {
            //查询
            errorSpecEntityTmp = errorSpecService.qryErrorSpecById(errorSpecEntityTmp);
            //获取对象转换成json字符串送到页面
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json =objectMapper.writeValueAsString(errorSpecEntityTmp);

            mav.addObject("message", "success");
            mav.addObject("errorSpecEntity",json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }

    //新增
    @RequestMapping(value="/editErrorSpec/add")
    @ResponseBody
    public Result addErrorSpec(@ModelAttribute ErrorSpecEntity errorSpecEntity){
        Result result = new Result();
        ErrorSpecEntity errorSpecEntityTmp = new ErrorSpecEntity();
        errorSpecEntityTmp.setErrorCode(errorSpecEntity.getErrorCode());
        errorSpecEntityTmp.setFlowId(errorSpecEntity.getFlowId());
        try {
            errorSpecEntityTmp = errorSpecService.qryErrorSpecById(errorSpecEntityTmp);
            if (errorSpecEntityTmp != null) {
                result.setSuccessful(false);
                result.setMsg("您所录入的错误编码和流程编码已存在，请重新录入!");
            } else {
                errorSpecEntityTmp = new ErrorSpecEntity();
                errorSpecEntityTmp.setFlowId(errorSpecEntity.getFlowId());
                errorSpecEntityTmp.setMachinCode(errorSpecEntity.getMachinCode());
                errorSpecEntityTmp = errorSpecService.qryErrorSpecById(errorSpecEntityTmp);
                if (errorSpecEntityTmp != null) {
                    result.setSuccessful(false);
                    result.setMsg("您所录入的流程ID与设备编号已存在，请重新录入!");
                } else {
                    errorSpecService.addErrorSpec(errorSpecEntity);
                    result.setSuccessful(true);
                    result.setMsg("新增故障信息成功！");
                }
            }
        } catch (RuntimeException e) {
            result.setSuccessful(false);
            result.setMsg("新增故障信息失败！"+e);
            e.printStackTrace();
        }
        return result;
    }

    //修改
    @RequestMapping(value="/editErrorSpec/mod")
    @ResponseBody
    public Result editErrorSpec(@ModelAttribute ErrorSpecEntity errorSpecEntity){
        Result result = new Result();
        ErrorSpecEntity errorSpecEntityTmp = new ErrorSpecEntity();
        try {
            errorSpecEntityTmp.setErrorCode(errorSpecEntity.getErrorCode());
            errorSpecEntityTmp.setFlowId(errorSpecEntity.getFlowId());
            errorSpecEntityTmp = errorSpecService.qryErrorSpecById(errorSpecEntityTmp);
            if (errorSpecEntityTmp != null && !errorSpecEntityTmp.getErrorSpecId().equals(errorSpecEntity.getErrorSpecId())) {
                result.setSuccessful(false);
                result.setMsg("您所录入的错误编码与流程编码已存在，请重新录入!");
            } else {
                errorSpecEntityTmp = new ErrorSpecEntity();
                errorSpecEntityTmp.setFlowId(errorSpecEntity.getFlowId());
                errorSpecEntityTmp.setMachinCode(errorSpecEntity.getMachinCode());
                errorSpecEntityTmp = errorSpecService.qryErrorSpecById(errorSpecEntityTmp);
                if (errorSpecEntityTmp != null && !errorSpecEntityTmp.getErrorSpecId().equals(errorSpecEntity.getErrorSpecId())) {
                    result.setSuccessful(false);
                    result.setMsg("您所录入的流程编码与设备编号已存在，请重新录入!");
                } else {
                    errorSpecService.modifyErrorSpec(errorSpecEntity);
                    result.setSuccessful(true);
                    result.setMsg("修改故障信息成功！");
                }
            }
        } catch (RuntimeException e) {
            result.setSuccessful(false);
            result.setMsg("修改故障信息失败！"+e);
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/delErrorSpec/{errorSpecId}")
    @ResponseBody
    public Result delErrorSpec(@PathVariable String errorSpecId){
        Result result = new Result();
        ErrorSpecEntity errorSpecEntity = new ErrorSpecEntity();
        try {
            //获取登录操作员的ID
            errorSpecEntity.setErrorSpecId(errorSpecId);
            errorSpecEntity = errorSpecService.qryErrorSpecById(errorSpecEntity);
            TableColumnByColumn tableColumnByColumn = new TableColumnByColumn();
            tableColumnByColumn.setShowCol("COUNT(1)");
            tableColumnByColumn.setTableName("DEVICE_ERROR_INFO");
            tableColumnByColumn.setCondCol("ERROR_CODE");
            tableColumnByColumn.setCondValue(errorSpecEntity.getErrorCode());
            tableColumnByColumn.setCondOperate("=");
            tableColumnByColumn.setCondType("1");
            tableColumnByColumn.setCondCnt(1);
            String tmp = commonService.getTableColumnByColumn(tableColumnByColumn);
            if (tmp != null && Integer.parseInt(tmp) > 0 ) {
                result.setSuccessful(false);
                result.setMsg("该故障编号正在被使用，不能删除！");
                return result;
            }
            //新增汽车信息
            errorSpecService.delErrorSpec(errorSpecEntity);
            result.setSuccessful(true);
            result.setMsg("删除故障定义信息成功！");
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("删除故障定义失败！");
            e.printStackTrace();
        }
        return result;
    }
    //-----------------------------------------------------------------------------------------------------carForecastInfo_start----------------------------------------------------------------------
    @RequestMapping(value="/carForecastInfo/listShow")
    @ResponseBody
    public GridModel carForecastInfoListShow() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity carForecastInfoEntity = SearchForm(CarForecastInfoEntity.class);
        //设置分页信息并计算记录开始和结束值
        carForecastInfoEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = carForecastInfoService.qryCarForecastInfoList((CarForecastInfoEntity) carForecastInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value="/carPlanInfo/listShow")
    @ResponseBody
    public GridModel carPlanInfoListShow() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity carPlanInfoEntity = SearchForm(CarPlanInfoEntity.class);
        //设置分页信息并计算记录开始和结束值
        carPlanInfoEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = carForecastInfoService.qryCarPlanInfoList((CarPlanInfoEntity) carPlanInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查看车辆信息详情
    @RequestMapping(value="/carForecastInfo/carForecastDetail/{forecastId}")
    @ResponseBody
    public ModelAndView qryCarForecastDetail(@PathVariable String forecastId){
        ModelAndView mav = new ModelAndView("/config/carForecastInfo/carForecastInfoEdit");
        // -1为新增
        if(forecastId == null || forecastId.equals("-1")){
            mav.addObject("message", "success");
            mav.addObject("carForecastInfoEntity","{'forecastId':'-1'}");
            return mav;
        }
        try {
            //查询
            CarForecastInfoEntity carForecastInfoEntity = carForecastInfoService.qryCarForecastInfoById(forecastId);
            //获取对象转换成json字符串送到页面
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json =objectMapper.writeValueAsString(carForecastInfoEntity);

            mav.addObject("message", "success");
            mav.addObject("carForecastInfoEntity",json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }


    @RequestMapping(value="/editCarForecastInfo/queryContractVendorMineType")
    @ResponseBody
    public Map<String,String> queryContractVendorMineType(@RequestParam Map<String,String> map){
        Map<String,String> param = new HashMap<String,String>();
        try{
            param.put("jsonStr",JSON.toJSONString(map));
            param.put("data","");
            param.put("resCode","");
            param.put("resMsg","");
            carForecastInfoService.queryContractVendorMineType(param);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return param;
        }
    }

    //新增
    @RequestMapping(value="/editCarForecastInfo/add")
    @ResponseBody
    public Result addCarForecastInfo(@ModelAttribute CarForecastInfoEntity carForecastInfoEntity){
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            CarForecastInfoEntity tmpCarForecastInfoEntity = new CarForecastInfoEntity();
            String opCode = shiroUser.getUser().getId().toString();
            carForecastInfoEntity.setOpCode(opCode);
            tmpCarForecastInfoEntity = carForecastInfoService.qryCarForecastInfoByEntity(carForecastInfoEntity);
            if (tmpCarForecastInfoEntity != null &&
                    carForecastInfoEntity.getVenNo().equals(tmpCarForecastInfoEntity.getVenNo()) &&
                    carForecastInfoEntity.getMineNo().equals(tmpCarForecastInfoEntity.getMineNo()) &&
                    carForecastInfoEntity.getCoalNo().equals(tmpCarForecastInfoEntity.getCoalNo()) &&
                    carForecastInfoEntity.getFcCycle().equals(tmpCarForecastInfoEntity.getFcCycle()) ) {
                if (carForecastInfoEntity.getFcCycle().equals("1")&&carForecastInfoEntity.getStartDate().equals(tmpCarForecastInfoEntity.getStartDate())) {//周期：1：日；2：月；3：年
                         result.setSuccessful(false);
                         result.setMsg("选择日数据已经存在，请选择其他日期！");
                } else if (carForecastInfoEntity.getFcCycle().equals("2") && carForecastInfoEntity.getStartDate().split("-")[2].equals(tmpCarForecastInfoEntity.getStartDate().split("-")[2])){
                        result.setSuccessful(false);
                        result.setMsg("选择月数据已经存在，请选择其他月份！");
                } else if (carForecastInfoEntity.getFcCycle().equals("3") && carForecastInfoEntity.getStartDate().split("-")[0].equals(tmpCarForecastInfoEntity.getStartDate().split("-")[0])) {
                        result.setSuccessful(false);
                        result.setMsg("选择年数据已经存在，请选择其他年份！");
                } else {
                    //新增汽车信息
                    carForecastInfoService.addCarForecast(carForecastInfoEntity);
                    result.setSuccessful(true);
                    result.setMsg("新增预报信息成功！");
                }
            } else {
                //新增汽车信息
                carForecastInfoService.addCarForecast(carForecastInfoEntity);
                result.setSuccessful(true);
                result.setMsg("新增预报信息成功！");
            }
        } catch (RuntimeException e) {
            result.setSuccessful(false);
            result.setMsg("新增预报信息失败！");
            e.printStackTrace();
        } catch (ParseException e) {
            result.setSuccessful(false);
            result.setMsg("新增预报信息失败！");
            e.printStackTrace();
        }
        return result;
    }

    //编辑
    @RequestMapping(value="/editCarForecastInfo/mod")
    @ResponseBody
    public Result modifyCarForecastInfo(@ModelAttribute CarForecastInfoEntity carForecastInfoEntity){
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carForecastInfoEntity.setUpdateCode(opCode);
            CarForecastInfoEntity tmpCarForecastInfoEntity = new CarForecastInfoEntity();
            tmpCarForecastInfoEntity = carForecastInfoService.qryCarForecastInfoByEntity(carForecastInfoEntity);
                if (tmpCarForecastInfoEntity == null || tmpCarForecastInfoEntity.getForecastId().equals(carForecastInfoEntity.getForecastId())) {
                    carForecastInfoService.modifyCarForecast(carForecastInfoEntity);
                    result.setSuccessful(true);
                    result.setMsg("修改预报信息成功！");
                } else {
                    if (carForecastInfoEntity.getFcCycle().equals("1")&&carForecastInfoEntity.getStartDate().equals(tmpCarForecastInfoEntity.getStartDate())) {//周期：1：日；2：月；3：年
                        result.setSuccessful(false);
                        result.setMsg("选择日数据已经存在，请选择其他日期！");
                    } else if (carForecastInfoEntity.getFcCycle().equals("2") && carForecastInfoEntity.getStartDate().split("-")[2].equals(tmpCarForecastInfoEntity.getStartDate().split("-")[2])){
                        result.setSuccessful(false);
                        result.setMsg("选择月数据已经存在，请选择其他月份！");
                    } else if (carForecastInfoEntity.getFcCycle().equals("3") && carForecastInfoEntity.getStartDate().split("-")[0].equals(tmpCarForecastInfoEntity.getStartDate().split("-")[0])) {
                        result.setSuccessful(false);
                        result.setMsg("选择年数据已经存在，请选择其他年份！");
                    } else {
                        carForecastInfoService.modifyCarForecast(carForecastInfoEntity);
                        result.setSuccessful(true);
                        result.setMsg("修改预报信息成功！");
                    }
                }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("修改预报信息失败！");
            e.printStackTrace();
        }
        return result;
    }
    //删除
    @RequestMapping(value="/delCarForecastInfo/{forecastId}")
    @ResponseBody
    public Result delCarForecastInfo(@PathVariable String forecastId){
        Result result = new Result();
        CarForecastInfoEntity carForecastInfoEntity = new CarForecastInfoEntity();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carForecastInfoEntity.setUpdateCode(opCode);
            carForecastInfoEntity.setForecastId(forecastId);

            //新增汽车信息
            carForecastInfoService.delCarForecast(carForecastInfoEntity);
            result.setSuccessful(true);
            result.setMsg("取消预报信息成功！");
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("取消预报信息失败！");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(value="/findAllTimeZoneSelect/{id}/{zoneTimes}")
    @ResponseBody
    public List<ComboboxOptionEntity> findAllTimeZoneSelect(@PathVariable Long id,@PathVariable String zoneTimes) {
        logger.debug("id = " + id);
        List<ComboboxOptionEntity> list = null;
        try {
            list = toolService.getComboboxOption("TIME_ZONE");
            for(ComboboxOptionEntity comboboxOptionEntity: list)
            {
                for (String timeZoneId : StringUtils.split(zoneTimes, ",")) {
                    if(StringUtils.equals(comboboxOptionEntity.getId(),timeZoneId)) {
                        comboboxOptionEntity.setSelected(true);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //删除
    @RequestMapping(value="/delCarPlanInfo/{carPlanId}")
    @ResponseBody
    public Result delCarPlanInfo(@PathVariable String carPlanId){
        Result result = new Result();
        CarPlanInfoEntity carPlanInfoEntity = new CarPlanInfoEntity();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carPlanInfoEntity.setUpdateCode(opCode);
            carPlanInfoEntity.setCarPlanId(carPlanId);

            //删除
            carForecastInfoService.delCarPlanInfo(carPlanInfoEntity);
            result.setSuccessful(true);
            result.setMsg("删除计划成功！");
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("删除计划失败！");
            e.printStackTrace();
        }
        return result;
    }
    //-----------------------------------------------------------------------------------------------------carForecastInfo_end----------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------coalType_start----------------------------------------------------------------------
    @RequestMapping(value="/coalType/listShow")
    @ResponseBody
    public GridModel coalTypeListShow() {
        GridModel m = new GridModel();
        Page info = null;
        CoalType coalType = form(CoalType.class);
        try {
            info = configService.coalTypeFindList(page(), coalType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }

    @RequestMapping(value="/coalType/delete/{id}", method=RequestMethod.POST)
    @ResponseBody
    public Result coalTypeDelete(@PathVariable String id) {
        Result result = new Result();
        TableColumnByColumn tableColumnByColumn = new TableColumnByColumn();
        tableColumnByColumn.setShowCol("COUNT(1)");
        tableColumnByColumn.setTableName("rlrecordmsthy");
        tableColumnByColumn.setCondCol("COAL_NO");
        tableColumnByColumn.setCondValue(id);
        tableColumnByColumn.setCondOperate("=");
        tableColumnByColumn.setCondType("1");
        tableColumnByColumn.setCondCnt(1);
        String tmp = commonService.getTableColumnByColumn(tableColumnByColumn);
        if (tmp != null && Integer.parseInt(tmp) > 0 ) {
            result.setSuccessful(false);
            result.setMsg("该煤种编号正在被使用，不能删除！");
            return result;
        }
        tableColumnByColumn.setTableName("rlrecordmstqy");
        tmp = commonService.getTableColumnByColumn(tableColumnByColumn);
        if (tmp != null && Integer.parseInt(tmp) > 0 ) {
            result.setSuccessful(false);
            result.setMsg("该煤种编号正在被使用，不能删除！");
            return result;
        }
        configService.coalTypeDelete(id);
        result.setSuccessful(true);
        result.setMsg("删除成功");
        return result;
    }

    @RequestMapping(value="/coalTypeEdit")
    @ResponseBody
    public ModelAndView coalTypeEdit() {
        ModelAndView mav = new ModelAndView(COALTYPEEDIT);
        CoalType coalType = new CoalType();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(coalType);
            mav.addObject("message", "success");
            mav.addObject("coalType",json);
            mav.addObject("doWhat","add");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/coalTypeEdit/{code}")
    @ResponseBody
    public ModelAndView editCoalType(@PathVariable String code) {
        ModelAndView mav = new ModelAndView(COALTYPEEDIT);
        try {
            CoalType  coalType = configService.getByCoalNo(code);
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(coalType);
            mav.addObject("message", "success");
            mav.addObject("coalType",json);
            mav.addObject("doWhat","edit");
        } catch (Exception e){
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/coalType/add")
    @ResponseBody
    public Result saveUser(@ModelAttribute CoalType coalType) {
        Result result = new Result();
        try {

            if (configService.getByCoalName(coalType.getCoalName()) != null) {
                result.setSuccessful(false);
                result.setMsg("煤种添加失败，煤种名称：" + coalType.getCoalName() + "已存在。");
                return result;
            }
            configService.saveCoalType(coalType);

            result.setSuccessful(true);
            result.setMsg("保存成功");
        }catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/coalType/edit/{coalNo}")
    @ResponseBody
    public Result editUser(@ModelAttribute CoalType coalType, @PathVariable String coalNo) {
        Result result = new Result();
        try {
            coalType.setCoalNo(coalNo);
            if(coalType.getCoalNo()!=null) {
                CoalType coalTypeTmp = configService.getByCoalName(coalType.getCoalName());
                if (coalTypeTmp != null) {
                    if (!coalType.getCoalNo().equals(coalTypeTmp.getCoalNo())) {
                        result.setSuccessful(false);
                        result.setMsg("煤种添加失败，煤种名称：" + coalType.getCoalName() + "已存在。");
                        return result;
                    }
                }
                configService.updateCoalType(coalType);
            }
            result.setSuccessful(true);
            result.setMsg("保存成功");
        }catch (Exception e){
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }
    //-----------------------------------------------------------------------------------------------------coalMine_start----------------------------------------------------------------------
    @RequestMapping(value="/coalMine/listShow")
    @ResponseBody
    public GridModel coalMineListShow() {
        GridModel m = new GridModel();
        CoalMine coalMine = form(CoalMine.class);
        //CoalMine coalMineTmp = new CoalMine();
        Page info = null;
        List<VendorMineRelEntity> list = null;
        List<CoalMine> resultList = new ArrayList<CoalMine>() ;
        VendorMineRelEntity vendorMineRelEntity = new VendorMineRelEntity();
        try {
            info = configService.coalMineFindList(page(), coalMine);
            //获取venNo
            for (int i=0;i<info.getRows().size();i++) {
                coalMine = (CoalMine)info.getRows().get(i);
                vendorMineRelEntity.setMineNo(coalMine.getMineNo());
                list = configService.qryVendorMineList(vendorMineRelEntity) ;
                if (list.size() > 0) {
                    coalMine.setVenNo(list.get(0).getVenNo());
                    coalMine.setVenName(list.get(0).getVenName());
                } else {
                    coalMine.setVenNo("0");
                    coalMine.setVenName("");
                }
                resultList.add(coalMine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        info.setRows(resultList);
        //获取venNo
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }

    @RequestMapping(value="/coalMine/delete/{id}", method=RequestMethod.POST)
    @ResponseBody
    public Result coalMineDelete(@PathVariable String id) {
        Result result = new Result();
        TableColumnByColumn tableColumnByColumn = new TableColumnByColumn();
        tableColumnByColumn.setShowCol("COUNT(1)");
        tableColumnByColumn.setTableName("rlrecordmsthy");
        tableColumnByColumn.setCondCol("COLRY_NO");
        tableColumnByColumn.setCondValue(id);
        tableColumnByColumn.setCondOperate("=");
        tableColumnByColumn.setCondType("1");
        tableColumnByColumn.setCondCnt(1);
        try {
            String tmp = commonService.getTableColumnByColumn(tableColumnByColumn);
            if (tmp != null && Integer.parseInt(tmp) > 0 ) {
                result.setSuccessful(false);
                result.setMsg("该煤矿编号正在被使用，不能删除！");
                return result;
            }
            tableColumnByColumn.setTableName("rlrecordmstqy");
            tmp = commonService.getTableColumnByColumn(tableColumnByColumn);
            if (tmp != null && Integer.parseInt(tmp) > 0 ) {
                result.setSuccessful(false);
                result.setMsg("该煤矿编号正在被使用，不能删除！");
                return result;
            }
            configService.coalMineDelete(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        result.setSuccessful(true);
        result.setMsg("删除成功");
        return result;
    }

    @RequestMapping(value="/coalMineEdit")
    @ResponseBody
    public ModelAndView coalMineEdit() {
        ModelAndView mav = new ModelAndView(COALMINEEDIT);
        CoalMine coalMine = new CoalMine();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(coalMine);
            mav.addObject("message", "success");
            mav.addObject("coalMine",json);
            mav.addObject("doWhat","add");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/coalMineEdit/{code}")
    @ResponseBody
    public ModelAndView editCoalMine(@PathVariable String code) {
        ModelAndView mav = new ModelAndView(COALMINEEDIT);
        try {
            CoalMine  coalMine = configService.getByMineNo(code);

            VendorMineRelEntity vendorMineRelEntity = new VendorMineRelEntity();
            List<VendorMineRelEntity> list = null;
            vendorMineRelEntity.setMineNo(coalMine.getMineNo());
            list = configService.qryVendorMineList(vendorMineRelEntity) ;
            if (list.size() >0)
                coalMine.setVenNo(list.get(0).getVenNo());
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(coalMine);
            mav.addObject("message", "success");
            mav.addObject("coalMine",json);
            mav.addObject("doWhat","edit");
        }catch (Exception e){
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/coalMine/add")
    @ResponseBody
    public Result saveUser(@ModelAttribute CoalMine coalMine) {
        Result result = new Result();
        try {
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            coalMine.setOpCode(opCode);
            if (configService.getByMineName(coalMine.getMineName()) != null) {
                result.setSuccessful(false);
                result.setMsg("煤矿添加失败，煤矿名称：" + coalMine.getMineName() + "已存在。");
                return result;
            }
            configService.saveCoalMine(coalMine);

            result.setSuccessful(true);
            result.setMsg("保存成功");
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(value="/coalMine/edit/{mineNo}")
    @ResponseBody
    public Result editUser(@ModelAttribute CoalMine coalMine, @PathVariable String mineNo) {
        Result result = new Result();
        try {
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            coalMine.setOpCode(opCode);
            coalMine.setMineNo(mineNo);
            if(coalMine.getMineNo()!=null) {
                CoalMine coalMineTmp = configService.getByMineName(coalMine.getMineName());
                if (coalMineTmp != null) {
                    if (!coalMine.getMineNo().equals(coalMineTmp.getMineNo())) {
                        result.setSuccessful(false);
                        result.setMsg("煤矿添加失败，煤矿名称：" + coalMine.getMineName() + "已存在。");
                        return result;
                    }
                }
                configService.updateCoalMine(coalMine);
            }
            result.setSuccessful(true);
            result.setMsg("保存成功");
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }
    //-----------------------------------------------------------------------------------------------------carrierInfo_start----------------------------------------------------------------------
    @RequestMapping(value="/carrierInfo/listShow")
    @ResponseBody
    public GridModel carrierInfoListShow() {
        GridModel m = new GridModel();
        CarrierInfo carrierInfo = form(CarrierInfo.class);
        Page info = null;
        try {
            info = configService.carrierInfoFindList(page(), carrierInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }

    @RequestMapping(value="/carrierInfo/delete/{id}", method=RequestMethod.POST)
    @ResponseBody
    public Result carrierInfoDelete(@PathVariable String id) {
        Result result = new Result();
        TableColumnByColumn tableColumnByColumn = new TableColumnByColumn();
        tableColumnByColumn.setShowCol("COUNT(1)");
        tableColumnByColumn.setTableName("rlrecordmsthy");
        tableColumnByColumn.setCondCol("CARRIER_NO");
        tableColumnByColumn.setCondValue(id);
        tableColumnByColumn.setCondOperate("=");
        tableColumnByColumn.setCondType("1");
        tableColumnByColumn.setCondCnt(1);
        try {
            String tmp = commonService.getTableColumnByColumn(tableColumnByColumn);
            if (tmp != null && Integer.parseInt(tmp) > 0 ) {
                result.setSuccessful(false);
                result.setMsg("该运输单位编号正在被使用，不能删除！");
                return result;
            }
            tableColumnByColumn.setTableName("rlrecordmstqy");
            tmp = commonService.getTableColumnByColumn(tableColumnByColumn);
            if (tmp != null && Integer.parseInt(tmp) > 0 ) {
                result.setSuccessful(false);
                result.setMsg("该运输单位编号正在被使用，不能删除！");
                return result;
            }
            configService.carrierInfoDelete(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        result.setSuccessful(true);
        result.setMsg("删除成功");
        return result;
    }

    @RequestMapping(value="/carrierInfoEdit")
    @ResponseBody
    public ModelAndView carrierInfoEdit() {
        ModelAndView mav = new ModelAndView(CARRIERINFOEDIT);
        CarrierInfo carrierInfo = new CarrierInfo();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(carrierInfo);
            mav.addObject("message", "success");
            mav.addObject("carrierInfo",json);
            mav.addObject("doWhat","add");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/carrierInfoEdit/{code}")
    @ResponseBody
    public ModelAndView editCarrierInfo(@PathVariable String code) {
        ModelAndView mav = new ModelAndView(CARRIERINFOEDIT);
        try {
            CarrierInfo  carrierInfo = configService.getByCarrierNo(code);
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(carrierInfo);
            mav.addObject("message", "success");
            mav.addObject("carrierInfo",json);
            mav.addObject("doWhat","edit");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/carrierInfo/add")
    @ResponseBody
    public Result saveUser(@ModelAttribute CarrierInfo carrierInfo) {
        Result result = new Result();
        try {

            if (configService.getByCarrierName(carrierInfo.getCarrierName()) != null) {
                result.setSuccessful(false);
                result.setMsg("运输单位添加失败，运输单位名称：" + carrierInfo.getCarrierName() + "已存在。");
                return result;
            }
            configService.saveCarrierInfo(carrierInfo);

            result.setSuccessful(true);
            result.setMsg("保存成功");
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(value="/carrierInfo/edit/{carrierNo}")
    @ResponseBody
    public Result editUser(@ModelAttribute CarrierInfo carrierInfo, @PathVariable String carrierNo) {
        Result result = new Result();
        try {
            carrierInfo.setCarrierNo(carrierNo);
            if(carrierInfo.getCarrierNo()!=null) {
                CarrierInfo carrierInfoTmp = configService.getByCarrierName(carrierInfo.getCarrierName());
                if (carrierInfoTmp != null) {
                    if (!carrierInfo.getCarrierNo().equals(carrierInfoTmp.getCarrierNo())) {
                        result.setSuccessful(false);
                        result.setMsg("运输单位添加失败，运输单位名称：" + carrierInfo.getCarrierName() + "已存在。");
                        return result;
                    }
                }
                configService.updateCarrierInfo(carrierInfo);
            }
            result.setSuccessful(true);
            result.setMsg("保存成功");
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }

    //-----------------------------------------------------------------------------------------------------vendorInfo_start----------------------------------------------------------------------
    @RequestMapping(value="/vendorInfo/listShow")
    @ResponseBody
    public GridModel vendorInfoListShow() {
        GridModel m = new GridModel();
        VendorInfo vendorInfo = form(VendorInfo.class);
        Page info = null;
        try {
            info = configService.vendorInfoFindList(page(), vendorInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }

    @RequestMapping(value="/vendorInfo/delete/{id}", method=RequestMethod.POST)
    @ResponseBody
    public Result vendorInfoDelete(@PathVariable String id) {
        Result result = new Result();
        TableColumnByColumn tableColumnByColumn = new TableColumnByColumn();
        tableColumnByColumn.setShowCol("COUNT(1)");
        tableColumnByColumn.setTableName("rlrecordmsthy");
        tableColumnByColumn.setCondCol("VEN_NO");
        tableColumnByColumn.setCondValue(id);
        tableColumnByColumn.setCondOperate("=");
        tableColumnByColumn.setCondType("1");
        tableColumnByColumn.setCondCnt(1);
        try {
            String tmp = commonService.getTableColumnByColumn(tableColumnByColumn);
            if (tmp != null && Integer.parseInt(tmp) > 0 ) {
                result.setSuccessful(false);
                result.setMsg("该供应商编号正在被使用，不能删除！");
                return result;
            }
            tableColumnByColumn.setTableName("rlrecordmstqy");
            tmp = commonService.getTableColumnByColumn(tableColumnByColumn);
            if (tmp != null && Integer.parseInt(tmp) > 0 ) {
                result.setSuccessful(false);
                result.setMsg("该供应商编号正在被使用，不能删除！");
                return result;
            }
            configService.vendorInfoDelete(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        result.setSuccessful(true);
        result.setMsg("删除成功");
        return result;
    }

    @RequestMapping(value="/vendorInfoEdit")
    @ResponseBody
    public ModelAndView vendorInfoEdit() {
        ModelAndView mav = new ModelAndView(VENDORINFOEDIT);
        VendorInfo vendorInfo = new VendorInfo();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(vendorInfo);
            mav.addObject("message", "success");
            mav.addObject("vendorInfo",json);
            mav.addObject("doWhat","add");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/vendorInfoEdit/{code}")
    @ResponseBody
    public ModelAndView editVendorInfo(@PathVariable String code) {
        ModelAndView mav = new ModelAndView(VENDORINFOEDIT);
        try {
            VendorInfo  vendorInfo = configService.getByVenNo(code);
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(vendorInfo);
            mav.addObject("message", "success");
            mav.addObject("vendorInfo",json);
            mav.addObject("doWhat","edit");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/vendorInfo/add")
    @ResponseBody
    public Result saveUser(@ModelAttribute VendorInfo vendorInfo) {
        Result result = new Result();
        try {

            if (configService.getByVenName(vendorInfo.getVenName()) != null) {
                result.setSuccessful(false);
                result.setMsg("供应商添加失败，供应商名称：" + vendorInfo.getVenName() + "已存在。");
                return result;
            }
            configService.saveVendorInfo(vendorInfo);

            result.setSuccessful(true);
            result.setMsg("保存成功");
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(value="/vendorInfo/edit/{venNo}")
    @ResponseBody
    public Result editUser(@ModelAttribute VendorInfo vendorInfo, @PathVariable String venNo) {
        Result result = new Result();
        try {
            vendorInfo.setVenNo(venNo);
            if(vendorInfo.getVenNo()!=null) {
                VendorInfo vendorInfoTmp = configService.getByVenName(vendorInfo.getVenName());
                if (vendorInfoTmp != null) {
                    if (!vendorInfo.getVenNo().equals(vendorInfoTmp.getVenNo())) {
                        result.setSuccessful(false);
                        result.setMsg("供应商修改失败，供应商名称：" + vendorInfo.getVenName() + "已存在。");
                        return result;
                    }
                }
                configService.updateVendorInfo(vendorInfo);
            }
            result.setSuccessful(true);
            result.setMsg("保存成功");
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }
    //-----------------------------------------------------------------------------------------------------railroadCar_start----------------------------------------------------------------------
    @RequestMapping(value="/railroadCarWeight/listShow")
    @ResponseBody
    public GridModel railroadCarWeightListShow() {
        GridModel m = new GridModel();
        RailroadCarWeight railroadCarWeight = form(RailroadCarWeight.class);
        Page info = null;
        try {
            info = configService.railroadCarWeightFindList(page(), railroadCarWeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }

    @RequestMapping(value="/railroadCarWeight/delete/{id}", method=RequestMethod.POST)
    @ResponseBody
    public Result railroadCarWeightDelete(@PathVariable int id) {
        Result result = new Result();
        try {
            configService.railroadCarWeightDelete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setSuccessful(true);
        result.setMsg("删除成功");
        return result;
    }

    @RequestMapping(value="/railroadCarWeightEdit")
    @ResponseBody
    public ModelAndView railroadCarWeightEdit() {
        ModelAndView mav = new ModelAndView(RAILROADCARWEIGHTEEDIT);
        RailroadCarWeight railroadCarWeight = new RailroadCarWeight();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(railroadCarWeight);
            mav.addObject("message", "success");
            mav.addObject("railroadCarWeight",json);
            mav.addObject("doWhat","add");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/railroadCarWeightEdit/{id}")
    @ResponseBody
    public ModelAndView editRailroadCarWeight(@PathVariable String id) {
        ModelAndView mav = new ModelAndView(RAILROADCARWEIGHTEEDIT);
        try {
            RailroadCarWeight  railroadCarWeight = configService.getByRailroadCarId(id);
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(railroadCarWeight);
            mav.addObject("message", "success");
            mav.addObject("railroadCarWeight",json);
            mav.addObject("doWhat","edit");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/railroadCarWeight/add")
    @ResponseBody
    public Result saveRailroadCarWeight(@ModelAttribute RailroadCarWeight railroadCarWeight) {
        Result result = new Result();
        //获取登录操作员的ID
        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        railroadCarWeight.setUpdateCode(Long.parseLong(opCode));
        railroadCarWeight.setOpCode(Long.parseLong(opCode));
        try {
            if(railroadCarWeight.getRailroadCarType()!=null) {
                if (configService.getByRailroadCarType(railroadCarWeight.getRailroadCarType()) != null) {
                    result.setSuccessful(false);
                    result.setMsg("车厢型号对应皮重添加失败，车厢类型：" + railroadCarWeight.getRailroadCarType() + "已存在。");
                    return result;
                }
                configService.saveRailroadCarWeight(railroadCarWeight);
            }
            result.setSuccessful(true);
            result.setMsg("保存成功");
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(value="/railroadCarWeight/edit")
    @ResponseBody
    public Result editRailroadCarWeight(@ModelAttribute RailroadCarWeight railroadCarWeight) {
        Result result = new Result();
        //获取登录操作员的ID
        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        railroadCarWeight.setUpdateCode(Long.parseLong(opCode));
        try {
            if(railroadCarWeight.getRailroadCarType()!=null) {
                configService.updateRailroadCarWeight(railroadCarWeight);
            }
            result.setSuccessful(true);
            result.setMsg("保存成功");
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询审批节点配置
     */
    @RequestMapping(value="/qryApproveNode")
    @ResponseBody
    public GridModel qryApproveNode(){
        ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = approveService.qryApproveNode(approveEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    //修改审批节点
    @RequestMapping(value="/editApprNode/{actionTag}")
    @ResponseBody
    public Result editApprNode(@ModelAttribute ApproveEntity approveEntity, @PathVariable String actionTag){
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            approveEntity.setOpCode(opCode);

            if (actionTag.equals("MOD")) {
                approveService.modifyApprNode(approveEntity);
            } else if (actionTag.equals("ADD")) {
                approveService.addApprNode(approveEntity);
            }

            if (approveEntity.getResCode() != null && approveEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("操作失败：" + approveEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作失败！");
            e.printStackTrace();
        }
        return result;
    }

    //删除审批节点
    @RequestMapping(value="/deleteApprNode/{approveNodeCd}")
    @ResponseBody
    public Result deleteApprNode(@PathVariable String approveNodeCd){
        Result result = new Result();
        ApproveEntity approveEntity = new ApproveEntity();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            approveEntity.setOpCode(opCode);

            approveEntity.setApproveNodeCd(approveNodeCd);

            approveService.deleteApprNode(approveEntity);

            if (approveEntity.getResCode() != null && approveEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("操作失败：" + approveEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作失败！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/workModeType/list/{typeId}")
    @ResponseBody
    public ModelAndView qryWorkModeTypeList(@PathVariable int typeId){
        ModelAndView mav = new ModelAndView(WORKMODETTPELIST);
        WorkModeTypeResult workModeTypeResult = new WorkModeTypeResult();
        String json;
        try {
            workModeTypeResult = getWorkModeTypeList(typeId);
            json = JSON.toJSONStringWithDateFormat(workModeTypeResult, "yyyy-MM-dd HH:mm:ss").toString();
            mav.addObject("message", "success");
            //mav.addObject("workModeTypeJson",json);
            mav.addObject("workModeType",workModeTypeResult);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }


    private WorkModeTypeResult getWorkModeTypeList(int typeId) {
        WorkModeTypeEntity workModeTypeEntity = new WorkModeTypeEntity();
        WorkModeTypeResult returnResult = new WorkModeTypeResult();
        List<WorkModeTypeResult> temp = new ArrayList<WorkModeTypeResult>();;
        workModeTypeEntity.setParentTypeId(typeId);
        try {
            List<WorkModeTypeEntity> list = configService.qryWorkModeTypeList(workModeTypeEntity);
            returnResult.setParentTypeId(workModeTypeEntity.getParentTypeId());
            if (list != null) {
                returnResult.setWorkModeTypeList(list);
                for (int i = 0;i<list.size();i++) {
                    if ("0".equals(list.get(i).getShowType())) {//父级需要取下级配置，子级不需要
                        temp.add(getWorkModeTypeList(list.get(i).getTypeId()));
                    }
                }
                returnResult.setWorkModeTypeResult(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnResult;
    }

    //用于大武口参数配置的权限分离
    @RequestMapping(value="/workModeType/list4DWK/{typeId}")
    @ResponseBody
    public ModelAndView qryWorkModeTypeList4DWK(@PathVariable int typeId){
        ModelAndView mav = new ModelAndView(WORKMODETTPELIST4DWK);
        WorkModeTypeResult workModeTypeResult = new WorkModeTypeResult();
        String json;
        try {
            workModeTypeResult = getWorkModeTypeList4DWK(typeId);
            json = JSON.toJSONStringWithDateFormat(workModeTypeResult, "yyyy-MM-dd HH:mm:ss").toString();
            mav.addObject("message", "success");
            //mav.addObject("workModeTypeJson",json);
            mav.addObject("workModeType",workModeTypeResult);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }

    //用于大武口参数配置的权限分离
    private WorkModeTypeResult getWorkModeTypeList4DWK(int typeId) {
        WorkModeTypeEntity workModeTypeEntity = new WorkModeTypeEntity();
        WorkModeTypeResult returnResult = new WorkModeTypeResult();
        List<WorkModeTypeResult> temp = new ArrayList<WorkModeTypeResult>();;
        workModeTypeEntity.setParentTypeId(typeId);
        try {
            List<WorkModeTypeEntity> list = configService.qryWorkModeTypeList4DWK(workModeTypeEntity);
            returnResult.setParentTypeId(workModeTypeEntity.getParentTypeId());
            if (list != null) {
                returnResult.setWorkModeTypeList(list);
                for (int i = 0;i<list.size();i++) {
                    if ("0".equals(list.get(i).getShowType())) {//父级需要取下级配置，子级不需要
                        temp.add(getWorkModeTypeList4DWK(list.get(i).getTypeId()));
                    }
                }
                returnResult.setWorkModeTypeResult(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnResult;
    }



    @RequestMapping(value="/workModeType/edit")
    @ResponseBody
    public Result saveUser(@ModelAttribute WorkModeTypeCommit workModeTypeCommit) {
        Result result = new Result();
        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        WorkModeTypeCommit workModeTypeCommitTmp = new WorkModeTypeCommit();
        workModeTypeCommit.setOpCode(opCode);
        try {
            workModeTypeCommitTmp = configService.qrySystemWorkMode(workModeTypeCommit);
            if (workModeTypeCommitTmp != null) {
                workModeTypeCommit.setOriginalValue(workModeTypeCommitTmp.getOriginalValue());
                workModeTypeCommit.setRemark("数据修改：（原值）"+workModeTypeCommit.getOriginalValue()+",(新值)"+workModeTypeCommit.getModifyValue());
                configService.modiSystemWorkMode(workModeTypeCommit);
            } else {
                workModeTypeCommit.setRemark("数据新增："+workModeTypeCommit.getModifyValue());
                configService.addSystemWorkMode(workModeTypeCommit);
            }
            result.setSuccessful(true);
            result.setMsg("处理完成");
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("处理失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/qryVendorMineList/{typeTag}/{objectId}")
    @ResponseBody
    public List<VendorMineRelEntity> qryVendorMineList(@PathVariable String typeTag,@PathVariable String objectId) {
        VendorMineRelEntity vendorMineRelEntity = new VendorMineRelEntity();
        List<VendorMineRelEntity> list = null;
        if ("venNo".equals(typeTag)) {
            vendorMineRelEntity.setVenNo(objectId);
        } else {
            vendorMineRelEntity.setMineNo(objectId);
        }
        try {
            list = configService.qryVendorMineList(vendorMineRelEntity) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping(value="/qryNoExistVendorMineList/{typeTag}/{objectId}")
    @ResponseBody
    public List<VendorMineRelEntity> qryNoExistVendorMineList(@PathVariable String typeTag,@PathVariable String objectId) {
        VendorMineRelEntity vendorMineRelEntity = new VendorMineRelEntity();
        List<VendorMineRelEntity> list = null;
        try {
            if ("mineNo".equals(typeTag)) {
                vendorMineRelEntity.setMineNo(objectId);
                 list = configService.qryNotExistVendorInfoList(vendorMineRelEntity) ;
            } else {
                vendorMineRelEntity.setVenNo(objectId);
                list = configService.qryNotExistMineInfoList(vendorMineRelEntity) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    @RequestMapping(value = "/vendorMine/save")
    @ResponseBody
    public Result saveVendorMine(@ModelAttribute VendorMineRelEntity vendorMineRelEntity) {
        Result result = new Result();
        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        vendorMineRelEntity.setOpCode(opCode);
        vendorMineRelEntity.setVenNo(vendorMineRelEntity.getVenNo1());
        try {
            configService.addVendorMineRel(vendorMineRelEntity);
            result.setSuccessful(true);
            result.setMsg("处理完成");
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("处理失败");
            e.printStackTrace();
        }
        return result;
    }

    //获取可配置字典数据
    @RequestMapping(value="/qryEditableDictionaryInfo")
    @ResponseBody
    public String qryEditableDictionaryInfo(){
        String editableDictionaryInfo = null;
        try {
            editableDictionaryInfo = configService.qryEditableDictionaryInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editableDictionaryInfo;
    }

    //编辑船运信息
    @RequestMapping(value="/editDicInfo")
    @ResponseBody
    public Result editDicInfo(@ModelAttribute DictionaryTableEntity dictionaryTableEntity){
        Result result = new Result();
        try {
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json =objectMapper.writeValueAsString(dictionaryTableEntity);
            dictionaryTableEntity.setJsonString(json);
            configService.editDicInfo(dictionaryTableEntity);

            //设置返回结果
            if (dictionaryTableEntity.getResCode() != null && dictionaryTableEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("操作失败！" + dictionaryTableEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作发生异常！");
            e.printStackTrace();
        }
        return result;
    }

    //新增汽车
    @RequestMapping(value="/carForecastInfo/addCarForecastDetail", method= RequestMethod.GET)
    public String addCarForecastDetail() {
        return "/config/carForecastInfo/addCarForecastDetail";
    }

    @RequestMapping(value="/editCarForecastDetail/add")
    @ResponseBody
    public Result addCarForecastDetail(@ModelAttribute CarForecastDetailEntity carForecastDetailEntity){
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carForecastDetailEntity.setOpCode(opCode);

            //新增汽车信息
            carForecastInfoService.addCarForecastDetail(carForecastDetailEntity);
            //设置返回结果
            if (carForecastDetailEntity.getResCode() != null && (carForecastDetailEntity.getResCode().equals("0") ||carForecastDetailEntity.getResCode().equals("10"))) {
                result.setMsg(carForecastDetailEntity.getResMsg());
                result.setSuccessful(true);
            } else {
                result.setMsg("新增预报明细信息失败:"+carForecastDetailEntity.getResMsg());
                result.setSuccessful(false);
            }

        } catch (RuntimeException e) {
            result.setSuccessful(false);
            result.setMsg("新增预报明细信息失败！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/carForecastDetail/listShow")
    @ResponseBody
    public GridModel carForecastDetailListShow() {
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity carForecastDetailEntity = SearchForm(CarForecastDetailEntity.class);
        //设置分页信息并计算记录开始和结束值
        carForecastDetailEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = carForecastInfoService.qryCarForecastDetailList((CarForecastDetailEntity) carForecastDetailEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //删除车
    @RequestMapping(value="/delCarForecastDetail/{recordId}")
    @ResponseBody
    public Result delCarForecastDetail(@PathVariable String recordId){
        Result result = new Result();
        CarForecastDetailEntity carForecastDetailEntity = new CarForecastDetailEntity();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            carForecastDetailEntity.setUpdateCode(opCode);
            carForecastDetailEntity.setRecordId(recordId);

            //新增汽车信息
            carForecastInfoService.delCarForecastDetail(carForecastDetailEntity);

            result.setSuccessful(true);
            result.setMsg("取消预报信息成功！");
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("取消预报信息失败！");
            e.printStackTrace();
        }
        return result;
    }
    //-----------------------------------------------------------------------------------------------------goodInfo_start----------------------------------------------------------------------
    @RequestMapping(value="/goodInfo/listShow")
    @ResponseBody
    public GridModel goodInfoListShow() {
        GridModel m = new GridModel();
        Page info = null;
        GoodInfoEntity goodInfoEntity = form(GoodInfoEntity.class);
        try {
            info = configService.goodInfoFindList(page(), goodInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }

    @RequestMapping(value="/goodInfo/delete/{id}", method=RequestMethod.POST)
    @ResponseBody
    public Result goodInfoDelete(@PathVariable String id) {
        Result result = new Result();
        TableColumnByColumn tableColumnByColumn = new TableColumnByColumn();
        tableColumnByColumn.setShowCol("COUNT(1)");
        tableColumnByColumn.setTableName("ship_cargo_record");
        tableColumnByColumn.setCondCol("GOOD_NO");
        tableColumnByColumn.setCondValue(id);
        tableColumnByColumn.setCondOperate("=");
        tableColumnByColumn.setCondType("1");
        tableColumnByColumn.setCondCnt(1);
        String tmp = commonService.getTableColumnByColumn(tableColumnByColumn);
        if (tmp != null && Integer.parseInt(tmp) > 0 ) {
            result.setSuccessful(false);
            result.setMsg("该货物编号正在被使用，不能删除！");
            return result;
        }
        tableColumnByColumn.setTableName("ship_cargo_record");
        tmp = commonService.getTableColumnByColumn(tableColumnByColumn);
        if (tmp != null && Integer.parseInt(tmp) > 0 ) {
            result.setSuccessful(false);
            result.setMsg("该货物编号正在被使用，不能删除！");
            return result;
        }
        configService.goodInfoDelete(id);
        result.setSuccessful(true);
        result.setMsg("删除成功");
        return result;
    }

    @RequestMapping(value="/goodInfoEdit")
    @ResponseBody
    public ModelAndView goodInfoEdit() {
        ModelAndView mav = new ModelAndView(GOODINFOEDIT);
        GoodInfoEntity goodInfoEntity = new GoodInfoEntity();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(goodInfoEntity);
            mav.addObject("message", "success");
            mav.addObject("goodInfo",json);
            mav.addObject("doWhat","add");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/goodInfoEdit/{code}")
    @ResponseBody
    public ModelAndView editGoodInfo(@PathVariable String code) {
        ModelAndView mav = new ModelAndView(GOODINFOEDIT);
        try {
            GoodInfoEntity  goodInfoEntity = configService.getByGoodNo(code);
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(goodInfoEntity);
            mav.addObject("message", "success");
            mav.addObject("goodInfo",json);
            mav.addObject("doWhat","edit");
        } catch (Exception e){
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/goodInfo/add")
    @ResponseBody
    public Result saveUser(@ModelAttribute GoodInfoEntity goodInfoEntity) {
        Result result = new Result();
        try {

            if (configService.getByGoodName(goodInfoEntity.getGoodName()) != null) {
                result.setSuccessful(false);
                result.setMsg("货物添加失败，货物名称：" + goodInfoEntity.getGoodName() + "已存在。");
                return result;
            }
            configService.saveGoodInfoEntity(goodInfoEntity);

            result.setSuccessful(true);
            result.setMsg("保存成功");
        }catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/goodInfo/edit/{goodNo}")
    @ResponseBody
    public Result editUser(@ModelAttribute GoodInfoEntity goodInfo, @PathVariable String goodNo) {
        Result result = new Result();
        try {

            goodInfo.setGoodNo(goodNo);
            if(goodInfo.getGoodNo()!=null) {
                GoodInfoEntity goodInfoTmp = configService.getByGoodName(goodInfo.getGoodName());
                if (goodInfoTmp != null) {
                    if (!goodInfo.getGoodNo().equals(goodInfoTmp.getGoodNo())) {
                        result.setSuccessful(false);
                        result.setMsg("货物添加失败，货物名称：" + goodInfo.getGoodName() + "已存在。");
                        return result;
                    }
                }
                configService.updateGoodInfoEntity(goodInfo);
            }
            result.setSuccessful(true);
            result.setMsg("保存成功");
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }

    //-----------------------------------------------------------------------------------------------------channleInfo_start----------------------------------------------------------------------
    @RequestMapping(value="/channelInfo/listShow")
    @ResponseBody
    public GridModel channelInfoListShow() {
        GridModel m = new GridModel();
        Page info = null;
       ChannelInfoEntity  channelInfoEntity = form(ChannelInfoEntity.class);
        try {
            info = configService.channelInfoFindList(page(), channelInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }

    @RequestMapping(value="/channelInfo/delete/{id}", method=RequestMethod.POST)
    @ResponseBody
    public Result channleInfoDelete(@PathVariable String id) {
        Result result = new Result();
        TableColumnByColumn tableColumnByColumn = new TableColumnByColumn();
        tableColumnByColumn.setShowCol("COUNT(1)");
        tableColumnByColumn.setTableName("rlrecordmstqy");
        tableColumnByColumn.setCondCol("IN_DOOR_NO");
        tableColumnByColumn.setCondValue(id);
        tableColumnByColumn.setCondOperate("=");
        tableColumnByColumn.setCondType("1");
        tableColumnByColumn.setCondCnt(1);
        String tmp1 = commonService.getTableColumnByColumn(tableColumnByColumn);



        tableColumnByColumn.setShowCol("COUNT(1)");
        tableColumnByColumn.setTableName("rlrecordmstqy");
        tableColumnByColumn.setCondCol("OUT_DOOR_NO");
        tableColumnByColumn.setCondValue(id);
        tableColumnByColumn.setCondOperate("=");
        tableColumnByColumn.setCondType("1");
        tableColumnByColumn.setCondCnt(1);
        String tmp2 = commonService.getTableColumnByColumn(tableColumnByColumn);

        if (tmp1 != null && Integer.parseInt(tmp1) > 0 ) {
            result.setSuccessful(false);
            result.setMsg("该通道正在被使用，不能删除！");
            return result;
        }
        if (tmp2 != null && Integer.parseInt(tmp2) > 0 ) {
            result.setSuccessful(false);
            result.setMsg("该通道正在被使用，不能删除！");
            return result;
        }

        configService.channelInfoDelete(id);
        result.setSuccessful(true);
        result.setMsg("删除成功");
        return result;
    }

    @RequestMapping(value="/channelInfoEdit")
    @ResponseBody
    public ModelAndView channelInfoEdit() {
        ModelAndView mav = new ModelAndView(CHANNLEINFOEDIT);
        ChannelInfoEntity channelInfoEntity = new ChannelInfoEntity();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(channelInfoEntity);
            mav.addObject("message", "success");
            mav.addObject("goodInfo",json);
            mav.addObject("doWhat","add");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/channelInfoEdit/{code}")
    @ResponseBody
    public ModelAndView editChannelInfo(@PathVariable String code) {
        ModelAndView mav = new ModelAndView(CHANNLEINFOEDIT);
        try {
            ChannelInfoEntity  channelInfoEntity = configService.getByChannelNo(code);
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(channelInfoEntity);
            mav.addObject("message", "success");
            mav.addObject("goodInfo",json);
            mav.addObject("doWhat","edit");
        } catch (Exception e){
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/channelInfo/add")
    @ResponseBody
    public Result saveUser(@ModelAttribute ChannelInfoEntity channelInfoEntity) {
        Result result = new Result();
        try {

            if (configService.getByChannelName(channelInfoEntity.getChannelName()) != null) {
                result.setSuccessful(false);
                result.setMsg("添加通道失败，通道名称：" + channelInfoEntity.getChannelName() + "已存在。");
                return result;
            }
            configService.saveChannelInfoEntity(channelInfoEntity);

            result.setSuccessful(true);
            result.setMsg("保存成功");
        }catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/channelInfo/edit/{channelNo}")
    @ResponseBody
    public Result editUser(@ModelAttribute ChannelInfoEntity channelInfo, @PathVariable String channelNo) {
        Result result = new Result();
        try {

            channelInfo.setChannelNo(channelNo);
            if(channelInfo.getChannelNo()!=null) {
                ChannelInfoEntity channelInfoTmp = configService.getByChannelName(channelInfo.getChannelName());
                if (channelInfoTmp != null) {
                    if (!channelInfoTmp.getChannelNo().equals(channelInfoTmp.getChannelNo())) {
                        result.setSuccessful(false);
                        result.setMsg("添加通道失败，通道名称：" + channelInfoTmp.getChannelName() + "已存在。");
                        return result;
                    }
                }
                configService.updateChannelInfoEntity(channelInfo);
            }
            result.setSuccessful(true);
            result.setMsg("保存成功");
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }
    //-----------------------------------------------------------------------------------------------------stockInfo_start----------------------------------------------------------------------
    @RequestMapping(value="/stockInfo/listShow")
    @ResponseBody
    public GridModel stockInfoListShow() {
        GridModel m = new GridModel();
        Page info = null;
        StockInfoEntity  stockInfoEntity = form(StockInfoEntity.class);
        try {
            info = configService.stockInfoFindList(page(), stockInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }

    @RequestMapping(value="/stockInfo/delete/{id}", method=RequestMethod.POST)
    @ResponseBody
    public Result stockInfoDelete(@PathVariable String id) {
        Result result = new Result();
        TableColumnByColumn tableColumnByColumn = new TableColumnByColumn();
        tableColumnByColumn.setShowCol("COUNT(1)");
        tableColumnByColumn.setTableName("ship_cargo_record");
        tableColumnByColumn.setCondCol("STOCK_NO");
        tableColumnByColumn.setCondValue(id);
        tableColumnByColumn.setCondOperate("=");
        tableColumnByColumn.setCondType("1");
        tableColumnByColumn.setCondCnt(1);
        String tmp1 = commonService.getTableColumnByColumn(tableColumnByColumn);

        if (tmp1 != null && Integer.parseInt(tmp1) > 0 ) {
            result.setSuccessful(false);
            result.setMsg("该堆垛正在被使用，不能删除！");
            return result;
        }

        configService.stockInfoDelete(id);
        result.setSuccessful(true);
        result.setMsg("删除成功");
        return result;
    }

    @RequestMapping(value="/stockInfoEdit")
    @ResponseBody
    public ModelAndView stockInfoEdit() {
        ModelAndView mav = new ModelAndView(STOCKINFOEDIT);
       StockInfoEntity stockInfoEntity = new StockInfoEntity();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(stockInfoEntity);
            mav.addObject("message", "success");
            mav.addObject("goodInfo",json);
            mav.addObject("doWhat","add");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/stockInfoEdit/{code}")
    @ResponseBody
    public ModelAndView editStockInfo(@PathVariable String code) {
        ModelAndView mav = new ModelAndView(STOCKINFOEDIT);
        try {
            StockInfoEntity  stockInfoEntity = configService.getByStockNo(code);
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(stockInfoEntity);
            mav.addObject("message", "success");
            mav.addObject("goodInfo",json);
            mav.addObject("doWhat","edit");
        } catch (Exception e){
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/stockInfo/add")
    @ResponseBody
    public Result saveUser(@ModelAttribute StockInfoEntity stockInfoEntity) {
        Result result = new Result();
        try {

            if (configService.getByStockName(stockInfoEntity.getStockName()) != null) {
                result.setSuccessful(false);
                result.setMsg("添加堆垛失败，堆垛名称：" + stockInfoEntity.getStockName() + "已存在。");
                return result;
            }
            configService.saveStockInfoEntity(stockInfoEntity);

            result.setSuccessful(true);
            result.setMsg("保存成功");
        }catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/stockInfo/edit/{stockNo}")
    @ResponseBody
    public Result editUser(@ModelAttribute StockInfoEntity stockInfo, @PathVariable String stockNo) {
        Result result = new Result();
        try {

            stockInfo.setStockNo(stockNo);
            if(stockInfo.getStockNo()!=null) {
                StockInfoEntity stockInfoTmp = configService.getByStockName(stockInfo.getStockName());
                if (stockInfoTmp != null) {
                    if (!stockInfoTmp.getStockNo().equals(stockInfoTmp.getStockNo())) {
                        result.setSuccessful(false);
                        result.setMsg("添加堆垛失败，堆垛名称：" + stockInfoTmp.getStockName() + "已存在。");
                        return result;
                    }
                }
                configService.updateStockInfoEntity(stockInfo);
            }
            result.setSuccessful(true);
            result.setMsg("保存成功");
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }
    //-----------------------------------------------------------------------------------------------------customerInfo_start----------------------------------------------------------------------
    @RequestMapping(value="/customerInfo/listShow")
    @ResponseBody
    public GridModel customerInfoListShow() {
        GridModel m = new GridModel();
        CustomerInfo customerInfo = form(CustomerInfo.class);
        Page info = null;
        try {
            info = configService.customerInfoFindList(page(), customerInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }

    @RequestMapping(value="/customerInfo/delete/{id}", method=RequestMethod.POST)
    @ResponseBody
    public Result customerInfoDelete(@PathVariable String id) {
        Result result = new Result();
        TableColumnByColumn tableColumnByColumn = new TableColumnByColumn();
        tableColumnByColumn.setShowCol("COUNT(1)");
        tableColumnByColumn.setTableName("rlrecordmstqy");
        tableColumnByColumn.setCondCol("customer_no");
        tableColumnByColumn.setCondValue(id);
        tableColumnByColumn.setCondOperate("=");
        tableColumnByColumn.setCondType("1");
        tableColumnByColumn.setCondCnt(1);
        try {
            String tmp = commonService.getTableColumnByColumn(tableColumnByColumn);
            if (tmp != null && Integer.parseInt(tmp) > 0 ) {
                result.setSuccessful(false);
                result.setMsg("该收货单位编号正在被使用，不能删除！");
                return result;
            }
            tableColumnByColumn.setTableName("rlrecordmstqy");
            tmp = commonService.getTableColumnByColumn(tableColumnByColumn);
            if (tmp != null && Integer.parseInt(tmp) > 0 ) {
                result.setSuccessful(false);
                result.setMsg("该收货编号正在被使用，不能删除！");
                return result;
            }
            configService.customerInfoDelete(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        result.setSuccessful(true);
        result.setMsg("删除成功");
        return result;
    }

    @RequestMapping(value="/customerInfoEdit")
    @ResponseBody
    public ModelAndView customerInfoEdit() {
        ModelAndView mav = new ModelAndView(CUSTOMERINFOEDIT);
        CustomerInfo customerInfo = new CustomerInfo();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(customerInfo);
            mav.addObject("message", "success");
            mav.addObject("customerInfo",json);
            mav.addObject("doWhat","add");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/customerInfoEdit/{code}")
    @ResponseBody
    public ModelAndView editCustomerInfo(@PathVariable String code) {
        ModelAndView mav = new ModelAndView(CUSTOMERINFOEDIT);
        try {
            CustomerInfo  customerInfo = configService.getByCusNo(code);
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(customerInfo);
            mav.addObject("message", "success");
            mav.addObject("customerInfo",json);
            mav.addObject("doWhat","edit");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/customerInfo/add")
    @ResponseBody
    public Result saveUser(@ModelAttribute CustomerInfo customerInfo) {
        Result result = new Result();
        try {

            if (configService.getByCusName(customerInfo.getCustomerName()) != null) {
                result.setSuccessful(false);
                result.setMsg("收货单位添加失败，收货单位名称：" + customerInfo.getCustomerName() + "已存在。");
                return result;
            }
            configService.saveCustomerInfo(customerInfo);

            result.setSuccessful(true);
            result.setMsg("保存成功");
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(value="/customerInfo/edit/{customerNo}")
    @ResponseBody
    public Result editUser(@ModelAttribute CustomerInfo customerInfo, @PathVariable String customerNo) {
        Result result = new Result();
        try {
            customerInfo.setCustomerNo(customerNo);
            if(customerInfo.getCustomerNo()!=null) {
                CustomerInfo customerInfoTmp = configService.getByCusName(customerInfo.getCustomerName());
                if (customerInfoTmp != null) {
                    if (!customerInfo.getCustomerNo().equals(customerInfoTmp.getCustomerNo())) {
                        result.setSuccessful(false);
                        result.setMsg("收货单位修改失败，收货单位名称：" + customerInfoTmp.getCustomerName() + "已存在。");
                        return result;
                    }
                }
                configService.updateCustomerInfo(customerInfo);
            }
            result.setSuccessful(true);
            result.setMsg("保存成功");
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }
}
