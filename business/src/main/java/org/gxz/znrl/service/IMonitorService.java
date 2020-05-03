package org.gxz.znrl.service;

import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.entity.*;
import org.gxz.znrl.viewModel.GridModel;

import java.util.List;
import java.util.Map;

/**
 * Created by xieyt on 14-11-18.
 */
public interface IMonitorService {

    //查询称重
    public GridModel qryWeightList(WeightEntity weightEntity);

    //查询采样
    public GridModel qrySampleList(WeightRptEntity sampleEntity);

    //提交控制设备命令
    public void commitCtrlCmd(CtrlEntity ctrlEntity);

    //设置当前卸煤船
    public void setCurrentShip(ShipEntity shipEntity);

    //设置当前卸煤船
    public void setMultCurrentShip(ShipEntity shipEntity);

    //今日来煤信息汇总查询
    public GridModel qryTodayArrivedCoal();

    //查询今日汽车动态信息
    public GridModel qryCarDynamic();

    //查询制样结果动态信息
    public GridModel qrySampling();

    //查询操作日志功能
    public GridModel qryOpLogInfo(LogOpRecEntity logOpRecEntity);

    public GridModel qryOpLogInfoXW(LogOpRecEntity logOpRecEntity);

    //查询设备动态信息
    public GridModel qryDeviceBroad(DeviceBroadEntity deviceBroadEntity);

    //查询设备动态信息
    public GridModel qryDeviceBroadView(DeviceBroadEntity deviceBroadEntity);

    //查询故障报警信息
    public GridModel qryDeviceErr();

    //查询故障报警信息
    public GridModel qryDeviceErrView(DeviceErrEntity deviceErrEntity);

    //查询故障报警信息
    public List qryDeviceErrList();

    //查询故障报警信息详情
    public DeviceBroadEntity qryDeviceErrDetail(String recId);

    //故障报警处理
    public void deviceErrDeal(DeviceBroadEntity deviceBroadEntity);
    //强制结束批次
    public void forceUpdateBatch(ShipEntity shipEntity);
    //存查样柜的柜子状态查询
    public List<SampleBoxEntity> qrySampleBoxesInfo();

    //查询柜子里的样包信息
    public List<SampleBoxEntity> qrySampleBagInfo(String boxNo);

    //查询某柜子里的样包信息,用于审批查询使用
    public GridModel qrySampleBag4Get(SampleBoxEntity sampleBoxEntity);

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

    //调整编辑顺序
    public void adjustTrainOrder(RegisterEntity registerEntity);

    //火车来煤分批
    public void dealTrainBatchInfo(RegisterEntity registerEntity);

    //煤样追踪查询-煤样瓶接收确认
    public void confirmSampleRecv(SampleTraceEntity sampleTraceEntity);

    //删除单个车厢
    public void deleteOneTrain(RegisterEntity registerEntity);

    //查询火车入厂登记信息详情
    public GridModel qryTrainDetailList(RegisterEntity registerEntity);
    //查询火车入厂登记信息详情
    public GridModel qryTrainDetailListHf(RegisterEntity registerEntity);
    //查询火车入厂登记信息合计
    public GridModel qryTrainDetailListSummary(RegisterEntity registerEntity);

    //查询火车入厂登记信息详情
    public GridModel qryTrainDetailListDk(RegisterEntity registerEntity);

    //查询火车入厂登记信息详情 for fc
    public GridModel qryTrainDetailListfc(RegisterEntity registerEntity);

    //查询火车入厂登记信息详情
    public GridModel qryTrainBasicList(RegisterEntity registerEntity);

    //根据车次号删除火车记录
    public void deleteTrainInfo(String trainNo);

    //新增汽车信息
    public void addCarInfo(CarInfoEntity carInfoEntity);
    //新增汽车信息
    public void updateCarInfo(CarInfoEntity carInfoEntity);

    //新增矿卡信息
    public void addMineCardInfo(MineCardDetailEntity mineCardDetailEntity);

    //查询登记注册汽车基本信息
    public GridModel qryRegisteredCarList(CarInfoEntity carInfoEntity);



    public GridModel qryTransRegisteredList(TransmstEntity transmstEntity);

    //查询登记注册汽车基本信息
    public GridModel qryRegisterMineList(MineCardInfoEntity mineCardInfoEntity);

    //删除注册的汽车信息
    public void deleteCarRegInfo(String recId);

    //作废注册的汽车信息
    public void unableCarRegInfo(CarInfoEntity carInfoEntity);

    //删除注册的汽车信息
    public void deleteMineRegInfo(String cardRecId);

