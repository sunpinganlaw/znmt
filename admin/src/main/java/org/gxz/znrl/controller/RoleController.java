package org.gxz.znrl.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.common.util.JacksonMapper;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.entity.*;
import org.gxz.znrl.service.*;
import org.gxz.znrl.viewModel.GridModel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2014/7/22.
 */

@Controller
@RequestMapping("/management/security/role")
public class RoleController extends BaseAction {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private static final String LIST = "management/security/role/list";
    private static final String PERMISSION = "management/security/role/rolePermissionTree";
    private static final String PERMISSION_OLD = "management/security/role/rolePermission";

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RolePermissionService rolePermissionService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModuleService moduleService;

    @RequiresPermissions("Role:show")
    @RequestMapping(value="/list", method= RequestMethod.GET)
    public String list(HttpServletRequest request) {
        return LIST;
    }

    @RequestMapping(value="/roleList")
    @ResponseBody
    public GridModel roleList(){
        Role role = form(Role.class);
        Page page = roleService.findByPage(page(), role);
        GridModel m = new GridModel();
        m.setRows(page.getRows());
        m.setTotal(page.getCount());
        return m;
    }


    @RequestMapping(value="/saveRoleList")
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
            List<Role> listDeleted = JSON.parseArray(deleted, Role.class);
            UserRole userRole = new UserRole();
            for(Role role1 : listDeleted){
                if(role1.getId() != null){
                    userRole.setRoleId(role1.getId());
                    if(userRoleService.find(userRole).size()>0) {
                        result.setMsg("该角色有用户，不可以删除");
                        result.setSuccessful(false);
                        break;
                    }
                    else {
                        roleService.delete(role1.getId() );
                    }
                }
                result.setMsg("删除成功");
                result.setSuccessful(true);
            }
        }

        //添加
        if(inserted != null){
            //把json字符串转换成对象
            Role role = new Role();
            List<Role> listInserted = JSON.parseArray(inserted, Role.class);
            for(Role role1 : listInserted){
                if(role1.getId() == null){
                    role.setName(role1.getName());
                    if(roleService.find(null,role).size()>0) {
                        result.setMsg("已存在角色，不可添加");
                        result.setSuccessful(false);
                        break;
                    }
                    else {
                        roleService.save(role1);
                    }
                }
                result.setMsg("添加成功");
                result.setSuccessful(true);
            }
        }



        //修改
        if(updated != null){
            //把json字符串转换成对象
                Role role = new Role();
                List<Role> listUpdated = JSON.parseArray(updated, Role.class);
                for(Role role1 : listUpdated){
                    if(role1.getId() != null){
                        role.setName(role1.getName());
                        if(roleService.find(null,role).size()>1) {
                            result.setMsg("已存在角色多个，不可修改");
                            result.setSuccessful(false);
                            break;
                        }
                        else {
                            roleService.update(role1);
                        }
                    }
                    result.setMsg("添加成功");
                    result.setSuccessful(true);
                }
        }
        return result;
    }


    @RequestMapping(value="/findAllRole/{id}")
    @ResponseBody
    public List<Role> findAllRole(@PathVariable Long id) {
        logger.debug("id = " + id);
        List<Role> list = roleService.findAll();
        return list;
    }

    @RequiresPermissions("Role:edit")
    @RequestMapping(value="/rolePermissionOld/{id}")
    @ResponseBody
    public ModelAndView rolePermissionOld (@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView(PERMISSION_OLD);
        List<RolePermission> rolePermissions = rolePermissionService.findByRoleId(null,id);
        List<Permission> list = new ArrayList<>();
        for(RolePermission permission: rolePermissions)
        {
            list.add(permissionService.get(permission.getPermissionId()));
        }
        modelAndView.addObject("PermissionList",list);//一个角色拥有的权限

        List<Module> listAll = moduleService.findAll("");

        modelAndView.addObject("ModuleAll",listAll);//所有权限
        modelAndView.addObject("roleid",id);//角色
        return modelAndView;
    }

    @RequiresPermissions("Role:edit")
    @RequestMapping(value="/rolePermission/{id}")
    @ResponseBody
    public ModelAndView rolePermission (@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView(PERMISSION);
        List<RolePermission> rolePermissions = rolePermissionService.findByRoleId(null,id);
        List<Permission> list = new ArrayList<>();
        for(RolePermission permission: rolePermissions)
        {
            list.add(permissionService.get(permission.getPermissionId()));
        }
        ObjectMapper mapper = JacksonMapper.getInstance();
        try {
            String json =mapper.writeValueAsString(list);
            modelAndView.addObject("PermissionList",json);//一个角色拥有的权限
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<Module> listAll = moduleService.findAll("");

        modelAndView.addObject("ModuleAll",listAll);//所有权限
        modelAndView.addObject("roleid",id);//角色
        return modelAndView;
    }

    @RequestMapping(value="/saveRolePermission/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Result saveRolePermission(@PathVariable Long id,@RequestParam String rolePermissionStr){
        //设置请求编码
        Result result = new Result();
        try {
            //获取编辑数据 这里获取到的是json字符串

            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(id);
            rolePermissionService.delete(rolePermission);//删除角色全部权限

            if(rolePermissionStr!=null){
            String[] rPermission = rolePermissionStr.split(",");
            if(rolePermissionStr.indexOf(",")>0)
            for (String p : rPermission) {
                String[] per = p.split("_");
                Permission permission = new Permission();
                permission.setShortName(per[0]);
                permission.setModuleId(Long.valueOf(per[1]));
                List<Permission> permissions = permissionService.get(permission);//查找选择的模块权限ID
                if(permissions.size()==0)
                {
                    permission.setName(per[0]);
                    permissionService.save(permission);
                    permission.setName(null);
                    List<Permission> permissions_add_then = permissionService.get(permission);//查找选择的模块权限ID
                    for (Permission p2 : permissions_add_then) {
                        RolePermission rolePermission_in = new RolePermission();
                        rolePermission_in.setPermissionId(p2.getId());
                        rolePermission_in.setRoleId(id);
                        rolePermissionService.save(rolePermission_in);//添加角色选择的模块权限
                    }
                }
                else {
                    for (Permission p2 : permissions) {
                        RolePermission rolePermission_in = new RolePermission();
                        rolePermission_in.setPermissionId(p2.getId());
                        rolePermission_in.setRoleId(id);
                        rolePermissionService.save(rolePermission_in);//添加角色选择的模块权限
                    }
                }
            }
            result.setMsg("操作成功");
            result.setSuccessful(true);
            }
        }
        catch (Exception e)
        {
            result.setMsg("操作失败");
            result.setSuccessful(false);
            e.printStackTrace();
        }
        return result;
    }

}
