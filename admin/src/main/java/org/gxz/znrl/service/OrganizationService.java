package org.gxz.znrl.service;

import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.Organization;
import org.gxz.znrl.exception.ServiceException;

import java.util.List;

/** 
 * @description: 组织
 * @version 1.0
 * @author vincent
 * @createDate 2014-1-11;下午02:15:24
 */
public interface OrganizationService {

	List<Organization> find(Long parentId, String name, Page page);
	List<Organization> findByParentId(Long parentId);

    List<Organization> findAll();

	Organization getTree();

	void save(Organization organization);

	Organization get(Long id);

	void update(Organization organization);

	void delete(Long id) throws ServiceException;
	
	Organization getByName(String name);
}
