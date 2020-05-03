package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.TrainWeightRptEntity;
import org.gxz.znrl.entity.WeightRptEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xieyt on 14-11-18.
 */

@Repository
public interface TrainRptMapper {

    public Integer qryWeightRptListCnt(TrainWeightRptEntity trainWeightRptEntity);

    public List<TrainWeightRptEntity> qryWeightRptList(TrainWeightRptEntity trainWeightRptEntity);

    public Integer qryEntryTotalCnt(TrainWeightRptEntity trainWeightRptEntity);
    public Integer qryWeightTotalCnt(TrainWeightRptEntity trainWeightRptEntity);
    public Integer qryLightTotalCnt(TrainWeightRptEntity trainWeightRptEntity);

    public Float qryTotalMz(TrainWeightRptEntity trainWeightRptEntity);
    public Float qryTotalPz(TrainWeightRptEntity trainWeightRptEntity);
    public Float qryTotalNet(TrainWeightRptEntity trainWeightRptEntity);
}
