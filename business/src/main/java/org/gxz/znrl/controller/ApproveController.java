package org.gxz.znrl.controller;

import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.entity.ApproveEntity;
import org.gxz.znrl.entity.CarInfoEntity;
import org.gxz.znrl.entity.TruckFuelEntranceRecordEntity;
import org.gxz.znrl.entity.TruckFuelUnloadEntity;
import org.gxz.znrl.service.IApproveService;
import org.gxz.znrl.shiro.ShiroUser;
import org.gxz.znrl.viewModel.GridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gxz.znrl.common.util.JacksonMapper;

/**
 * Created by xieyt on 2015/1/20.
 */

@Controller
@RequestMapping("/business/approve")
@SuppressWarnings("unchecked")
public class ApproveController extends BaseAction {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public IApproveService approveService;

    //审批页面跳转
    @RequestMapping(value="/apprwork/list", method= RequestMethod.GET)
    public String gotoApprove() {
        return "/approve/approveList";
    }

    //审批页面跳转
    @RequestMapping(value="/apprwork/hislist", method= RequestMethod.GET)
    public String gotoApproveHis() {
        return "/approve/approveHisList";
    }


    @RequestMapping(value="/qryApproveList")
    @ResponseBody
    public GridModel qryApproveList(){
        ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

        //获取登录操作员的ID
        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        approveEntity.setOpCode(opCode);

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = approveService.qryApprove( approveEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    /**
     * 查询当前用户相关的审批的事项
     */
    @RequestMapping(value="/qryMyApproveList")
    @ResponseBody
    public GridModel qryMyApprove(){
        ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

        approveEntity.setPageRowIndex(getPageIndex(), getPageSize());
        //获取登录操作员的ID
        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        approveEntity.setOpCode(opCode);

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = approveService.qryMyApprove(approveEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    /**
     * 查询审批的事项审批过程
     */
    @RequestMapping(value="/qryApproveProgress")
    @ResponseBody
    public GridModel qryApproveProgress(){
        ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

        //查询分页
        GridModel gridModel = null;
        try {
            gridModel = approveService.qryApproveProgress(approveEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    //保存审批结果
    @RequestMapping(value="/saveApprResult")
    @ResponseBody
    public Result saveApprResult(){
        Result result = new Result();
        try {
            ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            approveEntity.setOpCode(opCode);

            //保存审批结果
            approveService.saveApprResult(approveEntity);

            //设置返回结果
            if (approveEntity.getResCode() != null && approveEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("审批成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("审批失败！" + approveEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("审批结果提交失败:"+e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    //气动样柜提交审批
    @RequestMapping(value="/submitToApprove")
    @ResponseBody
    public Result submitToApprove(){
        Result result = new Result();
        try {
            ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            approveEntity.setOpCode(opCode);

            //保存审批结果
            approveService.submitToApprove(approveEntity);

            //设置返回结果
            if (approveEntity.getResCode() != null && approveEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("提交审批成功，请等待审批...");
            } else {
                result.setSuccessful(false);
                result.setMsg("提交审批失败！" + approveEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("提交审批异常！");
            e.printStackTrace();
        }
        return result;
    }


    //远光气动样柜提交审批
    @RequestMapping(value="/submitToApprove4YG")
    @ResponseBody
    public Result submitToApprove4YG(){
        Result result = new Result();
        try {
            ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            approveEntity.setOpCode(opCode);

            //保存审批结果
            approveService.submitToApprove4YG(approveEntity);

            //设置返回结果
            if (approveEntity.getResCode() != null && approveEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("提交审批成功，请等待审批...");
            } else {
                result.setSuccessful(false);
                result.setMsg("提交审批失败！" + approveEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("提交审批异常！");
            e.printStackTrace();
        }
        return result;
    }

    //修改计量数据提交审批
    @RequestMapping(value="/submitKdToApprove")
    @ResponseBody
    public Result submitKdToApprove(){
        Result result = new Result();
        try {
            ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            approveEntity.setOpCode(opCode);

            //保存审批结果
            approveService.submitKdToApprove(approveEntity);

            //设置返回结果
            if (approveEntity.getResCode() != null && approveEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("提交审批成功，请等待审批...");
            } else {
                result.setSuccessful(false);
                result.setMsg("提交审批失败！" + approveEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("提交审批异常！");
            e.printStackTrace();
        }
        return result;
    }

    //人工采样提交审批
    @RequestMapping(value="/submitManualSampleApprove")
    @ResponseBody
    public Result submitManualSampleApprove(){
        Result result = new Result();
        try {
            ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            approveEntity.setOpCode(opCode);

            //保存审批结果
            approveService.submitManualSampleApprove(approveEntity);

            //设置返回结果
            if (approveEntity.getResCode() != null && approveEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("提交审批成功，请等待审批...");
            } else {
                result.setSuccessful(false);
                result.setMsg("提交审批失败！" + approveEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("提交审批异常！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/viewKdApprDetial/{apprEventId}")
    @ResponseBody
    public ModelAndView viewKdApprDetial(@PathVariable String apprEventId){
        ModelAndView mav = new ModelAndView("/report/kdQty");

        try {
            ApproveEntity approveEntity = new ApproveEntity();

            approveEntity.setApprEventId(apprEventId);
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            approveEntity.setOpCode(opCode);

            //查询分页
            GridModel gridModel = null;
            try {
                gridModel = approveService.qryApprove( approveEntity);

                if (gridModel.getRows().size() > 0){
                    TruckFuelEntranceRecordEntity truckFuelUnloadEntity = new TruckFuelEntranceRecordEntity();
                    ApproveEntity approveEntityRes = new ApproveEntity();
                    approveEntityRes = (ApproveEntity)gridModel.getRows().get(0);
                    //整合
                    truckFuelUnloadEntity.setMzQty(approveEntityRes.getParam3());
                    truckFuelUnloadEntity.setPzQty(approveEntityRes.getParam4());
                    truckFuelUnloadEntity.setTickQty(approveEntityRes.getParam6());
                    truckFuelUnloadEntity.setKdQty(approveEntityRes.getParam5());
                    truckFuelUnloadEntity.setKdBak(approveEntityRes.getApprContent());
                    truckFuelUnloadEntity.setCarId(approveEntityRes.getParam11());
                    ObjectMapper objectMapper = JacksonMapper.getInstance();
                    String json =objectMapper.writeValueAsString(truckFuelUnloadEntity);
                    mav.addObject("message", "success");
                    mav.addObject("truckFuelUnloadEntity",json);
                }else{
                    mav.addObject("message", "fail");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //查询汽车重量信息
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }

    //电子样柜提交审批
    @RequestMapping(value="/boxSubmitToApprove")
    @ResponseBody
    public Result boxSubmitToApprove(){
        Result result = new Result();
        try {
            ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            approveEntity.setOpCode(opCode);

            //保存审批结果
            approveService.boxSubmitToApprove(approveEntity);

            //设置返回结果
            if (approveEntity.getResCode() != null && approveEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("提交审批成功，请等待审批...");
            } else {
                result.setSuccessful(false);
                result.setMsg("提交审批失败！" + approveEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("提交审批异常！");
            e.printStackTrace();
        }
        return result;
    }


    //查看审批详情
    @RequestMapping(value="/approveProgressWin")
    @ResponseBody
    public ModelAndView goToApproveProgressWin(){
        return new ModelAndView("/approve/viewApprovedProgress");
    }


    //查看审批详情
    @RequestMapping(value="/approveCabinetProgressWin/{eventId}")
    @ResponseBody
    public ModelAndView qryLabReportData4RL(@PathVariable String eventId) {
        ModelAndView mav = new ModelAndView("/approve/approveCabinetProgressWin");
        String resString = eventId;
        mav.addObject("v_eventId",resString);
        return mav;
    }

    /**
     * 查询审批的煤样取样、弃样审批详情
     */
    @RequestMapping(value="/qryApproveResultInfo")
    @ResponseBody
    public GridModel qryApproveResultInfo(){
        ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = approveService.qryApproveResultInfo(approveEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查看新增火车车节详情子页面
    @RequestMapping(value="/addTrainsDetailView/{eventId}")
    @ResponseBody
    public ModelAndView gotoAddTrainsDetailView(@PathVariable String eventId){
        ModelAndView mav = new ModelAndView("/approve/addTrainsDetailView");
        mav.addObject("eventId",eventId);
        return mav;
    }

    //大开新增火车车节信息审批
    @RequestMapping(value="/submitToApprove4AddTrains")
    @ResponseBody
    public Result submitToApprove4AddTrains(){
        Result result = new Result();
        try {
            ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            approveEntity.setOpCode(opCode);

            //保存审批结果
            approveService.submitToApprove4AddTrains(approveEntity);

            //设置返回结果
            if (approveEntity.getResCode() != null && approveEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("提交审批成功，请等待审批...");
            } else {
                result.setSuccessful(false);
                result.setMsg("提交审批失败！" + approveEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("提交审批异常！");
            e.printStackTrace();
        }
        return result;
    }

    //大开-新增获取火车车节审批详情
    @RequestMapping(value="/getApprAddTrainsDetailData/{eventId}")
    @ResponseBody
    public Result getApprAddTrainsDetailData(@PathVariable String eventId){
        Result result = new Result();
        try {

            ApproveEntity approveEntity = new ApproveEntity();
            approveEntity.setEventId(eventId);
            approveEntity = approveService.getApprAddTrainsDetailData(approveEntity);
            String json = null;

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            json =objectMapper.writeValueAsString(approveEntity);

            //设置返回结果
            if (json != null) {
                result.setSuccessful(true);
                result.setData(json);
            } else {
                result.setSuccessful(false);
                result.setMsg("查询审批详情失败！");
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("查询审批详情失败！");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询审批的煤样取样、弃样审批详情 谏壁审批详情作废的人不需要显示 hxl20180125
     */
    @RequestMapping(value="/qryApproveResultInfo4JB")
    @ResponseBody
    public GridModel qryApproveResultInfo4JB(){
        ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = approveService.qryApproveResultInfo4JB(approveEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    /**
     * 查询审批的煤样取样、弃样审批详情 大武口 hxl20180517
     */
    @RequestMapping(value="/qryApproveResultInfo4DWK")
    @ResponseBody
    public GridModel qryApproveResultInfo4DWK(){
        ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = approveService.qryApproveResultInfo4DWK(approveEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**
     * 申请管理员账号的 提交审批 xxs20180809
     */
    @RequestMapping(value="/submitSysUserToApprove")
    @ResponseBody
    public Result submitSysUserToApprove(){
        Result result = new Result();
        try {
            ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            approveEntity.setOpCode(opCode);

            //保存审批结果
            approveService.submitSysUserToApprove(approveEntity);

            //设置返回结果
            if (approveEntity.getResCode() != null && approveEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("提交审批成功，请等待审批...");
            } else {
                result.setSuccessful(false);
                result.setMsg("提交审批失败！" + approveEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("提交审批异常！");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询 申请管理员账号的 审批详情 xxs20180809
     */
    @RequestMapping(value="/viewSysUserApprDetial/{apprEventId}")
    @ResponseBody
    public ModelAndView viewSysUserApprDetial(@PathVariable String apprEventId){
        ModelAndView mav = new ModelAndView("/monitor/sysUserApprDetail");

        try {
            ApproveEntity approveEntity = new ApproveEntity();

            approveEntity.setApprEventId(apprEventId);
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            approveEntity.setOpCode(opCode);

            //查询分页
            GridModel gridModel = null;
            try {
                gridModel = approveService.qryApprove( approveEntity);

                if (gridModel.getRows().size() > 0){
                    ApproveEntity approveEntityRes = new ApproveEntity();
                    approveEntityRes = (ApproveEntity)gridModel.getRows().get(0);
                    //整合
                    approveEntity.setUserId(approveEntityRes.getParam11());
                    approveEntity.setUserName(approveEntityRes.getParam12());
                    approveEntity.setUserPassword(approveEntityRes.getParam13());
                    approveEntity.setUserRealName(approveEntityRes.getParam14());
                    approveEntity.setRoleName(approveEntityRes.getParam15());
                    approveEntity.setHours(approveEntityRes.getParam18());
                    approveEntity.setRemark(approveEntityRes.getApprContent());
                    ObjectMapper objectMapper = JacksonMapper.getInstance();
                    String json =objectMapper.writeValueAsString(approveEntity);
                    mav.addObject("message", "success");
                    mav.addObject("ApproveEntity",json);
                }else{
                    mav.addObject("message", "fail");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //查询汽车重量信息
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/qryApproveResultInfo4Qz")
    @ResponseBody
    public GridModel qryApproveResultInfo4Qz(){
        ApproveEntity approveEntity = SearchForm(ApproveEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = approveService.qryApproveResultInfo4Qz(approveEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }
}
