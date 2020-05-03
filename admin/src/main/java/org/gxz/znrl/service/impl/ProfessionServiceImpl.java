package org.gxz.znrl.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.gxz.znrl.common.basedao.BaseDao;
import org.gxz.znrl.common.baseservice.BaseService;
import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.Profession;
import org.gxz.znrl.entity.ProfessionCriteria;
import org.gxz.znrl.mapper.ProfessionMapper;
import org.gxz.znrl.service.CommonService;
import org.gxz.znrl.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin-rubbissh on 2014/12/5.
 */
@Service("professionService")
@Transactional
@SuppressWarnings("unchecked")
public class ProfessionServiceImpl extends BaseService implements ProfessionService {

    @Autowired
    private CommonService commonService;

    @Override
    public List<Profession> find(Page page, Profession profession) {
        if(page == null)
            return baseDao.getMapper(ProfessionMapper.class).selectByExample(getCriteria(page,profession));
        else {
            List<Profession> list = baseDao.selectByPage("org.gxz.znrl.mapper.ProfessionMapper." + BaseDao.SELECT_BY_EXAMPLE, getCriteria(page, profession), page);
            return  list;
        }
    }

    public List<Profession> findAll(){
        ProfessionCriteria criteria = new ProfessionCriteria();
        return baseDao.getMapper(ProfessionMapper.class).selectByExample(criteria);
    }

    public ProfessionCriteria getCriteria(Page page,Profession profession)
    {
        ProfessionCriteria criteria = new ProfessionCriteria();
        ProfessionCriteria.Criteria cri = criteria.createCriteria();
        if (profession != null) {
            if(StringUtils.isNotBlank(profession.getDescription())){
                cri.andDescriptionEqualTo(profession.getDescription());
            }
            if(StringUtils.isNotBlank(profession.getName())){
                cri.andNameEqualTo(profession.getName());
            }
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        return criteria;
    }

    @Override
    public void update(Profession profession) {
        baseDao.getMapper(ProfessionMapper.class).updateByPrimaryKeySelective(profession);
    }

    @Override
    public void delete(Long id) {
        try {
            baseDao.getMapper(ProfessionMapper.class).deleteByPrimaryKey(id.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Profession profession) {
        try{
            profession.setId((int)commonService.getNextval("SEQ_PROFESSION_ID"));
            baseDao.getMapper(ProfessionMapper.class).insertSelective(profession);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Page findByPage(Page page, Profession profession) {
        page.setCount(find(null,profession).size());
        return page.setRows(find(page,profession));
    }
}
