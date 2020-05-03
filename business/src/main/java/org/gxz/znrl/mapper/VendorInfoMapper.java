package org.gxz.znrl.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.gxz.znrl.entity.VendorInfo;
import org.gxz.znrl.entity.VendorInfoCriteria;

public interface VendorInfoMapper {
    int countByExample(VendorInfoCriteria example);

    int deleteByExample(VendorInfoCriteria example);

    int deleteByPrimaryKey(String venNo);

    int insert(VendorInfo record);

    int insertSelective(VendorInfo record);

    List<VendorInfo> selectByExample(VendorInfoCriteria example);

    VendorInfo selectByPrimaryKey(String venNo);

    int updateByExampleSelective(@Param("record") VendorInfo record, @Param("example") VendorInfoCriteria example);

    int updateByExample(@Param("record") VendorInfo record, @Param("example") VendorInfoCriteria example);

    int updateByPrimaryKeySelective(VendorInfo record);

    int updateByPrimaryKey(VendorInfo record);
}