package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by xieyt on 14-11-18.
 */

@Repository
public interface RptMapper {

    public Integer qryWeightRptListSumCnt(WeightRptEntity weightRptEntity);

    public List<WeightRptEntity> qryWeightRptSumList(WeightRptEntity weightRptEntity);

    public Integer qryWeightRptListCnt(WeightRptEntity weightRptEntity);

    public List<WeightRptEntity> qryWeightRptList(WeightRptEntity weightRptEntity);

    public List<WeightRptEntity> qryWeightRptList4CN(WeightRptEntity weightRptEntity);
    public List<WeightRptEntity> qryWeightRptList4CNOther(WeightRptEntity weightRptEntity);

    //宣威 要增加按批次统计的页面 xxs20180329
    public Integer qryWeightRptListCnt4CarBatch(WeightRptEntity weightRptEntity);
    //宣威 要增加按批次统计的页面 xxs20180329
    public List<WeightRptEntity> qryWeightRptList4CarBatch(WeightRptEntity weightRptEntity);

    //宣威 要增加按批次统计的页面 合计
    public List<WeightRptEntity> qryWeightRptList4CarBatchFoot(WeightRptEntity weightRptEntity);

    //宣威 要增加火车按批次统计的页面 zl20180710
    public Integer qryWeightRptListCnt4TrainBatch(WeightRptEntity weightRptEntity);
    //宣威 要增加火车按批次统计的页面 zl20180710
    public List<WeightRptEntity> qryWeightRptList4TrainBatch(WeightRptEntity weightRptEntity);
    public Integer qryWeightListCnt(WeightRptEntity weightRptEntity);

    public List<WeightRptEntity> qryWeightList(WeightRptEntity weightRptEntity);

    public String qryKpiValue();

    public Integer qryEntryTotalCnt(WeightRptEntity weightRptEntity);
    public Integer qryWeightTotalCnt(WeightRptEntity weightRptEntity);
    public Integer qryLightTotalCnt(WeightRptEntity weightRptEntity);

    public String qryTotalMz(WeightRptEntity weightRptEntity);
    public String qryTotalPz(WeightRptEntity weightRptEntity);
    public String qryTotalNet(WeightRptEntity weightRptEntity);
    public String qryTotalTick(WeightRptEntity weightRptEntity);
    public String qryTotalKd(WeightRptEntity weightRptEntity);
    public String qryTotalKd4ZJ(WeightRptEntity weightRptEntity);
    public String qryTotalHpQty(WeightRptEntity weightRptEntity);
    public String qryTotalNoKdNetQty(WeightRptEntity weightRptEntity);
    public String qryTotalDiffQty(WeightRptEntity weightRptEntity);

    /*train begin*/
    public Integer qryTrainWeightRptListCnt(WeightRptEntity weightRptEntity);
    public List<WeightRptEntity> qryTrainWeightRptList(WeightRptEntity weightRptEntity);
    public Integer qryTrainEntryTotalCnt(WeightRptEntity weightRptEntity);
    public Integer qryTrainWeightTotalCnt(WeightRptEntity weightRptEntity);
    public String qryTrainTotalMz(WeightRptEntity weightRptEntity);
    public String qryTrainTotalPz(WeightRptEntity weightRptEntity);
    public String qryTrainTotalNet(WeightRptEntity weightRptEntity);
    /*train end*/

    public  void addFuelTruckUnload(KdQtyEntity kdQtyEntity);

    public  TruckFuelUnloadEntity qryTruckFuelUnload(TruckFuelUnloadEntity truckFuelUnloadEntity);

    public  TruckFuelEntranceRecordEntity qryTruckFuelEntranceRecord(TruckFuelEntranceRecordEntity truckFuelEntranceRecordEntity);

    public  void modifyTruckFuelUnload(TruckFuelUnloadEntity truckFuelUnloadEntity);

    public  void modifyFuelTruckState(KdQtyEntity kdQtyEntity);

    public  void modifyFuelTruckRecord(KdQtyEntity kdQtyEntity);

