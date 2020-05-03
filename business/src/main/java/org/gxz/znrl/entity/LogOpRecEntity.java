package org.gxz.znrl.entity;

/**
 * Created by yangfeifei on 2016/6/20.
 */
public class LogOpRecEntity extends BaseEntity{

    public  String logId;

    public  String logOpCode;

    public  String logOpName;

    public  String logType;

    public String logTypeDec;

    public  String modDec;

    public  String opStartTime;

    public  String opEndTime;

    public  String logTime;

    public String hostIp;
    public String operMenu;
    public String operButton;
    public String requestParams;
    public String sysLogType;

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getLogTypeDec() {
        return logTypeDec;
    }

    public void setLogTypeDec(String logTypeDec) {
        this.logTypeDec = logTypeDec;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getLogOpCode() {
        return logOpCode;
    }

    public void setLogOpCode(String logOpCode) {
        this.logOpCode = logOpCode;
    }

    public String getLogOpName() {
        return logOpName;
    }

    public void setLogOpName(String logOpName) {
        this.logOpName = logOpName;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getModDec() {
        return modDec;
    }

    public void setModDec(String modDec) {
        this.modDec = modDec;
    }

    public String getOpStartTime() {
        return opStartTime;
    }

    public void setOpStartTime(String opStartTime) {
        this.opStartTime = opStartTime;
    }

    public String getOpEndTime() {
        return opEndTime;
    }

    public void setOpEndTime(String opEndTime) {
        this.opEndTime = opEndTime;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getOperMenu() {
        return operMenu;
    }

    public void setOperMenu(String operMenu) {
        this.operMenu = operMenu;
    }

    public String getOperButton() {
        return operButton;
    }

    public void setOperButton(String operButton) {
        this.operButton = operButton;
    }

    public String getSysLogType() {
        return sysLogType;
    }

    public void setSysLogType(String sysLogType) {
        this.sysLogType = sysLogType;
    }
}
