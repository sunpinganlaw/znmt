package org.gxz.znrl.entity;

/**
 * Created by xieyt on 2015-1-11.
 */

public class CarInfoEntity extends BaseEntity {
    //基本信息
    public String recId;
    public String orgNo;
    public String orgName;
    public String validSta;
    public String statusName;
    public String carId;
    public String backCarId;
    public String carTyp;
    public String carTypeName;
    public String transTyp;
    public String cardId;
    public String cardTyp;
    public String regDtm;
    public String endDtm;
    public String stdQty;
    public String floatQty;
    public String blkLstFlg;
    public String carToWLength;

    public String cardTypeName;
    public String transTypeName;
    public String beginDate;
    public String endDate;

    public String ship_name;




    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String  batchNo;


    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String tradeType;
    public String tradeName;


    //车厢1
    public String car1Length;
    public String car1Width;
    public String car1ToFLength;
    public String car1ToNLength;
    public String car1NTFLength;
    public String car1L1TWLenght;
    public String car1L2TL1Length;
    public String car1L3TL2Length;
    public String car1L4TL3Length;
    public String car1L5TL4Length;
    public String car1L6TL5Length;
    public String car1G1TWLength;
    public String car1G2TG1Length;
    public String car1G3TG2Length;
    public String car1G4TG3Length;
    public String car1G5TG4Length;
    public String car1G6TG5Length;

    //车厢1到2的距离
    public String car1To2Length;

    //车头离前车厢的间距
    public String carTo1Length;

    //车厢2
    public String car2Length;
    public String car2Width;
    public String car2ToFLength;
    public String car2ToNLength;
    public String car2NTFLength;
    public String car2L1TWLenght;
    public String car2L2TL1Length;
    public String car2L3TL2Length;
    public String car2L4TL3Length;
    public String car2L5TL4Length;
    public String car2L6TL5Length;
    public String car2G1TWLength;
    public String car2G2TG1Length;
    public String car2G3TG2Length;
    public String car2G4TG3Length;
    public String car2G5TG4Length;
    public String car2G6TG5Length;

    //操作人员信息
    public String fstUsrId;
    public String fstUsrDtm;
    public String lstUsrId;
    public String lstUsrDtm;
    public String rmtNot;

    public String todayCnt;

    //返回操作结果
    public String resCode;
    public String resMsg;

    //织金单独要增加 【是否签订厂内卸煤管理协议】
    public String protocolSta;

    public String doActionTag;//动作标识 ADD, MOD
    public String jsonString;//json字符串
    public String opCode;


    public String getCarTo1Length() {
        return carTo1Length;
    }

