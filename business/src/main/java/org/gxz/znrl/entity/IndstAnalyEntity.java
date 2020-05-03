package org.gxz.znrl.entity;

/**
 * Created by xieyt on 15-6-5.
 */
public class IndstAnalyEntity {
    /////////////各个化验都有的
    public String iaResId;
    public String laborCode;
    public String deviceNo;
    public String deviceName;
    public String ggNo;
    public String temperature;//温度
    public String humidity;//湿度
    public String lossStyle;

    public String getLossStyle() {
        return lossStyle;
    }

    public void setLossStyle(String lossStyle) {
        this.lossStyle = lossStyle;
    }

    public String standard;
    public String samplingName;
    public String opName;
    public String opTime;
    public String opDate;
    public String opCode;
    public String updateTime;
    public String updateCode;
    public String confirmTime;
    public String confirmCode;
    public String insertTime;
    public String beginDate;
    public String endDate;
    public String batchType;
    public String otherStatusName;
    ////////////////////////////各个化验不同的
    public String shKggWeight;//水灰空坩埚重
    public String shSamplingWeight;//水灰煤样重

    public String mad;//空干基水分
    public String aad;//空干基灰分
    public String ad;//干基灰分
    public String hkTotalWeight;//挥空坩埚加样重
    public String hkSamplingWeight;//挥空煤样重量
    public String vad;//空干基挥发分
    public String vdaf;//收到基挥发分
    public String ccVal;//超差
    public String ccStatus;
    public String status;
    public String statusName;
    ////////////////////////////////////////
    public String id;
    public String updateString;
    public String dataType;
    public String resCode;
    public String resMsg;
    public String apprEventTypeCd;

    private String reportTag;
    private String usage;

    public String getOpDate() {
        return opDate;
    }

    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    public String getCcStatus() {
        return ccStatus;
    }

    public void setCcStatus(String ccStatus) {
        this.ccStatus = ccStatus;
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

    public String getSamplingName() {
        return samplingName;
    }

    public void setSamplingName(String samplingName) {
        this.samplingName = samplingName;
    }

    public String getIaResId() {
        return iaResId;
    }

    public void setIaResId(String iaResId) {
        this.iaResId = iaResId;
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

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
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

    public String getShKggWeight() {
        return shKggWeight;
    }

    public void setShKggWeight(String shKggWeight) {
        this.shKggWeight = shKggWeight;
    }

    public String getShSamplingWeight() {
        return shSamplingWeight;
    }

    public void setShSamplingWeight(String shSamplingWeight) {
        this.shSamplingWeight = shSamplingWeight;
    }

    public String getMad() {
        return mad;
    }

    public void setMad(String mad) {
        this.mad = mad;
    }

    public String getAad() {
        return aad;
    }

    public void setAad(String aad) {
        this.aad = aad;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getHkTotalWeight() {
        return hkTotalWeight;
    }

    public void setHkTotalWeight(String hkTotalWeight) {
        this.hkTotalWeight = hkTotalWeight;
    }

    public String getHkSamplingWeight() {
        return hkSamplingWeight;
    }

    public void setHkSamplingWeight(String hkSamplingWeight) {
        this.hkSamplingWeight = hkSamplingWeight;
    }

    public String getVad() {
        return vad;
    }

    public void setVad(String vad) {
        this.vad = vad;
    }

    public String getVdaf() {
        return vdaf;
    }

    public void setVdaf(String vdaf) {
        this.vdaf = vdaf;
    }

    public String getCcVal() {
        return ccVal;
    }

    public void setCcVal(String ccVal) {
        this.ccVal = ccVal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdateString() {
        return updateString;
    }

    public void setUpdateString(String updateString) {
        this.updateString = updateString;
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

    public String getApprEventTypeCd() {
        return apprEventTypeCd;
    }

    public void setApprEventTypeCd(String apprEventTypeCd) {
        this.apprEventTypeCd = apprEventTypeCd;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
