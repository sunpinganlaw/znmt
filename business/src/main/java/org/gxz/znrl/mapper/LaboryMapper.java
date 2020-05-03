package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by xieyt on 15-1-21.
 */

@Repository
public interface LaboryMapper {

    /**
     * 查询灰融化验原始数据
     */
    public List<MeltEntity> qryMeltResOrgData(MeltEntity meltEntity);

    /**
     * 查询灰融化验数据
     */
    public List<MeltEntity> qryMeltResData(MeltEntity meltEntity);

    /**
     * 查询天平化验原始数据
     */
    public List<ScalesEntity> qryScalesResOrgData(ScalesEntity scalesEntity);

    /**
     * 查询天平化验原始数据
     */
    public List<ScalesEntity> qryScalesResOrgDataNew(ScalesEntity scalesEntity);

    /**
     * 查询水，灰，挥发的确认后的化验数据
     */
    public List<ScalesEntity> qryMAVResData(ScalesEntity scalesEntity);

    /**
     * 前台手工录入天平数据
     */
    public void addLabScalesResultOrg(ScalesEntity scalesEntity);

    /**
     * 前台手工录入天平数据
     */
    public void addLabScalesResultOrgNew(ScalesEntity scalesEntity);

    /**
     * 查询天平化验数据
     */
    public List<ScalesEntity> qryScalesResData(ScalesEntity scalesEntity);

    /**
     * 查询定硫仪化验数据
     */
    public List<SEntity> qrySResData(SEntity sEntity);

    /**
     * 查询定硫仪化验原始数据
     */
    public List<SEntity> qrySResOrgData(SEntity sEntity);


    public List<SEntity> submitLabResults(SEntity sEntity);

    public List<SEntity> submitRLLabResults(SEntity sEntity);

    public List<SEntity> doProportion(SEntity sEntity);

    //编辑化验数据
    public void editLabData(SEntity sEntity);

    //编辑化验数据
    public void deleteLabData(SEntity sEntity);
    //提交审批，并生成化验报告
    public void submit4LabReport(SEntity sEntity);

    //提交审批，并生成化验报告
    public void submit4LabReportNew(SEntity sEntity);

    //提交审批
    public void labReport2Appr(SEntity sEntity);

    //生成化验台账并提交审批
    public void submit4LabBook(BookEntity bookEntity);

    /**
     * 查询工业分析仪化验原始数据
     */
    public List<IndstAnalyEntity> qryIAResOrgData(IndstAnalyEntity indstAnalyEntity);

    /**
     * 查询工业分析仪化验数据
     */
    public List<IndstAnalyEntity> qryIAResData(IndstAnalyEntity indstAnalyEntity);

    /**
     * 查询量热仪化验原始数据
     */
    public List<HotEntity> qryHotResOrgData(HotEntity hotEntity);

    /**
     * 查询量热仪化验数据
     */
    public List<HotEntity> qryHotResData(HotEntity hotEntity);

    /**
     * 查询光波分析仪化验原始数据
     */
    public List<LightAnalyEntity> qryLaResOrgData(LightAnalyEntity lightAnalyEntity);

    /**
     * 查询红外测氢仪化验原始数据
     */
    public List<HEntity> qryHwResOrgData(HEntity hEntity);

    /**
     * 查询光波分析仪化验数据
     */
    public List<LightAnalyEntity> qryLaResData(LightAnalyEntity lightAnalyEntity);
    /**
     * 查询红外测氢仪化验数据
     */
    public List<HEntity> qryHwResData(HEntity hEntity);


    /**    根据入厂入炉条件查询化验原始数据开始  */

    public List<ScalesEntity> qryScalesResOrgDataByUsage(ScalesEntity scalesEntity);

    public  List<IndstAnalyEntity> qryIAResOrgDataByUsage(IndstAnalyEntity indstAnalyEntity);

    public List<HotEntity> qryHotResOrgDataByUsage(HotEntity hotEntity);

    public List<SEntity> qrySResOrgDataByUsage(SEntity sEntity);

    public List<LightAnalyEntity> qryLaResOrgDataByUsage(LightAnalyEntity lightAnalyEntity);

    public List<MeltEntity> qryMeltResOrgDataByUsage(MeltEntity meltEntity);

    public List<HEntity> qryHwResOrgDataByUsage(HEntity hEntity);

    /**    根据入厂入炉条件查询化验原始数据结束  */

