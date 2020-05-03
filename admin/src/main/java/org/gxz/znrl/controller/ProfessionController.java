package org.gxz.znrl.controller;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.entity.Profession;
import org.gxz.znrl.entity.User;
import org.gxz.znrl.service.ProfessionService;
import org.gxz.znrl.service.UserService;
import org.gxz.znrl.viewModel.GridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.common.mybatis.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by admin-rubbissh on 2014/12/5.
 */
@Controller
@RequestMapping("/management/security/profession")
public class ProfessionController extends BaseAction {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private static final String LIST = "management/security/profession/list";

    @Autowired
    private ProfessionService professionService;

    @Autowired
    private UserService userService;

    @RequiresPermissions("Role:show")
    @RequestMapping(value="/list", method= RequestMethod.GET)
    public String list(HttpServletRequest request) {
        return LIST;
    }

    @RequestMapping(value="/professionList")
    @ResponseBody
    public GridModel roleList(){
        Profession profession = form(Profession.class);
        Page page = professionService.findByPage(page(), profession);
        GridModel m = new GridModel();
        m.setRows(page.getRows());
        m.setTotal(page.getCount());
        return m;
    }

    @RequestMapping(value="/saveProfessionList")
    @ResponseBody
    @Transactional
    public Result saveRoleList(HttpServletRequest req){
        //设置请求编码
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取编辑数据 这里获取到的是json字符串
        String deleted = req.getParameter("deleted");
        String inserted = req.getParameter("inserted");
        String updated = req.getParameter("updated");

        Result result = new Result();
        Page info;

        //TODO 删除
        if(deleted != null){
            //把json字符串转换成对象
            List<Profession> listDeleted = JSON.parseArray(deleted, Profession.class);
            User user = new User();
            for(Profession profession1 : listDeleted){
                if(profession1.getId() != null){
                    user.setProfessionId(profession1.getId());
                    if(userService.find(user).size()>0) {
                        result.setMsg("该职称配置了用户，不可以删除");
                        result.setSuccessful(false);
                        break;
                    }
                    else {
                        professionService.delete(profession1.getId().longValue());
                    }
                }
                result.setMsg("删除成功");
                result.setSuccessful(true);
            }
        }

        //添加
        if(inserted != null){
            //把json字符串转换成对象
            Profession profession = new Profession();
            List<Profession> listInserted = JSON.parseArray(inserted, Profession.class);
            for(Profession profession1 : listInserted){
                if(profession1.getId() == null){
                    profession.setName(profession1.getName());
                    if(professionService.find(null,profession).size()>0) {
                        result.setMsg("已存在职称，不可添加");
                        result.setSuccessful(false);
                        break;
                    }
                    else {
                        professionService.save(profession1);
                        result.setMsg("添加成功");
                    }
                }
                result.setSuccessful(true);
            }
        }



        //修改
        if(updated != null){
            //把json字符串转换成对象
            Profession profession = new Profession();
            List<Profession> listUpdated = JSON.parseArray(updated, Profession.class);
            for(Profession profession1 : listUpdated){
                if(profession1.getId() != null){
                    profession.setName(profession1.getName());
                    if(professionService.find(null,profession).size()>1) {
                        result.setMsg("已存在职称多个，不可修改");
                        result.setSuccessful(false);
                        break;
                    }
                    else {
                        professionService.update(profession1);
                        result.setMsg("修改成功");
                    }
                }
                result.setSuccessful(true);
            }
        }
        return result;
    }

}
