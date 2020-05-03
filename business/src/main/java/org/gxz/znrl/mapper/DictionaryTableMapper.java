package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.DictionaryTableEntity;
import org.gxz.znrl.entity.WorkModeTypeCommit;
import org.gxz.znrl.entity.WorkModeTypeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Rubbish on 2015/6/30.
 */
@Repository
public interface DictionaryTableMapper {

    public List<DictionaryTableEntity> qryDictionaryTableList(DictionaryTableEntity dictionaryTableEntity);

    public  void addDictionaryTable(DictionaryTableEntity dictionaryTableEntity);

    public  void modiDictionaryTable(DictionaryTableEntity dictionaryTableEntity);

    public  String qryEditableDictionaryInfo();

    //编辑字典信息
    public void editDicInfo(DictionaryTableEntity dictionaryTableEntity);

}