    /**
     * 查询化验报告结果
     */
    public List<ReportEntity> qryReportData(ReportEntity reportEntity);
    //为都匀单独使用 20170508 by xxs
    public List<ReportEntity> qryReportData4Dy(ReportEntity reportEntity);
    //为蚌埠化验月报单独使用 20170830 by zwj
    public List<ReportEntity> qryReportData4BB(ReportEntity reportEntity);
    /**
     * 查询化验报告结果-船运
     */
    public List<ReportEntity> qryReportData4Ship(ReportEntity reportEntity);
    public List<ReportEntity> qryReportData4ShipBB(ReportEntity reportEntity);
    public List<ReportEntity> qryReportData4CCY(ReportEntity reportEntity);
    /**
     * 查询化验报告结果-船运 乐东罗荣需要特定的排序方式 xxs20180316
     */
    public List<ReportEntity> qryReportData4Ship4ld(ReportEntity reportEntity);
    /**
     * 查询化验报告结果-加权后的
     */
    public List<ReportEntity> qryReportData4proportion(ReportEntity reportEntity);
    /**
     * 查询化验台账结果
     */
    public List<BookEntity> qryBookData(BookEntity bookEntity);
    /**
     * 根据化验台账查询化验结果
     */
    public List<ReportEntity> qryReportDataView(ReportEntity reportEntity);

    public List<ReportEntity> qryResultReportData(ReportEntity reportEntity);

    //检查船是否已经有灰熔融性温度
    public String countShipST(ReportEntity reportEntity);

    //查询化验报告数据
    public String qryLabReportData(ReportEntity reportEntity);
    //查询抽检样化验报告数据
    public String qryLabReportData4Sample(ReportEntity reportEntity);
    //蚌埠查看抽查样对比报告
    public String qryLabReportData4SampleCompare(ReportEntity reportEntity);
    //查询化验报告数据
    public String qryLabReportData4Ship(ReportEntity reportEntity);
    public String qryLabReportData4RL(ReportEntity reportEntity);
    //查询化验报告数据
    public String getLabReportData4Ship(ReportEntity reportEntity);
    //以下为元素设置方法 begin
    public MineChemicalRelEntity qryMineChemicalReal(MineChemicalRelEntity mineChemicalRelEntity);

    public LabChemicalAnalyEntity qryLabChemicalAnaly(LabChemicalAnalyEntity labChemicalAnalyEntity);

    public void addMineChemicalRel(MineChemicalRelEntity mineChemicalRelEntity);

    public void addLabChemicalAnaly(LabChemicalAnalyEntity labChemicalAnalyEntity);

    public void addLabChemicalAnalyLog(LabChemicalAnalyLogEntity labChemicalAnalyLogEntity);

    public void modiLabChemicalAnalyLog(LabChemicalAnalyLogEntity labChemicalAnalyLogEntity);

    public void delLabChemicalAnaly(LabChemicalAnalyEntity labChemicalAnalyEntity);

    public void delLabChemicalAnalyLog(LabChemicalAnalyLogEntity labChemicalAnalylogEntity);

    public void modiLabChemicalAnaly(LabChemicalAnalyEntity labChemicalAnalyEntity);

    public Integer qryLabChemicalAnalyListCnt(LabChemicalAnalyLogEntity labChemicalAnalyLogEntity);

    public Integer qryLabChemicalAnalyLogListCnt(LabChemicalAnalyLogEntity labChemicalAnalyLogEntity);

    public List<LabChemicalAnalyLogEntity> qryLabChemicalAnalyList(LabChemicalAnalyLogEntity labChemicalAnalyLogEntity);

    public void scalesLotAddSubmit(ScalesEntity scalesEntity);

    public void labOrgDataConfirm(ScalesEntity scalesEntity);

    public void labMTFinalConfirm(ScalesEntity scalesEntity);

    /**
     * 查询化验室取数配置
     */
    public List<LabDataUploadEntity> qryLabDataUploadCfg(LabDataUploadEntity labDataUploadEntity);

    /**
     * 同步数据到znrl数据库
     */
    public void syncData2TargetDB(LabDataUploadEntity labDataUploadEntity);

    /**
     * 计算天平称量数据的挥发分
     * @param sEntity
     */
    public void calcVad(SEntity sEntity);

    /**
     * 计算天平称量数据的挥发分，通过指定ID进行计算
     * @param sEntity
     */
    public void calcVadByIds(SEntity sEntity);

    /**
     * 撤销已经确认的数据
     * @param sEntity
     */
    public void labDataUndo(SEntity sEntity);

    //新增化验原始数据
    public void addLabOrgData(SEntity sEntity);

