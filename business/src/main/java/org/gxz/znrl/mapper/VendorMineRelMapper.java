package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.VendorMineRelEntity;
import org.gxz.znrl.entity.WorkModeTypeCommit;
import org.gxz.znrl.entity.WorkModeTypeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Rubbish on 2015/6/30.
 */
@Repository
public interface VendorMineRelMapper {

    public List<VendorMineRelEntity> qryVendorMineList(VendorMineRelEntity vendorMineRelEntity);

    public List<VendorMineRelEntity> qryNotExistVendorInfoList(VendorMineRelEntity vendorMineRelEntity);

    public List<VendorMineRelEntity> qryNotExistMineInfoList(VendorMineRelEntity vendorMineRelEntity);

    public  void addVendorMineRel(VendorMineRelEntity vendorMineRelEntity);

    public  void delVendorMineRel(VendorMineRelEntity vendorMineRelEntity);

    public  void updateVendorMineRelByVenNo(VendorMineRelEntity vendorMineRelEntity);

    public  void updateVendorMineRelByMineNo(VendorMineRelEntity vendorMineRelEntity);

}
