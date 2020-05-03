package org.gxz.znrl.entity;

/**
 * Created by Rubbish on 2015/7/1.
 */
public class WorkModeTypeCommit {

    private Integer typeId;

    private String originalValue;

    private String modifyValue;

    private String opCode;

    private String remark;

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getModifyValue() {
        return modifyValue;
    }

    public void setModifyValue(String modifyValue) {
        this.modifyValue = modifyValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }
}
