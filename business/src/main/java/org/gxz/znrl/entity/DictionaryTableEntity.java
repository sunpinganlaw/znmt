package org.gxz.znrl.entity;

/**
 * Created by Rubbish on 2015/7/20.
 */
public class DictionaryTableEntity {

    private Long dictionaryTableId;

    private String id;

    private String name;

    private String type;

    private String parentId;

    private String remark;

    private String priorId;

    private String createDate;

    private String status;

    private String dicId;

    private String actionTag;

    private String resCode;

    private String resMsg;

    private String jsonString;

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getActionTag() {
        return actionTag;
    }

    public void setActionTag(String actionTag) {
        this.actionTag = actionTag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDicId() {
        return dicId;
    }

    public void setDicId(String dicId) {
        this.dicId = dicId;
    }

    public Long getDictionaryTableId() {
        return dictionaryTableId;
    }

    public void setDictionaryTableId(Long dictionaryTableId) {
        this.dictionaryTableId = dictionaryTableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPriorId() {
        return priorId;
    }

    public void setPriorId(String priorId) {
        this.priorId = priorId;
    }
}
