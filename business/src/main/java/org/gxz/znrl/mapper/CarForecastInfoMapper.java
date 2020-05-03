package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.CarForecastDetailEntity;
import org.gxz.znrl.entity.CarForecastInfoEntity;
import org.gxz.znrl.entity.CarForecastMapEntity;
import org.gxz.znrl.entity.CarPlanInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by admin-rubbissh on 2015/3/5.
 */
@Repository
public interface CarForecastInfoMapper {

    public Integer qryCarForecastInfoListCnt(CarForecastInfoEntity CarForecastInfoEntity);

    public List<CarForecastInfoEntity> qryCarForecastInfoList(CarForecastInfoEntity CarForecastInfoEntity);

    public List<CarForecastInfoEntity> qryCarForecastInfoFoot(CarForecastInfoEntity CarForecastInfoEntity);

    public List<CarForecastInfoEntity> qryCarForecastInfoById(CarForecastInfoEntity CarForecastInfoEntity);

    public  void addCarForecast(CarForecastInfoEntity carForecastInfoEntity);

    public  void addCarForecastMap(CarForecastMapEntity carForecastMapEntity);

    public  void delCarForecastMap(CarForecastMapEntity carForecastMapEntity);

    public  void modifyCarForecast(CarForecastInfoEntity carForecastInfoEntity);

    public  void delCarForecast(CarForecastInfoEntity carForecastInfoEntity);

    public Integer qryCarPlanInfoListCnt(CarPlanInfoEntity CarPlanInfoEntity);

    public List<CarPlanInfoEntity> qryCarPlanInfoList(CarPlanInfoEntity CarPlanInfoEntity);

    public  void delBatchNoInfo(CarPlanInfoEntity CarPlanInfoEntity);

    public  void delCarPlanInfo(CarPlanInfoEntity CarPlanInfoEntity);

    public  CarForecastInfoEntity qryCarForecastInfoByEntity(CarForecastInfoEntity CarForecastInfoEntity);

    public  void addCarPlanInfo(CarForecastInfoEntity carForecastInfoEntity);

    public  void addBatchNoInfo(CarForecastInfoEntity carForecastInfoEntity);

    public  void addCarForecastDetail(CarForecastDetailEntity carForecastDetailEntity) ;

    public Integer qryCarForecastDetailCnt(CarForecastDetailEntity carForecastDetailEntity);

    public List<CarForecastDetailEntity> qryCarForecastDetailList(CarForecastDetailEntity carForecastDetailEntity);

    public  void delCarForecastDetail(CarForecastDetailEntity carForecastDetailEntity);

    public Map<String,String> queryContractVendorMineType(Map map);
}
