package org.gxz.znrl.entity;

/**
 * Created by xieyt on 14-12-25.
 */
public class RegisterEntity {
    //具体信息
    public String recordNo;//入厂流水
    public String trainNo;//车次号
    public String trainNoAlias;//车次号
    public String carId;//车号
    public String oldCarId;//原车号
    public String colryNo;//矿点
    public String coalType;//煤矿id
    public String coalNo;//煤质类型
    public String leaveDtm;//发货日期
    public String recordDtm;//入厂时间
    public String czDtm;//称重时间
    public String tickQty;//票重
    public String mzQty;//毛重
    public String totalMzQty;//总毛重
    public String pzQty;//皮重
    public String netQty;//净重
    public String bzQty;
    public String totalNetQty;//总毛重
    public String totalTickQty;//总票重
    public String ydQty;//盈吨
    public String kudQty;//亏吨
    public String lossQty;//运损
    public String fcTime;//翻车时间
    public String batchNo;//批次号
    public String balanceNo;//轨道号
    public String carTyp;//车型
    public String remark;//备注
    public String trackNo;//火车入厂的轨道号 3#， 5# 。。。
    public String sampleCode;
    public String manCheckSample;//抽检样采样编码
    public String attachBatchNo;
    public String sampleTyp;//采样方式 0在线采样  1离线采样
    public String emptyFlg;//是否为空 0是空  1非空
    public String xhNum;//车厢序号
    public String sampleTypName;//采样方式 0在线采样  1离线采样
    public String emptyFlgName;//是否为空 0是空  1非空

    public String carCnt;//车数
    public String venNo;//供应商ID
    public String beginTime;//入厂开始时间
    public String endTime;//入厂结束时间
    public String mineName;//煤矿名称
    public String coalName;//煤种名称
    public String venName;//供应商

    public String startPlace;//始发站
    public String startPlaceName;//始发站
    public String finalPlace;//到站
    public String finalPlaceName;//到站

    public String speed;//过衡速度
    public String aimTime;//对位时间

    public String goodsReceiver;//收货单位
    public String deliverTime;//发站时间

    public String contractNo;//合同编号
    public String contractName;//合同名称

    //以json字符串的方式传入存储过程
    public String insertString;
    public String updateString;
    public String deleteString;
    public String publicString;
    public String opCode;

    public String updateCode;
    public String updateName;

    //返回操作结果
    public String resCode;
    public String resMsg;

    //返回操作结果
    public String purchaseType;//采购类型
    public String contractType;//合同类型
    public String provinceNo;//身份编号

    private String uploadStatus ;
    private String cfmFlg ;  //借用给修改标志
    public String updateTime;
    public String totalYdQty;//总盈吨
    public String totalKudQty;//总亏吨
    public String totalLossQty;//总亏吨
    public String ykd;
    public String getYkd() {
        return ykd;
    }

    public void setYkd(String ykd) {
        this.ykd = ykd;
    }

    public String getProvinceNo() {
        return provinceNo;
    }

    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getAttachBatchNo() {
        return attachBatchNo;
    }

    public void setAttachBatchNo(String attachBatchNo) {
        this.attachBatchNo = attachBatchNo;
    }

    public String getManCheckSample() {
        return manCheckSample;
    }

    public void setManCheckSample(String manCheckSample) {
        this.manCheckSample = manCheckSample;
    }

    public String getBzQty() {
        return bzQty;
    }

