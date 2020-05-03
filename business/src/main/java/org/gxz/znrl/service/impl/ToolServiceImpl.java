package org.gxz.znrl.service.impl;

import org.gxz.znrl.entity.*;
import org.gxz.znrl.mapper.DictionaryTableMapper;
import org.gxz.znrl.mapper.ToolMapper;
import org.gxz.znrl.service.CommonService;
import org.gxz.znrl.service.IToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xieyt on 14-11-18.
 */
@Service("toolService")
@Transactional
@SuppressWarnings("unchecked")
public class ToolServiceImpl implements IToolService {

    @Autowired
    public ToolMapper toolMapper;

    @Autowired
    public DictionaryTableMapper dictionaryTableMapper;

    @Autowired
    private CommonService commonService;

    public List<ComboboxOptionEntity> getComboboxOption(String tag){
        //查询下拉选项框
        List<ComboboxOptionEntity> r = null;
        if (tag.equals("COAL_TYPE")) {
            r = toolMapper.getCoalTypeOption();
        } else if (tag.equals("COAL_TYPE_LIST")) {
            r = toolMapper.getCoalTypeListOption();
        } else if (tag.equals("VENDOR_INFO")){
            r = toolMapper.getVendorOption();
        } else if (tag.equals("VENDOR_INFO_LIST")){
            r = toolMapper.getVendorListOption();
        } else if (tag.equals("CARRIER_INFO")) {
            r = toolMapper.getCarrierOption();
        } else if (tag.equals("CARRIER_INFO_LIST")) {
            r = toolMapper.getCarrierListOption();
        }else if (tag.equals("COAL_MINE")) {
            r = toolMapper.getMineOption();
        }else if (tag.equals("COAL_MINE_FORSHORT")) {
            r = toolMapper.getMineForShortOption();
        } else if (tag.equals("COAL_MINE_LIST")) {
            r = toolMapper.getMineListOption();
        } else if (tag.equals("APPR_EVENT_TYPE")) {
            r = toolMapper.getApprEventTypeOption();
        }else if (tag.equals("TIME_ZONE")) {
            r = toolMapper.getTimeZoneOption();
        }else if (tag.equals("SAMPLE_CODES")) {
            r = toolMapper.getSampleCodes();
        }else if (tag.equals("SAMPLE_CODES_QZ")) {
            r = toolMapper.getSampleCodesQz();
        }else if (tag.equals("SAMPLE_CODES_ZJ")) {
            r = toolMapper.getSampleCodesZj();
        }else if (tag.equals("SAMPLING_CODES")) {
            r = toolMapper.getSamplingCodes();
        }else if (tag.equals("SAMPLE_CODES4XY")) {
            r = toolMapper.getSampleCodes4XY();
        }else if (tag.equals("SAMPLE_CODES_LC")) {
            r = toolMapper.getFurnaceSampleCodes();
        }else if (tag.equals("SAMPLE_CODES_LCJB")) {
            r = toolMapper.getFurnaceSampleCodesJB();
        }else if (tag.equals("SAMPLE_CODES4XY_LC")) {
            r = toolMapper.getFurnaceSampleCodes4XY();
        }else if (tag.equals("SAMPLE_CODES4TRAIN")) {
            r = toolMapper.getSampleCodes4Train();
        }else if (tag.equals("SAMPLE_CODES4TRAINXW")) {
            r = toolMapper.getSampleCodes4TrainXW();
        }else if (tag.equals("SAMPLE_CODES4TRAINXY")) {
            r = toolMapper.getSampleCodes4TrainXY();
        }else if (tag.equals("SAMPLE_CODES4CAR")) {
            r = toolMapper.getSampleCodes4Car();
        }else if (tag.equals("SAMPLE_CODES4HF")) {
            r = toolMapper.getSampleCodes4Hf();
        }else if (tag.equals("SAMPLE_CODES4SHIP")) {
            r = toolMapper.getSampleCodes4Ship();
        }else if (tag.equals("SCALES_STANDARD")) {
            r = toolMapper.getScalesStandard();
        }else if (tag.equals("S")) {
            r = toolMapper.getSStandard();
        }else if (tag.equals("IA")) {
            r = toolMapper.getIAStandard();
        }else if (tag.equals("HOT")) {
            r = toolMapper.getHotStandard();
        }else if (tag.equals("LA")) {
            r = toolMapper.getLAStandard();
        }else if (tag.equals("H")) {
            r = toolMapper.getHStandard();
        }else if (tag.equals("MELT")) {
            r = toolMapper.getMELTStandard();
        }else if (tag.equals("SYS_USER")) {
            r = toolMapper.getSysUser();
        }else if (tag.equals("LABOR_CODE")) {
            r = toolMapper.getLaborCode();
        }else if (tag.equals("LABOR_CODE4BB")) {
            r = toolMapper.getLaborCode4BB();
        }else if (tag.equals("LABOR_CODE4CCY")) {
            r = toolMapper.getLaborCode4CCY();
        }else if (tag.equals("LABOR_DEVICE")) {
            r = toolMapper.getLaborDevice();
        }else if (tag.equals("SHIP_INFO")) {
            r = toolMapper.getShipInfo();
        }else if(tag.equals("DEVICE_INFO")){
            r = toolMapper.getDeviceInfoList();
        }else if (tag.equals("SHIP_REC_ID")) {
            r = toolMapper.getShipRecId();
        }else if (tag.equals("USER_NAME")) {
            r = toolMapper.getUserNameList();
        }else if (tag.equals("contractNo")) {
            r = toolMapper.getContractNoList();
        }else if (tag.equals("batchNo")) {
            r = toolMapper.getBatchNoList();
        }else if (tag.equals("BOTTLE")) {
            r = toolMapper.getBottleCntList();
        }else if (tag.equals("COAL_TYPE_LIST_RL")) {
            r = toolMapper.getCoalTypeListRlOption();
        }else if (tag.equals("COAL_MINE_FORECAST")) {
            r = toolMapper.getCoalTypeListForecastOption();
        }else if (tag.equals("LAB_DEVICE")) {
            r = toolMapper.getLabDeviceOption();
        }else if (tag.equals("SHUICHI_TICKET")) {
            r = toolMapper.getShuichiTicket();
        }else if (tag.equals("GOOD_INFO")) {
            r = toolMapper.getGoodInfoOption();
        }else if (tag.equals("STOCK_INFO")) {
            r = toolMapper.getStockInfoOption();
        }else if (tag.equals("CHANNEL_INFO")) {
            r = toolMapper.getChannelInfoOption();
        }

        return r;
    }

