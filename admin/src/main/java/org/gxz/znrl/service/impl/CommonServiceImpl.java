package org.gxz.znrl.service.impl;

import org.gxz.znrl.common.baseservice.BaseService;
import org.gxz.znrl.entity.ConstantEntity;
import org.gxz.znrl.entity.TableColumnByColumn;
import org.gxz.znrl.mapper.CommonMapper;
import org.gxz.znrl.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("commonService")
@Transactional
@SuppressWarnings("unchecked")
public class CommonServiceImpl  extends BaseService implements CommonService {

//   @Autowired
//	private CommonMapper commonMapper;

    public Timestamp getSysdate() throws RuntimeException {
//		return commonMapper.getSysdate();
        return baseDao.getMapper(CommonMapper.class).getSysdate();
    }

    public long getNextval(String seqName) throws RuntimeException {
        Map paraMap = new HashMap();
        paraMap.put("seqName", seqName);
        return baseDao.getMapper(CommonMapper.class).getNextval(paraMap);
//		return commonMapper.getNextval(paraMap);
    }

    public String getTableColumnByColumn(TableColumnByColumn tableColumnByColumn) throws RuntimeException {
        return baseDao.getMapper(CommonMapper.class).getTableColumnByColumn(tableColumnByColumn);
    }

    //加载配置常量到内存
    @Override
    public List<ConstantEntity> qryConstantCfgData() throws Exception{
        return baseDao.getMapper(CommonMapper.class).qryConstantCfgData();
    }

    @Override
    public List<ConstantEntity> qryLogMenuButtonInfo() throws Exception {
        return baseDao.getMapper(CommonMapper.class).qryLogMenuButtonInfo();
    }
}
