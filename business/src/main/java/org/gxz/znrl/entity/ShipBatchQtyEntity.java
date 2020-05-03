package org.gxz.znrl.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liuzh
 * @date 2020/2/12 15:22
 */

public class ShipBatchQtyEntity extends BaseEntity {
    private Long weightRecId;

    private String batchNo;

    private String sampleCode;

    private BigDecimal planWeight;

    private String beltNo;

    private BigDecimal beginWeight;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date beginTime;

    private BigDecimal endWeight;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;

    private String remark;

    public Long getWeightRecId() {
        return weightRecId;
    }

    public void setWeightRecId(Long weightRecId) {
        this.weightRecId = weightRecId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode == null ? null : sampleCode.trim();
    }

    public BigDecimal getPlanWeight() {
        return planWeight;
    }

    public void setPlanWeight(BigDecimal planWeight) {
        this.planWeight = planWeight;
    }

    public String getBeltNo() {
        return beltNo;
    }

    public void setBeltNo(String beltNo) {
        this.beltNo = beltNo == null ? null : beltNo.trim();
    }

    public BigDecimal getBeginWeight() {
        return beginWeight;
    }

    public void setBeginWeight(BigDecimal beginWeight) {
        this.beginWeight = beginWeight;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public BigDecimal getEndWeight() {
        return endWeight;
    }

    public void setEndWeight(BigDecimal endWeight) {
        this.endWeight = endWeight;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}