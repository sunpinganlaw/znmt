package org.gxz.znrl.entity;

/**
 * Created by xuxs on 2018/1/6.
 * 人工制样记录单
 */
public class ManualSampleRecordEntity extends BaseEntity {

    private String batchNo;
    private String sampleCode;
    private String venNo;
    private String venName;
    private String mineNo;
    private String mineName;
    private String coalNo;
    private String coalName;
    private String carCnt;
    private String allNetQty;
    private String nominalMaxSize;
    private String sampleArea;
    private String sampleCnt;
    private String sampleWeight;
    private String allSampleWeight;
    private String sampleMjcode;
    private String sampleMjname;
    private String sampleOpcode;
    private String sampleOpname;
    private String sampleDate;
    private String samplePlace;

    private String remark;
    private String resCode;
    private String resMsg;
    private String manualSampleId;
    private String startTime;
    private String endTime;
    private String id;

    private String opCode;
    private String opName;
    private String updateCode;
    private String updateName;
    private String opTime;
    private String updateTime;

    public String doActionTag;//动作标识 ADD, MOD
    public String jsonString;//json字符串


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

    public String getCarCnt() {
        return carCnt;
    }

    public void setCarCnt(String carCnt) {
        this.carCnt = carCnt;
    }

    public String getAllNetQty() {
        return allNetQty;
    }

    public void setAllNetQty(String allNetQty) {
        this.allNetQty = allNetQty;
    }

    public String getNominalMaxSize() {
        return nominalMaxSize;
    }

    public void setNominalMaxSize(String nominalMaxSize) {
        this.nominalMaxSize = nominalMaxSize;
    }

    public String getSampleArea() {
        return sampleArea;
    }

    public void setSampleArea(String sampleArea) {
        this.sampleArea = sampleArea;
    }

    public String getSampleCnt() {
        return sampleCnt;
    }

    public void setSampleCnt(String sampleCnt) {
        this.sampleCnt = sampleCnt;
    }

    public String getSampleWeight() {
        return sampleWeight;
    }

    public void setSampleWeight(String sampleWeight) {
        this.sampleWeight = sampleWeight;
    }

    public String getSampleMjcode() {
        return sampleMjcode;
    }

    public void setSampleMjcode(String sampleMjcode) {
        this.sampleMjcode = sampleMjcode;
    }

    public String getSampleMjname() {
        return sampleMjname;
    }

    public void setSampleMjname(String sampleMjname) {
        this.sampleMjname = sampleMjname;
    }

    public String getSampleOpcode() {
        return sampleOpcode;
    }

    public void setSampleOpcode(String sampleOpcode) {
        this.sampleOpcode = sampleOpcode;
    }

    public String getSampleOpname() {
        return sampleOpname;
    }

    public void setSampleOpname(String sampleOpname) {
        this.sampleOpname = sampleOpname;
    }

    public String getSampleDate() {
        return sampleDate;
    }

    public void setSampleDate(String sampleDate) {
        this.sampleDate = sampleDate;
    }

    public String getAllSampleWeight() {
        return allSampleWeight;
    }

    public void setAllSampleWeight(String allSampleWeight) {
        this.allSampleWeight = allSampleWeight;
    }

    public String getSamplePlace() {
        return samplePlace;
    }

    public void setSamplePlace(String samplePlace) {
        this.samplePlace = samplePlace;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getManualSampleId() {
        return manualSampleId;
    }

    public void setManualSampleId(String manualSampleId) {
        this.manualSampleId = manualSampleId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getUpdateCode() {
        return updateCode;
    }

    public void setUpdateCode(String updateCode) {
        this.updateCode = updateCode;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDoActionTag() {
        return doActionTag;
    }

    public void setDoActionTag(String doActionTag) {
        this.doActionTag = doActionTag;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}
