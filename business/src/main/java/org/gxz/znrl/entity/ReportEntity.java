package org.gxz.znrl.entity;

/**
 * Created by dage on 15-6-19.
 */
public class ReportEntity {
    private String labReportId;
    private String laborCode;
    private String shipRecID;
    private String mt;
    private String mad;
    private String aad;
    private String ad;
    private String aar;
    private String vad;
    private String vd;
    private String fcad;
    private String fcar;
    private String fcd;
    private String fcdaf;

    private String var;
    private String vdaf;
    private String stad;
    private String std;
    private String star;
    private String hd;
    private String had;
    private String har;
    private String hdaf;
    private String cad;
    private String apprStatusCode;

    private String nad;
    private String st;
    private String dt;
    private String ht;
    private String ft;
    private String crc;

    private String qbad;
    private String qbar;
    private String qbd;
    private String qbdaf;
    private String qgrad;
    private String qgrar;
    private String qgrd;
    private String qgrdaf;
    private String qnetad;

    private String qnetarj;
    private String qnetarm;
    private String qnetd;
    private String batchType;
    private String apprStatus;
    private String updateTime;
    private String updateCode;
    private String jhTime;
    private String jhCode;
    private String hyDate;
    private String sampleDate;

    private String insertTime;
    private String opCode;
    private String opDesc;
    public String beginDate;
    public String endDate;
    public String coalName;
    public String venName;
    public String mineName;
    public String standingBookId;
    public String shipId;
    public String shipName;
    public String batchNo;
    public String batchNoType;
    public String transNo;
    public String rlDate;
    public String updateString;
    public String resCode;
    public String resMsg;
    //加权使用的
    public String proportionRequired;
    public String proportionType;
    public String proportion;
    public String proportionTag;
    public String proportionTypeName;
    public String proportionTagName;
    private String carCnt;
    private String batchTime;

    private String startPlace;
    private String laborCodeArray;
    private String receiveOp; //接样人
    private String receiveTime; //接样时间

    private String term;//期号

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
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

    public String getUpdateString() {
        return updateString;
    }

    public void setUpdateString(String updateString) {
        this.updateString = updateString;
    }

    public String getBatchNoType() {
        return batchNoType;
    }

    public void setBatchNoType(String batchNoType) {
        this.batchNoType = batchNoType;
    }

    public String getMineName() {
        return mineName;
    }

    public void setMineName(String mineName) {
        this.mineName = mineName;
    }

    public String getProportionTypeName() {
        return proportionTypeName;
    }

    public void setProportionTypeName(String proportionTypeName) {
        this.proportionTypeName = proportionTypeName;
    }

    public String getProportionTagName() {
        return proportionTagName;
    }

    public void setProportionTagName(String proportionTagName) {
        this.proportionTagName = proportionTagName;
    }

    public String getProportionType() {
        return proportionType;
    }

