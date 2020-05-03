package org.gxz.znrl.entity;

/**
 * Created by rubish on 2015/4/9.
 */
public class ErrorSpecEntity extends BaseEntity  {

    private String errorSpecId;

    private String errorCode;

    private String errorTitle;

    private String errorSpec;

    private String dealMethod;

    private String machinCode;

    private String state;

    private String stateName;

    private String createDate;

    private String flowId;

    private String channelNo;

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getErrorSpecId() {
        return errorSpecId;
    }

    public void setErrorSpecId(String errorSpecId) {
        this.errorSpecId = errorSpecId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public void setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
    }

    public String getErrorSpec() {
        return errorSpec;
    }

    public void setErrorSpec(String errorSpec) {
        this.errorSpec = errorSpec;
    }

    public String getDealMethod() {
        return dealMethod;
    }

    public void setDealMethod(String dealMethod) {
        this.dealMethod = dealMethod;
    }

    public String getMachinCode() {
        return machinCode;
    }

    public void setMachinCode(String machinCode) {
        this.machinCode = machinCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