    //查询车辆详细信息
    public CarInfoEntity qryCarDetail(String recId);

    //查询车辆详细信息
    public MineCardDetailEntity qryMineCardDetail(String cardRecId);

    //修改汽车信息
    public void modifyCarInfo(CarInfoEntity carInfoEntity);


    //批量删除车节信息
    public void deleteMassTrainBody(RegisterEntity registerEntity);

    //批量删除车节信息
    public void operateTrains(RegisterEntity registerEntity);


    //修改矿卡信息
    public void modifyMineCardInfo( MineCardDetailEntity mineCardDetailEntity);

    //查询当天注册发卡了多少汽车
    public Integer qryTodayRegisterCarCnt();

    //查询当天注册发卡了多少矿卡
    public Integer qryTodayRegisterMineCnt();

    //定位汽车信息
    public CarInfoEntity focusCar(CarInfoEntity carInfoEntity);

    //新增汽车来煤信息
    public void addCarTransRecord(CarTransRecordEntity carTransRecordEntity);

    //新增汽车来煤信息
    public void addCarTransRecordNew(CarTransRecordEntity carTransRecordEntity);

    public void confirmUnload(CarTransRecordEntity carTransRecordEntity);

    ////切换入厂车的通道
    public void changeChannel(CarTransRecordEntity carTransRecordEntity);

    //查询汽车来煤信息
    public GridModel qryCarTransRecordList(CarTransRecordEntity carTransRecordEntity);

    //查询汽车来煤信息4DWK
    public GridModel qryCarTransRecordList4DWK(CarTransRecordEntity carTransRecordEntity);

    //查询织金项目开元气动存查样柜信息
    public GridModel qryKYCabinetInfoList(CabinetEntity cabinetEntity);

    //查询织金项目开元气动存查样柜汇总信息
    public CabinetEntity qryKYCabinetSumInfo();

    //查询气动存查样柜信息
    public List<CabinetEntity> qryCabinetInfoList(CabinetEntity cabinetEntity);

    //查询气动存查样柜信息,老谢新的简化后的
    public List<CabinetEntity> qryCabinetInfoListNew(CabinetEntity cabinetEntity);

    //查询气动存查样柜历史信息
    public GridModel qryCabinetOpRecList(CabinetOpRecEntity cabinetOpRecEntity);
    public GridModel qryCabinetOpRecListMain(CabinetOpRecEntity cabinetOpRecEntity);
    public GridModel qryCabinetOpRecList2(CabinetOpRecEntity cabinetOpRecEntity);

    //查询气动存查样柜取样取样申请记录
    public GridModel qryCabinetApplyRecList(CabinetOpRecEntity cabinetOpRecEntity);

    //查询气动存查样柜信息-取样，弃样
    public GridModel qryCabinetSampleList(CabinetEntity cabinetEntity);

    //查询气动存查样柜信息-取样、弃样（谏壁-支持分页）
    public GridModel qryCabinetSampleListPage(CabinetEntity cabinetEntity);

    //查询气动存查样柜信息-取样，弃样
    public GridModel qryCabinetSampleListNew(CabinetEntity cabinetEntity);

    //查询单个车厢详情
    public RegisterEntity qryOneTrainDetail(String recordNo);

    //查询单个车厢详情 for fc
    public RegisterEntity qryOneTrainDetailfc(String recordNo);

    //修改单个车厢信息
    public void modifyOneTrainInfo(RegisterEntity registerEntity);

    //查询集样罐状的采样编码
    public GridModel qrySampleCodes(String machineType, String command);

    //查询集样罐状的采样编码
    public GridModel qrySampleCodesByTransType(String machineType, String command, String transType);

    public List<TakeSampleRecEntity> qryTakeSampleRec(TakeSampleRecEntity takeSampleRecEntity);

    //使用新工作模式
    public void addNewWorkMode(WorkModeEntity workModeEntity);

    //查询系统工作模式
    public WorkModeEntity qryWorkModeInfo();
    public DeviceBroadEntity qryEmergencyWarnMsg(DeviceBroadEntity entity);
    //查询船舶入厂工作模式（谏壁）
    public WorkModeEntity qryShipSampleWorkMode();

    //查询人工采样编码信息
    public GridModel qryManSampleInfo(ManualSampleEntity manualSampleEntity);

    //查询煤场信息
    public GridModel qryMcInfo(CoalPileInfoEntity coalPileInfoEntity);

    //查询人工采样编码信息
    public GridModel qryManInfo(ManualSampleEntity manualSampleEntity);

    //查询人工采样编码信息
    public GridModel qryCarManSampleInfo(ManualSampleEntity manualSampleEntity);

