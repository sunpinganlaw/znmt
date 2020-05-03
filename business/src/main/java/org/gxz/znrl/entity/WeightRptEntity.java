package org.gxz.znrl.entity;

/**
 * Created by xieyt on 14-12-5.
 */
public class WeightRptEntity extends BaseEntity {
    public String recordNo;//入厂序号
    public String beginTime; //开始时间
    public String endTime; //结束时间

    public String leaveBeginTime; //出厂开始时间
    public String leaveEndTime; //出厂结束时间
    public String czBeginTime; //称重开始时间
    public String czEndTime; //称重结束时间

    public String venNo;//供煤单位ID
    public String venNam;//供煤单位
    public String carrierNo;//运输单位ID
    public String carrierNam;//运输单位
    public String carId;//车牌号
    public String carTyp;//车型编码   0半挂  1外挂
    public String carTypeName;//车型
    public String transTyp;//车辆类型 0煤车 1灰车 2其他 5厂内倒运煤
    public String transTypeName;//车辆类型
    public String planMktNo;//计划口径ID ？
    public String planMktName;//计划口径 ？
    public String positionNo;//位置
    public String positionNam;//位置
    public String coalNo;//煤种
    public String coalNam;//煤种名称
    public String colryNam;//矿点
    public String startPlace;//始发站
    public String startPlaceName;//始发站

    public String bottomHeight;//车底高度
    public String maxLoad;//最大载重
    public String entryOrder;//入厂顺序
    public String doorNo;//入厂位置
    public String recordDtm;//入厂时间
    public String curPos;//车的当前位置
    public String nextPos;//车的下一个位置

    public String smplStartTime;//采样开始时间
    public String smplEndTime;//采样结束时间
    public String machineCode;//采样机
    public String sampleUserName;//采样人

    public String czDtm;//称重时间
    public String jqDtm;//称轻时间
    public String mzQty;//毛重
    public String kdQty;//扣吨
    public String kgQty;//扣吨
    public String pzQty;//皮重
    public String netQty;//净重
    public String tickQty;//票重
    public String ykQty;//盈亏
    public String ysQty;//验收重量
    public String jqBalaNo;//回皮位置
    public String czBalaNo;//称重位置
    public String status;//1采样未称重  2称重未称轻  3已经称轻
    public String batchNo;//批次号
    public String xhNum;//车厢序号
    public String balanceNo;//火车过衡序号


    /////////////////////汇总报表数据
    public String totalEntryCnt;//入厂车数
    public String totalCZCnt;//称重车数
    public String totalJQCnt;//称轻车数
    public String totalSampleCnt;//采样车数
    public String totalDenyCnt;//拒收车数
    public String totalCheckInCnt;//验收车数
    public String totalCheckInTunCnt;//回皮位置

    public String totalMzQty;//总毛重
    public String totalKdQty;//总扣吨
    public String totalPzQty;//总皮重
    public String totalNetQty;//总净重
    public String totalTickQty;//总票重(矿发数量)
    public String totalHpQty;//回皮总重量

    public String carCount;//当天发货商车辆总数

    public String coalMine;

    public String tickNo;

    public String kdOpName;//扣吨人
    public String kdStatus;//扣吨状态 0-未扣吨  1-已扣吨
    public String mineCardType;//矿卡类型
    public String noKdNetQty;
    public String diffQty;
    public String totalDiffQty;
    public String totalNoKdNetQty;
    public String diffRatio;
    public String batchNoType;//批次类型}
    public String powerFactory;//电厂    ZJ织金

