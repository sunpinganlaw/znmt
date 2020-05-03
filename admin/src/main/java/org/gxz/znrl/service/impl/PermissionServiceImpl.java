package org.gxz.znrl.service.impl;


import org.gxz.znrl.common.baseservice.BaseService;
import org.gxz.znrl.entity.Permission;
import org.gxz.znrl.entity.PermissionCriteria;
import org.gxz.znrl.mapper.ModuleMapper;
import org.gxz.znrl.mapper.PermissionMapper;
import org.gxz.znrl.service.CommonService;
import org.gxz.znrl.service.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("permissionService")
public class PermissionServiceImpl extends BaseService implements PermissionService {

    @Autowired
    private CommonService commonService;

	@Override
	public void delete(Long id) {
		baseDao.getMapper(PermissionMapper.class).deleteByPrimaryKey(id);
	}


    @Override
	public Permission get(Long id) {
		Permission p = baseDao.getMapper(PermissionMapper.class).selectByPrimaryKey(id);
		if(p != null && p.getModuleId() != null){
			p.setModule(baseDao.getMapper(ModuleMapper.class).selectByPrimaryKey(p.getModuleId()));
		}
		return p;
	}

    @Override
    public List<Permission> get(Permission permission) {
        PermissionCriteria criteria = new PermissionCriteria();
        PermissionCriteria.Criteria cri = criteria.createCriteria();

        if(StringUtils.isNotEmpty(permission.getShortName())){
            cri.andShortNameEqualTo(permission.getShortName());
        }
        if(permission.getModuleId()!=null){
            cri.andModuleIdEqualTo(permission.getModuleId());
        }

        List<Permission> list= baseDao.getMapper(PermissionMapper.class).selectByExample(criteria);
        return list;
    }

    @Override
    public List<Permission> getAll() {
        PermissionCriteria criteria = new PermissionCriteria();
        List<Permission> list= baseDao.getMapper(PermissionMapper.class).selectByExample(criteria);
        List<Permission> resultList = new ArrayList<>();
        for(Permission permission:list)
        {
            if(permission != null && permission.getModuleId() != null){
                permission.setModule(baseDao.getMapper(ModuleMapper.class).selectByPrimaryKey(permission.getModuleId()));
            }
            resultList.add(permission);
        }
        return resultList;
    }

    @Override
	public void save(Permission permission) {
        try{
            permission.setId(commonService.getNextval("SEQ_PERMISSION_ID"));
            baseDao.getMapper(PermissionMapper.class).insertSelective(permission);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public void update(Permission permission) {
		baseDao.getMapper(PermissionMapper.class).updateByPrimaryKeySelective(permission);
	}

}
