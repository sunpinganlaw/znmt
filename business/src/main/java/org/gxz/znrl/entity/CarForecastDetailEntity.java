package org.gxz.znrl.entity;

/**
 * Created by admin-rubbissh on 2015/3/4.
 */
public class CarForecastDetailEntity extends BaseEntity  {

    private String recordId;

    private String forecastId;

    private String cardId;

    private String carId;

    private String mzQty;

    private String pzQty;

    private String netQty;

    private String venNo;

    private String venNoName;

    private String carrierNo;

    private String carrierNoName;

    private String mineNo;

    private String mineNoName;

    private String coalNo;

    private String coalNoName;

    private String startDate;

    private String endDate;

    private String status;

    private String opCode;

    private String insertTime;

    private String updateTime;

    private String updateCode;

    private String remark;

    private String actionTag;

    private String jsonStr;

    //返回操作结果
    public String resCode;
    public String resMsg;


    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getVenNoName() {
        return venNoName;
    }

    public void setVenNoName(String venNoName) {
        this.venNoName = venNoName;
    }

    public String getCarrierNoName() {
        return carrierNoName;
    }

    public void setCarrierNoName(String carrierNoName) {
        this.carrierNoName = carrierNoName;
    }

    public String getMineNoName() {
        return mineNoName;
    }

    public void setMineNoName(String mineNoName) {
        this.mineNoName = mineNoName;
    }

    public String getCoalNoName() {
        return coalNoName;
    }

    public void setCoalNoName(String coalNoName) {
        this.coalNoName = coalNoName;
    }

    public String getForecastId() {
        return forecastId;
    }

    public void setForecastId(String forecastId) {
        this.forecastId = forecastId;
    }

    public String getVenNo() {
        return venNo;
    }

    public void setVenNo(String venNo) {
        this.venNo = venNo;
    }

    public String getCarrierNo() {
        return carrierNo;
    }

    public void setCarrierNo(String carrierNo) {
        this.carrierNo = carrierNo;
    }

    public String getMineNo() {
        return mineNo;
    }

    public void setMineNo(String mineNo) {
        this.mineNo = mineNo;
    }

    public String getCoalNo() {
        return coalNo;
    }

    public void setCoalNo(String coalNo) {
        this.coalNo = coalNo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
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

    public String getUpdateCode() {
        return updateCode;
    }

    public void setUpdateCode(String updateCode) {
        this.updateCode = updateCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getMzQty() {
        return mzQty;
    }

    public void setMzQty(String mzQty) {
        this.mzQty = mzQty;
    }

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

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getActionTag() {
        return actionTag;
    }

    public void setActionTag(String actionTag) {
        this.actionTag = actionTag;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

}
