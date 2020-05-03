package org.gxz.znrl.entity;

/**
 * 船运煤的entity
 * Created by xieyt on 15-09-30.
 */
public class ShipEntity {
    //船
    public String contractNo;  // 合同编号
    public String contractName;//合同名称
    public String shipId;
    public String shipName;
    public String shipEngName;
    public String shipCode;
    public String fixShipNo;
    public String cabinCnt;
    public String loadTun;
    public String totalTun;
    public String width;
    public String length;
    public String nationCd;
    public String companyNo;
    public String companyName;
    public String remark;

    //运输记录
    public String shipRecID;
    public String shipTransNo;
    public String carrierNo;
    public String carrierName;
    public String waybillNo;
    public String waterTun;
    public String receiverNo;
    public String receiverName;
    public String status;
    public String statusName;
    public String loadHours;
    public String unloadHours;
    public String startPortNo;
    public String finalPortNo;
    public String startPortName;
    public String finalPortName;
    public String startTime;
    public String estimateTime;
    public String factTime;
    public String norTime;
    public String arrangeTime;
    public String unloadTime;

    public String departTime;
    public String berthNo;
    public String opCode;
    public String updateCode;

    //货运记录
    public String recordNo;
    public String venNo;
    public String venName;

    public String tickQty;

    public String sampleType;//采样方式
    public String sampleTypeName;//采样方式
    public String isBatch;
    public String isBatchName;
    public String isCurrent;
    public String currentBatchNo;
    public String currentSampleCode;
    public String currentSamplingCode;
    public String batchNo;

    public String trainNo;
    public String batchTime;

    public String customerNo;
    public String customerName;
    public String shipType;
    public String shipTypeName;
    public String tradeType;
    public String tradeName;
    public String goodNo;
    public String goodName;
    public String goodTypeName;
    public String jsonStr;
    public String goodType;
    public String billNo;

    public String areaName;

    public String areaPosition;
    public String areaHeight;

    public String netQty;

    public String getNetQty() {
        return netQty;
    }

    public void setNetQty(String netQty) {
        this.netQty = netQty;
    }

    public String getLeftQty() {
        return leftQty;
    }

    public void setLeftQty(String leftQty) {
        this.leftQty = leftQty;
    }

    public String leftQty;

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public String areaNo;


    public String storageCode;
    public String storageName;


    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaPosition() {
        return areaPosition;
    }

    public void setAreaPosition(String areaPosition) {
        this.areaPosition = areaPosition;
    }

    public String getAreaHeight() {
        return areaHeight;
    }

    public void setAreaHeight(String areaHeight) {
        this.areaHeight = areaHeight;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }





    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getGoodNo() {
        return goodNo;
    }

    public void setGoodNo(String goodNo) {
        this.goodNo = goodNo;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }


    public String getShipTypeName() {
        return shipTypeName;
    }

