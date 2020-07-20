package org.gxz.znrl.service.impl;

import org.gxz.znrl.entity.*;
import org.gxz.znrl.mapper.RptMapper;
import org.gxz.znrl.service.IRptService;
import org.gxz.znrl.viewModel.GridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by xieyt on 14-11-18.
 */
@Service("rptService")
@Transactional
@SuppressWarnings("unchecked")
public class RptServiceImpl implements IRptService {

    @Autowired
    public RptMapper rptMapper;

    @Override
    public GridModel qryWeightRptSumList(WeightRptEntity weightRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryWeightRptListSumCnt(weightRptEntity);
        //查询本次页面的结果集
        List<WeightRptEntity> weightRptSumList = rptMapper.qryWeightRptSumList(weightRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(weightRptSumList);
        m.setTotal(totalCnt);

        return m;
    }

    public GridModel qryWeightRptList(WeightRptEntity weightRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryWeightRptListCnt(weightRptEntity);
        //查询本次页面的结果集
        List<WeightRptEntity> weightRptList = rptMapper.qryWeightRptList(weightRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(weightRptList);
        m.setTotal(totalCnt);

        return m;
    }

    //宣威 要增加按批次统计的页面 xxs20180329  zl 20180710
    public GridModel qryWeightRptList4CarBatch(WeightRptEntity weightRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryWeightRptListCnt4CarBatch(weightRptEntity);
        //查询本次页面的结果集
        List<WeightRptEntity> weightRptList = rptMapper.qryWeightRptList4CarBatch(weightRptEntity);
        //设置返回结果
        List<WeightRptEntity> weightRptFootList = rptMapper.qryWeightRptList4CarBatchFoot(weightRptEntity);
        GridModel m = new GridModel();
        m.setRows(weightRptList);
        m.setTotal(totalCnt);
        m.setFooter(weightRptFootList);

        return m;
    }

    public GridModel qryTrainWeightRptList(WeightRptEntity weightRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryTrainWeightRptListCnt(weightRptEntity);
        //查询本次页面的结果集
        List<WeightRptEntity> weightRptList = rptMapper.qryTrainWeightRptList(weightRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(weightRptList);
        m.setTotal(totalCnt);

        return m;
    }

    public GridModel qryWeightList(WeightRptEntity weightRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryWeightListCnt(weightRptEntity);
        //查询本次页面的结果集
        List<WeightRptEntity> weightRptList = rptMapper.qryWeightList(weightRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(weightRptList);
        m.setTotal(totalCnt);

        return m;
    }

    public WeightRptEntity qryWeightSummaryRpt(WeightRptEntity weightRptEntity){
        Integer intEntryTotalCnt = rptMapper.qryEntryTotalCnt(weightRptEntity);
        Integer intWeightTotalCnt = rptMapper.qryWeightTotalCnt(weightRptEntity);
        Integer intLightTotalCnt = rptMapper.qryLightTotalCnt(weightRptEntity);

        String intTotalMz = rptMapper.qryTotalMz(weightRptEntity);
        String intTotalPz = rptMapper.qryTotalPz(weightRptEntity);
        String intTotalNet = rptMapper.qryTotalNet(weightRptEntity);
        String intTotalTick = rptMapper.qryTotalTick(weightRptEntity);

        String intTotalKd = "0";

        String intTotalHpQty = rptMapper.qryTotalHpQty(weightRptEntity);
        String intTotalNoKdNetQty = rptMapper.qryTotalNoKdNetQty(weightRptEntity);
        String intTotalDiffQty = rptMapper.qryTotalDiffQty(weightRptEntity);
        //设置返回结果
        WeightRptEntity w = new WeightRptEntity();
        w.setTotalEntryCnt(intEntryTotalCnt.toString());

        w.setTotalMzQty(intTotalMz);
        w.setTotalPzQty(intTotalPz);
        w.setTotalNetQty(intTotalNet);
        w.setTotalTickQty(intTotalTick);
        w.setTotalKdQty(intTotalKd);

        return w;
    }

    public WeightRptEntity qryTrainWeightSummaryRpt(WeightRptEntity weightRptEntity){
        Integer intEntryTotalCnt = rptMapper.qryTrainEntryTotalCnt(weightRptEntity);
        Integer intWeightTotalCnt = rptMapper.qryTrainWeightTotalCnt(weightRptEntity);

        String intTotalMz = rptMapper.qryTrainTotalMz(weightRptEntity);
        String intTotalPz = rptMapper.qryTrainTotalPz(weightRptEntity);
        String intTotalNet = rptMapper.qryTrainTotalNet(weightRptEntity);

        //设置返回结果
        WeightRptEntity w = new WeightRptEntity();
        w.setTotalEntryCnt(intEntryTotalCnt.toString());

        w.setTotalMzQty(intTotalMz);
        w.setTotalPzQty(intTotalPz);
        w.setTotalNetQty(intTotalNet);
        return w;
    }

    @Override
    public void modifyKdQty(KdQtyEntity kdQtyEntity) {
        KdQtyEntity kdQtyEntityTmp = new KdQtyEntity();
        TruckFuelUnloadEntity truckFuelUnloadEntity = new TruckFuelUnloadEntity();
        truckFuelUnloadEntity.setRecordNo(kdQtyEntity.getRecordNo());
        truckFuelUnloadEntity = rptMapper.qryTruckFuelUnload(truckFuelUnloadEntity);
        if (truckFuelUnloadEntity == null) {
            kdQtyEntityTmp  = rptMapper.qryFuelTruckState(kdQtyEntity);
            kdQtyEntityTmp.setKsQty(kdQtyEntity.getKsQty());
            kdQtyEntityTmp.setKgQty(kdQtyEntity.getKgQty());
            kdQtyEntityTmp.setKdQty(kdQtyEntity.getKdQty());
            rptMapper.addFuelTruckUnload(kdQtyEntityTmp);
            rptMapper.modifyFuelTruckRecord(kdQtyEntity);
            rptMapper.modifyFuelTruckState(kdQtyEntity);
        } else {
            truckFuelUnloadEntity.setKsQty(kdQtyEntity.getKsQty());
            truckFuelUnloadEntity.setKdQty(kdQtyEntity.getKdQty());
            truckFuelUnloadEntity.setKgQty(kdQtyEntity.getKgQty());
            rptMapper.modifyTruckFuelUnload(truckFuelUnloadEntity);
            rptMapper.modifyFuelTruckRecord(kdQtyEntity);
            rptMapper.modifyFuelTruckState(kdQtyEntity);
        }

    }

    @Override
    public void modifyMeasureData(MeasureDataEntity measureDataEntity) {
        rptMapper.modifyMeasureData(measureDataEntity);
    }

    public void kdOperate(MeasureDataEntity measureDataEntity) {
        rptMapper.kdOperate(measureDataEntity);
    }


    @Override
    public TruckFuelUnloadEntity qryTruckFuelUnload(TruckFuelUnloadEntity truckFuelUnloadEntity) {
        return rptMapper.qryTruckFuelUnload(truckFuelUnloadEntity);
    }

    @Override
    public TruckFuelEntranceRecordEntity qryTruckFuelEntranceRecord(TruckFuelEntranceRecordEntity truckFuelEntranceRecordEntity) {
        return rptMapper.qryTruckFuelEntranceRecord(truckFuelEntranceRecordEntity);
    }

    @Override
    public GridModel qrySampleRptList(SampleRptEntity sampleRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qrySampleRptCnt(sampleRptEntity);
        //查询本次页面的结果集
        List<SampleRptEntity> sampleRptEntityList = rptMapper.qrySampleRptList(sampleRptEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(sampleRptEntityList);

        m.setTotal(totalCnt);

        return m;
    }


    @Override
    public GridModel qryCarWeightLocal(SampleRptEntity sampleRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryCarWeightLocalCnt(sampleRptEntity);
        //查询本次页面的结果集
        List<SampleRptEntity> carWeightLocal = rptMapper.qryCarWeightLocal(sampleRptEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(carWeightLocal);

        m.setTotal(totalCnt);

        return m;
    }

    @Override
    public GridModel qrySampleRptList4BB(SampleRptEntity sampleRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qrySampleRptCnt4BB(sampleRptEntity);
        //查询本次页面的结果集
        List<SampleRptEntity> sampleRptEntityList = rptMapper.qrySampleRptList4BB(sampleRptEntity);



//        System.out.println(sampleRptEntityList.get(0).getRemark());

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(sampleRptEntityList);

        m.setTotal(totalCnt);

        return m;
    }

    @Override
    public GridModel qrySampleRptList4Hf(SampleRptEntity sampleRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qrySampleRptCnt4Hf(sampleRptEntity);
        //查询本次页面的结果集
        List<SampleRptEntity> sampleRptEntityList = rptMapper.qrySampleRptList4Hf(sampleRptEntity);
        List<SampleRptEntity> footer = rptMapper.qrySampleRptFooter(sampleRptEntity);

//        System.out.println(sampleRptEntityList.get(0).getRemark());

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(sampleRptEntityList);
        m.setFooter(footer);
        m.setTotal(totalCnt);
        return m;
    }

    @Override
    public String qryKpiValue(){
        //查询总记录数
        //查询本次页面的结果集
        String json = null;
        json = rptMapper.qryKpiValue();
        //设置返回结果
        return json;
    }

    @Override
    public GridModel qryMergeCarBatchList(BatchNoInfoEntity batchNoInfoEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryMergeCarBatchCnt(batchNoInfoEntity);
        //查询本次页面的结果集
        List<BatchNoInfoEntity> batchNoInfoEntityList = rptMapper.qryMergeCarBatchList(batchNoInfoEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(batchNoInfoEntityList);
        m.setTotal(totalCnt);
        return m;
    }

    @Override
    public GridModel qrySamplingRptList(SamplingRptEntity samplingRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qrySamplingRptCnt(samplingRptEntity);
        //查询本次页面的结果集
        List<SamplingRptEntity> samplingRptEntityList = rptMapper.qrySamplingRptList(samplingRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(samplingRptEntityList);
        m.setTotal(totalCnt);
        return m;
    }

    @Override
    public GridModel qrySamplingRptList4ZJ(SamplingRptEntity samplingRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qrySamplingRptCnt(samplingRptEntity);
        //查询本次页面的结果集
        List<SamplingRptEntity> samplingRptEntityList = rptMapper.qrySamplingRptList4ZJ(samplingRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(samplingRptEntityList);
        m.setTotal(totalCnt);
        return m;
    }

    @Override
    public GridModel qrySamplingRptList4BB(SamplingRptEntity samplingRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qrySamplingRptCnt4BB(samplingRptEntity);
        //查询本次页面的结果集
        List<SamplingRptEntity> samplingRptEntityList = rptMapper.qrySamplingRptList4BB(samplingRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(samplingRptEntityList);
        m.setTotal(totalCnt);
        return m;
    }

    @Override
    public GridModel qrySamplingRptList4CCY(SamplingRptEntity samplingRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qrySamplingRptCnt4CCY(samplingRptEntity);
        //查询本次页面的结果集
        List<SamplingRptEntity> samplingRptEntityList = rptMapper.qrySamplingRptList4CCY(samplingRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(samplingRptEntityList);
        m.setTotal(totalCnt);
        return m;
    }

    @Override
    public GridModel qrySamplingRptList4JJ(SamplingRptEntity samplingRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qrySamplingRptCnt(samplingRptEntity);
        //查询本次页面的结果集
        List<SamplingRptEntity> samplingRptEntityList = rptMapper.qrySamplingRptList4JJ(samplingRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(samplingRptEntityList);
        m.setTotal(totalCnt);
        return m;
    }

    //哈平南用
    @Override
    public GridModel qrySamplingRptList4Hpn(SamplingRptEntity samplingRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qrySamplingRptCnt(samplingRptEntity);
        //查询本次页面的结果集
        List<SamplingRptEntity> samplingRptEntityList = rptMapper.qrySamplingRptList4Hpn(samplingRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(samplingRptEntityList);
        m.setTotal(totalCnt);
        return m;
    }

    //制样结果查询，谏壁使用
    @Override
    public GridModel qrySamplingRptList4JB(SamplingRptEntity samplingRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qrySamplingRptCnt4JB(samplingRptEntity);
        //查询本次页面的结果集
        List<SamplingRptEntity> samplingRptEntityList = rptMapper.qrySamplingRptList4JB(samplingRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(samplingRptEntityList);
        m.setTotal(totalCnt);
        return m;
    }

    @Override
    public GridModel qrySamplingRptListView(SamplingRptEntity samplingRptEntity) {
        List<SamplingRptEntity> list;
        list = rptMapper.qrySamplingRptListView(samplingRptEntity);
        GridModel m = new GridModel();
        m.setRows(list);
        return  m;
    }

    @Override
    public GridModel qrySampleBarrelRptList(SampleRptEntity sampleRptEntity){
        //查询总记录数
        Integer totalCnt = rptMapper.qrySampleBarrelRptCnt(sampleRptEntity);
        //查询本次页面的结果集
        List<SampleRptEntity> sampleRptEntityList = rptMapper.qrySampleBarrelRptList(sampleRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(sampleRptEntityList);
        m.setTotal(totalCnt);
        return m;
    }

    //查询采样报告数据
    public String qrySampleReport(String sampleCode){
        SampleRptEntity sampleRptEntity = new SampleRptEntity();
        sampleRptEntity.setSampleCode(sampleCode);
        return rptMapper.qrySampleResultData(sampleRptEntity);
    }

    //查询制样报告数据
    public String qrySamplingReport(String samplingCode){
        SamplingRptEntity samplingRptEntity = new SamplingRptEntity();
        samplingRptEntity.setSamplingCode(samplingCode);
        System.out.println(samplingRptEntity.getSamplingCode());
        System.out.println(samplingRptEntity.getRemark());
        return rptMapper.qrySamplingResultData(samplingRptEntity);
    }

    //聊城制样结果查询
    public GridModel qrySamplingRptList4LC(SamplingRptEntity samplingRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qrySamplingRptCnt(samplingRptEntity);
        //查询本次页面的结果集
        List<SamplingRptEntity> samplingRptEntityList = rptMapper.qrySamplingRptList4LC(samplingRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(samplingRptEntityList);
        m.setTotal(totalCnt);
        return m;
    }

    //衡丰制样结果查询
    public GridModel qrySamplingRptList4HF(SamplingRptEntity samplingRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qrySamplingRptCnt4HF(samplingRptEntity);
        //查询本次页面的结果集
        List<SamplingRptEntity> samplingRptEntityList = rptMapper.qrySamplingRptList4HF(samplingRptEntity);
        List<SamplingRptEntity> footer = rptMapper.qrySamplingRptFooter(samplingRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(samplingRptEntityList);
        m.setFooter(footer);
        m.setTotal(totalCnt);
        return m;
    }

    //提交控制设备命令
    public void setArguementBatch(SamplingRptEntity samplingRptEntity){
        rptMapper.setArguementBatch(samplingRptEntity);

    }

    //人工制样记录 查询 xxs 20180106
    public GridModel qryManualSamplingList(ManualSamplingRecordEntity manualSamplingRecordEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryManualSamplingCnt(manualSamplingRecordEntity);
        //查询本次页面的结果集
        List<ManualSamplingRecordEntity> manualSamplingRecordEntityList = rptMapper.qryManualSamplingList(manualSamplingRecordEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(manualSamplingRecordEntityList);
        m.setTotal(totalCnt);
        return m;
    }

    //人工制样记录 编辑 xxs 20180106
    public void addManualSamplingRecInfo(ManualSamplingRecordEntity manualSamplingRecordEntity){
        rptMapper.addManualSamplingRecInfo(manualSamplingRecordEntity);
    }

    //人工采样记录 查询 xxs 20180302
    public GridModel qryManualSampleList(ManualSampleRecordEntity manualSampleRecordEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryManualSampleCnt(manualSampleRecordEntity);
        //查询本次页面的结果集
        List<ManualSampleRecordEntity> manualSampleRecordEntityList = rptMapper.qryManualSampleList(manualSampleRecordEntity);

        List<ManualSampleRecordEntity> ListFoot = rptMapper.qryManualSampleListFoot(manualSampleRecordEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(manualSampleRecordEntityList);
        m.setFooter(ListFoot);
        m.setTotal(totalCnt);
        return m;
    }

    //人工采样记录 编辑 xxs 20180106
    public void addManualSampleRecInfo(ManualSampleRecordEntity manualSampleRecordEntity){
        rptMapper.addManualSampleRecInfo(manualSampleRecordEntity);
    }

    //查询抽查批次
    public GridModel qryCompareBatchInfoList(CompareBatchInfoEntity compareBatchInfoEntity){
        List<SamplingRptEntity> list = rptMapper.qryCompareBatchInfoList(compareBatchInfoEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //批次查询
    public GridModel qryBatchInfoList(SamplingRptEntity samplingRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryBatchInfoListCnt(samplingRptEntity);
        //查询本次页面的结果集
        List<SamplingRptEntity> samplingRptEntityList = rptMapper.qryBatchInfoList(samplingRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(samplingRptEntityList);
        m.setTotal(totalCnt);
        return m;
    }

    //大武口先提的 厂内倒运的车辆只过重磅 不分批 单独做个统计报表 xxs20180609
    public GridModel qryWeightRptList4CN(WeightRptEntity weightRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryWeightRptListCnt4CN(weightRptEntity);
        //查询本次页面的结果集
        List<WeightRptEntity> weightRptList = rptMapper.qryWeightRptList4CN(weightRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(weightRptList);
        m.setTotal(totalCnt);

        return m;
    }
    //大武口先提的 厂内倒运的车辆只过重磅 不分批 单独做个统计报表 xxs20180609
    public GridModel qryWeightRptList4CNOther(WeightRptEntity weightRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryWeightRptListCnt4CNOther(weightRptEntity);
        //查询本次页面的结果集
        List<WeightRptEntity> weightRptList = rptMapper.qryWeightRptList4CNOther(weightRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(weightRptList);
        m.setTotal(totalCnt);

        return m;
    }

    //大武口先提的 厂内倒运的车辆只过重磅 不分批 单独做个统计报表 xxs20180609
    public WeightRptEntity qryWeightSummaryRpt4CN(WeightRptEntity weightRptEntity){
        Integer intEntryTotalCnt = rptMapper.qryEntryTotalCnt4CN(weightRptEntity);
        Integer intWeightTotalCnt = rptMapper.qryWeightTotalCnt4CN(weightRptEntity);
        Integer intLightTotalCnt = rptMapper.qryLightTotalCnt4CN(weightRptEntity);

        String intTotalMz = rptMapper.qryTotalMz4CN(weightRptEntity);
        String intTotalPz = rptMapper.qryTotalPz4CN(weightRptEntity);
        String intTotalNet = rptMapper.qryTotalNet4CN(weightRptEntity);
        String intTotalTick = rptMapper.qryTotalTick4CN(weightRptEntity);
        String intTotalKd = rptMapper.qryTotalKd4CN(weightRptEntity);
        //设置返回结果
        WeightRptEntity w = new WeightRptEntity();
        w.setTotalEntryCnt(intEntryTotalCnt.toString());

        w.setTotalMzQty(intTotalMz);
        w.setTotalPzQty(intTotalPz);
        w.setTotalNetQty(intTotalNet);
        w.setTotalTickQty(intTotalTick);
        w.setTotalKdQty(intTotalKd);
        return w;
    }



    //大武口先提的 厂内倒运的车辆只过重磅 不分批 单独做个统计报表 xxs20180609
    public GridModel qryAutoRateRptList(SampleRptEntity sampleRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryAutoRateRptCnt(sampleRptEntity);
        //查询本次页面的结果集
        List<SampleRptEntity> autoRateRptList = rptMapper.qryAutoRateRptList(sampleRptEntity);

        List<SampleRptEntity> listFoot = rptMapper.qryAutoRateRptListFoot(sampleRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(autoRateRptList);
        m.setTotal(totalCnt);
        m.setFooter(listFoot);
        return m;
    }

    //宣威 要增加火车按批次统计的页面 zl20180710
    @Override
    public GridModel qryWeightRptList4TrainBatch(WeightRptEntity weightRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryWeightRptListCnt4TrainBatch(weightRptEntity);
        //查询本次页面的结果集
        List<WeightRptEntity> weightRptList = rptMapper.qryWeightRptList4TrainBatch(weightRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(weightRptList);
        m.setTotal(totalCnt);

        return m;
    }

    //    泉州抽查批次需要一张单独的报表
    public GridModel qryCheckBatchRptList(SamplingRptEntity samplingRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryCheckBatchRptCnt(samplingRptEntity);
        //查询本次页面的结果集
        List<SamplingRptEntity> samplingRptEntityList = rptMapper.qryCheckBatchRptList(samplingRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(samplingRptEntityList);
        m.setTotal(totalCnt);
        return m;
    }
	
	 //大武口查询新旧两套皮带秤示数
    public GridModel qryBeltWeightRptList(BeltWeightEntity beltWeightEntity){
        //查询本次页面的结果集
        List<BeltWeightEntity> beltWeightRptList = rptMapper.qryBeltWeightRptList(beltWeightEntity);

        List<BeltWeightEntity> beltWeightFooter = rptMapper.qryBeltWeightFooter(beltWeightEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(beltWeightRptList);
        m.setFooter(beltWeightFooter);

        return m;
    }

    //大武口汽车采样结果明细查询
    public GridModel qrySampleDetailRptList(SampleRptEntity sampleRptEntity){
        //查询总记录数
        Integer totalCnt = rptMapper.qrySampleDetailCnt(sampleRptEntity);
        //查询本次页面的结果集
        List<SampleRptEntity> sampleDetailRptList = rptMapper.qrySampleDetailRptList(sampleRptEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(sampleDetailRptList);
        m.setTotal(totalCnt);

        return m;
    }

    public List<Map<String,Object>> qrySampleEvaluation(Map<String,Object> paramMap){
        //查询本次页面的结果集
        List<Map<String,Object>> list = rptMapper.qrySampleEvaluation(paramMap);
        return list;
    }

    @Override
    public GridModel qrySampleRptList4JB(SampleRptEntity sampleRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qrySampleRptCnt4JB(sampleRptEntity);
        //查询本次页面的结果集
        List<SampleRptEntity> sampleRptEntityList = rptMapper.qrySampleRptList4JB(sampleRptEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(sampleRptEntityList);

        m.setTotal(totalCnt);

        return m;
    }

    //库车用
    @Override
    public GridModel qrySamplingRptList4KC(SamplingRptEntity samplingRptEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qrySamplingRptCnt4KC(samplingRptEntity);
        //查询本次页面的结果集
        List<SamplingRptEntity> samplingRptEntityList = rptMapper.qrySamplingRptList4KC(samplingRptEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(samplingRptEntityList);
        m.setTotal(totalCnt);
        return m;
    }

    //衡丰，查询投运率记录
    @Override
    public GridModel qryUploadRateRptList(UploadRateEntity uploadRateEntity) {
        //查询总记录数
        Integer totalCnt = rptMapper.qryUploadRateRptListCnt(uploadRateEntity);
        //查询本次页面的结果集
        List<UploadRateEntity> uploadRateEntityList = rptMapper.qryUploadRateRptList(uploadRateEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(uploadRateEntityList);

        m.setTotal(totalCnt);

        return m;
    }

    @Override
    public void updateSampleCnt(SampleRptEntity sampleRptEntity) {
        rptMapper.updateSampleCnt(sampleRptEntity);
    }
}
