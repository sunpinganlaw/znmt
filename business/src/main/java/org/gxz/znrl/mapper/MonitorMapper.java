package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-11-18.
 */

@Repository
public interface MonitorMapper{

    //称重 查询总记录数
    public Integer qryWeightListCnt(WeightEntity weightEntity);

    public List<WeightEntity> qryWeightList(WeightEntity weightEntity);

    //采样 查询总记录数
    public Integer qrySampleListCnt(WeightRptEntity sampleEntity);

    //查询本次页面的结果集
    public List<WeightRptEntity> qrySampleList(WeightRptEntity sampleEntity);

    //提交控制设备命令qryBeltWeightRec
    public void commitCtrlCmdPro(CtrlEntity ctrlEntity);

    //设置当前卸煤船
    public void setCurrentShip(ShipEntity shipEntity);

    public void setShipStatus(ShipEntity shipEntity);

    public void setShipRecoveryStatus(ShipEntity shipEntity);

    //查询今日来煤情况汇总
    public List<WeightRptEntity> qryTodayArrivedCoal();

    //查询今日汽车动态信息
    public List<WeightRptEntity> qryCarDynamic();

    //查询制样结果动态信息
    public List<MakeSampleEntity> qrySampling();

    //查询操作日志
    public  Integer qryOpLogInfoCnt(LogOpRecEntity logOpRecEntity);

    //查询操作日志
    public List<LogOpRecEntity> qryOpLogInfo(LogOpRecEntity logOpRecEntity);

    //查询设备动态信息
    public Integer qryDeviceBroadViewCnt(DeviceBroadEntity deviceBroadEntity);

    //查询设备动态信息
    public List<DeviceBroadEntity> qryDeviceBroadView(DeviceBroadEntity deviceBroadEntity);

    //查询设备动态信息
    public List<DeviceBroadEntity> qryDeviceBroad(DeviceBroadEntity deviceBroadEntity);

    //查询故障报警信息
    public List<DeviceBroadEntity> qryDeviceErr();

    //查询故障报警信息总数
    public Integer qryDeviceErrViewCnt(DeviceErrEntity deviceErrEntity);

    //查询故障报警信息
    public List<DeviceErrEntity> qryDeviceErrView(DeviceErrEntity deviceErrEntity);

    //查询故障报警信息详情
    public List<DeviceBroadEntity> qryDeviceErrDetail(String recId);

    //故障报警处理
    public void deviceErrDeal(DeviceBroadEntity deviceBroadEntity);

    //强制结束批次
    public void forceUpdateBatch(ShipEntity shipEntity);

    //煤样追踪查询-煤样瓶接收确认
    public void confirmSampleRecv(SampleTraceEntity sampleTraceEntity);

    //存查样柜的柜子状态查询
    public List<SampleBoxEntity> qrySampleBoxesInfo();

    //查询某柜子里的样包信息
    public List<SampleBoxEntity> qrySampleBagInfo(String boxNo);

    //查询某柜子里的样包信息,用于审批查询使用
    public List<SampleBoxEntity> qrySampleBag4Get(SampleBoxEntity sampleBoxEntity);

    //火车注册
    public void trainRegister(RegisterEntity registerEntity);

    //火车大票批量录入
    public void batchTrainDPRecord(RegisterEntity registerEntity);

    //火车大票批量录入并同时分批
    public void batchTrainDPRecordAndBatch(RegisterEntity registerEntity);

    //新增火车，可以在原来车次上新增，也可以新增测试
    public void addNewTrain(RegisterEntity registerEntity);

    //合并火车车次
    public void mergeTrain(RegisterEntity registerEntity);

    //合并车辆批次
    public void mergeCarBatchNo(RegisterEntity registerEntity);

    //拆分车次
    public void splitTrain(RegisterEntity registerEntity);

    //火车大票批量录入并同时分批
    public void adjustTrainOrder(RegisterEntity registerEntity);

    //火车来煤分批
    public void dealTrainBatchInfo(RegisterEntity registerEntity);

    //删除单个车厢
    public void deleteOneTrain(RegisterEntity registerEntity);

    //查询火车入厂登记信息详情
    public List<RegisterEntity> qryTrainDetailList(RegisterEntity registerEntity);
    //查询火车入厂登记信息详情
    public List<RegisterEntity> qryTrainDetailListHf(RegisterEntity registerEntity);
    //查询火车入厂登记信息合计
    public List<RegisterEntity> qryTrainDetailListSummary(RegisterEntity registerEntity);

