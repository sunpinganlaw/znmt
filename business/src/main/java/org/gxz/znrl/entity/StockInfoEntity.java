package org.gxz.znrl.entity;

public class StockInfoEntity {
    private String stockNo;


    private String stockName;


    public String getStockHeight() {
        return stockHeight;
    }

    public void setStockHeight(String stockHeight) {
        this.stockHeight = stockHeight;
    }

    public String getStockArea() {
        return stockArea;
    }

    public void setStockArea(String stockArea) {
        this.stockArea = stockArea;
    }

    private String stockHeight;

    private String stockArea;

    private String stockType;

    private String remark;

    private String forShort;

    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }


    public String getForShort() {
        return forShort;
    }

    public void setForShort(String forShort) {
        this.forShort = forShort;
    }



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}