    public void setShipTypeName(String shipTypeName) {
        this.shipTypeName = shipTypeName;
    }



    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }



    public String getGoodTypeName() {
        return goodTypeName;
    }

    public void setGoodTypeName(String goodTypeName) {
        this.goodTypeName = goodTypeName;
    }



    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }


    //查询条件用的
    public String actionTag;
    public String beginDate;
    public String endDate;
    public String jsonString;
    public String resCode;
    public String resMsg;

    private String multShipId;
    private String oldStatus;
    private String beltNo;
    private String shipRecIDWayTag;

    private String allNetQty ;
    private String uploadStatus ;

    private String lossQty;
    private String lossQtyPercent;

    public String getLossQty() {
        return lossQty;
    }

    public void setLossQty(String lossQty) {
        this.lossQty = lossQty;
    }

    public String getLossQtyPercent() {
        return lossQtyPercent;
    }

    public void setLossQtyPercent(String lossQtyPercent) {
        this.lossQtyPercent = lossQtyPercent;
    }

    public String getAllNetQty() {
        return allNetQty;
    }

    public void setAllNetQty(String allNetQty) {
        this.allNetQty = allNetQty;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getShipRecIDWayTag() {
        return shipRecIDWayTag;
    }

    public void setShipRecIDWayTag(String shipRecIDWayTag) {
        this.shipRecIDWayTag = shipRecIDWayTag;
    }

    public String getBatchTime() {
        return batchTime;
    }

    public void setBatchTime(String batchTime) {
        this.batchTime = batchTime;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }



    public String getBeltNo() {
        return beltNo;
    }

    public void setBeltNo(String beltNo) {
        this.beltNo = beltNo;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getMultShipId() {
        return multShipId;
    }

    public void setMultShipId(String multShipId) {
        this.multShipId = multShipId;
    }

    public String getNorTime() {
        return norTime;
    }

    public void setNorTime(String norTime) {
        this.norTime = norTime;
    }


    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCurrentBatchNo() {
        return currentBatchNo;
    }

    public void setCurrentBatchNo(String currentBatchNo) {
        this.currentBatchNo = currentBatchNo;
    }

    public String getCurrentSampleCode() {
        return currentSampleCode;
    }

    public void setCurrentSampleCode(String currentSampleCode) {
        this.currentSampleCode = currentSampleCode;
    }

    public String getCurrentSamplingCode() {
        return currentSamplingCode;
    }

    public void setCurrentSamplingCode(String currentSamplingCode) {
        this.currentSamplingCode = currentSamplingCode;
    }

    public String getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(String isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public String getIsBatch() {
        return isBatch;
    }

    public void setIsBatch(String isBatch) {
        this.isBatch = isBatch;
    }

    public String getIsBatchName() {
        return isBatchName;
    }

    public void setIsBatchName(String isBatchName) {
        this.isBatchName = isBatchName;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
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

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getActionTag() {
        return actionTag;
    }

    public void setActionTag(String actionTag) {
        this.actionTag = actionTag;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getStartPortName() {
        return startPortName;
    }

    public void setStartPortName(String startPortName) {
        this.startPortName = startPortName;
    }

    public String getFinalPortName() {
        return finalPortName;
    }

    public void setFinalPortName(String finalPortName) {
        this.finalPortName = finalPortName;
    }



    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipEngName() {
        return shipEngName;
    }

    public void setShipEngName(String shipEngName) {
        this.shipEngName = shipEngName;
    }

    public String getShipCode() {
        return shipCode;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode;
    }

    public String getFixShipNo() {
        return fixShipNo;
    }

    public void setFixShipNo(String fixShipNo) {
        this.fixShipNo = fixShipNo;
    }

    public String getCabinCnt() {
        return cabinCnt;
    }

    public void setCabinCnt(String cabinCnt) {
        this.cabinCnt = cabinCnt;
    }

    public String getLoadTun() {
        return loadTun;
    }

    public void setLoadTun(String loadTun) {
        this.loadTun = loadTun;
    }

    public String getTotalTun() {
        return totalTun;
    }

    public void setTotalTun(String totalTun) {
        this.totalTun = totalTun;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getNationCd() {
        return nationCd;
    }

    public void setNationCd(String nationCd) {
        this.nationCd = nationCd;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShipRecID() {
        return shipRecID;
    }

    public void setShipRecID(String shipRecID) {
        this.shipRecID = shipRecID;
    }

    public String getShipTransNo() {
        return shipTransNo;
    }

    public void setShipTransNo(String shipTransNo) {
        this.shipTransNo = shipTransNo;
    }

    public String getCarrierNo() {
        return carrierNo;
    }

    public void setCarrierNo(String carrierNo) {
        this.carrierNo = carrierNo;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getWaterTun() {
        return waterTun;
    }

    public void setWaterTun(String waterTun) {
        this.waterTun = waterTun;
    }

    public String getReceiverNo() {
        return receiverNo;
    }

    public void setReceiverNo(String receiverNo) {
        this.receiverNo = receiverNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getLoadHours() {
        return loadHours;
    }

    public void setLoadHours(String loadHours) {
        this.loadHours = loadHours;
    }

    public String getUnloadHours() {
        return unloadHours;
    }

    public void setUnloadHours(String unloadHours) {
        this.unloadHours = unloadHours;
    }

    public String getStartPortNo() {
        return startPortNo;
    }

    public void setStartPortNo(String startPortNo) {
        this.startPortNo = startPortNo;
    }

    public String getFinalPortNo() {
        return finalPortNo;
    }

    public void setFinalPortNo(String finalPortNo) {
        this.finalPortNo = finalPortNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(String estimateTime) {
        this.estimateTime = estimateTime;
    }

    public String getFactTime() {
        return factTime;
    }

    public void setFactTime(String factTime) {
        this.factTime = factTime;
    }

    public String getArrangeTime() {
        return arrangeTime;
    }

    public void setArrangeTime(String arrangeTime) {
        this.arrangeTime = arrangeTime;
    }

    public String getUnloadTime() {
        return unloadTime;
    }

    public void setUnloadTime(String unloadTime) {
        this.unloadTime = unloadTime;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public String getBerthNo() {
        return berthNo;
    }

    public void setBerthNo(String berthNo) {
        this.berthNo = berthNo;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getUpdateCode() {
        return updateCode;
    }

    public void setUpdateCode(String updateCode) {
        this.updateCode = updateCode;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getVenNo() {
        return venNo;
    }

    public void setVenNo(String venNo) {
        this.venNo = venNo;
    }

    public String getVenName() {
        return venName;
    }

    public void setVenName(String venName) {
        this.venName = venName;
    }

    public String getTickQty() {
        return tickQty;
    }

    public void setTickQty(String tickQty) {
        this.tickQty = tickQty;
    }





    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }
}
