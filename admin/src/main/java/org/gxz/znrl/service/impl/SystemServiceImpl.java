package org.gxz.znrl.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.gxz.znrl.common.baseservice.BaseService;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.common.util.SimpleDate;
import org.gxz.znrl.entity.Role;
import org.gxz.znrl.entity.User;
import org.gxz.znrl.entity.UserPlaintext;
import org.gxz.znrl.entity.UserRole;
import org.gxz.znrl.entity.newsystem.RoleEntity;
import org.gxz.znrl.entity.newsystem.UserEntity;
import org.gxz.znrl.entity.newsystem.TreeNode;
import org.gxz.znrl.mapper.SystemComMapper;
import org.gxz.znrl.service.CommonService;
import org.gxz.znrl.service.SystemService;
import org.gxz.znrl.shiro.ShiroDbRealm;
import org.gxz.znrl.util.TreeBuilder;
import org.gxz.znrl.viewModel.GridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.sf.json.JSONArray;
import java.util.*;

/**
 * Created by hujy on 18-4-23.
 */
@Service("systemService")
@Transactional
public class SystemServiceImpl extends BaseService implements SystemService {

    private static final Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Autowired
    private SystemComMapper systemComMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ShiroDbRealm shiroRealm;

    @Override
    public GridModel findUsersByPage(UserEntity user) {

        Integer totalCnt = null;
        try {
            totalCnt = systemComMapper.findUsersByPageCnt(user);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

        List<UserEntity> userList = null;
        try {
            userList = systemComMapper.findUsersList(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置返回结果
        GridModel gm = new GridModel();
        gm.setRows(userList);
        gm.setTotal(totalCnt);
        return gm;
    }

    @Override
    public List<UserRole> qryUserRoleByUserId(Long userId) {
        List<UserRole>  userRoles = systemComMapper.qryUserRoleByUserId(userId);
        return userRoles;
    }

    @Override
    public UserEntity getUser(Long id) {
        UserEntity user = systemComMapper.selectByPrimaryKey(id);
        if(user != null) {
            List<UserRole> userRoles = systemComMapper.qryUserRoleByUserId(id);
            user.setUserRoles(userRoles);
        }
        return user;
    }

    @Override
    public List<Role> qryRole() {
        return systemComMapper.qryRole();
    }

    @Override
    public int getByUsername(String username) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        int totalCnt = systemComMapper.findUsersByPageCnt(user);
        return totalCnt;
    }

    @Override
    public void saveUser(User user) {
        long userId = commonService.getNextval("SEQ_USER_ID");
        user.setId(userId);
        //设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
        if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
            ShiroDbRealm.HashPassword hashPassword = ShiroDbRealm.encryptPassword(user.getPlainPassword());
            user.setSalt(hashPassword.salt);
            user.setPassword(hashPassword.password);
        }
        user.setCreateTime(SimpleDate.getDateTime());
        systemComMapper.insertUserInfo(user);

        //加入明文密码表 begin
        UserPlaintext userPlaintext = new UserPlaintext();
        userPlaintext.setId(user.getId());
        userPlaintext.setPassword(user.getPlainPassword());
        userPlaintext.setUsername(user.getUsername());
        systemComMapper.insertUserPlainTextInfo(userPlaintext);
        //加入明文密码表 end

        String roles = user.getRoles();
        if(StringUtils.isNotBlank(roles)){
            if(roles.indexOf(",")> 0){
                for (String roleId : StringUtils.split(roles,",")) {
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(Long.parseLong(roleId));
                    userRole.setUserId(userId);
                    userRole.setPriority(99);
                    userRole.setId(commonService.getNextval("SEQ_USER_ROLE_ID"));
                    systemComMapper.insertUserRoleInfo(userRole);
                }
            }else{
                UserRole userRole = new UserRole();
                userRole.setRoleId(Long.parseLong(roles));
                userRole.setUserId(userId);
                userRole.setPriority(99);
                userRole.setId(commonService.getNextval("SEQ_USER_ROLE_ID"));
                systemComMapper.insertUserRoleInfo(userRole);
            }
        }
        shiroRealm.clearCachedAuthorizationInfo(user.getUsername());

    }

    @Override
    public void updateUser(User user) {
        String roles = user.getRoles();
        Long userId = user.getId();
        if(StringUtils.isNotBlank(roles)){
            Set<String>  set1 = new HashSet<String>();
            for(String role : roles.split(",")){
                set1.add(role);
            }
            List<UserRole>  userRoles = systemComMapper.qryUserRoleByUserId(userId);
            Set<String>  set2 = new HashSet<String>();
            for(UserRole userRole:userRoles){
                set2.add(userRole.getRoleId().toString());
            }
            //如果前台传的角色与后台的角色相等不作处理
            //如果前台传的角色与后台的角色不相等
            if(set1.equals(set2)==false){
                //先删
                systemComMapper.deleteUserRoleInfo(userId);
                // 后插
                for(String roleId : roles.split(",")){
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(Long.parseLong(roleId));
                    userRole.setUserId(userId);
                    userRole.setPriority(99);
                    userRole.setId(commonService.getNextval("SEQ_USER_ROLE_ID"));
                    systemComMapper.insertUserRoleInfo(userRole);
                }
            }
        }

        if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
            ShiroDbRealm.HashPassword hashPassword = ShiroDbRealm.encryptPassword(user.getPlainPassword());
            user.setSalt(hashPassword.salt);
            user.setPassword(hashPassword.password);
        }
        systemComMapper.updateByPrimaryKeySelective(user);

        if(StringUtils.isNotBlank(user.getPlainPassword())){
            UserPlaintext userPlaintext = systemComMapper.getUserPlainTextByKey(user.getId());
            if (userPlaintext != null) {
                userPlaintext.setPassword(user.getPlainPassword());
                systemComMapper.updateUserPlainTextByKey(userPlaintext);
            } else {
                userPlaintext = new UserPlaintext();
                userPlaintext.setId(user.getId());
                userPlaintext.setPassword(user.getPlainPassword());
                userPlaintext.setUsername(user.getUsername());
                systemComMapper.insertUserPlainTextInfo(userPlaintext);
            }
        }
        shiroRealm.clearCachedAuthorizationInfo(user.getUsername());

    }

