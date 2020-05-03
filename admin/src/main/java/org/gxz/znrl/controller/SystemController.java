package org.gxz.znrl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.util.JacksonMapper;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.entity.Role;
import org.gxz.znrl.entity.RoleSelect;
import org.gxz.znrl.entity.newsystem.RoleEntity;
import org.gxz.znrl.entity.User;
import org.gxz.znrl.entity.newsystem.UserEntity;
import org.gxz.znrl.entity.newsystem.TreeNode;
import org.gxz.znrl.service.SystemService;
import org.gxz.znrl.service.UserService;
import org.gxz.znrl.shiro.SecurityUtils;
import org.gxz.znrl.viewModel.GridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by hujy on 18-4-20.
 */
@Controller
@RequestMapping("/newSysmgr/security")
public class SystemController extends BaseAction {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final String USER_LIST = "newSysmgr/security/user/list";
    private static final String USER_ADD_EDIT = "newSysmgr/security/user/userEdit";

    @Autowired
    public SystemService systemService;

    @RequiresPermissions("User:show")
    @RequestMapping(value="/user/list", method= RequestMethod.GET)
    public String list(HttpServletRequest request) {
        return USER_LIST;
    }

    @RequestMapping(value="/user/userList")
    @ResponseBody
    public GridModel userList(){
        UserEntity user = form(UserEntity.class);
        user.setPageRowIndex(getPageIndex(),getPageSize());
        return systemService.findUsersByPage(user);
    }