    //查询火车入厂登记信息详情
    public List<RegisterEntity> qryTrainDetailListfc(RegisterEntity registerEntity);

    //查询火车入厂登记信息详情,大开使用
    public List<RegisterEntity> qryTrainDetailListDk(RegisterEntity registerEntity);

    //查询火车入厂登记基本信息详情
    public List<RegisterEntity> qryTrainBasicList(RegisterEntity registerEntity);

    //根据车次号删除火车记录
    public void deleteTrainInfo(String trainNo);

    //新增汽车信息
    public void addCarInfo(CarInfoEntity carInfoEntity);

    //新增汽车信息
    public void updateCarInfo(CarInfoEntity carInfoEntity);

    public void addMineCardInfo(MineCardDetailEntity mineCardDetailEntity);

    //查询登记注册汽车基本信息
    public List<CarInfoEntity> qryRegisteredCarList(CarInfoEntity carInfoEntity);

    //查询登记注册汽车基本信息记录数
    public Integer qryRegisteredCarCnt(CarInfoEntity carInfoEntity);




    public List<TransmstEntity> qryTransRegisteredList(TransmstEntity transmstEntity);


    public Integer qryTransRegisteredCnt(TransmstEntity transmstEntity);



    //查询登记注册汽车基本信息
    public List<CarInfoEntity> qryRegisterMineList(MineCardInfoEntity mineCardInfoEntity);

    //查询登记注册汽车基本信息记录数
    public Integer qryRegisterMineCnt(MineCardInfoEntity mineCardInfoEntity);


    //删除注册的汽车信息
    public void deleteCarRegInfo(String recId);

    //作废注册的汽车信息
    public void unableCarRegInfo(CarInfoEntity carInfoEntity);

    //删除注册的汽车信息
    public void deleteMineRegInfo(String cardRecId);

    //查询车辆详细信息
    public List<CarInfoEntity> qryCarDetail(String recId);

    //查询车辆详细信息
    public List<MineCardDetailEntity> qryMineCardDetail(String cardRecId);

    //修改汽车信息
    public void modifyCarInfo(CarInfoEntity carInfoEntity);

    //批量删除车节信息 edit by yangff 2016.3.22
    public void deleteMassTrainBody(RegisterEntity registerEntity);

    public void operateTrains(RegisterEntity registerEntity);

    //修改矿卡信息
    public void modifyMineCardInfo( MineCardDetailEntity mineCardDetailEntity);

    //查询当天注册发卡了多少汽车
    public Integer qryTodayRegisterCarCnt();

    //查询当天注册发卡了多少矿卡
    public Integer qryTodayRegisterMineCnt();

    //定位单个车辆
    public List<CarInfoEntity> focusCar(CarInfoEntity carInfoEntity);

    //查询汽车来煤信息
    public List<CarTransRecordEntity> qryCarTransRecordList(CarTransRecordEntity carTransRecordEntity);
    //查询汽车来煤信息记录数
    public Integer qryCarTransRecordListCnt(CarTransRecordEntity carTransRecordEntity);

    //查询汽车来煤信息4DWK
    public List<CarTransRecordEntity> qryCarTransRecordList4DWK(CarTransRecordEntity carTransRecordEntity);
    //查询汽车来煤信息记录数4DWK
    public Integer qryCarTransRecordListCnt4DWK(CarTransRecordEntity carTransRecordEntity);

    //新增汽车来煤信息
    public void addCarTransRecord(CarTransRecordEntity carTransRecordEntity);

    //新增汽车来煤信息
    public void addCarTransRecordNew(CarTransRecordEntity carTransRecordEntity);
    public void confirmUnload(CarTransRecordEntity carTransRecordEntity);

    //切换入厂车的通道
    public void changeChannel(CarTransRecordEntity carTransRecordEntity);

    //查询织金项目开元气动存查样柜信息
    public List<CabinetEntity> qryKYCabinetInfoList(CabinetEntity cabinetEntity);

    //查询织金项目开元气动存查样柜信息
    public Integer qryKYCabinetInfoCnt(CabinetEntity cabinetEntity);

    //查询织金项目开元气动存查样柜汇总信息
    public CabinetEntity qryKYCabinetSumInfo();

    //查询气动存查样柜信息
    public List<CabinetEntity> qryCabinetInfoList(CabinetEntity cabinetEntity);