    //查询入炉采样编码信息
    public GridModel qryLcManSampleInfo(ManualSampleEntity manualSampleEntity);
    //获取人工制样封装码
    public void getPackCode(ManualSampleEntity manualSampleEntity);

    //调用操作记录发送接口报文
    public String callYGWSIntf(String opRecId);

    //调用接口查询远光柜子的存储汇总信息
    public YGCabinetEntity qryYGCabinetSumInfo();

    //调用接口查询远光柜子的存储汇总信息
    public YGCabinetEntity qryYGCabinetSumInfoExpiredCnt(String typeId,String comeTag);

    //查询远光存查样柜的存样信息
    public GridModel qryYGCabinetInfo(YGCabinetEntity yGCabinetEntity);

    //查询汽车违规信息
    public GridModel qryViolaData(ViolaEntity violaEntity);

    //查询汽车违规详细信息
    public  ViolaEntity  viewViolaDetail(String violaRecId);

    //修改汽车违规信息
    public void editViolaDetail( ViolaEntity violaEntity);

    public List<CarInfoEntity> qryCarByCarIdOrCardId(CarInfoEntity carInfoEntity);

    //查询船运信息
    public GridModel qryShipTransInfo(ShipEntity shipEntity);

    //蚌埠-查询船运煤批次信息
    public GridModel qryShipBatch(ShipEntity shipEntity);

    //查询船资料信息
    public GridModel qryShipInfo(ShipEntity shipEntity);

    //查询船运的货运信息
    public GridModel qryShipCargoInfo(ShipEntity shipEntity);

    //编辑轮船信息
    public void editShipTransRec(ShipEntity shipEntity);

    //编辑轮船信息
    public void submitEditShipInfo(ShipEntity shipEntity);

    //编辑船运煤信息
    public void editShipCargoRec(ShipEntity shipEntity);
    //结束卸煤
    public void finishUnloadRec(ShipEntity shipEntity);
    //查询船运分批详细信息
    public GridModel qryShipBatchInfo(BatchNoInfoEntity batchNoInfoEntity);

    //补录人工制样的封装码信息
    public void editSamplingResultRec(EditSamplingRptEntity editsamplingRptEntity);

    //新增系统外批次
    public void addVirtualBatchInfoRecord(SamplingRptEntity samplingRptEntity);

    //新增系统外批次
    public void addFurnaceBatchInfoRecord(SamplingRptEntity samplingRptEntity);

    //底样批次操作
    public void carAttachBatchForBat(RegisterEntity registerEntity);

    //删除整个车次(用存储过程)
    public void deleteWholeTrain(RegisterEntity registerEntity);

    //结束或删除船运批次
    public void compOrDelShipBatchPro(ShipEntity shipEntity);

    //查询卸煤沟当前信息
    public  GridModel qryXmTransList(XmTransSetEntity xmTransSetEntity);



    //修改汽车队列
    public  void  editCarQueue(XmTransSetEntity xmTransSetEntity);


    //修改卸煤通道信息
    public void editxmTransDetail(XmTransSetEntity xmTransSetEntity);

    //皮带秤计量信息查询
    public GridModel qryBeltBalanceInfo(BatchNoInfoEntity beltBalanceEntity);

    //确认上传燃料MIS（衡丰）
    public void uploadToMis(UploadToMisEntity uploadToMisEntity);

    //确认上传燃料MIS（哈平南 远光MIS）
    public void uploadToMisYg(UploadToMisEntity uploadToMisEntity);

    //编辑汽车违规信息（新增）
    public void addViolateRecord(ViolaEntity violaEntity);

    //蚌埠新增船煤批次
    public void addNewShipBatch(ShipEntity shipEntity);

    //蚌埠操作状态
    public void operateBatchStatus(ShipEntity shipEntity);
    //补录人工采样信息
    public void editSampleResultRec(SampleRptEntity sampleRptEntity);

    //蚌埠校准船运卸煤吨位
    public void modifyAllNetQty(ShipEntity shipEntity);

    //wangz ,20160727 参考接口文档: 3.6.10	样品全量盘点下达(31200),远光 ChaXun31200
    //调用此方法,需要注意异常  throw RuntimeException
    public String callYGIntfForCode31200();
	
	public GridModel qryShipWeightListInfo(ShipEntity shipEntity);

    public GridModel qryAccessControlInfoList(AccessControlEntity accessControlEntity);

    //查询煤样追踪信息记录
    public GridModel  qrySampleDetailList(SampleTraceEntity sampleTraceEntity);

    public BatchNoInfoEntity qrySampleBatchNoInfo(BatchNoInfoEntity batchNoInfoEntity);
	
