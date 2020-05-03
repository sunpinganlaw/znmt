package org.gxz.znrl.common.baseservice;

import org.gxz.znrl.common.basedao.BaseDao;

import javax.annotation.Resource;

public class BaseService implements Service{
	    protected BaseDao baseDao;	    
	    @Resource(name="baseDao")
	    public void setBaseDao(BaseDao baseDao) {
	        this.baseDao =baseDao;
	    }
}

