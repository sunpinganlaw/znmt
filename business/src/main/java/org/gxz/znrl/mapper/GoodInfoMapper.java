package org.gxz.znrl.mapper;

import org.apache.ibatis.annotations.Param;
import org.gxz.znrl.entity.CoalType;
import org.gxz.znrl.entity.CoalTypeCriteria;
import org.gxz.znrl.entity.GoodInfoEntity;

import java.util.List;

public interface GoodInfoMapper {
    int countByExample(CoalTypeCriteria example);

    int deleteByExample(CoalTypeCriteria example);

    int deleteByPrimaryKey(String goodNo);

    int insert(GoodInfoEntity record);

    int insertSelective(GoodInfoEntity record);

    List<GoodInfoEntity> selectByExample(CoalTypeCriteria example);

    GoodInfoEntity selectByPrimaryKey(String goodNo);

    int updateByExampleSelective(@Param("record") GoodInfoEntity record, @Param("example") CoalTypeCriteria example);

    int updateByExample(@Param("record") GoodInfoEntity record, @Param("example") CoalTypeCriteria example);

    int updateByPrimaryKeySelective(GoodInfoEntity record);

    int updateByPrimaryKey(GoodInfoEntity record);
}