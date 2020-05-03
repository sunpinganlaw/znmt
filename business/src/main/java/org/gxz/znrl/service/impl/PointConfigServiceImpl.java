package org.gxz.znrl.service.impl;

import org.gxz.znrl.entity.ComboboxOptionEntity;
import org.gxz.znrl.entity.PointConfigEntity;
import org.gxz.znrl.mapper.PointConfigMapper;
import org.gxz.znrl.service.IPointConfigService;
import org.gxz.znrl.viewModel.GridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("pointConfigService")
@Transactional
public class PointConfigServiceImpl implements IPointConfigService {

    @Autowired
    public PointConfigMapper pointCfgMapper;

    //查询channelName.deviceName的配置信息
    public GridModel qryPointCfgInfo(PointConfigEntity pointConfigEntity){
        List<PointConfigEntity> list = pointCfgMapper.qryPointCfgInfo(pointConfigEntity);
        //设置返回结果
        GridModel m = new GridModel();
        m.setRows(list);
        return m;
    }

    //操作point_cfg增删改csv
    public void operaPointCfg(PointConfigEntity pointCfgEntity){
        pointCfgMapper.operaPointCfg(pointCfgEntity);
    }

    public List<ComboboxOptionEntity> getChannelList(){
        //查询下拉选项框
        List<ComboboxOptionEntity> r = pointCfgMapper.getChannelList();
        return r;
    }

    //删除
    public void delPointCfgByTag(String prefix) throws Exception {
        PointConfigEntity pointCfgEntity = new PointConfigEntity();
        pointCfgEntity.setPrefix (prefix);
        pointCfgMapper.delPointCfgByTag(pointCfgEntity);
    }

    //增改
    public void savePointCfgs(List<PointConfigEntity> pointCfgEntitys) throws  Exception{
        for (PointConfigEntity pointCfgEntity : pointCfgEntitys) {
            pointCfgMapper.insertPointCfg(pointCfgEntity);
        }
    }
}
