package org.gxz.znrl.service.impl;

import org.apache.batik.css.engine.value.StringValue;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.gxz.znrl.common.util.Result;
import org.gxz.znrl.entity.*;
import org.gxz.znrl.mapper.MonitorMapper;
import org.gxz.znrl.service.IMonitorService;
import org.gxz.znrl.shiro.ShiroUser;
import org.gxz.znrl.util.Constant;
import org.gxz.znrl.util.LogUtil;
import org.gxz.znrl.util.WSCall;
import org.gxz.znrl.viewModel.GridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by xieyt on 14-11-18.
 */
@Service("monitorService")
@Transactional
@SuppressWarnings("unchecked")
public class MonitorServiceImpl implements IMonitorService {
    private static final Logger LOG = LoggerFactory.getLogger(MonitorServiceImpl.class);
    @Autowired
    public MonitorMapper monitorMapper;

    //称重监控明细查询
    public GridModel qryWeightList(WeightEntity weightEntity) {
        //查询总记录数
        Integer totalCnt = monitorMapper.qryWeightListCnt(weightEntity);

        //查询本次页面的结果集
        List<WeightEntity> weightList = monitorMapper.qryWeightList(weightEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(weightList);
        m.setTotal(totalCnt);

        return m;
    }

    //采样监控明细查询
    public GridModel qrySampleList(WeightRptEntity sampleEntity) {
        //查询总记录数
        Integer totalCnt = monitorMapper.qrySampleListCnt(sampleEntity);

        //查询本次页面的结果集
        List<WeightRptEntity> sampleList = monitorMapper.qrySampleList(sampleEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(sampleList);
        m.setTotal(totalCnt);

        return m;
    }

    //提交设备控制命令
    public void commitCtrlCmd(CtrlEntity ctrlEntity){
        monitorMapper.commitCtrlCmdPro(ctrlEntity);
    }


    //设置当前卸煤船
    public void setCurrentShip(ShipEntity shipEntity){
        monitorMapper.setCurrentShip(shipEntity);
    }


    //设置卸煤船
    public void setMultCurrentShip(ShipEntity shipEntity)  {
            shipEntity.setOldStatus(shipEntity.getStatus());//前台传入的是5
            shipEntity.setStatus("6");//将所有船都设置成为6，再根据前台传入的值设置具体船
            monitorMapper.setShipRecoveryStatus(shipEntity);
            shipEntity.setStatus(shipEntity.getOldStatus());//取回原来的值
            for (int i=0;i<shipEntity.getMultShipId().split(",").length;i++) {
                shipEntity.setShipRecID(shipEntity.getMultShipId().split(",")[i]);
                monitorMapper.setShipStatus(shipEntity);
            }
    }

    //查询今日来煤情况
    public GridModel qryTodayArrivedCoal() {
        //查询本次页面的结果集
        List<WeightRptEntity> list = monitorMapper.qryTodayArrivedCoal();

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    //查询今日汽车动态信息
    public GridModel qryCarDynamic() {
        //查询本次页面的结果集
        List<WeightRptEntity> list = monitorMapper.qryCarDynamic();

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }


    //查询制样结果动态信息
    public GridModel qrySampling(){
        //查询本次页面的结果集
        List<MakeSampleEntity> list = monitorMapper.qrySampling();

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }


    //查询设备动态信息
    public GridModel qryDeviceBroad(DeviceBroadEntity deviceBroadEntity){
        //查询本次页面的结果集
        List<DeviceBroadEntity> list = monitorMapper.qryDeviceBroad(deviceBroadEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    //查询设备动态信息
    public GridModel qryOpLogInfo(LogOpRecEntity logOpRecEntity){
        logOpRecEntity.setSysLogType("0");
        Integer totalCnt = monitorMapper.qryOpLogInfoCnt(logOpRecEntity);
        //查询本次页面的结果集
        List<LogOpRecEntity> list = monitorMapper.qryOpLogInfo(logOpRecEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);

        return m;
    }

    public GridModel qryOpLogInfoXW(LogOpRecEntity logOpRecEntity){
        logOpRecEntity.setSysLogType("1");
        Integer totalCnt = monitorMapper.qryOpLogInfoCnt(logOpRecEntity);
        //查询本次页面的结果集
        List<LogOpRecEntity> list = monitorMapper.qryOpLogInfo(logOpRecEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);
        return m;
    }

    //查询设备动态信息
    public GridModel qryDeviceBroadView(DeviceBroadEntity deviceBroadEntity){
        Integer totalCnt = monitorMapper.qryDeviceBroadViewCnt(deviceBroadEntity);
        //查询本次页面的结果集
        List<DeviceBroadEntity> list = monitorMapper.qryDeviceBroadView(deviceBroadEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);

        return m;
    }


    //查询故障报警信息
    public GridModel qryDeviceErr(){
        //查询本次页面的结果集
        List<DeviceBroadEntity> list = monitorMapper.qryDeviceErr();

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    //查询故障报警信息
    public GridModel qryDeviceErrView(DeviceErrEntity deviceErrEntity){
        //查询总记录数
        Integer totalCnt = monitorMapper.qryDeviceErrViewCnt(deviceErrEntity);
        //查询本次页面的结果集
        List<DeviceErrEntity> list = monitorMapper.qryDeviceErrView(deviceErrEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);

        return m;
    }

    //查询故障报警信息
    public List qryDeviceErrList(){
        //查询本次页面的结果集
        List<DeviceBroadEntity> list = monitorMapper.qryDeviceErr();

        return list;
    }

    //查询故障报警信息详情
    public DeviceBroadEntity qryDeviceErrDetail(String recId){
        List<DeviceBroadEntity> list = monitorMapper.qryDeviceErrDetail(recId);

        //设置返回结果
        DeviceBroadEntity d = list.get(0);

        return d;
    }

    //故障报警处理
    public void deviceErrDeal(DeviceBroadEntity deviceBroadEntity){
        monitorMapper.deviceErrDeal(deviceBroadEntity);
    }

    //强制结束批次
    public void forceUpdateBatch(ShipEntity shipEntity){
        monitorMapper.forceUpdateBatch(shipEntity);
    }

    //存查样柜的柜子状态查询
    public List<SampleBoxEntity> qrySampleBoxesInfo(){
        return monitorMapper.qrySampleBoxesInfo();
    }

    //查询柜子里的样包信息
    public List<SampleBoxEntity> qrySampleBagInfo(String boxNo) {
        //查询本次页面的结果集
        List<SampleBoxEntity> list = monitorMapper.qrySampleBagInfo(boxNo);
        return list;
    }

    //查询某柜子里的样包信息,用于审批查询使用
    public GridModel qrySampleBag4Get(SampleBoxEntity sampleBoxEntity){
        //查询本次页面的结果集
        List<SampleBoxEntity> list = monitorMapper.qrySampleBag4Get(sampleBoxEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    //火车注册
    public void trainRegister(RegisterEntity registerEntity){
        monitorMapper.trainRegister(registerEntity);
    }

    //火车大票批量录入
    public void batchTrainDPRecord(RegisterEntity registerEntity){
        monitorMapper.batchTrainDPRecord(registerEntity);
    }

    //火车大票批量录入并同时分批
    public void batchTrainDPRecordAndBatch(RegisterEntity registerEntity){
        monitorMapper.batchTrainDPRecordAndBatch(registerEntity);
    }

    //新增火车，可以在原来车次上新增，也可以新增测试
    public void addNewTrain(RegisterEntity registerEntity){
        monitorMapper.addNewTrain(registerEntity);
    }

    //合并火车车次
    public void mergeTrain(RegisterEntity registerEntity){
        monitorMapper.mergeTrain(registerEntity);
    }

    @Override
    public void mergeCarBatchNo(RegisterEntity registerEntity) {
        monitorMapper.mergeCarBatchNo(registerEntity);
    }

    //拆分车次
    public void splitTrain(RegisterEntity registerEntity){
        monitorMapper.splitTrain(registerEntity);
    }

    //调整编辑顺序
    public void adjustTrainOrder(RegisterEntity registerEntity){
        monitorMapper.adjustTrainOrder(registerEntity);
    }

    //火车来煤分批
    public void dealTrainBatchInfo(RegisterEntity registerEntity){
        monitorMapper.dealTrainBatchInfo(registerEntity);
    }

    //删除单个车厢
    public void deleteOneTrain(RegisterEntity registerEntity){
        monitorMapper.deleteOneTrain(registerEntity);
    }

    //查询火车入厂登记信息详情
    public GridModel qryTrainDetailList(RegisterEntity registerEntity){
        //查询本次页面的结果集
        List<RegisterEntity> list =  monitorMapper.qryTrainDetailList(registerEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    //查询火车入厂登记信息详情
    public GridModel qryTrainDetailListHf(RegisterEntity registerEntity){
        //查询本次页面的结果集
        List<RegisterEntity> list =  monitorMapper.qryTrainDetailListHf(registerEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    //查询火车入厂登记信息合计
    public GridModel qryTrainDetailListSummary(RegisterEntity registerEntity){
        //查询本次页面的结果集
        List<RegisterEntity> list =  monitorMapper.qryTrainDetailListSummary(registerEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    //查询火车入厂登记信息详情
    public GridModel qryTrainDetailListDk(RegisterEntity registerEntity){
        //查询本次页面的结果集
        List<RegisterEntity> list =  monitorMapper.qryTrainDetailListDk(registerEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    //查询火车入厂登记信息详情丰城项目查询
    public GridModel qryTrainDetailListfc(RegisterEntity registerEntity){
        //查询本次页面的结果集
        List<RegisterEntity> list =  monitorMapper.qryTrainDetailListfc(registerEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }


    //查询火车入厂登记信息详情
    public GridModel qryTrainBasicList(RegisterEntity registerEntity){
        //查询本次页面的结果集
        List<RegisterEntity> list = monitorMapper.qryTrainBasicList(registerEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }


    //根据车次号删除火车记录
    public void deleteTrainInfo(String trainNo){
        monitorMapper.deleteTrainInfo(trainNo);
    }


    //新增汽车信息
    public void addCarInfo(CarInfoEntity carInfoEntity){
        monitorMapper.addCarInfo(carInfoEntity);
    }

    //新增汽车信息
    public void updateCarInfo(CarInfoEntity carInfoEntity){
        monitorMapper.updateCarInfo(carInfoEntity);
    }

    public void addMineCardInfo(MineCardDetailEntity mineCardDetailEntity){
        monitorMapper.addMineCardInfo(mineCardDetailEntity);
    }

    //查询登记注册汽车基本信息
    public GridModel qryRegisteredCarList(CarInfoEntity carInfoEntity) {
        //查询总记录数
        Integer totalCnt = monitorMapper.qryRegisteredCarCnt(carInfoEntity);

        //查询本次页面的结果集
        List<CarInfoEntity> carInfoList = monitorMapper.qryRegisteredCarList(carInfoEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(carInfoList);
        m.setTotal(totalCnt);

        return m;
    }



    //查询登记注册汽车基本信息
    public GridModel qryTransRegisteredList(TransmstEntity transmstEntity) {
        //查询总记录数
        Integer totalCnt = monitorMapper.qryTransRegisteredCnt(transmstEntity);

        //查询本次页面的结果集
        List<TransmstEntity> carInfoList = monitorMapper.qryTransRegisteredList(transmstEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(carInfoList);
        m.setTotal(totalCnt);

        return m;
    }



    public void deleteMassTrainBody(RegisterEntity registerEntity){
        monitorMapper.deleteMassTrainBody(registerEntity);
    }


    public void operateTrains(RegisterEntity registerEntity){
        monitorMapper.operateTrains(registerEntity);
    }


    //查询登记注册汽车基本信息
    public GridModel qryRegisterMineList(MineCardInfoEntity mineCardInfoEntity) {
        //查询总记录数
        Integer totalCnt = monitorMapper.qryRegisterMineCnt(mineCardInfoEntity);

        //查询本次页面的结果集
        List<CarInfoEntity> carInfoList = monitorMapper.qryRegisterMineList(mineCardInfoEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(carInfoList);
        m.setTotal(totalCnt);

        return m;
    }

    //删除注册的汽车信息
    public void deleteCarRegInfo(String recId){
        monitorMapper.deleteCarRegInfo(recId);
    }

    //作废注册的汽车信息 2017.3.15
    public void unableCarRegInfo(CarInfoEntity carInfoEntity){
        monitorMapper.unableCarRegInfo(carInfoEntity);
    }

    //删除注册的汽车信息
    public void deleteMineRegInfo(String cardRecId){
        monitorMapper.deleteMineRegInfo(cardRecId);
    }

    //查询车辆详细信息
    public CarInfoEntity qryCarDetail(String recId){
        List<CarInfoEntity> list = monitorMapper.qryCarDetail(recId);

        //设置返回结果
        CarInfoEntity d = list.get(0);

        return d;
    }

    //查询车辆详细信息
    public MineCardDetailEntity qryMineCardDetail(String cardRecId){
        List<MineCardDetailEntity> list = monitorMapper.qryMineCardDetail(cardRecId);

        //设置返回结果
        MineCardDetailEntity d = list.get(0);

        return d;
    }


    //修改汽车信息
    public void modifyCarInfo(CarInfoEntity carInfoEntity){
        monitorMapper.modifyCarInfo(carInfoEntity);
    }

    //修改汽车信息
    public void modifyMineCardInfo( MineCardDetailEntity mineCardDetailEntity){
        monitorMapper.modifyMineCardInfo(mineCardDetailEntity);
    }

    //查询当天注册发卡了多少汽车
    public Integer qryTodayRegisterCarCnt(){
        return monitorMapper.qryTodayRegisterCarCnt();
    }

    //查询当天注册发卡了多少矿卡
    public Integer qryTodayRegisterMineCnt(){
        return monitorMapper.qryTodayRegisterMineCnt();
    }

    //定位汽车信息
    public CarInfoEntity focusCar(CarInfoEntity carInfoEntity){
        CarInfoEntity c = null;
        List<CarInfoEntity> carInfoList = monitorMapper.focusCar(carInfoEntity);
        if (carInfoList != null && carInfoList.size()>0) {
            c = carInfoList.get(0);
        }
        return c;
    }

    //查询汽车来煤信息
    public GridModel qryCarTransRecordList(CarTransRecordEntity carTransRecordEntity){
        //查询总记录数
        Integer totalCnt = monitorMapper.qryCarTransRecordListCnt(carTransRecordEntity);

        //查询本次页面的结果集
        List<CarTransRecordEntity> list = monitorMapper.qryCarTransRecordList(carTransRecordEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);

        return m;
    }

    //查询汽车来煤信息4DWK
    public GridModel qryCarTransRecordList4DWK(CarTransRecordEntity carTransRecordEntity){
        //查询总记录数
        Integer totalCnt = monitorMapper.qryCarTransRecordListCnt4DWK(carTransRecordEntity);

        //查询本次页面的结果集
        List<CarTransRecordEntity> list = monitorMapper.qryCarTransRecordList4DWK(carTransRecordEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);

        return m;
    }


    //新增汽车来煤信息
    public void addCarTransRecord(CarTransRecordEntity carTransRecordEntity){
        monitorMapper.addCarTransRecord(carTransRecordEntity);
    }

    //新增汽车来煤信息
    public void addCarTransRecordNew(CarTransRecordEntity carTransRecordEntity){
        monitorMapper.addCarTransRecordNew(carTransRecordEntity);
    }

    public void confirmUnload(CarTransRecordEntity carTransRecordEntity){
        monitorMapper.confirmUnload(carTransRecordEntity);
    }


    //煤样追踪查询-煤样瓶接收确认
    public void confirmSampleRecv(SampleTraceEntity sampleTraceEntity){
        monitorMapper.confirmSampleRecv(sampleTraceEntity);
    }

    //切换入厂车的通道
    public void changeChannel(CarTransRecordEntity carTransRecordEntity){
        monitorMapper.changeChannel(carTransRecordEntity);
    }

    //查询织金项目开元气动存查样柜信息
    public GridModel qryKYCabinetInfoList(CabinetEntity cabinetEntity){

        Integer totalCnt = monitorMapper.qryKYCabinetInfoCnt(cabinetEntity);
        List<CabinetEntity> list = monitorMapper.qryKYCabinetInfoList(cabinetEntity);

        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);
        return m ;

    }

    //查询织金项目开元气动存查样柜汇总信息
    public CabinetEntity qryKYCabinetSumInfo(){

        CabinetEntity qryData = monitorMapper.qryKYCabinetSumInfo();

        return qryData ;

    }

    //查询气动存查样柜信息
    public List<CabinetEntity> qryCabinetInfoList(CabinetEntity cabinetEntity){
        List<CabinetEntity> list = monitorMapper.qryCabinetInfoList(cabinetEntity);
        return list;
    }

    //查询气动存查样柜信息，老谢新的简化后的
    public List<CabinetEntity> qryCabinetInfoListNew(CabinetEntity cabinetEntity){
        List<CabinetEntity> list = monitorMapper.qryCabinetInfoListNew(cabinetEntity);
        return list;
    }

    //查询气动存查样柜信息历史
    public GridModel qryCabinetOpRecList(CabinetOpRecEntity cabinetOpRecEntity){

        Integer totalCnt = monitorMapper.qryCabinetOpRecCnt(cabinetOpRecEntity);
        List<CabinetOpRecEntity> list = monitorMapper.qryCabinetOpRecList(cabinetOpRecEntity);

        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);
        return m ;
    }
    public GridModel qryCabinetOpRecListMain(CabinetOpRecEntity cabinetOpRecEntity){

        Integer totalCnt = monitorMapper.qryCabinetOpRecCntMain(cabinetOpRecEntity);
        List<CabinetOpRecEntity> list = monitorMapper.qryCabinetOpRecListMain(cabinetOpRecEntity);

        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);
        return m ;
    }
    public GridModel qryCabinetOpRecList2(CabinetOpRecEntity cabinetOpRecEntity){

        Integer totalCnt = monitorMapper.qryCabinetOpRecCnt2(cabinetOpRecEntity);
        List<CabinetOpRecEntity> list = monitorMapper.qryCabinetOpRecList2(cabinetOpRecEntity);

        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);
        return m ;
    }

    //查询气动存查样柜取样取样申请记录
    public GridModel qryCabinetApplyRecList(CabinetOpRecEntity cabinetOpRecEntity){

        Integer totalCnt = monitorMapper.qryCabinetApplyRecCnt(cabinetOpRecEntity);
        List<CabinetOpRecEntity> list = monitorMapper.qryCabinetApplyRecList(cabinetOpRecEntity);

        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);
        return m ;
    }

    //查询气动存查样柜信息-取样，弃样
    public GridModel qryCabinetSampleList(CabinetEntity cabinetEntity){
        List<CabinetEntity> list = monitorMapper.qryCabinetInfoList(cabinetEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //查询气动存查样柜信息-取样、弃样（谏壁-支持分页）
    public GridModel qryCabinetSampleListPage(CabinetEntity cabinetEntity) {
        //查询总记录数
        Integer totalCnt = monitorMapper.qryKYCabinetInfoCnt(cabinetEntity);
        //查询本次页面的结果集
        List<CabinetEntity> list = monitorMapper.qryCabinetInfoListPg(cabinetEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);
        return m;
    }

    //查询气动存查样柜信息-取样，弃样
    public GridModel qryCabinetSampleListNew(CabinetEntity cabinetEntity){
        List<CabinetEntity> list = monitorMapper.qryCabinetInfoListNew(cabinetEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //查询单个车厢详情
    public RegisterEntity qryOneTrainDetail(String recordNo) {
        List<RegisterEntity> list = monitorMapper.qryOneTrainDetail(recordNo);

        //设置返回结果
        RegisterEntity d = list.get(0);

        return d;
    }

    //查询单个车厢详情
    public RegisterEntity qryOneTrainDetailfc(String recordNo) {
        List<RegisterEntity> list = monitorMapper.qryOneTrainDetailfc(recordNo);

        //设置返回结果
        RegisterEntity d = list.get(0);

        return d;
    }

    //修改单个车厢信息
    public void modifyOneTrainInfo(RegisterEntity registerEntity){
        monitorMapper.modifyOneTrainInfo(registerEntity);
    }

    //查询集样罐状的采样编码
    public GridModel qrySampleCodes(String machineType, String command){
        List<CtrlEntity> list = null;
        if (machineType.equals("1") && command.equals("4")) { //采样机的卸样命令
//            list = monitorMapper.qrySampleCodes4XY();

            //因为采样机有时候不靠谱，放开，不限制非得有采样罐
            list = monitorMapper.qrySampleCodes();
        } else {
            list = monitorMapper.qrySampleCodes();
        }

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //查询集样罐状的采样编码
    public GridModel qrySampleCodesByTransType(String machineType, String command, String transType){
        List<CtrlEntity> list = null;
        if (transType != null && transType.equals("0")) { //采样机的卸样命令
            list = monitorMapper.qrySampleCodes4Car();
        } else if (transType != null && transType.equals("1")) { //采样机的卸样命令
            list = monitorMapper.qrySampleCodes4Train();
        } else if (transType != null && transType.equals("2")) { //采样机的卸样命令
            list = monitorMapper.qrySampleCodes4Ship();
        } else {
            list = monitorMapper.qrySampleCodes();
        }

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    @Override
    public List<TakeSampleRecEntity> qryTakeSampleRec(TakeSampleRecEntity takeSampleRecEntity) {
        List<TakeSampleRecEntity> list = monitorMapper.qryTakeSampleRec(takeSampleRecEntity);
        return list;
    }

    //使用新工作模式
    public void addNewWorkMode(WorkModeEntity workModeEntity){
        monitorMapper.addNewWorkMode(workModeEntity);
    }

    //查询系统工作模式
    public WorkModeEntity qryWorkModeInfo(){
        List<WorkModeEntity> list = monitorMapper.qryWorkModeInfo();
        //设置返回结果
        return list.get(0);
    }

    public DeviceBroadEntity qryEmergencyWarnMsg(DeviceBroadEntity entity){
        DeviceBroadEntity r = null;
        List<DeviceBroadEntity> list = monitorMapper.qryEmergencyWarnMsg(entity);

        if (list != null && list.size() > 0) {
            r = list.get(0);
        }

        return r;
    }


    //查询船舶入厂工作模式（谏壁）
    public WorkModeEntity qryShipSampleWorkMode() {
        List<WorkModeEntity> list = monitorMapper.qryShipSampleWorkMode();
        //设置返回结果
        return list.get(0);
    }


    //查询人工采样编码信息
    public GridModel qryManSampleInfo(ManualSampleEntity manualSampleEntity){
        List<ManualSampleEntity> list = monitorMapper.qryManSampleInfo(manualSampleEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //查询煤场信息
    public GridModel qryMcInfo(CoalPileInfoEntity coalPileInfoEntity){
        List<CoalPileInfoEntity> list = monitorMapper.qryMcInfo(coalPileInfoEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }
    //查询人工采样编码信息
    public GridModel qryManInfo(ManualSampleEntity manualSampleEntity){
        List<ManualSampleEntity> list = monitorMapper.qryManInfo(manualSampleEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //查询人工采样编码信息
    public GridModel qryCarManSampleInfo(ManualSampleEntity manualSampleEntity){
        List<ManualSampleEntity> list = monitorMapper.qryCarManSampleInfo(manualSampleEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //查询入炉采样编码信息
    public GridModel qryLcManSampleInfo(ManualSampleEntity manualSampleEntity){
        List<ManualSampleEntity> list = monitorMapper.qryLcManSampleInfo(manualSampleEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //获取人工制样封装码
    public void getPackCode(ManualSampleEntity manualSampleEntity){
        monitorMapper.getPackCode(manualSampleEntity);
    }


    //调用操作记录发送接口报文
    public String callYGWSIntf(String opRecId){
        String resCode = "1";
        String respXml;

        YGCabinetEntity yGCabinetEntity = new YGCabinetEntity();
        yGCabinetEntity.setOpRecId(opRecId);

        //获取报文（过程里组装报文）
        try {
            LOG.error("saveApprResult(incall)---1 begin proc");
            monitorMapper.packYGCabinetOpInfo(yGCabinetEntity);
        } catch (Exception e) {
            LOG.error("saveApprResult(incall)---2 begin proc and err:"+e.getMessage());
            throw new RuntimeException("组装存查样柜接口xml发生异常："+e.getMessage());
        }

        String reqXml = null;

        //获取报文成功
        LOG.error("saveApprResult(incall)---3 end proc:"+yGCabinetEntity.getResCode()+","+yGCabinetEntity.getReqXml());
        if (yGCabinetEntity.getResCode() != null && yGCabinetEntity.getResCode().equals("0")) {
            reqXml = yGCabinetEntity.getReqXml();

            //LOG.debug("请求报文为:" + reqXml);
            if (reqXml == null || reqXml.equals("")){
                throw new RuntimeException("取得存查样柜接口xml失败");
            }
        } else {
            throw new RuntimeException("取得存查样柜接口xml失败："+yGCabinetEntity.getResMsg());
        }

        //调用接口并获取返回报文
        try {
            LOG.error("saveApprResult(incall)---4 begin call:");
            respXml = WSCall.invoke(Constant.getConstVal("YG_CABINET_WS_ADDR"),
                                    Constant.getConstVal("YG_CABINET_WS_NAME"),
                                    Constant.getConstVal("YG_CABINET_WS_QNAME"),
                                    Constant.getConstVal("YG_CABINET_WS_ACTION"),
                                    Constant.getConstVal("YG_CABINET_INPUT_PARM"),
                                    reqXml);
        } catch (Exception e) {
            LOG.error("saveApprResult(incall)---5 end call err:"+e.getMessage());
            throw new RuntimeException("调用存查样柜接口发生异常："+e.getMessage());
        }

        //对返回报文进行处理
        LOG.error("saveApprResult(incall)---6 end call xml:["+respXml+"]");
        if (respXml == null || respXml.equals("")) {
            throw new RuntimeException("调用存查样柜接口发送操作指令失败，请稍后重新审批或联系管理员");
        } else {
            try {
                Document docIntf  = DocumentHelper.parseText(respXml.replace("&lt;","<").replace("&#xd;",""));
                Element intfRoot = docIntf.getRootElement();
                //String oriMsgNo = intfRoot.selectSingleNode("/YGCT/MSG/MsgReturn90002/OriMsgNo").getText();
                String result = intfRoot.selectSingleNode("/YGCT/MSG/MsgReturn90002/Result").getText();
                String addWord = intfRoot.selectSingleNode("/YGCT/MSG/MsgReturn90002/AddWord").getText();
                String information = intfRoot.selectSingleNode("/YGCT/MSG/MsgReturn90002/Information").getText();
                LOG.error("saveApprResult(incall)---7 parse retxml:["+result+"]");
                if (result != null && result.equals("90000")){
                    resCode = "0";
                } else {
                    throw new RuntimeException("解析存查样柜接口返回报文错误："+addWord+information);
                }
            } catch (DocumentException e) {
                LOG.error("saveApprResult(incall)---8 parse retxml:"+e.getMessage());
                throw new RuntimeException("解析存查样柜接口返回报文异常："+e.getMessage());
            }

        }
        return resCode;
    }


    //调用接口查询远光柜子的存储汇总信息
    public YGCabinetEntity qryYGCabinetSumInfo(){
        String respXml;
        YGCabinetEntity yGCabinetEntity = new YGCabinetEntity();
        String reqXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<YGCT ><HEAD><VERSION>1.0</VERSION><SRC>3</SRC><DES>4</DES><MsgNo>31199</MsgNo>" +
                "<MsgId></MsgId><MsgRef /><TransDate></TransDate><Reserve /></HEAD><MSG><ChaXun31199>" +
                "<ID>"+System.currentTimeMillis()+"</ID>" +
                "</ChaXun31199></MSG></YGCT>";

        LOG.debug("请求报文为:" + reqXml);

        //调用接口并获取返回报文
        try {
//            //TODO TEST
//            respXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
//                    "<YGCT ><MSG><ChaXunback41199>" +
//                    "<ID>"+System.currentTimeMillis()+"</ID>" +
//                    "<YSYCWSL>802</YSYCWSL>" +
//                    "<YCYPSL>100</YCYPSL>" +
//                    "<sMMSL>200</sMMSL>" +
//                    "<tMMSL>200</tMMSL>" +
//                    "<t1MMSL>400</t1MMSL>" +
//                    "<t2MMSL>400</t2MMSL>" +
//                    "<CXSJ></CXSJ>" +
//                    "</ChaXunback41199></MSG></YGCT>";

            respXml = WSCall.invoke(Constant.getConstVal("YG_CABINET_WS_ADDR"),
                                    Constant.getConstVal("YG_CABINET_WS_NAME"),
                                    Constant.getConstVal("YG_CABINET_WS_QNAME"),
                                    Constant.getConstVal("YG_CABINET_WS_ACTION"),
                                    Constant.getConstVal("YG_CABINET_INPUT_PARM"),
                                    reqXml);
            LOG.debug("返回报文为:" + respXml);

        } catch (Exception e) {
            throw new RuntimeException("调用存查样柜接口发生异常："+e.getMessage());
        }
        //对返回报文进行处理
        if (respXml == null || respXml.equals("")) {
            throw new RuntimeException("查询存查样柜使用情况汇总信息失败");
        } else {
            try {
                Document docIntf  = DocumentHelper.parseText(respXml);
                Element intfRoot = docIntf.getRootElement();
                String totalCnt = Constant.getConstVal("YG_CABINET_POSI_CNT");
                String usedCnt ="";
//                String usedCnt = intfRoot.selectSingleNode("/YGCT/MSG/ChaXunback41199/YSYCWSL").getText();
                //add by zhangf 20150923加入远光报错信息，返回前台
                Object resultObj = intfRoot.selectSingleNode("/YGCT/MSG/ChaXunback41199/YSYCWSL");
                if (resultObj == null) {
                    String result = intfRoot.selectSingleNode("/YGCT/MSG/MsgReturn90002/Result").getText();
                    String addWord = intfRoot.selectSingleNode("/YGCT/MSG/MsgReturn90002/AddWord").getText();
                    String information = intfRoot.selectSingleNode("/YGCT/MSG/MsgReturn90002/Information").getText();
                    if (result != null) {
                        throw new RuntimeException(information);
                    }
                } else {
                    usedCnt = intfRoot.selectSingleNode("/YGCT/MSG/ChaXunback41199/YSYCWSL").getText();
                }
                String exceptCnt = intfRoot.selectSingleNode("/YGCT/MSG/ChaXunback41199/YCYPSL").getText();
                String sixMMCnt = intfRoot.selectSingleNode("/YGCT/MSG/ChaXunback41199/YPSL_6").getText();
                String threeCnt = intfRoot.selectSingleNode("/YGCT/MSG/ChaXunback41199/YPSL_3").getText();
                String zero2LabCnt = intfRoot.selectSingleNode("/YGCT/MSG/ChaXunback41199/YPSL_200").getText();
                String zero2StoCnt = intfRoot.selectSingleNode("/YGCT/MSG/ChaXunback41199/YPSL_201").getText();



                yGCabinetEntity.setTotalCnt(totalCnt);
                yGCabinetEntity.setUsedCnt(usedCnt);
                yGCabinetEntity.setLeftCnt(new Integer(Integer.parseInt(totalCnt)-Integer.parseInt(usedCnt)).toString());
                yGCabinetEntity.setExceptCnt(exceptCnt);
                yGCabinetEntity.setSixMMCnt(sixMMCnt);
                yGCabinetEntity.setThreeCnt(threeCnt);
                yGCabinetEntity.setZero2LabCnt(zero2LabCnt);
                yGCabinetEntity.setZero2StoCnt(zero2StoCnt);

            } catch (DocumentException e) {
                throw new RuntimeException("解析存查样柜接口返回柜子使用汇总情况信息报文异常："+e.getMessage());
            }
        }

        return yGCabinetEntity;
    }

    public YGCabinetEntity qryYGCabinetSumInfoExpiredCnt(String typeId,String comeTag){
        String tagCondition = "";
        YGCabinetEntity yGCabinetEntity = new YGCabinetEntity();
        yGCabinetEntity.setSampleStatus("0302");
        if ("CYCLE_6".equals(typeId) || "CYCLE_6_RL".equals(typeId)) {
            tagCondition = "AND A.SAMPLE_TYPE IN ('61', '62')";
        } else if ("CYCLE_3".equals(typeId) || "CYCLE_3_RL".equals(typeId)) {
            tagCondition = "AND A.SAMPLE_TYPE IN ('31', '32', '33', '34', '35')";
        } else if ("CYCLE_2".equals(typeId) || "CYCLE_2_RL".equals(typeId)) {
            tagCondition = "AND A.SAMPLE_TYPE IN ('21', '22')";
        }else if ("CYCLE_7".equals(typeId) || "CYCLE_7_RL".equals(typeId)) {
            tagCondition = "AND A.SAMPLE_TYPE IN ('71', '72', '73', '74')";
        }

        if(comeTag.equals("9")){
            comeTag = " = '9'";
        }else{
            comeTag = " <> '9'";
        }

        yGCabinetEntity.setSampleLimitedTime("<= TRUNC(SYSDATE - TO_NUMBER(GET_WORK_MODE('"+typeId+"'))) "+tagCondition +" AND A.SAMPLING_CODE IN (SELECT B.SAMPLING_CODE  FROM BATCH_NO_INFO B WHERE B.BATCH_NO_TYPE " + comeTag + ")");
        List<YGCabinetEntity> list = monitorMapper.qryYGCabinetInfo(yGCabinetEntity);
        yGCabinetEntity.setLimitedTimeCnt(String.valueOf(list.size()));
        return yGCabinetEntity;
    }



    //查询远光存查样柜的存样信息
    public GridModel qryYGCabinetInfo(YGCabinetEntity yGCabinetEntity){
        List<YGCabinetEntity> list = monitorMapper.qryYGCabinetInfo(yGCabinetEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }
//查询汽车违规信息
    public GridModel qryViolaData(ViolaEntity violaEntity){
        List<ViolaEntity> list = monitorMapper.qryViolaData(violaEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }
    //查询汽车违规详细信息
    public  ViolaEntity  viewViolaDetail(String violaRecId){
        List<ViolaEntity> list = monitorMapper.viewViolaDetail(violaRecId);

        //设置返回结果
        ViolaEntity d = list.get(0);

        return d;
    }

    //修改汽车违规信息
    public void editViolaDetail( ViolaEntity violaEntity){
        monitorMapper.editViolaDetail(violaEntity);
    }

    public List<CarInfoEntity> qryCarByCarIdOrCardId(CarInfoEntity carInfoEntity){
        List<CarInfoEntity>  list = monitorMapper.qryCarByCarIdOrCardId(carInfoEntity);
        return list;
    }

    //查询船运信息
    public GridModel qryShipTransInfo(ShipEntity shipEntity){
        List<ShipEntity>  list = monitorMapper.qryShipTransInfo(shipEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //蚌埠-查询船运煤批次信息
    public GridModel qryShipBatch(ShipEntity shipEntity){
        List<ShipEntity>  list = monitorMapper.qryShipBatch(shipEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //查询船资料信息
    public GridModel qryShipInfo(ShipEntity shipEntity){
        List<ShipEntity>  list = monitorMapper.qryShipInfo(shipEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //查询船运的货运信息
    public GridModel qryShipCargoInfo(ShipEntity shipEntity){
        List<ShipEntity>  list = monitorMapper.qryShipCargoInfo(shipEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //编辑轮船信息
    public void editShipTransRec(ShipEntity shipEntity){
        monitorMapper.editShipTransRec(shipEntity);
    }

    //编辑轮船信息
    public void submitEditShipInfo(ShipEntity shipEntity){
        monitorMapper.submitEditShipInfo(shipEntity);
    }

    //编辑船运煤信息
    public void editShipCargoRec(ShipEntity shipEntity){
        monitorMapper.editShipCargoRec(shipEntity);
    }

    //结束卸煤
    public void finishUnloadRec(ShipEntity shipEntity){
        monitorMapper.finishUnloadRec(shipEntity);
    }


    //查询船运分批详细信息
    public GridModel qryShipBatchInfo(BatchNoInfoEntity batchNoInfoEntity){
        List<BatchNoInfoEntity>  list = monitorMapper.qryShipBatchInfo(batchNoInfoEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //补录人工制样的封装码信息
    public void editSamplingResultRec(EditSamplingRptEntity editSamplingRptEntity){
        monitorMapper.editSamplingResultRec(editSamplingRptEntity);
    }

    //新增系统外批次
    public void addVirtualBatchInfoRecord(SamplingRptEntity samplingRptEntity){
        monitorMapper.addVirtualBatchInfoRecord(samplingRptEntity);
    }

    @Override
    public void addFurnaceBatchInfoRecord(SamplingRptEntity samplingRptEntity) {
        monitorMapper.addFurnaceBatchInfoRecord(samplingRptEntity);
    }

    //底样批次操作
    public void carAttachBatchForBat(RegisterEntity registerEntity){
        monitorMapper.carAttachBatchForBat(registerEntity);
    }

    //删除整个车次(用存储过程)
    public void deleteWholeTrain(RegisterEntity registerEntity){
        monitorMapper.deleteWholeTrain(registerEntity);
    }


    //结束或删除船运批次
    public void compOrDelShipBatchPro(ShipEntity shipEntity){
        monitorMapper.compOrDelShipBatchPro(shipEntity);
    }

    //查询卸煤沟信息详情
    public GridModel qryXmTransList(XmTransSetEntity xmTransSetEntity){
        List<XmTransSetEntity>  list = monitorMapper.qryXmTransList(xmTransSetEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }


    //修改汽车卸煤队列
    public  void  editCarQueue(XmTransSetEntity xmTransSetEntity) {
        monitorMapper.editCarQueue(xmTransSetEntity);
    }


    //修改卸煤通道信息
    public  void  editxmTransDetail(XmTransSetEntity xmTransSetEntity) {
        monitorMapper.editxmTransDetail(xmTransSetEntity);
    }

    public GridModel qryBeltBalanceInfo(BatchNoInfoEntity beltBalanceEntity){
        List<BatchNoInfoEntity> list = monitorMapper.qryBeltBalanceInfo(beltBalanceEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }


    //确认上传燃料MIS（衡丰）
    public void uploadToMis(UploadToMisEntity uploadToMisEntity){
        monitorMapper.uploadToMis(uploadToMisEntity);
    }

    //确认上传燃料MIS（哈平南 远光MIS）
    public void uploadToMisYg(UploadToMisEntity uploadToMisEntity){
        monitorMapper.uploadToMisYg(uploadToMisEntity);
    }

    //编辑汽车违规信息（新增）
    public void addViolateRecord(ViolaEntity violaEntity){
        monitorMapper.addViolateRecord(violaEntity);
    }

    //蚌埠新增船煤批次
    public void addNewShipBatch(ShipEntity shipEntity){
        monitorMapper.addNewShipBatch(shipEntity);
    }

    //蚌埠操作状态
    public void operateBatchStatus(ShipEntity shipEntity){
        monitorMapper.operateBatchStatus(shipEntity);
    }

    //补录人工采样信息
    public void editSampleResultRec(SampleRptEntity sampleRptEntity){
        monitorMapper.editSampleResultRec(sampleRptEntity);
    }

    //蚌埠校准船运卸煤吨位
    public void modifyAllNetQty(ShipEntity shipEntity){
        monitorMapper.modifyAllNetQty(shipEntity);
    }

    //调用此方法,需要注意异常  throw RuntimeException
    private String callYGWebService(String reqXml){
        String resCode = "1";
        String respXml;

        //调用接口并获取返回报文
        respXml = WSCall.invoke(Constant.getConstVal("YG_CABINET_WS_ADDR"),
                Constant.getConstVal("YG_CABINET_WS_NAME"),
                Constant.getConstVal("YG_CABINET_WS_QNAME"),
                Constant.getConstVal("YG_CABINET_WS_ACTION"),
                Constant.getConstVal("YG_CABINET_INPUT_PARM"),
                reqXml);
        return respXml;
    }

    //wangz ,20160727 参考接口文档: 3.6.10	样品全量盘点下达(31200),远光 ChaXun31200
    //调用此方法,需要注意异常  throw RuntimeException
    public String callYGIntfForCode31200(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        StringBuffer sb = new StringBuffer();

        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        sb.append("<YGCT>");
        sb.append("<HEAD>");
        sb.append("<VERSION>1.0</VERSION>");
        sb.append("<SRC>3</SRC>");
        sb.append("<DES>4</DES>");
        sb.append("<MsgNo>31200</MsgNo>");
        sb.append("<MsgId>").append(dateString).append("</MsgId>");
        sb.append("<MsgRef/>");
        sb.append("<TransDate>").append(dateString).append("</TransDate>");
        sb.append("<Reserve/>");
        sb.append("</HEAD>");
        sb.append("<MSG>");
        sb.append("<ChaXun31200>");
        sb.append("<ID>All</ID>");
        sb.append("</ChaXun31200>");
        sb.append("</MSG>");
        sb.append("</YGCT>");
        return callYGWebService(sb.toString());
    }
	
	//九江查询船运计量统计报表
    public GridModel qryShipWeightListInfo(ShipEntity shipEntity){
        List<ShipEntity>  list = monitorMapper.qryShipWeightInfo(shipEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //查询门禁系统历史数据
    public GridModel qryAccessControlInfoList(AccessControlEntity accessControlEntity){

        Integer totalCnt = monitorMapper.qryAccessControlCnt(accessControlEntity);
        //查询本次页面的结果集
        List<AccessControlEntity> list = monitorMapper.qryAccessControlInfoList(accessControlEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);

        return m;
    }

  //查询煤样追踪信息
    public GridModel qrySampleDetailList(SampleTraceEntity sampleTraceEntity){

        List<SampleTraceEntity> list = monitorMapper.qrySampleDetailList(sampleTraceEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    public BatchNoInfoEntity qrySampleBatchNoInfo(BatchNoInfoEntity batchNoInfoEntity){
        BatchNoInfoEntity singleBatchNoInfoEntity = monitorMapper.qrySingleBatchNoInfo(batchNoInfoEntity);
        return singleBatchNoInfoEntity;
    }
	
    //提交设备控制命令
    public void commitCtrlCmd2(CtrlEntity ctrlEntity){
        monitorMapper.commitCtrlCmdPro2(ctrlEntity);
    }

    //江南，对于0.2mm待取样的化验样，自动发送取样到化验室的指令到远光存查样柜
    public void saveYg02mmAutoToLab(){
        long beginTime = System.currentTimeMillis();
        String sampleStoreId = monitorMapper.qryYG02mmReadyStateAutoToLab();
        String respXml = null;
        String resCode = null;
        
        if(sampleStoreId != null && sampleStoreId.length() > 1){
            BizLogEntity logEntity = new BizLogEntity();
            logEntity.setInParam(sampleStoreId==null ?"":sampleStoreId);
            long startTime = System.currentTimeMillis();
            logEntity.setBizId("0");
            logEntity.setServiceName("saveYg02mmAutoToLab");
            logEntity.setTransId("0");
            logEntity.setSysId("1");
            logEntity.setReqUserId("1");
            logEntity.setTransId(sampleStoreId);
            //调用存储过程，处理变动纪录，并返回报文
            YGCabinetEntity yGCabinetEntity = new YGCabinetEntity();
            yGCabinetEntity.setOpRecId(sampleStoreId);

            //获取登录操作员的ID
            String opCode = Constant.getConstVal("YG02MM_AUTO_UPLOAD_STAFF");
            yGCabinetEntity.setStaffId(opCode);

            yGCabinetEntity.setOpType("02"); //取样  03是弃样
            yGCabinetEntity.setDestination("1"); //1 化验室取样站，2 人工制样间取样站，3 弃样站 //送1

            try {
                //此存储过程中无commit，如果saveYg02mmAutoToLab方法中，抛出RuntimeException，则事务回退
                monitorMapper.packYg02mmAutoToLab(yGCabinetEntity);
            } catch (Exception e) {
                logEntity.setResCode("1");
                logEntity.setResMsg("调用存储过程返回失败:"+e.getMessage());
                LogUtil.recordLog(logEntity);
                throw new RuntimeException("0.2mm待取样的化验样自动取样【调用存储过程】异常："+e.getMessage());
            }

            String reqXml = null;
            //获取报文成功
            if (yGCabinetEntity.getResCode() != null && yGCabinetEntity.getResCode().equals("0")) {
                reqXml = yGCabinetEntity.getReqXml();
                //LOG.debug("请求报文为:" + reqXml);
                if (reqXml == null || reqXml.equals("")){
                    logEntity.setResCode("1");
                    logEntity.setResMsg("存储过程返回报文为空");
                    LogUtil.recordLog(logEntity);
                    throw new RuntimeException("0.2mm待取样的化验样自动取样【存储过程返回报文为空】异常");
                }
            } else {
                logEntity.setResCode("1");
                logEntity.setResMsg("存储过程调用异常"+yGCabinetEntity.getResMsg());
                throw new RuntimeException("0.2mm待取样的化验样自动取样【存储过程调用】异常："+yGCabinetEntity.getResMsg());
            }

            //调用接口并获取返回报文
            try {
                respXml = WSCall.invoke(Constant.getConstVal("YG_CABINET_WS_ADDR"),
                        Constant.getConstVal("YG_CABINET_WS_NAME"),
                        Constant.getConstVal("YG_CABINET_WS_QNAME"),
                        Constant.getConstVal("YG_CABINET_WS_ACTION"),
                        Constant.getConstVal("YG_CABINET_INPUT_PARM"),
                        reqXml);
            } catch (Exception e) {
                e.printStackTrace();
                logEntity.setResCode("1");
                logEntity.setResMsg("webservice调用异常"+e.getMessage());
                LogUtil.recordLog(logEntity);
                throw new RuntimeException("0.2mm待取样的化验样自动取样【调用远光接口】异常："+e.getMessage());
            }

            //对返回报文进行处理
            if (respXml == null || respXml.equals("")) {
                logEntity.setResCode("1");
                logEntity.setResMsg("0.2mm待取样的化验样自动取样【远光返回报文为空】异常");
                LogUtil.recordLog(logEntity);
                throw new RuntimeException("0.2mm待取样的化验样自动取样【远光返回报文为空】异常");
            } else {
                try {
                    Document docIntf  = DocumentHelper.parseText(respXml.replace("&lt;","<").replace("&#xd;",""));
                    Element intfRoot = docIntf.getRootElement();
                    String result = intfRoot.selectSingleNode("/YGCT/MSG/MsgReturn90002/Result").getText();
                    String addWord = intfRoot.selectSingleNode("/YGCT/MSG/MsgReturn90002/AddWord").getText();
                    String information = intfRoot.selectSingleNode("/YGCT/MSG/MsgReturn90002/Information").getText();
                    if (result != null && result.equals("90000")){
                        resCode = "0";
                        logEntity.setResCode("0");
                        logEntity.setResMsg("成功");
                    } else {
                        logEntity.setResCode("1");
                        logEntity.setResMsg("0.2mm待取样的化验样自动取样【解析远光返回报文】异常");
                        LogUtil.recordLog(logEntity);
                        throw new RuntimeException("0.2mm待取样的化验样自动取样【解析远光返回报文】异常"+addWord+information);
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                    logEntity.setResCode("1");
                    logEntity.setResMsg("0.2mm待取样的化验样自动取样【处理】异常："+e.getMessage());
                    LogUtil.recordLog(logEntity);
                    throw new RuntimeException("0.2mm待取样的化验样自动取样【处理】异常："+e.getMessage());
                }
            }
            LogUtil.recordLog(logEntity);
        }
    }

    public void modifyBatchNoInfo(SamplingRptEntity samplingRptEntity){
        monitorMapper.modifyBatchNoInfo(samplingRptEntity);
    }

    //修改批次重量信息 for常州 20170207
    public void editBatchWeightInfo(SampleRptEntity sampleRptEntity){
        monitorMapper.editBatchWeightInfo(sampleRptEntity);
    }

    /*江南项目人工增加汽车煤批次***/
    public void manualCarBatchInfo(BatchNoInfoEntity batchNoInfoEntity){
        monitorMapper.manualCarBatchInfo(batchNoInfoEntity);
    }


    /*江南汽车煤批次信息查询**/
    public GridModel qryManualCarBatchList(BatchNoInfoEntity batchNoInfoEntity){

        Integer num = monitorMapper.qryManualCarBatchCnt(batchNoInfoEntity);
        List<BatchNoInfoEntity> list = monitorMapper.qryManualCarBatchList(batchNoInfoEntity);

        GridModel grid = new GridModel();
        grid.setTotal(num);
        grid.setRows(list);

        return  grid;
    }
	
	//编辑锁车信息（给织金使用）
    public void addLockCarRecord(CarLockInfoEntity carLockInfoEntity){
        monitorMapper.addLockCarRecord(carLockInfoEntity);
    }

    //查询锁车信息（给织金使用）
    public GridModel qryLockCarList( CarLockInfoEntity carLockInfoEntity) {
        //查询本次页面的结果集
        List<CarLockInfoEntity> carLockInfoList = monitorMapper.qryLockCarList(carLockInfoEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(carLockInfoList);
        return m;
    }


	/*存查样柜提交样品延期清样*/
    public void submitCabinetDelay(CabinetEntity cabinetEntity){
        monitorMapper.submitCabinetDelay(cabinetEntity);
    }

    public GridModel qryCabinetDelayRecList(CabinetEntity cabinetEntity){

        Integer num = monitorMapper.qryCabinetDelayRecCnt(cabinetEntity);

        List<CabinetEntity> list = monitorMapper.qryCabinetDelayRec(cabinetEntity);

        GridModel grid = new GridModel();

        grid.setTotal(num);
        grid.setRows(list);

        return  grid;
    }


    public void massSaveErrInfo(DeviceErrEntity deviceErrEntity){
        monitorMapper.massSaveErrInfo(deviceErrEntity);
    }

    /*根据用户名称查询密码**/
    public String qryPwdByUserName(String userName){
        return monitorMapper.qryPwdByUserName(userName);
    }
	
	 //录入采样报告信息
    public void editSampleReport(SampleRptEntity sampleRptEntity){
        monitorMapper.editSampleReport(sampleRptEntity);
    }

    //录入采样报告信息
    public void dealEmergencyWarn(DeviceBroadEntity deviceBroadEntity){
        monitorMapper.dealEmergencyWarn(deviceBroadEntity);
    }

    //录入制样报告信息
    public void editSamplingReport(SamplingRptEntity samplingRptEntity){
        monitorMapper.editSamplingReport(samplingRptEntity);
        System.out.println(samplingRptEntity.getJsonString());
    }
	
    //九江记录皮带秤数据
    public void  saveBeltWeightInfo(BeltWeightEntity beltWeightEntity){
        monitorMapper.saveBeltWeightInfo(beltWeightEntity);
    }
	
    //九江查询皮带秤数据
    public GridModel qryBeltWeightInfo(BeltWeightEntity beltWeightEntity){

        Integer num = monitorMapper.qryBeltWeightRecordCnt(beltWeightEntity);

        List<BeltWeightEntity> list = monitorMapper.qryBeltWeightRec(beltWeightEntity);

        GridModel grid = new GridModel();

        grid.setTotal(num);
        grid.setRows(list);

        return  grid;
    }

    //九江-船运皮带秤批次计量 信息查询
    //liuzh 2020-02-12
    public GridModel qryShipBatchQtyInfo(ShipBatchQtyEntity shipBatchQtyEntity){

        Integer num = monitorMapper.qryShipBatchQtyInfoCnt(shipBatchQtyEntity);

        List<ShipBatchQtyEntity> list = monitorMapper.qryShipBatchQtyInfo(shipBatchQtyEntity);

        GridModel grid = new GridModel();

        grid.setTotal(num);
        grid.setRows(list);

        return  grid;
    }

    //泉州查询皮带秤数据
    public GridModel qryBeltWeightRec4Qz(BeltWeightEntity beltWeightEntity){

        List<BeltWeightEntity> list = monitorMapper.qryBeltWeightRec(beltWeightEntity);

        GridModel grid = new GridModel();

        grid.setRows(list);

        return  grid;
    }

    //查询大开汽车来煤信息
    public GridModel qryCarTransRecordList4DK(CarTransRecordEntity carTransRecordEntity){
        //查询总记录数
        Integer totalCnt = monitorMapper.qryCarTransRecordListCnt4DK(carTransRecordEntity);
        //查询本次页面的结果集
        List<CarTransRecordEntity> list = monitorMapper.qryCarTransRecordList4DK(carTransRecordEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);
        return m;
    }


    //大开执行分批操作
    public void doBatch(CarTransRecordEntity carTransRecordEntity){
        monitorMapper.doBatch(carTransRecordEntity);
    }


    //聊城查询人工采样编码信息
    public GridModel qryManInfo4LC(ManualSampleEntity manualSampleEntity){
        List<ManualSampleEntity> list = monitorMapper.qryManInfo4LC(manualSampleEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //化验编码打印计数
    public void addPrintNum(ManualSampleEntity manualSampleEntity)  {
        monitorMapper.addPrintNum(manualSampleEntity);
    }

    //编码打印记录
    public void addPrintRec(ManualSampleEntity manualSampleEntity)  {
        monitorMapper.addPrintRec(manualSampleEntity);
    }


    //聊城查询气动存查样柜信息-取样，弃样
    public GridModel qryCabinetSampleList4LC(CabinetEntity cabinetEntity){
        List<CabinetEntity> list = monitorMapper.qryCabinetInfoList4LC(cabinetEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }


    public void manualControlBeltWeight(ShipEntity shipEntity){
        monitorMapper.manualControlBeltWeight(shipEntity);
    }

    //新增汽车信息 用于存储过程 以便记录日志 xxs20180527
    public void addCarInfoNew(CarInfoEntity carInfoEntity){
        monitorMapper.addCarInfoNew(carInfoEntity);
    }

    public void autoTakeSample4XW(ApproveEntity approveEntity){
        monitorMapper.autoTakeSample4XW(approveEntity);
    }

    //查询汽车来煤信息4XW
    public GridModel qryCarTransRecordList4XW(CarTransRecordEntity carTransRecordEntity){
        //查询总记录数
        Integer totalCnt = monitorMapper.qryCarTransRecordListCnt(carTransRecordEntity);

        //查询本次页面的结果集
        List<CarTransRecordEntity> list = monitorMapper.qryCarTransRecordList4XW(carTransRecordEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        m.setTotal(totalCnt);

        return m;
    }

    //管理员权限账号列表 查询
    public GridModel qrySysUserApprList(ApproveEntity approveEntity){
        List<ApproveEntity>  list = monitorMapper.qrySysUserApprList(approveEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //查询封装码的打印次数 宣威先要的
    public GridModel qryManInfoPackCode(ManualSampleEntity manualSampleEntity){
        List<ManualSampleEntity> list = monitorMapper.qryManInfoPackCode(manualSampleEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //查询采制化编码的打印次数 宣威先要的
    public GridModel qryManInfo4XW(ManualSampleEntity manualSampleEntity){
        List<ManualSampleEntity> list = monitorMapper.qryManInfo4XW(manualSampleEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //查询汽车批次信息详情
    public GridModel qryCarBasicList(CarTransRecordEntity carTransRecordEntity){
        //查询本次页面的结果集
        List<CarTransRecordEntity> list = monitorMapper.qryCarBasicList(carTransRecordEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    //合并汽车批次  宣威
    public void mergeCarBatchs(CarTransRecordEntity carTransRecordEntity){
        monitorMapper.mergeCarBatchs(carTransRecordEntity);
    }

    //查询火车重量修改记录  聊城
    public GridModel qryOneTrainHisList(RegisterEntity registerEntity){
       //查询本次页面的结果集
        List<RegisterEntity> list = monitorMapper.qryOneTrainHisList(registerEntity);

        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);

        return m;
    }

    public void dealCarTransRecord4KcAppr(CarTransRecordEntity carTransRecordEntity){
        monitorMapper.dealCarTransRecord4KcAppr(carTransRecordEntity);
    }

    //查询船运的货运信息
    public GridModel qryShipCargoInfo4LD(ShipEntity shipEntity){
        List<ShipEntity>  list = monitorMapper.qryShipCargoInfo4LD(shipEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //克拉玛依人工制样
    @Override
    public void manualSampleKlmy(Map<String, Object> params) {
        monitorMapper.manualSampleKlmy(params);
    }
}
