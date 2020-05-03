package org.gxz.znrl.entity;

/**
 * Created by xieyt on 15-6-5.
 */
public class SEntity {
    /////////////各个化验都有的
    public String sResId;
    public String laborCode;
    public String deviceNo;
    public String deviceName;
    public String ggNo;
    public String temperature;//温度
    public String humidity;//湿度
    public String opName;
    public String opTime;
    public String opDate;
    public String opCode;
    public String updateTime;
    public String updateCode;
    public String confirmTime;
    public String confirmCode;
    public String insertTime;
    public String hyDate;
    public String beginDate;
    public String endDate;
    public String batchType;
    public String lossStyle;
    public String otherStatusName;
    ////////////////////////////各个化验不同的
    public String samplingName;//试样名称
    public String standard;
    public String samplingWeight;//试样重量
    public String mad;//空干基水分
    public String stAd;//空干基全硫
    public String stD;//干基全硫
    public String ccVal;//超差
    public String jzStyle;//校正方式
    public String totalTime;//总时间
    public String status;
    public String ccStatus;
    public String statusName;
    public String proportion;//加权值
    public String proportionType;//加权类型

    ////////////////////////////////////////
    public String id;
    public String updateString;
    public String dataType;
    public String resCode;
    public String resMsg;
    public String apprEventTypeCd;
    public String labReportId;
    public String shipRecID;
    public String st;
    public String crc;
    public String had;
    private String reportTag;
    private String jsonStr;
    private String idsArray;
    private String indexType;
    private String indexValue;
    private String remark;

    private String sampleStatus;
    private String mtSampleSize;
    private String labSampleSize;
    private String size;
    private String usage;

    /////////////////////////////////////

    public String getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    public String getMtSampleSize() {
        return mtSampleSize;
    }

    public void setMtSampleSize(String mtSampleSize) {
        this.mtSampleSize = mtSampleSize;
    }

    public String getLabSampleSize() {
        return labSampleSize;
    }

    public void setLabSampleSize(String labSampleSize) {
        this.labSampleSize = labSampleSize;
    }

    public String getHad() {
        return had;
    }

    public void setHad(String had) {
        this.had = had;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIdsArray() {
        return idsArray;
    }

    public void setIdsArray(String idsArray) {
        this.idsArray = idsArray;
    }

    public String getOpDate() {
        return opDate;
    }

    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    public String getHyDate() {
        return hyDate;
    }

    public void setHyDate(String hyDate) {
        this.hyDate = hyDate;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(String indexValue) {
        this.indexValue = indexValue;
    }

    public String getCcStatus() {
        return ccStatus;
    }

    public void setCcStatus(String ccStatus) {
        this.ccStatus = ccStatus;
    }

    public String getProportionType() {
        return proportionType;
    }

    public void setProportionType(String proportionType) {
        this.proportionType = proportionType;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    public String getLabReportId() {
        return labReportId;
    }

    public void setLabReportId(String labReportId) {
        this.labReportId = labReportId;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getReportTag() {
        return reportTag;
    }

    public void setReportTag(String reportTag) {
        this.reportTag = reportTag;
    }

    public String getOtherStatusName() {
        return otherStatusName;
    }

    public void setOtherStatusName(String otherStatusName) {
        this.otherStatusName = otherStatusName;
    }

    public String getShipRecID() {
        return shipRecID;
    }

    public void setShipRecID(String shipRecID) {
        this.shipRecID = shipRecID;
    }

    public String getLossStyle() {
        return lossStyle;
    }

    public void setLossStyle(String lossStyle) {
        this.lossStyle = lossStyle;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    public String getApprEventTypeCd() {
        return apprEventTypeCd;
    }

    public void setApprEventTypeCd(String apprEventTypeCd) {
        this.apprEventTypeCd = apprEventTypeCd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
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

    public String getUpdateString() {
        return updateString;
    }

    public void setUpdateString(String updateString) {
        this.updateString = updateString;
    }

    public String getsResId() {
        return sResId;
    }

    public void setsResId(String sResId) {
        this.sResId = sResId;
    }

    public String getLaborCode() {
        return laborCode;
    }

    public void setLaborCode(String laborCode) {
        this.laborCode = laborCode;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getGgNo() {
        return ggNo;
    }

    public void setGgNo(String ggNo) {
        this.ggNo = ggNo;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getSamplingName() {
        return samplingName;
    }

    public void setSamplingName(String samplingName) {
        this.samplingName = samplingName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSamplingWeight() {
        return samplingWeight;
    }

    public void setSamplingWeight(String samplingWeight) {
        this.samplingWeight = samplingWeight;
    }

    public String getMad() {
        return mad;
    }

    public void setMad(String mad) {
        this.mad = mad;
    }

    public String getStAd() {
        return stAd;
    }

    public void setStAd(String stAd) {
        this.stAd = stAd;
    }

    public String getStD() {
        return stD;
    }

    public void setStD(String stD) {
        this.stD = stD;
    }

    public String getCcVal() {
        return ccVal;
    }

    public void setCcVal(String ccVal) {
        this.ccVal = ccVal;
    }

    public String getJzStyle() {
        return jzStyle;
    }

    public void setJzStyle(String jzStyle) {
        this.jzStyle = jzStyle;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateCode() {
        return updateCode;
    }

    public void setUpdateCode(String updateCode) {
        this.updateCode = updateCode;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
