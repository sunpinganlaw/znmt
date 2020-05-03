package org.gxz.znrl.mapper;

import org.apache.ibatis.annotations.Param;
import org.gxz.znrl.entity.StockInfoEntity;
import org.gxz.znrl.entity.StockInfoCriteria;

import java.util.List;

public interface StockInfoMapper {
    int countByExample(StockInfoCriteria example);

    int deleteByExample(StockInfoCriteria example);

    int deleteByPrimaryKey(String coalNo);

    int insert(StockInfoEntity record);

    int insertSelective(StockInfoEntity record);

    List<StockInfoEntity> selectByExample(StockInfoCriteria example);

    StockInfoEntity selectByPrimaryKey(String coalNo);

    int updateByExampleSelective(@Param("record") StockInfoEntity record, @Param("example") StockInfoCriteria example);

    int updateByExample(@Param("record") StockInfoEntity record, @Param("example") StockInfoCriteria example);

    int updateByPrimaryKeySelective(StockInfoEntity record);

    int updateByPrimaryKey(StockInfoEntity record);
}