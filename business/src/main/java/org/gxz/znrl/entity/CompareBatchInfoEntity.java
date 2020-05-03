package org.gxz.znrl.entity;

public class CompareBatchInfoEntity extends BaseEntity {
    private String relaId;
    private String batchNo;
    private String sampleCode;
    private String samplingCode;
    private String laborCode;

    private String relaBatchNo;
    private String relaSampleCode;
    private String relaSamplingCode;
    private String relaLaborCode;

    private String relaType;
    private String relaSeq;
    private String status;

    private String startTime;//本批次采样开始时间
    private String endTime;//本批次采样结束时间


    private String doActionTag;
    private String remark;
    private String opCode;
    private String updateCode;
    private String updateTime;
    private String insertTime;
    private String resCode;
    private String resMsg;
    private String jsonString;

    public String getRelaId(){ return relaId; }

    public void setRelaId(String relaId) { this.relaId = relaId; }

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

    public String getRelaBatchNo() {
        return relaBatchNo;
    }

    public void setRelaBatchNo(String relaBatchNo) {
        this.relaBatchNo = relaBatchNo;
    }

    public String getRelaSampleCode() {
        return relaSampleCode;
    }

    public void setRelaSampleCode(String relaSampleCode) {
        this.relaSampleCode = relaSampleCode;
    }

    public String getRelaSamplingCode() {
        return relaSamplingCode;
    }

    public void setRelaSamplingCode(String relaSamplingCode) {
        this.relaSamplingCode = relaSamplingCode;
    }

    public String getRelaLaborCode() {
        return relaLaborCode;
    }

    public void setRelaLaborCode(String relaLaborCode) {
        this.relaLaborCode = relaLaborCode;
    }

    public String getRelaSeq() {
        return relaSeq;
    }

    public void setRelaSeq(String relaSeq) {
        this.relaSeq = relaSeq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRelaType() {
        return relaType;
    }

    public void setRelaType(String relaType) {
        this.relaType = relaType;
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

}