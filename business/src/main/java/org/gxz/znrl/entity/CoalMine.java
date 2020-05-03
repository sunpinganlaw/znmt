package org.gxz.znrl.entity;

import java.math.BigDecimal;

public class CoalMine {
    private String mineNo;

    private String mineName;

    private String area;

    private BigDecimal transDist;

    private BigDecimal transTime;

    private BigDecimal yearOutp;

    private String coalNo;

    private String lealPerson;

    private String transMode;

    private String mineProp;

    private String remark;

    private String forShort;

    private String venNo;

    private String venName;

    private String opCode;

    public String getVenName() {
        return venName;
    }

    public void setVenName(String venName) {
        this.venName = venName;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getVenNo() {
        return venNo;
    }

    public void setVenNo(String venNo) {
        this.venNo = venNo;
    }

    public String getForShort() {
        return forShort;
    }

    public void setForShort(String forShort) {
        this.forShort = forShort;
    }

    public String getMineNo() {
        return mineNo;
    }

    public void setMineNo(String mineNo) {
        this.mineNo = mineNo == null ? null : mineNo.trim();
    }

    public String getMineName() {
        return mineName;
    }

    public void setMineName(String mineName) {
        this.mineName = mineName == null ? null : mineName.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public BigDecimal getTransDist() {
        return transDist;
    }

    public void setTransDist(BigDecimal transDist) {
        this.transDist = transDist;
    }

    public BigDecimal getTransTime() {
        return transTime;
    }

    public void setTransTime(BigDecimal transTime) {
        this.transTime = transTime;
    }

    public BigDecimal getYearOutp() {
        return yearOutp;
    }

    public void setYearOutp(BigDecimal yearOutp) {
        this.yearOutp = yearOutp;
    }

    public String getCoalNo() {
        return coalNo;
    }

    public void setCoalNo(String coalNo) {
        this.coalNo = coalNo == null ? null : coalNo.trim();
    }

    public String getLealPerson() {
        return lealPerson;
    }

    public void setLealPerson(String lealPerson) {
        this.lealPerson = lealPerson == null ? null : lealPerson.trim();
    }

    public String getTransMode() {
        return transMode;
    }

    public void setTransMode(String transMode) {
        this.transMode = transMode == null ? null : transMode.trim();
    }

    public String getMineProp() {
        return mineProp;
    }

    public void setMineProp(String mineProp) {
        this.mineProp = mineProp == null ? null : mineProp.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}