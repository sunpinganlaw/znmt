package org.gxz.znrl.entity;

/**
 * Created by admin-rubbissh on 2015/1/13.
 */
public class TrainMakeSampleEntity {
    public String batchNo;//批次号
    public String samplingCode;//制样编码
    public String packCode;//封装码
    public String zyWeight;//来样重量
    public String zyType;//制样方式
    public String sampleType;//采样类型
    public String startTime;//开始时间
    public String endTime;//结束时间
    public String sampleWeight;//出样重量
    public String sampleUserName;//制样人

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSamplingCode() {
        return samplingCode;
    }

    public void setSamplingCode(String samplingCode) {
        this.samplingCode = samplingCode;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    public String getZyWeight() {
        return zyWeight;
    }

    public void setZyWeight(String zyWeight) {
        this.zyWeight = zyWeight;
    }

    public String getZyType() {
        return zyType;
    }

    public void setZyType(String zyType) {
        this.zyType = zyType;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
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

    public String getSampleWeight() {
        return sampleWeight;
    }

    public void setSampleWeight(String sampleWeight) {
        this.sampleWeight = sampleWeight;
    }

    public String getSampleUserName() {
        return sampleUserName;
    }

    public void setSampleUserName(String sampleUserName) {
        this.sampleUserName = sampleUserName;
    }
}
