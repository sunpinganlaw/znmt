package org.gxz.znrl.service;

import org.gxz.znrl.entity.*;
import org.gxz.znrl.viewModel.GridModel;

import java.util.List;

/**
 * Created by xieyt on 14-11-18.
 */
public interface IToolService {
    //获取下拉框列表的数据
    public List<ComboboxOptionEntity> getComboboxOption(String tag);

    //获取下拉框里的数据，根据输入模糊匹配
    public List<ComboboxOptionEntity> getComboboxOptionByInput(String tag, String input);

    //获取新的火车车次
    public String generateNewTrainNo();

    public String getCarNo(String sampleCode,String tag);

    public String checkPrivilege(PrivilegeEntity privilegeEntity);

    public List<DictionaryTableEntity> qryDictionaryTableByType(String type);

    public List<DictionaryTableEntity> qryDictionaryTablePrior(String id,String type);

    public List<DictionaryTableEntity> qryDictionaryTableById(String id);

    public List<DictionaryTableEntity> qryDictionaryTableByParentId(String id);

    public List<DictionaryTableEntity> qryDictionaryTable(DictionaryTableEntity dictionaryTableEntity);

    public  void addDictionaryTable(DictionaryTableEntity dictionaryTableEntity);

    public  void modiDictionaryTable(DictionaryTableEntity dictionaryTableEntity);

    //public void loadConstant();

    public List<MsgPushEntity> qryPushMsgList(List<String> msgIdsList);

    public void updatePushMsg(MsgPushEntity msgPushEntity);

    //插入接口日志关键数据
    public void insertIntfLog(BizLogEntity logEntity);

    //获取SYSTEM_WORK_MODE中的配置参数
    public String getWorkMode(String typeField);

    //获取参数上下限值hxl
    public String getLimitValue(String typeField, String defaultValue);
}
