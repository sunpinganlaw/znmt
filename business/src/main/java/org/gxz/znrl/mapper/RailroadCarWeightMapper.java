package org.gxz.znrl.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.gxz.znrl.entity.RailroadCarWeight;
import org.gxz.znrl.entity.RailroadCarWeightCriteria;

public interface RailroadCarWeightMapper {
    int countByExample(RailroadCarWeightCriteria example);

    int deleteByExample(RailroadCarWeightCriteria example);

    int deleteByPrimaryKey(Integer railroadCarId);

    int insert(RailroadCarWeight record);

    int insertSelective(RailroadCarWeight record);

    List<RailroadCarWeight> selectByExample(RailroadCarWeightCriteria example);

    RailroadCarWeight selectByPrimaryKey(Integer railroadCarId);

    int updateByExampleSelective(@Param("record") RailroadCarWeight record, @Param("example") RailroadCarWeightCriteria example);

    int updateByExample(@Param("record") RailroadCarWeight record, @Param("example") RailroadCarWeightCriteria example);

    int updateByPrimaryKeySelective(RailroadCarWeight record);

    int updateByPrimaryKey(RailroadCarWeight record);
}