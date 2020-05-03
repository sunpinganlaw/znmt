package org.gxz.znrl.entity;

/**
 * Created by Rubbish on 2015/6/30.
 */
public class WorkModeTypeEntity {

    private Integer typeId;

    private Integer parentTypeId;

    private String typeName;

    private String showType;

    private String dataLoadType;

    private String dataUrl;

    private String unit;

    private Integer showColumn;

    private Integer dependTypeId;

    private String dependValue;

    private String workModeValue;

    public String getWorkModeValue() {
        return workModeValue;
    }

    public void setWorkModeValue(String workModeValue) {
        this.workModeValue = workModeValue;
    }

    public Integer getDependTypeId() {
        return dependTypeId;
    }

    public void setDependTypeId(Integer dependTypeId) {
        this.dependTypeId = dependTypeId;
    }

    public String getDependValue() {
        return dependValue;
    }

    public void setDependValue(String dependValue) {
        this.dependValue = dependValue;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(Integer parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getDataLoadType() {
        return dataLoadType;
    }

    public void setDataLoadType(String dataLoadType) {
        this.dataLoadType = dataLoadType;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getShowColumn() {
        return showColumn;
    }

    public void setShowColumn(Integer showColumn) {
        this.showColumn = showColumn;
    }
}
