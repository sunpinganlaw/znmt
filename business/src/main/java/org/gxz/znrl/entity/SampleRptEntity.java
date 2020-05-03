package org.gxz.znrl.entity;

/**
 * Created by rubish on 2015/4/17.
 * 采样报表查询
 */
public class SampleRptEntity extends BaseEntity {
    private String sampleCode;

    private String samplingCode;

    private String startTime;

    private String endTime;

    private String coalNo;

    private String coalName;

    private String sampleType;//采样方式名称

    private String carNum;

    private String carId;

    private String batchNo;

    private String batchNoType;
    private String batchTypeName;

    private String samplePoints;

    private String sampleCnt;

    private String totalNetQty;

    private String allNetQty;
    private String venNo;

    private String venName;

    private String mineNo;

    private String mineName;

    private String colryNo;

    private String carCnt;

    private String opCode;

    private String sampleTyp;//采样方式编码

    private String userCode;

    private String jsonString;

    private String resCode;

    private String resMsg;

    private String sampleQty;

    private String machineCode;

    private String suofenCnt;

    private String sampleCntNew;

    private String usageRate;//北仑采样机投运率

    private String blNeed;//北仑需要按批次统计的采样机投运率

    private String sampleFrequence;//从江南需求增加而来(采样间隔)

    private String sampleGape;//从江南需求增加而来(缩分间隔)
    private String remark;

    private String barrelCode;

    private String coordinate;
	
	private String userName;

    private String barrelCnt;

    private String batchCnt;
    private String autoSampleBatchCnt;
    private String manualSampleBatchCnt;
    private String autoSampleRate;
    private String autoSamplingBatchCnt;
    private String manualSamplingBatchCnt;
    private String autoSamplingRate;
    private String operType;
    private String batchTime;
    private String coalType;
    private String recordNo;

    private String boilerNo;
    private String goodsReceiver;
    private String bc;
    private String sampleSize;
    private String balanceNo;
    private String weightTime;
    private String weight;
    private String barrelId;
    private String teamNo;//班次
    private String classNo;//值次

    public String getBarrelId() {
        return barrelId;
    }