    //查询气动存查样柜信息-取样、弃样（谏壁-支持分页）
    public List<CabinetEntity> qryCabinetInfoListPg(CabinetEntity cabinetEntity);

    //查询气动存查样柜信息，老谢新的简化后的
    public List<CabinetEntity> qryCabinetInfoListNew(CabinetEntity cabinetEntity);

    //查询单个车厢详情
    public List<RegisterEntity> qryOneTrainDetail(String recordNo);

    public List<RegisterEntity> qryOneTrainDetailfc(String recordNo);

    //修改单个车厢信息
    public void modifyOneTrainInfo(RegisterEntity registerEntity);

    //查询集样罐状的采样编码
    public List<CtrlEntity> qrySampleCodes4XY();
    //查询集样罐状的采样编码
    public List<CtrlEntity> qrySampleCodes();
    public List<CtrlEntity> qrySampleCodes4Train();
    public List<CtrlEntity> qrySampleCodes4Car();
    public List<CtrlEntity> qrySampleCodes4Ship();

    public List<TakeSampleRecEntity> qryTakeSampleRec(TakeSampleRecEntity takeSampleRecEntity);

    //新增工作模式
    public void addNewWorkMode(WorkModeEntity workModeEntity);

    //查询系统工作模式
    public List<WorkModeEntity> qryWorkModeInfo();
    public List<DeviceBroadEntity> qryEmergencyWarnMsg(DeviceBroadEntity entity);
    //查询火车人工采样编码信息
    public List<ManualSampleEntity> qryManSampleInfo(ManualSampleEntity manualSampleEntity);

    //查询煤场信息
    public List<CoalPileInfoEntity> qryMcInfo(CoalPileInfoEntity coalPileInfoEntity);

    //查询汽车人工采样编码信息
    public List<ManualSampleEntity> qryCarManSampleInfo(ManualSampleEntity manualSampleEntity);

    //查询入炉采样编码信息
    public List<ManualSampleEntity> qryLcManSampleInfo(ManualSampleEntity manualSampleEntity);

    //查询所有人工采样编码信息
    public List<ManualSampleEntity> qryManInfo(ManualSampleEntity manualSampleEntity);

    //查询气动存查样柜历史信息
    public List<CabinetOpRecEntity> qryCabinetOpRecList(CabinetOpRecEntity cabinetOpRecEntity);
    public List<CabinetOpRecEntity> qryCabinetOpRecListMain(CabinetOpRecEntity cabinetOpRecEntity);
    public List<CabinetOpRecEntity> qryCabinetOpRecList2(CabinetOpRecEntity cabinetOpRecEntity);
    public List<CabinetOpRecEntity> qryCabinetApplyRecList(CabinetOpRecEntity cabinetOpRecEntity);

    public Integer qryCabinetOpRecCnt(CabinetOpRecEntity cabinetOpRecEntity);
    public Integer qryCabinetOpRecCnt2(CabinetOpRecEntity cabinetOpRecEntity);
    public Integer qryCabinetOpRecCntMain(CabinetOpRecEntity cabinetOpRecEntity);
    public Integer qryCabinetApplyRecCnt(CabinetOpRecEntity cabinetOpRecEntity);

    //获取人工制样封装码
    public void getPackCode(ManualSampleEntity manualSampleEntity);
	
	//查询汽车违规信息
    public List<ViolaEntity> qryViolaData(ViolaEntity violaEntity);

    //查询车辆详细信息
    public List<ViolaEntity> viewViolaDetail(String violaRecId);
    //修改汽车信息
    public void editViolaDetail(ViolaEntity violaEntity);

    //查询远光存查样柜的存样信息
    public List<YGCabinetEntity> qryYGCabinetInfo(YGCabinetEntity yGCabinetEntity);

    //组装远光存查样柜操作请求的xml
    public void packYGCabinetOpInfo(YGCabinetEntity yGCabinetEntity);

    //查询车辆详细信息
    public List<CarInfoEntity> qryCarByCarIdOrCardId(CarInfoEntity carInfoEntity);

    //查询船运信息
    public List<ShipEntity> qryShipTransInfo(ShipEntity shipEntity);

    //查询船资料信息
    public List<ShipEntity> qryShipInfo(ShipEntity shipEntity);

    //查询船运的货运信息
    public List<ShipEntity> qryShipCargoInfo(ShipEntity shipEntity);

    //编辑轮船信息
    public void editShipTransRec(ShipEntity shipEntity);

