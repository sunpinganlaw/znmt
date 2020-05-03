package org.gxz.znrl.service;

import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.entity.Role;
import org.gxz.znrl.entity.UserRole;
import org.gxz.znrl.entity.newsystem.RoleEntity;
import org.gxz.znrl.entity.User;
import org.gxz.znrl.entity.newsystem.UserEntity;
import org.gxz.znrl.entity.newsystem.TreeNode;
import org.gxz.znrl.viewModel.GridModel;

import java.util.List;
import java.util.Map;

/**
 * Created by zhupei on 18-4-23.
 */
public interface SystemService {
    //用户
    GridModel findUsersByPage(UserEntity user);

    List<UserRole> qryUserRoleByUserId(Long userId);

    UserEntity getUser(Long id);

    List<Role> qryRole();

    public int getByUsername(String username);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserInfo(User user);

    //角色
    GridModel qryRoleList(RoleEntity role);

    List<Map<String,Object>> userListByRoleId(Long id);

    Result saveRoleList(String deleted, String insert, String update);

    void deleteRole(Long id);

    Map<String,Object> queryRoleByName(Role role);

    void saveRole(Role role);

    void updateRole(Role role);

    //权限
    List<TreeNode> getNodeDataTree(Map<String,Object> params);

    public List<RoleEntity> selectRoleAll();

    List<Map<String,Object>> getRoleIdByUserId(Map<String, Object> params);

    Result saveRolePerm(Map<String,Object> params);

}
