package org.gxz.znrl.service.impl;

import org.gxz.znrl.entity.CarForecastInfoEntity;
import org.gxz.znrl.entity.CarForecastMapEntity;
import org.gxz.znrl.entity.CarPlanInfoEntity;
import org.gxz.znrl.entity.CarForecastDetailEntity;
import org.gxz.znrl.mapper.CarForecastInfoMapper;
import org.gxz.znrl.service.CommonService;
import org.gxz.znrl.service.ICarForecastInfoService;
import org.gxz.znrl.viewModel.GridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by admin-rubbissh on 2015/3/5.
 */
@Service("carForecastInfoService")
@Transactional
public class CarForecastInfoServiceImpl implements ICarForecastInfoService {

    @Autowired
    public CarForecastInfoMapper carForecastInfoMapper;

    @Autowired
    private CommonService commonService;

    @Override
    public GridModel qryCarForecastInfoList(CarForecastInfoEntity carForecastInfoEntity) {
        //查询总记录数
        Integer totalCnt = carForecastInfoMapper.qryCarForecastInfoListCnt(carForecastInfoEntity);
        //查询本次页面的结果集
        List<CarForecastInfoEntity> list = carForecastInfoMapper.qryCarForecastInfoList(carForecastInfoEntity);

        List<CarForecastInfoEntity> listFoot = carForecastInfoMapper.qryCarForecastInfoFoot(carForecastInfoEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);
        m.setFooter(listFoot);

        return m;
    }

    @Override
    public CarForecastInfoEntity qryCarForecastInfoById(String id) {
        CarForecastInfoEntity d = new CarForecastInfoEntity();
        d.setForecastId(id);
        List<CarForecastInfoEntity> list = carForecastInfoMapper.qryCarForecastInfoById(d);
        //设置返回结果
        d = list.get(0);
        return d;
    }

    @Override
    public void addCarForecast(CarForecastInfoEntity carForecastInfoEntity) throws ParseException,RuntimeException {
            carForecastInfoEntity.setForecastId(String.valueOf(commonService.getNextval("SEQ_FORECAST_ID")));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            //carForecastInfoEntity.setStartDate(df.format(now));
            //carForecastInfoEntity.setEndDate(df.format(now));
            carForecastInfoMapper.addCarForecast(carForecastInfoEntity);
            CarForecastMapEntity carForecastMapEntity = new CarForecastMapEntity();
            if(carForecastInfoEntity.getTimeZoneId()!=null){
                String ss[] = carForecastInfoEntity.getTimeZoneId().split(",");
    //            carForecastInfoMapper.addCarForecast(carForecastInfoEntity);
    //            CarForecastMapEntity carForecastMapEntity = new CarForecastMapEntity();
    //            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+ss.length+",>>>>>:"+carForecastInfoEntity.getTimeZoneId());
                for (int i = 0;i<ss.length;i++) {
                    carForecastMapEntity.setForecastId(Integer.parseInt(carForecastInfoEntity.getForecastId()));
                    carForecastMapEntity.setTimeZoneId(Integer.parseInt(ss[i]));
                    this.addCarForecastMap(carForecastMapEntity);
                }
                Date startDate = df.parse(carForecastInfoEntity.getStartDate());
                Date nowDate = df.parse(df.format(now));
                //时间比now大，为-1，比now小，为 1 ,相等为0;
                int date = nowDate.compareTo(startDate);
                CarPlanInfoEntity carPlanInfoEntity = new CarPlanInfoEntity();
                carPlanInfoEntity.setVenNo(carForecastInfoEntity.getVenNo());
                carPlanInfoEntity.setMineNo(carForecastInfoEntity.getMineNo());
                carPlanInfoEntity.setCoalNo(carForecastInfoEntity.getCoalNo());
                carPlanInfoEntity.setCreateDate(df.format(now));
                List<CarPlanInfoEntity> carPlanInfoList = this.qryCarPlanInfoList(carPlanInfoEntity).getRows();
    //            System.out.println("date:"+date);
    //            System.out.println("carForecastInfoEntity.getImmediatelyTag():"+carForecastInfoEntity.getImmediatelyTag());
    //            System.out.println("carPlanInfoList.size():"+carPlanInfoList.size());
                if (date == 0 && carForecastInfoEntity.getImmediatelyTag().equals("1") && carPlanInfoList.size() <= 0) {
                    carForecastInfoEntity.setCarPlanId(String.valueOf(commonService.getNextval("SEQ_CAR_PLAN_ID")));
                    this.addCarPlanInfo(carForecastInfoEntity);
                    this.addBatchNoInfo(carForecastInfoEntity);
                }
            }else{
//                String ss[] = carForecastInfoEntity.getTimeZoneId().split(",");
                //            carForecastInfoMapper.addCarForecast(carForecastInfoEntity);
                //            CarForecastMapEntity carForecastMapEntity = new CarForecastMapEntity();
                //            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+ss.length+",>>>>>:"+carForecastInfoEntity.getTimeZoneId());
//                for (int i = 0;i<ss.length;i++) {
//                    carForecastMapEntity.setForecastId(Integer.parseInt(carForecastInfoEntity.getForecastId()));
//                    carForecastMapEntity.setTimeZoneId(Integer.parseInt(ss[i]));
//                    this.addCarForecastMap(carForecastMapEntity);
//                }
//                Date startDate = df.parse(carForecastInfoEntity.getStartDate());
//                Date nowDate = df.parse(df.format(now));
//                //时间比now大，为-1，比now小，为 1 ,相等为0;
//                int date = nowDate.compareTo(startDate);
                CarPlanInfoEntity carPlanInfoEntity = new CarPlanInfoEntity();
                carPlanInfoEntity.setVenNo(carForecastInfoEntity.getVenNo());
                carPlanInfoEntity.setMineNo(carForecastInfoEntity.getMineNo());
                carPlanInfoEntity.setCoalNo(carForecastInfoEntity.getCoalNo());
                carPlanInfoEntity.setCreateDate(df.format(now));
                carPlanInfoEntity.setEffectDate(df.format(now));
                List<CarPlanInfoEntity> carPlanInfoList = this.qryCarPlanInfoList(carPlanInfoEntity).getRows();
                //            System.out.println("date:"+date);
                //            System.out.println("carForecastInfoEntity.getImmediatelyTag():"+carForecastInfoEntity.getImmediatelyTag());
                //            System.out.println("carPlanInfoList.size():"+carPlanInfoList.size());
                if ( carPlanInfoList.size() <= 0) {
                    carForecastInfoEntity.setCarPlanId(String.valueOf(commonService.getNextval("SEQ_CAR_PLAN_ID")));
                    this.addCarPlanInfo(carForecastInfoEntity);
                 //   this.addBatchNoInfo(carForecastInfoEntity);
                }
            }
    }

