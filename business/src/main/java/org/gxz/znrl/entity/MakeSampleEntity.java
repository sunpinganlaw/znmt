package org.gxz.znrl.entity;

/**
 * Created by xieyt on 14-12-19.
 */
public class MakeSampleEntity {
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

    private String sampleCode;
    private String  packCode3;// --3mm封装码
    private String  packCode21; //--0.2mm备查样封装码
    private String  packCode22; //--0.2mm化验样封装码
    private String  packCode6; //--6mm全水样封装码

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

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getPackCode3() {
        return packCode3;
    }

    public void setPackCode3(String packCode3) {
        this.packCode3 = packCode3;
    }

    public String getPackCode21() {
        return packCode21;
    }

    public void setPackCode21(String packCode21) {
        this.packCode21 = packCode21;
    }

    public String getPackCode22() {
        return packCode22;
    }

    public void setPackCode22(String packCode22) {
        this.packCode22 = packCode22;
    }

    public String getPackCode6() {
        return packCode6;
    }

    public void setPackCode6(String packCode6) {
        this.packCode6 = packCode6;
    }
}