    public void setBzQty(String bzQty) {
        this.bzQty = bzQty;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getGoodsReceiver() {
        return goodsReceiver;
    }

    public void setGoodsReceiver(String goodsReceiver) {
        this.goodsReceiver = goodsReceiver;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getCzDtm() {
        return czDtm;
    }

    public void setCzDtm(String czDtm) {
        this.czDtm = czDtm;
    }

    public String getTrainNoAlias() {
        return trainNoAlias;
    }

    public void setTrainNoAlias(String trainNoAlias) {
        this.trainNoAlias = trainNoAlias;
    }

    public String getTotalMzQty() {
        return totalMzQty;
    }

    public void setTotalMzQty(String totalMzQty) {
        this.totalMzQty = totalMzQty;
    }

    public String getTotalNetQty() {
        return totalNetQty;
    }

    public void setTotalNetQty(String totalNetQty) {
        this.totalNetQty = totalNetQty;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getStartPlaceName() {
        return startPlaceName;
    }

    public void setStartPlaceName(String startPlaceName) {
        this.startPlaceName = startPlaceName;
    }

    public String getFinalPlace() {
        return finalPlace;
    }

    public void setFinalPlace(String finalPlace) {
        this.finalPlace = finalPlace;
    }

    public String getFinalPlaceName() {
        return finalPlaceName;
    }

    public void setFinalPlaceName(String finalPlaceName) {
        this.finalPlaceName = finalPlaceName;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getAimTime() {
        return aimTime;
    }

    public void setAimTime(String aimTime) {
        this.aimTime = aimTime;
    }

    public String getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo;
    }

    public String getSampleTypName() {
        return sampleTypName;
    }

    public void setSampleTypName(String sampleTypName) {
        this.sampleTypName = sampleTypName;
    }

    public String getEmptyFlgName() {
        return emptyFlgName;
    }

    public void setEmptyFlgName(String emptyFlgName) {
        this.emptyFlgName = emptyFlgName;
    }

    public String getSampleTyp() {
        return sampleTyp;
    }

    public void setSampleTyp(String sampleTyp) {
        this.sampleTyp = sampleTyp;
    }

    public String getEmptyFlg() {
        return emptyFlg;
    }

    public void setEmptyFlg(String emptyFlg) {
        this.emptyFlg = emptyFlg;
    }

    public String getXhNum() {
        return xhNum;
    }

    public void setXhNum(String xhNum) {
        this.xhNum = xhNum;
    }

    public String getMineName() {
        return mineName;
    }

    public void setMineName(String mineName) {
        this.mineName = mineName;
    }

    public String getCoalName() {
        return coalName;
    }

    public void setCoalName(String coalName) {
        this.coalName = coalName;
    }

    public String getVenName() {
        return venName;
    }

    public void setVenName(String venName) {
        this.venName = venName;
    }

    public String getVenNo() {
        return venNo;
    }

    public void setVenNo(String venNo) {
        this.venNo = venNo;
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

    public String getCarCnt() {
        return carCnt;
    }

    public void setCarCnt(String carCnt) {
        this.carCnt = carCnt;
    }

    public String getCoalNo() {
        return coalNo;
    }

    public void setCoalNo(String coalNo) {
        this.coalNo = coalNo;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getOldCarId() {
        return oldCarId;
    }

    public void setOldCarId(String oldCarId) {
        this.oldCarId = oldCarId;
    }

    public String getColryNo() {
        return colryNo;
    }

    public void setColryNo(String colryNo) {
        this.colryNo = colryNo;
    }

    public String getCoalType() {
        return coalType;
    }

    public void setCoalType(String coalType) {
        this.coalType = coalType;
    }

    public String getLeaveDtm() {
        return leaveDtm;
    }

    public void setLeaveDtm(String leaveDtm) {
        this.leaveDtm = leaveDtm;
    }

    public String getRecordDtm() {
        return recordDtm;
    }

    public void setRecordDtm(String recordDtm) {
        this.recordDtm = recordDtm;
    }

    public String getTickQty() {
        return tickQty;
    }

    public void setTickQty(String tickQty) {
        this.tickQty = tickQty;
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

    public String getYdQty() {
        return ydQty;
    }

    public void setYdQty(String ydQty) {
        this.ydQty = ydQty;
    }

    public String getLossQty() {
        return lossQty;
    }

    public void setLossQty(String lossQty) {
        this.lossQty = lossQty;
    }

    public String getFcTime() {
        return fcTime;
    }

    public void setFcTime(String fcTime) {
        this.fcTime = fcTime;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBalanceNo() {
        return balanceNo;
    }

    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    public String getCarTyp() {
        return carTyp;
    }

    public void setCarTyp(String carTyp) {
        this.carTyp = carTyp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
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

    public String getPublicString() {
        return publicString;
    }

    public void setPublicString(String publicString) {
        this.publicString = publicString;
    }

    public String getInsertString() {
        return insertString;
    }

    public void setInsertString(String insertString) {
        this.insertString = insertString;
    }

    public String getUpdateString() {
        return updateString;
    }

    public void setUpdateString(String updateString) {
        this.updateString = updateString;
    }

    public String getDeleteString() {
        return deleteString;
    }

    public void setDeleteString(String deleteString) {
        this.deleteString = deleteString;
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

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getTotalTickQty() {
        return totalTickQty;
    }

    public void setTotalTickQty(String totalTickQty) {
        this.totalTickQty = totalTickQty;
    }


    public String getContractNo() { return contractNo;  }

    public void setContractNo(String contractNo) {this.contractNo = contractNo;    }

    public String getContractName() { return contractName; }

    public void setContractNamey(String contractName) {  this.contractName = contractName; }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getCfmFlg() {
        return cfmFlg;
    }

    public void setCfmFlg(String cfmFlg) {
        this.cfmFlg = cfmFlg;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getKudQty() {
        return kudQty;
    }

    public void setKudQty(String kudQty) {
        this.kudQty = kudQty;
    }

    public String getTotalYdQty() {
        return totalYdQty;
    }

    public void setTotalYdQty(String totalYdQty) {
        this.totalYdQty = totalYdQty;
    }

    public String getTotalKudQty() {
        return totalKudQty;
    }

    public void setTotalKudQty(String totalKudQty) {
        this.totalKudQty = totalKudQty;
    }

    public String getTotalLossdQty() {
        return totalLossQty;
    }

    public void setTotalLossQty(String totalLossQty) {
        this.totalLossQty = totalLossQty;
    }
}
