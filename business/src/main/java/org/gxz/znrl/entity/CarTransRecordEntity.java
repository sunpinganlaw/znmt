package org.gxz.znrl.entity;


public class CarTransRecordEntity extends BaseEntity{
    public String recordId;//主键ID
    public String recordNo;//入厂序号，也做流水号
    public String cardId;//卡号
    public String cardTyp;//卡类型
    public String cardTypeName;//卡类型
    public String carId;//前车牌号
    public String backCarId;//后车牌号
    public String inNo;//入场序列号
    public String outNo;//出场序列号



    public String inDoorNo;//入厂通道号
    public String outDoorNo;//出厂通道号

    public String stationNo;//场站号

    public String contactIdFront;//集装箱前箱号

    public String contactIdBack;//集装箱后箱号
    public String contactSize;//集装箱尺寸
    public String contactType;//集装箱类型

    public String carTyp;//车型
    public String carTypeName;//车型名称


    public String carrierNo;//运输单位id
    public String orgNo;
    public String carrierName;//运输单位
    public String venNo;//供应商
    public String venName;//供应商名称

    public String tickNo;//提货单号
    public String tickQty;//票重


    public String recordDtm;//记录时间
    public String czDtm;//称重时间
    public String remark;//备注
    public String batchNo;//批次号

    public String beginDate;
    public String endDate;



    public String beginLeaveDate;
    public String endLeaveDate;







    public String insertTime;//插入数据时间
    public String opCode;//操作人员

    public String doActionTag;//动作标识 ADD, MOD
    public String jsonString;//json字符串

    //返回操作结果
    public String resCode;
    public String resMsg;

    private String mzQty;//毛重
    private String pzQty;//皮重
    private String netQty;//净重
    private String kdQty;//扣吨
    private String attachBatchNo;//采集序号 卡扣上传过来的唯一编码

    private String doorNo;//通道号
    private String channel;
    private String actionTag;
    private String readTag;

    private String ghIndex;

    private String startPlace;
    private String finalPlace;
    private String startName;
    private String finalName;

    private String sampleType;
    public String sampleDtm;//采样厂时间
    public String jqDtm;//称轻时间
    public String carStatus;//车辆状态
    public String sampleCode;
    public String updateString;
    public String publicString;
    private String batchNoType;
    public String totalEntryCnt;//入厂车数
    public String totalMzQty;//总毛重
    public String totalPzQty;//总皮重
    public String totalKdQty;//总扣吨
    public String totalNetQty;//总净重
    public String totalTickQty;//总票重(矿发数量)
    public String apprEventTypeCd;

    public String mineCardType;//矿卡类型
    public String trainNo;

    public String shipName;//船名称
    public String goodTypeName;//货物类型名称

    public String shipNo;//航次
    public String goodName;//货物名称

    public String goodNo;//货物编号

    public String shipId;//船ID

    public String czBalanceNo;//称重通道号

    public String jqBalanceNo;//称轻通道号

    public String getLeaveFlag() {
        return leaveFlag;
    }

    public void setLeaveFlag(String leaveFlag) {
        this.leaveFlag = leaveFlag;
    }

    public String leaveFlag ;//出厂海关确认

    public String getCfmFlag() {
        return cfmFlag;
    }

    public void setCfmFlag(String cfmFlag) {
        this.cfmFlag = cfmFlag;
    }

    public String cfmFlag ;//进场厂海关确认


    public String getInNo() {
        return inNo;
    }

    public void setInNo(String inNo) {
        this.inNo = inNo;
    }

    public String getOutNo() {
        return outNo;
    }

    public void setOutNo(String outNo) {
        this.outNo = outNo;
    }

    public String getInDoorNo() {
        return inDoorNo;
    }

    public void setInDoorNo(String inDoorNo) {
        this.inDoorNo = inDoorNo;
    }

    public String getOutDoorNo() {
        return outDoorNo;
    }

    public void setOutDoorNo(String outDoorNo) {
        this.outDoorNo = outDoorNo;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public String getContactIdFront() {
        return contactIdFront;
    }

    public void setContactIdFront(String contactIdFront) {
        this.contactIdFront = contactIdFront;
    }

    public String getContactIdBack() {
        return contactIdBack;
    }

    public void setContactIdBack(String contactIdBack) {
        this.contactIdBack = contactIdBack;
    }

    public String getContactSize() {
        return contactSize;
    }

    public void setContactSize(String contactSize) {
        this.contactSize = contactSize;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }


    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodNo() {
        return goodNo;
    }

    public void setGoodNo(String goodNo) {
        this.goodNo = goodNo;
    }

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo;
    }



    public String getGoodTypeName() {
        return goodTypeName;
    }

