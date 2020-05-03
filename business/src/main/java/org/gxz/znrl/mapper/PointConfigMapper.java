package org.gxz.znrl.mapper;

import org.gxz.znrl.entity.ComboboxOptionEntity;
import org.gxz.znrl.entity.PointConfigEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointConfigMapper {

    //查询channelName.deviceName的配置信息
    public List<PointConfigEntity> qryPointCfgInfo(PointConfigEntity pointConfigEntity);

    //操作point_cfg增删改csv
    public void operaPointCfg(PointConfigEntity pointCfgEntity);

    //获取下拉框里的数据
    public List<ComboboxOptionEntity> getChannelList();

    //删除
    void delPointCfgByTag(PointConfigEntity pointCfgEntity) throws  Exception;

    //改
    //void updatePointCfg(PointConfigEntity pointCfgEntity) throws  Exception;

    //增
    void insertPointCfg(PointConfigEntity pointCfgEntity) throws  Exception;
}