    //提交控制设备命令
    public void commitCtrlCmd2(CtrlEntity ctrlEntity);

    //江南，对于0.2mm待取样的化验样，自动发送取样到化验室的指令到远光存查样柜
    public void saveYg02mmAutoToLab();

    public  void modifyBatchNoInfo(SamplingRptEntity samplingRptEntity);

    //入厂入炉批次重量信息修改 for常州 20170207
    public void editBatchWeightInfo(SampleRptEntity sampleRptEntity);

    /*江南项目人工增加汽车煤批次***/
    public void manualCarBatchInfo(BatchNoInfoEntity batchNoInfoEntity);

    /*江南汽车煤批次信息查询**/
    public GridModel qryManualCarBatchList(BatchNoInfoEntity batchNoInfoEntity);
	
	//编辑锁车信息（给织金使用）
    public void addLockCarRecord(CarLockInfoEntity carLockInfoEntity);

    //查询锁车信息（给织金使用）
    public GridModel qryLockCarList( CarLockInfoEntity carLockInfoEntity);
	
	/*存查样柜提交样品延期清样*/
    public void submitCabinetDelay(CabinetEntity cabinetEntity);

    public GridModel qryCabinetDelayRecList(CabinetEntity cabinetEntity);

    /*批量删除故障信息**/
    public void massSaveErrInfo(DeviceErrEntity deviceErrEntity);

    /*根据用户名称查询密码**/
    public String qryPwdByUserName(String userName);
	
	//录入采样报告信息
    public void editSampleReport(SampleRptEntity sampleRptEntity);

    public void dealEmergencyWarn(DeviceBroadEntity deviceBroadEntity);

    //录入制样报告信息
    public void editSamplingReport(SamplingRptEntity samplingRptEntity);

    //九江-记录皮带秤数据
    public void saveBeltWeightInfo(BeltWeightEntity beltWeightEntity);

    //九江-查询皮带秤数据
    public GridModel qryBeltWeightInfo(BeltWeightEntity beltWeightEntity);

    //九江-船运皮带秤批次计量 信息查询
    //liuzh 2020-02-12
    public GridModel qryShipBatchQtyInfo(ShipBatchQtyEntity shipBatchQtyEntity);

    //泉州-查询皮带秤数据
    public GridModel qryBeltWeightRec4Qz(BeltWeightEntity beltWeightEntity);

    //查询大开汽车来煤信息
    public GridModel qryCarTransRecordList4DK(CarTransRecordEntity carTransRecordEntity);

    //打开执行分批
    public void doBatch(CarTransRecordEntity carTransRecordEntity);

    //聊城查询人工采样编码信息
    public GridModel qryManInfo4LC(ManualSampleEntity manualSampleEntity);

    //化验编码打印计数
    public void addPrintNum(ManualSampleEntity manualSampleEntity);

    //编码打印记录
    public void addPrintRec(ManualSampleEntity manualSampleEntity);

    //聊城查询气动存查样柜信息-取样，弃样
    public GridModel qryCabinetSampleList4LC(CabinetEntity cabinetEntity);

    public void manualControlBeltWeight(ShipEntity shipEntity);

    //汽车车卡注册 用于存储过程 以便记录日志 xxs20180527
    public void addCarInfoNew(CarInfoEntity carInfoEntity);

    //宣威自动随机取样功能
    public void autoTakeSample4XW(ApproveEntity approveEntity);

    //查询汽车来煤信息4XW
    public GridModel qryCarTransRecordList4XW(CarTransRecordEntity carTransRecordEntity);

    //管理员权限账号列表 查询
    public  GridModel qrySysUserApprList(ApproveEntity approveEntity);

    //查询封装码的打印次数 宣威先要的
    public GridModel qryManInfoPackCode(ManualSampleEntity manualSampleEntity);

    //查询采制化编码的打印次数 宣威先要的
    public GridModel qryManInfo4XW(ManualSampleEntity manualSampleEntity);

    //查询汽车批次信息详情
    public GridModel qryCarBasicList(CarTransRecordEntity carTransRecordEntity);

    //合并汽车批次  宣威
    public void mergeCarBatchs(CarTransRecordEntity carTransRecordEntity);

    //查询火车重量修改记录  聊城
    public GridModel qryOneTrainHisList(RegisterEntity registerEntity);

    public void dealCarTransRecord4KcAppr(CarTransRecordEntity carTransRecordEntity);

    //查询船运的货运信息
    public GridModel qryShipCargoInfo4LD(ShipEntity shipEntity);

    public void manualSampleKlmy(Map<String,Object> params);
}
