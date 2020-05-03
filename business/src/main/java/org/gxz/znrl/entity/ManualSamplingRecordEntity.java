package org.gxz.znrl.entity;

/**
 * Created by xuxs on 2018/1/6.
 * 人工制样记录单
 */
public class ManualSamplingRecordEntity extends BaseEntity {

    private String samplingDate;
    private String samplingCode;
    private String sampleWeight;
    private String sample6Weight;
    private String sample3Weight;
    private String sample2Weight;
    private String sampleSize;
    private String beginDt;
    private String endDt;
    private String lianheDevice;
    private String posuiDevice;
    private String guoshai3mmRate;
    private String twofenqiDevice;
    private String dryBeginDt;
    private String dryEndDt;
    private String twofensuiDevice;
    private String guoshai2Rate;
    private String opCode;
    private String takeCode;
    private String remark;
    private String sampleType;
    private String sampleTypeName;
    private String resCode;
    private String resMsg;
    private String startTime;
    private String endTime;
    private String manualSamplingId;
    private String id;
    private String makeCode;
    private String updateCode;
    private String opTime;
    private String updateTime;
    private String takeName;
    private String makeName;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    private String size;

    public String doActionTag;//动作标识 ADD, MOD
    public String jsonString;//json字符串


    public String getSamplingDate() {
        return samplingDate;
    }

    public void setSamplingDate(String samplingDate) {
        this.samplingDate = samplingDate;
    }

    public String getSamplingCode() {
        return samplingCode;
    }

    public void setSamplingCode(String samplingCode) {
        this.samplingCode = samplingCode;
    }

    public String getSampleWeight() {
        return sampleWeight;
    }

    public void setSampleWeight(String sampleWeight) {
        this.sampleWeight = sampleWeight;
    }

    public String getSample6Weight() {
        return sample6Weight;
    }

    public void setSample6Weight(String sample6Weight) {
        this.sample6Weight = sample6Weight;
    }

    public String getSample3Weight() {
        return sample3Weight;
    }

    public void setSample3Weight(String sample3Weight) {
        this.sample3Weight = sample3Weight;
    }

    public String getSample2Weight() {
        return sample2Weight;
    }

    public void setSample2Weight(String sample2Weight) {
        this.sample2Weight = sample2Weight;
    }

    public String getSampleSize() {
        return sampleSize;
    }

    public void setSampleSize(String sampleSize) {
        this.sampleSize = sampleSize;
    }

    public String getBeginDt() {
        return beginDt;
    }

    public void setBeginDt(String beginDt) {
        this.beginDt = beginDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getLianheDevice() {
        return lianheDevice;
    }

    public void setLianheDevice(String lianheDevice) {
        this.lianheDevice = lianheDevice;
    }

    public String getPosuiDevice() {
        return posuiDevice;
    }

    public void setPosuiDevice(String posuiDevice) {
        this.posuiDevice = posuiDevice;
    }

    public String getGuoshai3mmRate() {
        return guoshai3mmRate;
    }

    public void setGuoshai3mmRate(String guoshai3mmRate) {
        this.guoshai3mmRate = guoshai3mmRate;
    }

    public String getTwofenqiDevice() {
        return twofenqiDevice;
    }

    public void setTwofenqiDevice(String twofenqiDevice) {
        this.twofenqiDevice = twofenqiDevice;
    }

    public String getDryBeginDt() {
        return dryBeginDt;
    }

    public void setDryBeginDt(String dryBeginDt) {
        this.dryBeginDt = dryBeginDt;
    }

    public String getDryEndDt() {
        return dryEndDt;
    }

    public void setDryEndDt(String dryEndDt) {
        this.dryEndDt = dryEndDt;
    }

    public String getTwofensuiDevice() {
        return twofensuiDevice;
    }

    public void setTwofensuiDevice(String twofensuiDevice) {
        this.twofensuiDevice = twofensuiDevice;
    }

    public String getGuoshai2Rate() {
        return guoshai2Rate;
    }

    public void setGuoshai2Rate(String guoshai2Rate) {
        this.guoshai2Rate = guoshai2Rate;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getTakeCode() {
        return takeCode;
    }

    public void setTakeCode(String takeCode) {
        this.takeCode = takeCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getManualSamplingId() {
        return manualSamplingId;
    }

    public void setManualSamplingId(String manualSamplingId) {
        this.manualSamplingId = manualSamplingId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMakeCode() {
        return makeCode;
    }

    public void setMakeCode(String makeCode) {
        this.makeCode = makeCode;
    }

    public String getUpdateCode() {
        return updateCode;
    }

    public void setUpdateCode(String updateCode) {
        this.updateCode = updateCode;
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDoActionTag() {
        return doActionTag;
    }

    public void setDoActionTag(String doActionTag) {
        this.doActionTag = doActionTag;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }

    public String getTakeName() {
        return takeName;
    }

    public void setTakeName(String takeName) {
        this.takeName = takeName;
    }
}