    //编辑轮船信息
    public void submitEditShipInfo(ShipEntity shipEntity);

    //编辑船运煤信息
    public void editShipCargoRec(ShipEntity shipEntity);

    //卸煤结束
    public void finishUnloadRec(ShipEntity shipEntity);

    //查询船运分批详细信息
    public List<BatchNoInfoEntity> qryShipBatchInfo(BatchNoInfoEntity batchNoInfoEntity);

    //蚌埠-特殊的船运煤信息查询
    public List<ShipEntity> qryShipBatch(ShipEntity batchNoInfoEntity);

    //补录人工制样的封装码信息
    public void editSamplingResultRec(EditSamplingRptEntity editSamplingRptEntity);


    //系统外批次信息录入
    public void addVirtualBatchInfoRecord(SamplingRptEntity SamplingRptEntity);

    //入炉批次信息录入
    public void addFurnaceBatchInfoRecord(SamplingRptEntity SamplingRptEntity);

    //底样批次操作
    public void carAttachBatchForBat(RegisterEntity registerEntity);

    //删除整个车次(存储过程实现)
    public void deleteWholeTrain(RegisterEntity registerEntity);

    //结束或删除船运批次
    public void compOrDelShipBatchPro(ShipEntity shipEntity);

    //查询卸煤沟信息
    public List<XmTransSetEntity> qryXmTransList(XmTransSetEntity xmTransSetEntity);



    //修改汽车卸煤队列
    public  void editCarQueue(XmTransSetEntity xmTransSetEntity);


    //修改卸煤通道信息
    public  void editxmTransDetail(XmTransSetEntity xmTransSetEntity);

    public List<BatchNoInfoEntity> qryBeltBalanceInfo(BatchNoInfoEntity beltBalanceEntity);

    public void uploadToMis(UploadToMisEntity uploadToMisEntity);

    public void uploadToMisYg(UploadToMisEntity uploadToMisEntity);

    //编辑汽车违规信息（新增）
    public void addViolateRecord(ViolaEntity violaEntity);

    //蚌埠新增船煤批次
    public void addNewShipBatch(ShipEntity shipEntity);

    //蚌埠-操作船运煤批次状态
    public void operateBatchStatus(ShipEntity shipEntity);

    //补录人工采样信息
    public void editSampleResultRec(SampleRptEntity sampleRptEntity);

    //蚌埠校准船运卸煤吨位
    public void modifyAllNetQty(ShipEntity shipEntity);
	
    public List<ShipEntity> qryShipWeightInfo(ShipEntity shipEntity);

    public Integer qryAccessControlCnt(AccessControlEntity accessControlEntity);

    public List<AccessControlEntity> qryAccessControlInfoList(AccessControlEntity accessControlEntity);

    //提交控制设备命令
    public void commitCtrlCmdPro2(CtrlEntity ctrlEntity);

    //江南，对于0.2mm待取样的化验样，自动发送取样到化验室的指令到远光存查样柜, 先查询出带处理的纪录
    public String qryYG02mmReadyStateAutoToLab();

    public void packYg02mmAutoToLab(YGCabinetEntity yGCabinetEntity);
	
    //查询煤样追踪信息
    public List<SampleTraceEntity> qrySampleDetailList(SampleTraceEntity sampleTraceEntity);

    public BatchNoInfoEntity qrySingleBatchNoInfo(BatchNoInfoEntity batchNoInfoEntity);

    //常州修改入炉批次开始结束时间 并进行统计上煤量的刷新
    public void modifyBatchNoInfo(SamplingRptEntity samplingRptEntity);

    //入厂、入炉批次重量信息修改 for常州 20170207
    public void editBatchWeightInfo(SampleRptEntity sampleRptEntity);

    /*江南项目人工增加汽车煤批次***/
    public void manualCarBatchInfo(BatchNoInfoEntity batchNoInfoEntity);

    public Integer qryManualCarBatchCnt(BatchNoInfoEntity batchNoInfoEntity);

    /*江南汽车煤批次信息查询**/
    public List<BatchNoInfoEntity> qryManualCarBatchList(BatchNoInfoEntity batchNoInfoEntity);
	
	//编辑锁车信息（给织金使用）
    public void addLockCarRecord(CarLockInfoEntity carLockInfoEntity);

    //查询锁车信息（给织金使用）
    public List<CarLockInfoEntity> qryLockCarList(CarLockInfoEntity carLockInfoEntity);
	
