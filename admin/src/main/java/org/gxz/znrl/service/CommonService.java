package org.gxz.znrl.service;


import org.gxz.znrl.entity.ConstantEntity;
import org.gxz.znrl.entity.TableColumnByColumn;

import java.sql.Timestamp;
import java.util.List;

public interface CommonService {

	public Timestamp getSysdate() throws RuntimeException;

	public long getNextval(String seqName) throws RuntimeException;

	public String getTableColumnByColumn(TableColumnByColumn tableColumnByColumn) throws RuntimeException;

    //加载配置常量到内存
    public List<ConstantEntity> qryConstantCfgData() throws Exception;

    public List<ConstantEntity> qryLogMenuButtonInfo() throws Exception;
}
