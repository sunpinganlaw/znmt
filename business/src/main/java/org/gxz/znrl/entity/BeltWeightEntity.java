package org.gxz.znrl.entity;

/**
 * Created by yangfeifei on 2017/6/14.
 */
public class BeltWeightEntity extends BaseEntity{
    private String pdNo;
    private String hhId;
    private String curDtm;
    private String valueType;
    private String jzQty;
    private String insertTime;
    private String updateTime;
    private String updateCode;
    private String beginTime;
    private String endTime;
    private String jsonStr;
    private String teamName;
    private String batchNo;
    private String beltWeight_A_old;
    private String beltWeight_A_new;
    private String beltWeight_B_old;
    private String beltWeight_B_new;

    public String getTeamName()
    {
        return this.teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getBatchNo()
    {
        return this.batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBeltWeight_A_new()
    {
        return this.beltWeight_A_new;
    }

    public void setBeltWeight_A_new(String beltWeight_A_new) {
        this.beltWeight_A_new = beltWeight_A_new;
    }

    public String getBeltWeight_A_old() {
        return this.beltWeight_A_old;
    }

    public void setBeltWeight_A_old(String beltWeight_A_old) {
        this.beltWeight_A_old = beltWeight_A_old;
    }

    public String getBeltWeight_B_old() {
        return this.beltWeight_B_old;
    }

    public void setBeltWeight_B_old(String beltWeight_B_old) {
        this.beltWeight_B_old = beltWeight_B_old;
    }

    public String getBeltWeight_B_new() {
        return this.beltWeight_B_new;
    }

    public void setBeltWeight_B_new(String beltWeight_B_new) {
        this.beltWeight_B_new = beltWeight_B_new;
    }

    public String getPdNo() {
        return pdNo;
    }

    public void setPdNo(String pdNo) {
        this.pdNo = pdNo;
    }

    public String getHhId() {
        return hhId;
    }

    public void setHhId(String hhId) {
        this.hhId = hhId;
    }

    public String getCurDtm() {
        return curDtm;
    }

    public void setCurDtm(String curDtm) {
        this.curDtm = curDtm;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getJzQty() {
        return jzQty;
    }

    public void setJzQty(String jzQty) {
        this.jzQty = jzQty;
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

    public String getUpdateCode() {
        return updateCode;
    }

    public void setUpdateCode(String updateCode) {
        this.updateCode = updateCode;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}
