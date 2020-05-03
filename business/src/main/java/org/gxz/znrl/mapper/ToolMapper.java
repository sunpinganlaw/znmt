package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xieyt on 14-11-18.
 */

@Repository
public interface ToolMapper {

    //获取下拉框里的数据
    public List<ComboboxOptionEntity> getCoalTypeOption();

    public List<ComboboxOptionEntity> getCoalTypeListOption();

    public List<ComboboxOptionEntity> getCoalTypeListRlOption();

    public List<ComboboxOptionEntity> getCoalTypeListForecastOption();

    public List<ComboboxOptionEntity> getVendorOption();

    public List<ComboboxOptionEntity> getVendorListOption();

    public List<ComboboxOptionEntity> getCarrierOption();

    public List<ComboboxOptionEntity> getCarrierListOption();

    public List<ComboboxOptionEntity> getMineOption();

    public List<ComboboxOptionEntity> getMineForShortOption();

    public List<ComboboxOptionEntity> getMineListOption();

    public List<ComboboxOptionEntity> getApprEventTypeOption();

    public List<ComboboxOptionEntity> getTimeZoneOption();

    //获取新的火车车次
    public String generateNewTrainNo();

    public String getCarNo(FunctionEntity functionEntity);

    public String checkPrivilege(PrivilegeEntity privilegeEntity);

    public List<ComboboxOptionEntity> getSampleCodes4XY();

    public List<ComboboxOptionEntity> getSampleCodes();

    public List<ComboboxOptionEntity> getSampleCodesZj();

    public List<ComboboxOptionEntity> getSamplingCodes();

    public List<ComboboxOptionEntity> getFurnaceSampleCodes4XY();

    public List<ComboboxOptionEntity> getFurnaceSampleCodes();

    public List<ComboboxOptionEntity> getFurnaceSampleCodesJB();

    public List<ComboboxOptionEntity> getSampleCodes4Train();

    public List<ComboboxOptionEntity> getSampleCodes4TrainXW();
    public List<ComboboxOptionEntity> getSampleCodes4TrainXY();

    public List<ComboboxOptionEntity> getSampleCodes4Car();
    public List<ComboboxOptionEntity> getSampleCodes4Hf();

    public List<ComboboxOptionEntity> getSampleCodes4Ship();

    public List<ComboboxOptionEntity> getScalesStandard();

    public List<ComboboxOptionEntity> getSStandard();

    public List<ComboboxOptionEntity> getHStandard();

    public List<ComboboxOptionEntity> getMELTStandard();

    public List<ComboboxOptionEntity> getSysUser();

    public List<ComboboxOptionEntity> getLaborCode();

    public List<ComboboxOptionEntity> getLaborCode4BB();

    public List<ComboboxOptionEntity> getLaborCode4CCY();

    public List<ComboboxOptionEntity> getShipRecId();

    public List<ComboboxOptionEntity> getContractNoList();

    public List<ComboboxOptionEntity> getLaborCodeByInput(String input);

    public List<ComboboxOptionEntity> getCoalMineForShortByInput(String input);

    public List<ComboboxOptionEntity> getLaborCodeByType(String input);

    public List<ComboboxOptionEntity> getSampleCodeByInput(String input);

    public List<ComboboxOptionEntity> getSampleCodeByInputZj(String input);

    public List<ComboboxOptionEntity> getSamplingCodeByInput(String input);

    public List<ComboboxOptionEntity> getcarIdByInput(String input);

    public List<ComboboxOptionEntity> getShipRecIdByInput(String input);

    public List<ComboboxOptionEntity> getShipsByInput(String input);

    public List<ComboboxOptionEntity> getLaborDevice();

    public List<ComboboxOptionEntity> getIAStandard();

    public List<ComboboxOptionEntity> getHotStandard();

    public List<ComboboxOptionEntity> getLAStandard();

    public List<ComboboxOptionEntity> getShipInfo();

    public List<ConstantEntity> loadConstant();

    public List<MsgPushEntity> qryPushMsgList(List<String> msgIdsList);

    public void updatePushMsg(MsgPushEntity msgPushEntity);

    //加载配置常量到内存
    public List<ConstantEntity> qryConstantCfgData();

    //插入接口日志关键数据
    public void insertIntfLog(BizLogEntity logEntity);

    //插入接口请求报文
    public void insertIntfLogExt(BizLogEntity logEntity);

    //获取下一个序列ID
    public String getSeqNextval(CommonEntity commonEntity);
	
	//获取管控系统的所有设备列表
	public List<ComboboxOptionEntity> getDeviceInfoList();

    //获取管控系统用户名
    public List<ComboboxOptionEntity> getUserNameList();

    public String getWorkMode(FunctionEntity functionEntity);

    //获取参数上下限值hxl
    public String getLimitValue(FunctionEntity functionEntity);

    //获取批次号
    public List<ComboboxOptionEntity> getBatchNoList();

    //获取瓶数
    public List<ComboboxOptionEntity> getBottleCntList();

    //获取瓶数，通过化验编码查询查询
    public List<ComboboxOptionEntity> getBottleCntByInput(String input);

    //获取接卸方案4蚌埠
    public List<ComboboxOptionEntity> getDischargeforecastByInput(String input);

    //根据输入模糊查询批次号
    public List<ComboboxOptionEntity> getBatchNoByInput(String input);

    //根据输入模糊查询车号
    public List<ComboboxOptionEntity> getCarIdByInput(String input);

    public List<ComboboxOptionEntity> getLabDeviceOption();

    public List<ComboboxOptionEntity> getSampleCodesQz();
//水尺单号 大开使用
    public List<ComboboxOptionEntity> getShuichiTicket();

    //获取接卸方案4蚌埠
    public List<ComboboxOptionEntity> getGoodInfoByInput(String input);

    public List<ComboboxOptionEntity> getGoodInfoOption();

    public List<ComboboxOptionEntity> getStockInfoOption();
    public List<ComboboxOptionEntity> getChannelInfoOption();
}
