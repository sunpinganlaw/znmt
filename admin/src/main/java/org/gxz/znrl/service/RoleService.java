package org.gxz.znrl.service;

import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.Role;

import java.util.List;

/** 
 * @description: 角色管理
 * @version 1.0
 * @author vincent
 * @createDate 2014-1-11;下午02:11:00
 */
public interface RoleService {


    Page findByPage(Page page, Role role);

	List<Role> find(Page page, Role role);
	
	List<Role> findAll();
	
	void save(Role role);

	Role get(Long id);

	void update(Role role);

	void delete(Long id);
}
