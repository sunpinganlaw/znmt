package org.gxz.znrl.service;

import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.RolePermissionDataControl;

import java.util.List;


/** 
 * @description: 
 * @version 1.0
 * @author vincent
 * @createDate 2014-1-11;下午02:14:56
 */
public interface RolePermissionDataControlService {

	RolePermissionDataControl get(Long id);

	void saveOrUpdate(RolePermissionDataControl rolePermissionDataControl);

	void delete(Long id);
	
	List<RolePermissionDataControl> findAll(Page page);
	
	List<RolePermissionDataControl> findByExample(RolePermissionDataControl rolePermissionDataControl, Page page);
	
	void save(Iterable<RolePermissionDataControl> entities);
	
	void delete(Iterable<RolePermissionDataControl> entities);

	List<RolePermissionDataControl> findByRolePermissionId(Long rolePermissionId);
	
	List<RolePermissionDataControl> findByRoleId(Long roleId);
}