    //查询化验报告数据
    public String qryResultData(ReportEntity reportEntity);

    public String getMineByLaborCode(String laborCode);
    //元素设置方法 end

    //编辑化验数据
    public void addHValueSummit(String laborCode);

    //提交人工输入的化验指标
    public void submitInput(SEntity sEntity);

    public void modifyLabReport(ReportEntity reportEntity);

    public void labOrgDataInvalid(SEntity sEntity);

    //蚌埠查询月化验报告记录数
    public String qryMonthRptNum(ReportEntity reportEntity);

    //提交氢元素数据
    public void  submitHad(SEntity sEntity);

    //前台配置氢元素数据
    public void  manualSubmitHad(LabChemicalAnalyEntity labChemicalAnalyEntity);

    //元素查询
    public  List<LabChemicalAnalyEntity> qryLabChemicalAnalyListNew(LabChemicalAnalyEntity labChemicalAnalyEntity);

    public Integer qryLabChemicalAnalyListNewCnt(LabChemicalAnalyEntity labChemicalAnalyEntity);

    public String qryLabOrgDataReportView(LabChemicalAnalyEntity labChemicalAnalyEntity);

    /**
     * 衡丰查询水，灰，挥发的确认后的化验数据
     */
    public List<ScalesEntity> qryMAVResDataNew(ScalesEntity scalesEntity);

    //查询化验抽查样报告
    public String qryCheckSampleData(ReportEntity reportEntity);

    //查询煤质期间分析核查记录
    public List<LabDayCheckEntity> qryLabDayCheckData(LabDayCheckEntity labDayCheckEntity);

    //查询热容量标定记录
    public List<LabQbCheckEntity> qryLabQbCheckData(LabQbCheckEntity labQbCheckEntity);

    //编辑煤期间分析核查记录，add by zwj 0913
    public void editLabDayCheck(ScalesEntity scalesEntity);

    //编辑热容量标定记录，add by zwj 0913
    public void editLabQbCheck(ScalesEntity scalesEntity);

    //入炉综合样   LD
    public List<Map<String,Object>> qryRLMonth(Map<String,Object> reportEntity);

    //单个编码报告 ，入厂、入厂总样、盘煤样、煤场煤样  LD
    public List<Map<String,Object>> qrySingleReport(Map<String,Object> reportEntity);
//    //mc综合样   LD
//    public List<Map<String,Object>> qryMcMonth(Map<String,Object> reportEntity);
    //入炉日报   LD
    public List<Map<String,Object>> qryRLDay(Map<String,Object> reportEntity);
    public List<Map<String,Object>> qryRLDay2(Map<String,Object> reportEntity);
    public List<Map<String,Object>> qryRLDay3(Map<String,Object> reportEntity);

    //查询化验员随机分配化验仪器的记录 xxs 20180207
    public List<LabManRandomEntity> qryLabManRandomList(LabManRandomEntity LabManRandomEntity);
    public void addLabManRandomInfo(LabManRandomEntity labManRandomEntity);

    //入炉煤月度加权表 LD
    public List<Map<String,Object>> qryCoalBurnRlMonthInfo(Map<String,Object> reportEntity);
	
	public List<Map<String,Object>> qryFlyAsh(Map<String,Object> reportEntity);

    public void editFlyAsh(ScalesEntity scalesEntity);
    public void setReceiveInfo(BatchNoInfoEntity batchNoInfoEntity);

    //化验设备国标信息   九江需要的  xxs 20190410
    public List<LabStandardEntity> qryLabStandardList(LabStandardEntity labStandardEntity);
    public void addLabStandardInfo(LabStandardEntity labStandardEntity);
    public void delLabStandardInfo(LabStandardEntity labStandardEntity);

    //飞灰月报-ld
    public List<Map<String,Object>> qryFlyAshMonth(Map<String,Object> reportEntity);
    //炉渣碳含量月报-ld
    public List<Map<String,Object>> qrySlagAshMonth(Map<String,Object> reportEntity);

    //化验超差数据进行取舍的原因  xxs20190419
    public Integer qryLabCcInfoCnt(LabCcInfoEntity labCcInfoEntity);
    public List<LabCcInfoEntity> qryLabCcInfoList(LabCcInfoEntity labCcInfoEntity);
    public void editLabCcInfo(LabCcInfoEntity labCcInfoEntity);

    //入炉煤全年统计表   LD
    public List<Map<String,Object>> qryCoalBurnMonthly(Map<String,Object> reportEntity);

    public String qryRLDayQz(Map<String,Object> param);

}
