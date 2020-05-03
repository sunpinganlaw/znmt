package org.gxz.znrl.entity;

/**
 * Created by admin-rubbissh on 2015/1/12.
 */
public class TrainWeightRptEntity extends BaseEntity  {

    public String recordNo;//入厂序号
    public String beginTime; //开始时间
    public String endTime; //结束时间

    public String venNo;//供煤单位ID
    public String venNam;//供煤单位
    public String carrierNo;//运输单位ID
    public String carrierNam;//运输单位
    public String carId;//车牌号
    public String carTyp;//车型编码   0半挂  1外挂
    public String carTypeName;//车型
    public String planMktNo;//计划口径ID ？
    public String planMktName;//计划口径 ？
    public String positionNo;//位置
    public String positionNam;//位置
    public String coalNo;//煤种
    public String coalNam;//煤种名称

    public String bottomHeight;//车底高度
    public String maxLoad;//最大载重
    public String entryOrder;//入厂顺序
    public String doorNo;//入厂位置
    public String recordDtm;//入厂时间
    public String curPos;//车的当前位置
    public String nextPos;//车的下一个位置

    public String smplStartTime;//采样开始时间
    public String smplEndTime;//采样结束时间
    public String machineCode;//采样机
    public String sampleUserName;//采样人

    public String czDtm;//称重时间
    public String jqDtm;//称轻时间
    public String mzQty;//毛重
    public String kdQty;//扣吨
    public String pzQty;//皮重
    public String netQty;//净重
    public String tickQty;//票重
    public String jqBalaNo;//回皮位置
    public String status;//1采样未称重  2称重未称轻  3已经称轻
    public String batchNo;//批次号
    /////////////////////汇总报表数据
    public String totalEntryCnt;//入厂车数
    public String totalCZCnt;//称重车数
    public String totalJQCnt;//称轻车数
    public String totalSampleCnt;//采样车数
    public String totalDenyCnt;//拒收车数
    public String totalCheckInCnt;//验收车数
    public String totalCheckInTunCnt;//回皮位置

    public String totalMzQty;//总毛重
    public String totalKdQty;//总扣吨
    public String totalPzQty;//总皮重
    public String totalNetQty;//总净重


    public String indicatorName;//指标名称
    public String yesterdayValue;//昨日值
    public String thisMonthValue;//当月至
    public String thisYearValue;//当年值

    public String sampleCode;//采样编码
    public String fcjCode;//翻车机号
    public String carNum;//车数
    public String enptyNum;//空车数
    public String netQtyNum;//已卸
    public String sampleTyp;//采样类型
    public String sampleTag;//采样状态
    public String sampleingTag;//制样状态


    public String takeSampleRecId;//采样记录ID
    public String packCode;//封装码


    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getFcjCode() {
        return fcjCode;
    }