    @Override
    public void modifyCarForecast(CarForecastInfoEntity carForecastInfoEntity) {
        if(carForecastInfoEntity.getTimeZoneId()!=null){
             String ss[] = carForecastInfoEntity.getTimeZoneId().split(",");
            CarForecastMapEntity carForecastMapEntity = new CarForecastMapEntity();
            carForecastMapEntity.setForecastId(Integer.parseInt(carForecastInfoEntity.getForecastId()));
            carForecastInfoMapper.modifyCarForecast(carForecastInfoEntity);
            carForecastInfoMapper.delCarForecastMap(carForecastMapEntity);
            for (int i = 0;i<ss.length;i++) {
                carForecastMapEntity.setTimeZoneId(Integer.parseInt(ss[i]));
                carForecastInfoMapper.addCarForecastMap(carForecastMapEntity);
            }
        }else{
            carForecastInfoMapper.modifyCarForecast(carForecastInfoEntity);
        }
    }

    @Override
    public void delCarForecast(CarForecastInfoEntity carForecastInfoEntity) {
        carForecastInfoMapper.delCarForecast(carForecastInfoEntity);
    }

    @Override
    public GridModel qryCarPlanInfoList(CarPlanInfoEntity carPlanInfoEntity) {
        //查询总记录数
        Integer totalCnt = carForecastInfoMapper.qryCarPlanInfoListCnt(carPlanInfoEntity);
        //查询本次页面的结果集
        List<CarPlanInfoEntity> list = carForecastInfoMapper.qryCarPlanInfoList(carPlanInfoEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);

        return m;
    }

    @Override
    public void delCarPlanInfo(CarPlanInfoEntity CarPlanInfoEntity) {
        carForecastInfoMapper.delBatchNoInfo(CarPlanInfoEntity);
        carForecastInfoMapper.delCarPlanInfo(CarPlanInfoEntity);
    }

    @Override
    public CarForecastInfoEntity qryCarForecastInfoByEntity(CarForecastInfoEntity CarForecastInfoEntity) {
        return carForecastInfoMapper.qryCarForecastInfoByEntity(CarForecastInfoEntity);
    }

    @Override
    public void addCarPlanInfo(CarForecastInfoEntity carForecastInfoEntity) {
        carForecastInfoMapper.addCarPlanInfo(carForecastInfoEntity);
    }

    @Override
    public void addBatchNoInfo(CarForecastInfoEntity carForecastInfoEntity) {
        carForecastInfoMapper.addBatchNoInfo(carForecastInfoEntity);
    }

    @Override
    public void addCarForecastMap(CarForecastMapEntity carForecastMapEntity) {
        carForecastInfoMapper.addCarForecastMap(carForecastMapEntity);
    }

    @Override
    public void addCarForecastDetail(CarForecastDetailEntity carForecastDetailEntity) {
        carForecastInfoMapper.addCarForecastDetail(carForecastDetailEntity);
    }

    @Override
    public GridModel qryCarForecastDetailList(CarForecastDetailEntity carForecastDetailEntity) {
        //查询总记录数
        Integer totalCnt = carForecastInfoMapper.qryCarForecastDetailCnt(carForecastDetailEntity);
        //查询本次页面的结果集
        List<CarForecastDetailEntity> list = carForecastInfoMapper.qryCarForecastDetailList(carForecastDetailEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);

        return m;
    }

    @Override
    public void delCarForecastDetail(CarForecastDetailEntity carForecastDetailEntity) {
        carForecastInfoMapper.delCarForecastDetail(carForecastDetailEntity);
    }

    @Override
    public Map<String,String> queryContractVendorMineType(Map map){
        return carForecastInfoMapper.queryContractVendorMineType(map);
    }

}