    @RequestMapping(value="/user/addUser")
    @ResponseBody
    public ModelAndView userEdit() {
        ModelAndView mav = new ModelAndView(USER_ADD_EDIT);
        User user = new User();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json = mapper.writeValueAsString(user);
            mav.addObject("message", "success");
            mav.addObject("user",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }


    @RequestMapping(value="/user/editUser/{id}")
    @ResponseBody
    public ModelAndView editUser(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView(USER_ADD_EDIT);
        try {
            UserEntity user = systemService.getUser(Long.parseLong(id + ""));
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(user);
            mav.addObject("message", "success");
            mav.addObject("user",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }


    @RequestMapping(value="/user/findAllRoleSelect")
    @ResponseBody
    public List<RoleSelect> findAllRoleSelect() {
        String roles = request.getParameter("roles");
        Set<String> rolesSet = new  HashSet<String>();
        for (String roleId : StringUtils.split(roles, ",")) {
            rolesSet.add(roleId);
        }
        List<RoleSelect> roleSelects = new ArrayList<>();
        List<Role> list = systemService.qryRole();
        for (Role role : list) {
            RoleSelect roleSelect = new RoleSelect();
            roleSelect.setId(role.getId());
            roleSelect.setDescription(role.getDescription());
            if(rolesSet.size()>0 && rolesSet.contains(role.getId().toString())){
                roleSelect.setSelected(true);
            }
            roleSelects.add(roleSelect);
        }
        return roleSelects;
    }

    @RequestMapping(value="/user/findAllRole/{id}")
    @ResponseBody
    public List<Role> findAllRole(@PathVariable Long id) {
        logger.debug("id = " + id);
        List<Role> list = systemService.qryRole();
        return list;
    }

    @Autowired
    private UserService userService;

    @RequestMapping(value="/user/saveUser")
    @ResponseBody
    public Result saveUser(HttpServletRequest request) {
        Result result = new Result();
        User user = new User();
        String id = request.getParameter("search_id");
        String realname = request.getParameter("search_realname");
        String username = request.getParameter("search_username");
        String plainPassword = request.getParameter("search_plainPassword");
        String status = request.getParameter("search_status");
        String phone = request.getParameter("search_phone");
        String email = request.getParameter("search_email");
        String roles = request.getParameter("search_roles");
        String orgId = request.getParameter("search_orgId");
        String professionId = request.getParameter("search_professionId");
        if(StringUtils.isNotBlank(id)){
            user.setId(Long.parseLong(id));
        }
        if(StringUtils.isNotBlank(realname)){
            user.setRealname(realname);
        }
        if(StringUtils.isNotBlank(username)){
            user.setUsername(username);
        }
        if(StringUtils.isNotBlank(plainPassword)){
            user.setPlainPassword(plainPassword);
        }
        if(StringUtils.isNotBlank(status)){
            user.setStatus(status);
        }
        if(StringUtils.isNotBlank(phone)){
            user.setPhone(phone);
        }
        if(StringUtils.isNotBlank(email)){
            user.setEmail(email);
        }
        if(StringUtils.isNotBlank(roles)){
            user.setRoles(roles);
        }
        if(StringUtils.isNotBlank(orgId)){
            user.setOrgId(Long.parseLong(orgId));
        }
        if(StringUtils.isNotBlank(professionId)){
            user.setProfessionId(Integer.parseInt(professionId));
        }
        try {
            if(StringUtils.isBlank(id)){
                if (systemService.getByUsername(user.getUsername()) > 0) {
                    result.setSuccessful(false);
                    result.setMsg("用户添加失败,登录名:" + user.getUsername() + "已存在.");
                    return result;
                }
                systemService.saveUser(user);
            }else{
                if (user.getStatus().equals("disabled")) {
                    user.setPassword("123456");
                    user.setPlainPassword("123456");
                }
                systemService.updateUser(user);
            }
            result.setSuccessful(true);
            result.setMsg("操作成功");
        }catch (Exception e){
            result.setSuccessful(false);
            result.setMsg("操作失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/user/deleteUserInfo/{id}", method=RequestMethod.POST)
    @ResponseBody
    public Result deleteUserInfo(@PathVariable Integer id) {
        User user = new User();
        user.setId(id.longValue());
        user.setStatus("disabled");
        systemService.deleteUserInfo(user);
        Result result = new Result();
        result.setMsg("操作成功");
        result.setSuccessful(true);
        return result;
    }


    private static final String ROLE_LIST = "newSysmgr/security/role/list";

    @RequiresPermissions("role:show")
    @RequestMapping(value="/role/list", method= RequestMethod.GET)
    public String gotoRoleList(HttpServletRequest request) {
        return ROLE_LIST;
    }


    @RequestMapping(value="/role/roleList")
    @ResponseBody
    public GridModel roleList(){
        RoleEntity role = form(RoleEntity.class);
        role.setPageRowIndex(getPageIndex(),getPageSize());
        return systemService.qryRoleList(role);
    }

    @RequestMapping(value="/role/saveRoleList")
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

        Result result = systemService.saveRoleList(deleted,inserted,updated);
        return result;
    }


    @RequestMapping(value="/permission/list", method= RequestMethod.GET)
    public ModelAndView  permissionList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("newSysmgr/security/permission/list");

        if(SecurityUtils.getLoginUser().getId()==1){
            mv.getModel().put("role_id", systemService.selectRoleAll().get(0).getId());
        }else{
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("userId",SecurityUtils.getLoginUser().getId());
            List<Map<String,Object>> rolesList = systemService.getRoleIdByUserId(params);
            if (CollectionUtils.isNotEmpty(rolesList)){
                mv.getModel().put("role_id", MapUtils.getString(rolesList.get(0), "ROLE_ID"));
            }
        }

        return mv;
    }

    @RequestMapping(value="/permission/rolePermList")
    @ResponseBody
    public List<TreeNode> getNodeDataTree(HttpServletRequest request){
        String roleId = (String) request.getParameter("roleId");

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("roleId",roleId);
        List<TreeNode>  ret = systemService.getNodeDataTree(params);

        return ret;
    }

    @RequestMapping(value = "/permission/selectRoleAll")
    @ResponseBody
    public List<RoleEntity> selectRoleAll(){
        return systemService.selectRoleAll();
    }

    @RequestMapping(value="/permission/saveRolePerm")
    @ResponseBody
    public Result saveRolePerm(HttpServletRequest request){
        //设置请求编码
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String,Object> params = new HashMap<String,Object>();
        //获取编辑数据 这里获取到的是json字符串
        String nodes = request.getParameter("nodes");
        String roleId = request.getParameter("role_id");
        params.put("nodes",nodes);
        params.put("roleId",roleId);
        Result result =  new Result();;
        try {
             result = systemService.saveRolePerm(params);
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsg("授权失败!");
            e.printStackTrace();
        }

        return result;
    }
}
