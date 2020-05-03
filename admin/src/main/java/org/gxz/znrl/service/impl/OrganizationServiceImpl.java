package org.gxz.znrl.service.impl;

import org.gxz.znrl.common.basedao.BaseDao;
import org.gxz.znrl.common.baseservice.BaseService;
import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.Organization;
import org.gxz.znrl.entity.OrganizationCriteria;
import org.gxz.znrl.entity.User;
import org.gxz.znrl.entity.UserCriteria;
import org.gxz.znrl.exception.ServiceException;
import org.gxz.znrl.mapper.OrganizationMapper;
import org.gxz.znrl.mapper.UserMapper;
import org.gxz.znrl.service.CommonService;
import org.gxz.znrl.service.OrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("organizationService")
@Transactional
@SuppressWarnings("unchecked")
public class OrganizationServiceImpl extends BaseService implements OrganizationService {

    @Autowired
    private CommonService commonService;

	@Override
	public void delete(Long id) throws ServiceException {
		if (isRoot(id)) {
			throw new ServiceException("不允许删除根组织。");
		}
		
		Organization organization = this.get(id);
		//先判断是否存在子模块，如果存在子模块，则不允许删除
		if(find(organization.getId(), null, null).size() > 0){
			throw new ServiceException(organization.getName() + "组织下存在子组织，不允许删除。");
		}
		
		UserCriteria criteria = new UserCriteria();
		criteria.createCriteria().andOrgIdEqualTo(organization.getId());
		List<User> userList = baseDao.getMapper(UserMapper.class).selectByExample(criteria);
		if (userList.size() > 0) {
			throw new ServiceException(organization.getName() + "组织下存在用户，不允许删除。");
		}
		
		baseDao.getMapper(OrganizationMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<Organization> find(Long parentId, String name, Page page) {
		OrganizationCriteria criteria = new OrganizationCriteria();
		OrganizationCriteria.Criteria cri = criteria.createCriteria();
		if(parentId != null){
			cri.andParentIdEqualTo(parentId);
		}
		
		if(StringUtils.isNotBlank(name)){
			cri.andNameEqualTo(name);
		}
		
		List<Organization> list = new ArrayList<Organization>();
		if(page == null){
			list = baseDao.getMapper(OrganizationMapper.class).selectByExample(criteria);
		}else{
			list = baseDao.selectByPage(BaseDao.SELECT_BY_EXAMPLE, criteria, page);
		}
		
		if(list.size() > 0){
			for(Organization o:list){
				if(o.getParentId() != null){
					o.setParent(this.get(o.getParentId()));
				}
			}
		}
		
		return list;
	}

    @Override
    public List<Organization> findByParentId(Long parentId) {
        OrganizationCriteria criteria = new OrganizationCriteria();
        OrganizationCriteria.Criteria cri = criteria.createCriteria();
        if(parentId != null){
            cri.andParentIdEqualTo(parentId);
        }
        List<Organization> list =   baseDao.getMapper(OrganizationMapper.class).selectByExample(criteria);
        return list;
    }

    @Override
    public List<Organization> findAll() {
        OrganizationCriteria criteria = new OrganizationCriteria();
            return baseDao.getMapper(OrganizationMapper.class).selectByExample(criteria);
    }

    @Override
	public Organization get(Long id) {
		// TODO Auto-generated method stub
		return baseDao.getMapper(OrganizationMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public Organization getByName(String name) {
		OrganizationCriteria criteria = new OrganizationCriteria();
		OrganizationCriteria.Criteria cri = criteria.createCriteria();
		
		if(StringUtils.isNotBlank(name)){
			cri.andNameEqualTo(name);
		}
		List<Organization> list = baseDao.getMapper(OrganizationMapper.class).selectByExample(criteria);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Organization getTree() {
		List<Organization> list = find(null, null, null);
		
		List<Organization> rootList = makeTree(list);
		
		return rootList.get(0);
	}
	
	private List<Organization> makeTree(List<Organization> list) {
		List<Organization> parent = new ArrayList<Organization>();
		// get parentId = null;
		for (Organization e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<Organization>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);
		
		makeChildren(parent, list);
		
		return parent;
	}
	
	private void makeChildren(List<Organization> parent, List<Organization> children) {
		if (children.isEmpty()) {
			return ;
		}
		
		List<Organization> tmp = new ArrayList<Organization>();
		for (Organization c1 : parent) {
			for (Organization c2 : children) {
				c2.setChildren(new ArrayList<Organization>(0));
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
	public void save(Organization organization) {
        try{
            organization.setId(commonService.getNextval("SEQ_ORGANIZATION_ID"));
            baseDao.getMapper(OrganizationMapper.class).insertSelective(organization);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public void update(Organization organization) {
		baseDao.getMapper(OrganizationMapper.class).updateByPrimaryKeySelective(organization);

	}
	
	/**
	 * 判断是否是根组织.
	 */
	private boolean isRoot(Long id) {
		return id == 1;
	}

}
