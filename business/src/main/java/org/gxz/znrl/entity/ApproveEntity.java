package org.gxz.znrl.entity;

/**
 * Created by xieyt on 15-1-21.
 */
public class ApproveEntity extends BaseEntity{
    public String apprId;//
    public String eventId;//
    public String apprEventTypeCd;//审批事件
    public String apprEventTypeName;
    public String apprEventId;//审批事件关键ID
    public String apprInitiator;//发起人
    public String apprInitiatorName;
    public String apprReqDt;//审批发起时间
    public String isArroved;//是否审批
    public String approver;//审批人
    public String isOk;//是否通过
    public String apprDesc;//审批意见
    public String apprDt;//审批时间
    public String apprStatus;//该审批请求状态
    public String apprContent;//审批内容描述
    public String apprOrder;//审批顺序

    public String opCode;//当前操作人
    /////////////////
    public String jsonStr;//
    public String dealType;
    public String remark;
    public String destination;

    public String submitTag;//1 我提交的标识
    public String approveTag;//1 我审批的标识
    public String apprEventTag;//谁搞的事情的描述

    public String resCode;
    public String resMsg;
    public String beginDt;
    public String endDt;
    //////////////////////////////
    public String approveNodeCd;
    public String ndeName;
    public String staffId;
    public String staffName;
    public String ndeType;
    public String nodeTypeName;
    public String nodeStatus;
    public String nodeStatusName;
    public String nodeOrder;

    public String param2;
    public String param3;
    public String param4;
    public String param5;
    public String param6;
    public String param7;
    public String param11;
    public String param12;
    public String param13;
    public String param14;
    public String param15;
    public String param16;
    public String param17;
    public String param18;
    public String param19;
    public String param20;

    public String batchNo;
    public String packCode;
    public String batchType;
    //系统账号申请时  需要走审批流程
    public String userId;
    public String userName;
    public String userRealName;
    public String userPassword;
    public String roleName;
    public String hours;


    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    public String getParam7() {
        return param7;
    }

    public void setParam7(String param7) {
        this.param7 = param7;
    }

    public String getParam11() {
        return param11;
    }

    public void setParam11(String param11) {
        this.param11 = param11;
    }

    public String getParam6() {
        return param6;
    }

    public void setParam6(String param6) {
        this.param6 = param6;
    }


    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public String getParam5() {
        return param5;
    }

    public void setParam5(String param5) {
        this.param5 = param5;
    }

    public String getNdeName() {
        return ndeName;
    }

    public void setNdeName(String ndeName) {
        this.ndeName = ndeName;
    }

    public String getNdeType() {
        return ndeType;
    }

    public void setNdeType(String ndeType) {
        this.ndeType = ndeType;
    }

    public String getApproveNodeCd() {
        return approveNodeCd;
    }

    public void setApproveNodeCd(String approveNodeCd) {
        this.approveNodeCd = approveNodeCd;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getNodeTypeName() {
        return nodeTypeName;
    }

    public void setNodeTypeName(String nodeTypeName) {
        this.nodeTypeName = nodeTypeName;
    }

    public String getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(String nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public String getNodeStatusName() {
        return nodeStatusName;
    }

    public void setNodeStatusName(String nodeStatusName) {
        this.nodeStatusName = nodeStatusName;
    }

    public String getNodeOrder() {
        return nodeOrder;
    }

    public void setNodeOrder(String nodeOrder) {
        this.nodeOrder = nodeOrder;
    }

    public String getApprOrder() {
        return apprOrder;
    }

    public void setApprOrder(String apprOrder) {
        this.apprOrder = apprOrder;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getBeginDt() {
        return beginDt;
    }

    public String getApprEventTag() {
        return apprEventTag;
    }

    public void setApprEventTag(String apprEventTag) {
        this.apprEventTag = apprEventTag;
    }

    public void setBeginDt(String beginDt) {
        this.beginDt = beginDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getSubmitTag() {
        return submitTag;
    }

    public void setSubmitTag(String submitTag) {
        this.submitTag = submitTag;
    }

    public String getApproveTag() {
        return approveTag;
    }

    public void setApproveTag(String approveTag) {
        this.approveTag = approveTag;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
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

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getApprId() {
        return apprId;
    }

    public void setApprId(String apprId) {
        this.apprId = apprId;
    }

    public String getApprEventTypeCd() {
        return apprEventTypeCd;
    }

    public void setApprEventTypeCd(String apprEventTypeCd) {
        this.apprEventTypeCd = apprEventTypeCd;
    }

    public String getApprEventTypeName() {
        return apprEventTypeName;
    }

    public void setApprEventTypeName(String apprEventTypeName) {
        this.apprEventTypeName = apprEventTypeName;
    }

    public String getApprEventId() {
        return apprEventId;
    }

    public void setApprEventId(String apprEventId) {
        this.apprEventId = apprEventId;
    }

    public String getApprInitiator() {
        return apprInitiator;
    }

    public void setApprInitiator(String apprInitiator) {
        this.apprInitiator = apprInitiator;
    }

    public String getApprInitiatorName() {
        return apprInitiatorName;
    }

    public void setApprInitiatorName(String apprInitiatorName) {
        this.apprInitiatorName = apprInitiatorName;
    }

    public String getApprReqDt() {
        return apprReqDt;
    }

    public void setApprReqDt(String apprReqDt) {
        this.apprReqDt = apprReqDt;
    }

    public String getIsArroved() {
        return isArroved;
    }

    public void setIsArroved(String isArroved) {
        this.isArroved = isArroved;
    }

    public String getIsOk() {
        return isOk;
    }

    public void setIsOk(String isOk) {
        this.isOk = isOk;
    }

    public String getApprDesc() {
        return apprDesc;
    }

    public void setApprDesc(String apprDesc) {
        this.apprDesc = apprDesc;
    }

    public String getApprDt() {
        return apprDt;
    }

    public void setApprDt(String apprDt) {
        this.apprDt = apprDt;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getApprContent() {
        return apprContent;
    }

    public void setApprContent(String apprContent) {
        this.apprContent = apprContent;
    }

    public String getParam20() {
        return param20;
    }

    public void setParam20(String param20) {
        this.param20 = param20;
    }

    public String getParam19() {
        return param19;
    }

    public void setParam19(String param19) {
        this.param19 = param19;
    }

    public String getParam12() {
        return param12;
    }

    public void setParam12(String param12) {
        this.param12 = param12;
    }

    public String getParam13() {
        return param13;
    }

    public void setParam13(String param13) {
        this.param13 = param13;
    }

    public String getParam14() {
        return param14;
    }

    public void setParam14(String param14) {
        this.param14 = param14;
    }

    public String getParam15() {
        return param15;
    }

    public void setParam15(String param15) {
        this.param15 = param15;
    }

    public String getParam16() {
        return param16;
    }

    public void setParam16(String param16) {
        this.param16 = param16;
    }

    public String getParam17() {
        return param17;
    }

    public void setParam17(String param17) {
        this.param17 = param17;
    }

    public String getParam18() {
        return param18;
    }

    public void setParam18(String param18) {
        this.param18 = param18;
    }

}
