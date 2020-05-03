package org.gxz.znrl.service.impl;

import org.gxz.znrl.common.basedao.BaseDao;
import org.gxz.znrl.common.baseservice.BaseService;
import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.Role;
import org.gxz.znrl.entity.RoleCriteria;
import org.gxz.znrl.mapper.RoleMapper;
import org.gxz.znrl.service.CommonService;
import org.gxz.znrl.service.RolePermissionService;
import org.gxz.znrl.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleService")
@Transactional
@SuppressWarnings("unchecked")
public class RoleServiceImpl extends BaseService implements RoleService {
	
	@Autowired
	private RolePermissionService rolePermissionService;

    @Autowired
    private CommonService commonService;
	
	@Override
	public void delete(Long id) {
		baseDao.getMapper(RoleMapper.class).deleteByPrimaryKey(id);
//		shiroRealm.clearAllCachedAuthorizationInfo();
	}

    @Override
    public Page findByPage(Page page, Role role) {
        page.setCount(find(null,role).size());
        return page.setRows(find(page,role));
    }

    public  RoleCriteria getCriteria(Page page,Role role)
    {
        RoleCriteria criteria = new RoleCriteria();
        RoleCriteria.Criteria cri = criteria.createCriteria();
        if (role != null) {
            if(StringUtils.isNotBlank(role.getDescription())){
                cri.andDescriptionEqualTo(role.getDescription());
            }
            if(StringUtils.isNotBlank(role.getName())){
                cri.andNameEqualTo(role.getName());
            }
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        return criteria;
    }

    @Override
	public List<Role> find(Page page, Role role) {
        if(page == null)
            return baseDao.getMapper(RoleMapper.class).selectByExample(getCriteria(page,role));
        else {
            List<Role> list = baseDao.selectByPage("org.gxz.znrl.mapper.RoleMapper." + BaseDao.SELECT_BY_EXAMPLE, getCriteria(page, role), page);
            return  list;
        }
	}
	
	public List<Role> findAll(){
		RoleCriteria criteria = new RoleCriteria();
		return baseDao.getMapper(RoleMapper.class).selectByExample(criteria);
	}

	@Override
	public Role get(Long id) {
		Role role = baseDao.getMapper(RoleMapper.class).selectByPrimaryKey(id);
		if(role != null){
			role.setRolePermissions(rolePermissionService.findByRoleId(role.getId()));
		}
		return role;
	}

	@Override
	public void save(Role role) {
		// TODO Auto-generated method stub
        try{
            role.setId(commonService.getNextval("SEQ_ROLE_ID"));
            baseDao.getMapper(RoleMapper.class).insertSelective(role);
        } catch (Exception e) {
            e.printStackTrace();
        }

	}

	@Override
	public void update(Role role) {
		// TODO Auto-generated method stub

        baseDao.getMapper(RoleMapper.class).updateByPrimaryKeySelective(role);
	}

}
