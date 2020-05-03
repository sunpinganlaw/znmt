package org.gxz.znrl.controller;

import com.alibaba.fastjson.JSON;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.entity.Module;
import org.gxz.znrl.exception.ExistedException;
import org.gxz.znrl.exception.ServiceException;
import org.gxz.znrl.service.ModuleService;
import org.gxz.znrl.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by admin on 2014/7/18.
 */

@Controller
@RequestMapping("/management/security/module")
public class ModuleController extends BaseAction {

    private static final String MODULE_TREE = "management/security/module/tree_list";


    @Autowired
    private ModuleService moduleService;
    
    @Autowired
    PermissionService permissionService;

    @RequestMapping(value="/treelist", method= RequestMethod.GET)
    public String tree_list(HttpServletRequest request) {
        return MODULE_TREE;
    }

    @RequestMapping(value="/getModuleList", method=RequestMethod.GET)
    @ResponseBody
    public List<Module> getModuleList() {
        List<Module>  modules = moduleService.findAll("");
        return modules;
    }

    @RequestMapping(value="/saveLists")
    @ResponseBody
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

        //TODO 删除
        if(deleted != null){
            //把json字符串转换成对象
            List<Module> listDeleted = JSON.parseArray(deleted, Module.class);
            for(Module module : listDeleted){
                if(module.getId() != null){

                    /*Permission permission = new Permission();
                    permission.setModuleId(module.getId());
                    if(permissionService.get(permission).size()>0) {
                        result.setMsg("该模块配置了权限，不可以删除");
                        result.setSuccessful(false);
                        break;
                    }*/

                    Module module1 = new Module();
                    module1.setParentId(module.getId());
                    if(moduleService.find(module1).size()>0) {
                        result.setMsg("该模块有子模块，不可以删除");
                        result.setSuccessful(false);
                        break;
                    }

                    else {
                        try {
                            moduleService.delete(module.getId());
                            result.setMsg("操作成功");
                            result.setSuccessful(true);
                        }
                        catch (ServiceException s)
                        {
                            result.setMsg(s.getMessage());
                            result.setSuccessful(false);
                            break;
                        }
                    }
                }
            }
        }

        //添加
        if(inserted != null){
            //把json字符串转换成对象
            Module modules = new Module();
            List<Module> listInserted = JSON.parseArray(inserted, Module.class);
            for(Module module : listInserted){
                if(module.getName() != null){
                    modules.setName(module.getName());
                    modules.setSn(module.getSn());
                    modules.setUrl(module.getUrl());
                    if(moduleService.find(modules).size()>0) {
                        result.setMsg("已存在，不可添加");
                        result.setSuccessful(false);
                        break;
                    }
                    else {
                        try {
                            if(module.getName() != null&&module.getSn()!=null&&module.getUrl()!=null)
                            moduleService.save(module);
                            result.setMsg("操作成功");
                            result.setSuccessful(true);
                        }
                        catch (ExistedException e)
                        {
                            result.setMsg(e.getMessage());
                            result.setSuccessful(false);
                            break;
                        }
                    }
                }
            }
        }

        //修改
        if(updated != null){
            //把json字符串转换成对象
            Module modules = new Module();
            List<Module> listUpdated = JSON.parseArray(updated, Module.class);
            for(Module module : listUpdated){
                if(module.getName() != null&& !(module.getId()==null)){
                    modules.setName(module.getName());
                    modules.setSn(module.getSn());
                    modules.setUrl(module.getUrl());
                    if(moduleService.find(modules).size()>1) {
                        result.setMsg("已存在多个，不可添加");
                        result.setSuccessful(false);
                        break;
                    }
                    else {
                        try {
                            moduleService.update(module);
                            result.setMsg("操作成功");
                            result.setSuccessful(true);
                        }
                        catch (ExistedException e)
                        {
                            result.setMsg(e.getMessage());
                            result.setSuccessful(false);
                            break;
                        }
                    }
                }
                if(module.getName() != null&& module.getId()==null){//treegrid 添加后直接修改变为了修改行，无添加行
                    if(module.getName() != null){
                        modules.setName(module.getName());
                        modules.setSn(module.getSn());
                        modules.setUrl(module.getUrl());
                        if(moduleService.find(modules).size()>0) {
                            result.setMsg("已存在，不可添加");
                            result.setSuccessful(false);
                            break;
                        }
                        else {
                            try {
                                if(module.getName() != null&&module.getSn()!=null&&module.getUrl()!=null) {
                                    moduleService.save(module);
                                }
                                result.setMsg("操作成功");
                                result.setSuccessful(true);
                            }
                            catch (ExistedException e)
                            {
                                result.setMsg(e.getMessage());
                                result.setSuccessful(false);
                                break;
                            }
                        }
                    }
                }
            }
        }


        return result;
    }
}
