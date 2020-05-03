package org.gxz.znrl.service.impl;

import org.gxz.znrl.common.baseservice.BaseService;
import org.gxz.znrl.entity.OrganizationRole;
import org.gxz.znrl.entity.OrganizationRoleCriteria;
import org.gxz.znrl.mapper.OrganizationRoleMapper;
import org.gxz.znrl.service.OrganizationRoleService;
import org.gxz.znrl.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("organizationRoleService")
@Transactional
@SuppressWarnings("unchecked")
public class OrganizationRoleServiceImpl extends BaseService implements OrganizationRoleService {

	@Resource(name="roleService")
	private RoleService roleService;
	
	@Override
	public void delete(Long organizationRoleId) {
		baseDao.getMapper(OrganizationRoleMapper.class).deleteByPrimaryKey(organizationRoleId);
	}

	@Override
	public List<OrganizationRole> find(Long organizationId) {
		OrganizationRoleCriteria criteria = new OrganizationRoleCriteria();
		OrganizationRoleCriteria.Criteria cri = criteria.createCriteria();
		if(organizationId != null){
			cri.andOrganizationIdEqualTo(organizationId);
		}
		
		List<OrganizationRole> list = baseDao.getMapper(OrganizationRoleMapper.class).selectByExample(criteria);
		for(OrganizationRole or : list){
			if(or.getRoleId() != null){
				or.setRole(roleService.get(or.getRoleId()));
			}
		}
		
		return list;
	}

	@Override
	public OrganizationRole get(Long id) {
		// TODO Auto-generated method stub
		return baseDao.getMapper(OrganizationRoleMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void save(OrganizationRole organizationRole) {
		baseDao.getMapper(OrganizationRoleMapper.class).insertSelective(organizationRole);

	}

}
