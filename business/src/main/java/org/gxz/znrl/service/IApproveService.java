package org.gxz.znrl.service;

import org.gxz.znrl.entity.ApproveEntity;
import org.gxz.znrl.viewModel.GridModel;
import org.codehaus.groovy.runtime.StringGroovyMethods;

import java.util.List;

/**
 * Created by xieyt on 15-1-21.
 */
public interface IApproveService {
    /**
     * 查询等待当前用户审批的事项
     */
    public GridModel qryApprove(ApproveEntity approveEntity);

    /**
     * 查询等待当前用户审批的事项
     */
    public void saveApprResult(ApproveEntity approveEntity);

    /**
     * 气动样柜提交审批请求
     */
    public void submitToApprove(ApproveEntity approveEntity);

    /**
     * 远光气动样柜提交审批请求
     */
    public void submitToApprove4YG(ApproveEntity approveEntity);

    /**
     * 电子样柜提交审批请求
     */
    public void boxSubmitToApprove(ApproveEntity approveEntity);

    /**
     * 查询当前用户相关的审批的事项
     */
    public GridModel qryMyApprove(ApproveEntity approveEntity);

    /*查询审批流程情况*/
    public GridModel qryApproveProgress(ApproveEntity approveEntity);

    /*查询审批流程节点配置信息*/
    public GridModel qryApproveNode(ApproveEntity approveEntity);

    /*修改审批节点*/
    public void modifyApprNode(ApproveEntity approveEntity);

    /*新增审批节点*/
    public void addApprNode(ApproveEntity approveEntity);

    /*删除审批节点*/
    public void deleteApprNode(ApproveEntity approveEntity);

    /* 查询审批的煤样取样、弃样审批详情*/
     public GridModel qryApproveResultInfo(ApproveEntity approveEntity);

    /*大开电厂增加车节信息审批*/
    public void submitToApprove4AddTrains(ApproveEntity approveEntity);

    //大开-新增获取火车车节审批详情
    public ApproveEntity getApprAddTrainsDetailData(ApproveEntity approveEntity);
	
    /* 扣吨提交审批*/
    public void submitKdToApprove(ApproveEntity approveEntity);



    public void submitManualSampleApprove(ApproveEntity approveEntity);

    /* 查询审批的煤样取样、弃样审批详情 谏壁审批详情作废的人不需要显示 hxl20180125*/
    public GridModel qryApproveResultInfo4JB(ApproveEntity approveEntity);

    /* 查询审批的煤样取样、弃样审批详情 大武口 hxl20180517*/
    public GridModel qryApproveResultInfo4DWK(ApproveEntity approveEntity);

    /* 申请管理员账号 提交审批*/
    public void submitSysUserToApprove(ApproveEntity approveEntity);

    /* 泉州审批查询审批的煤样取样、弃样审批详情*/
    public GridModel qryApproveResultInfo4Qz(ApproveEntity approveEntity);
}
