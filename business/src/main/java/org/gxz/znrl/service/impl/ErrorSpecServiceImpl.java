package org.gxz.znrl.service.impl;

import org.gxz.znrl.entity.CarForecastInfoEntity;
import org.gxz.znrl.entity.ErrorSpecEntity;
import org.gxz.znrl.mapper.ErrorSpecMapper;
import org.gxz.znrl.service.CommonService;
import org.gxz.znrl.service.IErrorSpecService;
import org.gxz.znrl.viewModel.GridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by rubish on 2015/4/9.
 */
@Service("errorSpecService")
@Transactional
@SuppressWarnings("unchecked")
public class ErrorSpecServiceImpl implements IErrorSpecService {

    @Autowired
    public ErrorSpecMapper errorSpecMapper;

    @Autowired
    private CommonService commonService;

    @Override
    public GridModel qryErrorSpecList(ErrorSpecEntity errorSpecEntity) {
        //查询总记录数
        Integer totalCnt = errorSpecMapper.qryErrorSpecListCnt(errorSpecEntity);
        List<ErrorSpecEntity> list  = errorSpecMapper.qryErrorSpecList(errorSpecEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);
        return m;
    }

    @Override
    public  List<ErrorSpecEntity> qryErrorSpecAsList(ErrorSpecEntity errorSpecEntity) {
        //查询总记录数
        List<ErrorSpecEntity> list  = errorSpecMapper.qryErrorAsSpecList(errorSpecEntity);
        //设置返回结果
        return list;
    }
    @Override
    public ErrorSpecEntity qryErrorSpecById(ErrorSpecEntity errorSpecEntity) {
        return errorSpecMapper.qryErrorSpec(errorSpecEntity);
    }

    @Override
    public void addErrorSpec(ErrorSpecEntity errorSpecEntity) {
        errorSpecEntity.setErrorSpecId(String.valueOf(commonService.getNextval("SEQ_ERROR_SPEC_ID")));
        errorSpecMapper.addErrorSpec(errorSpecEntity);
    }

    @Override
    public void modifyErrorSpec(ErrorSpecEntity errorSpecEntity) {
        errorSpecMapper.modifyErrorSpec(errorSpecEntity);
    }

    @Override
    public void delErrorSpec(ErrorSpecEntity errorSpecEntity) {
        errorSpecMapper.delErrorSpec(errorSpecEntity);
    }
}
