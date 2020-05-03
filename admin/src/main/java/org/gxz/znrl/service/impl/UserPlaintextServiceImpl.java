package org.gxz.znrl.service.impl;

import org.gxz.znrl.common.baseservice.BaseService;
import org.gxz.znrl.entity.UserPlaintext;
import org.gxz.znrl.mapper.UserPlaintextMapper;
import org.gxz.znrl.service.UserPlaintextService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rubish on 2015/5/8.
 */
@Service("userPlaintextService")@Transactional
@SuppressWarnings("unchecked")
public class UserPlaintextServiceImpl extends BaseService implements UserPlaintextService {

    @Override
    public void update(UserPlaintext userPlaintext) {
        baseDao.getMapper(UserPlaintextMapper.class).updateByPrimaryKey(userPlaintext);
    }

    @Override
    public void save(UserPlaintext userPlaintext) {
        baseDao.getMapper(UserPlaintextMapper.class).insertSelective(userPlaintext);
    }

    @Override
    public void delete(Long id) {
        baseDao.getMapper(UserPlaintextMapper.class).deleteByPrimaryKey(id);
    }

    @Override
    public UserPlaintext get(Long id) {
       return baseDao.getMapper(UserPlaintextMapper.class).selectByPrimaryKey(id);
    }
}