    public  void modifyMeasureData(MeasureDataEntity measureDataEntity);

    public  void kdOperate(MeasureDataEntity measureDataEntity);

    public  KdQtyEntity qryFuelTruckState(KdQtyEntity kdQtyEntity);


    public Integer qrySampleRptCnt(SampleRptEntity SampleRptEntity);
    public Integer qryCarWeightLocalCnt(SampleRptEntity SampleRptEntity);

    public List<SampleRptEntity> qrySampleRptList(SampleRptEntity SampleRptEntity);
    public List<SampleRptEntity> qryCarWeightLocal(SampleRptEntity SampleRptEntity);

    public List<SampleRptEntity> qrySampleRptFooter(SampleRptEntity SampleRptEntity);


    public Integer qrySampleRptCnt4BB(SampleRptEntity SampleRptEntity);

    public List<SampleRptEntity> qrySampleRptList4BB(SampleRptEntity SampleRptEntity);

    public Integer qrySampleRptCnt4Hf(SampleRptEntity SampleRptEntity);

    public List<SampleRptEntity> qrySampleRptList4Hf(SampleRptEntity SampleRptEntity);

    public Integer qryMergeCarBatchCnt(BatchNoInfoEntity batchNoInfoEntity);

    public List<BatchNoInfoEntity> qryMergeCarBatchList(BatchNoInfoEntity batchNoInfoEntity);

    public Integer qrySamplingRptCnt(SamplingRptEntity SamplingRptEntity);

    public Integer qrySamplingRptCnt4BB(SamplingRptEntity SamplingRptEntity);
    public List<SamplingRptEntity> qrySamplingRptList4BB(SamplingRptEntity SamplingRptEntity);

    public Integer qrySamplingRptCnt4CCY(SamplingRptEntity SamplingRptEntity);
    public List<SamplingRptEntity> qrySamplingRptList4CCY(SamplingRptEntity SamplingRptEntity);

    //哈平南使用
    public List<SamplingRptEntity> qrySamplingRptList4Hpn(SamplingRptEntity SamplingRptEntity);

    //制样结果查询，谏壁使用
    public Integer qrySamplingRptCnt4JB(SamplingRptEntity SamplingRptEntity);

    public Integer qrySamplingRptCnt4HF(SamplingRptEntity SamplingRptEntity);

    public List<SamplingRptEntity> qrySamplingRptList(SamplingRptEntity SamplingRptEntity);
    public List<SamplingRptEntity> qrySamplingRptList4ZJ(SamplingRptEntity SamplingRptEntity);
    public List<SamplingRptEntity> qrySamplingRptFooter(SamplingRptEntity SamplingRptEntity);

    public List<SamplingRptEntity> qrySamplingRptList4JJ(SamplingRptEntity SamplingRptEntity);
    //制样结果查询，谏壁使用
    public List<SamplingRptEntity> qrySamplingRptList4JB(SamplingRptEntity SamplingRptEntity);

    public List<SamplingRptEntity> qrySamplingRptListView(SamplingRptEntity SamplingRptEntity);

    public Integer qrySampleBarrelRptCnt(SampleRptEntity sampleRptEntity);

    public List<SampleRptEntity> qrySampleBarrelRptList(SampleRptEntity sampleRptEntity);
	
	public String qrySampleResultData(SampleRptEntity sampleRptEntity);

    public String qrySamplingResultData(SamplingRptEntity samplingRptEntity);

    //制样结果查询，聊城使用
    public List<SamplingRptEntity> qrySamplingRptList4LC(SamplingRptEntity SamplingRptEntity);

    public List<SamplingRptEntity> qrySamplingRptList4HF(SamplingRptEntity SamplingRptEntity);

    public void setArguementBatch(SamplingRptEntity samplingRptEntity);

    public Integer qryManualSamplingCnt(ManualSamplingRecordEntity manualSamplingRecordEntity);

