package org.gxz.znrl.service.impl;

import org.gxz.znrl.common.basedao.BaseDao;
import org.gxz.znrl.common.baseservice.BaseService;
import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.Module;
import org.gxz.znrl.entity.ModuleCriteria;
import org.gxz.znrl.exception.ExistedException;
import org.gxz.znrl.exception.ServiceException;
import org.gxz.znrl.mapper.ModuleMapper;
import org.gxz.znrl.service.CommonService;
import org.gxz.znrl.service.ModuleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("moduleService")
@SuppressWarnings("unchecked")
@Transactional
public class ModuleServiceImpl extends BaseService implements ModuleService {

    @Autowired
    private CommonService commonService;

	@Override
	public void delete(Long id) throws ServiceException {
		if (isRoot(id)) {
			throw new ServiceException("不允许删除根模块。");
		}
		
		Module module = this.get(id);
		
		//先判断是否存在子模块，如果存在子模块，则不允许删除
		if(getModuleByParentId(module.getId()).size() > 0){
			throw new ServiceException(module.getName() + "模块下存在子模块，不允许删除。");
		}
		
		baseDao.getMapper(ModuleMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<Module> find(Long parentId,String name, Page page) {
		ModuleCriteria criteria = new ModuleCriteria();
		ModuleCriteria.Criteria cri = criteria.createCriteria();
		
		if(parentId != null){
			cri.andParentIdEqualTo(parentId);
		}
		
		if(StringUtils.isNotBlank(name)){
			cri.andNameEqualTo(name);
		}
		
		if(page == null){
			return baseDao.getMapper(ModuleMapper.class).selectByExample(criteria);
		}
		
		return baseDao.selectByPage(BaseDao.SELECT_BY_EXAMPLE, criteria, page);
	}

	@Override
	public List<Module> findAll(String orderByClause) {
		ModuleCriteria criteria = new ModuleCriteria();
		ModuleCriteria.Criteria cri = criteria.createCriteria();
		
		if(StringUtils.isBlank(orderByClause)){
			orderByClause = "priority ASC";
		}
		
		criteria.setOrderByClause(orderByClause);
		
		List<Module> list = baseDao.getMapper(ModuleMapper.class).selectByExample(criteria);
		
		if(list != null && list.size() > 0){
			for(Module m:list){
				if(m.getParentId() != null){
					m.setParent(this.get(m.getParentId()));
				}
			}
		}
		return list;
	}

    @Override
    public List<Module> find(Module module) {
        ModuleCriteria criteria = new ModuleCriteria();
        ModuleCriteria.Criteria cri = criteria.createCriteria();

        if(StringUtils.isNotBlank(module.getName())){
            cri.andNameEqualTo(module.getName());
        }

        if(StringUtils.isNotBlank(module.getUrl())){
            cri.andUrlEqualTo(module.getUrl());
        }

        if(StringUtils.isNotBlank(module.getSn())){
            cri.andSnEqualTo(module.getSn());
        }

        if(module.getParentId()!=null){
            cri.andParentIdEqualTo(module.getParentId());
        }

        if(module.getId()!=null){
            cri.andIdEqualTo(module.getId());
        }

        return baseDao.getMapper(ModuleMapper.class).selectByExample(criteria);

    }

    @Override
	public Module get(Long id) {
		return baseDao.getMapper(ModuleMapper.class).selectByPrimaryKey(id);
	}
	
	public List<Module> getModuleByParentId(Long parentId){
		ModuleCriteria criteria = new ModuleCriteria();
		ModuleCriteria.Criteria cri = criteria.createCriteria();
		
		if(parentId != null){
			cri.andParentIdEqualTo(parentId);
		}
		
		return baseDao.getMapper(ModuleMapper.class).selectByExample(criteria);
	}

	@Override
	public List<Module> getBySn(String sn) {
		ModuleCriteria criteria = new ModuleCriteria();
		ModuleCriteria.Criteria cri = criteria.createCriteria();
		
		if(StringUtils.isNotBlank(sn)){
			cri.andSnEqualTo(sn);
		}
		
		return baseDao.getMapper(ModuleMapper.class).selectByExample(criteria);
	}

	@Override
	public Module getTree() {
		List<Module> list = findAll(null);
		
		if(list != null && list.size() > 0){
			List<Module> rootList = makeTree(list);
			
			return rootList.get(0);
		}
		
		return null;
	}
	
	private List<Module> makeTree(List<Module> list) {
		List<Module> parent = new ArrayList<Module>();
		// get parentId = null;
		for (Module e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<Module>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);
		
		makeChildren(parent, list);
		
		return parent;
	}
	
	private void makeChildren(List<Module> parent, List<Module> children) {
		if (children.isEmpty()) {
			return ;
		}
		
		List<Module> tmp = new ArrayList<Module>();
		for (Module c1 : parent) {
			for (Module c2 : children) {
				c2.setChildren(new ArrayList<Module>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}
		
		children.removeAll(tmp);
		
		makeChildren(tmp, children);
	}

	@Override
	public void save(Module module) throws ExistedException {
		if (getBySn(module.getSn()).size()>1) {
			throw new ExistedException("已存在sn=" + module.getSn() + "的模块。");
		}
        try{
            module.setId(commonService.getNextval("SEQ_MODULE_ID"));
            baseDao.getMapper(ModuleMapper.class).insertSelective(module);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public void update(Module module) {
        if (getBySn(module.getSn()).size()>1) {
            throw new ExistedException("已存在sn=" + module.getSn() + "的模块。");
        }
		baseDao.getMapper(ModuleMapper.class).updateByPrimaryKeySelective(module);
	}
	
	/**
	 * 判断是否是根模块.
	 */
	private boolean isRoot(Long id) {
		return id == 1;
	}

}
