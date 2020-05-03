package org.gxz.znrl.service;

import org.gxz.znrl.entity.CtrlEntity;
import org.gxz.znrl.entity.TrainWeightEntity;
import org.gxz.znrl.entity.TrainWeightRptEntity;
import org.gxz.znrl.viewModel.GridModel;

/**
 * Created by admin-rubbissh on 2015/1/12.
 */
public interface ITrainMonitorService {

    //查询称重
    public GridModel qryWeightList(TrainWeightEntity weightEntity);

    //查询采样
    public GridModel qrySampleList(TrainWeightRptEntity sampleEntity);

    //查询制样结果动态信息
    public GridModel qrySampling();

    //提交控制设备命令
    public void commitCtrlCmd(CtrlEntity ctrlEntity);

    //今日来煤信息汇总查询
    public GridModel qryTodayArrivedCoal();

    //燃料指标
    public GridModel qryFuelIndicator();

    //查询今日汽车动态信息
    public GridModel qryCarDynamic();

    public GridModel qryTrainOveriew();

    public  GridModel qryTrainComeIn();

    public  GridModel qryCarDumperList();
}
