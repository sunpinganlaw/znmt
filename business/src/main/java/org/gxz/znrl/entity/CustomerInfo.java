package org.gxz.znrl.entity;

public class CustomerInfo {
    private String customerNo;

    private String customerName;

    private String lealPerson;



    private String customerAddr;

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
    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getLealPerson() {
        return lealPerson;
    }

    public void setLealPerson(String lealPerson) {
        this.lealPerson = lealPerson == null ? null : lealPerson.trim();
    }


    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
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