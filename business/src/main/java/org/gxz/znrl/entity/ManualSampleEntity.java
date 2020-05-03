package org.gxz.znrl.entity;

public class ManualSampleEntity {
    private String batchNo;
    private String sampleCode;
    private String manSampleCode;
    private String carIds;
    private String xhNums;
    private String beginDate;
    private String endDate;
    private String sampleType;
    private String laborCode;
    private String samplingCode;
    private String trainNo;
    private String batchNoType;
    private String packCode;

    private String carCnt;//人工采样车数

    private String printNum; //化验编码打印次数

    private String manPackCode;//人工制样封装码
    public String opCode;

    //返回操作结果
    public String resCode;
    public String resMsg;

    private String printType;//打印编码的类型
    private String printCode;//打印的编码
    private String printNumLabor;    //化验编码打印次数
    private String printNumSample;   //采样编码打印次数
    private String printNumSampling; //制样编码打印次数
    private String printNumPack;     //封装编码打印次数
    private String printOpTimeLabor;    //化验编码打印时间
    private String printOpTimeSample;   //采样编码打印时间
    private String printOpTimeSampling; //制样编码打印时间
    private String printOpTimePack;     //封装编码打印时间
    private String printOpNameLabor;    //化验编码打印人员
    private String printOpNameSample;   //采样编码打印人员
    private String printOpNameSampling; //制样编码打印人员
    private String printOpNamePack;     //封装编码打印人员

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public String getPrintCode() {
        return printCode;
    }

    public void setPrintCode(String printCode) {
        this.printCode = printCode;
    }

    public String getPrintNumLabor() {
        return printNumLabor;
    }

    public void setPrintNumLabor(String printNumLabor) {
        this.printNumLabor = printNumLabor;
    }

    public String getPrintNumSample() {
        return printNumSample;
    }

    public void setPrintNumSample(String printNumSample) {
        this.printNumSample = printNumSample;
    }

    public String getPrintNumSampling() {
        return printNumSampling;
    }

    public void setPrintNumSampling(String printNumSampling) {
        this.printNumSampling = printNumSampling;
    }

    public String getPrintNumPack() {
        return printNumPack;
    }

    public void setPrintNumPack(String printNumPack) {
        this.printNumPack = printNumPack;
    }

    public String getPrintOpTimeLabor() {
        return printOpTimeLabor;
    }

    public void setPrintOpTimeLabor(String printOpTimeLabor) {
        this.printOpTimeLabor = printOpTimeLabor;
    }

    public String getPrintOpTimeSample() {
        return printOpTimeSample;
    }

    public void setPrintOpTimeSample(String printOpTimeSample) {
        this.printOpTimeSample = printOpTimeSample;
    }

    public String getPrintOpTimeSampling() {
        return printOpTimeSampling;
    }

    public void setPrintOpTimeSampling(String printOpTimeSampling) {
        this.printOpTimeSampling = printOpTimeSampling;
    }

    public String getPrintOpTimePack() {
        return printOpTimePack;
    }

    public void setPrintOpTimePack(String printOpTimePack) {
        this.printOpTimePack = printOpTimePack;
    }

    public String getPrintOpNameLabor() {
        return printOpNameLabor;
    }

    public void setPrintOpNameLabor(String printOpNameLabor) {
        this.printOpNameLabor = printOpNameLabor;
    }

    public String getPrintOpNameSample() {
        return printOpNameSample;
    }

    public void setPrintOpNameSample(String printOpNameSample) {
        this.printOpNameSample = printOpNameSample;
    }

    public String getPrintOpNameSampling() {
        return printOpNameSampling;
    }

    public void setPrintOpNameSampling(String printOpNameSampling) {
        this.printOpNameSampling = printOpNameSampling;
    }

    public String getPrintOpNamePack() {
        return printOpNamePack;
    }

    public void setPrintOpNamePack(String printOpNamePack) {
        this.printOpNamePack = printOpNamePack;
    }

    public String getCarCnt() {
        return carCnt;
    }

    public void setCarCnt(String carCnt) {
        this.carCnt = carCnt;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    public String getBatchNoType() {
        return batchNoType;
    }

    public void setBatchNoType(String batchNoType) {
        this.batchNoType = batchNoType;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getSamplingCode() {
        return samplingCode;
    }

    public void setSamplingCode(String samplingCode) {
        this.samplingCode = samplingCode;
    }

    public String getLaborCode() {
        return laborCode;
    }

    public void setLaborCode(String laborCode) {
        this.laborCode = laborCode;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getManSampleCode() {
        return manSampleCode;
    }

    public void setManSampleCode(String manSampleCode) {
        this.manSampleCode = manSampleCode;
    }

    public String getCarIds() {
        return carIds;
    }

    public void setCarIds(String carIds) {
        this.carIds = carIds;
    }

    public String getXhNums() {
        return xhNums;
    }

    public void setXhNums(String xhNums) {
        this.xhNums = xhNums;
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

    public String getManPackCode() {
        return manPackCode;
    }

    public void setManPackCode(String manPackCode) {
        this.manPackCode = manPackCode;
    }

    public String getPrintNum() {
        return printNum;
    }

    public void setPrintNum(String printNum) {
        this.printNum = printNum;
    }
}