    //获取下拉框里的数据，根据输入模糊匹配
    public List<ComboboxOptionEntity> getComboboxOptionByInput(String tag, String input){
        //查询下拉选项框
        List<ComboboxOptionEntity> r = null;
        if (tag.equals("LABOR_CODE")) {
            r = toolMapper.getLaborCodeByInput(input);
        }    else if (tag.equals("LABOR_CODE_BY_TYPE")) {
            r = toolMapper.getLaborCodeByType(input);
        }
        else if (tag.equals("COAL_MINE_FORSHORT")) {
            r = toolMapper.getCoalMineForShortByInput(input);
        }
        else if (tag.equals("SHIP_REC_ID")) {
            r = toolMapper.getShipRecIdByInput(input);
        } else if (tag.equals("SHIPS")) {
            r = toolMapper.getShipsByInput(input);
        } else if (tag.equals("SAMPLE_CODE")) {
            r = toolMapper.getSampleCodeByInput(input);
        } else if (tag.equals("SAMPLE_CODE_ZJ")) {
            r = toolMapper.getSampleCodeByInputZj(input);
        } else if (tag.equals("SAMPLING_CODE")) {
            r = toolMapper.getSamplingCodeByInput(input);
        }else if (tag.equals("carId")) {
            r = toolMapper.getcarIdByInput(input);
        }else if (tag.equals("dischargeforecast")) {
            r = toolMapper.getDischargeforecastByInput(input);
        }else if (tag.equals("BOTTLE")) {
            r = toolMapper.getBottleCntByInput(input);
        }else if (tag.equals("batch_no")) {
            r = toolMapper.getBatchNoByInput(input);
        }else if (tag.equals("CAR_ID")) {
            r = toolMapper.getCarIdByInput(input);
        }else if (tag.equals("goodInfo")) {
            r = toolMapper.getGoodInfoByInput(input);
        }
        return r;
    }

