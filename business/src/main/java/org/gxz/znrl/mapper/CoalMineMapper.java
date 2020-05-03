package org.gxz.znrl.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.gxz.znrl.entity.CoalMine;
import org.gxz.znrl.entity.CoalMineCriteria;

public interface CoalMineMapper {
    int countByExample(CoalMineCriteria example);

    int deleteByExample(CoalMineCriteria example);

    int deleteByPrimaryKey(String mineNo);

    int insert(CoalMine record);

    int insertSelective(CoalMine record);

    List<CoalMine> selectByExample(CoalMineCriteria example);

    CoalMine selectByPrimaryKey(String mineNo);

    int updateByExampleSelective(@Param("record") CoalMine record, @Param("example") CoalMineCriteria example);

    int updateByExample(@Param("record") CoalMine record, @Param("example") CoalMineCriteria example);

    int updateByPrimaryKeySelective(CoalMine record);

    int updateByPrimaryKey(CoalMine record);
}