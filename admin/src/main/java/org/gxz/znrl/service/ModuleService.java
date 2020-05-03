package org.gxz.znrl.service;

import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.Module;
import org.gxz.znrl.exception.ExistedException;
import org.gxz.znrl.exception.ServiceException;

import java.util.List;


public interface ModuleService {

	void save(Module module) throws ExistedException;
	
	Module get(Long id);

    List<Module> getBySn(String sn);
	
	void update(Module module);
	
	void delete(Long id) throws ServiceException;
	
	Module getTree();
	
	List<Module> findAll(String orderByClause);


    List<Module> find(Module module);
	
	List<Module> find(Long parentId, String name, Page page);
	
	/**
	 * @param parentId 根据父类查询
	 * @return
	 */
	public List<Module> getModuleByParentId(Long parentId);
	
}
