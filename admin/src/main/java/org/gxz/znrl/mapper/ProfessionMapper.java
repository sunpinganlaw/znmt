package org.gxz.znrl.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.gxz.znrl.entity.Profession;
import org.gxz.znrl.entity.ProfessionCriteria;

public interface ProfessionMapper {
    int countByExample(ProfessionCriteria example);

    int deleteByExample(ProfessionCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Profession record);

    int insertSelective(Profession record);

    List<Profession> selectByExample(ProfessionCriteria example);

    Profession selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Profession record, @Param("example") ProfessionCriteria example);

    int updateByExample(@Param("record") Profession record, @Param("example") ProfessionCriteria example);

    int updateByPrimaryKeySelective(Profession record);

    int updateByPrimaryKey(Profession record);
}