package org.gxz.znrl.entity;

/**
 * Created by xieyt on 14-12-17.
 */
public class CtrlEntity {
    public String machineCode;//设备编号
    public String machineType;//设备类型
    public String commandCode;//命令代码
    public String opCode;//操作用户
    public String sampleCode;//采样编码
    public String batchNo;//批次号
    public String remark;//备注信息
    public String startType;//大开一键启动的启动方式
    //返回操作结果
    public String resCode;
    public String resMsg;

    //参数传递
    public String jsonString;
    public String coalWater;
    public String samplePlan;

    public String getSamplePlan() {
        return samplePlan;
    }

    public void setSamplePlan(String samplePlan) {
        this.samplePlan = samplePlan;
    }

    public String getCoalWater() {
        return coalWater;
    }

    public void setCoalWater(String coalWater) {
        this.coalWater = coalWater;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getCommandCode() {
        return commandCode;
    }

    public void setCommandCode(String commandCode) {
        this.commandCode = commandCode;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
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

    public String getStartType() {
        return startType;
    }

    public void setStartType(String startType) {
        this.startType = startType;
    }

    @Override
    public String toString(){
        return "machineCode:"+machineCode+" machineType:"+machineType+" commandCode:"+commandCode+" opCode:"+opCode;
    }
}
