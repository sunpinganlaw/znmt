package org.gxz.znrl.service;

import org.gxz.znrl.entity.TrainWeightRptEntity;
import org.gxz.znrl.entity.WeightRptEntity;
import org.gxz.znrl.viewModel.GridModel;

/**
 * Created by xieyt on 14-11-18.
 */
public interface ITrainRptService {
    //查询称重明细报表
    public GridModel qryWeightRptList(TrainWeightRptEntity trainWeightRptEntity);

    //查询称重汇总报表
    public TrainWeightRptEntity qryWeightSummaryRpt(TrainWeightRptEntity trainWeightRptEntity);

}
