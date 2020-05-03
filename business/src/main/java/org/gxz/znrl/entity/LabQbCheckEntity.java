package org.gxz.znrl.entity;

/**
 * Created by dage on 15-6-19.
 */
public class LabQbCheckEntity {
    // 公共参数
    private String labCheckId;
	private String deviceName;
    private String laborCode;//标煤编码
	private String bjsQgrd;//苯甲酸热值
	private String bmQgrd;//标煤热值
	private String bmMad;
	private String bmStad;
	private String evaluation;//评价
	private String deviceCode1;//弹桶编号
	private String deviceCode2;
	private String o2BumbNo1;//氧弹编号
	private String o2BumbNo2;
    private String diffPercent1;//与上次标定差
    private String diffPercent2;
	//热容量测试
	private String autoNo11;//试样编号
	private String autoNo12;
	private String autoNo13;
	private String autoNo14;
	private String autoNo15;	
	private String autoNo21;
	private String autoNo22;
	private String autoNo23;
	private String autoNo24;
	private String autoNo25;
	
	private String samWeight11;//试样重量
	private String samWeight12;
	private String samWeight13;
	private String samWeight14;
	private String samWeight15;	
	private String samWeight21;
	private String samWeight22;
	private String samWeight23;
	private String samWeight24;
	private String samWeight25;
	
	private String qb11;//热容量
	private String qb12;
	private String qb13;
	private String qb14;
	private String qb15;
	private String qb21;
	private String qb22;
	private String qb23;
	private String qb24;
	private String qb25;
	
	private String rsd1;//相对标准差
	private String rsd2;
	private String qbAvg1;//热容量平均值
	private String qbAvg2;
    //反标热值测试
	private String reSamWeight11;//第一次反标样重
	private String reSamWeight12;
	private String reSamWeight13;
	private String reSamWeight14;
	private String reSamWeight15;
	private String reSamWeight21;
	private String reSamWeight22;
	private String reSamWeight23;
	private String reSamWeight24;
	private String reSamWeight25;
	private String reSamWeightAvg1;
	private String reSamWeightAvg2;
	
	private String reQbad11;//第一次反标热值（空干基）
	private String reQbad12;
	private String reQbad13;
	private String reQbad14;
	private String reQbad15;
	private String reQbad21;
	private String reQbad22;
	private String reQbad23;
	private String reQbad24;
	private String reQbad25;
	private String reQbadAvg1;
	private String reQbadAvg2;
	
	private String reQgrd11; //第一次反标热值
	private String reQgrd12; 
	private String reQgrd13; 
	private String reQgrd14; 
	private String reQgrd15;	
	private String reQgrd21; 
	private String reQgrd22; 
	private String reQgrd23; 
	private String reQgrd24; 
	private String reQgrd25; 
	private String reQgrdAvg1;
	private String reQgrdAvg2;
  // 公共参数
    private String beginDate;
    private String endDate;
    private String checkDate;//试验日期
    private String nextDate;//下次试验日期
    private String status; //到期状态

	private String opDate; //信息录入日期
	private String apprDate; //审核日期
    private String insertTime;
    private String updateTime;
    private String opCode;
    private String opName;//化验人
    private String apprName; //审核人
    private String opDesc;
    public String updateString;
    public String resCode;
    public String resMsg;
    public String remark;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateString() {
        return updateString;
    }

    public void setUpdateString(String updateString) {
        this.updateString = updateString;
    }

    public String getLabCheckId() {
        return labCheckId;
    }

    public void setLabCheckId(String labCheckId) {
        this.labCheckId = labCheckId;
    }

    public String getLaborCode() {
        return laborCode;
    }

    public void setLaborCode(String laborCode) {
        this.laborCode = laborCode;
    }

    public String getBjsQgrd() {
        return bjsQgrd;
    }

    public void setBjsQgrd(String bjsQgrd) {
        this.bjsQgrd = bjsQgrd;
    }

    public String getBmQgrd() {
        return bmQgrd;
    }

    public void setBmQgrd(String bmQgrd) {
        this.bmQgrd = bmQgrd;
    }

    public String getBmMad() {
        return bmMad;
    }

    public void setBmMad(String bmMad) {
        this.bmMad = bmMad;
    }

    public String getBmStad() {
        return bmStad;
    }

