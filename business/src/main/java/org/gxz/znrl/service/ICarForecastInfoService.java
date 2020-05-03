package org.gxz.znrl.service;

import org.gxz.znrl.entity.CarForecastInfoEntity;
import org.gxz.znrl.entity.CarForecastMapEntity;
import org.gxz.znrl.entity.CarPlanInfoEntity;
import org.gxz.znrl.entity.CarForecastDetailEntity;
import org.gxz.znrl.viewModel.GridModel;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by admin-rubbissh on 2015/3/5.
 */
public interface ICarForecastInfoService {

    //查询来煤预报列表
    public GridModel qryCarForecastInfoList(CarForecastInfoEntity carForecastInfoEntity);

    public  CarForecastInfoEntity qryCarForecastInfoById(String id);

    public  void addCarForecast(CarForecastInfoEntity carForecastInfoEntity) throws ParseException,RuntimeException;

    public  void modifyCarForecast(CarForecastInfoEntity carForecastInfoEntity);

    public  void delCarForecast(CarForecastInfoEntity carForecastInfoEntity);

    public GridModel qryCarPlanInfoList(CarPlanInfoEntity CarPlanInfoEntity);

    public  void delCarPlanInfo(CarPlanInfoEntity CarPlanInfoEntity);

    public  CarForecastInfoEntity qryCarForecastInfoByEntity(CarForecastInfoEntity CarForecastInfoEntity);

    public  void addCarPlanInfo(CarForecastInfoEntity carForecastInfoEntity);

    public  void addBatchNoInfo(CarForecastInfoEntity carForecastInfoEntity);

    public  void addCarForecastMap(CarForecastMapEntity carForecastMapEntity);

    public  void addCarForecastDetail(CarForecastDetailEntity carForecastDetailEntity) ;

    public GridModel qryCarForecastDetailList(CarForecastDetailEntity carForecastDetailEntity);

    public  void delCarForecastDetail(CarForecastDetailEntity carForecastDetailEntity);

    public Map<String,String> queryContractVendorMineType(Map map);
}
