package org.gxz.znrl.entity;

import java.util.List;

/**
 * Created by Rubbish on 2015/6/30.
 */
public class WorkModeTypeResult {

    private Integer parentTypeId;

    private List<WorkModeTypeEntity> workModeTypeList;

    private List<WorkModeTypeResult> workModeTypeResult;

    public List<WorkModeTypeResult> getWorkModeTypeResult() {
        return workModeTypeResult;
    }

    public void setWorkModeTypeResult(List<WorkModeTypeResult> workModeTypeResult) {
        this.workModeTypeResult = workModeTypeResult;
    }

    public Integer getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(Integer parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public List<WorkModeTypeEntity> getWorkModeTypeList() {
        return workModeTypeList;
    }

    public void setWorkModeTypeList(List<WorkModeTypeEntity> workModeTypeList) {
        this.workModeTypeList = workModeTypeList;
    }
}
