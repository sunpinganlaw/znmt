package org.gxz.znrl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.util.JacksonMapper;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.entity.*;
import org.gxz.znrl.service.IExcelService;
import org.gxz.znrl.service.IToolService;
import org.gxz.znrl.shiro.ShiroUser;
import org.gxz.znrl.util.ExcelFileTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
/**
 * Created by xieyt on 14-12-8.
 */

@Controller
@RequestMapping("/business/tool")
@SuppressWarnings("unchecked")
public class ToolController extends BaseAction {

    @Autowired
    public IToolService toolService;

    @Autowired
    public IExcelService excelService;

    //获取下拉框里的数据
    @RequestMapping(value="/getComboboxOpt/{tag}")
    @ResponseBody
    public List<ComboboxOptionEntity> getComboboxOption(@PathVariable String tag) {
        List<ComboboxOptionEntity> list = null;
        try {
            list = toolService.getComboboxOption(tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    //获取下拉框里的数据，根据输入模糊匹配  change by wz 2016-08-29 :处理参数乱码
    @RequestMapping(value="/getComboboxOpt/{tag}/{input}")
    @ResponseBody
    public List<ComboboxOptionEntity> getComboboxOptionByInput(@PathVariable String tag, @PathVariable String input) {
        List<ComboboxOptionEntity> list = null;
        try {
            list = toolService.getComboboxOptionByInput(tag, encodeStr(input));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping(value="/getComboboxOptionByInputChinese")
    @ResponseBody
    public List<ComboboxOptionEntity> getComboboxOptionByInputChinese(@RequestBody JSONObject json,
                                                                           HttpServletRequest request,
                                                                           HttpServletResponse response) {
        List<ComboboxOptionEntity> list = null;
        try {
            list = toolService.getComboboxOptionByInput(json.getString("tag"),json.getString("input") );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 转码函数
     */
    public static String encodeStr(String str){
        try{
            if(str == null){
                return str;
            }
            if(str.equals("all")){
                return "";
            }
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }return null;
    }

    //获取下拉框里的数据
    @RequestMapping(value="/getComboboxOptData/{tag}")
    @ResponseBody
    public String getComboboxOptionData(@PathVariable String tag) {
        List<ComboboxOptionEntity> list = toolService.getComboboxOption(tag);
        ObjectMapper objectMapper = JacksonMapper.getInstance();
        String jsonStr = null;
        try {
            jsonStr = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonStr;
    }


    //获取新的火车车次
    @RequestMapping(value="/generateNewTrainNo")
    @ResponseBody
    public String generateNewTrainNo(){
        String newTrainNo = null;
        try {
            newTrainNo = toolService.generateNewTrainNo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newTrainNo;
    }

    //获取车号
    @RequestMapping(value="/getCarNo/{sampleCode}/{tag}")
    @ResponseBody
    public String getCarNo(@PathVariable String sampleCode,@PathVariable String tag){
        String newTrainNo = null;
        try {
            newTrainNo = toolService.getCarNo(sampleCode,tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newTrainNo;
    }

    //检查操作员是否有某个操作权限的公共方法
    @RequestMapping(value="/checkPrivilege")
    @ResponseBody
    public String checkPrivilege(){
        PrivilegeEntity privilegeEntity = SearchForm(PrivilegeEntity.class);
        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = shiroUser.getUser().getId().toString();
        privilegeEntity.setOperId(opCode);

        String res = null;
        try {
            res = toolService.checkPrivilege(privilegeEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    //获取当前时间
    @RequestMapping(value="/getCurrentTime")
    @ResponseBody
    public String getCurrentTime(){
       return String.valueOf(System.currentTimeMillis()/1000);
    }


    @RequestMapping(value="/getDictionaryByType/{type}")
    @ResponseBody
    public List<DictionaryTableEntity> getDictionaryByType(@PathVariable String type) {
        List<DictionaryTableEntity> list = null;
        try {
            list = toolService.qryDictionaryTableByType(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping(value="/qryDictionaryTablePrior/{type}/{priorId}")
    @ResponseBody
    public List<DictionaryTableEntity> qryDictionaryTablePrior(@PathVariable String type,@PathVariable String priorId) {
        List<DictionaryTableEntity> list = null;
        try {
            list = toolService.qryDictionaryTablePrior(priorId,type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping(value="/getDictionaryById/{id}")
    @ResponseBody
    public List<DictionaryTableEntity> getDictionaryById(@PathVariable String id) {
        List<DictionaryTableEntity> list = null;
        try {
            list = toolService.qryDictionaryTableById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping(value="/getDictionaryByParentId/{parentId}")
    @ResponseBody
    public List<DictionaryTableEntity> getDictionaryByParentId(@PathVariable String parentId) {
        List<DictionaryTableEntity> list = null;
        try {
            list = toolService.qryDictionaryTableByParentId(parentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //修改
    @RequestMapping(value="/editDictionaryTable/mod")
    @ResponseBody
    public Result editDictionaryTable(@ModelAttribute DictionaryTableEntity dictionaryTableEntity){
        Result result = new Result();
        try {
            toolService.modiDictionaryTable(dictionaryTableEntity);
            result.setSuccessful(true);
            result.setMsg("修改字典信息成功！");
        } catch (RuntimeException e) {
            result.setSuccessful(false);
            result.setMsg("修改字典信息失败！"+e);
            e.printStackTrace();
        }
        return result;
    }

    //增加
    @RequestMapping(value="/addDictionaryTable/add")
    @ResponseBody
    public Result addDictionaryTable(@ModelAttribute DictionaryTableEntity dictionaryTableEntity){
        Result result = new Result();
        try {
            toolService.addDictionaryTable(dictionaryTableEntity);
            result.setSuccessful(true);
            result.setMsg("新增字典信息成功！");
        } catch (RuntimeException e) {
            result.setSuccessful(false);
            result.setMsg("新增字典信息失败！"+e);
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/generateExcel" ,method = RequestMethod.POST)
    @ResponseBody
    public String generateExcel(@RequestBody JSONArray jsonArray) {
        //获取登录操作员的ID
        Map<String,Object> result = null;
        try {
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("userId",opCode);
            param.put("entityName",jsonObject.get("entityName"));
            String[] excelHeader = jsonObject.get("excelHeader").toString().split(",");
            //要导出的excel表格表头对应的查询语句中对应的字段名,要大写
            String[] columnsName = jsonObject.get("excelColumn").toString().split(",");
            //excelHeader和columnsName为对应的表格表头参数对应的key值
            param.put("excelHeader",excelHeader);
            param.put("columnsName",columnsName);
            //countSql为导出查询计数语句的Ibatis配置文件对应的Id
            param.put("countSql",jsonObject.get("countSql"));
            //listSql为导出查询对应的检索语句的Ibatis配置文件对应的Id
            param.put("listSql",jsonObject.get("listSql"));
            //前台查询参数 begin
            JSONObject jsonObjectPara = JSONObject.parseObject(jsonObject.get("inputParameter").toString());
            Set<Map.Entry<String,Object>> set = jsonObjectPara.entrySet();
            Iterator<Map.Entry<String,Object>> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry= (Map.Entry<String, Object>) it.next();
                if (entry.getValue() != null && !"".equals(entry.getValue())) {
                    param.put(entry.getKey(),entry.getValue());
                }
            }
            //前台查询参数end
            result = excelService.exportToExcel(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value="/downloadExcel" ,method = RequestMethod.POST)
    @ResponseBody
    public String getExcel(HttpServletRequest request,HttpServletResponse response ) {
        File file;
        String fileHome = ExcelFileTool.getFileHome();
        String filePath = request.getParameter("filePath");
        String fileName = request.getParameter("fileName");
        try {
            file = new File(fileHome+filePath);
            downLoad(response, file, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (null);
    }

    private void downLoad(HttpServletResponse response, File file,
                          String fileName) throws Exception {
        ServletOutputStream out = null;
        InputStream in = null;
        try {
            byte[] buf = new byte[1024];
            in = new FileInputStream(file);
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + toUtf8String(fileName));
            out = response.getOutputStream();

            int len;

            while ((len = in.read(buf, 0, 1024)) != -1) {
                out.write(buf, 0, len);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }

            if (out != null) {
                out.close();
            }
        }
    }

    private String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if ((c >= 0) && (c <= 255)) {
                sb.append(c);
            } else {
                byte[] b;

                try {
                    b = String.valueOf(c).getBytes("UTF-8");
                } catch (Exception ex) {
                    b = new byte[0];
                }

                for (int j = 0; j < b.length; j++) {
                    int k = b[j];

                    if (k < 0) {
                        k += 256;
                    }

                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }

        return sb.toString();
    }


    @RequestMapping(value="/qryPushMsgList/{msgIds}")
    @ResponseBody
    public ModelAndView qryPushMsgList(@PathVariable String msgIds){
        ModelAndView mav = new ModelAndView("/tool/msgPushList");

        try {
            MsgPushEntity msgPushEntity = new MsgPushEntity();
            msgPushEntity.setJsonString(msgIds);

            //获取登录操作员的ID
            ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
            String opCode = shiroUser.getUser().getId().toString();
            msgPushEntity.setOpCode(opCode);

            JSONArray ja = JSON.parseArray(msgIds);
            List<String> msgIdsList = new ArrayList();

            for(int i=0; i<ja.size();i++){
                msgIdsList.add((String)ja.get(i));
            }
            List<MsgPushEntity> msgList = toolService.qryPushMsgList(msgIdsList);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json =objectMapper.writeValueAsString(msgList);

            if (msgList != null && msgList.size() > 0) {
                toolService.updatePushMsg(msgPushEntity);
            }

            mav.addObject("message", "success");
            mav.addObject("msgList",json);
        } catch (Exception e) {
            mav.addObject("message", "fail");
            e.printStackTrace();
        }
        return mav;
    }

    //获取参数上下限值hxl
    @RequestMapping(value="/getLimitValue/{typeField}/{defaultValue}")
    @ResponseBody
    public String getLimitValue(@PathVariable String typeField, @PathVariable String defaultValue){
        String Value = null;
        try {
            Value = toolService.getLimitValue(typeField, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Value;
    }
}
