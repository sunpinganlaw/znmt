package org.gxz.znrl.entity;

/**
 * Created by xieyt on 15-1-24.
 */
public class CabinetEntity extends  BaseEntity{
    public String cabinetRecId;
    public String ggName;//柜名1-10
    public String ccName;//层名A1-A14， B1-B14
    public String ccLikeName;//层名A， B,用来 like 'A%'
    public String wwName;//位名1-30
    public String ppName;//盘位名
    public String isEnabled;//是否可用 1可 0否
    public String isEnabledName;
    public String depositTime;
    public String makeSampleTime;
    public String sampleType;//样品类型
    public String sampleTypeName;
    public String packCode;//封装码
    public String sampleStatus;
    public String sampleStatusName;
    public String insertTime;
    public String opCode;
    public String opName;
    public String updateTime;
    public String updateCode;
    public String updateName;
    public String idName;//图片在单元格里的名称

    public String apprEventId;

    public String batchNo;

    public String laborCode;

    public String venNo;

    public String coalSize;

    private String depositStartTime;

    private String depositEndTime;

    public String samplingCode;

    public String totalCnt;//总仓位数

    public String usedCnt;//已使用仓位数

    public String leftCnt;//剩余仓位数

    public String exceptCnt;//异常仓位数

    public String normalCnt;//剩余仓位数

    public String outtimeCnt;//异常仓位数

    private String sampleTypeName_bl;//beilun6mm3个瓶子

    private String sampleTypeName_jb;//谏壁入厂8个样

    private String extRecId;

    private String delayDays;//清样延迟天数

    private String jsonStr;

    private String remark;

    private String resCode;

    private String resMsg;

    private String shipName;

    private String batchTime;

    private String delayStatus;

    private String venName;

    private String startTime;

    private String endTime;

    public String batchNoType;//批次类型

    private String makeSampleStartTime;

    private String makeSampleEndTime;

    private String arguementFlag;

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    private String overTime;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getVenName() {
        return venName;
    }

    public void setVenName(String venName) {
        this.venName = venName;
    }

    public String getDelayStatus() {
        return delayStatus;
    }

    public void setDelayStatus(String delayStatus) {
        this.delayStatus = delayStatus;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getBatchTime() {
        return batchTime;
    }

    public void setBatchTime(String batchTime) {
        this.batchTime = batchTime;
    }

    public String getExtRecId() {
        return extRecId;
    }

    public void setExtRecId(String extRecId) {
        this.extRecId = extRecId;
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

    public String getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(String delayDays) {
        this.delayDays = delayDays;
    }

    public String getSampleTypeName_jb() {
        return sampleTypeName_jb;
    }

    public void setSampleTypeName_jb(String sampleTypeName_jb) {
        this.sampleTypeName_jb = sampleTypeName_jb;
    }

    public String getSampleTypeName_bl() {
        return sampleTypeName_bl;
    }

    public void setSampleTypeName_bl(String sampleTypeName_bl) {
        this.sampleTypeName_bl = sampleTypeName_bl;
    }

    public String getOuttimeCntYG() {
        return outtimeCnt;
    }

    public void setOuttimeCntYG(String outtimeCntYG) {
        this.outtimeCnt = outtimeCnt;
    }

    public String getNormalCntYG() {
        return normalCnt;
    }

    public void setNormalCntYG(String normalCntYG) {
        this.normalCnt = normalCnt;
    }

    public String getLeftCnt() {
        return leftCnt;
    }

    public void setLeftCnt(String leftCnt) {
        this.leftCnt = leftCnt;
    }

    public String getUsedCnt() {
        return usedCnt;
    }

    public void setUsedCnt(String usedCnt) {
        this.usedCnt = usedCnt;
    }

    public String getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(String totalCnt) {
        this.totalCnt = totalCnt;
    }

    public String getExceptCnt() {
        return exceptCnt;
    }

    public void setExceptCnt(String exceptCnt) {
        this.exceptCnt = exceptCnt;
    }

    public String getSamplingCode() {
        return samplingCode;
    }

    public void setSamplingCode(String samplingCode) {
        this.samplingCode = samplingCode;
    }

    public String getPpName() {
        return ppName;
    }

    public void setPpName(String ppName) {
        this.ppName = ppName;
    }

    public String getDepositStartTime() {
        return depositStartTime;
    }

    public void setDepositStartTime(String depositStartTime) {
        this.depositStartTime = depositStartTime;
    }

    public String getDepositEndTime() {
        return depositEndTime;
    }

    public void setDepositEndTime(String depositEndTime) {
        this.depositEndTime = depositEndTime;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getLaborCode() {
        return laborCode;
    }

    public void setLaborCode(String laborCode) {
        this.laborCode = laborCode;
    }

    public String getVenNo() {
        return venNo;
    }

    public void setVenNo(String venNo) {
        this.venNo = venNo;
    }

    public String getCoalSize() {
        return coalSize;
    }

    public void setCoalSize(String coalSize) {
        this.coalSize = coalSize;
    }

    public String getApprEventId() {
        return apprEventId;
    }

    public void setApprEventId(String apprEventId) {
        this.apprEventId = apprEventId;
    }

    public String getCcLikeName() {
        return ccLikeName;
    }

    public void setCcLikeName(String ccLikeName) {
        this.ccLikeName = ccLikeName;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getIsEnabledName() {
        return isEnabledName;
    }

    public void setIsEnabledName(String isEnabledName) {
        this.isEnabledName = isEnabledName;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getSampleStatusName() {
        return sampleStatusName;
    }

    public void setSampleStatusName(String sampleStatusName) {
        this.sampleStatusName = sampleStatusName;
    }

    public String getCabinetRecId() {
        return cabinetRecId;
    }

    public void setCabinetRecId(String cabinetRecId) {
        this.cabinetRecId = cabinetRecId;
    }

    public String getGgName() {
        return ggName;
    }

    public void setGgName(String ggName) {
        this.ggName = ggName;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public String getWwName() {
        return wwName;
    }

    public void setWwName(String wwName) {
        this.wwName = wwName;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(String depositTime) {
        this.depositTime = depositTime;
    }

    public String getMakeSampleTime() {
        return makeSampleTime;
    }

    public void setMakeSampleTime(String makeSampleTime) {
        this.makeSampleTime = makeSampleTime;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    public String getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus;
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

    public String getMakeSampleEndTime() {
        return makeSampleEndTime;
    }

    public void setMakeSampleEndTime(String makeSampleEndTime) {
        this.makeSampleEndTime = makeSampleEndTime;
    }

    public String getMakeSampleStartTime() {
        return makeSampleStartTime;
    }

    public void setMakeSampleStartTime(String makeSampleStartTime) {
        this.makeSampleStartTime = makeSampleStartTime;
    }

    public String getBatchNoType() {
        return batchNoType;
    }

    public void setBatchNoType(String batchNoType) {
        this.batchNoType = batchNoType;
    }

    public String getArguementFlag() {
        return arguementFlag;
    }

    public void setArguementFlag(String arguementFlag) {
        this.arguementFlag = arguementFlag;
    }
}
