package org.gxz.znrl.entity;

/**
 * Created by HXL on 2017/2/14.
 */
public class CarLockInfoEntity extends BaseEntity {
    private String lockRecId;
    private String recId;
    private String carId;
    private String status;
    private String effTime;
    private String expTime;
    private String reason;
    private String opCode;
    private String updateCode;
    private String insertTime;
    private String updateTime;
    private String remark;
    private String jsonStr;
    private String flag;
    private String resCode;
    private String resMsg;
    private String beginTime;//锁车操作时间
    private String endTime;//锁车操作时间

    private String preKdFlag;//是否预扣吨
    private String preKdQty;//预扣吨吨位

    public String getLockRecId() {
        return lockRecId;
    }

    public void setLockRecId(String lockRecId) {
        this.lockRecId = lockRecId;
    }

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEffTime() {
        return effTime;
    }

    public void setEffTime(String effTime) {
        this.effTime = effTime;
    }

    public String getExpTime() {
        return expTime;
    }

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getUpdateCode() {
        return updateCode;
    }

    public void setUpdateCode(String updateCode) {
        this.updateCode = updateCode;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setV_resMsg(String v_resMsg) {
        this.resMsg = v_resMsg;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPreKdFlag() {
        return preKdFlag;
    }

    public void setPreKdFlag(String preKdFlag) {
        this.preKdFlag = preKdFlag;
    }

    public String getPreKdQty() {
        return preKdQty;
    }

    public void setPreKdQty(String preKdQty) {
        this.preKdQty = preKdQty;
    }
}
