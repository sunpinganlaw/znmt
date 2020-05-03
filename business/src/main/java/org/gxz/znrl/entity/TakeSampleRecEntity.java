package org.gxz.znrl.entity;

/**
 * Created by xieyt on 15-1-21.
 */
public class TakeSampleRecEntity extends BaseEntity{

    private String sampleAreas;//

    private String carId;

    private String startTime;

    private String endTime;

    private String machineCode;


    public String getSampleAreas() {
        return sampleAreas;
    }

    public void setSampleAreas(String sampleAreas) {
        this.sampleAreas = sampleAreas;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

}