	/*存查样柜提交样品延期清样*/
    public void submitCabinetDelay(CabinetEntity cabinetEntity);

    /*存查样柜提交样品延期信息查询*/
    public Integer qryCabinetDelayRecCnt(CabinetEntity cabinetEntity);

    /*存查样柜提交样品延期信息查询*/
    public List<CabinetEntity> qryCabinetDelayRec(CabinetEntity cabinetEntity);

    /*批量删除故障信息***********************/
    public void massSaveErrInfo(DeviceErrEntity deviceErrEntity);

    /*根据用户名称查询密码**/
    public String qryPwdByUserName(String userName);
	
	 //录入采样报告信息
    public void editSampleReport(SampleRptEntity sampleRptEntity);
    public void dealEmergencyWarn(DeviceBroadEntity deviceBroadEntity);

    //录入制样报告信息
    public void editSamplingReport(SamplingRptEntity samplingRptEntity);

    //查询船舶入厂工作模式（谏壁）
    public List<WorkModeEntity> qryShipSampleWorkMode();

    public void saveBeltWeightInfo(BeltWeightEntity beltWeightEntity);

    public Integer qryBeltWeightRecordCnt(BeltWeightEntity beltWeightEntity);

    public List<BeltWeightEntity> qryBeltWeightRec(BeltWeightEntity beltWeightEntity);

    //九江-船运皮带秤批次计量 信息计数查询
    //liuzh 2020-02-12
    public Integer qryShipBatchQtyInfoCnt(ShipBatchQtyEntity shipBatchQtyEntity);
    //九江-船运皮带秤批次计量 信息查询
    //liuzh 2020-02-12
    public List<ShipBatchQtyEntity> qryShipBatchQtyInfo(ShipBatchQtyEntity shipBatchQtyEntity);

	 //查询大开汽车来煤信息
    public List<CarTransRecordEntity> qryCarTransRecordList4DK(CarTransRecordEntity carTransRecordEntity);
    //查询大开汽车来煤信息记录数
    public Integer qryCarTransRecordListCnt4DK(CarTransRecordEntity carTransRecordEntity);

    public void doBatch(CarTransRecordEntity carTransRecordEntity);

    //聊城查询所有人工采样编码信息
    public List<ManualSampleEntity> qryManInfo4LC(ManualSampleEntity manualSampleEntity);

    public void addPrintNum(ManualSampleEntity manualSampleEntity);

    //编码打印记录
    public void addPrintRec(ManualSampleEntity manualSampleEntity);

    //聊城查询气动存查样柜信息
    public List<CabinetEntity> qryCabinetInfoList4LC(CabinetEntity cabinetEntity);

    public  void manualControlBeltWeight(ShipEntity shipEntity);

    //汽车车卡注册 用于存储过程 以便记录日志 xxs20180527
    public void addCarInfoNew(CarInfoEntity carInfoEntity);

    public void autoTakeSample4XW(ApproveEntity approveEntity);

    //查询汽车来煤信息4XW
    public List<CarTransRecordEntity> qryCarTransRecordList4XW(CarTransRecordEntity carTransRecordEntity);

    //管理员权限账号列表 查询
    public List<ApproveEntity> qrySysUserApprList(ApproveEntity approveEntity);

    //查询封装码的打印次数 宣威先要的
    public List<ManualSampleEntity> qryManInfoPackCode(ManualSampleEntity manualSampleEntity);

    //查询采制化编码的打印次数 宣威先要的
    public List<ManualSampleEntity> qryManInfo4XW(ManualSampleEntity manualSampleEntity);

    //查询汽车批次信息详情
    public List<CarTransRecordEntity> qryCarBasicList(CarTransRecordEntity carTransRecordEntity);

    //合并汽车批次s
    public void mergeCarBatchs(CarTransRecordEntity carTransRecordEntity);

    //查询火车重量修改记录  聊城
    public List<RegisterEntity> qryOneTrainHisList(RegisterEntity registerEntity);

    public void dealCarTransRecord4KcAppr(CarTransRecordEntity carTransRecordEntity);

    public List<BeltWeightEntity> qryBeltWeightRec4Qz(BeltWeightEntity beltWeightEntity);

    //查询船运的货运信息
    public List<ShipEntity> qryShipCargoInfo4LD(ShipEntity shipEntity);

    public Map<String,Object> manualSampleKlmy(Map<String,Object> params);
}
