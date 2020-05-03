package org.gxz.znrl.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.gxz.znrl.activiti.tools.TaskSelfBean;
import org.gxz.znrl.entity.Msg;
import org.gxz.znrl.service.MsgService;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.SecurityPortal;
import org.gxz.znrl.entity.SecurityPortalReport;
import org.gxz.znrl.service.PortalService;
import org.gxz.znrl.viewModel.GridModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by vincent on 2014/9/14.
 */
@Controller
@RequestMapping("/portal")
@SuppressWarnings("unchecked")
public class PortalController extends BaseAction {
    @Autowired
    PortalService portalService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private MsgService msgService;

    private static final String TEST = "management/index/test";


    @RequestMapping(value="test", method= RequestMethod.GET)
    public String test() {
        return TEST;
    }

    /**
     * 获取用户公共
     * @return
     */
    @RequestMapping(value="/information")
    @ResponseBody
    public JSONObject information(){

        JSONObject jsonobject = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();
            Msg msg = new Msg();
            Page page = page();
            page.setSort("msg_ID");
            page.setOrder("asc");
            for(Msg d : msgService.findList(page,msg))
            {
                jArray.add(d);
            }
            jsonobject.put("results",jArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
    }

    /**
     * 获取用户首页配置数据
     * @return
     */
    @RequestMapping(value="/portalConfig")
    @ResponseBody
    public JSONObject portalConfig(){
        SecurityPortal securityPortal = new SecurityPortal();
        securityPortal.setUserId(Integer.parseInt(org.gxz.znrl.shiro.SecurityUtils.getLoginUser().getId().toString()));
        JSONObject jsonobject = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();
            Page page = page();
            page.setSort("index_show");
            page.setOrder("asc");
            for(SecurityPortal d : (List<SecurityPortal>)(portalService.findByPage(page,securityPortal).getRows()))
            {
                jArray.add(d);
            }
            jsonobject.put("results",jArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
    }

    /**
     * 获取客户分类
     * @return
     */
    @RequestMapping(value="/customTypeSum")
    @ResponseBody
    public JSONObject customType(){
        JSONObject jsonobject = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();
            Page page = page();
            page.setSort("index_show");
            page.setOrder("asc");
            for(SecurityPortalReport d : portalService.customTypeSum(1))
            {
                jArray.add(d);
            }
            jsonobject.put("results",jArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
    }

    /**
     * 获取客户分月汇总
     * @return
     */
    @RequestMapping(value="/customMonthSum")
    @ResponseBody
    public JSONObject customMonth(){
        JSONObject jsonobject = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();
            for(SecurityPortalReport d : portalService.customMonthSum(1))
            {
                jArray.add(d);
            }
            jsonobject.put("results",jArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
    }

    /**
     * 机房使用占比
     * @return
     */
    @RequestMapping(value="/serverRoom/{srid}")
    @ResponseBody
    public JSONObject serverRoom(@PathVariable Integer srid){
        JSONObject jsonobject = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();
            for(SecurityPortalReport d : portalService.serverRoom(srid))
            {
                jArray.add(d);
            }
            jsonobject.put("results",jArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
    }

    /**
     * 获取投资情况
     * @return
     */
    @RequestMapping(value="/investMonth")
    @ResponseBody
    public String investMonth(){
        StringBuffer stringBuffer = new StringBuffer("月份,增加,减少\r\n");
        try {

            for(SecurityPortalReport d : portalService.investMonth())
            {
                stringBuffer.append(d.getTitle()).append(",").append(d.getColValue()!=null?d.getColValue():0).append(",").append(d.getColValueSecond()!=null?d.getColValueSecond():0).append("\r\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    /**
     * 获取投诉情况
     * @return String
     */
    @RequestMapping(value="/complaintMonth")
    @ResponseBody
    public String complaintMonth(){
        StringBuffer stringBuffer = new StringBuffer("日期,新增,处理\r\n");
        try {

            for(SecurityPortalReport d : portalService.complaintMonth())
            {
                stringBuffer.append(d.getTitle()).append(",").append(d.getColValue()!=null?d.getColValue():0).append(",").append(d.getColValueSecond()!=null?d.getColValueSecond():0).append("\r\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    /**
     * 本年度销售排名
     * @return String
     */
    @RequestMapping(value="/orderMonth")
    @ResponseBody
    public String orderMonth(){
        StringBuffer stringBuffer = new StringBuffer("人员,销售,回款\r\n");
        try {

            for(SecurityPortalReport d : portalService.orderMonth())
            {
                stringBuffer.append(d.getTitle()).append(",").append(d.getColValue()!=null?d.getColValue():0).append(",").append(d.getColValueSecond()!=null?d.getColValueSecond():0).append("\r\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
