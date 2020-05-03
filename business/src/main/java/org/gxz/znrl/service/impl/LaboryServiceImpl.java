package org.gxz.znrl.service.impl;

import org.gxz.znrl.entity.*;
import org.gxz.znrl.mapper.LabDataUploadDAO;
import org.gxz.znrl.mapper.LaboryMapper;
import org.gxz.znrl.service.CommonService;
import org.gxz.znrl.service.ILaboryService;
import org.gxz.znrl.viewModel.GridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by xieyt on 14-11-18.
 */
@Service("laboryService")
@Transactional
@SuppressWarnings("unchecked")
public class LaboryServiceImpl implements ILaboryService {

    @Autowired
    public LaboryMapper laboryMapper;

    @Autowired
    private CommonService commonService;

    /**
     * 查询天平化验原始数据
     */
    public GridModel qryMeltResOrgData(MeltEntity meltEntity){
        //查询本次页面的结果集
        List<MeltEntity> list = laboryMapper.qryMeltResOrgData(meltEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询天平化验原始数据
     */
    public GridModel qryMeltResData(MeltEntity meltEntity){
        //查询本次页面的结果集
        List<MeltEntity> list = laboryMapper.qryMeltResData(meltEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询天平化验原始数据
     */
    public GridModel qryScalesResOrgData(ScalesEntity scalesEntity){
        //查询本次页面的结果集
        List<ScalesEntity> list = laboryMapper.qryScalesResOrgData(scalesEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询天平化验原始数据
     */
    public GridModel qryScalesResOrgDataNew(ScalesEntity scalesEntity){
        //查询本次页面的结果集
        List<ScalesEntity> list = laboryMapper.qryScalesResOrgDataNew(scalesEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询天平化验数据
     */
    public GridModel qryScalesResData(ScalesEntity scalesEntity){
        //查询本次页面的结果集
        List<ScalesEntity> list = laboryMapper.qryScalesResData(scalesEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询水，灰，挥发的确认后的化验数据
     */
    public GridModel qryMAVResData(ScalesEntity scalesEntity){
        //查询本次页面的结果集
        List<ScalesEntity> list = laboryMapper.qryMAVResData(scalesEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    @Override
    public void addLabScalesResultOrg(ScalesEntity scalesEntity) {
        scalesEntity.setScalesResId(String .valueOf(commonService.getNextval("SEQ_MINE_CHEMICAL_ID")));
        laboryMapper.addLabScalesResultOrg(scalesEntity);
    }

    @Override
    public void addLabScalesResultOrgNew(ScalesEntity scalesEntity) {
        laboryMapper.addLabScalesResultOrgNew(scalesEntity);
    }

    /**
     * 查询定硫仪化验数据
     */
    public GridModel qrySResData(SEntity sEntity){
        //查询本次页面的结果集
        List<SEntity> list = laboryMapper.qrySResData(sEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询定硫仪化验原始数据
     */
    public GridModel qrySResOrgData(SEntity sEntity){
        //查询本次页面的结果集
        List<SEntity> list = laboryMapper.qrySResOrgData(sEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }


    //编辑化验数据
    public void editLabData(SEntity sEntity){
        laboryMapper.editLabData(sEntity);
    }
    //编辑化验数据
    public void deleteLabData(SEntity sEntity){
        laboryMapper.deleteLabData(sEntity);
    }
    //提交审批，并生成化验报告
    public void submit4LabReport(SEntity sEntity){
        laboryMapper.submit4LabReport(sEntity);
    }

    //提交审批，并生成化验报告
    public void submit4LabReportNew(SEntity sEntity){
        laboryMapper.submit4LabReportNew(sEntity);
    }

    //提交审批
    public void labReport2Appr(SEntity sEntity){
        laboryMapper.labReport2Appr(sEntity);
    }

    @Override
    public void submitLabResults(SEntity sEntity) {
        laboryMapper.submitLabResults(sEntity);
    }

    @Override
    public void submitRLLabResults(SEntity sEntity) {
        laboryMapper.submitRLLabResults(sEntity);
    }


    //加权处理
    @Override
    public void doProportion(SEntity sEntity) {
        laboryMapper.doProportion(sEntity);
    }

    //生成化验台账并提交审批
    public void submit4LabBook(BookEntity bookEntity){
        laboryMapper.submit4LabBook(bookEntity);
    }

    /**
     * 查询工业分析仪化验原始数据
     */
    public GridModel qryIAResOrgData(IndstAnalyEntity indstAnalyEntity){
        //查询本次页面的结果集
        List<IndstAnalyEntity> list = laboryMapper.qryIAResOrgData(indstAnalyEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询工业分析仪化验数据
     */
    public GridModel qryIAResData(IndstAnalyEntity indstAnalyEntity){
        //查询本次页面的结果集
        List<IndstAnalyEntity> list = laboryMapper.qryIAResData(indstAnalyEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询工业分析仪化验原始数据
     */
    public GridModel qryHotResOrgData(HotEntity hotEntity){
        //查询本次页面的结果集
        List<HotEntity> list = laboryMapper.qryHotResOrgData(hotEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询工业分析仪化验数据
     */
    public GridModel qryHotResData(HotEntity hotEntity){
        //查询本次页面的结果集
        List<HotEntity> list = laboryMapper.qryHotResData(hotEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }


    /**
     * 查询工业分析仪化验原始数据
     */
    public GridModel qryLaResOrgData(LightAnalyEntity lightAnalyEntity){
        //查询本次页面的结果集
        List<LightAnalyEntity> list = laboryMapper.qryLaResOrgData(lightAnalyEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询红外测氢仪化验原始数据
     */
    public GridModel qryHwResOrgData(HEntity hEntity){
        //查询本次页面的结果集
        List<HEntity> list = laboryMapper.qryHwResOrgData(hEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询工业分析仪化验数据
     */
    public GridModel qryLaResData(LightAnalyEntity lightAnalyEntity){
        //查询本次页面的结果集
        List<LightAnalyEntity> list = laboryMapper.qryLaResData(lightAnalyEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询红外测氢仪化验数据
     */
    public GridModel qryHwResData(HEntity hEntity){
        //查询本次页面的结果集
        List<HEntity> list = laboryMapper.qryHwResData(hEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }


    /**    根据入厂入炉条件查询化验原始数据开始  */

    /**
     * 查询天平化验原始数据
     */
    public GridModel qryScalesResOrgDataByUsage(ScalesEntity scalesEntity){
        //查询本次页面的结果集
        List<ScalesEntity> list = laboryMapper.qryScalesResOrgDataByUsage(scalesEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询工业分析仪化验原始数据
     */
    public GridModel qryIAResOrgDataByUsage(IndstAnalyEntity indstAnalyEntity){
        //查询本次页面的结果集
        List<IndstAnalyEntity> list = laboryMapper.qryIAResOrgDataByUsage(indstAnalyEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询量热仪化验原始数据
     */
    public GridModel qryHotResOrgDataByUsage(HotEntity hotEntity){
        //查询本次页面的结果集
        List<HotEntity> list = laboryMapper.qryHotResOrgDataByUsage(hotEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询定硫仪化验原始数据
     */
    public GridModel qrySResOrgDataByUsage(SEntity sEntity){
        //查询本次页面的结果集
        List<SEntity> list = laboryMapper.qrySResOrgDataByUsage(sEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询光波水分化验原始数据
     */
    public GridModel qryLaResOrgDataByUsage(LightAnalyEntity lightAnalyEntity){
        //查询本次页面的结果集
        List<LightAnalyEntity> list = laboryMapper.qryLaResOrgDataByUsage(lightAnalyEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询红外测氢仪化验原始数据
     */
    public GridModel qryHwResOrgDataByUsage(HEntity hEntity){
        //查询本次页面的结果集
        List<HEntity> list = laboryMapper.qryHwResOrgDataByUsage(hEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询灰熔融数据
     */
    public GridModel qryMeltResOrgDataByUsage(MeltEntity meltEntity){
        //查询本次页面的结果集
        List<MeltEntity> list = laboryMapper.qryMeltResOrgDataByUsage(meltEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**    根据入厂入炉条件查询化验原始数据结束  */



    /**
     * 查询化验报告数据
     */
    public GridModel qryReportData(ReportEntity reportEntity){
        //查询本次页面的结果集
        List<ReportEntity> list = laboryMapper.qryReportData(reportEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }
    //为都匀单独使用 by xxs
    public GridModel qryReportData4Dy(ReportEntity reportEntity){
        //查询本次页面的结果集
        List<ReportEntity> list = laboryMapper.qryReportData4Dy(reportEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    //为蚌埠化验月报单独使用 by zwj
    public GridModel qryReportData4BB(ReportEntity reportEntity){
        //查询本次页面的结果集
        List<ReportEntity> list = laboryMapper.qryReportData4BB(reportEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询船运煤化验报告数据
     */
    public GridModel qryReportData4Ship(ReportEntity reportEntity){
        //查询本次页面的结果集
        List<ReportEntity> list = laboryMapper.qryReportData4Ship(reportEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询船运煤化验报告数据
     */
    public GridModel qryReportData4ShipBB(ReportEntity reportEntity){
        //查询本次页面的结果集
        List<ReportEntity> list = laboryMapper.qryReportData4ShipBB(reportEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询船运煤化验报告数据
     */
    public GridModel qryReportData4CCY(ReportEntity reportEntity){
        //查询本次页面的结果集
        List<ReportEntity> list = laboryMapper.qryReportData4CCY(reportEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询船运煤化验报告数据 乐东罗荣需要特定的排序方式 xxs20180316
     */
    public GridModel qryReportData4Ship4ld(ReportEntity reportEntity){
        //查询本次页面的结果集
        List<ReportEntity> list = laboryMapper.qryReportData4Ship4ld(reportEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    /**
     * 查询加权后的化验报告数据
     */
    public GridModel qryReportData4proportion(ReportEntity reportEntity){
        //查询本次页面的结果集
        List<ReportEntity> list = laboryMapper.qryReportData4proportion(reportEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }
    /**
     * 查询化验报告数据
     */
    public GridModel qryBookData(BookEntity bookEntity){
        //查询本次页面的结果集
        List<BookEntity> list = laboryMapper.qryBookData(bookEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }
    /**
     * 查询化验报告数据
     */
    public GridModel qryReportDataView(ReportEntity reportEntity){
        //查询本次页面的结果集
        List<ReportEntity> list = laboryMapper.qryReportDataView(reportEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    public GridModel qryResultReportData(ReportEntity reportEntity){
        //查询本次页面的结果集
        List<ReportEntity> list = laboryMapper.qryResultReportData(reportEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    //检查船是否已经有灰熔融性温度
    public String countShipST(String laborCode){
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setLaborCode(laborCode);
        return laboryMapper.countShipST(reportEntity);
    }

    //查询化验报告数据
    public String qryLabReportData(String laborCode,String batchType){
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setLaborCode(laborCode);
        reportEntity.setBatchType(batchType);
        return laboryMapper.qryLabReportData(reportEntity);
    }

    //查询抽检样化验报告数据
    public String qryLabReportData4Sample(String laborReportID,String sampleLaborReportID){
        ReportEntity reportEntity = new ReportEntity();
        String laborReportIDTemp = laborReportID + '+' + sampleLaborReportID;//将两个ID拼接下传进去
        reportEntity.setLabReportId(laborReportIDTemp);
        return laboryMapper.qryLabReportData4Sample(reportEntity);
    }

    //蚌埠查看抽查样对比报告
    public String qryLabReportData4SampleCompare(String sampleLaborReportID){
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setLabReportId(sampleLaborReportID);
        return laboryMapper.qryLabReportData4SampleCompare(reportEntity);
    }

    //查询化验报告数据
    public String qryLabReportData4Ship(String shipRecID){
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setShipRecID(shipRecID);
        return laboryMapper.qryLabReportData4Ship(reportEntity);
    }

    //查询入炉化验报告数据
    public String qryLabReportData4RL(String rlDate){
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setRlDate(rlDate);
        return laboryMapper.qryLabReportData4RL(reportEntity);
    }

    //查询化验报告数据
    public String getLabReportData4Ship(String shipRecID){
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setShipRecID(shipRecID);
        return laboryMapper.getLabReportData4Ship(reportEntity);
    }

    @Override
    public GridModel qryLabChemicalAnalyList(LabChemicalAnalyLogEntity labChemicalAnalyLogEntity) {
        List<LabChemicalAnalyLogEntity> list = laboryMapper.qryLabChemicalAnalyList(labChemicalAnalyLogEntity);
        Integer totalCnt = laboryMapper.qryLabChemicalAnalyListCnt(labChemicalAnalyLogEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);
        return m;
    }

    @Override
    public void addLabChemicalAnaly(LabChemicalAnalyEntity labChemicalAnalyEntity) {
        MineChemicalRelEntity mineChemicalRelEntity = new MineChemicalRelEntity();
        MineChemicalRelEntity mineChemicalRelEntityTmp = new MineChemicalRelEntity();
        LabChemicalAnalyLogEntity labChemicalAnalyLogEntity = new LabChemicalAnalyLogEntity();
        LabChemicalAnalyEntity labChemicalAnalyEntityTmp = new LabChemicalAnalyEntity();
        Long mineChemicalId = null;
        Long chemicalAnalyId = null;
        Long chemicalAnalyLogId = null;

        mineChemicalRelEntity.setMineNo(labChemicalAnalyEntity.getMineNo());
        mineChemicalRelEntity.setChemicalType(labChemicalAnalyEntity.getChemicalType());
        mineChemicalRelEntityTmp = laboryMapper.qryMineChemicalReal(mineChemicalRelEntity);
        if (mineChemicalRelEntityTmp == null) {
            mineChemicalId = commonService.getNextval("SEQ_MINE_CHEMICAL_ID");
            mineChemicalRelEntity.setMineChemicalId(mineChemicalId);
            mineChemicalRelEntity.setOpCode(labChemicalAnalyEntity.getOpCode());
            laboryMapper.addMineChemicalRel(mineChemicalRelEntity);
        } else {
            mineChemicalId = mineChemicalRelEntityTmp.getMineChemicalId();
        }
        labChemicalAnalyEntity.setMineChemicalId(mineChemicalId);
        chemicalAnalyLogId = commonService.getNextval("SEQ_LAB_CHEMICAL_ANALY_LOG_ID");

        labChemicalAnalyLogEntity.setChemicalAnalyLogId(chemicalAnalyLogId);
        labChemicalAnalyLogEntity.setMineChemicalId(labChemicalAnalyEntity.getMineChemicalId());
        labChemicalAnalyLogEntity.setStartTime(labChemicalAnalyEntity.getStartTime());
        labChemicalAnalyLogEntity.setEndTime(labChemicalAnalyEntity.getEndTime());
        labChemicalAnalyLogEntity.setChemicalValue(labChemicalAnalyEntity.getChemicalValue());
        labChemicalAnalyLogEntity.setOpCode(labChemicalAnalyEntity.getOpCode());
        labChemicalAnalyLogEntity.setHisTag(1);

        if (labChemicalAnalyEntity.getDoWhat().equals("0")) {
            chemicalAnalyId = commonService.getNextval("SEQ_LAB_CHEMICAL_ANALY_ID");
            labChemicalAnalyEntity.setChemicalAnalyId(chemicalAnalyId);
            labChemicalAnalyLogEntity.setChemicalAnalyId(labChemicalAnalyEntity.getChemicalAnalyId());
            labChemicalAnalyLogEntity.setOperateTag(1);
            laboryMapper.addLabChemicalAnaly(labChemicalAnalyEntity);
            saveLabChemicalAnalyLog(labChemicalAnalyLogEntity);
        } else {
            labChemicalAnalyLogEntity.setChemicalAnalyId(labChemicalAnalyEntity.getChemicalAnalyId());
            labChemicalAnalyLogEntity.setOperateTag(2);
            laboryMapper.modiLabChemicalAnaly(labChemicalAnalyEntity);
            saveLabChemicalAnalyLog(labChemicalAnalyLogEntity);
        }
    }

    @Override
    public void delLabChemicalAnaly(LabChemicalAnalyEntity labChemicalAnalyEntity) {
        LabChemicalAnalyLogEntity labChemicalAnalyLogEntity = new LabChemicalAnalyLogEntity();
        labChemicalAnalyLogEntity.setChemicalAnalyLogId(commonService.getNextval("SEQ_LAB_CHEMICAL_ANALY_LOG_ID"));
        LabChemicalAnalyEntity labChemicalAnalyEntityTmp = laboryMapper.qryLabChemicalAnaly(labChemicalAnalyEntity);
        labChemicalAnalyLogEntity.setChemicalAnalyId(labChemicalAnalyEntityTmp.getChemicalAnalyId());
        labChemicalAnalyLogEntity.setMineChemicalId(labChemicalAnalyEntityTmp.getMineChemicalId());
        labChemicalAnalyLogEntity.setStartTime(labChemicalAnalyEntityTmp.getStartTime());
        labChemicalAnalyLogEntity.setEndTime(labChemicalAnalyEntityTmp.getEndTime());
        labChemicalAnalyLogEntity.setChemicalValue(labChemicalAnalyEntityTmp.getChemicalValue());
        labChemicalAnalyLogEntity.setHisTag(1);
        labChemicalAnalyLogEntity.setOperateTag(0);
        laboryMapper.delLabChemicalAnalyLog(labChemicalAnalyLogEntity);
        laboryMapper.delLabChemicalAnaly(labChemicalAnalyEntityTmp);
//        saveLabChemicalAnalyLog(labChemicalAnalyLogEntity);
    }


    public void delLabChemicalAnalyNew(LabChemicalAnalyEntity labChemicalAnalyEntity) {

        laboryMapper.delLabChemicalAnaly(labChemicalAnalyEntity);

    }



    @Override
    public LabChemicalAnalyEntity qryLabChemicalAnaly(LabChemicalAnalyEntity labChemicalAnalyEntity) {
        LabChemicalAnalyEntity  returnValue = null;
        MineChemicalRelEntity mineChemicalRelEntity = new MineChemicalRelEntity();
        mineChemicalRelEntity.setMineNo(labChemicalAnalyEntity.getMineNo());
        mineChemicalRelEntity.setChemicalType(labChemicalAnalyEntity.getChemicalType());
        mineChemicalRelEntity = laboryMapper.qryMineChemicalReal(mineChemicalRelEntity);
        if (mineChemicalRelEntity != null) {
            labChemicalAnalyEntity.setMineChemicalId(mineChemicalRelEntity.getMineChemicalId());
            returnValue = laboryMapper.qryLabChemicalAnaly(labChemicalAnalyEntity);
        }
        return returnValue;

    }

    public void saveLabChemicalAnalyLog(LabChemicalAnalyLogEntity labChemicalAnalyLogEntity) {
        laboryMapper.modiLabChemicalAnalyLog(labChemicalAnalyLogEntity);
        laboryMapper.addLabChemicalAnalyLog(labChemicalAnalyLogEntity);
    }

    @Override
    public Integer qryLabChemicalAnalyLogListCnt(LabChemicalAnalyLogEntity labChemicalAnalyLogEntity) {
        return laboryMapper.qryLabChemicalAnalyLogListCnt(labChemicalAnalyLogEntity);
    }
    public void scalesLotAddSubmit(ScalesEntity scalesEntity){
        laboryMapper.scalesLotAddSubmit(scalesEntity);
    }

    public void labOrgDataConfirm(ScalesEntity scalesEntity){
        laboryMapper.labOrgDataConfirm(scalesEntity);
    }

    public void labMTFinalConfirm(ScalesEntity scalesEntity){
        laboryMapper.labMTFinalConfirm(scalesEntity);
    }

    /**
     * 查询化验室取数配置
     */
    public GridModel qryLabDataUploadCfg(LabDataUploadEntity labDataUploadEntity){
        //查询本次页面的结果集
        List<LabDataUploadEntity> list = laboryMapper.qryLabDataUploadCfg(labDataUploadEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    //上传化验数据
    public void uploadLabData(LabDataUploadEntity labDataUploadEntity) throws Exception {
        boolean wbRes = true;//true表示执行成功
        Connection connLab = null;

        try {
            //查询到当前需要上传的
            List<LabDataUploadEntity> list = laboryMapper.qryLabDataUploadCfg(labDataUploadEntity);
            LabDataUploadEntity config = list.get(0);

            //建立数据库连接
            try {
                Class.forName(config.getDbDriver()).newInstance();
                connLab = DriverManager.getConnection(config.getDbLinkUrl(), config.getLoginAcct() == null ? "" : config.getLoginAcct(), config.getLoginPasswd() == null ? "" : config.getLoginPasswd());
            } catch (Exception e) {
                labDataUploadEntity.setResCode("1");
                labDataUploadEntity.setResMsg("连接化验室数据库失败:"+e.getMessage());
                return;
            }

            if (connLab == null) {
                labDataUploadEntity.setResCode("1");
                labDataUploadEntity.setResMsg("连接化验室数据库失败！");
                return;
            }

            //获取化验数据
            String laborCode = labDataUploadEntity.getLaborCode();
            String dbType = config.getDbType();
            String dataString = "";
            if (dbType != null && dbType.equals("sqlite")){
                 dataString = LabDataUploadDAO.getInstance().getSyncDataSqlite(config, connLab, laborCode);
            }else {
                 dataString = LabDataUploadDAO.getInstance().getSyncData(config, connLab, laborCode);
            }

            if (dataString != null && dataString.equals("[]")) {
                labDataUploadEntity.setResCode("1");
                labDataUploadEntity.setResMsg("未查询到可上传的数据！");
                return;
            } else if (dataString == null) {
                labDataUploadEntity.setResCode("1");
                labDataUploadEntity.setResMsg("查询上传数据失败！");
                return;
            }

            labDataUploadEntity.setSyncData(dataString);
            labDataUploadEntity.setTaskId(config.getLabCfgId());

            //同步数据到管控系统，失败了数据库自己回滚
            laboryMapper.syncData2TargetDB(labDataUploadEntity);

            if (labDataUploadEntity.getResCode() != null && labDataUploadEntity.getResCode().equals("0")) {
                if (config.getWriteBackTag().equalsIgnoreCase("Y")) {

                    wbRes = LabDataUploadDAO.getInstance().writeBackSyncData(config, dataString, connLab);
                    if (!wbRes) {
                        connLab.rollback();
                        throw new Exception("同步数据回写状态失败");//抛出异常，则会回滚oracle
                    }
                }
            } else {//失败了数据库自己回滚
                labDataUploadEntity.setResCode("1");
                labDataUploadEntity.setResMsg("同步数据入库失败：" + labDataUploadEntity.getResMsg());
                return;
            }

            //最后都成功了，则提交
            connLab.commit();
        } catch (Exception e) {
            if (connLab != null) {
                try {
                    connLab.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (connLab != null) {
                try {
                    connLab.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 计算天平称量数据的挥发分
     */
    public void calcVad(SEntity sEntity){
        String laborCode = sEntity.getLaborCode();

        //如果有化验编码，则通过化验编码计算挥发分的值，否则默认通过指定记录的主键ID来计算挥发分
        if (laborCode != null && laborCode.length() > 0) {
            laboryMapper.calcVad(sEntity);
        } else {
            laboryMapper.calcVadByIds(sEntity);
        }

    }

    /**
     * 撤销已经确认的数据
     */
    public void labDataUndo(SEntity sEntity){
        laboryMapper.labDataUndo(sEntity);
    }

    public void addLabOrgData(SEntity sEntity){
        laboryMapper.addLabOrgData(sEntity);
    }

    //查询化验报告数据
    public String qryResultData(String laborCode,String batchType){
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setLaborCode(laborCode);
        reportEntity.setBatchType(batchType);
        return laboryMapper.qryResultData(reportEntity);
    }

    public String getMineByLaborCode(String laborCode){
        return laboryMapper.getMineByLaborCode(laborCode);
    }

    public void addHValueSummit(String laborCode){
        laboryMapper.addHValueSummit(laborCode);
    }

    //提交人工输入的化验指标
    public void submitInput(SEntity sEntity){
        laboryMapper.submitInput(sEntity);
    }

    public void modifyLabReport(ReportEntity reportEntity){
        laboryMapper.modifyLabReport(reportEntity);
    }

    public void labOrgDataInvalid(SEntity sEntity){
        laboryMapper.labOrgDataInvalid(sEntity);
    }

    //提交氢元素数据
    public void submitHad(SEntity sEntity){
        laboryMapper.submitHad(sEntity);
    }

    //前台配置氢元素数据
    public void manualSubmitHad(LabChemicalAnalyEntity labChemicalAnalyEntity){
        laboryMapper.manualSubmitHad(labChemicalAnalyEntity);
    }
    @Override
    public GridModel qryLabChemicalAnalyListNew(LabChemicalAnalyEntity labChemicalAnalyEntity) {
        List<LabChemicalAnalyEntity> list = laboryMapper.qryLabChemicalAnalyListNew(labChemicalAnalyEntity);
        Integer totalCnt = laboryMapper.qryLabChemicalAnalyListNewCnt(labChemicalAnalyEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);
        return m;
    }

    public  String qryLabOrgDataReportView(LabChemicalAnalyEntity labChemicalAnalyEntity){
        return laboryMapper.qryLabOrgDataReportView(labChemicalAnalyEntity);
    }

    /**
     * 衡丰电厂查询水，灰，挥发的确认后的化验数据
     */
    public GridModel qryMAVResDataNew(ScalesEntity scalesEntity){
        //查询本次页面的结果集
        List<ScalesEntity> list = laboryMapper.qryMAVResDataNew(scalesEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }


    //查询化验抽查样报告
    public String qryCheckSampleData(String laborCodeArray){
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setLaborCodeArray(laborCodeArray);
        return laboryMapper.qryCheckSampleData(reportEntity);
    }
	
    //查询煤期间分析核查记录
    public GridModel qryLabDayCheckData(LabDayCheckEntity labDayCheckEntity){
        //查询本次页面的结果集
        List<LabDayCheckEntity> list = laboryMapper.qryLabDayCheckData(labDayCheckEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
	    return m;
    }
	
   //编辑煤期间分析核查记录，add by zwj 0913
    public void editLabDayCheck(ScalesEntity scalesEntity){
        laboryMapper.editLabDayCheck(scalesEntity);
    }

    //查询热容量标定记录
    public GridModel qryLabQbCheckData(LabQbCheckEntity labQbCheckEntity){
        //查询本次页面的结果集
        List<LabQbCheckEntity> list = laboryMapper.qryLabQbCheckData(labQbCheckEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //编辑热容量标定记录，add by zwj 0913
    public void editLabQbCheck(ScalesEntity scalesEntity){
        laboryMapper.editLabQbCheck(scalesEntity);
    }

    ////入炉煤综合样   LD
    public List<Map<String,Object>> qryRLMonth(Map<String,Object> reportEntity){
        //查询本次页面的结果集
        List<Map<String,Object>> list = laboryMapper.qryRLMonth(reportEntity);
        return list;
    }

    //单个编码报告 ，入厂、入厂总样、盘煤样、煤场煤样  LD
    public List<Map<String,Object>> qrySingleReport(Map<String,Object> reportEntity){
        //查询本次页面的结果集
        List<Map<String,Object>> list = laboryMapper.qrySingleReport(reportEntity);
        return list;
    }

//    ////mc煤综合样   LD
//    public List<Map<String,Object>> qryMcMonth(Map<String,Object> reportEntity){
//        //查询本次页面的结果集
//        List<Map<String,Object>> list = laboryMapper.qryMcMonth(reportEntity);
//        return list;
//    }
    ////入炉煤综合样   LD
    public List<Map<String,Object>> qryRLDay(Map<String,Object> reportEntity){
        //查询本次页面的结果集
        List<Map<String,Object>> list = laboryMapper.qryRLDay(reportEntity);
        return list;
    }
    public List<Map<String,Object>> qryRLDay2(Map<String,Object> reportEntity){
        //查询本次页面的结果集
        List<Map<String,Object>> list = laboryMapper.qryRLDay2(reportEntity);
        return list;
    }

    public List<Map<String,Object>> qryRLDay3(Map<String,Object> reportEntity){
        //查询本次页面的结果集
        List<Map<String,Object>> list = laboryMapper.qryRLDay3(reportEntity);
        return list;
    }

    //查询化验员随机分配化验仪器的记录 xxs 20180207
    public GridModel qryLabManRandomList(LabManRandomEntity labManRandomEntity) {
        //查询本次页面的结果集
        List<LabManRandomEntity> LabManRandomEntityList = laboryMapper.qryLabManRandomList(labManRandomEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(LabManRandomEntityList);
        return m;
    }

    //查询化验员随机分配化验仪器的记录 xxs 20180207
    public void addLabManRandomInfo(LabManRandomEntity labManRandomEntity){
        laboryMapper.addLabManRandomInfo(labManRandomEntity);
    }

    //入炉煤月度加权表 LD
    public List<Map<String,Object>> qryCoalBurnRlMonthInfo(Map<String,Object> reportEntity){
        //查询本次页面的结果集
        List<Map<String,Object>> list = laboryMapper.qryCoalBurnRlMonthInfo(reportEntity);
        return list;
    }
	
	    //ld飞灰
    public List<Map<String,Object>> qryFlyAsh(Map<String,Object> reportEntity){
        //查询本次页面的结果集
        List<Map<String,Object>> list = laboryMapper.qryFlyAsh(reportEntity);
        return list;
    }
    //ld飞灰编辑新增保存
    public void editFlyAsh(ScalesEntity scalesEntity){
        laboryMapper.editFlyAsh(scalesEntity);
    }

    //保存接样信息
    public void setReceiveInfo(BatchNoInfoEntity batchNoInfoEntity){
        laboryMapper.setReceiveInfo(batchNoInfoEntity);
    }

    //化验设备国标信息   九江需要的  xxs 20190410
    public GridModel qryLabStandardList(LabStandardEntity labStandardEntity){
        //查询本次页面的结果集
        List<LabStandardEntity> LabStandardEntityList = laboryMapper.qryLabStandardList(labStandardEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(LabStandardEntityList);
        return m;
    }

    public void addLabStandardInfo(LabStandardEntity labStandardEntity){
        laboryMapper.addLabStandardInfo(labStandardEntity);
    }

    public void delLabStandardInfo(LabStandardEntity labStandardEntity){
        laboryMapper.delLabStandardInfo(labStandardEntity);
    }

    //飞灰月报-ld
    public List<Map<String,Object>> qryFlyAshMonth(Map<String,Object> reportEntity){
        //查询本次页面的结果集
        List<Map<String,Object>> list = laboryMapper.qryFlyAshMonth(reportEntity);
        return list;
    }

    //炉渣碳含量月报-ld
    public List<Map<String,Object>> qrySlagAshMonth(Map<String,Object> reportEntity){
        //查询本次页面的结果集
        List<Map<String,Object>> list = laboryMapper.qrySlagAshMonth(reportEntity);
        return list;
    }

    //化验超差数据进行取舍的原因  xxs20190419
    public GridModel qryLabCcInfoList(LabCcInfoEntity labCcInfoEntity){
        //查询总记录数
        Integer totalCnt = laboryMapper.qryLabCcInfoCnt(labCcInfoEntity);
        //查询本次页面的结果集
        List<LabCcInfoEntity> LabCcInfoEntityList = laboryMapper.qryLabCcInfoList(labCcInfoEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(LabCcInfoEntityList);
        m.setTotal(totalCnt);
        return m;
    }

    //化验超差数据进行取舍的原因  xxs20190419
    public void editLabCcInfo(LabCcInfoEntity labCcInfoEntity){
        laboryMapper.editLabCcInfo(labCcInfoEntity);
    }

    //入炉煤全年统计表   LD
    public List<Map<String,Object>> qryCoalBurnMonthly(Map<String,Object> reportEntity){
        //查询本次页面的结果集
        List<Map<String,Object>> list = laboryMapper.qryCoalBurnMonthly(reportEntity);
        return list;
    }

    //查询化验报告数据
    public String qryRLDayQz(Map<String,Object> param){
        String res = laboryMapper.qryRLDayQz(param);
        return res;
    }

}
