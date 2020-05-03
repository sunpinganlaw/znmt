package org.gxz.znrl.entity;

/**
 * Created by xieyt on 14-11-18.
 */
public class WeightEntity extends BaseEntity{
    public String recordNo;//入厂序号，也做流水号
    public String cardId;//卡号
    public String cardTyp;//卡类型
    public String cardTypeName;//卡类型
    public String carId;//车牌号
    public String colryName;//煤矿
    public String carTyp;//车型
    public String carTypeName;//车型名称
    public String coalName;//
    public String carrierNo;//运输单位id
    public String carrierName;//运输单位
    public String mzQty;//毛重
    public String pzQty;//皮重
    public String netQty;//净重
    public String czTime;//称重时间
    public String jqTime;//称轻时间

    public String getPzQty() {
        return pzQty;
    }

    public void setPzQty(String pzQty) {
        this.pzQty = pzQty;
    }

    public String getNetQty() {
        return netQty;
    }

    public void setNetQty(String netQty) {
        this.netQty = netQty;
    }

    public String getJqTime() {
        return jqTime;
    }

    public void setJqTime(String jqTime) {
        this.jqTime = jqTime;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getColryName() {
        return colryName;
    }

    public void setColryName(String colryName) {
        this.colryName = colryName;
    }

    public String getCoalName() {
        return coalName;
    }

    public void setCoalName(String coalName) {
        this.coalName = coalName;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getMzQty() {
        return mzQty;
    }

    public void setMzQty(String mzQty) {
        this.mzQty = mzQty;
    }

    public String getCzTime() {
        return czTime;
    }

    public void setCzTime(String czTime) {
        this.czTime = czTime;
    }
}