    public void setBarrelId(String barrelId) {
        this.barrelId = barrelId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeightTime() {
        return weightTime;
    }

    public void setWeightTime(String weightTime) {
        this.weightTime = weightTime;
    }

    public String getBalanceNo() {
        return balanceNo;
    }

    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getCoalType() {
        return coalType;
    }

    public void setCoalType(String coalType) {
        this.coalType = coalType;
    }

    public String getBatchTime() {
        return batchTime;
    }

    public void setBatchTime(String batchTime) {
        this.batchTime = batchTime;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getBatchCnt() {
        return batchCnt;
    }

    public void setBatchCnt(String batchCnt) {
        this.batchCnt = batchCnt;
    }

    public String getAutoSampleBatchCnt() {
        return autoSampleBatchCnt;
    }

    public void setAutoSampleBatchCnt(String autoSampleBatchCnt) {
        this.autoSampleBatchCnt = autoSampleBatchCnt;
    }

    public String getManualSampleBatchCnt() {
        return manualSampleBatchCnt;
    }

    public void setManualSampleBatchCnt(String manualSampleBatchCnt) {
        this.manualSampleBatchCnt = manualSampleBatchCnt;
    }

    public String getAutoSampleRate() {
        return autoSampleRate;
    }

    public void setAutoSampleRate(String autoSampleRate) {
        this.autoSampleRate = autoSampleRate;
    }

    public String getAutoSamplingBatchCnt() {
        return autoSamplingBatchCnt;
    }

    public void setAutoSamplingBatchCnt(String autoSamplingBatchCnt) {
        this.autoSamplingBatchCnt = autoSamplingBatchCnt;
    }

    public String getManualSamplingBatchCnt() {
        return manualSamplingBatchCnt;
    }

    public void setManualSamplingBatchCnt(String manualSamplingBatchCnt) {
        this.manualSamplingBatchCnt = manualSamplingBatchCnt;
    }

    public String getAutoSamplingRate() {
        return autoSamplingRate;
    }

    public void setAutoSamplingRate(String autoSamplingRate) {
        this.autoSamplingRate = autoSamplingRate;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getBarrelCode() {
        return barrelCode;
    }

    public void setBarrelCode(String barrelCode) {
        this.barrelCode = barrelCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSampleGape() {
        return sampleGape;
    }

    public void setSampleGape(String sampleGape) {
        this.sampleGape = sampleGape;
    }



    public String getSampleFrequence() {
        return sampleFrequence;
    }

    public void setSampleFrequence(String sampleFrequence) {
        this.sampleFrequence = sampleFrequence;
    }



    public String getSampleCntNew() {
        return sampleCntNew;
    }

    public void setSampleCntNew(String sampleCntNew) {
        this.sampleCntNew = sampleCntNew;
    }


    public String getSuofenCnt() {
        return suofenCnt;
    }

    public void setSuofenCnt(String suofenCnt) {
        this.suofenCnt = suofenCnt;
    }


    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getCarCnt() {
        return carCnt;
    }

    public void setCarCnt(String carCnt) {
        this.carCnt = carCnt;
    }

    public String getBatchTypeName() {
        return batchTypeName;
    }

    public void setBatchTypeName(String batchTypeName) {
        this.batchTypeName = batchTypeName;
    }

    public String getCoalName() {
        return coalName;
    }

    public void setCoalName(String coalName) {
        this.coalName = coalName;
    }

    public String getVenName() {
        return venName;
    }

    public void setVenName(String venName) {
        this.venName = venName;
    }

    public String getMineNo() {
        return mineNo;
    }

    public void setMineNo(String mineNo) {
        this.mineNo = mineNo;
    }

    public String getMineName() {
        return mineName;
    }

    public void setMineName(String mineName) {
        this.mineName = mineName;
    }

    public String getVenNo() {
        return venNo;
    }

    public void setVenNo(String venNo) {
        this.venNo = venNo;
    }

    public String getColryNo() {
        return colryNo;
    }

    public void setColryNo(String colryNo) {
        this.colryNo = colryNo;
    }

    public String getTotalNetQty() {
        return totalNetQty;
    }

    public void setTotalNetQty(String totalNetQty) {
        this.totalNetQty = totalNetQty;
    }

    public String getAllNetQty() {
        return allNetQty;
    }

    public void setAllNetQty(String allNetQty) {
        this.allNetQty = allNetQty;
    }

    public String getSampleCnt() {
        return sampleCnt;
    }

    public void setSampleCnt(String sampleCnt) {
        this.sampleCnt = sampleCnt;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCoalNo() {
        return coalNo;
    }

    public void setCoalNo(String coalNo) {
        this.coalNo = coalNo;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getSamplePoints() {
        return samplePoints;
    }

    public void setSamplePoints(String samplePoints) {
        this.samplePoints = samplePoints;
    }

    public String getBatchNoType() {
        return batchNoType;
    }

    public void setBatchNoType(String batchNoType) {
        this.batchNoType = batchNoType;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getSampleTyp() {
        return sampleTyp;
    }

    public void setSampleTyp(String sampleTyp) {
        this.sampleTyp = sampleTyp;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getSampleQty() {
        return sampleQty;
    }

    public void setSampleQty(String sampleQty) {
        this.sampleQty = sampleQty;
    }

    public String getUsageRate() {
        return usageRate;
    }

    public void setUsageRate(String usageRate) {
        this.usageRate = usageRate;
    }

    public String getBlNeed() {
        return blNeed;
    }

    public void setBlNeed(String blNeed) {
        this.blNeed = blNeed;
    }

    public String getBarrelCnt() {
        return barrelCnt;
    }

    public void setBarrelCnt(String barrelCnt) {
        this.barrelCnt = barrelCnt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSamplingCode() {
        return samplingCode;
    }

    public void setSamplingCode(String samplingCode) {
        this.samplingCode = samplingCode;
    }

    public String getBoilerNo() {
        return boilerNo;
    }

    public void setBoilerNo(String boilerNo) {
        this.boilerNo = boilerNo;
    }

    public String getGoodsReceiver() {
        return goodsReceiver;
    }

    public void setGoodsReceiver(String goodsReceiver) {
        this.goodsReceiver = goodsReceiver;
    }

    public String getBc() {
        return bc;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }

    public String getSampleSize() {
        return sampleSize;
    }

    public void setSampleSize(String sampleSize) {
        this.sampleSize = sampleSize;
    }

    public String getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(String teamNo) {
        this.teamNo = teamNo;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }
}