    public String shipNo;//船名ID
    public String shipName;//船名名称
    public String trainNo;

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }


    public String getPowerFactory() {
        return powerFactory;
    }

    public void setPowerFactory(String powerFactory) {
        this.powerFactory = powerFactory;
    }

    public String getKgQty() {
        return kgQty;
    }

    public void setKgQty(String kgQty) {
        this.kgQty = kgQty;
    }

    public String getBatchNoType() {
        return batchNoType;
    }

    public void setBatchNoType(String batchNoType) {
        this.batchNoType = batchNoType;
    }

    public String getDiffRatio() {
        return diffRatio;
    }

    public void setDiffRatio(String diffRatio) {
        this.diffRatio = diffRatio;
    }

    public String getTotalDiffQty() {
        return totalDiffQty;
    }

    public void setTotalDiffQty(String totalDiffQty) {
        this.totalDiffQty = totalDiffQty;
    }

    public String getDiffQty() {
        return diffQty;
    }

    public void setDiffQty(String diffQty) {
        this.diffQty = diffQty;
    }

    public String getTotalNoKdNetQty() {
        return totalNoKdNetQty;
    }

    public void setTotalNoKdNetQty(String totalNoKdNetQty) {
        this.totalNoKdNetQty = totalNoKdNetQty;
    }


    public String getNoKdNetQty() {
        return noKdNetQty;
    }

    public void setNoKdNetQty(String noKdNetQty) {
        this.noKdNetQty = noKdNetQty;
    }

    public String getTickNo() {
        return tickNo;
    }

    public void setTickNo(String tickNo) {
        this.tickNo = tickNo;
    }

    public String getYsQty() {
        return ysQty;
    }

    public void setYsQty(String ysQty) {
        this.ysQty = ysQty;
    }

    public String getCarCount() {
        return carCount;
    }

    public void setCarCount(String carCount) {
        this.carCount = carCount;
    }

    public String getXhNum() {
        return xhNum;
    }

    public void setXhNum(String xhNum) {
        this.xhNum = xhNum;
    }

    public String getBalanceNo() {
        return balanceNo;
    }

    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    public String getCoalMine() {
        return coalMine;
    }

    public void setCoalMine(String coalMine) {
        this.coalMine = coalMine;
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

    public String getCurPos() {
        return curPos;
    }

    public void setCurPos(String curPos) {
        this.curPos = curPos;
    }

    public String getNextPos() {
        return nextPos;
    }

    public void setNextPos(String nextPos) {
        this.nextPos = nextPos;
    }

    public String getSmplStartTime() {
        return smplStartTime;
    }

    public void setSmplStartTime(String smplStartTime) {
        this.smplStartTime = smplStartTime;
    }

    public String getSmplEndTime() {
        return smplEndTime;
    }

    public void setSmplEndTime(String smplEndTime) {
        this.smplEndTime = smplEndTime;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getSampleUserName() {
        return sampleUserName;
    }

    public void setSampleUserName(String sampleUserName) {
        this.sampleUserName = sampleUserName;
    }

    public String getTotalMzQty() {
        return totalMzQty;
    }

    public void setTotalMzQty(String totalMzQty) {
        this.totalMzQty = totalMzQty;
    }

    public String getTotalKdQty() {
        return totalKdQty;
    }

    public void setTotalKdQty(String totalKdQty) {
        this.totalKdQty = totalKdQty;
    }

    public String getTotalPzQty() {
        return totalPzQty;
    }

    public void setTotalPzQty(String totalPzQty) {
        this.totalPzQty = totalPzQty;
    }

    public String getTotalNetQty() {
        return totalNetQty;
    }

    public void setTotalNetQty(String totalNetQty) {
        this.totalNetQty = totalNetQty;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalEntryCnt() {
        return totalEntryCnt;
    }

    public void setTotalEntryCnt(String totalEntryCnt) {
        this.totalEntryCnt = totalEntryCnt;
    }

    public String getTotalCZCnt() {
        return totalCZCnt;
    }

    public void setTotalCZCnt(String totalCZCnt) {
        this.totalCZCnt = totalCZCnt;
    }

    public String getTotalJQCnt() {
        return totalJQCnt;
    }

    public void setTotalJQCnt(String totalJQCnt) {
        this.totalJQCnt = totalJQCnt;
    }

    public String getTotalSampleCnt() {
        return totalSampleCnt;
    }

    public void setTotalSampleCnt(String totalSampleCnt) {
        this.totalSampleCnt = totalSampleCnt;
    }

    public String getTotalDenyCnt() {
        return totalDenyCnt;
    }

    public void setTotalDenyCnt(String totalDenyCnt) {
        this.totalDenyCnt = totalDenyCnt;
    }

    public String getTotalCheckInCnt() {
        return totalCheckInCnt;
    }

    public void setTotalCheckInCnt(String totalCheckInCnt) {
        this.totalCheckInCnt = totalCheckInCnt;
    }

    public String getTotalCheckInTunCnt() {
        return totalCheckInTunCnt;
    }

    public void setTotalCheckInTunCnt(String totalCheckInTunCnt) {
        this.totalCheckInTunCnt = totalCheckInTunCnt;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
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

    public String getVenNo() {
        return venNo;
    }

    public void setVenNo(String venNo) {
        this.venNo = venNo;
    }

    public String getVenNam() {
        return venNam;
    }

    public void setVenNam(String venNam) {
        this.venNam = venNam;
    }

    public String getCarrierNo() {
        return carrierNo;
    }

    public void setCarrierNo(String carrierNo) {
        this.carrierNo = carrierNo;
    }

    public String getCarrierNam() {
        return carrierNam;
    }

    public void setCarrierNam(String carrierNam) {
        this.carrierNam = carrierNam;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarTyp() {
        return carTyp;
    }

    public void setCarTyp(String carTyp) {
        this.carTyp = carTyp;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getTransTyp() {
        return transTyp;
    }

    public void setTransTyp(String transTyp) {
        this.transTyp = transTyp;
    }

    public String getTransTypeName() {
        return transTypeName;
    }

    public void setTransTypeName(String transTypeName) {
        this.transTypeName = transTypeName;
    }

    public String getPlanMktNo() {
        return planMktNo;
    }

    public void setPlanMktNo(String planMktNo) {
        this.planMktNo = planMktNo;
    }

    public String getPlanMktName() {
        return planMktName;
    }

    public void setPlanMktName(String planMktName) {
        this.planMktName = planMktName;
    }

    public String getPositionNo() {
        return positionNo;
    }

    public void setPositionNo(String positionNo) {
        this.positionNo = positionNo;
    }

    public String getPositionNam() {
        return positionNam;
    }

    public void setPositionNam(String positionNam) {
        this.positionNam = positionNam;
    }

    public String getCoalNo() {
        return coalNo;
    }

    public String getColryNam() {
        return colryNam;
    }

    public void setColryNam(String colryNam) {
        this.colryNam = colryNam;
    }

    public void setCoalNo(String coalNo) {
        this.coalNo = coalNo;
    }

    public String getCoalNam() {
        return coalNam;
    }

    public void setCoalNam(String coalNam) {
        this.coalNam = coalNam;
    }



    public String getBottomHeight() {
        return bottomHeight;
    }

    public void setBottomHeight(String bottomHeight) {
        this.bottomHeight = bottomHeight;
    }

    public String getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(String maxLoad) {
        this.maxLoad = maxLoad;
    }

    public String getEntryOrder() {
        return entryOrder;
    }

    public void setEntryOrder(String entryOrder) {
        this.entryOrder = entryOrder;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getRecordDtm() {
        return recordDtm;
    }

    public void setRecordDtm(String recordDtm) {
        this.recordDtm = recordDtm;
    }

    public String getCzDtm() {
        return czDtm;
    }

    public void setCzDtm(String czDtm) {
        this.czDtm = czDtm;
    }

    public String getJqDtm() {
        return jqDtm;
    }

    public void setJqDtm(String jqDtm) {
        this.jqDtm = jqDtm;
    }

    public String getMzQty() {
        return mzQty;
    }

    public void setMzQty(String mzQty) {
        this.mzQty = mzQty;
    }

    public String getKdQty() {
        return kdQty;
    }

    public void setKdQty(String kdQty) {
        this.kdQty = kdQty;
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

    public String getTotalTickQty() {
        return totalTickQty;
    }

    public void setTotalTickQty(String totalTickQty) {
        this.totalTickQty = totalTickQty;
    }

    public String getJqBalaNo() {
        return jqBalaNo;
    }

    public void setJqBalaNo(String jqBalaNo) {
        this.jqBalaNo = jqBalaNo;
    }

    public String getTickQty() {
        return tickQty;
    }

    public void setTickQty(String tickQty) {
        this.tickQty = tickQty;
    }

    public String getYkQty() {
        return ykQty;
    }

    public void setYkQty(String ykQty) {
        this.ykQty = ykQty;
    }

    public String getLeaveBeginTime() {
        return leaveBeginTime;
    }

    public void setLeaveBeginTime(String leaveBeginTime) {
        this.leaveBeginTime = leaveBeginTime;
    }

    public String getLeaveEndTime() {
        return leaveEndTime;
    }

    public void setLeaveEndTime(String leaveEndTime) {
        this.leaveEndTime = leaveEndTime;
    }

    public String getCzBeginTime() {
        return czBeginTime;
    }

    public void setCzBeginTime(String czBeginTime) {
        this.czBeginTime = czBeginTime;
    }

    public String getCzEndTime() {
        return czEndTime;
    }

    public void setCzEndTime(String czEndTime) {
        this.czEndTime = czEndTime;
    }

    public String getCzBalaNo() {
        return czBalaNo;
    }

    public void setCzBalaNo(String czBalaNo) {
        this.czBalaNo = czBalaNo;
    }

    public String getKdOpName() {
        return kdOpName;
    }

    public void setKdOpName(String kdOpName) {
        this.kdOpName = kdOpName;
    }

    public String getKdStatus() {
        return kdStatus;
    }

    public void setKdStatus(String kdStatus) {
        this.kdStatus = kdStatus;
    }

    public String getMineCardType() {
        return mineCardType;
    }

    public void setMineCardType(String mineCardType) {
        this.mineCardType = mineCardType;
    }
	
	    public String getTotalHpQty() {
        return totalHpQty;
    }

    public void setTotalHpQty(String totalHpQty) {
        this.totalHpQty = totalHpQty;
    }

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

}
