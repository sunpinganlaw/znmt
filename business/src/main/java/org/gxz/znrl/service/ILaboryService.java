package org.gxz.znrl.service;

import org.gxz.znrl.entity.*;
import org.gxz.znrl.viewModel.GridModel;

import java.util.List;
import java.util.Map;

/**
 * Created by xieyt on 15-1-21.
 */
public interface ILaboryService {
    /**
     * 查询灰融化验原始数据
     */
    public GridModel qryMeltResOrgData(MeltEntity meltEntity);

    /**
     * 查询灰融化验数据
     */
    public GridModel qryMeltResData(MeltEntity meltEntity);
    /**
     * 查询天平化验数据
     */
    public GridModel qryScalesResData(ScalesEntity scalesEntity);

    /**
     * 前台手工录入天平数据
     */
    public void addLabScalesResultOrg(ScalesEntity scalesEntity);

    public void addLabScalesResultOrgNew(ScalesEntity scalesEntity);

    /**
     * 查询天平化验原始数据
     */
    public GridModel qryScalesResOrgData(ScalesEntity scalesEntity);

    /**
     * 查询水，灰，挥发的确认后的化验数据
     */
    public GridModel qryMAVResData(ScalesEntity scalesEntity);

    /**
     * 查询天平化验原始数据
     */
    public GridModel qryScalesResOrgDataNew(ScalesEntity scalesEntity);

    /**
     * 查询定硫仪化验数据
     */
    public GridModel qrySResData(SEntity sEntity);

    /**
     * 查询定硫仪化验原始数据
     */
    public GridModel qrySResOrgData(SEntity sEntity);

    //编辑化验数据
    public void editLabData(SEntity sEntity);
    //失效化验数据
    public void deleteLabData(SEntity sEntity);

    //提交审批，并生成化验报告
    public void submit4LabReport(SEntity sEntity);

    //提交审批，并生成化验报告
    public void submit4LabReportNew(SEntity sEntity);

    //提交审批
    public void labReport2Appr(SEntity sEntity);

    //提交审批
    public void submitLabResults(SEntity sEntity);

    public void submitRLLabResults(SEntity sEntity);
    //加权处理
    public void doProportion(SEntity sEntity);

    //生成化验台账并提交审批
    public void submit4LabBook(BookEntity bookEntity);

    /**
     * 查询工业分析仪化验原始数据
     */
    public GridModel qryIAResOrgData(IndstAnalyEntity indstAnalyEntity);

    /**
     * 查询工业分析仪化验数据
     */
    public GridModel qryIAResData(IndstAnalyEntity indstAnalyEntity);

    /**
     * 查询工业分析仪化验原始数据
     */
    public GridModel qryHotResOrgData(HotEntity hotEntity);

    /**
     * 查询工业分析仪化验数据
     */
    public GridModel qryHotResData(HotEntity hotEntity);

    /**
     * 查询工业分析仪化验原始数据
     */
    public GridModel qryLaResOrgData(LightAnalyEntity lightAnalyEntity);

    /**
     * 查询元素分析析仪化验原始数据
     */
    public GridModel qryHwResOrgData(HEntity hEntity);

    /**    根据入厂入炉条件查询化验原始数据开始  */

    public GridModel qryScalesResOrgDataByUsage(ScalesEntity scalesEntity);

    public GridModel qryIAResOrgDataByUsage(IndstAnalyEntity indstAnalyEntity);

    public GridModel qryHotResOrgDataByUsage(HotEntity hotEntity);

    public GridModel qrySResOrgDataByUsage(SEntity sEntity);

    public GridModel qryLaResOrgDataByUsage(LightAnalyEntity lightAnalyEntity);

    public GridModel qryMeltResOrgDataByUsage(MeltEntity meltEntity);

    public GridModel qryHwResOrgDataByUsage(HEntity hEntity);

    /**    根据入厂入炉条件查询化验原始数据结束  */


