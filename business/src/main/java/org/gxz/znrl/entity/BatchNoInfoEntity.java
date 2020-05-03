package org.gxz.znrl.entity;

public class BatchNoInfoEntity extends BaseEntity {
    private String shipRecID;
    private String batchNo;
    private String dayBatchNum;
    private String shipTransNo;//船运编号
    private String venNo;
    private String venName;
    private String mineNo;
    private String mineName;
    private String coalNo;
    private String coalName;
    private String thisBatchQty;
    private String sampleCode;
    private String samplingCode;
    private String laborCode;
    private String batchTime;
    private String carIds;//本批次包含车、船仓号
    private String startTime;//本批次采样开始时间
    private String endTime;//本批次采样结束时间
    private String batchState;//批次状态描述
    private String isValid;//批次状态值
    private String batchStatusName;

    private String allNetQty;

    private String doActionTag;
    private String remark;
    private String opCode;
    private String updateCode;
    private String updateTime;
    private String insertTime;
    private String resCode;
    private String resMsg;
    private String jsonString;
    private String carNum;//大开汽车的车数

    private String allTickQty; //大开汽车的票重
    private String realBatchQty; //江南汽车批次统计 该批次实际来煤量

    private String receiveOp; //接样人
    private String receiveTime; //接样时间

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getAllTickQty() {
        return allTickQty;
    }

    public void setAllTickQty(String allTickQty) {
        this.allTickQty = allTickQty;
    }

    public String getBatchStatusName() {
        return batchStatusName;
    }

    public void setBatchStatusName(String batchStatusName) {
        this.batchStatusName = batchStatusName;
    }

    public String getShipRecID() {
        return shipRecID;
    }

    public void setShipRecID(String shipRecID) {
        this.shipRecID = shipRecID;
    }

    public String getVenNo() {
        return venNo;
    }

    public void setVenNo(String venNo) {
        this.venNo = venNo;
    }

    public String getVenName() {
        return venName;
    }

    public void setVenName(String venName) {
        this.venName = venName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getDayBatchNum() {
        return dayBatchNum;
    }

    public void setDayBatchNum(String dayBatchNum) {
        this.dayBatchNum = dayBatchNum;
    }

    public String getShipTransNo() {
        return shipTransNo;
    }

    public void setShipTransNo(String shipTransNo) {
        this.shipTransNo = shipTransNo;
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

    public String getCoalNo() {
        return coalNo;
    }

    public void setCoalNo(String coalNo) {
        this.coalNo = coalNo;
    }

    public String getCoalName() {
        return coalName;
    }

    public void setCoalName(String coalName) {
        this.coalName = coalName;
    }

    public String getThisBatchQty() {
        return thisBatchQty;
    }

    public void setThisBatchQty(String thisBatchQty) {
        this.thisBatchQty = thisBatchQty;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getSamplingCode() {
        return samplingCode;
    }

    public void setSamplingCode(String samplingCode) {
        this.samplingCode = samplingCode;
    }

    public String getLaborCode() {
        return laborCode;
    }

    public void setLaborCode(String laborCode) {
        this.laborCode = laborCode;
    }

    public String getBatchTime() {
        return batchTime;
    }

    public void setBatchTime(String batchTime) {
        this.batchTime = batchTime;
    }

    public String getCarIds() {
        return carIds;
    }

    public void setCarIds(String carIds) {
        this.carIds = carIds;
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

    public String getBatchState() {
        return batchState;
    }

    public void setBatchState(String batchState) {
        this.batchState = batchState;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getAllNetQty() {
        return allNetQty;
    }

    public void setAllNetQty(String allNetQty) {
        this.allNetQty = allNetQty;
    }

    public String getDoActionTag() {
        return doActionTag;
    }

    public void setDoActionTag(String doActionTag) {
        this.doActionTag = doActionTag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getUpdateCode() {
        return updateCode;
    }

    public void setUpdateCode(String updateCode) {
        this.updateCode = updateCode;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
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

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getRealBatchQty() {
        return realBatchQty;
    }

    public void setRealBatchQty(String realBatchQty) {
        this.realBatchQty = realBatchQty;
    }

    public String getReceiveOp() {
        return receiveOp;
    }

    public void setReceiveOp(String receiveOp) {
        this.receiveOp = receiveOp;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }
}