    public void setFcjCode(String fcjCode) {
        this.fcjCode = fcjCode;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getEnptyNum() {
        return enptyNum;
    }

    public void setEnptyNum(String enptyNum) {
        this.enptyNum = enptyNum;
    }

    public String getNetQtyNum() {
        return netQtyNum;
    }

    public void setNetQtyNum(String netQtyNum) {
        this.netQtyNum = netQtyNum;
    }

    public String getSampleTyp() {
        return sampleTyp;
    }

    public void setSampleTyp(String sampleTyp) {
        this.sampleTyp = sampleTyp;
    }

    public String getSampleTag() {
        return sampleTag;
    }

    public void setSampleTag(String sampleTag) {
        this.sampleTag = sampleTag;
    }

    public String getSampleingTag() {
        return sampleingTag;
    }

    public void setSampleingTag(String sampleingTag) {
        this.sampleingTag = sampleingTag;
    }

    public String getTickQty() {
        return tickQty;
    }

    public void setTickQty(String tickQty) {
        this.tickQty = tickQty;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getYesterdayValue() {
        return yesterdayValue;
    }

    public void setYesterdayValue(String yesterdayValue) {
        this.yesterdayValue = yesterdayValue;
    }

    public String getThisMonthValue() {
        return thisMonthValue;
    }

    public void setThisMonthValue(String thisMonthValue) {
        this.thisMonthValue = thisMonthValue;
    }

    public String getThisYearValue() {
        return thisYearValue;
    }

    public void setThisYearValue(String thisYearValue) {
        this.thisYearValue = thisYearValue;
    }

    public String getCurPos() {
        return curPos;
    }

    public void setCurPos(String curPos) {
        this.curPos = curPos;
    }

    public String getNextPos() {
        return nextPos;
    }

    public void setNextPos(String nextPos) {
        this.nextPos = nextPos;
    }

    public String getSmplStartTime() {
        return smplStartTime;
    }

    public void setSmplStartTime(String smplStartTime) {
        this.smplStartTime = smplStartTime;
    }

    public String getSmplEndTime() {
        return smplEndTime;
    }

    public void setSmplEndTime(String smplEndTime) {
        this.smplEndTime = smplEndTime;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getSampleUserName() {
        return sampleUserName;
    }

    public void setSampleUserName(String sampleUserName) {
        this.sampleUserName = sampleUserName;
    }

    public String getTotalMzQty() {
        return totalMzQty;
    }

    public void setTotalMzQty(String totalMzQty) {
        this.totalMzQty = totalMzQty;
    }

    public String getTotalKdQty() {
        return totalKdQty;
    }

    public void setTotalKdQty(String totalKdQty) {
        this.totalKdQty = totalKdQty;
    }

    public String getTotalPzQty() {
        return totalPzQty;
    }

    public void setTotalPzQty(String totalPzQty) {
        this.totalPzQty = totalPzQty;
    }

    public String getTotalNetQty() {
        return totalNetQty;
    }

    public void setTotalNetQty(String totalNetQty) {
        this.totalNetQty = totalNetQty;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalEntryCnt() {
        return totalEntryCnt;
    }

    public void setTotalEntryCnt(String totalEntryCnt) {
        this.totalEntryCnt = totalEntryCnt;
    }

    public String getTotalCZCnt() {
        return totalCZCnt;
    }

    public void setTotalCZCnt(String totalCZCnt) {
        this.totalCZCnt = totalCZCnt;
    }

    public String getTotalJQCnt() {
        return totalJQCnt;
    }

    public void setTotalJQCnt(String totalJQCnt) {
        this.totalJQCnt = totalJQCnt;
    }

    public String getTotalSampleCnt() {
        return totalSampleCnt;
    }

    public void setTotalSampleCnt(String totalSampleCnt) {
        this.totalSampleCnt = totalSampleCnt;
    }

    public String getTotalDenyCnt() {
        return totalDenyCnt;
    }

    public void setTotalDenyCnt(String totalDenyCnt) {
        this.totalDenyCnt = totalDenyCnt;
    }

    public String getTotalCheckInCnt() {
        return totalCheckInCnt;
    }

    public void setTotalCheckInCnt(String totalCheckInCnt) {
        this.totalCheckInCnt = totalCheckInCnt;
    }

    public String getTotalCheckInTunCnt() {
        return totalCheckInTunCnt;
    }

    public void setTotalCheckInTunCnt(String totalCheckInTunCnt) {
        this.totalCheckInTunCnt = totalCheckInTunCnt;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getVenNo() {
        return venNo;
    }

    public void setVenNo(String venNo) {
        this.venNo = venNo;
    }

    public String getVenNam() {
        return venNam;
    }

    public void setVenNam(String venNam) {
        this.venNam = venNam;
    }

    public String getCarrierNo() {
        return carrierNo;
    }

    public void setCarrierNo(String carrierNo) {
        this.carrierNo = carrierNo;
    }

    public String getCarrierNam() {
        return carrierNam;
    }

    public void setCarrierNam(String carrierNam) {
        this.carrierNam = carrierNam;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarTyp() {
        return carTyp;
    }

    public void setCarTyp(String carTyp) {
        this.carTyp = carTyp;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getPlanMktNo() {
        return planMktNo;
    }

    public void setPlanMktNo(String planMktNo) {
        this.planMktNo = planMktNo;
    }

    public String getPlanMktName() {
        return planMktName;
    }

    public void setPlanMktName(String planMktName) {
        this.planMktName = planMktName;
    }

    public String getPositionNo() {
        return positionNo;
    }

    public void setPositionNo(String positionNo) {
        this.positionNo = positionNo;
    }

    public String getPositionNam() {
        return positionNam;
    }

    public void setPositionNam(String positionNam) {
        this.positionNam = positionNam;
    }

    public String getCoalNo() {
        return coalNo;
    }

    public void setCoalNo(String coalNo) {
        this.coalNo = coalNo;
    }

    public String getCoalNam() {
        return coalNam;
    }

    public void setCoalNam(String coalNam) {
        this.coalNam = coalNam;
    }

    public String getBottomHeight() {
        return bottomHeight;
    }

    public void setBottomHeight(String bottomHeight) {
        this.bottomHeight = bottomHeight;
    }

    public String getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(String maxLoad) {
        this.maxLoad = maxLoad;
    }

    public String getEntryOrder() {
        return entryOrder;
    }

    public void setEntryOrder(String entryOrder) {
        this.entryOrder = entryOrder;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getRecordDtm() {
        return recordDtm;
    }

    public void setRecordDtm(String recordDtm) {
        this.recordDtm = recordDtm;
    }

    public String getCzDtm() {
        return czDtm;
    }

    public void setCzDtm(String czDtm) {
        this.czDtm = czDtm;
    }

    public String getJqDtm() {
        return jqDtm;
    }

    public void setJqDtm(String jqDtm) {
        this.jqDtm = jqDtm;
    }

    public String getMzQty() {
        return mzQty;
    }

    public void setMzQty(String mzQty) {
        this.mzQty = mzQty;
    }

    public String getKdQty() {
        return kdQty;
    }

    public void setKdQty(String kdQty) {
        this.kdQty = kdQty;
    }

    public String getPzQty() {
        return pzQty;
    }

    public void setPzQty(String pzQty) {
        this.pzQty = pzQty;
    }

    public String getNetQty() {
        return netQty;
    }

    public void setNetQty(String netQty) {
        this.netQty = netQty;
    }

    public String getJqBalaNo() {
        return jqBalaNo;
    }

    public void setJqBalaNo(String jqBalaNo) {
        this.jqBalaNo = jqBalaNo;
    }
}
