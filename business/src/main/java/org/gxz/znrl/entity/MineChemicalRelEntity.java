package org.gxz.znrl.entity;

/**
 * Created by Rubbish on 2015/7/23.
 */
public class MineChemicalRelEntity {

    private Long mineChemicalId;

    private String mineNo;

    private String chemicalType;

    private String createDate;

    private String updateDate;

    private String opCode;

    public Long getMineChemicalId() {
        return mineChemicalId;
    }

    public void setMineChemicalId(Long mineChemicalId) {
        this.mineChemicalId = mineChemicalId;
    }

    public String getMineNo() {
        return mineNo;
    }

    public void setMineNo(String mineNo) {
        this.mineNo = mineNo;
    }

    public String getChemicalType() {
        return chemicalType;
    }

    public void setChemicalType(String chemicalType) {
        this.chemicalType = chemicalType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }
}
