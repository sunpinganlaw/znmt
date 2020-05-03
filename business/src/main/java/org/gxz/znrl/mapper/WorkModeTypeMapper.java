package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.WorkModeTypeCommit;
import org.gxz.znrl.entity.WorkModeTypeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Rubbish on 2015/6/30.
 */
@Repository
public interface WorkModeTypeMapper {

    public List<WorkModeTypeEntity> qryWorkModeTypeList(WorkModeTypeEntity workModeTypeEntity);

    //用于大武口参数配置的权限分离
    public List<WorkModeTypeEntity> qryWorkModeTypeList4DWK(WorkModeTypeEntity workModeTypeEntity);

    public  void addSystemWorkModeLog(WorkModeTypeCommit WorkModeTypeCommit);

    public  void addSystemWorkMode(WorkModeTypeCommit WorkModeTypeCommit);

    public  void modiSystemWorkMode(WorkModeTypeCommit WorkModeTypeCommit);

    public WorkModeTypeCommit qrySystemWorkMode(WorkModeTypeCommit WorkModeTypeCommit);

}