    public void setBmStad(String bmStad) {
        this.bmStad = bmStad;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getOpDate() {
        return opDate;
    }

    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getApprDate() {
        return apprDate;
    }

    public void setApprDate(String apprDate) {
        this.apprDate = apprDate;
    }

    public String getApprName() {
        return apprName;
    }

    public void setApprName(String apprName) {
        this.apprName = apprName;
    }

    public String getOpDesc() {
        return opDesc;
    }

    public void setOpDesc(String opDesc) {
        this.opDesc = opDesc;
    }

    public String getDeviceCode1() {
        return deviceCode1;
    }

    public void setDeviceCode1(String deviceCode1) {
        this.deviceCode1 = deviceCode1;
    }

    public String getDeviceCode2() {
        return deviceCode2;
    }

    public void setDeviceCode2(String deviceCode2) {
        this.deviceCode2 = deviceCode2;
    }

    public String getO2BumbNo1() {
        return o2BumbNo1;
    }

    public void setO2BumbNo1(String o2BumbNo1) {
        this.o2BumbNo1 = o2BumbNo1;
    }

    public String getDiffPercent1() {
        return diffPercent1;
    }

    public void setDiffPercent1(String diffPercent1) {
        this.diffPercent1 = diffPercent1;
    }

    public String getDiffPercent2() {
        return diffPercent2;
    }

    public void setDiffPercent2(String diffPercent2) {
        this.diffPercent2 = diffPercent2;
    }

    public String getO2BumbNo2() {
        return o2BumbNo2;
    }

    public void setO2BumbNo2(String o2BumbNo2) {
        this.o2BumbNo2 = o2BumbNo2;
    }

    public String getAutoNo11() {
        return autoNo11;
    }

    public void setAutoNo11(String autoNo11) {
        this.autoNo11 = autoNo11;
    }

    public String getAutoNo12() {
        return autoNo12;
    }

    public void setAutoNo12(String autoNo12) {
        this.autoNo12 = autoNo12;
    }

    public String getAutoNo13() {
        return autoNo13;
    }

    public void setAutoNo13(String autoNo13) {
        this.autoNo13 = autoNo13;
    }

    public String getAutoNo14() {
        return autoNo14;
    }

    public void setAutoNo14(String autoNo14) {
        this.autoNo14 = autoNo14;
    }

    public String getAutoNo15() {
        return autoNo15;
    }

    public void setAutoNo15(String autoNo15) {
        this.autoNo15 = autoNo15;
    }

    public String getAutoNo21() {
        return autoNo21;
    }

    public void setAutoNo21(String autoNo21) {
        this.autoNo21 = autoNo21;
    }

    public String getAutoNo22() {
        return autoNo22;
    }

    public void setAutoNo22(String autoNo22) {
        this.autoNo22 = autoNo22;
    }

    public String getAutoNo23() {
        return autoNo23;
    }

    public void setAutoNo23(String autoNo23) {
        this.autoNo23 = autoNo23;
    }

    public String getAutoNo24() {
        return autoNo24;
    }

    public void setAutoNo24(String autoNo24) {
        this.autoNo24 = autoNo24;
    }

    public String getAutoNo25() {
        return autoNo25;
    }

    public void setAutoNo25(String autoNo25) {
        this.autoNo25 = autoNo25;
    }

    public String getSamWeight11() {
        return samWeight11;
    }

    public void setSamWeight11(String samWeight11) {
        this.samWeight11 = samWeight11;
    }

    public String getSamWeight12() {
        return samWeight12;
    }

    public void setSamWeight12(String samWeight12) {
        this.samWeight12 = samWeight12;
    }

    public String getSamWeight13() {
        return samWeight13;
    }

    public void setSamWeight13(String samWeight13) {
        this.samWeight13 = samWeight13;
    }

    public String getSamWeight14() {
        return samWeight14;
    }

    public void setSamWeight14(String samWeight14) {
        this.samWeight14 = samWeight14;
    }

    public String getSamWeight15() {
        return samWeight15;
    }

    public void setSamWeight15(String samWeight15) {
        this.samWeight15 = samWeight15;
    }

    public String getSamWeight21() {
        return samWeight21;
    }

    public void setSamWeight21(String samWeight21) {
        this.samWeight21 = samWeight21;
    }

    public String getSamWeight22() {
        return samWeight22;
    }

    public void setSamWeight22(String samWeight22) {
        this.samWeight22 = samWeight22;
    }

    public String getSamWeight23() {
        return samWeight23;
    }

    public void setSamWeight23(String samWeight23) {
        this.samWeight23 = samWeight23;
    }

    public String getSamWeight24() {
        return samWeight24;
    }

    public void setSamWeight24(String samWeight24) {
        this.samWeight24 = samWeight24;
    }

    public String getSamWeight25() {
        return samWeight25;
    }

    public void setSamWeight25(String samWeight25) {
        this.samWeight25 = samWeight25;
    }

    public String getQb11() {
        return qb11;
    }

    public void setQb11(String qb11) {
        this.qb11 = qb11;
    }

    public String getQb12() {
        return qb12;
    }

    public void setQb12(String qb12) {
        this.qb12 = qb12;
    }

    public String getQb13() {
        return qb13;
    }

    public void setQb13(String qb13) {
        this.qb13 = qb13;
    }

    public String getQb14() {
        return qb14;
    }

    public void setQb14(String qb14) {
        this.qb14 = qb14;
    }

    public String getQb15() {
        return qb15;
    }

    public void setQb15(String qb15) {
        this.qb15 = qb15;
    }

    public String getQb21() {
        return qb21;
    }

    public void setQb21(String qb21) {
        this.qb21 = qb21;
    }

    public String getQb22() {
        return qb22;
    }

    public void setQb22(String qb22) {
        this.qb22 = qb22;
    }

    public String getQb23() {
        return qb23;
    }

    public void setQb23(String qb23) {
        this.qb23 = qb23;
    }

    public String getQb24() {
        return qb24;
    }

    public void setQb24(String qb24) {
        this.qb24 = qb24;
    }

    public String getQb25() {
        return qb25;
    }

    public void setQb25(String qb25) {
        this.qb25 = qb25;
    }

    public String getReSamWeight11() {
        return reSamWeight11;
    }

    public void setReSamWeight11(String reSamWeight11) {
        this.reSamWeight11 = reSamWeight11;
    }

    public String getReSamWeight12() {
        return reSamWeight12;
    }

    public void setReSamWeight12(String reSamWeight12) {
        this.reSamWeight12 = reSamWeight12;
    }

    public String getReSamWeight13() {
        return reSamWeight13;
    }

    public void setReSamWeight13(String reSamWeight13) {
        this.reSamWeight13 = reSamWeight13;
    }

    public String getReSamWeight14() {
        return reSamWeight14;
    }

    public void setReSamWeight14(String reSamWeight14) {
        this.reSamWeight14 = reSamWeight14;
    }

    public String getReSamWeight15() {
        return reSamWeight15;
    }

    public void setReSamWeight15(String reSamWeight15) {
        this.reSamWeight15 = reSamWeight15;
    }

    public String getReSamWeight21() {
        return reSamWeight21;
    }

    public void setReSamWeight21(String reSamWeight21) {
        this.reSamWeight21 = reSamWeight21;
    }

    public String getReSamWeight22() {
        return reSamWeight22;
    }

    public void setReSamWeight22(String reSamWeight22) {
        this.reSamWeight22 = reSamWeight22;
    }

    public String getReSamWeight23() {
        return reSamWeight23;
    }

    public void setReSamWeight23(String reSamWeight23) {
        this.reSamWeight23 = reSamWeight23;
    }

    public String getReSamWeight24() {
        return reSamWeight24;
    }

    public void setReSamWeight24(String reSamWeight24) {
        this.reSamWeight24 = reSamWeight24;
    }

    public String getReSamWeight25() {
        return reSamWeight25;
    }

    public void setReSamWeight25(String reSamWeight25) {
        this.reSamWeight25 = reSamWeight25;
    }

    public String getReQbad11() {
        return reQbad11;
    }

    public void setReQbad11(String reQbad11) {
        this.reQbad11 = reQbad11;
    }

    public String getReQbad12() {
        return reQbad12;
    }

    public void setReQbad12(String reQbad12) {
        this.reQbad12 = reQbad12;
    }

    public String getReQbad13() {
        return reQbad13;
    }

    public void setReQbad13(String reQbad13) {
        this.reQbad13 = reQbad13;
    }

    public String getReQbad14() {
        return reQbad14;
    }

    public void setReQbad14(String reQbad14) {
        this.reQbad14 = reQbad14;
    }

    public String getReQbad15() {
        return reQbad15;
    }

    public void setReQbad15(String reQbad15) {
        this.reQbad15 = reQbad15;
    }

    public String getReQbad21() {
        return reQbad21;
    }

    public void setReQbad21(String reQbad21) {
        this.reQbad21 = reQbad21;
    }

    public String getReQbad22() {
        return reQbad22;
    }

    public void setReQbad22(String reQbad22) {
        this.reQbad22 = reQbad22;
    }

    public String getReQbad23() {
        return reQbad23;
    }

    public void setReQbad23(String reQbad23) {
        this.reQbad23 = reQbad23;
    }

    public String getReQbad24() {
        return reQbad24;
    }

    public void setReQbad24(String reQbad24) {
        this.reQbad24 = reQbad24;
    }

    public String getReQbad25() {
        return reQbad25;
    }

    public void setReQbad25(String reQbad25) {
        this.reQbad25 = reQbad25;
    }

    public String getReQgrd11() {
        return reQgrd11;
    }

    public void setReQgrd11(String reQgrd11) {
        this.reQgrd11 = reQgrd11;
    }

    public String getReQgrd12() {
        return reQgrd12;
    }

    public void setReQgrd12(String reQgrd12) {
        this.reQgrd12 = reQgrd12;
    }

    public String getReQgrd13() {
        return reQgrd13;
    }

    public void setReQgrd13(String reQgrd13) {
        this.reQgrd13 = reQgrd13;
    }

    public String getReQgrd14() {
        return reQgrd14;
    }

    public void setReQgrd14(String reQgrd14) {
        this.reQgrd14 = reQgrd14;
    }

    public String getReQgrd15() {
        return reQgrd15;
    }

    public void setReQgrd15(String reQgrd15) {
        this.reQgrd15 = reQgrd15;
    }

    public String getReQgrd21() {
        return reQgrd21;
    }

    public void setReQgrd21(String reQgrd21) {
        this.reQgrd21 = reQgrd21;
    }

    public String getReQgrd22() {
        return reQgrd22;
    }

    public void setReQgrd22(String reQgrd22) {
        this.reQgrd22 = reQgrd22;
    }

    public String getReQgrd23() {
        return reQgrd23;
    }

    public void setReQgrd23(String reQgrd23) {
        this.reQgrd23 = reQgrd23;
    }

    public String getReQgrd24() {
        return reQgrd24;
    }

    public void setReQgrd24(String reQgrd24) {
        this.reQgrd24 = reQgrd24;
    }

    public String getReQgrd25() {
        return reQgrd25;
    }

    public void setReQgrd25(String reQgrd25) {
        this.reQgrd25 = reQgrd25;
    }

    public String getRsd1() {
        return rsd1;
    }

    public void setRsd1(String rsd1) {
        this.rsd1 = rsd1;
    }

    public String getRsd2() {
        return rsd2;
    }

    public void setRsd2(String rsd2) {
        this.rsd2 = rsd2;
    }

    public String getQbAvg1() {
        return qbAvg1;
    }

    public void setQbAvg1(String qbAvg1) {
        this.qbAvg1 = qbAvg1;
    }

    public String getQbAvg2() {
        return qbAvg2;
    }

    public void setQbAvg2(String qbAvg2) {
        this.qbAvg2 = qbAvg2;
    }

    public String getReSamWeightAvg1() {
        return reSamWeightAvg1;
    }

    public void setReSamWeightAvg1(String reSamWeightAvg1) {
        this.reSamWeightAvg1 = reSamWeightAvg1;
    }

    public String getReSamWeightAvg2() {
        return reSamWeightAvg2;
    }

    public void setReSamWeightAvg2(String reSamWeightAvg2) {
        this.reSamWeightAvg2 = reSamWeightAvg2;
    }

    public String getReQbadAvg1() {
        return reQbadAvg1;
    }

    public void setReQbadAvg1(String reQbadAvg1) {
        this.reQbadAvg1 = reQbadAvg1;
    }

    public String getReQbadAvg2() {
        return reQbadAvg2;
    }

    public void setReQbadAvg2(String reQbadAvg2) {
        this.reQbadAvg2 = reQbadAvg2;
    }

    public String getReQgrdAvg1() {
        return reQgrdAvg1;
    }

    public void setReQgrdAvg1(String reQgrdAvg1) {
        this.reQgrdAvg1 = reQgrdAvg1;
    }

    public String getReQgrdAvg2() {
        return reQgrdAvg2;
    }

    public void setReQgrdAvg2(String reQgrdAvg2) {
        this.reQgrdAvg2 = reQgrdAvg2;
    }

}
