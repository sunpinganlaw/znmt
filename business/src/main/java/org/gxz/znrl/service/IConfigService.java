package org.gxz.znrl.service;

import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.*;
import org.gxz.znrl.exception.ExistedException;
import org.gxz.znrl.viewModel.GridModel;

import java.util.List;
/**
 * Created by admin-rubbissh on 2015/1/1.
 */
public interface IConfigService {

    Page coalTypeFindList(Page page, CoalType coalType);

    Page coalMineFindList(Page page, CoalMine coalMine);

    Page carrierInfoFindList(Page page, CarrierInfo carrierInfo);

    Page vendorInfoFindList(Page page, VendorInfo vendorInfo);

    Page railroadCarWeightFindList(Page page, RailroadCarWeight railroadCarWeight);

    Page goodInfoFindList(Page page, GoodInfoEntity goodInfo);



    void coalTypeDelete(String id);

    void coalMineDelete(String id);

    void carrierInfoDelete(String id);

    void vendorInfoDelete(String id);

    void railroadCarWeightDelete(int id);

    void goodInfoDelete(String id);


    CoalType getByCoalNo(String code);

    CoalType getByCoalName(String name);

    void saveCoalType(CoalType coalType);

    void updateCoalType(CoalType coalType);


    GoodInfoEntity getByGoodNo(String code);
    GoodInfoEntity getByGoodName(String name);
    void saveGoodInfoEntity (GoodInfoEntity  goodInfo);
    void updateGoodInfoEntity (GoodInfoEntity goodInfo);



    ChannelInfoEntity getByChannelNo(String code);
    ChannelInfoEntity getByChannelName(String name);
    void saveChannelInfoEntity (ChannelInfoEntity  channelInfo);
    void updateChannelInfoEntity (ChannelInfoEntity channleInfo);
    void channelInfoDelete(String id);
     Page channelInfoFindList(Page page, ChannelInfoEntity channelInfo);


    StockInfoEntity getByStockNo(String code);
    StockInfoEntity getByStockName(String name);
    void saveStockInfoEntity (StockInfoEntity  stockInfo);
    void updateStockInfoEntity (StockInfoEntity stockInfo);
    void stockInfoDelete(String id);
    Page stockInfoFindList(Page page, StockInfoEntity stockInfo);


    CoalMine getByMineNo(String code);

    CoalMine getByMineName(String name);

    void saveCoalMine(CoalMine coalMine);

    void updateCoalMine(CoalMine coalMine);

    CarrierInfo getByCarrierNo(String code);

    CarrierInfo getByCarrierName(String name);

    void saveCarrierInfo(CarrierInfo carrierInfo);

    void updateCarrierInfo(CarrierInfo carrierInfo);

    VendorInfo getByVenNo(String code);

    VendorInfo getByVenName(String name);

    void saveVendorInfo(VendorInfo vendorInfo);

    void updateVendorInfo(VendorInfo vendorInfo);

    RailroadCarWeight getByRailroadCarType(String typeCode);

    RailroadCarWeight getByRailroadCarId(String id);

    void saveRailroadCarWeight(RailroadCarWeight railroadCarWeight);

    void updateRailroadCarWeight(RailroadCarWeight railroadCarWeight);

    public GridModel qryMachinWorkMode(MachinWorkModeEntity machinWorkModeEntity);

    public MachinWorkModeEntity qryMachinWorkModeById(MachinWorkModeEntity machinWorkModeEntity);

    public  void modifyMachinWorkMode(MachinWorkModeEntity machinWorkModeEntity);

    public List<WorkModeTypeEntity> qryWorkModeTypeList(WorkModeTypeEntity workModeTypeEntity);

    //用于大武口参数配置的权限分离
    public List<WorkModeTypeEntity> qryWorkModeTypeList4DWK(WorkModeTypeEntity workModeTypeEntity);

    public  void addSystemWorkMode(WorkModeTypeCommit workModeTypeCommit);

    public  void modiSystemWorkMode(WorkModeTypeCommit workModeTypeCommit);

    public WorkModeTypeCommit qrySystemWorkMode(WorkModeTypeCommit workModeTypeCommit);

    public List<VendorMineRelEntity> qryVendorMineList(VendorMineRelEntity vendorMineRelEntity);

    public List<VendorMineRelEntity> qryNotExistVendorInfoList(VendorMineRelEntity vendorMineRelEntity);

    public List<VendorMineRelEntity> qryNotExistMineInfoList(VendorMineRelEntity vendorMineRelEntity);

    public  void addVendorMineRel(VendorMineRelEntity vendorMineRelEntity);

    public  String qryEditableDictionaryInfo();

    //编辑字典信息
    public void editDicInfo(DictionaryTableEntity dictionaryTableEntity);
}
