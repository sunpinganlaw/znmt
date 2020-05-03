package org.gxz.znrl.service;

import org.gxz.znrl.entity.UserRole;

import java.util.List;


/** 
 * @description: 用户角色
 * @version 1.0
 * @author vincent
 * @createDate 2014-1-11;下午02:10:20
 */
public interface UserRoleService {

	UserRole get(Long id);
	
	/**
	 * 根据userId，找到已分配的角色。
	 * 描述
	 * @param userId
	 * @return
	 */
	List<UserRole> find(Long userId);

	void save(UserRole userRole);

	void delete(Long userRoleId);

    List<UserRole> find(UserRole userRole);
	
}
