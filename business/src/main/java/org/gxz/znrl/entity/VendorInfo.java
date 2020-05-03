package org.gxz.znrl.entity;

public class VendorInfo {
    private String venNo;

    private String venName;

    private String lealPerson;

    private String venAddr;

    private String contNum;

    private String contPerson;

    private String remark;

    private String forShort;

    public String getForShort() {
        return forShort;
    }

    public void setForShort(String forShort) {
        this.forShort = forShort;
    }

    public String getVenNo() {
        return venNo;
    }

    public void setVenNo(String venNo) {
        this.venNo = venNo == null ? null : venNo.trim();
    }

    public String getVenName() {
        return venName;
    }

    public void setVenName(String venName) {
        this.venName = venName == null ? null : venName.trim();
    }

    public String getLealPerson() {
        return lealPerson;
    }

    public void setLealPerson(String lealPerson) {
        this.lealPerson = lealPerson == null ? null : lealPerson.trim();
    }

    public String getVenAddr() {
        return venAddr;
    }

    public void setVenAddr(String venAddr) {
        this.venAddr = venAddr == null ? null : venAddr.trim();
    }

    public String getContNum() {
        return contNum;
    }

    public void setContNum(String contNum) {
        this.contNum = contNum == null ? null : contNum.trim();
    }

    public String getContPerson() {
        return contPerson;
    }

    public void setContPerson(String contPerson) {
        this.contPerson = contPerson == null ? null : contPerson.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}