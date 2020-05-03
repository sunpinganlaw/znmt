package org.gxz.znrl.controller;

import com.alibaba.fastjson.JSON;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.entity.Organization;
import org.gxz.znrl.entity.User;
import org.gxz.znrl.exception.ExistedException;
import org.gxz.znrl.exception.ServiceException;
import org.gxz.znrl.service.OrganizationRoleService;
import org.gxz.znrl.service.OrganizationService;
import org.gxz.znrl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by admin on 2014/5/12.
 */

@Controller
@RequestMapping("/management/security/organization")
public class OrganizationController extends BaseAction {

    private static final String LOCATION = "management/security/organization/location";
    private static final String LIST = "management/security/organization/list";

    @RequestMapping(value="test/location", method= RequestMethod.GET)
    public String list(HttpServletRequest request) {
        return LOCATION;
    }


    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationRoleService organizationRoleService;

    @RequestMapping(value="/findAll")
    @ResponseBody
    public List<Organization>  findAll() {
        List<Organization> list = organizationService.findAll();
        return list;
    }

    @RequestMapping(value="/findById/{id}", method= RequestMethod.POST)
    @ResponseBody
    public Organization  findById(@PathVariable Long id) {
        System.out.println("id = " + id);
        Organization organization1 = organizationService.get(id);
        return organization1;
    }


    @RequestMapping(value="/treelist", method= RequestMethod.GET)
    public String treelist(HttpServletRequest request) {
        return LIST;
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
            List<Organization> listDeleted = JSON.parseArray(deleted, Organization.class);
            for(Organization organization : listDeleted){
                if(organization.getId() != null){

                    User user = new User();
                    user.setOrgId(organization.getId());
                    if(userService.find(user).size()>0) {
                        result.setMsg("该组织部门配置了用户，不可以删除");
                        result.setSuccessful(false);
                        break;
                    }

                    // TODO: 配置了角色
                    if(organizationRoleService.find(organization.getId()).size()>0)
                    {
                        result.setMsg("该组织部门配置了角色，不可以删除");
                        result.setSuccessful(false);
                        break;
                    }

                    if(organizationService.findByParentId(organization.getId()).size()>0) {
                        result.setMsg("该组织部门有子部门，不可以删除");
                        result.setSuccessful(false);
                        break;
                    }

                    else {
                        try {
                            organizationService.delete(organization.getId());
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
            List<Organization> listInserted = JSON.parseArray(inserted, Organization.class);
            for(Organization organization : listInserted){
                if(organization.getName() != null){
                    if(organizationService.getByName(organization.getName())!=null) {
                        result.setMsg("已存在，不可添加");
                        result.setSuccessful(false);
                        break;
                    }
                    else {
                        try {
                            if(organization.getName() != null)
                                organizationService.save(organization);
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
            Organization modules = new Organization();
            List<Organization> listUpdated = JSON.parseArray(updated, Organization.class);
            for(Organization organization : listUpdated){
                if(organization.getName() != null&& !(organization.getId()==null)){

                    if(organizationService.find(null,organization.getName(),null).size()>1) {
                        result.setMsg("已存在多个，不可添加");
                        result.setSuccessful(false);
                        break;
                    }
                    else {
                        try {
                            organizationService.update(organization);
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
                if(organization.getName() != null&& organization.getId()==null){//treegrid 添加后直接修改变为了修改行，无添加行
                    if(organization.getName() != null){
                        if(organizationService.getByName(organization.getName())!=null) {
                            result.setMsg("已存在，不可添加");
                            result.setSuccessful(false);
                            break;
                        }
                        else {
                            try {
                                if(organization.getName() != null)
                                    organizationService.save(organization);
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
