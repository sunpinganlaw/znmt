package org.gxz.znrl.service;


import org.gxz.znrl.entity.ComboboxOptionEntity;
import org.gxz.znrl.entity.PointConfigEntity;
import org.gxz.znrl.viewModel.GridModel;

import java.util.List;

public interface IPointConfigService {

    //查询channelName.deviceName的配置信息
    public GridModel qryPointCfgInfo(PointConfigEntity pointConfigEntity);

    //操作point_cfg增删改csv
    public void operaPointCfg(PointConfigEntity pointCfgEntity);

    //获取下拉框列表的数据
    public List<ComboboxOptionEntity> getChannelList();

    //删除
    void delPointCfgByTag(String prefix) throws  Exception;

    //增改
    void savePointCfgs(List<PointConfigEntity> pointCfgEntitys)throws Exception;
}
