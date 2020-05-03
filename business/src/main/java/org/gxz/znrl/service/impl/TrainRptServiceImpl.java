package org.gxz.znrl.service.impl;

import org.gxz.znrl.entity.TrainWeightRptEntity;
import org.gxz.znrl.entity.WeightRptEntity;
import org.gxz.znrl.mapper.RptMapper;
import org.gxz.znrl.mapper.TrainRptMapper;
import org.gxz.znrl.service.IRptService;
import org.gxz.znrl.service.ITrainRptService;
import org.gxz.znrl.viewModel.GridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xieyt on 14-11-18.
 */
@Service("trainRptService")
@Transactional
@SuppressWarnings("unchecked")
public class TrainRptServiceImpl implements ITrainRptService {

    @Autowired
    public TrainRptMapper trainRptMapper;

    public GridModel qryWeightRptList(TrainWeightRptEntity trainWeightRptEntity) {
        //查询总记录数
        Integer totalCnt = trainRptMapper.qryWeightRptListCnt(trainWeightRptEntity);
        //查询本次页面的结果集
        List<TrainWeightRptEntity> weightRptList = trainRptMapper.qryWeightRptList(trainWeightRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(weightRptList);
        m.setTotal(totalCnt);

        return m;
    }

    public TrainWeightRptEntity qryWeightSummaryRpt(TrainWeightRptEntity weightRptEntity){
        Integer intEntryTotalCnt = trainRptMapper.qryEntryTotalCnt(weightRptEntity);
        Integer intWeightTotalCnt = trainRptMapper.qryWeightTotalCnt(weightRptEntity);
        Integer intLightTotalCnt = trainRptMapper.qryLightTotalCnt(weightRptEntity);

        Float intTotalMz = trainRptMapper.qryTotalMz(weightRptEntity);
        Float intTotalPz = trainRptMapper.qryTotalPz(weightRptEntity);
        Float intTotalNet = trainRptMapper.qryTotalNet(weightRptEntity);

        //设置返回结果
        TrainWeightRptEntity w = new TrainWeightRptEntity();
        w.setTotalEntryCnt(intEntryTotalCnt.toString());
        w.setTotalCZCnt(intWeightTotalCnt.toString());
        w.setTotalJQCnt(intLightTotalCnt.toString());
        w.setTotalMzQty(intTotalMz.toString());
        w.setTotalPzQty(intTotalPz.toString());
        w.setTotalNetQty(intTotalNet.toString());
        return w;
    }


}
