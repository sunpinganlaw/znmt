package org.gxz.znrl.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.common.util.JacksonMapper;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.entity.*;
import org.gxz.znrl.service.*;
import org.gxz.znrl.shiro.ShiroUser;
import org.gxz.znrl.util.Constant;
import org.gxz.znrl.viewModel.GridModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/management/security/user")
public class UserController extends BaseAction{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    private static final String LIST = "management/security/user/list";
    private static final String SHOW = "management/security/user/show";
    private static final String ADDEDIT = "management/security/user/userEdit";

    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProfessionService professionService;

    @Autowired
    private UserRoleService userRoleService;

    @RequiresPermissions("User:show")
    @RequestMapping(value="/list", method= RequestMethod.GET)
    public String list(HttpServletRequest request) {
        return LIST;
    }

    @RequestMapping(value="/show", method= RequestMethod.GET)
    public String show(HttpServletRequest request) {
        return SHOW;
    }

    @RequestMapping(value="/userList")
    @ResponseBody
    public GridModel userList(){
        User user = form(User.class);
        logger.debug("user.getUsername() = " + user.getUsername());
        Page info = userService.findByPage(page(), user);
        GridModel m = new GridModel();
        logger.debug("info.getRows() = " + info.getRows());
        logger.debug("info.getCount() = " + info.getCount());
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }

