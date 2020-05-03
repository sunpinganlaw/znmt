package org.gxz.znrl.entity;

/**
 * Created by xieyt on 15-8-20.
 */
public class YGCabinetEntity {
    public String sampleStoreId;
    public String samplingRecId;
    public String sampleType;
    public String batchNoType;
    public String sampleTypeName;
    public String samplingCode;
    public String capCode;
    public String bottomCode;
    public String plainCode;
    public String samplingTime;
    public String storeTime;
    public String storeBeginTime;
    public String storeEndTime;
    public String endTime;
    public String sampleStatus;
    public String remark;
    public String sampleStatusName;
    public String storeDates;

    public String apprKeyId;
    public String reqXml;
    public String opRecId;
    public String opType;
    public String staffId;
    public String destination;

    //样品存储信息
    public String totalCnt;//总仓位数
    public String usedCnt;//已使用仓位数
    public String leftCnt;//剩余仓位数
    public String exceptCnt;//异常仓位数
    public String sixMMCnt;//6mm样数量
    public String threeCnt;//3mm样数量
    public String zero2LabCnt;//0.2mm化验样
    public String zero2StoCnt;//0.2mm存查样

    public String resCode;
    public String resMsg;

    private String sampleLimitedTime;
    private String limitedTimeCnt;

    public String getBatchNoType() {
        return batchNoType;
    }

    public void setBatchNoType(String batchNoType) {
        this.batchNoType = batchNoType;
    }

    public String getLimitedTimeCnt() {
        return limitedTimeCnt;
    }

    public void setLimitedTimeCnt(String limitedTimeCnt) {
        this.limitedTimeCnt = limitedTimeCnt;
    }

    public String getSampleLimitedTime() {
        return sampleLimitedTime;
    }

    public void setSampleLimitedTime(String sampleLimitedTime) {
        this.sampleLimitedTime = sampleLimitedTime;
    }

    public String getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(String totalCnt) {
        this.totalCnt = totalCnt;
    }

    public String getUsedCnt() {
        return usedCnt;
    }

    public void setUsedCnt(String usedCnt) {
        this.usedCnt = usedCnt;
    }

    public String getLeftCnt() {
        return leftCnt;
    }

    public void setLeftCnt(String leftCnt) {
        this.leftCnt = leftCnt;
    }

    public String getExceptCnt() {
        return exceptCnt;
    }

    public void setExceptCnt(String exceptCnt) {
        this.exceptCnt = exceptCnt;
    }

    public String getSixMMCnt() {
        return sixMMCnt;
    }

    public void setSixMMCnt(String sixMMCnt) {
        this.sixMMCnt = sixMMCnt;
    }

    public String getThreeCnt() {
        return threeCnt;
    }

    public void setThreeCnt(String threeCnt) {
        this.threeCnt = threeCnt;
    }

    public String getZero2LabCnt() {
        return zero2LabCnt;
    }

    public void setZero2LabCnt(String zero2LabCnt) {
        this.zero2LabCnt = zero2LabCnt;
    }

    public String getZero2StoCnt() {
        return zero2StoCnt;
    }

    public void setZero2StoCnt(String zero2StoCnt) {
        this.zero2StoCnt = zero2StoCnt;
    }

    public String getOpRecId() {
        return opRecId;
    }

    public void setOpRecId(String opRecId) {
        this.opRecId = opRecId;
    }

    public String getReqXml() {
        return reqXml;
    }

    public void setReqXml(String reqXml) {
        this.reqXml = reqXml;
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

    public String getApprKeyId() {
        return apprKeyId;
    }

    public void setApprKeyId(String apprKeyId) {
        this.apprKeyId = apprKeyId;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public String getSampleStatusName() {
        return sampleStatusName;
    }

    public void setSampleStatusName(String sampleStatusName) {
        this.sampleStatusName = sampleStatusName;
    }

    public String getStoreDates() {
        return storeDates;
    }

    public void setStoreDates(String storeDates) {
        this.storeDates = storeDates;
    }

    public String getStoreBeginTime() {
        return storeBeginTime;
    }

    public void setStoreBeginTime(String storeBeginTime) {
        this.storeBeginTime = storeBeginTime;
    }

    public String getStoreEndTime() {
        return storeEndTime;
    }

    public void setStoreEndTime(String storeEndTime) {
        this.storeEndTime = storeEndTime;
    }

    public String getSampleStoreId() {
        return sampleStoreId;
    }

    public void setSampleStoreId(String sampleStoreId) {
        this.sampleStoreId = sampleStoreId;
    }

    public String getSamplingRecId() {
        return samplingRecId;
    }

    public void setSamplingRecId(String samplingRecId) {
        this.samplingRecId = samplingRecId;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSamplingCode() {
        return samplingCode;
    }

    public void setSamplingCode(String samplingCode) {
        this.samplingCode = samplingCode;
    }

    public String getCapCode() {
        return capCode;
    }

    public void setCapCode(String capCode) {
        this.capCode = capCode;
    }

    public String getBottomCode() {
        return bottomCode;
    }

    public void setBottomCode(String bottomCode) {
        this.bottomCode = bottomCode;
    }

    public String getPlainCode() {
        return plainCode;
    }

    public void setPlainCode(String plainCode) {
        this.plainCode = plainCode;
    }

    public String getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(String samplingTime) {
        this.samplingTime = samplingTime;
    }

    public String getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(String storeTime) {
        this.storeTime = storeTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