    public void setGoodTypeName(String goodTypeName) {
        this.goodTypeName = goodTypeName;
    }




    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }


    public String getCzBalanceNo() {
        return czBalanceNo;
    }

    public void setCzBalanceNo(String czBalanceNo) {
        this.czBalanceNo = czBalanceNo;
    }

    public String getJqBalanceNo() {
        return jqBalanceNo;
    }

    public void setJqBalanceNo(String jqBalanceNo) {
        this.jqBalanceNo = jqBalanceNo;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getMineCardType() {
        return mineCardType;
    }

    public void setMineCardType(String mineCardType) {
        this.mineCardType = mineCardType;
    }

    public String getApprEventTypeCd() {
        return apprEventTypeCd;
    }

    public void setApprEventTypeCd(String apprEventTypeCd) {
        this.apprEventTypeCd = apprEventTypeCd;
    }

    public String getTotalEntryCnt() {
        return totalEntryCnt;
    }

    public void setTotalEntryCnt(String totalEntryCnt) {
        this.totalEntryCnt = totalEntryCnt;
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

    public String getTotalTickQty() {
        return totalTickQty;
    }

    public void setTotalTickQty(String totalTickQty) {
        this.totalTickQty = totalTickQty;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getUpdateString() {
        return updateString;
    }

    public void setUpdateString(String updateString) {
        this.updateString = updateString;
    }

    public String getPublicString() {
        return publicString;
    }

    public void setPublicString(String publicString) {
        this.publicString = publicString;
    }

    public String getBatchNoType() {
        return batchNoType;
    }

    public void setBatchNoType(String batchNoType) {
        this.batchNoType = batchNoType;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getFinalPlace() {
        return finalPlace;
    }

    public void setFinalPlace(String finalPlace) {
        this.finalPlace = finalPlace;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public String getFinalName() {
        return finalName;
    }

    public void setFinalName(String finalName) {
        this.finalName = finalName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public String getKdQty() {
        return kdQty;
    }

    public void setKdQty(String kdQty) {
        this.kdQty = kdQty;
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

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDoActionTag() {
        return doActionTag;
    }

    public void setDoActionTag(String doActionTag) {
        this.doActionTag = doActionTag;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
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



    public String getBackCarId() {
        return backCarId;
    }

    public void setBackCarId(String backCarId) {
        this.backCarId = backCarId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardTyp() {
        return cardTyp;
    }

    public void setCardTyp(String cardTyp) {
        this.cardTyp = cardTyp;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
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



    public String getCarrierNo() {
        return carrierNo;
    }

    public void setCarrierNo(String carrierNo) {
        this.carrierNo = carrierNo;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getVenNo() {
        return venNo;
    }

    public void setVenNo(String venNo) {
        this.venNo = venNo;
    }

    public String getVenName() {
        return venName;
    }

    public void setVenName(String venName) {
        this.venName = venName;
    }


    public String getTickNo() {
        return tickNo;
    }

    public void setTickNo(String tickNo) {
        this.tickNo = tickNo;
    }

    public String getTickQty() {
        return tickQty;
    }

    public void setTickQty(String tickQty) {
        this.tickQty = tickQty;
    }

    public String getRecordDtm() {
        return recordDtm;
    }

    public void setRecordDtm(String recordDtm) {
        this.recordDtm = recordDtm;
    }

    public String getAttachBatchNo() {
        return attachBatchNo;
    }

    public void setAttachBatchNo(String attachBatchNo) {
        this.attachBatchNo = attachBatchNo;
    }



    public String getCzDtm() {
        return czDtm;
    }

    public void setCzDtm(String czDtm) {
        this.czDtm = czDtm;
    }

    public String getActionTag() {
        return actionTag;
    }

    public void setActionTag(String actionTag) {
        this.actionTag = actionTag;
    }

    public String getReadTag() {
        return readTag;
    }

    public void setReadTag(String readTag) {
        this.readTag = readTag;
    }

    public String getGhIndex() {
        return ghIndex;
    }

    public void setGhIndex(String ghIndex) {
        this.ghIndex = ghIndex;
    }

    public String getSampleDtm() {
        return sampleDtm;
    }

    public void setSampleDtm(String sampleDtm) {
        this.sampleDtm = sampleDtm;
    }

    public String getJqDtm() {
        return jqDtm;
    }

    public void setJqDtm(String jqDtm) {
        this.jqDtm = jqDtm;
    }
    public String getBeginLeaveDate() {
        return beginLeaveDate;
    }

    public void setBeginLeaveDate(String beginLeaveDate) {
        this.beginLeaveDate = beginLeaveDate;
    }

    public String getEndLeaveDate() {
        return endLeaveDate;
    }

    public void setEndLeaveDate(String endLeaveDate) {
        this.endLeaveDate = endLeaveDate;
    }

}
