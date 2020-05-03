package org.gxz.znrl.entity;

public class  UploadRateEntity extends BaseEntity  {
    public String batchNo;
    public String sampleType;
    public String sampleTypeName;
    public String zyType;
    public String zyTypeName;
    public String insertTime;
    public String beginTime;
    public String endTime;
    public String updateTime;
    public String remark;
    public String jsonStr;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public String getZyType() {
        return zyType;
    }

    public void setZyType(String zyType) {
        this.zyType = zyType;
    }

    public String getZyTypeName() {
        return zyTypeName;
    }

    public void setZyTypeName(String zyTypeName) {
        this.zyTypeName = zyTypeName;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}