    public void setCarTo1Length(String carTo1Length) {
        this.carTo1Length = carTo1Length;
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


    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTodayCnt() {
        return todayCnt;
    }

    public void setTodayCnt(String todayCnt) {
        this.todayCnt = todayCnt;
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

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getTransTypeName() {
        return transTypeName;
    }

    public void setTransTypeName(String transTypeName) {
        this.transTypeName = transTypeName;
    }

    public String getCar1To2Length() {
        return car1To2Length;
    }

    public void setCar1To2Length(String car1To2Length) {
        this.car1To2Length = car1To2Length;
    }

    public String getCar1L1TWLenght() {
        return car1L1TWLenght;
    }

    public void setCar1L1TWLenght(String car1L1TWLenght) {
        this.car1L1TWLenght = car1L1TWLenght;
    }

    public String getCar2L1TWLenght() {
        return car2L1TWLenght;
    }

    public void setCar2L1TWLenght(String car2L1TWLenght) {
        this.car2L1TWLenght = car2L1TWLenght;
    }

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getValidSta() {
        return validSta;
    }

    public void setValidSta(String validSta) {
        this.validSta = validSta;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getBackCarId() {
        return backCarId;
    }

    public void setBackCarId(String backCarId) {
        this.backCarId = backCarId;
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

    public String getRegDtm() {
        return regDtm;
    }

    public void setRegDtm(String regDtm) {
        this.regDtm = regDtm;
    }

    public String getEndDtm() {
        return endDtm;
    }

    public void setEndDtm(String endDtm) {
        this.endDtm = endDtm;
    }

    public String getStdQty() {
        return stdQty;
    }

    public void setStdQty(String stdQty) {
        this.stdQty = stdQty;
    }

    public String getFloatQty() {
        return floatQty;
    }

    public void setFloatQty(String floatQty) {
        this.floatQty = floatQty;
    }

    public String getBlkLstFlg() {
        return blkLstFlg;
    }

    public void setBlkLstFlg(String blkLstFlg) {
        this.blkLstFlg = blkLstFlg;
    }

    public String getCarToWLength() {
        return carToWLength;
    }

    public void setCarToWLength(String carToWLength) {
        this.carToWLength = carToWLength;
    }

    public String getCar1Length() {
        return car1Length;
    }

    public void setCar1Length(String car1Length) {
        this.car1Length = car1Length;
    }

    public String getCar1Width() {
        return car1Width;
    }

    public void setCar1Width(String car1Width) {
        this.car1Width = car1Width;
    }

    public String getCar1ToFLength() {
        return car1ToFLength;
    }

    public void setCar1ToFLength(String car1ToFLength) {
        this.car1ToFLength = car1ToFLength;
    }

    public String getCar1ToNLength() {
        return car1ToNLength;
    }

    public void setCar1ToNLength(String car1ToNLength) {
        this.car1ToNLength = car1ToNLength;
    }

    public String getCar1NTFLength() {
        return car1NTFLength;
    }

    public void setCar1NTFLength(String car1NTFLength) {
        this.car1NTFLength = car1NTFLength;
    }

    public String getCar1L2TL1Length() {
        return car1L2TL1Length;
    }

    public void setCar1L2TL1Length(String car1L2TL1Length) {
        this.car1L2TL1Length = car1L2TL1Length;
    }

    public String getCar1L3TL2Length() {
        return car1L3TL2Length;
    }

    public void setCar1L3TL2Length(String car1L3TL2Length) {
        this.car1L3TL2Length = car1L3TL2Length;
    }

    public String getCar1L4TL3Length() {
        return car1L4TL3Length;
    }

    public void setCar1L4TL3Length(String car1L4TL3Length) {
        this.car1L4TL3Length = car1L4TL3Length;
    }

    public String getCar1L5TL4Length() {
        return car1L5TL4Length;
    }

    public void setCar1L5TL4Length(String car1L5TL4Length) {
        this.car1L5TL4Length = car1L5TL4Length;
    }

    public String getCar1L6TL5Length() {
        return car1L6TL5Length;
    }

    public void setCar1L6TL5Length(String car1L6TL5Length) {
        this.car1L6TL5Length = car1L6TL5Length;
    }

    public String getCar1G1TWLength() {
        return car1G1TWLength;
    }

    public void setCar1G1TWLength(String car1G1TWLength) {
        this.car1G1TWLength = car1G1TWLength;
    }

    public String getCar1G2TG1Length() {
        return car1G2TG1Length;
    }

    public void setCar1G2TG1Length(String car1G2TG1Length) {
        this.car1G2TG1Length = car1G2TG1Length;
    }

    public String getCar1G3TG2Length() {
        return car1G3TG2Length;
    }

    public void setCar1G3TG2Length(String car1G3TG2Length) {
        this.car1G3TG2Length = car1G3TG2Length;
    }

    public String getCar1G4TG3Length() {
        return car1G4TG3Length;
    }

    public void setCar1G4TG3Length(String car1G4TG3Length) {
        this.car1G4TG3Length = car1G4TG3Length;
    }

    public String getCar1G5TG4Length() {
        return car1G5TG4Length;
    }

    public void setCar1G5TG4Length(String car1G5TG4Length) {
        this.car1G5TG4Length = car1G5TG4Length;
    }

    public String getCar1G6TG5Length() {
        return car1G6TG5Length;
    }

    public void setCar1G6TG5Length(String car1G6TG5Length) {
        this.car1G6TG5Length = car1G6TG5Length;
    }

    public String getCar2Length() {
        return car2Length;
    }

    public void setCar2Length(String car2Length) {
        this.car2Length = car2Length;
    }

    public String getCar2Width() {
        return car2Width;
    }

    public void setCar2Width(String car2Width) {
        this.car2Width = car2Width;
    }

    public String getCar2ToFLength() {
        return car2ToFLength;
    }

    public void setCar2ToFLength(String car2ToFLength) {
        this.car2ToFLength = car2ToFLength;
    }

    public String getCar2ToNLength() {
        return car2ToNLength;
    }

    public void setCar2ToNLength(String car2ToNLength) {
        this.car2ToNLength = car2ToNLength;
    }

    public String getCar2NTFLength() {
        return car2NTFLength;
    }

    public void setCar2NTFLength(String car2NTFLength) {
        this.car2NTFLength = car2NTFLength;
    }

    public String getCar2L2TL1Length() {
        return car2L2TL1Length;
    }

    public void setCar2L2TL1Length(String car2L2TL1Length) {
        this.car2L2TL1Length = car2L2TL1Length;
    }

    public String getCar2L3TL2Length() {
        return car2L3TL2Length;
    }

    public void setCar2L3TL2Length(String car2L3TL2Length) {
        this.car2L3TL2Length = car2L3TL2Length;
    }

    public String getCar2L4TL3Length() {
        return car2L4TL3Length;
    }

    public void setCar2L4TL3Length(String car2L4TL3Length) {
        this.car2L4TL3Length = car2L4TL3Length;
    }

    public String getCar2L5TL4Length() {
        return car2L5TL4Length;
    }

    public void setCar2L5TL4Length(String car2L5TL4Length) {
        this.car2L5TL4Length = car2L5TL4Length;
    }

    public String getCar2L6TL5Length() {
        return car2L6TL5Length;
    }

    public void setCar2L6TL5Length(String car2L6TL5Length) {
        this.car2L6TL5Length = car2L6TL5Length;
    }

    public String getCar2G1TWLength() {
        return car2G1TWLength;
    }

    public void setCar2G1TWLength(String car2G1TWLength) {
        this.car2G1TWLength = car2G1TWLength;
    }

    public String getCar2G2TG1Length() {
        return car2G2TG1Length;
    }

    public void setCar2G2TG1Length(String car2G2TG1Length) {
        this.car2G2TG1Length = car2G2TG1Length;
    }

    public String getCar2G3TG2Length() {
        return car2G3TG2Length;
    }

    public void setCar2G3TG2Length(String car2G3TG2Length) {
        this.car2G3TG2Length = car2G3TG2Length;
    }

    public String getCar2G4TG3Length() {
        return car2G4TG3Length;
    }

    public void setCar2G4TG3Length(String car2G4TG3Length) {
        this.car2G4TG3Length = car2G4TG3Length;
    }

    public String getCar2G5TG4Length() {
        return car2G5TG4Length;
    }

    public void setCar2G5TG4Length(String car2G5TG4Length) {
        this.car2G5TG4Length = car2G5TG4Length;
    }

    public String getCar2G6TG5Length() {
        return car2G6TG5Length;
    }

    public void setCar2G6TG5Length(String car2G6TG5Length) {
        this.car2G6TG5Length = car2G6TG5Length;
    }

    public String getFstUsrId() {
        return fstUsrId;
    }

    public void setFstUsrId(String fstUsrId) {
        this.fstUsrId = fstUsrId;
    }

    public String getFstUsrDtm() {
        return fstUsrDtm;
    }

    public void setFstUsrDtm(String fstUsrDtm) {
        this.fstUsrDtm = fstUsrDtm;
    }

    public String getLstUsrId() {
        return lstUsrId;
    }

    public void setLstUsrId(String lstUsrId) {
        this.lstUsrId = lstUsrId;
    }

    public String getLstUsrDtm() {
        return lstUsrDtm;
    }

    public void setLstUsrDtm(String lstUsrDtm) {
        this.lstUsrDtm = lstUsrDtm;
    }

    public String getRmtNot() {
        return rmtNot;
    }

    public void setRmtNot(String rmtNot) {
        this.rmtNot = rmtNot;
    }

    public String getProtocolSta() {
        return protocolSta;
    }

    public void setProtocolSta(String protocolSta) {
        this.protocolSta = protocolSta;
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

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }
}
