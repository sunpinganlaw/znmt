package org.gxz.znrl.entity;

/**
 * Created by xieyt on 14-12-19.
 */
public class DeviceBroadEntity extends BaseEntity {
    //动态信息部分
    public String broadRecId;//动态信息ID
    public String broadTime;//动态信息时间
    public String broadDec;//动态信息描述

    //设备故障报警信息部分
    public String errorRecId;//故障记录ID
    public String errorTime;//故障记录时间
    public String errorDec;//故障描述
    public String machineCode;//故障机器编码
    public String flowName;//流程名称
    public String errorCode;//错误代码
    public String errorPri;//故障优先级
    public String dataStatusCode;//数据状态编码
    public String dataStatus;//数据状态
    public String errorConfirmStatusCode;//确认状态编码
    public String errorConfirmStatus;//确认状态
    public String errorConfirmOp;//确认人
    public String errorConfirmTime;//确认时间
    public String createOp;//创建人
    public String opCode;//处理人ID
    public String confirmOpCode;//确认人ID
    public String bak;
    public String deviceIp;
    public String flowId;
    public String machinName;//故障机器名称

    private String deviceBroadPri;

    private String beginTime;
    private String endTime;

    private String resCode;
    private String resMsg;

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

    public String getDeviceBroadPri() {
        return deviceBroadPri;
    }

    public void setDeviceBroadPri(String deviceBroadPri) {
        this.deviceBroadPri = deviceBroadPri;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getDataStatusCode() {
        return dataStatusCode;
    }

    public void setDataStatusCode(String dataStatusCode) {
        this.dataStatusCode = dataStatusCode;
    }

    public String getErrorConfirmStatusCode() {
        return errorConfirmStatusCode;
    }

    public void setErrorConfirmStatusCode(String errorConfirmStatusCode) {
        this.errorConfirmStatusCode = errorConfirmStatusCode;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getConfirmOpCode() {
        return confirmOpCode;
    }

    public void setConfirmOpCode(String confirmOpCode) {
        this.confirmOpCode = confirmOpCode;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorPri() {
        return errorPri;
    }

    public void setErrorPri(String errorPri) {
        this.errorPri = errorPri;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getErrorConfirmStatus() {
        return errorConfirmStatus;
    }

    public void setErrorConfirmStatus(String errorConfirmStatus) {
        this.errorConfirmStatus = errorConfirmStatus;
    }

    public String getErrorConfirmOp() {
        return errorConfirmOp;
    }

    public void setErrorConfirmOp(String errorConfirmOp) {
        this.errorConfirmOp = errorConfirmOp;
    }

    public String getErrorConfirmTime() {
        return errorConfirmTime;
    }

    public void setErrorConfirmTime(String errorConfirmTime) {
        this.errorConfirmTime = errorConfirmTime;
    }

    public String getCreateOp() {
        return createOp;
    }

    public void setCreateOp(String createOp) {
        this.createOp = createOp;
    }

    public String getErrorRecId() {
        return errorRecId;
    }

    public void setErrorRecId(String errorRecId) {
        this.errorRecId = errorRecId;
    }

    public String getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(String errorTime) {
        this.errorTime = errorTime;
    }

    public String getErrorDec() {
        return errorDec;
    }

    public void setErrorDec(String errorDec) {
        this.errorDec = errorDec;
    }

    public String getBroadRecId() {
        return broadRecId;
    }

    public void setBroadRecId(String broadRecId) {
        this.broadRecId = broadRecId;
    }

    public String getBroadTime() {
        return broadTime;
    }

    public void setBroadTime(String broadTime) {
        this.broadTime = broadTime;
    }

    public String getBroadDec() {
        return broadDec;
    }

    public void setBroadDec(String broadDec) {
        this.broadDec = broadDec;
    }

    public String getMachinName() {
        return machinName;
    }

    public void setMachinName(String machinName) {
        this.machinName = machinName;
    }
}
