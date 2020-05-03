package org.gxz.znrl.entity;

/**
 * 获取下拉框的选项的通用entity
 * Created by xieyt on 14-12-8.
 */
public class ComboboxOptionEntity {
    public String id;//值
    public String name;//名称描述
    public String description;
    private boolean selected;

//begin蚌埠接卸计划需要在combogrid控件中展示
    private String transferNo;
    private String shipName;
    private String venName;
    private String mineName;
    private String startPlace;
    private String dischargeDate;
    private String contractNo;//用于存放调运计划id或合同号
    private String type;//用于区分是水运还是火运
    private String fuzzyInput;//用于模糊查询的字符串
    private String dischargeQty;//用于模糊查询的字符串
    private String transportNum;//用于模糊查询的字符串
  //end蚌埠接卸计划需要在combogrid控件中展示
    private String goodName;//用于模糊查询的字符串

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodTypeName() {
        return goodTypeName;
    }

    public void setGoodTypeName(String goodTypeName) {
        this.goodTypeName = goodTypeName;
    }

    private String goodTypeName;//用于模糊查询的字符串

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFuzzyInput() {
        return fuzzyInput;
    }

    public void setFuzzyInput(String fuzzyInput) {
        this.fuzzyInput = fuzzyInput;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getTransferNo() {
        return transferNo;
    }

    public void setTransferNo(String transferNo) {
        this.transferNo = transferNo;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getVenName() {
        return venName;
    }

    public void setVenName(String venName) {
        this.venName = venName;
    }

    public String getMineName() {
        return mineName;
    }

    public void setMineName(String mineName) {
        this.mineName = mineName;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDischargeQty() {
        return dischargeQty;
    }

    public void setDischargeQty(String dischargeQty) {
        this.dischargeQty = dischargeQty;
    }

    public String getTransportNum() {
        return transportNum;
    }

    public void setTransportNum(String transportNum) {
        this.transportNum = transportNum;
    }
}
