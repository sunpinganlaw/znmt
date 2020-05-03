package org.gxz.znrl.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.gxz.znrl.entity.CoalType;
import org.gxz.znrl.entity.CoalTypeCriteria;

public interface CoalTypeMapper {
    int countByExample(CoalTypeCriteria example);

    int deleteByExample(CoalTypeCriteria example);

    int deleteByPrimaryKey(String coalNo);

    int insert(CoalType record);

    int insertSelective(CoalType record);

    List<CoalType> selectByExample(CoalTypeCriteria example);

    CoalType selectByPrimaryKey(String coalNo);

    int updateByExampleSelective(@Param("record") CoalType record, @Param("example") CoalTypeCriteria example);

    int updateByExample(@Param("record") CoalType record, @Param("example") CoalTypeCriteria example);

    int updateByPrimaryKeySelective(CoalType record);

    int updateByPrimaryKey(CoalType record);
}