package org.gxz.znrl.service.impl;

import org.gxz.znrl.entity.ApproveEntity;
import org.gxz.znrl.entity.FunctionEntity;
import org.gxz.znrl.mapper.ApproveMapper;
import org.gxz.znrl.service.IApproveService;
import org.gxz.znrl.service.IMonitorService;
import org.gxz.znrl.util.Constant;
import org.gxz.znrl.viewModel.GridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gxz.znrl.common.util.JacksonMapper;

import java.util.List;

/**
 * Created by xieyt on 14-11-18.
 */
@Service("approveService")
@Transactional
@SuppressWarnings("unchecked")
public class ApproveServiceImpl implements IApproveService {
    private static final Logger LOG = LoggerFactory.getLogger(ApproveServiceImpl.class);

    @Autowired
    public ApproveMapper approveMapper;

    @Autowired
    public IMonitorService monitorService;

    /**
     * 查询等待当前用户审批的事项
     */
    public GridModel qryApprove(ApproveEntity approveEntity){
        //查询本次页面的结果集
        List<ApproveEntity> list = approveMapper.qryApprove(approveEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询当前用户相关的审批的事项
     */
    public GridModel qryMyApprove(ApproveEntity approveEntity){
        //查询本次页面的结果集
        List<ApproveEntity> list = approveMapper.qryMyApprove(approveEntity);
        Integer i = approveMapper.qryMyApproveCnt(approveEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(i);

        return m;
    }

    /**
     * 查询等待当前用户审批的事项
     */

    public void saveApprResult(ApproveEntity approveEntity){
        try {
            //保存审批结果，如果是远光存查样柜，还会增加操作记录
            approveMapper.saveApprResult(approveEntity);
            LOG.error("saveApprResult---1:"+(approveEntity == null));
            LOG.error("saveApprResult---2:"+approveEntity.getIsOk()+","+approveEntity.getApprEventTypeCd()+","+approveEntity.getResCode()+","+Constant.getConstVal("CABINET_VENDOR")+","+approveEntity.getResMsg());
            String callRes = "1";
            String  opRecId;
            //如果是审批通过，还要看是否处理成功，是否远光柜子
            if (approveEntity.getIsOk() != null && approveEntity.getIsOk().equals("Y")){
                //审批结果处理成功
                if ((approveEntity.getApprEventTypeCd().equals("8")||approveEntity.getApprEventTypeCd().equals("7"))&&approveEntity.getResCode() != null && approveEntity.getResCode().equals("0")) {
                    //是否远光的柜子                    add by zhangf 201509022判断approveEntity.getResMsg()是否为ok，如果是序列号，走谢的调远光的柜子接口，如果是ok，不走这里
                    //解释wz 20160721， 关于下面if条件的!approveEntity.getResMsg().equals("ok") :  approveMapper.saveApprResult(approveEntity) 方法调用
                    // approve_core.saveApproveResult-> approve_core.saveSingleApproveResult->approve_core.writeBackApproveEvent->pk_sample_store.yg_cabinet_op_record时，v_resmsg会被赋值
                    //存储过程中代码截取：
                    //      --准备主键序列
                    //      select SEQ_YG_CABINET_OP_REC.Nextval into iv_opRecId from dual;
                    //      v_resmsg := iv_opRecId;--返回操作记录
                    //这个返回值就是过程中插入的 YG_CABINET_OP_REC表的字段OP_REC_ID对应的一条新记录。
                    //江南存在这个问题：审批状态是成功了，但远光的接口没有调用到。所以综合起来，先改为，
                    //不要这个 && !approveEntity.getResMsg().equals("ok") 判断，
                    //只要是远光的，必须调用远光接口，即时approveEntity.getResMsg()为ok，也继续调用远光，到时就报错返回，保证事物回滚。
                    if (Constant.getConstVal("CABINET_VENDOR") != null && Constant.getConstVal("CABINET_VENDOR").equals("YG")  && !approveEntity.getResMsg().equals("ok")){
                        LOG.error("saveApprResult---3 begin callYGWSIntf");
                        callRes = monitorService.callYGWSIntf(approveEntity.getResMsg());
                        LOG.error("saveApprResult---3 after callYGWSIntf:"+callRes);
                        if (callRes == null || callRes.equals("1")) {
                            throw new RuntimeException("调用接口方法异常返回");
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("saveApprResult---4 exception :"+e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("提交审批失败："+e.getMessage());
        }
    }

    /**
     * 气动样柜提交审批请求
     */
    public void submitToApprove(ApproveEntity approveEntity){
        approveMapper.submitToApprove(approveEntity);
    }

    /**
     * 远光气动样柜提交审批请求
     */
    public void submitToApprove4YG(ApproveEntity approveEntity){
        approveMapper.submitToApprove4YG(approveEntity);
    }

    /**
     * 电子样柜提交审批请求
     */
    public void boxSubmitToApprove(ApproveEntity approveEntity){
        approveMapper.boxSubmitToApprove(approveEntity);
    }

    /*查询审批流程情况*/
    public GridModel qryApproveProgress(ApproveEntity approveEntity){
        //查询本次页面的结果集
        List<ApproveEntity> list = approveMapper.qryApproveProgress(approveEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /*查询审批流程节点配置信息*/
    public GridModel qryApproveNode(ApproveEntity approveEntity){
        //查询本次页面的结果集
        List<ApproveEntity> list = approveMapper.qryApproveNode(approveEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }


    /*修改审批节点*/
    public void modifyApprNode(ApproveEntity approveEntity){
        approveMapper.modifyApprNode(approveEntity);
    }

    /*新增审批节点*/
    public void addApprNode(ApproveEntity approveEntity){
        approveMapper.addApprNode(approveEntity);
    }

    /*删除审批节点*/
    public void deleteApprNode(ApproveEntity approveEntity){
        approveMapper.deleteApprNode(approveEntity);
    }

    /* 查询审批的煤样取样、弃样审批详情*/
    public GridModel qryApproveResultInfo(ApproveEntity approveEntity){
        //查询本次页面的结果集
        List<ApproveEntity> list = approveMapper.qryApproveResultInfo(approveEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /*大开电厂增加车节信息审批*/
    public void submitToApprove4AddTrains(ApproveEntity approveEntity){
        approveMapper.submitToApprove4AddTrains(approveEntity);
    }

    //大开-新增获取火车车节审批详情
    public ApproveEntity getApprAddTrainsDetailData(ApproveEntity approveEntity) {
        return approveMapper.getApprAddTrainsDetailData(approveEntity);
    }
	
	/**
     * 扣吨提交审批
     */
    public void submitKdToApprove(ApproveEntity approveEntity){
        approveMapper.submitKdToApprove(approveEntity);
    }

    /**
     * 申请管理员账号 提交审批
     */
    public void submitSysUserToApprove(ApproveEntity approveEntity){
        approveMapper.submitSysUserToApprove(approveEntity);
    }


    public void submitManualSampleApprove(ApproveEntity approveEntity){
        approveMapper.submitManualSampleToApprove(approveEntity);
    }

    /* 查询审批的煤样取样、弃样审批详情 谏壁审批详情作废的人不需要显示 hxl20180125*/
    public GridModel qryApproveResultInfo4JB(ApproveEntity approveEntity){
        //查询本次页面的结果集
        List<ApproveEntity> list = approveMapper.qryApproveResultInfo4JB(approveEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /* 查询审批的煤样取样、弃样审批详情 大武口 hxl20180517*/
    public GridModel qryApproveResultInfo4DWK(ApproveEntity approveEntity){
        //查询本次页面的结果集
        List<ApproveEntity> list = approveMapper.qryApproveResultInfo4DWK(approveEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /* 泉州审批查询审批的煤样取样、弃样审批详情*/
    public GridModel qryApproveResultInfo4Qz(ApproveEntity approveEntity){
        //查询本次页面的结果集
        List<ApproveEntity> list = approveMapper.qryApproveResultInfo4Qz(approveEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }
}
