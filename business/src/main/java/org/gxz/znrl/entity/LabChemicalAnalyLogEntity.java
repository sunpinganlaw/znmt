package org.gxz.znrl.entity;

/**
 * Created by Rubbish on 2015/7/23.
 */
public class LabChemicalAnalyLogEntity extends BaseEntity {

    private Long chemicalAnalyLogId;

    private int operateTag;

    private int hisTag;

    private Long chemicalAnalyId;

    private String chemicalValue;

    private String startTime;

    private String endTime;

    private String opCode;

    private String opTime;

    private Long mineChemicalId;

    private String mineNo;

    private String mineNoName;

    private String chemicalType;

    private String chemicalTypeName;

    private String operateTagName;

    private String hisTagName;

    private String nowDate;

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    public String getMineNoName() {
        return mineNoName;
    }

    public void setMineNoName(String mineNoName) {
        this.mineNoName = mineNoName;
    }

    public String getChemicalTypeName() {
        return chemicalTypeName;
    }

    public void setChemicalTypeName(String chemicalTypeName) {
        this.chemicalTypeName = chemicalTypeName;
    }

    public String getOperateTagName() {
        return operateTagName;
    }

    public void setOperateTagName(String operateTagName) {
        this.operateTagName = operateTagName;
    }

    public String getHisTagName() {
        return hisTagName;
    }

    public void setHisTagName(String hisTagName) {
        this.hisTagName = hisTagName;
    }

    public Long getChemicalAnalyId() {
        return chemicalAnalyId;
    }

    public void setChemicalAnalyId(Long chemicalAnalyId) {
        this.chemicalAnalyId = chemicalAnalyId;
    }

    public String getChemicalValue() {
        return chemicalValue;
    }

    public void setChemicalValue(String chemicalValue) {
        this.chemicalValue = chemicalValue;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public Long getMineChemicalId() {
        return mineChemicalId;
    }

    public void setMineChemicalId(Long mineChemicalId) {
        this.mineChemicalId = mineChemicalId;
    }

    public String getMineNo() {
        return mineNo;
    }

    public void setMineNo(String mineNo) {
        this.mineNo = mineNo;
    }

    public String getChemicalType() {
        return chemicalType;
    }

    public void setChemicalType(String chemicalType) {
        this.chemicalType = chemicalType;
    }

    public Long getChemicalAnalyLogId() {
        return chemicalAnalyLogId;
    }

    public void setChemicalAnalyLogId(Long chemicalAnalyLogId) {
        this.chemicalAnalyLogId = chemicalAnalyLogId;
    }

    public int getOperateTag() {
        return operateTag;
    }

    public void setOperateTag(int operateTag) {
        this.operateTag = operateTag;
    }

    public int getHisTag() {
        return hisTag;
    }

    public void setHisTag(int hisTag) {
        this.hisTag = hisTag;
    }
}