    /**
     * 查询工业分析仪化验数据
     */
    public GridModel qryLaResData(LightAnalyEntity lightAnalyEntity);

    /**
     * 查询工业分析仪化验数据
     */
    public GridModel qryHwResData(HEntity hEntity);
    /**
     * 查询化验报告数据
     */
    public GridModel qryReportData( ReportEntity reportEntity);
    //为都匀单独使用 by xxs
    public GridModel qryReportData4Dy( ReportEntity reportEntity);

    //为蚌埠化验月报单独使用 by zwj
    public GridModel qryReportData4BB( ReportEntity reportEntity);
    /**
     * 查询船运煤化验报告数据
     */
    public GridModel qryReportData4Ship( ReportEntity reportEntity);
    public GridModel qryReportData4ShipBB( ReportEntity reportEntity);
    public GridModel qryReportData4CCY( ReportEntity reportEntity);
    /**
     * 查询船运煤化验报告数据 乐东罗荣需要特定的排序方式 xxs20180316
     */
    public GridModel qryReportData4Ship4ld( ReportEntity reportEntity);
    /**
     * 查询船运煤化验报告数据
     */
    public GridModel qryReportData4proportion( ReportEntity reportEntity);
    /**
     * 查询化验台账数据
     */
    public GridModel qryBookData( BookEntity bookEntity);
    /**
     * 查询化验台账查询化验报告数据
     */
    public GridModel qryReportDataView( ReportEntity reportEntity);


    public GridModel qryResultReportData( ReportEntity reportEntity);

    //检查船是否已经有灰熔融性温度
    public String countShipST(String laborCode);

    //查询化验报告数据
    public String qryLabReportData(String laborCode,String batchType);

    //查询抽检样化验报告数据
    public String qryLabReportData4Sample(String laborReportID,String sampleLaborReportID);
    //蚌埠查看抽查样对比报告
    public String qryLabReportData4SampleCompare(String sampleLaborReportID);

    //查询化验报告数据
    public String qryLabReportData4Ship(String shipRecID);
    public String qryLabReportData4RL(String rlDate);

    //查询化验报告数据
    public String getLabReportData4Ship(String shipRecID);

    //元素设置方法
    public GridModel qryLabChemicalAnalyList(LabChemicalAnalyLogEntity labChemicalAnalyLogEntity);

    public void addLabChemicalAnaly(LabChemicalAnalyEntity labChemicalAnalyEntity);

    public void delLabChemicalAnaly(LabChemicalAnalyEntity labChemicalAnalyEntity);
    public void delLabChemicalAnalyNew(LabChemicalAnalyEntity labChemicalAnalyEntity);

    public LabChemicalAnalyEntity qryLabChemicalAnaly(LabChemicalAnalyEntity labChemicalAnalyEntity);

    public Integer qryLabChemicalAnalyLogListCnt(LabChemicalAnalyLogEntity labChemicalAnalyLogEntity);

    public void scalesLotAddSubmit(ScalesEntity scalesEntity);

    public void labOrgDataConfirm(ScalesEntity scalesEntity);

    public void labMTFinalConfirm(ScalesEntity scalesEntity);

    /**
     * 查询化验室取数配置
     */
    public GridModel qryLabDataUploadCfg(LabDataUploadEntity labDataUploadEntity);

    //上传化验数据
    public void uploadLabData(LabDataUploadEntity labDataUploadEntity) throws Exception;

    /**
     * 计算天平称量数据的挥发分
     */
    public void calcVad(SEntity sEntity);

    /**
     * 撤销已经确认的数据
     */
    public void labDataUndo(SEntity sEntity);

    //新增化验原始数据
    public void addLabOrgData(SEntity sEntity);

    //查询化验报告数据
    public String qryResultData(String laborCode,String batchType);

    public String getMineByLaborCode(String laborCode);

    public void addHValueSummit(String laborCode);

    //提交人工输入的化验指标
    public void submitInput(SEntity sEntity);

