package org.gxz.znrl.controller;

import java.io.InputStream;
import java.io.PrintWriter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.entity.ComboboxOptionEntity;
import org.gxz.znrl.entity.PointConfigEntity;
import org.gxz.znrl.service.IPointConfigService;
import org.gxz.znrl.shiro.ShiroUser;
import org.gxz.znrl.util.ImportExcelUtil;
import org.gxz.znrl.viewModel.GridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.gxz.znrl.common.util.Result;


@Controller
@RequestMapping("/business/pointConfig")
public class PointConfigController  extends BaseAction {

    private Connection connection = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    @Autowired
    public IPointConfigService pointCfgService;
    //组态测点配置页面跳转
    @RequestMapping(value = "/{pageName}", method = {RequestMethod.GET, RequestMethod.POST})
    public String showPointConfigPage(@PathVariable String pageName) {
        return "/pointConfig/" + pageName;
    }

    //查询channelName.deviceName的配置信息
    @RequestMapping(value="/qryPointCfgInfo")
    @ResponseBody
    public GridModel qryPointCfgInfo() {
        PointConfigEntity pointConfigEntity = SearchForm(PointConfigEntity.class);
        GridModel gridModel = null;
        try {
            gridModel = pointCfgService.qryPointCfgInfo(pointConfigEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridModel;
    }
    //导入
    @RequestMapping(value = "/importCsv4zt", method = RequestMethod.POST)
    @ResponseBody
    public Result importCsv4zt(@RequestParam CommonsMultipartFile file,
                                 HttpServletRequest request) throws Exception {

        request.setCharacterEncoding("utf-8");//客户端网页我们控制为UTF-8
        String prexfix = request.getParameter("prefix");
        String actionType = request.getParameter("actionType");
        Result result = new Result();
        result.setSuccessful(true);
        result.setMsg("文件导入成功！");
        try {

            InputStream in =null;
            List<List<Object>> listob = null;
            in = file.getInputStream();
            listob = new ImportExcelUtil().getBankListByExcel(in,file.getOriginalFilename());
            in.close();
            List<JSONObject> list = new ArrayList<>();

            //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
            for (int i = 0; i < listob.size(); i++) {
                List<Object> lo = listob.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("pointTag",String.valueOf(lo.get(0)));
                list.add(jsonObject);
            }
            System.out.println(JSONArray.fromObject(list));
            if(list==null && list.isEmpty()){
                result.setSuccessful(false);
                result.setMsg("文件内容为空，请重新选择！");
                return result;
            }
         PointConfigEntity pointCfgEntity=new PointConfigEntity();
        pointCfgEntity.setPrefix(prexfix);
        pointCfgEntity.setActionType(actionType);
        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        pointCfgEntity.setOpCode(opCode);
        JSONObject json = new JSONObject();
        json.put("pointList", JSONArray.fromObject(list));
        pointCfgEntity.setJsonString(json.toString());
        pointCfgService.operaPointCfg(pointCfgEntity);
        //设置返回结果
        if (pointCfgEntity.getResCode() != null && pointCfgEntity.getResCode().equals("0")) {
            result.setSuccessful(true);
            result.setMsg(pointCfgEntity.getResMsg());
        } else {
            result.setSuccessful(false);
            result.setMsg("文件导入失败：" + pointCfgEntity.getResMsg());
        }
        }catch(Exception e){
            result.setSuccessful(false);
            result.setMsg("上传文件信息异常:"+e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    //导入
    @RequestMapping(value = "/importCsv4car", method = RequestMethod.POST)
    @ResponseBody
    public Result importCsv4car(@RequestParam CommonsMultipartFile file,
                               HttpServletRequest request) throws Exception {

        request.setCharacterEncoding("utf-8");//客户端网页我们控制为UTF-8
        String prexfix = request.getParameter("prefix");
        String actionType = request.getParameter("actionType");
        Result result = new Result();
        result.setSuccessful(true);
        result.setMsg("文件导入成功！");
        try {

            InputStream in =null;
            List<List<Object>> listob = null;
            in = file.getInputStream();
            listob = new ImportExcelUtil().getBankListByExcel(in,file.getOriginalFilename());
            in.close();
            List<JSONObject> list = new ArrayList<>();

            //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
            for (int i = 0; i < listob.size(); i++) {
                List<Object> lo = listob.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("pointTag",String.valueOf(lo.get(0)));
                jsonObject.put("pointName",String.valueOf(lo.get(1)));
                jsonObject.put("pointInfo",String.valueOf(lo.get(2)));
                list.add(jsonObject);
            }
            System.out.println(JSONArray.fromObject(list));
            if(list==null && list.isEmpty()){
                result.setSuccessful(false);
                result.setMsg("文件内容为空，请重新选择！");
                return result;
            }
            PointConfigEntity pointCfgEntity=new PointConfigEntity();
            pointCfgEntity.setPrefix(prexfix);
            pointCfgEntity.setActionType(actionType);
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            pointCfgEntity.setOpCode(opCode);
            JSONObject json = new JSONObject();
            json.put("pointList", JSONArray.fromObject(list));
            pointCfgEntity.setJsonString(json.toString());
            pointCfgService.operaPointCfg(pointCfgEntity);
            //设置返回结果
            if (pointCfgEntity.getResCode() != null && pointCfgEntity.getResCode().equals("0")) {
                result.setSuccessful(true);
                result.setMsg(pointCfgEntity.getResMsg());
            } else {
                result.setSuccessful(false);
                result.setMsg("文件导入失败：" + pointCfgEntity.getResMsg());
            }
        }catch(Exception e){
            result.setSuccessful(false);
            result.setMsg("上传文件信息异常:"+e.getMessage());
            e.printStackTrace();
        }
        return result;
    }



    //获取下拉框里的数据
    @RequestMapping(value="/getChannelList")
    @ResponseBody
    public List<ComboboxOptionEntity> getChannelList() {
        List<ComboboxOptionEntity> list = null;
        try {
            list = pointCfgService.getChannelList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //保存数据
    @RequestMapping(value="/savePointConfig", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Result savePointConfig(){
        Result result = new Result();
        result.setSuccessful(true);
        result.setMsg("测点导入成功！");
        try{
            //删除
            String prefix = request.getParameter("prefix");
            if(prefix != null && !prefix.equals("")){
                pointCfgService.delPointCfgByTag(prefix);
            }

            // 取运损率信息
            String pointList = request.getParameter("pointList");
            JSONArray pointListArray = new JSONArray();
            if (null != pointList && !pointList.isEmpty()) {
                pointListArray = JSONArray.fromObject(pointList);
            }
            List<PointConfigEntity> pointCfgEntitys = new ArrayList<PointConfigEntity>();
            for (int i = 0; i < pointListArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) pointListArray.get(i);
                String pointTag = null == jsonObject.get("pointTag") ? null : jsonObject.get("pointTag").toString();
                String pointName = null == jsonObject.get("pointName") ? null : jsonObject.get("pointName").toString();
                String toDb = null == jsonObject.get("toDb") ? null : jsonObject.get("toDb").toString();
                String remark = jsonObject.get("remark").equals("null") ? null : jsonObject.get("remark").toString();
                String status = null == jsonObject.get("status") ? null : jsonObject.get("status").toString();
                PointConfigEntity pointCfg = new PointConfigEntity();
                pointCfg.setPointName(pointName);
                pointCfg.setPointTag(pointTag);
                pointCfg.setToDb(toDb);
                pointCfg.setStatus(status);
                pointCfg.setRemark(remark);
                pointCfgEntitys.add(pointCfg);
            }
            // 保存
            pointCfgService.savePointCfgs(pointCfgEntitys);
        }catch(Exception e){
            result.setSuccessful(false);
            result.setMsg("保存测点信息异常:"+e.getMessage());
            e.printStackTrace();
        }
        return result;
    }



}




