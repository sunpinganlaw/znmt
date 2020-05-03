package org.gxz.znrl.mapper;

import org.apache.ibatis.annotations.Param;
import org.gxz.znrl.entity.ChannelInfoEntity;
import org.gxz.znrl.entity.ChannelInfoCriteria;

import java.util.List;

public interface ChannelInfoMapper {
    int countByExample(ChannelInfoCriteria example);

    int deleteByExample(ChannelInfoCriteria example);

    int deleteByPrimaryKey(String channelNo);

    int insert(ChannelInfoEntity record);

    int insertSelective(ChannelInfoEntity record);

    List<ChannelInfoEntity> selectByExample(ChannelInfoCriteria example);

    ChannelInfoEntity selectByPrimaryKey(String channelNo);

    int updateByExampleSelective(@Param("record") ChannelInfoEntity record, @Param("example") ChannelInfoCriteria example);

    int updateByExample(@Param("record") ChannelInfoEntity record, @Param("example") ChannelInfoCriteria example);

    int updateByPrimaryKeySelective(ChannelInfoEntity record);

    int updateByPrimaryKey(ChannelInfoEntity record);
}