    public void modifyLabReport(ReportEntity reportEntity);

    public void labOrgDataInvalid(SEntity sEntity);

    //提交氢元素数据
    public void submitHad(SEntity sEntity);

    //前台配置氢元素数据
    public void manualSubmitHad(LabChemicalAnalyEntity labChemicalAnalyEntity);

    //化验元素查询
    public GridModel qryLabChemicalAnalyListNew(LabChemicalAnalyEntity labChemicalAnalyEntity);

    //查询化验原始数据表单
    public String qryLabOrgDataReportView(LabChemicalAnalyEntity labChemicalAnalyEntity);

    /**
     * 衡丰电厂查询水，灰，挥发的确认后的化验数据
     */
    public GridModel qryMAVResDataNew(ScalesEntity scalesEntity);

    //查询化验抽查样报告
    public String qryCheckSampleData(String laborCodeArray);

    //查询煤期间分析核查记录
    public GridModel qryLabDayCheckData(LabDayCheckEntity labDayCheckEntity);

    //编辑煤期间分析核查记录，add by zwj 0913
    public void editLabDayCheck(ScalesEntity scalesEntity);

    //查询煤期间分析核查记录
    public GridModel qryLabQbCheckData(LabQbCheckEntity labQbCheckEntity);

    //编辑煤期间分析核查记录，add by zwj 0913
    public void editLabQbCheck(ScalesEntity scalesEntity);
    //入炉煤综合样   LD
    public List<Map<String,Object>> qryRLMonth(Map<String,Object> reportEntity);

    //单个编码报告 ，入厂、入厂总样、盘煤样、煤场煤样  LD
    public List<Map<String,Object>> qrySingleReport(Map<String,Object> reportEntity);

//    //mc煤综合样   LD
//    public List<Map<String,Object>> qryMcMonth(Map<String,Object> reportEntity);

    //入炉煤日报   LD
    public List<Map<String,Object>> qryRLDay(Map<String,Object> reportEntity);
    public List<Map<String,Object>> qryRLDay2(Map<String,Object> reportEntity);
    public List<Map<String,Object>> qryRLDay3(Map<String,Object> reportEntity);

    //查询化验员随机分配化验仪器的记录 xxs 20180207
    public GridModel qryLabManRandomList(LabManRandomEntity labManRandomEntity);

    //查询化验员随机分配化验仪器的记录 xxs 20180207
    public void addLabManRandomInfo(LabManRandomEntity labManRandomEntity);

    //入炉煤化验月度统计报表 LD
    public List<Map<String,Object>> qryCoalBurnRlMonthInfo(Map<String,Object> reportEntity);
	
	    //飞灰可然物  ld
    public List<Map<String,Object>> qryFlyAsh(Map<String,Object> reportEntity);
    public void editFlyAsh(ScalesEntity scalesEntity);
    public void setReceiveInfo(BatchNoInfoEntity batchNoInfoEntity);

    //化验设备国标信息   九江需要的  xxs 20190410
    public GridModel qryLabStandardList(LabStandardEntity labStandardEntity);
    public void addLabStandardInfo(LabStandardEntity labStandardEntity);
    public void delLabStandardInfo(LabStandardEntity labStandardEntity);

    //飞灰月报-ld
    public List<Map<String,Object>> qryFlyAshMonth(Map<String,Object> reportEntity);

    //炉渣碳含量月报-ld
    public List<Map<String,Object>> qrySlagAshMonth(Map<String,Object> reportEntity);

    //化验超差数据进行取舍的原因  xxs20190419
    public GridModel qryLabCcInfoList(LabCcInfoEntity labCcInfoEntity);
    public void editLabCcInfo(LabCcInfoEntity labCcInfoEntity);

    //入炉煤全年统计表   LD
    public List<Map<String,Object>> qryCoalBurnMonthly(Map<String,Object> reportEntity);

    //入炉煤日报  qz
    public String qryRLDayQz(Map<String,Object> param);


}
