package org.gxz.znrl.entity;

public class CarrierInfo {
    private String carrierNo;

    private String carrierName;

    private String carrierAddr;

    private String contNum;

    private String contPerson;

    private Integer zycNum;

    private Integer pbcNum;

    private Integer wfcNum;

    private Integer tscNum;

    private String remark;

    private String forShort;

    public String getForShort() {
        return forShort;
    }

    public void setForShort(String forShort) {
        this.forShort = forShort;
    }

    public String getCarrierNo() {
        return carrierNo;
    }

    public void setCarrierNo(String carrierNo) {
        this.carrierNo = carrierNo == null ? null : carrierNo.trim();
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName == null ? null : carrierName.trim();
    }

    public String getCarrierAddr() {
        return carrierAddr;
    }

    public void setCarrierAddr(String carrierAddr) {
        this.carrierAddr = carrierAddr == null ? null : carrierAddr.trim();
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

    public Integer getZycNum() {
        return zycNum;
    }

    public void setZycNum(Integer zycNum) {
        this.zycNum = zycNum;
    }

    public Integer getPbcNum() {
        return pbcNum;
    }

    public void setPbcNum(Integer pbcNum) {
        this.pbcNum = pbcNum;
    }

    public Integer getWfcNum() {
        return wfcNum;
    }

    public void setWfcNum(Integer wfcNum) {
        this.wfcNum = wfcNum;
    }

    public Integer getTscNum() {
        return tscNum;
    }

    public void setTscNum(Integer tscNum) {
        this.tscNum = tscNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}