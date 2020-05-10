package org.gxz.znrl.service.impl;

import org.apache.commons.lang.StringUtils;
import org.gxz.znrl.common.basedao.BaseDao;
import org.gxz.znrl.common.baseservice.BaseService;
import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.*;
import org.gxz.znrl.mapper.*;
import org.gxz.znrl.service.CommonService;
import org.gxz.znrl.service.IConfigService;
import org.gxz.znrl.viewModel.GridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin-rubbissh on 2015/1/1.
 */
@Service("configService")
@Transactional
public class ConfigServiceImpl extends BaseService implements IConfigService {

    @Autowired
    public MachinWorkModeMapper machinWorkModeMapper;

    @Autowired
    public WorkModeTypeMapper workModeTypeMapper;

    @Autowired
    public VendorMineRelMapper vendorMineRelMapper;

    @Autowired
    public DictionaryTableMapper dictionaryTableMapper;

    @Autowired
    private CommonService commonService;
    @Override
    public Page coalTypeFindList(Page page, CoalType coalType) {
        CoalTypeCriteria criteria = new CoalTypeCriteria();
        CoalTypeCriteria.Criteria cri = criteria.createCriteria();
        if(coalType != null){
            if(StringUtils.isNotBlank(coalType.getCoalNo())){
                cri.andCoalNoEqualTo(coalType.getCoalNo());
            }
            if(StringUtils.isNotBlank(coalType.getCoalName())){
                cri.addCriterion(" COAL_TYPE LIKE '%"+coalType.getCoalName()+"%' ");
            }
            if(StringUtils.isNotBlank(coalType.getForShort())){
                cri.addCriterion(" FOR_SHORT LIKE '%"+coalType.getForShort()+"%' ");
            }
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        page.setCount(baseDao.getMapper(CoalTypeMapper.class).countByExample(criteria));
        List<CoalType> list = baseDao.selectByPage("org.gxz.znrl.mapper.CoalTypeMapper." + BaseDao.SELECT_BY_EXAMPLE, criteria, page);
        return page.setRows(list);
    }

    @Override
    public Page coalMineFindList(Page page, CoalMine coalMine) {
        CoalMineCriteria criteria = new CoalMineCriteria();
        CoalMineCriteria.Criteria cri = criteria.createCriteria();
        if(coalMine != null){
            if(StringUtils.isNotBlank(coalMine.getMineNo())){
                cri.andMineNoEqualTo(coalMine.getMineNo());
            }
            if(StringUtils.isNotBlank(coalMine.getMineName())){
                cri.addCriterion(" MINE_NAME LIKE '%"+coalMine.getMineName()+"%' ");
            }
            if(StringUtils.isNotBlank(coalMine.getForShort())){
                cri.addCriterion(" FOR_SHORT LIKE '%"+coalMine.getForShort()+"%' ");
            }
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        page.setCount(baseDao.getMapper(CoalMineMapper.class).countByExample(criteria));
        List<CoalMine> list = baseDao.selectByPage("org.gxz.znrl.mapper.CoalMineMapper." + BaseDao.SELECT_BY_EXAMPLE, criteria, page);
        return page.setRows(list);
    }

    @Override
    public Page carrierInfoFindList(Page page, CarrierInfo carrierInfo) {
        CarrierInfoCriteria criteria = new CarrierInfoCriteria();
        CarrierInfoCriteria.Criteria cri = criteria.createCriteria();
        if(carrierInfo != null){
            if(StringUtils.isNotBlank(carrierInfo.getCarrierNo())){
                cri.andCarrierNoEqualTo(carrierInfo.getCarrierNo());
            }
            if(StringUtils.isNotBlank(carrierInfo.getCarrierName())){
                cri.addCriterion(" CARRIER_NAME LIKE '%"+carrierInfo.getCarrierName()+"%' ");
            }
            if(StringUtils.isNotBlank(carrierInfo.getForShort())){
                cri.addCriterion(" FOR_SHORT LIKE '%"+carrierInfo.getForShort()+"%' ");
            }
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        page.setCount(baseDao.getMapper(CarrierInfoMapper.class).countByExample(criteria));
        List<CarrierInfo> list = baseDao.selectByPage("org.gxz.znrl.mapper.CarrierInfoMapper." + BaseDao.SELECT_BY_EXAMPLE, criteria, page);
        return page.setRows(list);
    }

    @Override
    public Page vendorInfoFindList(Page page, VendorInfo vendorInfo) {
        VendorInfoCriteria criteria = new VendorInfoCriteria();
        VendorInfoCriteria.Criteria cri = criteria.createCriteria();
        if(vendorInfo != null){
            if(StringUtils.isNotBlank(vendorInfo.getVenNo())){
                cri.andVenNoEqualTo(vendorInfo.getVenNo());
            }
            if(StringUtils.isNotBlank(vendorInfo.getVenName())){
                cri.addCriterion(" VEN_NAME LIKE '%"+vendorInfo.getVenName()+"%' ");
            }
            if(StringUtils.isNotBlank(vendorInfo.getForShort())){
                cri.addCriterion(" FOR_SHORT LIKE '%"+vendorInfo.getForShort()+"%' ");
            }
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        page.setCount(baseDao.getMapper(VendorInfoMapper.class).countByExample(criteria));
        List<VendorInfo> list = baseDao.selectByPage("org.gxz.znrl.mapper.VendorInfoMapper." + BaseDao.SELECT_BY_EXAMPLE, criteria, page);
        return page.setRows(list);
    }

    @Override
    public Page railroadCarWeightFindList(Page page, RailroadCarWeight railroadCarWeight) {
        RailroadCarWeightCriteria criteria = new RailroadCarWeightCriteria();
        RailroadCarWeightCriteria.Criteria cri = criteria.createCriteria();
        if(railroadCarWeight != null){
            if(StringUtils.isNotBlank(railroadCarWeight.getRailroadCarType())){
                cri.addCriterion(" RAILROAD_CAR_TYPE LIKE '%"+railroadCarWeight.getRailroadCarType()+"%' ");
            }
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        page.setCount(baseDao.getMapper(RailroadCarWeightMapper.class).countByExample(criteria));
        List<VendorInfo> list = baseDao.selectByPage("org.gxz.znrl.mapper.RailroadCarWeightMapper." + BaseDao.SELECT_BY_EXAMPLE, criteria, page);
        return page.setRows(list);
    }

    @Override
    public RailroadCarWeight getByRailroadCarType(String typeCode) {
        RailroadCarWeightCriteria criteria = new RailroadCarWeightCriteria();
        RailroadCarWeightCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(typeCode)){
            cri.andRailroadCarTypeEqualTo(typeCode);
        }
        RailroadCarWeight railroadCarWeight =  baseDao.selectOne("org.gxz.znrl.mapper.RailroadCarWeightMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return railroadCarWeight;
    }

    @Override
    public RailroadCarWeight getByRailroadCarId(String id) {
        return baseDao.getMapper(RailroadCarWeightMapper.class).selectByPrimaryKey(Integer.valueOf(id));
    }

    @Override
    public void saveRailroadCarWeight(RailroadCarWeight railroadCarWeight) {
        railroadCarWeight.setRailroadCarId((int)commonService.getNextval("SEQ_USER_ID"));
        baseDao.getMapper(RailroadCarWeightMapper.class).insertSelective(railroadCarWeight);
    }

    @Override
    public void updateRailroadCarWeight(RailroadCarWeight railroadCarWeight) {
        baseDao.getMapper(RailroadCarWeightMapper.class).updateByPrimaryKey(railroadCarWeight);
    }

    @Override
    public void coalTypeDelete(String id) {
        baseDao.getMapper(CoalTypeMapper.class).deleteByPrimaryKey(id);
    }

    @Override
    public void coalMineDelete(String id) {
        VendorMineRelEntity vendorMineRelEntity = new VendorMineRelEntity();
        vendorMineRelEntity.setMineNo(id);
        vendorMineRelMapper.delVendorMineRel(vendorMineRelEntity);
        baseDao.getMapper(CoalMineMapper.class).deleteByPrimaryKey(id);
    }

    @Override
    public void carrierInfoDelete(String id) {
        baseDao.getMapper(CarrierInfoMapper.class).deleteByPrimaryKey(id);
    }

    @Override
    public void vendorInfoDelete(String id) {
        baseDao.getMapper(VendorInfoMapper.class).deleteByPrimaryKey(id);
    }

    @Override
    public void railroadCarWeightDelete(int id) {
        baseDao.getMapper(RailroadCarWeightMapper.class).deleteByPrimaryKey(id);
    }

    @Override
    public CoalType getByCoalNo(String coalNo) {
        CoalTypeCriteria criteria = new CoalTypeCriteria();
        CoalTypeCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(coalNo)){
            cri.andCoalNoEqualTo(coalNo);
        }
        CoalType coalType =  baseDao.selectOne("org.gxz.znrl.mapper.CoalTypeMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return coalType;
    }

    @Override
    public CoalType getByCoalName(String coalName) {
        CoalTypeCriteria criteria = new CoalTypeCriteria();
        CoalTypeCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(coalName)){
            cri.andCoalNameEqualTo(coalName);
        }
        CoalType coalType =  baseDao.selectOne("org.gxz.znrl.mapper.CoalTypeMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return coalType;
    }

    @Override
    public void saveCoalType(CoalType coalType) {
        baseDao.getMapper(CoalTypeMapper.class).insertSelective(coalType);
    }

    @Override
    public void updateCoalType(CoalType coalType) {
        baseDao.getMapper(CoalTypeMapper.class).updateByPrimaryKey(coalType);
    }


    @Override
    public CoalMine getByMineNo(String code) {
        CoalMineCriteria criteria = new CoalMineCriteria();
        CoalMineCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(code)){
            cri.andMineNoEqualTo(code);
        }
        criteria.setDistinct(true);

        CoalMine coalMine =  baseDao.selectOne("org.gxz.znrl.mapper.CoalMineMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return coalMine;
    }

    @Override
    public CoalMine getByMineName(String name) {
        CoalMineCriteria criteria = new CoalMineCriteria();
        CoalMineCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(name)){
            cri.andMineNameEqualTo(name);
        }
        CoalMine coalMine =  baseDao.selectOne("org.gxz.znrl.mapper.CoalMineMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return coalMine;
    }

    @Override
    public void saveCoalMine(CoalMine coalMine) {
        String mineNo = String.valueOf(commonService.getNextval("SEQ_COMMON_CONFIG_ID"));
        coalMine.setMineNo(mineNo);
        baseDao.getMapper(CoalMineMapper.class).insertSelective(coalMine);
        if (coalMine.getVenNo() != null && !"".equals(coalMine.getVenNo())) {
            VendorMineRelEntity vendorMineRelEntity = new VendorMineRelEntity();
            vendorMineRelEntity.setVendorMineId(commonService.getNextval("SEQ_COMMON_ID"));
            vendorMineRelEntity.setVenNo(coalMine.getVenNo());
            vendorMineRelEntity.setMineNo(mineNo);
            vendorMineRelEntity.setOpCode(coalMine.getOpCode());
            vendorMineRelMapper.addVendorMineRel(vendorMineRelEntity);
        }
    }

    @Override
    public void updateCoalMine(CoalMine coalMine) {
        if (coalMine.getVenNo() != null && !"".equals(coalMine.getVenNo())) {
            VendorMineRelEntity vendorMineRelEntity = new VendorMineRelEntity();
            vendorMineRelEntity.setMineNo(coalMine.getMineNo());
            vendorMineRelEntity.setOpCode(coalMine.getOpCode());
            List<VendorMineRelEntity> list = vendorMineRelMapper.qryVendorMineList(vendorMineRelEntity);
            if (list.size() > 0) {
                vendorMineRelEntity.setVenNo(coalMine.getVenNo());
                vendorMineRelMapper.updateVendorMineRelByMineNo(vendorMineRelEntity);
            } else {
                vendorMineRelEntity.setVendorMineId(commonService.getNextval("SEQ_COMMON_ID"));
                vendorMineRelEntity.setVenNo(coalMine.getVenNo());
                vendorMineRelEntity.setMineNo(coalMine.getMineNo());
                vendorMineRelEntity.setOpCode(coalMine.getOpCode());
                vendorMineRelMapper.addVendorMineRel(vendorMineRelEntity);
            }
        }
        baseDao.getMapper(CoalMineMapper.class).updateByPrimaryKey(coalMine);
    }

    @Override
    public CarrierInfo getByCarrierNo(String code) {
        CarrierInfoCriteria criteria = new CarrierInfoCriteria();
        CarrierInfoCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(code)){
            cri.andCarrierNoEqualTo(code);
        }
        CarrierInfo carrierInfo =  baseDao.selectOne("org.gxz.znrl.mapper.CarrierInfoMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return carrierInfo;
    }

    @Override
    public CarrierInfo getByCarrierName(String name) {
        CarrierInfoCriteria criteria = new CarrierInfoCriteria();
        CarrierInfoCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(name)){
            cri.andCarrierNameEqualTo(name);
        }
        CarrierInfo carrierInfo =  baseDao.selectOne("org.gxz.znrl.mapper.CarrierInfoMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return carrierInfo;
    }

    @Override
    public void saveCarrierInfo(CarrierInfo carrierInfo) {
        baseDao.getMapper(CarrierInfoMapper.class).insertSelective(carrierInfo);
    }

    @Override
    public void updateCarrierInfo(CarrierInfo carrierInfo) {
        baseDao.getMapper(CarrierInfoMapper.class).updateByPrimaryKey(carrierInfo);
    }

    @Override
    public VendorInfo getByVenNo(String code) {
        VendorInfoCriteria criteria = new VendorInfoCriteria();
        VendorInfoCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(code)){
            cri.andVenNoEqualTo(code);
        }


        VendorInfo vendorInfo =  baseDao.selectOne("org.gxz.znrl.mapper.VendorInfoMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return vendorInfo;
    }

    @Override
    public VendorInfo getByVenName(String name) {
        VendorInfoCriteria criteria = new VendorInfoCriteria();
        VendorInfoCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(name)){
            cri.andVenNameEqualTo(name);
        }


        VendorInfo vendorInfo =  baseDao.selectOne("org.gxz.znrl.mapper.VendorInfoMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return vendorInfo;
    }

    @Override
    public void saveVendorInfo(VendorInfo vendorInfo) {
        baseDao.getMapper(VendorInfoMapper.class).insertSelective(vendorInfo);
    }

    @Override
    public void updateVendorInfo(VendorInfo vendorInfo) {
        baseDao.getMapper(VendorInfoMapper.class).updateByPrimaryKey(vendorInfo);
    }

    @Override
    public GridModel qryMachinWorkMode(MachinWorkModeEntity machinWorkModeEntity) {
        //查询总记录数
        Integer totalCnt = machinWorkModeMapper.qryMachinWorkModeCnt(machinWorkModeEntity);
        //查询本次页面的结果集
        List<MachinWorkModeEntity> machinWorkModeList = machinWorkModeMapper.qryMachinWorkMode(machinWorkModeEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(machinWorkModeList);
        m.setTotal(totalCnt);
        return m;
    }

    @Override
    public MachinWorkModeEntity qryMachinWorkModeById(MachinWorkModeEntity machinWorkModeEntity) {
        //查询本次页面的结果集
        MachinWorkModeEntity machinWorkMode = machinWorkModeMapper.qryMachinWorkModeById(machinWorkModeEntity);
        return machinWorkMode;
    }

    @Override
    public void modifyMachinWorkMode(MachinWorkModeEntity machinWorkModeEntity) {
        machinWorkModeMapper.modifyMachinWorkMode(machinWorkModeEntity);
    }

    @Override
    public List<WorkModeTypeEntity> qryWorkModeTypeList(WorkModeTypeEntity workModeTypeEntity) {
        return workModeTypeMapper.qryWorkModeTypeList(workModeTypeEntity);
    }

    //用于大武口参数配置的权限分离
    @Override
    public List<WorkModeTypeEntity> qryWorkModeTypeList4DWK(WorkModeTypeEntity workModeTypeEntity) {
        return workModeTypeMapper.qryWorkModeTypeList4DWK(workModeTypeEntity);
    }

    @Override
    public void addSystemWorkMode(WorkModeTypeCommit workModeTypeCommit) {
        workModeTypeMapper.addSystemWorkModeLog(workModeTypeCommit);
        workModeTypeMapper.addSystemWorkMode(workModeTypeCommit);
    }

    @Override
    public void modiSystemWorkMode(WorkModeTypeCommit workModeTypeCommit) {
        workModeTypeMapper.addSystemWorkModeLog(workModeTypeCommit);
        workModeTypeMapper.modiSystemWorkMode(workModeTypeCommit);
    }

    @Override
    public WorkModeTypeCommit qrySystemWorkMode(WorkModeTypeCommit workModeTypeCommit) {
        return workModeTypeMapper.qrySystemWorkMode(workModeTypeCommit);
    }


    @Override
    public List<VendorMineRelEntity> qryVendorMineList(VendorMineRelEntity vendorMineRelEntity) {
        return vendorMineRelMapper.qryVendorMineList(vendorMineRelEntity);
    }

    @Override
    public List<VendorMineRelEntity> qryNotExistVendorInfoList(VendorMineRelEntity vendorMineRelEntity) {
        return vendorMineRelMapper.qryNotExistVendorInfoList(vendorMineRelEntity);
    }

    @Override
    public List<VendorMineRelEntity> qryNotExistMineInfoList(VendorMineRelEntity vendorMineRelEntity) {
        return vendorMineRelMapper.qryNotExistMineInfoList(vendorMineRelEntity);
    }

    @Override
    public void addVendorMineRel(VendorMineRelEntity vendorMineRelEntity) {
        String [] objectArr = vendorMineRelEntity.getMultObjectId().split(",");
        String [] objectOldArr = vendorMineRelEntity.getMultObjectIdOld().split(",");
        String addStr = "";
        String delStr = "";
        int existTag = 0;//0不存在
        for (String x : objectArr) {
            existTag = 0;
            for (String y : objectOldArr) {
                if (x.equals(y)) {
                    existTag = 1;
                    break;
                }
            }
            if (existTag == 0) {
                addStr+="".equals(addStr)?x:","+x;
            }
        }
        for (String x : objectOldArr) {
            existTag = 0;
            for (String y : objectArr) {
                if (x.equals(y)) {
                    existTag = 1;
                    break;
                }
            }
            if (existTag == 0) {
                delStr+="".equals(delStr)?x:","+x;
            }
        }
        if (!"".equals(addStr)) {
            for (String x : addStr.split(",")) {
                if ("1".equals(vendorMineRelEntity.getDoWhat())) {
                    vendorMineRelEntity.setMineNo(x);
                } else {
                    vendorMineRelEntity.setVenNo(x);
                }
                vendorMineRelEntity.setVendorMineId(commonService.getNextval("SEQ_COMMON_ID"));
                vendorMineRelMapper.addVendorMineRel(vendorMineRelEntity);
            }
        }
        if (!"".equals(delStr)) {
            for (String x : delStr.split(",")) {
                if ("1".equals(vendorMineRelEntity.getDoWhat())) {
                    vendorMineRelEntity.setMineNo(x);
                } else {
                    vendorMineRelEntity.setVenNo(x);
                }
                vendorMineRelMapper.delVendorMineRel(vendorMineRelEntity);
            }
        }
    }

    public String qryEditableDictionaryInfo(){
        return dictionaryTableMapper.qryEditableDictionaryInfo();
    }

    //编辑字典信息
    public void editDicInfo(DictionaryTableEntity dictionaryTableEntity){
        dictionaryTableMapper.editDicInfo(dictionaryTableEntity);
    }


    public Page goodInfoFindList(Page page, GoodInfoEntity goodInfo) {
        CoalTypeCriteria criteria = new CoalTypeCriteria();
        CoalTypeCriteria.Criteria cri = criteria.createCriteria();
        if(goodInfo != null){
            if(StringUtils.isNotBlank(goodInfo.getGoodNo())){
                cri.andCoalNoEqualTo(goodInfo.getGoodNo());
            }
            if(StringUtils.isNotBlank(goodInfo.getGoodName())){
                cri.addCriterion(" G_NAME LIKE '%"+goodInfo.getGoodName()+"%' ");
            }

        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        page.setCount(baseDao.getMapper(CoalTypeMapper.class).countByExample(criteria));
        List<CoalType> list = baseDao.selectByPage("org.gxz.znrl.mapper.GoodInfoMapper." + BaseDao.SELECT_BY_EXAMPLE, criteria, page);
        return page.setRows(list);
    }

    @Override
    public void goodInfoDelete(String id) {
        baseDao.getMapper(GoodInfoMapper.class).deleteByPrimaryKey(id);
    }


    @Override
    public GoodInfoEntity getByGoodNo(String goodNo) {
//        CoalTypeCriteria criteria = new CoalTypeCriteria();
//        CoalTypeCriteria.Criteria cri = criteria.createCriteria();
//        if(StringUtils.isNotBlank(goodNo)){
//            cri.andCoalNoEqualTo(goodNo);
//        }


        GoodInfoCriteria criteria = new GoodInfoCriteria();
        GoodInfoCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(goodNo)){
            cri.andGoodNoEqualTo(goodNo);
        }
        GoodInfoEntity GoodInfo =  baseDao.selectOne("org.gxz.znrl.mapper.GoodInfoMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return GoodInfo;
    }


    @Override
    public GoodInfoEntity getByGoodName(String goodName) {
//        CoalTypeCriteria criteria = new CoalTypeCriteria();
//////        CoalTypeCriteria.Criteria cri = criteria.createCriteria();
//////        if(StringUtils.isNotBlank(goodName)){
//////            cri.andCoalNameEqualTo(goodName);
//////        }
        GoodInfoCriteria criteria = new GoodInfoCriteria();
        GoodInfoCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(goodName)){
            cri.andGoodNameEqualTo(goodName);
        }

        GoodInfoEntity GoodInfo =  baseDao.selectOne("org.gxz.znrl.mapper.GoodInfoMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return GoodInfo;
    }


    @Override
    public void saveGoodInfoEntity(GoodInfoEntity  goodInfo) {
        baseDao.getMapper(GoodInfoMapper.class).insertSelective(goodInfo);
    }

    @Override
    public void updateGoodInfoEntity(GoodInfoEntity goodInfo) {
        baseDao.getMapper(GoodInfoMapper.class).updateByPrimaryKey(goodInfo);
    }


    //-----------------------------------------------------------------------------------------------------channleInfo_start----------------

    public Page channelInfoFindList(Page page, ChannelInfoEntity channelInfo) {
        ChannelInfoCriteria criteria = new ChannelInfoCriteria();
        ChannelInfoCriteria.Criteria cri = criteria.createCriteria();
        if(channelInfo != null){
            if(StringUtils.isNotBlank(channelInfo.getChannelNo())){
                cri.andChannelNoEqualTo(channelInfo.getChannelNo());
            }
            if(StringUtils.isNotBlank(channelInfo.getChannelName())){
                cri.addCriterion(" G_NAME LIKE '%"+channelInfo.getChannelName()+"%' ");
            }
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        page.setCount(baseDao.getMapper(ChannelInfoMapper.class).countByExample(criteria));
        List<ChannelInfoEntity> list = baseDao.selectByPage("org.gxz.znrl.mapper.ChannelInfoMapper." + BaseDao.SELECT_BY_EXAMPLE, criteria, page);
        return page.setRows(list);
    }

    @Override
    public void channelInfoDelete(String id) {
        baseDao.getMapper(ChannelInfoMapper.class).deleteByPrimaryKey(id);
    }


    @Override
    public ChannelInfoEntity getByChannelNo(String channelNo) {

        ChannelInfoCriteria criteria = new ChannelInfoCriteria();
        ChannelInfoCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(channelNo)){
            cri.andChannelNoEqualTo(channelNo);
        }
        ChannelInfoEntity ChannelInfo =  baseDao.selectOne("org.gxz.znrl.mapper.ChannelInfoMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return ChannelInfo;
    }


    @Override
    public ChannelInfoEntity getByChannelName(String channelName) {

        ChannelInfoCriteria criteria = new ChannelInfoCriteria();
        ChannelInfoCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(channelName)){
            cri.andChannelNameEqualTo(channelName);
        }

        ChannelInfoEntity ChannelInfo =  baseDao.selectOne("org.gxz.znrl.mapper.ChannelInfoMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return ChannelInfo;
    }


    @Override
    public void saveChannelInfoEntity(ChannelInfoEntity  ChannelInfo) {
        baseDao.getMapper(ChannelInfoMapper.class).insertSelective(ChannelInfo);
    }

    @Override
    public void updateChannelInfoEntity(ChannelInfoEntity ChannelInfo) {
        baseDao.getMapper(ChannelInfoMapper.class).updateByPrimaryKey(ChannelInfo);
    }



    //-----------------------------------------------------------------------------------------------------stockInfo_start----------------

    public Page stockInfoFindList(Page page, StockInfoEntity stockInfo) {
        StockInfoCriteria criteria = new StockInfoCriteria();
        StockInfoCriteria.Criteria cri = criteria.createCriteria();
        if(stockInfo != null){
            if(StringUtils.isNotBlank(stockInfo.getStockNo())){
                cri.andStockNoEqualTo(stockInfo.getStockNo());
            }
            if(StringUtils.isNotBlank(stockInfo.getStockName())){
                cri.addCriterion(" G_NAME LIKE '%"+stockInfo.getStockName()+"%' ");
            }
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        page.setCount(baseDao.getMapper(StockInfoMapper.class).countByExample(criteria));
        List<StockInfoEntity> list = baseDao.selectByPage("org.gxz.znrl.mapper.StockInfoMapper." + BaseDao.SELECT_BY_EXAMPLE, criteria, page);
        return page.setRows(list);
    }

    @Override
    public void stockInfoDelete(String id) {
        baseDao.getMapper(StockInfoMapper.class).deleteByPrimaryKey(id);
    }


    @Override
    public StockInfoEntity getByStockNo(String stockNo) {

        StockInfoCriteria criteria = new StockInfoCriteria();
        StockInfoCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(stockNo)){
            cri.andStockNoEqualTo(stockNo);
        }
        StockInfoEntity stockInfo =  baseDao.selectOne("org.gxz.znrl.mapper.StockInfoMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return stockInfo;
    }


    @Override
    public StockInfoEntity getByStockName(String stockName) {

        StockInfoCriteria criteria = new StockInfoCriteria();
        StockInfoCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(stockName)){
            cri.andStockNameEqualTo(stockName);
        }

        StockInfoEntity StockInfo =  baseDao.selectOne("org.gxz.znrl.mapper.StockInfoMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return StockInfo;
    }


    @Override
    public void saveStockInfoEntity(StockInfoEntity  stockInfo) {
        baseDao.getMapper(StockInfoMapper.class).insertSelective(stockInfo);
    }

    @Override
    public void updateStockInfoEntity(StockInfoEntity stockInfo) {
        baseDao.getMapper(StockInfoMapper.class).updateByPrimaryKey(stockInfo);
    }
    //-----------------------------------------------------------------------------------------------------cutomerInfo_start----------------
    @Override
    public Page customerInfoFindList(Page page, CustomerInfo customerInfo) {
        CustomerInfoCriteria criteria = new CustomerInfoCriteria();
        CustomerInfoCriteria.Criteria cri = criteria.createCriteria();
        if(customerInfo != null){
            if(StringUtils.isNotBlank(customerInfo.getCustomerNo())){
                cri.andCusNoEqualTo(customerInfo.getCustomerNo());
            }
            if(StringUtils.isNotBlank(customerInfo.getCustomerName())){
                cri.addCriterion(" CUSTOMER_NAME LIKE '%"+customerInfo.getCustomerName()+"%' ");
            }
            if(StringUtils.isNotBlank(customerInfo.getForShort())){
                cri.addCriterion(" FOR_SHORT LIKE '%"+customerInfo.getForShort()+"%' ");
            }
        }
        if(page != null && page.getSort() != null && page.getOrder() != null){
            criteria.setOrderByClause(page.getSort() + " " + page.getOrder());
        }
        page.setCount(baseDao.getMapper(CustomerInfoMapper.class).countByExample(criteria));
        List<CustomerInfo> list = baseDao.selectByPage("org.gxz.znrl.mapper.CustomerInfoMapper." + BaseDao.SELECT_BY_EXAMPLE, criteria, page);
        return page.setRows(list);
    }

    @Override
    public CustomerInfo getByCusNo(String code) {
        CustomerInfoCriteria criteria = new CustomerInfoCriteria();
        CustomerInfoCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(code)){
            cri.andCusNoEqualTo(code);
        }


        CustomerInfo customerInfo =  baseDao.selectOne("org.gxz.znrl.mapper.CustomerInfoMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return customerInfo;
    }

    @Override
    public CustomerInfo getByCusName(String name) {
        CustomerInfoCriteria criteria = new CustomerInfoCriteria();
        CustomerInfoCriteria.Criteria cri = criteria.createCriteria();
        if(StringUtils.isNotBlank(name)){
            cri.andCusNameEqualTo(name);
        }


        CustomerInfo customerInfo =  baseDao.selectOne("org.gxz.znrl.mapper.CustomerInfoMapper."+BaseDao.SELECT_BY_EXAMPLE, criteria);
        return customerInfo;
    }

    @Override
    public void saveCustomerInfo(CustomerInfo customerInfo) {
        baseDao.getMapper(CustomerInfoMapper.class).insertSelective(customerInfo);
    }

    @Override
    public void updateCustomerInfo(CustomerInfo customerInfo) {
        baseDao.getMapper(CustomerInfoMapper.class).updateByPrimaryKey(customerInfo);
    }
    public void customerInfoDelete(String id) {
        baseDao.getMapper(CustomerInfoMapper.class).deleteByPrimaryKey(id);
    }

}
