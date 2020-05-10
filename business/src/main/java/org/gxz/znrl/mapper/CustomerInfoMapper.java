package org.gxz.znrl.mapper;

import org.apache.ibatis.annotations.Param;
import org.gxz.znrl.entity.CustomerInfo;
import org.gxz.znrl.entity.CustomerInfoCriteria;

import java.util.List;

public interface CustomerInfoMapper {
    int countByExample(CustomerInfoCriteria example);

    int deleteByExample(CustomerInfoCriteria example);

    int deleteByPrimaryKey(String venNo);

    int insert(CustomerInfo record);

    int insertSelective(CustomerInfo record);

    List<CustomerInfo> selectByExample(CustomerInfoCriteria example);

    CustomerInfo selectByPrimaryKey(String venNo);

    int updateByExampleSelective(@Param("record") CustomerInfo record, @Param("example") CustomerInfoCriteria example);

    int updateByExample(@Param("record") CustomerInfo record, @Param("example") CustomerInfoCriteria example);

    int updateByPrimaryKeySelective(CustomerInfo record);

    int updateByPrimaryKey(CustomerInfo record);
}