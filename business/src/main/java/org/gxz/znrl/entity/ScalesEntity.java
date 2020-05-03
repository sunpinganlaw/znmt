package org.gxz.znrl.entity;

/**
 * Created by xieyt on 15-6-5.
 */
public class ScalesEntity {
    //共性属性
    public String scalesResId;
    public String laborCode;
    public String deviceNo;
    public String deviceName;
    public String ggNo;
    public String temperature;
    public String humidity;
    public String standard;
    public String opName;
    public String opTime;
    public String opDate;
    public String status;
    public String updateTime;
    public String updateCode;
    public String insertTime;
    public String opCode;
    public String beginDate;
    public String endDate;
    public String batchType;
    public String statusName;
    public String otherStatusName;
    public String indexType;//指标类型
    public String indexVal;
    //////////////////////////////////个性属性
    public String mypQty;
    public String samplingQty;
    public String totalQty;
    public String afterDryQty;
    public String testQty;
    public String qtyLoss;
    public String lossStyle;
    public String mt;
    public String mad;
    public String adStyle;
    public String aad;
    public String ad;
    public String vad;
    public String id;
    public String ccVal;
    public String ccStatus;
    //以json字符串的方式传入存储过程
    public String insertString;
    public String updateString;
    public String deleteString;
    public String publicString;
    public String scalesDataString;
    public String deviceDataString;
    public String dataType;
    public String confirmCd;
    public String confirmTime;
    public String confirmName;
    public String remark;
    public String orgKeyId;
    public String indexTypeName;
    //返回操作结果
    public String resCode;
    public String resMsg;
    public String usage;

    public String getOpDate() {
        return opDate;
    }

    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmName() {
        return confirmName;
    }

    public void setConfirmName(String confirmName) {
        this.confirmName = confirmName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrgKeyId() {
        return orgKeyId;
    }

    public void setOrgKeyId(String orgKeyId) {
        this.orgKeyId = orgKeyId;
    }

    public String getIndexTypeName() {
        return indexTypeName;
    }

    public void setIndexTypeName(String indexTypeName) {
        this.indexTypeName = indexTypeName;
    }

    public String getScalesDataString() {
        return scalesDataString;
    }

    public void setScalesDataString(String scalesDataString) {
        this.scalesDataString = scalesDataString;
    }

    public String getDeviceDataString() {
        return deviceDataString;
    }

    public void setDeviceDataString(String deviceDataString) {
        this.deviceDataString = deviceDataString;
    }

    public String getIndexVal() {
        return indexVal;
    }

    public void setIndexVal(String indexVal) {
        this.indexVal = indexVal;
    }

    public String getCcStatus() {
        return ccStatus;
    }

    public void setCcStatus(String ccStatus) {
        this.ccStatus = ccStatus;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    private String reportTag;

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

    public String getCcVal() {
        return ccVal;
    }

    public void setCcVal(String ccVal) {
        this.ccVal = ccVal;
    }

    //录入类型判断
    private String scalesType;

    private String relScalesResId;

    private String add;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getConfirmCd() {
        return confirmCd;
    }

    public void setConfirmCd(String confirmCd) {
        this.confirmCd = confirmCd;
    }

    public String getInsertString() {
        return insertString;
    }

    public void setInsertString(String insertString) {
        this.insertString = insertString;
    }

    public String getUpdateString() {
        return updateString;
    }

    public void setUpdateString(String updateString) {
        this.updateString = updateString;
    }

    public String getDeleteString() {
        return deleteString;
    }

    public void setDeleteString(String deleteString) {
        this.deleteString = deleteString;
    }

    public String getPublicString() {
        return publicString;
    }

    public void setPublicString(String publicString) {
        this.publicString = publicString;
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

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getRelScalesResId() {
        return relScalesResId;
    }

    public void setRelScalesResId(String relScalesResId) {
        this.relScalesResId = relScalesResId;
    }

    public String getScalesType() {
        return scalesType;
    }

    public void setScalesType(String scalesType) {
        this.scalesType = scalesType;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getScalesResId() {
        return scalesResId;
    }

    public void setScalesResId(String scalesResId) {
        this.scalesResId = scalesResId;
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

    public String getMypQty() {
        return mypQty;
    }

    public void setMypQty(String mypQty) {
        this.mypQty = mypQty;
    }

    public String getSamplingQty() {
        return samplingQty;
    }

    public void setSamplingQty(String samplingQty) {
        this.samplingQty = samplingQty;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getAfterDryQty() {
        return afterDryQty;
    }

    public void setAfterDryQty(String afterDryQty) {
        this.afterDryQty = afterDryQty;
    }

    public String getTestQty() {
        return testQty;
    }

    public void setTestQty(String testQty) {
        this.testQty = testQty;
    }

    public String getQtyLoss() {
        return qtyLoss;
    }

    public void setQtyLoss(String qtyLoss) {
        this.qtyLoss = qtyLoss;
    }

    public String getLossStyle() {
        return lossStyle;
    }

    public void setLossStyle(String lossStyle) {
        this.lossStyle = lossStyle;
    }

    public String getMt() {
        return mt;
    }

    public void setMt(String mt) {
        this.mt = mt;
    }

    public String getMad() {
        return mad;
    }

    public void setMad(String mad) {
        this.mad = mad;
    }

    public String getAdStyle() {
        return adStyle;
    }

    public void setAdStyle(String adStyle) {
        this.adStyle = adStyle;
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

    public String getVad() {
        return vad;
    }

    public void setVad(String vad) {
        this.vad = vad;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
