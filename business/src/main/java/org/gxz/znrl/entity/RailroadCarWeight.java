package org.gxz.znrl.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RailroadCarWeight {
    private Integer railroadCarId;

    private String railroadCarType;

    private BigDecimal railroadCarWeight;

    private Long opCode;

    private Long updateCode;

    private Date updateDate;

    private Date createDate;

    public Integer getRailroadCarId() {
        return railroadCarId;
    }

    public void setRailroadCarId(Integer railroadCarId) {
        this.railroadCarId = railroadCarId;
    }

    public String getRailroadCarType() {
        return railroadCarType;
    }

    public void setRailroadCarType(String railroadCarType) {
        this.railroadCarType = railroadCarType == null ? null : railroadCarType.trim();
    }

    public BigDecimal getRailroadCarWeight() {
        return railroadCarWeight;
    }

    public void setRailroadCarWeight(BigDecimal railroadCarWeight) {
        this.railroadCarWeight = railroadCarWeight;
    }

    public Long getOpCode() {
        return opCode;
    }

    public void setOpCode(Long opCode) {
        this.opCode = opCode;
    }

    public Long getUpdateCode() {
        return updateCode;
    }

    public void setUpdateCode(Long updateCode) {
        this.updateCode = updateCode;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}