package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin-rubbissh on 2015/1/12.
 */
@Repository
public interface TrainMonitorMapper {

    //称重 查询总记录数
    public Integer qryWeightListCnt(TrainWeightEntity weightEntity);

    public List<TrainWeightEntity> qryWeightList(TrainWeightEntity weightEntity);

    //采样 查询总记录数
    public Integer qrySampleListCnt(TrainWeightRptEntity sampleEntity);

    //查询本次页面的结果集
    public List<TrainWeightRptEntity> qrySampleList(TrainWeightRptEntity sampleEntity);

    //查询制样结果动态信息
    public List<TrainMakeSampleEntity> qrySampling();

    //提交控制设备命令
    public void commitCtrlCmd(CtrlEntity ctrlEntity);

    //查询今日来煤情况汇总
    public List<TrainWeightRptEntity> qryTodayArrivedCoal();

    //查询今日来煤情况汇总
    public List<TrainWeightRptEntity> qryTrainComeIn();

    //查询燃料指标
    public List<TrainWeightRptEntity> qryFuelIndicator();

    //查询今日汽车动态信息
    public List<TrainWeightRptEntity> qryCarDynamic();

    public List<TrainWeightRptEntity> qryTrainOveriew();

    public List<TrainWeightRptEntity> qryCarDumperList();

    public Integer qryCarDumperListCnt();
}
