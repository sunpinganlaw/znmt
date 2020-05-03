package org.gxz.znrl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.engine.impl.util.json.JSONArray;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.util.JacksonMapper;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.entity.*;
import org.gxz.znrl.service.ILaboryService;
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
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



/**
 * Created by xieyt on 2015/6/4.
 * 化验室相关功能的后台controller入口
 */

@Controller
@RequestMapping("/business/labory")
@SuppressWarnings("unchecked")
public class LaboryController extends BaseAction {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public ILaboryService laboryService;

    //化验结果原始数据页面
    @RequestMapping(value="/qryLabOrgData", method= RequestMethod.GET)
    public String gotoLaboryOrgDataList() {
        return "/labory/laboryOrgDataList";
    }

    //化验结果数据页面
    @RequestMapping(value="/qryLabData", method= RequestMethod.GET)
    public String gotoLaboryDataList() {
        return "/labory/laboryDataList";
    }

    //抽查样化验报告页面
    @RequestMapping(value="/qryLabReport4CCY", method= RequestMethod.GET)
    public String gotoLaboryReportList4CCY() {
        return "/labory/labReportList4CCY";
    }
    //化验报告数据页面（隐藏矿点等敏感信息）
    @RequestMapping(value="/qryLabReport", method= RequestMethod.GET)
    public String gotoLaboryReportList() {
        return "/labory/labReportList";
    }

    //化验报告数据页面（带矿点等敏感信息展示）
    @RequestMapping(value="/qryLabReportAll", method= RequestMethod.GET)
    public String gotoQryLabReportAll() {
        return "/labory/labReportListMine";
    }
    //化验台账数据页面
    @RequestMapping(value="/qryLabBook", method= RequestMethod.GET)
    public String gotoLaboryBookList() {
        return "/labory/labBookList";
    }
    //化验台账查询化验结果数据页面
    @RequestMapping(value="/bookRptView", method= RequestMethod.GET)
    public String gotoBookRptView() {
        return "/labory/bookRptView";
    }

    //化验编码打印
    @RequestMapping(value="/manLabor", method= RequestMethod.GET)
    public String gotoManSample() {
        return "/labory/manLabor";
    }

    //跳转到新增化验原始数据页面 -
    @RequestMapping(value="/gotoAddLabOrgDataPage", method= RequestMethod.GET)
    public String gotoAddLabOrgDataPage() {
        return "/labory/addLabOrgData";
    }

    @RequestMapping(value="/loteditLabData", method= RequestMethod.GET)
    public String gotoLoteditLabData() {
        return "/labory/loteditLabData";
    }

    @RequestMapping(value="/bookRptViewForApprove", method= RequestMethod.GET)
    public ModelAndView bookRptViewForApprove(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/labory/bookRptViewForApprove");
        mav.addObject("standingBookId", request.getParameter("standingBookId"));
        return mav;
    }
    //元素值设置
    @RequestMapping(value="/qryChemicalList", method= RequestMethod.GET)
    public String qryChemicalList() {
        return "/labory/chemicalList";
    }

    //化验数据上传
    @RequestMapping(value="/labDataUploadPage", method= RequestMethod.GET)
    public String labDataUploadPage() {
        return "/labory/labDataUpload";
    }

    //九江全水替换页面
    @RequestMapping(value="/mtReplace", method= RequestMethod.GET)
    public String gotoMtReplacePage() {
        return "/labory/mtReplace";
    }


    /////////////////////////////////////////入炉化验室开始//////////////////////////////////////////////////
    //化验结果原始数据页面
    @RequestMapping(value="/qryLabOrgDataRL", method= RequestMethod.GET)
    public String gotoLaboryOrgDataListRL() {
        return "/labory/laboryOrgDataList_RL";
    }

    //化验结果数据页面
    @RequestMapping(value="/qryLabDataRL", method= RequestMethod.GET)
    public String gotoLaboryDataListRL() {
        return "/labory/laboryDataList_RL";
    }
    //化验报告数据页面
    @RequestMapping(value="/qryLabReportRL", method= RequestMethod.GET)
    public String gotoLaboryReportListRL() {
        return "/labory/labReportList_RL";
    }

    //化验编码打印
    @RequestMapping(value="/manLaborRL", method= RequestMethod.GET)
    public String gotoManSampleRL() {
        return "/labory/manLabor_RL";
    }

    //元素值设置
    @RequestMapping(value="/qryChemicalListRL", method= RequestMethod.GET)
    public String qryChemicalListRL() {
        return "/labory/chemicalList_RL";
    }

    //化验数据上传
    @RequestMapping(value="/labDataUploadPageRL", method= RequestMethod.GET)
    public String labDataUploadPageRL() {
        return "/labory/labDataUpload_RL";
    }

    //////////////////////////////////////////入炉化验室结束////////////////////////////////////////////////

