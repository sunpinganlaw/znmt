package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.MachinWorkModeEntity;

import java.util.List;

/**
 * Created by rubish on 2015/5/12.
 */
public interface MachinWorkModeMapper {

    public Integer qryMachinWorkModeCnt(MachinWorkModeEntity machinWorkModeEntity);

    public List<MachinWorkModeEntity> qryMachinWorkMode(MachinWorkModeEntity machinWorkModeEntity);

    public MachinWorkModeEntity qryMachinWorkModeById(MachinWorkModeEntity machinWorkModeEntity);

    public  void modifyMachinWorkMode(MachinWorkModeEntity machinWorkModeEntity);

}
