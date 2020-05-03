package org.gxz.znrl.entity;

import java.io.Serializable;

/**
 * Created by Rubbish on 2015/7/18.
 */
public class TableColumnByColumn implements Serializable {
    private String showCol;

    private String tableName;

    private String condCol;

    private String condValue;

    private String condOperate;

    private String condType;

    private int condCnt;

    public String getShowCol() {
        return showCol;
    }

    public void setShowCol(String showCol) {
        this.showCol = showCol;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCondCol() {
        return condCol;
    }

    public void setCondCol(String condCol) {
        this.condCol = condCol;
    }

    public String getCondValue() {
        return condValue;
    }

    public void setCondValue(String condValue) {
        this.condValue = condValue;
    }

    public String getCondOperate() {
        return condOperate;
    }

    public void setCondOperate(String condOperate) {
        this.condOperate = condOperate;
    }

    public String getCondType() {
        return condType;
    }

    public void setCondType(String condType) {
        this.condType = condType;
    }

    public int getCondCnt() {
        return condCnt;
    }

    public void setCondCnt(int condCnt) {
        this.condCnt = condCnt;
    }
}