    //查询灰融化验室结果原始数据
    @RequestMapping(value="/qryMeltResOrgData")
    @ResponseBody
    public GridModel qryMeltResOrgData(){
        MeltEntity meltEntity = SearchForm(MeltEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        meltEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryMeltResOrgData(meltEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    //查询灰融化验室结果原始数据
    @RequestMapping(value="/qryMeltResData")
    @ResponseBody
    public GridModel qryMeltResData(){
        MeltEntity meltEntity = SearchForm(MeltEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        meltEntity.setOpCode(opCode);
        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryMeltResData(meltEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询天平化验室结果原始数据
    @RequestMapping(value="/qryScalesResOrgData")
    @ResponseBody
    public GridModel qryScalesResOrgData(){
        ScalesEntity scalesEntity = SearchForm(ScalesEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        scalesEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryScalesResOrgData(scalesEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询天平化验室结果原始数据
    @RequestMapping(value="/qryScalesResOrgDataNew")
    @ResponseBody
    public GridModel qryScalesResOrgDataNew(){
        ScalesEntity scalesEntity = SearchForm(ScalesEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        scalesEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryScalesResOrgDataNew(scalesEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询天平化验室结果原始数据
    @RequestMapping(value="/qryMAVResData")
    @ResponseBody
    public GridModel qryMAVResData(){
        ScalesEntity scalesEntity = SearchForm(ScalesEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        scalesEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryMAVResData(scalesEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询天平化验室结果数据
    @RequestMapping(value="/qryScalesResData")
    @ResponseBody
    public GridModel qryScalesResData(){
        ScalesEntity scalesEntity = SearchForm(ScalesEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryScalesResData(scalesEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    //查询定硫仪化验室结果数据
    @RequestMapping(value="/qrySResData")
    @ResponseBody
    public GridModel qrySResData(){
        SEntity sEntity = SearchForm(SEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qrySResData(sEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询定硫仪化验室结果原始数据
    @RequestMapping(value="/qrySResOrgData")
    @ResponseBody
    public GridModel qrySResOrgData(){
        SEntity sEntity = SearchForm(SEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        sEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qrySResOrgData(sEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**
     * 查询工业分析仪化验原始数据
     */
    @RequestMapping(value="/qryIAResOrgData")
    @ResponseBody
    public GridModel qryIAResOrgData(){
        IndstAnalyEntity indstAnalyEntity = SearchForm(IndstAnalyEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        indstAnalyEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryIAResOrgData(indstAnalyEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**
     * 查询工业分析仪化验数据
     */
    @RequestMapping(value="/qryIAResData")
    @ResponseBody
    public GridModel qryIAResData(){
        IndstAnalyEntity indstAnalyEntity = SearchForm(IndstAnalyEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryIAResData(indstAnalyEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    /**
     * 查询量热仪化验原始数据
     */
    @RequestMapping(value="/qryHotResOrgData")
    @ResponseBody
    public GridModel qryHotResOrgData(){
        HotEntity hotEntity = SearchForm(HotEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        hotEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryHotResOrgData(hotEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**
     * 查询量热仪化验数据
     */
    @RequestMapping(value="/qryHotResData")
    @ResponseBody
    public GridModel qryHotResData(){
        HotEntity hotEntity = SearchForm(HotEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryHotResData(hotEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    /**
     * 查询光波分析化验原始数据
     */
    @RequestMapping(value="/qryLaResOrgData")
    @ResponseBody
    public GridModel qryLaResOrgData(){
        LightAnalyEntity lightAnalyEntity = SearchForm(LightAnalyEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        lightAnalyEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryLaResOrgData(lightAnalyEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**
     * 查询红外测氢仪化验原始数据
     */
    @RequestMapping(value="/qryHwResOrgData")
    @ResponseBody
    public GridModel qryHwResOrgData(){
        HEntity hEntity = SearchForm(HEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        hEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryHwResOrgData(hEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**    根据入厂入炉条件查询化验原始数据开始  */

    //查询天平化验室结果原始数据
    @RequestMapping(value="/qryScalesResOrgDataByUsage")
    @ResponseBody
    public GridModel qryScalesResOrgDataByUsage(){
        ScalesEntity scalesEntity = SearchForm(ScalesEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        scalesEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryScalesResOrgDataByUsage(scalesEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询定硫仪化验室结果原始数据
    @RequestMapping(value="/qrySResOrgDataByUsage")
    @ResponseBody
    public GridModel qrySResOrgDataByUsage(){
        SEntity sEntity = SearchForm(SEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        sEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qrySResOrgDataByUsage(sEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**
     * 查询工业分析仪化验原始数据
     */
    @RequestMapping(value="/qryIAResOrgDataByUsage")
    @ResponseBody
    public GridModel qryIAResOrgDataByUsage(){
        IndstAnalyEntity indstAnalyEntity = SearchForm(IndstAnalyEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        indstAnalyEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryIAResOrgDataByUsage(indstAnalyEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**
     * 查询量热仪化验原始数据
     */
    @RequestMapping(value="/qryHotResOrgDataByUsage")
    @ResponseBody
    public GridModel qryHotResOrgDataByUsage(){
        HotEntity hotEntity = SearchForm(HotEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        hotEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryHotResOrgDataByUsage(hotEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**
     * 查询光波分析化验原始数据
     */
    @RequestMapping(value="/qryLaResOrgDataByUsage")
    @ResponseBody
    public GridModel qryLaResOrgDataByUsage(){
        LightAnalyEntity lightAnalyEntity = SearchForm(LightAnalyEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        lightAnalyEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryLaResOrgDataByUsage(lightAnalyEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**
     * 查询红外测氢仪化验原始数据
     */
    @RequestMapping(value="/qryHwResOrgDataByUsage")
    @ResponseBody
    public GridModel qryHwResOrgDataByUsage(){
        HEntity hEntity = SearchForm(HEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        hEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryHwResOrgDataByUsage(hEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    //查询灰融化验室结果原始数据
    @RequestMapping(value="/qryMeltResOrgDataByUsage")
    @ResponseBody
    public GridModel qryMeltResOrgDataByUsage(){
        MeltEntity meltEntity = SearchForm(MeltEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        meltEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryMeltResOrgDataByUsage(meltEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**    根据入厂入炉条件查询化验原始数据结束 */

    /**
     * 查询光波分析化验数据
     */
    @RequestMapping(value="/qryLaResData")
    @ResponseBody
    public GridModel qryLaResData(){
        LightAnalyEntity lightAnalyEntity = SearchForm(LightAnalyEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryLaResData(lightAnalyEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**
     * 查询光波分析化验数据
     */
    @RequestMapping(value="/qryHwResData")
    @ResponseBody
    public GridModel qryHwResData(){
        HEntity hEntity = SearchForm(HEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryHwResData(hEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //编辑化验数据
    @RequestMapping(value="/editLabData/{dataType}")
    @ResponseBody
    @Transactional
    public Result editLabData(HttpServletRequest req, @PathVariable String dataType){
        Result result = new Result();

        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String updateStr = req.getParameter("updated");

        try {
            //获取界面输入参数
            SEntity sEntity = new SEntity();
            sEntity.setUpdateString(updateStr);
            sEntity.setDataType(dataType);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String updateCode = shiroUser.getUser().getId().toString();
            sEntity.setUpdateCode(updateCode);

            //编辑数据
            laboryService.editLabData(sEntity);

            //设置返回结果
            if (sEntity.getResCode() != null && sEntity.getResCode().equals("0")) {
                result.setMsg("化验数据更新成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("化验数据更新失败:"+sEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("化验数据更新异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }
    //编辑化验数据
    @RequestMapping(value="/deleteLabData/{dataType}")
    @ResponseBody
    @Transactional
    public Result deleteLabData(HttpServletRequest req, @PathVariable String dataType){
        Result result = new Result();

        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String updateStr = req.getParameter("updated");

        try {
            //获取界面输入参数
            SEntity sEntity = new SEntity();
            sEntity.setUpdateString(updateStr);
            sEntity.setDataType(dataType);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String updateCode = shiroUser.getUser().getId().toString();
            sEntity.setUpdateCode(updateCode);

            //编辑数据
            laboryService.deleteLabData(sEntity);

            //设置返回结果
            if (sEntity.getResCode() != null && sEntity.getResCode().equals("0")) {
                result.setMsg("化验数据失效成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("化验数据失效失败:"+sEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("化验数据失效异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //提交审批，并生成化验报告
    @RequestMapping(value="/submitLabReport")
    @ResponseBody
    @Transactional
    public Result submit4LabReport(){
        Result result = new Result();

        try {
            SEntity sEntity = SearchForm(SEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            sEntity.setOpCode(opCode);

            //编辑数据
            laboryService.submit4LabReport(sEntity);

            //设置返回结果
            if (sEntity.getResCode() != null && sEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:"+sEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //提交审批，并生成化验报告
    @RequestMapping(value="/submitLabReportNew")
    @ResponseBody
    @Transactional
    public Result submit4LabReportNew(){
        Result result = new Result();

        try {
            SEntity sEntity = SearchForm(SEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            sEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json =objectMapper.writeValueAsString(sEntity);

            sEntity.setJsonStr(json);

            //编辑数据
            laboryService.submit4LabReportNew(sEntity);

            //设置返回结果
            if (sEntity.getResCode() != null && sEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:"+sEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //提交审批
    @RequestMapping(value="/labReport2Appr")
    @ResponseBody
    @Transactional
    public Result labReport2Appr(){
        Result result = new Result();

        try {
            SEntity sEntity = SearchForm(SEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            sEntity.setOpCode(opCode);

            //编辑数据
            laboryService.labReport2Appr(sEntity);

            //设置返回结果
            if (sEntity.getResCode() != null && sEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:"+sEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //提交审批，并生成化验报告
    @RequestMapping(value="/submitLabResults")
    @ResponseBody
    @Transactional
    public Result submitLabResults(){
        Result result = new Result();

        try {
            SEntity sEntity = SearchForm(SEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            sEntity.setOpCode(opCode);

            //编辑数据
            laboryService.submitLabResults(sEntity);

            //设置返回结果
            if (sEntity.getResCode() != null && sEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:"+sEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //泉州电厂化验入炉化验报告审批，每天两个批次生成一个化验报告  edit by yangff 2016-06-01
    @RequestMapping(value="/submitRLLabResults")
    @ResponseBody
    @Transactional
    public Result submitRLLabResults(){
        Result result = new Result();

        try {
            SEntity sEntity = SearchForm(SEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            sEntity.setOpCode(opCode);

            //编辑数据
            laboryService.submitRLLabResults(sEntity);

            //设置返回结果
            if (sEntity.getResCode() != null && sEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:"+sEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //加权处理
    @RequestMapping(value="/doProportion")
    @ResponseBody
    @Transactional
    public Result doProportion(){
        Result result = new Result();

        try {
            SEntity sEntity = SearchForm(SEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            sEntity.setOpCode(opCode);

            //编辑数据
            laboryService.doProportion(sEntity);

            //设置返回结果
            if (sEntity.getResCode() != null && sEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败: "+sEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //提交审批化验台账
    @RequestMapping(value="/submitLabBook")
    @ResponseBody
    @Transactional
    public Result submitLabBook(){
        Result result = new Result();
        try {
            BookEntity bookEntity = SearchForm(BookEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            bookEntity.setOpCode(opCode);

            //编辑数据
            laboryService.submit4LabBook(bookEntity);

            //设置返回结果
            if (bookEntity.getResCode() != null && bookEntity.getResCode().equals("0")) {
                result.setMsg("操作成功，请等待审批!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:"+bookEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询船运煤化验报告数据
     */
    @RequestMapping(value="/qryReportData4Ship")
    @ResponseBody
    public GridModel qryReportData4Ship(){
        ReportEntity reportEntity = SearchForm(ReportEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryReportData4Ship(reportEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**
     * 查询船运煤化验报告数据
     */
    @RequestMapping(value="/qryReportData4ShipBB")
    @ResponseBody
    public GridModel qryReportData4ShipBB(){
        ReportEntity reportEntity = SearchForm(ReportEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryReportData4ShipBB(reportEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**
     * 查询船运煤化验报告数据
     */
    @RequestMapping(value="/qryReportData4CCY")
    @ResponseBody
    public GridModel qryReportData4CCY(){
        ReportEntity reportEntity = SearchForm(ReportEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryReportData4CCY(reportEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**
     * 查询船运煤化验报告数据  乐东罗荣需要特定的排序方式 xxs20180316
     */
    @RequestMapping(value="/qryReportData4Ship4ld")
    @ResponseBody
    public GridModel qryReportData4Ship4ld(){
        ReportEntity reportEntity = SearchForm(ReportEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryReportData4Ship4ld(reportEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**
     * 查询加权后的化验报告数据
     */
    @RequestMapping(value="/qryReportData4proportion")
    @ResponseBody
    public GridModel qryReportData4proportion(){
        ReportEntity reportEntity = SearchForm(ReportEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryReportData4proportion(reportEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    /**
     * 查询化验报告数据
     */
    @RequestMapping(value="/qryReportData")
    @ResponseBody
    public GridModel qryReportData(){
        ReportEntity reportEntity = SearchForm(ReportEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryReportData(reportEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }
//    为都匀单独使用 by xxs
    @RequestMapping(value="/qryReportData4Dy")
    @ResponseBody
    public GridModel qryReportData4Dy(){
        ReportEntity reportEntity = SearchForm(ReportEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryReportData4Dy(reportEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //    为蚌埠化验月报单独使用 by zwj
    @RequestMapping(value="/qryReportData4BB")
    @ResponseBody
    public GridModel qryReportData4BB(){
        ReportEntity reportEntity = SearchForm(ReportEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryReportData4BB(reportEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }
    /**
     * 查询化验台账数据
     */
    @RequestMapping(value="/qryBookData")
    @ResponseBody
    public GridModel qryBookData(){
        BookEntity bookEntity = SearchForm(BookEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryBookData(bookEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }
    /**
     * 根据化验台账查询化验报告
     */
    @RequestMapping(value="/qryReportDataView/{standingBookId}")
    @ResponseBody
    public GridModel qrySamplingRptListView(@PathVariable String standingBookId){
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setStandingBookId(standingBookId);
        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryReportDataView(reportEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    @RequestMapping(value="/qryResultReportData")
    @ResponseBody
    public GridModel qryResultReportData(){
        ReportEntity reportEntity = SearchForm(ReportEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryResultReportData(reportEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }


    @RequestMapping(value="/qryResultReportDataForAppr/{appEventId}")
    @ResponseBody
    public ModelAndView qryResultReportDataForAppr(@PathVariable String appEventId){
        ModelAndView mav = new ModelAndView("/labory/bookRptViewForAppr");
        mav.addObject("appEventId", appEventId);
        return mav;
    }

    @RequestMapping(value="/reportRptPrn/{sc}")
    @ResponseBody
    public ModelAndView gotoBarcodePrintWin(@PathVariable String sc) {
        String []str = sc.split("::");
        ModelAndView mav = new ModelAndView("/labory/bookRptPrn");

        mav.addObject("printStr1", str[0]);
        String str1= str[1].split(" ")[0];
        String str2= str[2].split(" ")[0];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(str1);
            date2 = sdf.parse(str2);
            SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMdd");
            String strs1 = sdfa.format(date1);
            String strs2 = sdfa.format(date2);
            mav.addObject("printStr2", strs1);
            mav.addObject("printStr3", strs2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mav;
    }

    //检查船是否已经有灰熔融性温度 常州需求
    @RequestMapping(value="/countShipST/{laborCode}")
    @ResponseBody
    public String countShipST(@PathVariable String laborCode) {
        String resString = null;
        try {
            resString = laboryService.countShipST(laborCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resString;
    }


    //查询化验报告详情
    @RequestMapping(value="/qryLabReportData/{laborCode}/{apprStatusCode}/{batchType}")
    @ResponseBody
    public ModelAndView qryLabReportData(@PathVariable String laborCode, @PathVariable String apprStatusCode, @PathVariable String batchType) {
        ModelAndView mav = new ModelAndView("/labory/labReport");
        String resString = null;
        try {
            resString = laboryService.qryLabReportData(laborCode,batchType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.addObject("LabReportData",resString);
        mav.addObject("apprStatusCode",apprStatusCode);

        return mav;
    }


    //选择化验原始数据类型
    @RequestMapping(value="/checkLabOrgDataType", method= RequestMethod.GET)
    public String gotoCheckLabOrgDataType() {
        return "/labory/labOrgDataTypeCheck";
    }

    //查询化验原始数据表单
    @RequestMapping(value="/qryLabOrgDataView/{laborCodeList}/{indexType}")
    @ResponseBody
    public ModelAndView qryLabOrgDataView(@PathVariable String laborCodeList,@PathVariable String indexType) {
        ModelAndView mav = new ModelAndView("/labory/LabOrgDataReport");
        mav.addObject("laborCodeList",laborCodeList);
        mav.addObject("indexType",indexType);
        return mav;
    }

    //查询抽检样化验报告详情
    @RequestMapping(value="/qryLabReportData4Sample/{laborReportID}/{sampleLaborReportID}")
    @ResponseBody
    public ModelAndView qryLabReportData4Sample(@PathVariable String laborReportID, @PathVariable String sampleLaborReportID) {
        ModelAndView mav = new ModelAndView("/labory/sampleLabReport");
        String resString = null;
        try {
            resString = laboryService.qryLabReportData4Sample(laborReportID,sampleLaborReportID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.addObject("SampleLabReportData",resString);
        return mav;
    }

    //蚌埠查看抽查样对比报告
    @RequestMapping(value="/qryLabReportData4SampleCompare/{sampleLaborReportID}")
    @ResponseBody
    public ModelAndView qryLabReportData4SampleCompare(@PathVariable String sampleLaborReportID) {
        ModelAndView mav = new ModelAndView("/labory/sampleLabReport");
        String resString = null;
        try {
            resString = laboryService.qryLabReportData4SampleCompare(sampleLaborReportID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.addObject("SampleLabReportData",resString);
        return mav;
    }

    //查询化验报告详情
    @RequestMapping(value="/qryLabReportData4Ship/{shipRecID}")
    @ResponseBody
    public ModelAndView qryLabReportData4Ship(@PathVariable String shipRecID) {
        ModelAndView mav = new ModelAndView("/labory/labReport");
        String resString = null;
        try {
            resString = laboryService.qryLabReportData4Ship(shipRecID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.addObject("LabReportData",resString);
        return mav;
    }

    //查询入炉化验报告详情
    @RequestMapping(value="/qryLabReportData4RL/{rlDate}")
    @ResponseBody
    public ModelAndView qryLabReportData4RL(@PathVariable String rlDate) {
        ModelAndView mav = new ModelAndView("/labory/labReportRL");
        String resString = null;
        try {
            resString = laboryService.qryLabReportData4RL(rlDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.addObject("LabReportData",resString);
        return mav;
    }

    //查询整船化验报告详情
    @RequestMapping(value="/getLabReportData4Ship/{shipRecID}")
    @ResponseBody
    public ModelAndView getLabReportData4Ship(@PathVariable String shipRecID) {
        ModelAndView mav = new ModelAndView("/labory/labReportFinal");
        String resString = null;
        try {
            resString = laboryService.getLabReportData4Ship(shipRecID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.addObject("LabReportData",resString);
        return mav;
    }
    //元素类型
    @RequestMapping(value="/qryChemicalAnalyList")
    @ResponseBody
    public GridModel qryChemicalAnalyList(){
        LabChemicalAnalyLogEntity labChemicalAnalyLogEntity = SearchForm(LabChemicalAnalyLogEntity.class);

        //设置分页信息并计算记录开始和结束值
        labChemicalAnalyLogEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryLabChemicalAnalyList(labChemicalAnalyLogEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value="/qryChemicalAnaly/{mineNo}/{chemicalType}")
    @ResponseBody
    public LabChemicalAnalyEntity qryChemicalAnaly(@PathVariable String mineNo,@PathVariable String chemicalType){
        LabChemicalAnalyEntity labChemicalAnalyEntity = new LabChemicalAnalyEntity();
        labChemicalAnalyEntity.setMineNo(mineNo);
        labChemicalAnalyEntity.setChemicalType(chemicalType);
        try {
            labChemicalAnalyEntity = laboryService.qryLabChemicalAnaly(labChemicalAnalyEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return labChemicalAnalyEntity;
    }

    //删除
    @RequestMapping(value="/delChemicalAnaly/{mineChemicalId}")
    @ResponseBody
    public Result delCarForecastInfo(@PathVariable Long mineChemicalId){
        Result result = new Result();
        int recordNo = 0;
        LabChemicalAnalyEntity labChemicalAnalyEntity = new LabChemicalAnalyEntity();
        LabChemicalAnalyLogEntity labChemicalAnalyLogEntity = new LabChemicalAnalyLogEntity();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            labChemicalAnalyEntity.setOpCode(opCode);
            labChemicalAnalyEntity.setMineChemicalId(mineChemicalId);
            labChemicalAnalyLogEntity.setMineChemicalId(mineChemicalId);

            recordNo= laboryService.qryLabChemicalAnalyLogListCnt(labChemicalAnalyLogEntity);
            if (recordNo == 1) {
                laboryService.delLabChemicalAnaly(labChemicalAnalyEntity);
                result.setSuccessful(true);
                result.setMsg("删除元素设置信息成功！");
            } else {
                result.setSuccessful(true);
                result.setMsg("已经有多条记录，删除元素失败！");
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("删除元素设置信息失败！");
            e.printStackTrace();
        }
        return result;
    }

    //删除
    @RequestMapping(value="/delChemicalAnalyNew/{chemicalAnalyId}")
    @ResponseBody
    public Result delChemicalAnalyNew(@PathVariable Long chemicalAnalyId){
        Result result = new Result();
        int recordNo = 0;
        LabChemicalAnalyEntity labChemicalAnalyEntity = new LabChemicalAnalyEntity();
        LabChemicalAnalyLogEntity labChemicalAnalyLogEntity = new LabChemicalAnalyLogEntity();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            labChemicalAnalyEntity.setOpCode(opCode);
            labChemicalAnalyEntity.setChemicalAnalyId(chemicalAnalyId);



                laboryService.delLabChemicalAnalyNew(labChemicalAnalyEntity);
                result.setSuccessful(true);
                result.setMsg("删除元素设置信息成功！");

        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("删除元素设置信息失败！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/chemicalAnalyEdit/{mineChemicalId}")
    @ResponseBody
    public ModelAndView editVendorInfo(@PathVariable Long mineChemicalId) {
        ModelAndView mav = new ModelAndView("/labory/chemicalEdit");
        LabChemicalAnalyEntity labChemicalAnalyEntity = new LabChemicalAnalyEntity();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(labChemicalAnalyEntity);
            mav.addObject("message", "success");
            mav.addObject("labChemicalAnaly",json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/addChemicalAnaly")
    @ResponseBody
    public Result saveUser(@ModelAttribute LabChemicalAnalyEntity labChemicalAnalyEntity) {
        Result result = new Result();
        try {
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            labChemicalAnalyEntity.setOpCode(opCode);
            if(labChemicalAnalyEntity.getMineNo()!=null) {
                laboryService.addLabChemicalAnaly(labChemicalAnalyEntity);
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

    @RequestMapping(value="/scalesEdit/{scalesResId}")
    @ResponseBody
    public ModelAndView scalesEdit(@PathVariable Long scalesResId) {
        ModelAndView mav = new ModelAndView("/labory/scalesEdit");
        ScalesEntity scalesEntity = new ScalesEntity();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(scalesEntity);
            mav.addObject("message", "success");
            mav.addObject("scalesEntity",json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value="/addScales")
    @ResponseBody
    public Result addScales(@ModelAttribute ScalesEntity scalesEntity) {
        Result result = new Result();
        int toBeContinue = 1;
        try {
            if (scalesEntity.getLaborCode() != null && !"".equals(scalesEntity.getLaborCode())) {
                ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
                scalesEntity.setStatus("0");
                GridModel gridModel = laboryService.qryScalesResOrgData(scalesEntity);
                List<ScalesEntity> list = gridModel.getRows();
                //1，全水分，2，水分，3，灰分，4，挥发分
                //遍历该化验批次下所有记录
                //如果记录为0，选择1，2，可以直接录入，3，4，需要依赖2的记录，所以不能继续操作
                if (list.size() == 0 && (scalesEntity.getScalesType().equals("3") || scalesEntity.getScalesType().equals("4"))) {
                    result.setSuccessful(true);
                    result.setMsg("请先录入水分，再录入灰分或者挥发分");
                    toBeContinue = 0;
                    //如果记录为1，如果为1，2，是否已经录入，如果已经录入，弹出；如果为3，4，该记录需是2。3，4才能继续录入
                } else if (list.size() == 1) {
                    if (scalesEntity.getScalesType().equals("3") || scalesEntity.getScalesType().equals("4")) {
                        if (list.get(0).getMad() == null || "".equals(list.get(0).getMad())) {
                            result.setSuccessful(true);
                            result.setMsg("请先录入水分，再录入灰分或者挥发分");
                            toBeContinue = 0;
                        } else {
                            scalesEntity.setRelScalesResId(list.get(0).getId());
                        }
                    } else if (scalesEntity.getScalesType().equals("1") && list.get(0).getMt() != null) {
                        result.setSuccessful(true);
                        result.setMsg("全水分已经录入过，请勿重复录入");
                        toBeContinue = 0;
                    } else if (scalesEntity.getScalesType().equals("2") && list.get(0).getMad() != null) {
                        result.setSuccessful(true);
                        result.setMsg("水分已经录入过，请勿重复录入");
                        toBeContinue = 0;
                    }
                    //如果记录大于1，1，2，3，4，都需要判断是否已经录入过，其中一项录入过，弹出；如果为3，4，该记录需是2。3，4才能继续录入
                } else if (list.size() > 1) {
                    for (int i = 0; i < list.size(); i++) {
                        if (scalesEntity.getScalesType().equals("3") &&  list.get(i).getAad() != null) {
                            result.setSuccessful(true);
                            result.setMsg("灰分已经录入过，请勿重复录入");
                            toBeContinue = 0;
                            break;
                        } else if (scalesEntity.getScalesType().equals("4") && list.get(i).getVad() != null) {
                            result.setSuccessful(true);
                            result.setMsg("挥发分已经录入过，请勿重复录入");
                            toBeContinue = 0;
                            break;
                        } else if (scalesEntity.getScalesType().equals("1") &&  list.get(i).getMt() != null) {
                            result.setSuccessful(true);
                            result.setMsg("全水分已经录入过，请勿重复录入");
                            toBeContinue = 0;
                            break;
                        } else if (scalesEntity.getScalesType().equals("2") && list.get(i).getMad() != null) {
                            result.setSuccessful(true);
                            result.setMsg("水分已经录入过，请勿重复录入");
                            toBeContinue = 0;
                            break;
                        }
                    }
                    for (int i = 0; i < list.size() && toBeContinue == 1; i++) {
                        if (scalesEntity.getScalesType().equals("3") || scalesEntity.getScalesType().equals("4")) {
                            if (list.get(i).getMad() != null && !"".equals(list.get(i).getMad())) {
                                scalesEntity.setRelScalesResId(list.get(i).getId());
                                break;
                            }
                        }
                    }
                }
                if (toBeContinue == 1) {
                    scalesEntity.setBatchType(String.valueOf(list.size() + 1));
                    String opCode = shiroUser.getUser().getId().toString();
                    scalesEntity.setOpCode(opCode);
                    laboryService.addLabScalesResultOrg(scalesEntity);
                    result.setSuccessful(true);
                    result.setMsg("保存成功");
                }
            }
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value="/addScalesNew")
    @ResponseBody
    public Result addScalesNew(@ModelAttribute ScalesEntity scalesEntity) {
        Result result = new Result();
        try {
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json =objectMapper.writeValueAsString(scalesEntity);
            scalesEntity.setInsertString(json);//insertString

            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            scalesEntity.setOpCode(opCode);

            laboryService.addLabScalesResultOrgNew(scalesEntity);

            //设置返回结果
            if (scalesEntity.getResCode() != null && scalesEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:"+scalesEntity.getResMsg());
                result.setSuccessful(false);
            }
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("保存失败");
            e.printStackTrace();
        }
        return result;
    }

    //天平信息批量确认。
    @RequestMapping(value="/lotAddSubmit/{dataType}")
    @ResponseBody
    @Transactional
    public Result lotAddSubmit(HttpServletRequest req ,@PathVariable String dataType){
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
            ScalesEntity scalesEntity =  new ScalesEntity ();

            scalesEntity.setUpdateString(updateStr);
            scalesEntity.setPublicString(publicStr);

            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            scalesEntity.setConfirmCd(opCode);
            scalesEntity.setDataType(dataType);

            laboryService.scalesLotAddSubmit(scalesEntity);

            //设置返回结果
            if (scalesEntity.getResCode() != null && scalesEntity.getResCode().equals("0")) {
                result.setMsg("批量确认成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("批量确认失败:"+scalesEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("批量确认异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //化验室按化验指标分类确认，新版 20160223
    @RequestMapping(value="/labOrgDataConfirm/{dataType}")
    @ResponseBody
    @Transactional
    public Result labOrgDataConfirm(HttpServletRequest req ,@PathVariable String dataType){
        Result result = new Result();

        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String publicStr = req.getParameter("publicInfo");
        String scalesDataString = req.getParameter("scalesDataString");
        String deviceDataString = req.getParameter("deviceDataString");

        try {
            //获取界面输入参数
            ScalesEntity scalesEntity =  new ScalesEntity ();

            scalesEntity.setDeviceDataString(deviceDataString);
            scalesEntity.setScalesDataString(scalesDataString);
            scalesEntity.setPublicString(publicStr);

            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            scalesEntity.setConfirmCd(opCode);
            scalesEntity.setDataType(dataType);

            laboryService.labOrgDataConfirm(scalesEntity);

            //设置返回结果
            if (scalesEntity.getResCode() != null && scalesEntity.getResCode().equals("0")) {
                result.setMsg("化验数据确认成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("化验数据确认失败:"+scalesEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("化验数据确认异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //化验室按化验指标分类确认，新版 20160223
    @RequestMapping(value="/labMTFinalConfirm")
    @ResponseBody
    @Transactional
    public Result labMTFinalConfirm(HttpServletRequest req){
        Result result = new Result();

        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String scalesDataString = req.getParameter("scalesDataString");

        try {
            //获取界面输入参数
            ScalesEntity scalesEntity =  new ScalesEntity ();
            scalesEntity.setScalesDataString(scalesDataString);
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            scalesEntity.setConfirmCd(opCode);

            laboryService.labMTFinalConfirm(scalesEntity);

            //设置返回结果
            if (scalesEntity.getResCode() != null && scalesEntity.getResCode().equals("0")) {
                result.setMsg("化验数据确认成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("化验数据确认失败:"+scalesEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("化验数据确认异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询化验室取数配置
     */
    @RequestMapping(value="/qryLabDataUploadCfgByUsage/{usage}")
    @ResponseBody
    public GridModel qryLabDataUploadCfgByUsage(@PathVariable String usage){
        LabDataUploadEntity labDataUploadEntity = new LabDataUploadEntity();
        labDataUploadEntity.setUsage(usage);
        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryLabDataUploadCfg(labDataUploadEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    /**
     * 查询化验室取数配置
     */
    @RequestMapping(value="/qryLabDataUploadCfg")
    @ResponseBody
    public GridModel qryLabDataUploadCfg(){
        LabDataUploadEntity labDataUploadEntity = new LabDataUploadEntity();
        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryLabDataUploadCfg(labDataUploadEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }


    //上传化验室数据
    @RequestMapping(value="/uploadLabData")
    @ResponseBody
    @Transactional
    public Result uploadLabData(){
        Result result = new Result();
        try {
            LabDataUploadEntity labDataUploadEntity = SearchForm(LabDataUploadEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            labDataUploadEntity.setOpCode(opCode);

            //上传数据
            laboryService.uploadLabData(labDataUploadEntity);

            //设置返回结果
            if (labDataUploadEntity.getResCode() != null && labDataUploadEntity.getResCode().equals("0")) {
                result.setMsg("数据上传完成!");
                result.setSuccessful(true);
            } else {
                result.setMsg("数据上传未完成:"+labDataUploadEntity.getResMsg());
                result.setSuccessful(false);
            }

        } catch (ConnectException e) {
            result.setMsg("网络连接异常，请检查化验室电脑及上传服务是否已经开启！");
            result.setSuccessful(false);
            e.printStackTrace();
        } catch (Exception e) {
            result.setMsg("发生异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 计算天平称量数据的挥发分
     */
    @RequestMapping(value="/calcVad")
    @ResponseBody
    @Transactional
    public Result calcVad(){
        Result result = new Result();

        try {
            SEntity sEntity = SearchForm(SEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            sEntity.setOpCode(opCode);

            //编辑数据
            laboryService.calcVad(sEntity);

            //设置返回结果
            if (sEntity.getResCode() != null && sEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:"+sEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 撤销已经确认的数据
     */
    @RequestMapping(value="/labDataUndo")
    @ResponseBody
    @Transactional
    public Result labDataUndo(){
        Result result = new Result();

        try {
            SEntity sEntity = SearchForm(SEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            sEntity.setOpCode(opCode);

            //编辑数据
            laboryService.labDataUndo(sEntity);

            //设置返回结果
            if (sEntity.getResCode() != null && sEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:"+sEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //编辑船信息
    @RequestMapping(value="/addLabOrgData")
    @ResponseBody
    public Result addLabOrgData(@ModelAttribute SEntity sEntity){
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            sEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json =objectMapper.writeValueAsString(sEntity);
            sEntity.setJsonStr(json);
            laboryService.addLabOrgData(sEntity);

            //设置返回结果
            if (sEntity.getResCode() != null && sEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("操作成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("操作失败！" + sEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("操作发生异常！");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/qryResultData/{laborCode}/{apprStatusCode}/{batchType}")
    @ResponseBody
    public ModelAndView qryResultData(@PathVariable String laborCode, @PathVariable String apprStatusCode, @PathVariable String batchType) {
        ModelAndView mav = new ModelAndView("/labory/resultData");
        String resString = laboryService.qryResultData(laborCode,batchType);


        mav.addObject("LabReportData",resString);
        mav.addObject("apprStatusCode",apprStatusCode);

        return mav;
    }

    //提交审批化验台账
    @RequestMapping(value="/addHwData")
    @ResponseBody
    @Transactional
    public Result addHwData(){
        Result result = new Result();
        try {
            HEntity hEntity = SearchForm(HEntity.class);

            if(hEntity.getLaborCode()==null||hEntity.getLaborCode().trim().equals("")){
                result.setMsg("操作失败:化验编码为空");
                result.setSuccessful(false);
            }else{
                String mineNo = laboryService.getMineByLaborCode(hEntity.getLaborCode());
                if(mineNo==null||mineNo.trim().equals("")){
                    result.setMsg("操作失败:此化验编码未配置煤矿！");
                    result.setSuccessful(false);
                }else{
                    //编辑数据
                    //设置返回结果
                    LabChemicalAnalyEntity labChemicalAnalyEntity = new LabChemicalAnalyEntity();
                    labChemicalAnalyEntity.setMineNo(mineNo);
                    labChemicalAnalyEntity.setChemicalType("1");
                    LabChemicalAnalyEntity labChemicalAnalyEntitys = laboryService.qryLabChemicalAnaly(labChemicalAnalyEntity);
                    if(labChemicalAnalyEntitys!=null){
                        labChemicalAnalyEntity.setChemicalAnalyId(labChemicalAnalyEntitys.getChemicalAnalyId());
                        labChemicalAnalyEntity.setDoWhat("1");
                        result.setMsg("操作失败:此化验编码所属煤矿已经存在氢值，请失效当前记录。");
                        result.setSuccessful(false);
                        return result;
                    }else{
                        labChemicalAnalyEntity.setDoWhat("0");
                    }
                    SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd");
                    labChemicalAnalyEntity.setStartTime(myFmt.format(new Date()));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE));

                    labChemicalAnalyEntity.setEndTime(myFmt.format(calendar.getTime()));
                    labChemicalAnalyEntity.setChemicalValue(hEntity.getHad());
                    ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
                    String opCode = shiroUser.getUser().getId().toString();
                    labChemicalAnalyEntity.setOpCode(opCode);
                    labChemicalAnalyEntity.setChemicalType("1");
                    labChemicalAnalyEntity.setMineNo(mineNo);

                    if(labChemicalAnalyEntity.getMineNo()!=null) {
                        laboryService.addLabChemicalAnaly(labChemicalAnalyEntity);

                        laboryService.addHValueSummit(hEntity.getLaborCode());
                    }


                    result.setMsg("操作成功：此矿的氢值已经生成！");

                }
            }
        } catch (Exception e) {
            result.setMsg("操作异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }



    //提交人工输入的化验指标
    @RequestMapping(value="/submitInput")
    @ResponseBody
    @Transactional
    public Result submitInput(){
        Result result = new Result();

        try {
            SEntity sEntity = SearchForm(SEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            sEntity.setOpCode(opCode);

            //编辑数据
            laboryService.submitInput(sEntity);

            //设置返回结果
            if (sEntity.getResCode() != null && sEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:"+sEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //修改化验报告数据的方法
    @RequestMapping(value="/modifyLabReport")
    @ResponseBody
    @Transactional
    public Result modifyLabReport(HttpServletRequest req){
        Result result = new Result();
        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String updateStr = req.getParameter("updated");

        try {
            //获取界面输入参数
            ReportEntity reportEntity = new ReportEntity();

            reportEntity.setUpdateString(updateStr);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            reportEntity.setOpCode(opCode);

            //新增数据
            laboryService.modifyLabReport(reportEntity);

            //设置返回结果
            if (reportEntity.getResCode() != null && reportEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:"+reportEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //一次性批量提交作废化验数据
    @RequestMapping(value="/labOrgDataInvalid")
    @ResponseBody
    @Transactional
    public Result labOrgDataInvalid(){
        Result result = new Result();

        try {
            SEntity sEntity = SearchForm(SEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            sEntity.setOpCode(opCode);
            
            //编辑数据
            laboryService.labOrgDataInvalid(sEntity);

            //设置返回结果
            if (sEntity.getResCode() != null && sEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:"+sEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //蚌埠化验室月度报表
    @RequestMapping(value="/gotoLaborMonthRpt",method = RequestMethod.GET)
    public String gotoLaborMonthRpt(){
        return "/labory/LaborMonthRpt";
    }



    //提交化验氢值数据到氢元素配置表
    @RequestMapping(value="/submitHad")
    @ResponseBody
    @Transactional
    public Result submitHad(){
        Result result = new Result();
        try {
            SEntity sEntity = SearchForm(SEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            sEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json =objectMapper.writeValueAsString(sEntity);

            sEntity.setJsonStr(json);

            //编辑数据
            laboryService.submitHad(sEntity);

            //设置返回结果
            if (sEntity.getResCode() != null && sEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:"+sEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //前台配置氢值数据到氢元素配置表
    @RequestMapping(value="/manualSubmitHad")
    @ResponseBody
    @Transactional
    public Result manualSubmitHad(){
        Result result = new Result();
        try {
            LabChemicalAnalyEntity labChemicalAnalyEntity = SearchForm(LabChemicalAnalyEntity.class);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            labChemicalAnalyEntity.setOpCode(opCode);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json =objectMapper.writeValueAsString(labChemicalAnalyEntity);

            labChemicalAnalyEntity.setJsonStr(json);
            //编辑数据
            laboryService.manualSubmitHad(labChemicalAnalyEntity);

            //设置返回结果
            if (labChemicalAnalyEntity.getResCode() != null && labChemicalAnalyEntity.getResCode().equals("0")) {
                result.setMsg("操作成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("操作失败:"+labChemicalAnalyEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("操作异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //查询化验化验元素设置
    @RequestMapping(value="/qryChemicalAnalyListNew")
    @ResponseBody
    public GridModel qryChemicalAnalyListNew(){
        LabChemicalAnalyEntity labChemicalAnalyEntity = SearchForm(LabChemicalAnalyEntity.class);

        //设置分页信息并计算记录开始和结束值
        labChemicalAnalyEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryLabChemicalAnalyListNew(labChemicalAnalyEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询化验原始数据表单
    @RequestMapping(value="/qryLabOrgDataReportView")
    @ResponseBody
    public Result qryLabOrgDataReportView(){

        Result result = new Result();
        LabChemicalAnalyEntity labChemicalAnalyEntity = SearchForm(LabChemicalAnalyEntity.class);
        String resString = null;

        try {
            resString = laboryService.qryLabOrgDataReportView(labChemicalAnalyEntity);
            if(resString != null){
                result.setSuccessful(true);
                result.setData(resString);
            }else{
                result.setSuccessful(false);
                result.setMsg("获取原始数据失败！");
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("获取原始数据失败！");
            e.printStackTrace();
        }

        return result;
    }

    //衡丰查询mav指标
    @RequestMapping(value="/qryMAVResDataNew")
    @ResponseBody
    public GridModel qryMAVResDataNew(){
        ScalesEntity scalesEntity = SearchForm(ScalesEntity.class);

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        scalesEntity.setOpCode(opCode);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryMAVResDataNew(scalesEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }



    //查询化验原始记录
    @RequestMapping(value="/qryOriginalData/{laborCode}/{apprStatusCode}/{batchType}")
    @ResponseBody
    public String qryOriginalData(@PathVariable String laborCode, @PathVariable String apprStatusCode, @PathVariable String batchType) {
            String resString = laboryService.qryResultData(laborCode,batchType);
            return resString;
    }


    //查询化验抽查样报告
    @RequestMapping(value="/qryCheckSampleData/{laborCodeArray}")
    @ResponseBody
    public String qryCheckSampleData(@PathVariable String laborCodeArray) {
        String resString = null;
        try {
            resString = laboryService.qryCheckSampleData(laborCodeArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resString;
    }

    //编辑煤期间分析核查记录
    @RequestMapping(value="/gotoEditLabDayCheck", method= RequestMethod.GET)
    public String gotoEditLabDayCheck() {
        return "/labory/editLabDayCheck";
    }

    //编辑热容量标定记录
    @RequestMapping(value="/gotoEditLabQbCheck", method= RequestMethod.GET)
    public String gotoEditLabQbCheck() {
        return "/labory/editLabQbCheck";
    }

    //热容量标定记录
    @RequestMapping(value="/labQbCheckDataList", method= RequestMethod.GET)
    public String gotoQryLabQbCheck() {
        return "/labory/labQbCheckDataList";
    }

    //查询煤分析期间核查记录  日期区间查询
    @RequestMapping(value="/qryLabDayCheckData/{beginDate}/{endDate}")
    @ResponseBody
    public GridModel qryLabDayCheckData(@PathVariable String beginDate ,@PathVariable String endDate){

        LabDayCheckEntity labDayCheckEntity = new LabDayCheckEntity();
        labDayCheckEntity.setBeginDate(beginDate);
        labDayCheckEntity.setEndDate(endDate);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryLabDayCheckData(labDayCheckEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询煤分析期间核查记录 当日查询
    @RequestMapping(value="/qryLabDayCheckData/{checkDate}")
    @ResponseBody
    public GridModel qryLabDayCheckData(@PathVariable String checkDate ){

        LabDayCheckEntity labDayCheckEntity = new LabDayCheckEntity();
        labDayCheckEntity.setCheckDate(checkDate);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryLabDayCheckData(labDayCheckEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    //编辑煤期间分析核查记录，add by zwj 0913
    @RequestMapping(value="/editLabDayCheck")
    @ResponseBody
    @Transactional
    public Result editLabDayCheck(HttpServletRequest req ){
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
            ScalesEntity scalesEntity =  new ScalesEntity ();

            scalesEntity.setUpdateString(updateStr);
            scalesEntity.setPublicString(publicStr);

            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            scalesEntity.setConfirmCd(opCode);

            laboryService.editLabDayCheck(scalesEntity);

            //设置返回结果
            if (scalesEntity.getResCode() != null && scalesEntity.getResCode().equals("0")) {
                result.setMsg("编辑数据成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("编辑数据失败:"+scalesEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("编辑数据异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }




    //编辑热容量标定记录，add by zwj 0913
    @RequestMapping(value="/editLabQbCheck")
    @ResponseBody
    @Transactional
    public Result editLabQbCheck(HttpServletRequest req ){
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
            ScalesEntity scalesEntity =  new ScalesEntity ();

            scalesEntity.setUpdateString(updateStr);
            scalesEntity.setPublicString(publicStr);

            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            scalesEntity.setConfirmCd(opCode);

            laboryService.editLabQbCheck(scalesEntity);

            //设置返回结果
            if (scalesEntity.getResCode() != null && scalesEntity.getResCode().equals("0")) {
                result.setMsg("编辑数据成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("编辑数据失败:"+scalesEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("编辑数据异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //查询热容量标定记录
    @RequestMapping(value="/qryLabQbCheckDataList")
    @ResponseBody
    public GridModel qryLabQbCheckDataList(){

        LabQbCheckEntity labQbCheckEntity = SearchForm(LabQbCheckEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryLabQbCheckData(labQbCheckEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //查询热容量标定记录 ID查询
    @RequestMapping(value="/qryLabQbCheckData/{labCheckId}")
    @ResponseBody
    public GridModel qryLabQbCheckData(@PathVariable String labCheckId ){

        LabQbCheckEntity labQbCheckEntity = new LabQbCheckEntity();
        labQbCheckEntity.setLabCheckId(labCheckId);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryLabQbCheckData(labQbCheckEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    @RequestMapping(value="/RLMonth", method= RequestMethod.GET)
    public String gotoRLMonth() {
        return "/labory/RLmonth";
    }
    //入炉煤综合样查询
    @RequestMapping(value="/qryRLMonth")
    @ResponseBody
    public List<Map<String,Object>> qryRLMonth(){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("qryBeginDt",request.getParameter("qryBeginDt"));
        paramMap.put("batchType",request.getParameter("batchType"));
        List<Map<String,Object>> gridModel = null;
        try {
            gridModel = laboryService.qryRLMonth(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }
    @RequestMapping(value="/rlDay", method= RequestMethod.GET)
    public String gotoRlDay() {
        return "/labory/rlDay";
    }
    //入炉煤综合样查询日报LD
    @RequestMapping(value="/qryRLDay")
    @ResponseBody
    public List<Map<String,Object>> qryRLDay(){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("qryBeginDt",request.getParameter("qryBeginDt"));
        List<Map<String,Object>> gridModel = null;
        try {
            gridModel = laboryService.qryRLDay(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    //LD入炉煤综合样查询日报加权平均1
    @RequestMapping(value="/qryRLDay2")
    @ResponseBody
    public List<Map<String,Object>> qryRLDay2(){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("qryBeginDt",request.getParameter("qryBeginDt"));
        List<Map<String,Object>> gridModel = null;
        try {
            gridModel = laboryService.qryRLDay2(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }
    //LD入炉煤综合样查询日报加权平均2
    @RequestMapping(value="/qryRLDay3")
    @ResponseBody
    public List<Map<String,Object>> qryRLDay3(){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("qryBeginDt",request.getParameter("qryBeginDt"));
        List<Map<String,Object>> gridModel = null;
        try {
            gridModel = laboryService.qryRLDay3(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }

    @RequestMapping(value="/rlDay2", method= RequestMethod.GET)
    public String gotoRlDay2() {
        return "/labory/rlDay2";
    }


    //LD rc
    @RequestMapping(value="/rcMonth", method= RequestMethod.GET)
    public String gotoRcMonth() {
        return "/labory/rcMonth";
    }
    @RequestMapping(value="/rcMonth2", method= RequestMethod.GET)
    public String gotoRcMonth2() {
        return "/labory/rcMonth2";
    }
    @RequestMapping(value="/PanMei", method= RequestMethod.GET)
    public String gotoPanMei() {
        return "/labory/PanMei";
    }

    //rc煤综合样查询   已经作废不用了
    @RequestMapping(value="/qryRcMonth")
    @ResponseBody
    public List<Map<String,Object>> qryRcMonth(){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("laborCode",request.getParameter("laborCode"));
        paramMap.put("batchType",request.getParameter("batchType"));
        List<Map<String,Object>> gridModel = null;
        try {
            gridModel = null; //laboryService.qryRcMonth(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //LD mc
    @RequestMapping(value="/mcMonth", method= RequestMethod.GET)
    public String gotoMcMonth() {
        return "/labory/mcMonth";
    }

    //单个编码报告 ，入厂、入厂总样、盘煤样、煤场煤样  LD
    @RequestMapping(value="/qrySingleReport")
    @ResponseBody
    public List<Map<String,Object>> qrySingleReport(){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("laborCode",request.getParameter("laborCode"));
        paramMap.put("batchType",request.getParameter("batchType"));
        List<Map<String,Object>> gridModel = null;
        try {
            gridModel = laboryService.qrySingleReport(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //化验员随机分配化验仪器 xxs 20180207
    @RequestMapping(value="/labManRandom", method= RequestMethod.GET)
    public String gotoLabManRandom() {
        return "/labory/labManRandom";
    }

    //查询化验员随机分配化验仪器的记录 xxs 20180207
    @RequestMapping(value="/qryLabManRandomList")
    @ResponseBody
    public GridModel qryLabManRandomList(){
        LabManRandomEntity labManRandomEntity = SearchForm(LabManRandomEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryLabManRandomList(labManRandomEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //化验员随机分配化验仪器 编辑页面跳转 xxs 20180207
    @RequestMapping(value="/gotoEditLabManRandomInfo", method= RequestMethod.GET)
    public String gotoEditLabManRandomInfo() {
        return "/labory/editLabManRandom";
    }

    //化验员随机分配化验仪器 编辑 xxs 20180207
    @RequestMapping(value="/editLabManRandomInfo/add")
    @ResponseBody
    public Result addLabManRandomInfo(@ModelAttribute LabManRandomEntity labManRandomEntity){
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            labManRandomEntity.setOpCode(opCode);
            labManRandomEntity.setDoActionTag("ADD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json =objectMapper.writeValueAsString(labManRandomEntity);
            labManRandomEntity.setJsonString(json);

            laboryService.addLabManRandomInfo(labManRandomEntity);

            //设置返回结果
            if (labManRandomEntity.getResCode() != null && labManRandomEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("新增随机分配记录成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("新增随机分配记录失败！" + labManRandomEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("新增随机分配记录异常！");
            e.printStackTrace();
        }
        return result;
    }

    //入炉煤月度加权表
    @RequestMapping(value="/qryCoalBurnRlMonthInfo")
    @ResponseBody
    public List<Map<String,Object>> qryCoalBurnRlMonthInfo(){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("qryBeginDt",request.getParameter("qryBeginDt"));
        List<Map<String,Object>> gridModel = null;
        try {
            gridModel = laboryService.qryCoalBurnRlMonthInfo(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }
	
	    //飞灰可燃物
    @RequestMapping(value="/labFlyAsh", method= RequestMethod.GET)
    public String gotoLabFlyAsh() {
        return "/labory/labFlyAsh";
    }
    //飞灰可燃物查询
    @RequestMapping(value="/qryFlyAsh")
    @ResponseBody
    public List<Map<String,Object>> qryFlyAsh(){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("qryBeginDt",request.getParameter("qryBeginDt"));
        paramMap.put("qryClassNo",request.getParameter("qryClassNo"));
        List<Map<String,Object>> gridModel = null;
        try {
            gridModel = laboryService.qryFlyAsh(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }
    //飞灰可燃物编辑新增
    @RequestMapping(value="/gotoEditFlyAsh", method= RequestMethod.GET)
    public String gotoEditFlyAsh() {
        return "/labory/editLabFlyAsh";
    }



    //飞灰可燃物编辑新增保存
    @RequestMapping(value="/editFlyAsh")
    @ResponseBody
    @Transactional
    public Result editFlyAsh(HttpServletRequest req ){
        Result result = new Result();

        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String publicStr = req.getParameter("publicInfo");
        //String updateStr = req.getParameter("updateInfo");
        try {
            //获取界面输入参数
            ScalesEntity scalesEntity =  new ScalesEntity ();

            //scalesEntity.setUpdateString(updateStr);
            scalesEntity.setPublicString(publicStr);

            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            scalesEntity.setOpCode(opCode);

            laboryService.editFlyAsh(scalesEntity);

            //设置返回结果
            if (scalesEntity.getResCode() != null && scalesEntity.getResCode().equals("0")) {
                result.setMsg("编辑数据成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("编辑数据失败:"+scalesEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("编辑数据异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //保存接样信息
    @RequestMapping(value="/setReceiveInfo/{laborCode}")
    @ResponseBody
    @Transactional
    public Result setReceiveInfo(HttpServletRequest req, @PathVariable String laborCode){
        Result result = new Result();

        try {
            //获取界面输入参数
            BatchNoInfoEntity batchNoInfoEntity = new BatchNoInfoEntity();
            batchNoInfoEntity.setLaborCode(laborCode);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            batchNoInfoEntity.setOpCode(opCode);

            //编辑数据
            laboryService.setReceiveInfo(batchNoInfoEntity);

            //设置返回结果
            if (batchNoInfoEntity.getResCode() != null && batchNoInfoEntity.getResCode().equals("0")) {
                result.setMsg("化验数据更新成功!");
                result.setSuccessful(true);
            } else {
                result.setMsg("化验数据更新失败:"+batchNoInfoEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("化验数据更新异常:"+e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

    //化验设备国标信息   九江需要的  xxs 20190410
    @RequestMapping(value="/labStandard", method= RequestMethod.GET)
    public String gotoLabStandard() {
        return "/labory/labStandard";
    }

    //化验设备国标信息   九江需要的  xxs 20190410
    @RequestMapping(value="/qryLabStandardList")
    @ResponseBody
    public GridModel qryLabStandardList(){
        LabStandardEntity labStandardEntity = SearchForm(LabStandardEntity.class);

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryLabStandardList(labStandardEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //化验设备国标信息   九江需要的  xxs 20190410
    @RequestMapping(value="/gotoEditLabStandInfo", method= RequestMethod.GET)
    public String gotoEditLabStandInfo() {
        return "/labory/editLabStandard";
    }

    //化验设备国标信息   九江需要的  xxs 20190410
    @RequestMapping(value="/editLabStandardInfo/add")
    @ResponseBody
    public Result addLabStandardInfo(@ModelAttribute LabStandardEntity labStandardEntity){
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            labStandardEntity.setOpCode(opCode);
            labStandardEntity.setDoActionTag("ADD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json =objectMapper.writeValueAsString(labStandardEntity);
            labStandardEntity.setJsonString(json);

            laboryService.addLabStandardInfo(labStandardEntity);

            //设置返回结果
            if (labStandardEntity.getResCode() != null && labStandardEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("新增化验国标信息成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("新增化验国标信息失败！" + labStandardEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("新增化验国标信息异常！");
            e.printStackTrace();
        }
        return result;
    }

    //化验设备国标信息   九江需要的  xxs 20190410
    @RequestMapping(value="/delLabStandardInfo/{labStandardId}")
    @ResponseBody
    @Transactional
    public Result delLabStandInfo(@PathVariable String labStandardId) {
        Result result = new Result();
        LabStandardEntity labStandardEntity = new LabStandardEntity();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            labStandardEntity.setOpCode(opCode);
            labStandardEntity.setDoActionTag("DEL");//作废标识，存储过程用以区分
            labStandardEntity.setLabStandardId(labStandardId);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json =objectMapper.writeValueAsString(labStandardEntity);
            labStandardEntity.setJsonString(json);
            //作废数据
            laboryService.delLabStandardInfo(labStandardEntity);
            //设置返回结果
            if (labStandardEntity.getResCode() != null && labStandardEntity.getResCode().equals("0")) {
                result.setMsg("国标记录作废成功！");
                result.setSuccessful(true);
            } else {
                result.setMsg("国标记录作废失败:"+labStandardEntity.getResMsg());
                result.setSuccessful(false);
            }
        } catch (Exception e) {
            result.setMsg("国标记录作废异常:" + e.getMessage());
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }


    //飞灰月报-ld
    @RequestMapping(value="/qryFlyAshMonth")
    @ResponseBody
    public List<Map<String,Object>> qryFlyAshMonth(){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("qryBeginDt",request.getParameter("qryBeginDt"));
        List<Map<String,Object>> gridModel = null;
        try {
            gridModel = laboryService.qryFlyAshMonth(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //炉渣碳含量月报-ld
    @RequestMapping(value="/qrySlagAshMonth")
    @ResponseBody
    public List<Map<String,Object>> qrySlagAshMonth(){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("qryBeginDt",request.getParameter("qryBeginDt"));
        List<Map<String,Object>> gridModel = null;
        try {
            gridModel = laboryService.qrySlagAshMonth(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }


    //化验超差数据进行取舍的原因  xxs20190419
    @RequestMapping(value="/labCcInfo", method= RequestMethod.GET)
    public String gotoLabCcInfo() {
        return "/labory/labCcInfo";
    }

    //化验超差数据进行取舍的原因  xxs20190419
    @RequestMapping(value="/qryLabCcInfoList")
    @ResponseBody
    public GridModel qryLabCcInfoList(){
        //需要分页的实体bean都用父类定义，方便设置page相关参数 xieyt
        BaseEntity labCcInfoEntity = SearchForm(LabCcInfoEntity.class);

        //设置分页信息并计算记录开始和结束值
        labCcInfoEntity.setPageRowIndex(getPageIndex(), getPageSize());

        GridModel gridModel = null;
        try {
            gridModel = laboryService.qryLabCcInfoList((LabCcInfoEntity)labCcInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //化验超差数据进行取舍的原因  xxs20190419
    @RequestMapping(value="/gotoEditLabCcInfo", method= RequestMethod.GET)
    public String gotoEditLabCcInfo() {
        return "/labory/editLabCcRecord";
    }

    //化验超差数据进行取舍的原因  xxs20190419
    @RequestMapping(value="/editLabCcInfo/edit")
    @ResponseBody
    public Result editLabCcInfo(@ModelAttribute LabCcInfoEntity labCcInfoEntity){
        Result result = new Result();
        try {
            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            labCcInfoEntity.setOpCode(opCode);
            labCcInfoEntity.setDoActionTag("ADD");//新增标识，存储过程用以区分

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json =objectMapper.writeValueAsString(labCcInfoEntity);
            labCcInfoEntity.setJsonString(json);

            laboryService.editLabCcInfo(labCcInfoEntity);

            //设置返回结果
            if (labCcInfoEntity.getResCode() != null && labCcInfoEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg("填报化验超差数据取舍依据成功！");
            } else {
                result.setSuccessful(false);
                result.setMsg("填报化验超差数据取舍依据失败！" + labCcInfoEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("填报化验超差数据取舍依据异常！");
            e.printStackTrace();
        }
        return result;
    }

    //入炉煤全年统计表
    @RequestMapping(value="/qryCoalBurnMonthly")
    @ResponseBody
    public List<Map<String,Object>> qryCoalBurnMonthly(){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("qryBeginDt",request.getParameter("qryBeginDt"));
        List<Map<String,Object>> gridModel = null;
        try {
            gridModel = laboryService.qryCoalBurnMonthly(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridModel;
    }

    //入炉煤综合样查询日报 qz
    @RequestMapping(value="/qryRLDayQz")
    @ResponseBody
    public String qryRLDayQz(){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("batchDate",request.getParameter("batchDate"));
        paramMap.put("period",request.getParameter("period"));

        String resString = laboryService.qryRLDayQz(paramMap);
        return resString;
    }



}