    //获取新的火车车次
    public String generateNewTrainNo(){
        return toolMapper.generateNewTrainNo();
    }

    @Override
    public String getCarNo(String sampleCode,String tag) {
        FunctionEntity functionEntity = new FunctionEntity();
        functionEntity.setInPrm1(sampleCode);
        functionEntity.setInPrm2(tag);
        return toolMapper.getCarNo(functionEntity);
    }

    @Override
    public String checkPrivilege(PrivilegeEntity privilegeEntity) {
        return toolMapper.checkPrivilege(privilegeEntity);
    }

    @Override
    public List<DictionaryTableEntity> qryDictionaryTableByType(String type) {
        DictionaryTableEntity dictionaryTableEntity = new DictionaryTableEntity();
        dictionaryTableEntity.setType(type);
        return this.qryDictionaryTable(dictionaryTableEntity);
    }

    @Override
    public List<DictionaryTableEntity> qryDictionaryTablePrior(String id, String type) {
        DictionaryTableEntity dictionaryTableEntity = new DictionaryTableEntity();
        dictionaryTableEntity.setType(type);
        dictionaryTableEntity.setPriorId(id);
        return this.qryDictionaryTable(dictionaryTableEntity);
    }

    @Override
    public List<DictionaryTableEntity> qryDictionaryTableById(String id) {
        DictionaryTableEntity dictionaryTableEntity = new DictionaryTableEntity();
        dictionaryTableEntity.setId(id);
        return this.qryDictionaryTable(dictionaryTableEntity);
    }

    @Override
    public List<DictionaryTableEntity> qryDictionaryTableByParentId(String parentId) {
        DictionaryTableEntity dictionaryTableEntity = new DictionaryTableEntity();
        dictionaryTableEntity.setParentId(parentId);
        return this.qryDictionaryTable(dictionaryTableEntity);
    }

    @Override
    public List<DictionaryTableEntity> qryDictionaryTable(DictionaryTableEntity dictionaryTableEntity) {
        return dictionaryTableMapper.qryDictionaryTableList(dictionaryTableEntity);
    }

    @Override
    public void addDictionaryTable(DictionaryTableEntity dictionaryTableEntity) {
        dictionaryTableEntity.setDictionaryTableId(commonService.getNextval("SEQ_COMMON_ID"));
        dictionaryTableMapper.addDictionaryTable(dictionaryTableEntity);
    }

    @Override
    public void modiDictionaryTable(DictionaryTableEntity dictionaryTableEntity) {
        dictionaryTableMapper.modiDictionaryTable(dictionaryTableEntity);
    }

//    public void loadConstant(){
//        List<ConstantEntity> list = toolMapper.loadConstant();
//        for (ConstantEntity ce : list){
//            ConstantBAK.GLOBAL_CONSTANT_MAP.put(ce.getKey(), ce.getValue());
//        }
//    }

    public List<MsgPushEntity> qryPushMsgList(List<String> msgIdsList){
        List<MsgPushEntity> list = toolMapper.qryPushMsgList(msgIdsList);
        return list;
    }

    public void updatePushMsg(MsgPushEntity msgPushEntity){
        toolMapper.updatePushMsg(msgPushEntity);
    }


    //插入接口日志关键数据
    public void insertIntfLog(BizLogEntity logEntity){
        CommonEntity commonEntity = new CommonEntity();
        commonEntity.setSeqName("SEQ_INTF_LOG");
        String logId = toolMapper.getSeqNextval(commonEntity);
        logEntity.setLogId(logId);
        toolMapper.insertIntfLog(logEntity);
        toolMapper.insertIntfLogExt(logEntity);
    }

    @Override
    public String getWorkMode(String typeField) {
        FunctionEntity functionEntity = new FunctionEntity();
        functionEntity.setInPrm1(typeField);
        return toolMapper.getWorkMode(functionEntity);
    }

    //获取参数上下限值hxl
    public String getLimitValue(String typeField, String defaultValue){
        FunctionEntity functionEntity = new FunctionEntity();
        functionEntity.setInPrm1(typeField);
        functionEntity.setInPrm2(defaultValue);
        return toolMapper.getLimitValue(functionEntity);
    }
}
