package org.gxz.znrl.service;

import org.gxz.znrl.entity.ErrorSpecEntity;
import org.gxz.znrl.viewModel.GridModel;

import java.util.List;

/**
 * Created by rubish on 2015/4/9.
 */
public interface IErrorSpecService {

    public GridModel qryErrorSpecList(ErrorSpecEntity errorSpecEntity);
    public List<ErrorSpecEntity>  qryErrorSpecAsList(ErrorSpecEntity errorSpecEntity);

    public ErrorSpecEntity qryErrorSpecById(ErrorSpecEntity errorSpecEntity);

    public  void addErrorSpec(ErrorSpecEntity errorSpecEntity);

    public  void modifyErrorSpec(ErrorSpecEntity errorSpecEntity);

    public  void delErrorSpec(ErrorSpecEntity errorSpecEntity);

}
