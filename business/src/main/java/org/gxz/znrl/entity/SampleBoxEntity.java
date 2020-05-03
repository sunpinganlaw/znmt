package org.gxz.znrl.entity;

/**
 * 存查样柜的entity
 * Created by xieyt on 14-12-22.
 */
public class SampleBoxEntity {
    //柜子
    public String boxNo;//箱子编号001-096
    public String siStored;//是否存样
    public String samNum;//存样数量
    public String boxStatus;//箱子状态
    public String samTime;//存样时间
    public String isExceed;//是否超期，Y是  N否
    public String isUsed;//使用情况

    //样包
    public String bagId;
    public String grain;
    public String bagStatus;
    public String keeper;
    public String takeSampleStatus;
    public String takeSampleTime;
    public String taker;
    public String reCheckTake;
    public String cleanSampleStatus;
    public String cleanSampleTime;
    public String cleaner;
    public String reCheckClean;

    //查询用
    public String beginSamTime;
    public String endSamTime;
    public String apprEventId;

    //电子存查样柜：取样，清样状态
    private String tsamsta;
    private String csamsta;
    private String samidsc;

    public String getSamidsc() {
        return samidsc;
    }

    public void setSamidsc(String samidsc) {
        this.samidsc = samidsc;
    }

    public String getTsamsta() {
        return tsamsta;
    }

    public void setTsamsta(String tsamsta) {
        this.tsamsta = tsamsta;
    }

    public String getCsamsta() {
        return csamsta;
    }

    public void setCsamsta(String csamsta) {
        this.csamsta = csamsta;
    }

    public String getApprEventId() {
        return apprEventId;
    }

    public void setApprEventId(String apprEventId) {
        this.apprEventId = apprEventId;
    }

    public String getBeginSamTime() {
        return beginSamTime;
    }

    public void setBeginSamTime(String beginSamTime) {
        this.beginSamTime = beginSamTime;
    }

    public String getEndSamTime() {
        return endSamTime;
    }

    public void setEndSamTime(String endSamTime) {
        this.endSamTime = endSamTime;
    }

    public String getBagId() {
        return bagId;
    }

    public void setBagId(String bagId) {
        this.bagId = bagId;
    }

    public String getGrain() {
        return grain;
    }

    public void setGrain(String grain) {
        this.grain = grain;
    }

    public String getBagStatus() {
        return bagStatus;
    }

    public void setBagStatus(String bagStatus) {
        this.bagStatus = bagStatus;
    }

    public String getKeeper() {
        return keeper;
    }

    public void setKeeper(String keeper) {
        this.keeper = keeper;
    }

    public String getTakeSampleStatus() {
        return takeSampleStatus;
    }

    public void setTakeSampleStatus(String takeSampleStatus) {
        this.takeSampleStatus = takeSampleStatus;
    }

    public String getTakeSampleTime() {
        return takeSampleTime;
    }

    public void setTakeSampleTime(String takeSampleTime) {
        this.takeSampleTime = takeSampleTime;
    }

    public String getTaker() {
        return taker;
    }

    public void setTaker(String taker) {
        this.taker = taker;
    }

    public String getReCheckTake() {
        return reCheckTake;
    }

    public void setReCheckTake(String reCheckTake) {
        this.reCheckTake = reCheckTake;
    }

    public String getCleanSampleStatus() {
        return cleanSampleStatus;
    }

    public void setCleanSampleStatus(String cleanSampleStatus) {
        this.cleanSampleStatus = cleanSampleStatus;
    }

    public String getCleanSampleTime() {
        return cleanSampleTime;
    }

    public void setCleanSampleTime(String cleanSampleTime) {
        this.cleanSampleTime = cleanSampleTime;
    }

    public String getCleaner() {
        return cleaner;
    }

    public void setCleaner(String cleaner) {
        this.cleaner = cleaner;
    }

    public String getReCheckClean() {
        return reCheckClean;
    }

    public void setReCheckClean(String reCheckClean) {
        this.reCheckClean = reCheckClean;
    }

    public String getBoxStatus() {
        return boxStatus;
    }

    public void setBoxStatus(String boxStatus) {
        this.boxStatus = boxStatus;
    }

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public String getSiStored() {
        return siStored;
    }

    public void setSiStored(String siStored) {
        this.siStored = siStored;
    }

    public String getSamNum() {
        return samNum;
    }

    public void setSamNum(String samNum) {
        this.samNum = samNum;
    }

    public String getSamTime() {
        return samTime;
    }

    public void setSamTime(String samTime) {
        this.samTime = samTime;
    }

    public String getIsExceed() {
        return isExceed;
    }

    public void setIsExceed(String isExceed) {
        this.isExceed = isExceed;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }
}
