package org.gxz.znrl.entity;

/**
 * Created by rubish on 2015/4/27.
 */
public class TruckFuelEntranceRecordEntity {

    public String recordNo;//入厂序号

    public String mzQty;

    public String pzQty;

    public String tickQty;

    public String kdQty;

    public String kdBak;

    public String carId;

    public String getKdBak() {
        return kdBak;
    }

    public void setKdBak(String kdBak) {
        this.kdBak = kdBak;
    }
    
    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getMzQty() {
        return mzQty;
    }

    public void setMzQty(String mzQty) {
        this.mzQty = mzQty;
    }

    public String getPzQty() {
        return pzQty;
    }

    public void setPzQty(String pzQty) {
        this.pzQty = pzQty;
    }

    public String getTickQty() {
        return tickQty;
    }

    public void setTickQty(String tickQty) {
        this.tickQty = tickQty;
    }

    public String getKdQty() {
        return kdQty;
    }

    public void setKdQty(String kdQty) {
        this.kdQty = kdQty;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}