    public void setProportionType(String proportionType) {
        this.proportionType = proportionType;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    public String getProportionTag() {
        return proportionTag;
    }

    public void setProportionTag(String proportionTag) {
        this.proportionTag = proportionTag;
    }

    public String getProportionRequired() {
        return proportionRequired;
    }

    public void setProportionRequired(String proportionRequired) {
        this.proportionRequired = proportionRequired;
    }

    public String getRlDate() {
        return rlDate;
    }

    public void setRlDate(String rlDate) {
        this.rlDate = rlDate;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public String getFcd() {
        return fcd;
    }

    public void setFcd(String fcd) {
        this.fcd = fcd;
    }

    public String getFcdaf() {
        return fcdaf;
    }

    public void setFcdaf(String fcdaf) {
        this.fcdaf = fcdaf;
    }

    public String getHar() {
        return har;
    }

    public void setHar(String har) {
        this.har = har;
    }

    public String getHdaf() {
        return hdaf;
    }

    public void setHdaf(String hdaf) {
        this.hdaf = hdaf;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getHt() {
        return ht;
    }

    public void setHt(String ht) {
        this.ht = ht;
    }

    public String getFt() {
        return ft;
    }

    public void setFt(String ft) {
        this.ft = ft;
    }

    public String getFcad() {
        return fcad;
    }

    public void setFcad(String fcad) {
        this.fcad = fcad;
    }

    public String getFcar() {
        return fcar;
    }

    public void setFcar(String fcar) {
        this.fcar = fcar;
    }

    public String getShipRecID() {
        return shipRecID;
    }

    public void setShipRecID(String shipRecID) {
        this.shipRecID = shipRecID;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getHyDate() {
        return hyDate;
    }

    public void setHyDate(String hyDate) {
        this.hyDate = hyDate;
    }

    public String getSampleDate() {
        return sampleDate;
    }

    public void setSampleDate(String sampleDate) {
        this.sampleDate = sampleDate;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getApprStatusCode() {
        return apprStatusCode;
    }

    public void setApprStatusCode(String apprStatusCode) {
        this.apprStatusCode = apprStatusCode;
    }

    public String getLabReportId() {
        return labReportId;
    }

    public void setLabReportId(String labReportId) {
        this.labReportId = labReportId;
    }

    public String getLaborCode() {
        return laborCode;
    }

    public void setLaborCode(String laborCode) {
        this.laborCode = laborCode;
    }

    public String getMt() {
        return mt;
    }

    public void setMt(String mt) {
        this.mt = mt;
    }

    public String getMad() {
        return mad;
    }

    public void setMad(String mad) {
        this.mad = mad;
    }

    public String getAad() {
        return aad;
    }

    public void setAad(String aad) {
        this.aad = aad;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAar() {
        return aar;
    }

    public void setAar(String aar) {
        this.aar = aar;
    }

    public String getVad() {
        return vad;
    }

    public void setVad(String vad) {
        this.vad = vad;
    }

    public String getVd() {
        return vd;
    }

    public void setVd(String vd) {
        this.vd = vd;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getVdaf() {
        return vdaf;
    }

    public void setVdaf(String vdaf) {
        this.vdaf = vdaf;
    }

    public String getStad() {
        return stad;
    }

    public void setStad(String stad) {
        this.stad = stad;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    public String getHad() {
        return had;
    }

    public void setHad(String had) {
        this.had = had;
    }

    public String getCad() {
        return cad;
    }

    public void setCad(String cad) {
        this.cad = cad;
    }

    public String getNad() {
        return nad;
    }

    public void setNad(String nad) {
        this.nad = nad;
    }

    public String getQbad() {
        return qbad;
    }

    public void setQbad(String qbad) {
        this.qbad = qbad;
    }

    public String getQbar() {
        return qbar;
    }

    public void setQbar(String qbar) {
        this.qbar = qbar;
    }

    public String getQbd() {
        return qbd;
    }

    public void setQbd(String qbd) {
        this.qbd = qbd;
    }

    public String getQbdaf() {
        return qbdaf;
    }

    public void setQbdaf(String qbdaf) {
        this.qbdaf = qbdaf;
    }

    public String getQgrad() {
        return qgrad;
    }

    public void setQgrad(String qgrad) {
        this.qgrad = qgrad;
    }

    public String getQgrar() {
        return qgrar;
    }

    public void setQgrar(String qgrar) {
        this.qgrar = qgrar;
    }

    public String getQgrd() {
        return qgrd;
    }

    public void setQgrd(String qgrd) {
        this.qgrd = qgrd;
    }

    public String getQgrdaf() {
        return qgrdaf;
    }

    public void setQgrdaf(String qgrdaf) {
        this.qgrdaf = qgrdaf;
    }

    public String getQnetad() {
        return qnetad;
    }

    public void setQnetad(String qnetad) {
        this.qnetad = qnetad;
    }

    public String getQnetarj() {
        return qnetarj;
    }

    public void setQnetarj(String qnetarj) {
        this.qnetarj = qnetarj;
    }

    public String getQnetarm() {
        return qnetarm;
    }

    public void setQnetarm(String qnetarm) {
        this.qnetarm = qnetarm;
    }

    public String getQnetd() {
        return qnetd;
    }

    public void setQnetd(String qnetd) {
        this.qnetd = qnetd;
    }

    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateCode() {
        return updateCode;
    }

    public void setUpdateCode(String updateCode) {
        this.updateCode = updateCode;
    }

    public String getJhTime() {
        return jhTime;
    }

    public void setJhTime(String jhTime) {
        this.jhTime = jhTime;
    }

    public String getJhCode() {
        return jhCode;
    }

    public void setJhCode(String jhCode) {
        this.jhCode = jhCode;
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

    public String getOpDesc() {
        return opDesc;
    }

    public void setOpDesc(String opDesc) {
        this.opDesc = opDesc;
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
//查询条件，开始时间、结束时间


    public String getStandingBookId() {
        return standingBookId;
    }

    public void setStandingBookId(String standingBookId) {
        this.standingBookId = standingBookId;
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

    public String getCarCnt() {
        return carCnt;
    }

    public void setCarCnt(String carCnt) {
        this.carCnt = carCnt;
    }

    public String getBatchTime() {
        return batchTime;
    }

    public void setBatchTime(String batchTime) {
        this.batchTime = batchTime;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getLaborCodeArray() {
        return laborCodeArray;
    }

    public void setLaborCodeArray(String laborCodeArray) {
        this.laborCodeArray = laborCodeArray;
    }

    public String getReceiveOp() {
        return receiveOp;
    }

    public void setReceiveOp(String receiveOp) {
        this.receiveOp = receiveOp;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }
}
