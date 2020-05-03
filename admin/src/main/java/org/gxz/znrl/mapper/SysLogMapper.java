package org.gxz.znrl.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by hujy on 18-5-24.
 */
@Repository
public interface SysLogMapper {
    public int insertSysLog(Map<String,Object> log);
}