    public List<ManualSamplingRecordEntity> qryManualSamplingList(ManualSamplingRecordEntity ManualSamplingRecordEntity);

    public void addManualSamplingRecInfo(ManualSamplingRecordEntity manualSamplingRecordEntity);

    public Integer qryManualSampleCnt(ManualSampleRecordEntity manualSampleRecordEntity);

    public List<ManualSampleRecordEntity> qryManualSampleList(ManualSampleRecordEntity manualSampleRecordEntity);

    public List<ManualSampleRecordEntity> qryManualSampleListFoot(ManualSampleRecordEntity manualSampleRecordEntity);

    public void addManualSampleRecInfo(ManualSampleRecordEntity manualSampleRecordEntity);

    //查询抽查批次
    public List<SamplingRptEntity> qryCompareBatchInfoList(CompareBatchInfoEntity compareBatchInfoEntity);
    //查询批次
    public Integer qryBatchInfoListCnt(SamplingRptEntity SamplingRptEntity);
    //查询批次
    public List<SamplingRptEntity> qryBatchInfoList(SamplingRptEntity SamplingRptEntity);

    //大武口先提的 厂内倒运的车辆只过重磅 不分批 单独做个统计报表 xxs20180609
    public Integer qryWeightRptListCnt4CN(WeightRptEntity weightRptEntity);
    public Integer qryWeightRptListCnt4CNOther(WeightRptEntity weightRptEntity);

    //大武口先提的 厂内倒运的车辆只过重磅 不分批 单独做个统计报表 xxs20180609
    public Integer qryEntryTotalCnt4CN(WeightRptEntity weightRptEntity);
    public Integer qryWeightTotalCnt4CN(WeightRptEntity weightRptEntity);
    public Integer qryLightTotalCnt4CN(WeightRptEntity weightRptEntity);

    public String qryTotalMz4CN(WeightRptEntity weightRptEntity);
    public String qryTotalPz4CN(WeightRptEntity weightRptEntity);
    public String qryTotalNet4CN(WeightRptEntity weightRptEntity);
    public String qryTotalTick4CN(WeightRptEntity weightRptEntity);
    public String qryTotalKd4CN(WeightRptEntity weightRptEntity);

    public Integer qryAutoRateRptCnt(SampleRptEntity sampleRptEntity);
    public List<SampleRptEntity> qryAutoRateRptList(SampleRptEntity sampleRptEntity);
    public List<SampleRptEntity> qryAutoRateRptListFoot(SampleRptEntity sampleRptEntity);

    public Integer qryCheckBatchRptCnt(SamplingRptEntity SamplingRptEntity);
    public List<SamplingRptEntity> qryCheckBatchRptList(SamplingRptEntity SamplingRptEntity);
	
	//查询本次页面的结果集
    public List<BeltWeightEntity> qryBeltWeightRptList(BeltWeightEntity beltWeightEntity);

    public List<BeltWeightEntity> qryBeltWeightFooter(BeltWeightEntity beltWeightEntity);

    public Integer qrySampleDetailCnt(SampleRptEntity sampleRptEntity);

    public List<SampleRptEntity> qrySampleDetailRptList(SampleRptEntity sampleRptEntity);

    public List<Map<String,Object>> qrySampleEvaluation(Map<String,Object> paramMap);

    public Integer qrySampleRptCnt4JB(SampleRptEntity SampleRptEntity);

    public List<SampleRptEntity> qrySampleRptList4JB(SampleRptEntity SampleRptEntity);

    //库车使用
    public Integer qrySamplingRptCnt4KC(SamplingRptEntity SamplingRptEntity);
    public List<SamplingRptEntity> qrySamplingRptList4KC(SamplingRptEntity SamplingRptEntity);

    //衡丰，查询投运率记录
    public Integer qryUploadRateRptListCnt(UploadRateEntity uploadRateEntity);
    public List<UploadRateEntity> qryUploadRateRptList(UploadRateEntity uploadRateEntity);

    void updateSampleCnt(SampleRptEntity sampleRptEntity);
}
