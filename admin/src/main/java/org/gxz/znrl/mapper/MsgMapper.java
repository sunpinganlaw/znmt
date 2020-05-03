package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.Msg;
import org.gxz.znrl.entity.MsgCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MsgMapper {
    int countByExample(MsgCriteria example);

    int deleteByExample(MsgCriteria example);

    int deleteByPrimaryKey(Long msgId);

    int insert(Msg record);

    int insertSelective(Msg record);

    List<Msg> selectByExampleWithBLOBs(MsgCriteria example);

    List<Msg> selectByExample(MsgCriteria example);

    Msg selectByPrimaryKey(Long msgId);

    int updateByExampleSelective(@Param("record") Msg record, @Param("example") MsgCriteria example);

    int updateByExampleWithBLOBs(@Param("record") Msg record, @Param("example") MsgCriteria example);

    int updateByExample(@Param("record") Msg record, @Param("example") MsgCriteria example);

    int updateByPrimaryKeySelective(Msg record);

    int updateByPrimaryKeyWithBLOBs(Msg record);

    int updateByPrimaryKey(Msg record);
}