package org.gxz.znrl.service.impl;

import org.gxz.znrl.entity.CtrlEntity;
import org.gxz.znrl.entity.TrainMakeSampleEntity;
import org.gxz.znrl.entity.TrainWeightEntity;
import org.gxz.znrl.entity.TrainWeightRptEntity;
import org.gxz.znrl.mapper.TrainMonitorMapper;
import org.gxz.znrl.service.ITrainMonitorService;
import org.gxz.znrl.viewModel.GridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin-rubbissh on 2015/1/12.
 */
@Service("trainMonitorService")
@Transactional
public class TrainMonitorServiceImpl implements ITrainMonitorService {

    @Autowired
    public TrainMonitorMapper trainMonitorMapper;

    //称重监控明细查询
    @Override
    public GridModel qryWeightList(TrainWeightEntity weightEntity) {
        //查询总记录数
        Integer totalCnt = trainMonitorMapper.qryWeightListCnt(weightEntity);
        //查询本次页面的结果集
        List<TrainWeightEntity> weightList = trainMonitorMapper.qryWeightList(weightEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(weightList);
        m.setTotal(totalCnt);
        return m;
    }

    @Override
    public GridModel qrySampleList(TrainWeightRptEntity sampleEntity) {
        //查询总记录数
        Integer totalCnt = trainMonitorMapper.qrySampleListCnt(sampleEntity);
        //查询本次页面的结果集
        List<TrainWeightRptEntity> sampleList = trainMonitorMapper.qrySampleList(sampleEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(sampleList);
        m.setTotal(totalCnt);

        return m;
    }

    //查询制样结果动态信息
    public GridModel qrySampling(){
        //查询本次页面的结果集
        List<TrainMakeSampleEntity> list = trainMonitorMapper.qrySampling();
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //提交设备控制命令
    public void commitCtrlCmd(CtrlEntity ctrlEntity){
        trainMonitorMapper.commitCtrlCmd(ctrlEntity);
    }

    //查询今日来煤情况
    public GridModel qryTodayArrivedCoal() {
        //查询本次页面的结果集
        List<TrainWeightRptEntity> list = trainMonitorMapper.qryTodayArrivedCoal();
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    //燃料指标
    public GridModel qryFuelIndicator() {
        //查询本次页面的结果集
        List<TrainWeightRptEntity> list = trainMonitorMapper.qryFuelIndicator();
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    //查询今日汽车动态信息
    public GridModel qryCarDynamic() {
        //查询本次页面的结果集
        List<TrainWeightRptEntity> list = trainMonitorMapper.qryCarDynamic();

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    @Override
    public GridModel qryTrainOveriew() {
        //查询本次页面的结果集
        List<TrainWeightRptEntity> list = trainMonitorMapper.qryTrainOveriew();

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    public GridModel qryTrainComeIn() {
        //查询本次页面的结果集
        List<TrainWeightRptEntity> list = trainMonitorMapper.qryTrainComeIn();

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }


    public GridModel qryCarDumperList() {
        //查询总记录数
        Integer totalCnt = trainMonitorMapper.qryCarDumperListCnt();
        //查询本次页面的结果集
        List<TrainWeightRptEntity> list = trainMonitorMapper.qryCarDumperList();
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);

        return m;
    }
}
