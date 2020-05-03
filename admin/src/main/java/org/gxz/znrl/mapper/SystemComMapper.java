package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.Role;
import org.gxz.znrl.entity.UserPlaintext;
import org.gxz.znrl.entity.UserRole;
import org.gxz.znrl.entity.newsystem.RoleEntity;
import org.gxz.znrl.entity.User;
import org.gxz.znrl.entity.newsystem.UserEntity;
import org.gxz.znrl.entity.newsystem.TreeNode;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by hujy on 18-4-23.
 */
@Repository
public interface SystemComMapper {
    //用户
    Integer findUsersByPageCnt(UserEntity user);

    List<UserEntity> findUsersList(UserEntity user);

    List<UserRole> qryUserRoleByUserId(Long userId);

    UserEntity selectByPrimaryKey(Long id);

    List<Role> qryRole();

    int insertUserInfo(User record);

    int insertUserPlainTextInfo(UserPlaintext record);

    int insertUserRoleInfo(UserRole record);

    int deleteUserRoleInfo(Long userId);

    int updateByPrimaryKeySelective(User record);

    UserPlaintext getUserPlainTextByKey(Long id);

    int updateUserPlainTextByKey(UserPlaintext record);

    int deleteUserByUserId(User user);
    //角色
    Integer qryRoleListCnt(RoleEntity role);

    List<UserEntity> qryRoleList(RoleEntity role);

    List<Map<String,Object>> userListByRoleId(Long id);

    int deleteRole(Long id);

    Map<String,Object> queryRoleByName(Role role);

    void insertRole(Role role);

    void updateRole(Role role);

    //权限
    List<TreeNode> selectModuleList(Map<String, Object> params);

    List<TreeNode> selectOperateType();

    List<TreeNode> selectModulePermByRoleid(Map<String, Object> params);

    public List<RoleEntity> selectRoleAll();

    List<Map<String,Object>> getRoleIdByUserId(Map<String, Object> params);

    List<Integer> selectPermSequences(Integer cont);

    int deletePermModule(String roleId);

    int deletePermRole(String roleId);

    int batchPermInsert(List<TreeNode> list);

    int addPermRole(List<TreeNode> list);
}