    //新增客户，弹出userEdit页面，先构造空json对象
    @RequestMapping(value="/userEdit")
    @ResponseBody
    public ModelAndView userEdit() {
        ModelAndView mav = new ModelAndView(ADDEDIT);
        User user = new User();
        try {
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

    @RequestMapping(value="/saveUser")
    @ResponseBody
    public Result saveUser(@ModelAttribute User user) {
        Result result = new Result();
        try {

            if(user.getId()==null) {
                long userId = commonService.getNextval("SEQ_USER_ID");
                user.setId(userId);
                if (userService.getByUsername(user.getUsername()) != null) {
                    result.setSuccessful(false);
                    result.setMsg("用户添加失败，登录名：" + user.getUsername() + "已存在。");
                    return result;
                }

                if (userService.getByEmail(user.getEmail()) != null) {
                    result.setSuccessful(false);
                    result.setMsg("用户添加失败，登录邮箱：" + user.getEmail() + "已存在。");
                    return result;
                }
                //先保存图片，因为是客户id绑定的，所以即使图片保存成功后面数据库失败也没关系
                saveUserSignPic(user.getSignPicContent(),user.getId());
                userService.save(user);
            }
            else
            {
                if (user.getStatus().equals("disabled")) {
                    user.setPassword("123456");
                    user.setPlainPassword("123456");
                }
                //先保存图片，因为是客户id绑定的，所以即使图片保存成功后面数据库失败也没关系
                saveUserSignPic(user.getSignPicContent(),user.getId());
                userService.update(user);
            }
            result.setSuccessful(true);
            result.setMsg("操作成功");
        }
        catch (Exception e)
        {
            result.setSuccessful(false);
            result.setMsg("操作失败"+e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     *  保存客户签名图片
     * @param base64PicStr
     * @param userId
     * @throws java.io.IOException
     */
    private void saveUserSignPic(String base64PicStr,long userId) throws IOException{
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            //Base64解码
            if(base64PicStr != null && base64PicStr.length() <=22){
                decoder = null;
                return;
            }
            byte[] imgBtyes = decoder.decodeBuffer(base64PicStr.substring(22));
            for (int i = 0; i < imgBtyes.length; ++i) {
                if (imgBtyes[i] < 0) {//调整异常数据
                    imgBtyes[i] += 256;
                }
            }
            String filePath = Constant.getConstVal("USER_SING_PIC_PATH");
            if(filePath!= null && filePath.length() > 0){
                if (!new File(filePath).exists()) {
                    createFile(filePath, 0);
                }
                out = new FileOutputStream(filePath+userId+".jpg");
                out.write(imgBtyes);
                out.flush();
            }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建新文件(目录),  fileFlag 0 目录， 1 文件
     */
    public static void createFile(String filePath, int fileFlag) throws IOException {
        try {
            File f = new File(filePath);
            if (fileFlag == 0)
                f.mkdirs();
            else if (fileFlag == 1)
                f.createNewFile();
        } catch (IOException e) {
            throw e;
        }
    }

    //从本地磁盘取出图片，并转化成base64字符串
    public static String getImageStr(String userId) {
        String filePath = Constant.getConstVal("USER_SING_PIC_PATH");
        String imgFile = filePath+userId+".jpg";
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        if(! (new File(imgFile)).exists() ){
            return "";
        }
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(data != null){
            BASE64Encoder encoder = new BASE64Encoder();
            return "data:image/png;base64,"+encoder.encode(data);
        }else{
            return "";
        }
    }

    //再转向修改客户页面前时，根据id将客户数据查出
    @RequestMapping(value="/editUser/{id}")
    @ResponseBody
    public ModelAndView editUser(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView(ADDEDIT);
        try {
            User user = userService.get(Long.parseLong(id + ""));
            //再将图片取出转换为base64编码，放入user对象中
            user.setSignPicContent(getImageStr(String.valueOf(id)));
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

    @RequestMapping(value="/deleteInfo/{id}", method=RequestMethod.POST)
    @ResponseBody
    public Result deleteInfo(@ModelAttribute User user, @PathVariable Integer id) {
        logger.debug("id = " + id);
        Result result = new Result();
        user.setStatus("disabled");
        userService.delete(user);
        result.setMsg("操作成功");
        result.setSuccessful(true);
        return result;
    }

    @RequestMapping(value="/saveUserList")
    @ResponseBody
    @Transactional
    public Result saveUserList(HttpServletRequest req){
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
        User user_temp = new User();
        Page info;

        //删除
        if(deleted != null){
            //把json字符串转换成对象
            List<User> listDeleted = JSON.parseArray(deleted, User.class);
            for(User ur : listDeleted){
                if(ur.getId() != null){
                    logger.debug("ur.getId()=============="+ur.getId());
                    if(ur.getId()==1) {
                        logger.debug("ADMIN=====DELETE=====ERROR====");
                        result.setMsg("ADMIN=====DELETE=====ERROR====");
                        result.setSuccessful(false);
                        break;
                    }
                    else {
                        //TODO 下面就可以根据转换后的对象进行相应的操作了
                        List<UserRole> list = userRoleService.find(ur.getId());
                        for(UserRole role : list)
                        {
                            userRoleService.delete(role.getId());
                        }
                        userService.delete(ur.getId());
                        result.setMsg("删除成功");
                        result.setSuccessful(true);
                    }
                }
            }
        }

        //添加

        if(inserted != null){
            List<User> listInserted = JSON.parseArray(inserted, User.class);
            for(User ur : listInserted){
                if(ur.getId() != null){
                    logger.debug("ur.getId()=============="+ur.getId());
                    user_temp.setUsername(ur.getUsername());
                    info = userService.findByPage(page(), user_temp);
                    if(info.getCount()>0) {
                        logger.debug("USER=NAME=repeat======ERROR====");

                        result.setMsg("USER=NAME=repeat======ERROR====");
                        result.setSuccessful(false);
                        result.setMsg("OK");
                        break;
                    }

                    user_temp.setUsername(null);
                    user_temp.setEmail(ur.getEmail());
                    info = userService.findByPage(page(), user_temp);
                    if(info.getCount()>0) {
                        logger.debug("USER=EMAIL=repeat======ERROR====");
                        result.setMsg("USER=EMAIL=repeat======ERROR====");
                        result.setSuccessful(false);
                        result.setMsg("OK");
                        break;
                    }

                    user_temp.setEmail(null);
                    user_temp.setPhone(ur.getPhone());
                    info = userService.findByPage(page(), user_temp);
                    if(info.getCount()>0) {
                        logger.debug("USER=Phone=repeat======ERROR====");
                        result.setMsg("USER=Phone=repeat======ERROR====");
                        result.setSuccessful(false);
                        result.setMsg("OK");
                        break;
                    }

                    if(ur.getUserRoles().size()>0) {

                    }
                    //TODO 下面就可以根据转换后的对象进行相应的操作了
                    result.setMsg("OK");
                    result.setSuccessful(true);
                    userService.save(ur);
                }
            }
        }

        //修改
        if(updated != null){
            //把json字符串转换成对象
            List<User> listUpdated = JSON.parseArray(updated, User.class);
            for(User ur : listUpdated){
                if(ur.getId() != null){
                    logger.debug("ur.getId()=============="+ur.getId());
                    user_temp.setUsername(ur.getUsername());
                    info = userService.findByPage(page(), user_temp);
                    if(info.getCount()>1) {
                        logger.debug("USER=NAME=repeat======ERROR====");

                        result.setMsg("USER=NAME=repeat======ERROR====");
                        result.setSuccessful(false);
                        result.setMsg("OK");
                        break;
                    }
                    user_temp.setUsername(null);
                    user_temp.setEmail(ur.getEmail());
                    info = userService.findByPage(page(), user_temp);
                    if(info.getCount()>1) {
                        logger.debug("USER=EMAIL=repeat======ERROR====");
                        result.setMsg("USER=EMAIL=repeat======ERROR====");
                        result.setSuccessful(false);
                        result.setMsg("OK");
                        break;
                    }

                    user_temp.setEmail(null);
                    user_temp.setPhone(ur.getPhone());
                    info = userService.findByPage(page(), user_temp);
                    if(info.getCount()>1) {
                        logger.debug("USER=Phone=repeat======ERROR====");
                        result.setMsg("USER=Phone=repeat======ERROR====");
                        result.setSuccessful(false);
                        result.setMsg("OK");
                        break;
                    }
                    //TODO 下面就可以根据转换后的对象进行相应的操作了
                    result.setMsg("OK");
                    result.setSuccessful(true);
                    userService.update(ur);
                }
            }
        }

        return result;

    }

    @RequestMapping(value="/editinfo/{id}", method=RequestMethod.POST)
    @ResponseBody
    public Result editingUser(@ModelAttribute User user, @PathVariable Integer id) {
        logger.debug("id = " + id);
        Result result = new Result();
        if(!Strings.isNullOrEmpty(id+""))
        userService.update(user);
        else
        userService.save(user);
        result.setMsg("successful");
        result.setSuccessful(true);
        return result;
    }

    @RequestMapping(value="/userinfo")
    @ResponseBody
    public User userInfo(HttpServletRequest request) throws AuthenticationException {
        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        User user = userService.get(shiroUser.getUser().getId());
        return user;
    }
    @RequestMapping(value="/findAllRole/{id}")
    @ResponseBody
    public List<Role> findAllRole(@PathVariable Long id) {
        logger.debug("id = " + id);
        List<Role> list = roleService.findAll();
        return list;
    }

    @RequestMapping(value="/findAllProfession")
    @ResponseBody
    public List<Profession> findAllProfession() {
        List<Profession> list = professionService.findAll();
        return list;
    }

    @RequestMapping(value="/findAllRoleSelect/{id}/{roles}")
    @ResponseBody
    public List<RoleSelect> findAllRoleSelect(@PathVariable Long id,@PathVariable String roles) {
        logger.debug("id = " + id);
        List<RoleSelect> roleSelects = new ArrayList<>();
        List<Role> list = roleService.findAll();

        for(Role role: list)
        {
            RoleSelect roleSelect = new RoleSelect();
                roleSelect.setId(role.getId());
                roleSelect.setDescription(role.getDescription());
                for (String roleId : StringUtils.split(roles, ",")) {
                    if(StringUtils.equals(role.getId().toString(),roleId)) {
                        roleSelect.setSelected(true);
                        break;
                    }
                }
                roleSelects.add(roleSelect);
        }
        return roleSelects;
    }
}
