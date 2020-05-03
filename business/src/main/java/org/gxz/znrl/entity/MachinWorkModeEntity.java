package org.gxz.znrl.entity;

/**
 * Created by rubish on 2015/5/12.
 */
public class MachinWorkModeEntity extends BaseEntity {

    private String modeRecId;

    private String workMode;

    private String remark;

    private String insertTime;

    private String updateTime;

    private String opCode;

    private String updateCode;

    private String param1;

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getModeRecId() {
        return modeRecId;
    }

    public void setModeRecId(String modeRecId) {
        this.modeRecId = modeRecId;
    }

    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getUpdateCode() {
        return updateCode;
    }

    public void setUpdateCode(String updateCode) {
        this.updateCode = updateCode;
    }
}
