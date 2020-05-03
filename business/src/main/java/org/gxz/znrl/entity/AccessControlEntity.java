package org.gxz.znrl.entity;

/**
 * Created by yangfeifei on 2016/9/2.
 */
public class AccessControlEntity extends  BaseEntity{

    public String useId;
    public String userId;
    public String userName;
    public String useType;
    public String useDesc;
    public String useTime;
    public String cardId;
    public String useBeginTime;
    public String useEndTime;
    public String userArea;


    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUseDesc() {
        return useDesc;
    }

    public void setUseDesc(String useDesc) {
        this.useDesc = useDesc;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUseBeginTime() {
        return useBeginTime;
    }

    public void setUseBeginTime(String useBeginTime) {
        this.useBeginTime = useBeginTime;
    }

    public String getUseEndTime() {
        return useEndTime;
    }

    public void setUseEndTime(String useEndTime) {
        this.useEndTime = useEndTime;
    }

    public String getUserArea() {
        return userArea;
    }

    public void setUserArea(String userArea) {
        this.userArea = userArea;
    }



}
