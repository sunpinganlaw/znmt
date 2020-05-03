package org.gxz.znrl.entity;

/**
 * Created by xieyt on 14-12-19.
 */
public class DeviceErrEntity extends BaseEntity {
    //设备故障报警信息部分
    public String errorRecId;//故障记录ID
    public String machineCode;//故障机器编码
    public String flowId;
    public String errorCode;//错误代码
    public String errorTime;//故障记录时间
    public String errorDec;//故障描述
    public String dataStatus;//数据状态
    public String errorConfirm;//确认状态
    public String confirmOp;//确认人
    public String confirmTime;//确认时间
    public String errorPri;//故障优先级
    public String opCode;//处理人ID
    public String bak;
    public String deviceIp;
    public String flowName;//流程名称
    public String confirmOpCode;//确认人ID
    public String machineName;//故障机器编码
    public String beginTime;
    public String endTime;
    public String jsonStr;
    public String resCode;
    public String resMsg;

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
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

    public String getErrorConfirm() {
        return errorConfirm;
    }

    public void setErrorConfirm(String errorConfirm) {
        this.errorConfirm = errorConfirm;
    }

    public String getConfirmOp() {
        return confirmOp;
    }

    public void setConfirmOp(String confirmOp) {
        this.confirmOp = confirmOp;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
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
}