    @Override
    public void deleteUserInfo(User user) {
        UserEntity userTmp =  systemComMapper.selectByPrimaryKey(user.getId());
        systemComMapper.deleteUserByUserId(user);
        shiroRealm.clearCachedAuthorizationInfo(userTmp.getUsername());
    }

    @Override
    public GridModel qryRoleList(RoleEntity role) {
        Integer totalCnt = systemComMapper.qryRoleListCnt(role);
        List<UserEntity> roleList = systemComMapper.qryRoleList(role);
        GridModel gm = new GridModel();
        gm.setRows(roleList);
        gm.setTotal(totalCnt);
        return gm;
    }

    @Override
    public Result saveRoleList(String deleted, String inserted, String updated) {
        Result result = new Result();
        Boolean operateFlag = false;
        //删除
        if(deleted != null){
            //把json字符串转换成对象
            List<Role> listDeleted = JSON.parseArray(deleted, Role.class);
            for(Role roleMap : listDeleted){
                if(roleMap.getId() != null){
                    if(CollectionUtils.isNotEmpty(this.userListByRoleId(roleMap.getId()))) {
                        result.setMsg("该角色有用户，不可以删除");
                        result.setSuccessful(false);
                        operateFlag = false;
                        break;
                    }else {
                        try {
                            this.deleteRole(roleMap.getId());
                            operateFlag = true;
                        } catch (Exception e) {
                            operateFlag = false;
                            break;
                        }
                    }
                }
            }
            if (operateFlag == true){
                result.setMsg("删除成功");
                result.setSuccessful(true);
            }

        }

        //添加
        if(inserted != null){
            //把json字符串转换成对象
            List<Role> insertedList = JSON.parseArray(inserted, Role.class);
            for(Role roleMap : insertedList){
                if(roleMap.getId() == null){
                    if(StringUtils.isNotBlank(roleMap.getName())&&StringUtils.isNotBlank(roleMap.getDescription())){
                        if(MapUtils.isNotEmpty(this.queryRoleByName(roleMap))) {
                            result.setMsg("已存在角色，不可添加");
                            result.setSuccessful(false);
                            operateFlag = false;
                            break;
                        }else {
                            try {
                                this.saveRole(roleMap);
                                operateFlag = true;
                            } catch (Exception e) {
                                result.setMsg("添加失败"+ ExceptionUtils.getStackTrace(e));
                                result.setSuccessful(false);
                                operateFlag = false;
                                break;
                            }
                        }
                    }

                }
            }
            if (operateFlag == true){
                result.setMsg("添加成功");
                result.setSuccessful(true);
            }

        }



        //修改
        if(updated != null){
            //把json字符串转换成对象
            List<Role>  updatedList= JSON.parseArray(updated, Role.class);
            for(Role roleMap : updatedList){
                if(roleMap.getId() != null){
                    if(MapUtils.isNotEmpty(this.queryRoleByName(roleMap))) {
                        result.setMsg("已存在角色多个,不可修改");
                        result.setSuccessful(false);
                        operateFlag = false;
                        break;
                    }else {
                        try {
                            if(StringUtils.isNotBlank(roleMap.getName())||StringUtils.isNotBlank(roleMap.getDescription())){
                                this.updateRole(roleMap);
                                operateFlag = true;
                            }
                        }catch (Exception e){
                            operateFlag = false;
                            break;
                        }

                    }
                }
            }
            if (operateFlag == true){
                result.setMsg("修改成功");
                result.setSuccessful(true);
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> userListByRoleId(Long id) {
        return systemComMapper.userListByRoleId(id);
    }

    @Override
    public void deleteRole(Long id) {
        systemComMapper.deleteRole(id);
    }

    @Override
    public Map<String,Object> queryRoleByName(Role role) {
        Map<String,Object> map  = systemComMapper.queryRoleByName(role);
        return map;
    }

    @Override
    public void saveRole(Role role) {
        systemComMapper.insertRole(role);
    }

    @Override
    public void updateRole(Role role) {
        systemComMapper.updateRole(role);
    }

    @Override
    public List<TreeNode> getNodeDataTree(Map<String, Object> params) {
        List<TreeNode> moduleList =systemComMapper.selectModuleList(params);
        TreeBuilder treeBuilder = new TreeBuilder(moduleList);
        List<TreeNode> treeNodes = treeBuilder.buildTree();
        List<TreeNode> operNodesAll = systemComMapper.selectOperateType();
        Map<String,List<TreeNode>> mapByRoleId = selectModulePermByRoleid(params);
        handleModulePerm(mapByRoleId,operNodesAll);
        setLastTreeNodes(treeNodes,operNodesAll,mapByRoleId);
        JSONArray json = JSONArray.fromObject(treeNodes);
        String jsonStr =json.toString();
        return treeNodes;
    }

    public void setLastTreeNodes(List<TreeNode> treeNodes,List<TreeNode> operNodes,Map<String,List<TreeNode>> mapByRoleId){
        if (CollectionUtils.isNotEmpty(treeNodes)){
            for(int i = 0;i<treeNodes.size();i++){
                TreeNode node = treeNodes.get(i);
                String parentId = node.getId();
                List<TreeNode> subChildren = node.getChildren();
                if (CollectionUtils.isNotEmpty(subChildren)){
                    setLastTreeNodes(subChildren,operNodes,mapByRoleId);
                }else{
                    if (mapByRoleId.containsKey(node.getId())){
                            node.setChildren(mapByRoleId.get(node.getId()));
                            node.setState("closed");
                    }else{
                            List<TreeNode> newSubTreeNode = operNodes;
                            node.setChildren(newSubTreeNode);
                            node.setState("closed");
                    }
                }

            }
        }
    }

    public Map<String,List<TreeNode>> selectModulePermByRoleid(Map<String, Object> params){
        Map<String,List<TreeNode>> map = new HashMap<String,List<TreeNode>>();
        List<TreeNode> nodesList = systemComMapper.selectModulePermByRoleid(params);
        for(TreeNode node:nodesList){
            if (map.containsKey(node.getParentId())){
                List<TreeNode> existList = map.get(node.getParentId());
                existList.add(node);
            }else{
                List<TreeNode> newList = new  ArrayList<TreeNode>();
                newList.add(node);
                map.put(node.getParentId(),newList);
            }
        }
        return map;
    }

    public void handleModulePerm(Map<String,List<TreeNode>> map,List<TreeNode> operNodesAll){

        Set<String> keys = map.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            List<TreeNode> arrayList = map.get(key);
            if(arrayList.size()==operNodesAll.size()){
                continue;
            }
            Map<String,TreeNode> subMap = new HashMap<String,TreeNode>();
            for(TreeNode treeNode:arrayList){
                subMap.put(treeNode.getTextCode(),treeNode);
            }
            Map<String,TreeNode> linkedMap = new LinkedHashMap<String,TreeNode>();
            for (TreeNode node:operNodesAll){
                if(subMap.containsKey(node.getTextCode())){
                    linkedMap.put(node.getTextCode(),subMap.get(node.getTextCode()));
                }else{
                    linkedMap.put(node.getTextCode(),node);
                }
            }
            List<TreeNode> newArrayList  = new ArrayList<TreeNode>();
            for(Map.Entry<String,TreeNode> entry : linkedMap.entrySet()){
                newArrayList.add(entry.getValue());
            }
            map.put(key,newArrayList);
        }

    }

    @Override
    public List<RoleEntity> selectRoleAll() {
        return systemComMapper.selectRoleAll();
    }

    @Override
    public List<Map<String, Object>> getRoleIdByUserId(Map<String, Object> params) {
        return systemComMapper.getRoleIdByUserId(params);
    }

    @Override
    public Result saveRolePerm(Map<String,Object> params) {
        Result result = new Result();
        String roleId = MapUtils.getString(params,"roleId");

        String nodes = MapUtils.getString(params,"nodes");
        if(StringUtils.isNotBlank(nodes)){
            List<TreeNode> permList = JSON.parseArray(nodes, TreeNode.class);
            if(CollectionUtils.isNotEmpty(permList)){
                int cont = 0;
                for(TreeNode treeNode : permList){
                    treeNode.setIconCls(roleId);
                    if("n".equals(treeNode.getId())){
                        cont ++;
                    }
                }
                if(cont>0){
                    List<Integer> seqList = systemComMapper.selectPermSequences(cont);
                    int seqCont = seqList.size();
                    int nextInt = 0;
                    for(TreeNode treeNode : permList){
                        if("n".equals(treeNode.getId())){
                            if(nextInt < seqCont){
                                treeNode.setId(seqList.get(nextInt).toString());
                                nextInt ++;
                            }
                        }
                    }
                }
                //先删再插
                //先删SECURITY_PERMISSION SECURITY_ROLE_PERMISSION
                int del1 = systemComMapper.deletePermModule(roleId);
                int del2 = systemComMapper.deletePermRole(roleId);
                //再插新
                int add1 = systemComMapper.batchPermInsert(permList);
                int add2 =  systemComMapper.addPermRole(permList);
            }
        }
        result.setSuccessful(true);
        result.setMsg("授权成功!");
        return result;
    }
}
