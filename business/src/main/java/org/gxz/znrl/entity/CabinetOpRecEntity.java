package org.gxz.znrl.entity;

/**
 * Created by Rubbish on 2015/7/3.
 */
public class CabinetOpRecEntity extends BaseEntity {

    private  String opRecId;

    private  String cabinetRecId;

    private  String opType;

    private  String opDest;

    private  String dataStatus;

    private  String opCode;

    private String opName;

    private  String insertTime;

    private  String opTime;

    private  String opResult;

    private String ggName;//柜名1-10

    private String ccName;//层名A1-A14， B1-B14

    private String wwName;//位名1-30

    private String isEnabled;//是否可用 1可 0否

    private String isEnabledName;

    private String depositTime;

    private String makeSampleTime;

    private String sampleType;//样品类型

    private String sampleTypeName;

    private String packCode;//封装码

    private String sampleStatus;

    private String sampleStatusName;

    private String depositStartTime;

    private String depositEndTime;

    private String batchNo;

    private String laborCode;

    private String venNo;

    private String idName;//图片在单元格里的名称

    private String recHisId;

    private String  samplingCode;

    private String batchNofc;

    private String samplingCodefc;

    private String sampleTypeName_bl;//beilun6mm3个瓶子

    private String sampleTypeName_jb;//谏壁8个瓶子

    private String sampleWeight;//谏壁要出样重量

    private String apprStatus;
    private String updateName;
    private String updateTime;

    public String getSampleWeight() {
        return sampleWeight;
    }

    public void setSampleWeight(String sampleWeight) {
        this.sampleWeight = sampleWeight;
    }

    public String getBatchNoType() {
        return batchNoType;
    }

    public void setBatchNoType(String batchNoType) {
        this.batchNoType = batchNoType;
    }

    private String    batchNoType;

    public String getSampleTypeName_bl() {
        return sampleTypeName_bl;
    }

    public void setSampleTypeName_bl(String sampleTypeName_bl) {
        this.sampleTypeName_bl = sampleTypeName_bl;
    }

    public String getSampleTypeName_jb() {
        return sampleTypeName_jb;
    }

    public void setSampleTypeName_jb(String sampleTypeName_jb) {
        this.sampleTypeName_jb = sampleTypeName_jb;
    }

    public String getBatchNofc() {
        return batchNofc;
    }

    public void setBatchNofc(String batchNofc) {
        this.batchNofc = batchNofc;
    }

    public String getSamplingCodefc() {
        return samplingCodefc;
    }

    public void setSamplingCodefc(String samplingCodefc) {
        this.samplingCodefc = samplingCodefc;
    }

    public String getRecHisId() {
        return recHisId;
    }

    public void setRecHisId(String recHisId) {
        this.recHisId = recHisId;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
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

    public String getOpRecId() {
        return opRecId;
    }

    public void setOpRecId(String opRecId) {
        this.opRecId = opRecId;
    }

    public String getCabinetRecId() {
        return cabinetRecId;
    }

    public void setCabinetRecId(String cabinetRecId) {
        this.cabinetRecId = cabinetRecId;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getOpDest() {
        return opDest;
    }

    public void setOpDest(String opDest) {
        this.opDest = opDest;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
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

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public String getOpResult() {
        return opResult;
    }

    public void setOpResult(String opResult) {
        this.opResult = opResult;
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

    public String getIsEnabledName() {
        return isEnabledName;
    }

    public void setIsEnabledName(String isEnabledName) {
        this.isEnabledName = isEnabledName;
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

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
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

    public String getSampleStatusName() {
        return sampleStatusName;
    }

    public void setSampleStatusName(String sampleStatusName) {
        this.sampleStatusName = sampleStatusName;
    }

    public String getSamplingCode() {
        return samplingCode;
    }

    public void setSamplingCode(String samplingCode) {
        this.samplingCode = samplingCode;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
