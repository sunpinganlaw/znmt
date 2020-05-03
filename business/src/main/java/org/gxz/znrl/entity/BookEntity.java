package org.gxz.znrl.entity;

/**
 * Created by dage on 15-6-24.
 */
public class BookEntity {

    private String standingBookId ;
    private String laborCode ;
    private String hyDate ;
    private String zbDate ;
    private String zbName ;

    private String  jhTime;
    private String  jhCode ;
    private String  insertTime ;
    private String  opCode;
    private String  opDesc ;
    private String  jhName;
    private String  apprName;
    private String  apprStatus;
    private String  apprStatusCode;
    private String  dataStatus;
    private String  apprDesc;
    private String  apprDate;
    private String resCode;
    private String resMsg;

    private String reportIds;
    private String apprEventTypeCd;
    //查询条件，开始时间、结束时间

    public String beginDate;
    public String endDate;

    public String getApprDesc() {
        return apprDesc;
    }

    public void setApprDesc(String apprDesc) {
        this.apprDesc = apprDesc;
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

    public String getJhName() {
        return jhName;
    }

    public void setJhName(String jhName) {
        this.jhName = jhName;
    }

    public String getApprEventTypeCd() {
        return apprEventTypeCd;
    }

    public void setApprEventTypeCd(String apprEventTypeCd) {
        this.apprEventTypeCd = apprEventTypeCd;
    }

    public String getReportIds() {
        return reportIds;
    }

    public void setReportIds(String reportIds) {
        this.reportIds = reportIds;
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

    public String getApprStatusCode() {
        return apprStatusCode;
    }

    public void setApprStatusCode(String apprStatusCode) {
        this.apprStatusCode = apprStatusCode;
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

    public String getStandingBookId() {
        return standingBookId;
    }

    public void setStandingBookId(String standingBookId) {
        this.standingBookId = standingBookId;
    }

    public String getLaborCode() {
        return laborCode;
    }

    public void setLaborCode(String laborCode) {
        this.laborCode = laborCode;
    }

    public String getHyDate() {
        return hyDate;
    }

    public void setHyDate(String hyDate) {
        this.hyDate = hyDate;
    }

    public String getZbDate() {
        return zbDate;
    }

    public void setZbDate(String zbDate) {
        this.zbDate = zbDate;
    }

    public String getZbName() {
        return zbName;
    }

    public void setZbName(String zbName) {
        this.zbName = zbName;
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

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }
}
