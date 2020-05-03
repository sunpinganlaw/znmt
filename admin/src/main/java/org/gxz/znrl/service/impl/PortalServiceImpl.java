package org.gxz.znrl.service.impl;

import org.gxz.znrl.common.basedao.BaseDao;
import org.gxz.znrl.common.baseservice.BaseService;
import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.SecurityPortal;
import org.gxz.znrl.entity.SecurityPortalCriteria;
import org.gxz.znrl.entity.SecurityPortalReport;
import org.gxz.znrl.mapper.SecurityPortalMapper;
import org.gxz.znrl.mapper.SecurityPortalReportMapper;
import org.gxz.znrl.service.PortalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vincent on 2014/9/14.
 */
@Service("portalService")
@Transactional
@SuppressWarnings("unchecked")
public class PortalServiceImpl extends BaseService implements PortalService  {
    @Override
    public Page findByPage(Page page, SecurityPortal securityPortal) {
        page.setCount(countByExample(page,securityPortal));
        List<SecurityPortal> list= baseDao.selectByPage("org.gxz.znrl.mapper.SecurityPortalMapper."+ BaseDao.SELECT_BY_EXAMPLE, getCriteria(page,securityPortal),page);
        if(list!=null)
            return page.setRows(list);
        else
            return null;
    }

    @Override
    public int countByExample(Page page, SecurityPortal securityPortal) {

        return baseDao.getMapper(SecurityPortalMapper.class).countByExample(getCriteria(page,securityPortal));
    }

    /**
     * 条件
     * @param page
     * @param securityPortal
     * @return
     */
    private SecurityPortalCriteria getCriteria(Page page, SecurityPortal securityPortal) {
        SecurityPortalCriteria criteria = new SecurityPortalCriteria();
        SecurityPortalCriteria.Criteria cri = criteria.createCriteria();
        if (securityPortal != null) {
            if (securityPortal.getId()!= null) {
                cri.andIdEqualTo(securityPortal.getId());
            }
            if (securityPortal.getState() != null) {
                if(securityPortal.getState()!=-1)
                    cri.andStateEqualTo(securityPortal.getState());
            }
            else
            {
                cri.andStateEqualTo(1);
            }
            if (securityPortal.getUserId()!=null) {
                cri.addCriterion(" id in ( SELECT r.portal_id FROM security_portal_role r where r.role_id in (select role_id from security_user_role u where u.user_id = "+securityPortal.getUserId()+" ) )");
            }
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        return criteria;
    }

    @Override
    public SecurityPortal get(int id) {
        return null;
    }

    @Override
    public List<SecurityPortalReport> customTypeSum(int state) {
        return baseDao.getMapper(SecurityPortalReportMapper.class).customTypeSum(state);
    }

    @Override
    public List<SecurityPortalReport> customMonthSum(int state) {
        return baseDao.getMapper(SecurityPortalReportMapper.class).customMonthSum(state);
    }

    @Override
    public List<SecurityPortalReport> serverRoom(int serverRoomId) {
        return baseDao.getMapper(SecurityPortalReportMapper.class).serverRoom(serverRoomId);
    }

    @Override
    public List<SecurityPortalReport> investMonth() {
        return baseDao.getMapper(SecurityPortalReportMapper.class).investMonth();
    }

    @Override
    public List<SecurityPortalReport> orderMonth() {
        return baseDao.getMapper(SecurityPortalReportMapper.class).orderMonth();
    }

    @Override
    public List<SecurityPortalReport> complaintMonth() {
        return baseDao.getMapper(SecurityPortalReportMapper.class).complaintMonth();
    }

}
