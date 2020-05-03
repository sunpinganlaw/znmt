package org.gxz.znrl.service;

import org.gxz.znrl.entity.*;
import org.gxz.znrl.viewModel.GridModel;

import java.util.List;
import java.util.Map;

/**
 * Created by xieyt on 14-11-18.
 */
public interface IRptService {


    //按日期、供应商查询总和
    public GridModel qryWeightRptSumList(WeightRptEntity weightRptEntity);

    //查询称重明细报表
    public GridModel qryWeightRptList(WeightRptEntity weightRptEntity);

    //宣威 要增加按批次统计的页面 xxs20180329
    public GridModel qryWeightRptList4CarBatch(WeightRptEntity weightRptEntity);

    public GridModel qryTrainWeightRptList(WeightRptEntity weightRptEntity);

    //查询称重明细报表
    public GridModel qryWeightList(WeightRptEntity weightRptEntity);

    //查询称重汇总报表
    public WeightRptEntity qryWeightSummaryRpt(WeightRptEntity weightRptEntity);

    public WeightRptEntity qryTrainWeightSummaryRpt(WeightRptEntity weightRptEntity);

    public  void modifyKdQty(KdQtyEntity kdQtyEntity);

    public  String qryKpiValue();

    public  void modifyMeasureData(MeasureDataEntity measureDataEntity);
    public  void kdOperate(MeasureDataEntity measureDataEntity);

    public TruckFuelUnloadEntity qryTruckFuelUnload(TruckFuelUnloadEntity truckFuelUnloadEntity);

    public TruckFuelEntranceRecordEntity qryTruckFuelEntranceRecord(TruckFuelEntranceRecordEntity truckFuelEntranceRecordEntity);

    public GridModel qrySampleRptList(SampleRptEntity SampleRptEntity);
    public GridModel qryCarWeightLocal(SampleRptEntity SampleRptEntity);

    public GridModel qrySampleRptList4BB(SampleRptEntity SampleRptEntity);

    public GridModel qrySampleRptList4Hf(SampleRptEntity SampleRptEntity);

    public GridModel qryMergeCarBatchList(BatchNoInfoEntity batchNoInfoEntity);

    public GridModel qrySamplingRptList(SamplingRptEntity SamplingRptEntity);
    public GridModel qrySamplingRptList4ZJ(SamplingRptEntity SamplingRptEntity);
    public GridModel qrySamplingRptList4BB(SamplingRptEntity SamplingRptEntity);

    public GridModel qrySamplingRptList4CCY(SamplingRptEntity SamplingRptEntity);

    public GridModel qrySamplingRptList4JJ(SamplingRptEntity SamplingRptEntity);

    public GridModel qrySamplingRptList4Hpn(SamplingRptEntity SamplingRptEntity);
    //制样结果查询，谏壁使用
    public GridModel qrySamplingRptList4JB(SamplingRptEntity SamplingRptEntity);

    public GridModel qrySamplingRptListView(SamplingRptEntity SamplingRptEntity);

    public GridModel qrySampleBarrelRptList(SampleRptEntity sampleRptEntity);

    public String qrySampleReport(String sampleCode);

    public String qrySamplingReport(String samplingCode);

    //制样结果查询，聊城使用
    public GridModel qrySamplingRptList4LC(SamplingRptEntity SamplingRptEntity);

    public GridModel qrySamplingRptList4HF(SamplingRptEntity SamplingRptEntity);
    //
    public void setArguementBatch(SamplingRptEntity samplingRptEntity);

    //查询人工制样记录，add by xxs 20180106
    public GridModel qryManualSamplingList(ManualSamplingRecordEntity manualSamplingRecordEntity);

    //查询人工制样记录，add by xxs 20180106
    public void addManualSamplingRecInfo(ManualSamplingRecordEntity manualSamplingRecordEntity);

    //查询人工采样记录，add by xxs 20180302
    public GridModel qryManualSampleList(ManualSampleRecordEntity manualSampleRecordEntity);

    //查询人工采样记录，add by xxs 20180302
    public void addManualSampleRecInfo(ManualSampleRecordEntity manualSampleRecordEntity);

    //查询抽查批次
    public GridModel qryCompareBatchInfoList(CompareBatchInfoEntity compareBatchInfoEntity);
    //查询批次
    public GridModel qryBatchInfoList(SamplingRptEntity SamplingRptEntity);

    //大武口先提的 厂内倒运的车辆只过重磅 不分批 单独做个统计报表 xxs20180609
    public GridModel qryWeightRptList4CN(WeightRptEntity weightRptEntity);
    public WeightRptEntity qryWeightSummaryRpt4CN(WeightRptEntity weightRptEntity);
    public GridModel qryWeightRptList4CNOther(WeightRptEntity weightRptEntity);
    //宣威 要增加火车按批次统计的页面 xxs20180710
    public GridModel qryWeightRptList4TrainBatch(WeightRptEntity weightRptEntity);

    public GridModel qryAutoRateRptList(SampleRptEntity sampleRptEntity);

    public GridModel qryCheckBatchRptList(SamplingRptEntity SamplingRptEntity);
	
	//大武口查询新旧两套皮带秤示数
    public GridModel qryBeltWeightRptList(BeltWeightEntity beltWeightEntity);

    //大武口查询汽车采样明细
    public GridModel qrySampleDetailRptList(SampleRptEntity sampleRptEntity);

    //查询采样方案评价
    public List<Map<String,Object>> qrySampleEvaluation(Map<String,Object> paramMap);

    //谏壁采样结果查询
    public GridModel qrySampleRptList4JB(SampleRptEntity SampleRptEntity);

    public GridModel qrySamplingRptList4KC(SamplingRptEntity SamplingRptEntity);

    //衡丰，查询投运率记录
    public GridModel qryUploadRateRptList(UploadRateEntity uploadRateEntity);

    void updateSampleCnt(SampleRptEntity sampleRptEntity);
}
