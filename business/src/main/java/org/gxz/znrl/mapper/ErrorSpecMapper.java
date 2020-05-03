package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.ErrorSpecEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by rubish on 2015/4/9.
 */
@Repository
public interface ErrorSpecMapper {

    public List<ErrorSpecEntity> qryErrorSpecList(ErrorSpecEntity errorSpecEntity);

    public List<ErrorSpecEntity> qryErrorAsSpecList(ErrorSpecEntity errorSpecEntity);

    public Integer qryErrorSpecListCnt(ErrorSpecEntity errorSpecEntity);

    public ErrorSpecEntity qryErrorSpec(ErrorSpecEntity errorSpecEntity);

    public  void addErrorSpec(ErrorSpecEntity errorSpecEntity);

    public  void modifyErrorSpec(ErrorSpecEntity errorSpecEntity);

    public void delErrorSpec(ErrorSpecEntity errorSpecEntity);

}
