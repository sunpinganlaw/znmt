package org.gxz.znrl.service.impl;

import org.gxz.znrl.common.baseservice.BaseService;
import org.gxz.znrl.mapper.SysLogMapper;
import org.gxz.znrl.service.SysLogService;
import org.gxz.znrl.shiro.SecurityUtils;
import org.gxz.znrl.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by hujy on 18-5-24.
 */
@Service("sysLogService")
@Transactional
public class SysLogServiceImpl extends BaseService implements SysLogService{

    @Autowired
    public SysLogMapper sysLogMapper;

    @Override
    public void saveSysLog(Map<String,Object> params) {
        try {
            sysLogMapper.insertSysLog(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
