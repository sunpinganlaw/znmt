package org.gxz.znrl.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.gxz.znrl.entity.CarrierInfo;
import org.gxz.znrl.entity.CarrierInfoCriteria;

public interface CarrierInfoMapper {
    int countByExample(CarrierInfoCriteria example);

    int deleteByExample(CarrierInfoCriteria example);

    int deleteByPrimaryKey(String carrierNo);

    int insert(CarrierInfo record);

    int insertSelective(CarrierInfo record);

    List<CarrierInfo> selectByExample(CarrierInfoCriteria example);

    CarrierInfo selectByPrimaryKey(String carrierNo);

    int updateByExampleSelective(@Param("record") CarrierInfo record, @Param("example") CarrierInfoCriteria example);

    int updateByExample(@Param("record") CarrierInfo record, @Param("example") CarrierInfoCriteria example);

    int updateByPrimaryKeySelective(CarrierInfo record);

    int updateByPrimaryKey(CarrierInfo record);
}