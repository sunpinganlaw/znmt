package org.gxz.znrl.service.impl;

import org.gxz.znrl.entity.Msg;
import org.gxz.znrl.entity.MsgCriteria;
import org.gxz.znrl.mapper.MsgMapper;
import org.gxz.znrl.service.MsgService;
import org.gxz.znrl.common.basedao.BaseDao;
import org.gxz.znrl.common.baseservice.BaseService;
import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Alex on 2014/9/11.
 */
@Service("MsgService")
@Transactional
public class MsgServiceImpl extends BaseService implements MsgService {

    private static final Logger logger = LoggerFactory.getLogger(MsgServiceImpl.class);
    @Autowired
    private UserService userService;

    @Override
    public Page find(Page page, Msg msg){
//        return page;

        page.setCount(countByExample(page,msg));
        List<Msg>  list= baseDao.selectByPage("org.gxz.znrl.mapper.MsgMapper."+BaseDao.SELECT_BY_EXAMPLE, getCriteria(page,msg),page);

        if(list.size() > 0){
            for(Msg m:list){
                if (m.getCreateUserId() != null){
                    m.setCreateUserName(userService.get(m.getCreateUserId()).getRealname());
                }
            }
        }

        return page.setRows(list);
    }
    @Override
    public int countByExample(Page page,Msg msg){
        return baseDao.getMapper(MsgMapper.class).countByExample(getCriteria(page,msg));
    }
    public MsgCriteria getCriteria(Page page, Msg msg)
    {
        MsgCriteria criteria = new MsgCriteria();
        MsgCriteria.Criteria cri = criteria.createCriteria();
        if (msg != null) {
            if(StringUtils.isNotBlank(msg.getMsgTitle()))
            {
                cri.andMsgTitleLike(msg.getMsgTitle());
            }
            if(StringUtils.isNotBlank(msg.getState()))
            {
                cri.andStateEqualTo(msg.getState());
            }
            if(StringUtils.isNotBlank(msg.getMsgType()))
            {
                cri.andMsgTypeEqualTo(msg.getMsgType());
            }

        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        return criteria;
    }


    @Override
    public List<Msg> verfyList(Msg msg){

        MsgCriteria criteria = new MsgCriteria();
        MsgCriteria.Criteria cri = criteria.createCriteria();

        if(msg != null){
            if(StringUtils.isNotBlank(msg.getMsgTitle()))
            {
                cri.andMsgTitleLike(msg.getMsgTitle());
            }
            if(StringUtils.isNotBlank(msg.getState()))
            {
                cri.andStateEqualTo(msg.getState());
            }
            if(StringUtils.isNotBlank(msg.getMsgType()))
            {
                cri.andMsgTypeEqualTo(msg.getMsgType());
            }
        }

        return baseDao.getMapper(MsgMapper.class).selectByExample(criteria);

    }

    @Override
    public List<Msg> findList(Page page, Msg msg){
        MsgCriteria criteria = new MsgCriteria();
        MsgCriteria.Criteria cri = criteria.createCriteria();

        if(msg != null){
            if(StringUtils.isNotBlank(msg.getMsgTitle()))
            {
                cri.andMsgTitleLike(msg.getMsgTitle());
            }
            if(StringUtils.isNotBlank(msg.getState()))
            {
                cri.andStateEqualTo(msg.getState());
            }
            if(StringUtils.isNotBlank(msg.getMsgType()))
            {
                cri.andMsgTypeEqualTo(msg.getMsgType());
            }
        }

        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }

        return baseDao.selectByPage("org.gxz.znrl.mapper.MsgMapper." + BaseDao.SELECT_BY_EXAMPLE, criteria, page);

    }
    @Override
    public List<Msg> findAll(){
        MsgCriteria criteria = new MsgCriteria();
        return baseDao.getMapper(MsgMapper.class).selectByExample(criteria);
    }
    @Override
    public void save(Msg msg){
        baseDao.getMapper(MsgMapper.class).insertSelective(msg);
        //baseDao.getMapper(MsgGroupMapper.class).insertSelective(msgGroup);
    }
    @Override
    public Msg get(Long id){
        return baseDao.getMapper(MsgMapper.class).selectByPrimaryKey(id);
    }
    @Override
    public void update(Msg msg){
        baseDao.getMapper(MsgMapper.class).updateByPrimaryKeySelective(msg);
    }
    @Override
    public void delete(Long id){
        baseDao.getMapper(MsgMapper.class).deleteByPrimaryKey(id);
    }

}
