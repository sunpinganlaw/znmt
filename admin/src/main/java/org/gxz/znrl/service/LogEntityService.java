package org.gxz.znrl.service;

import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.LogEntity;
import org.gxz.znrl.log.LogLevel;

import java.util.List;


/** 
 * @description: 登录日志
 * @version 1.0
 * @author vincent
 * @createDate 2014-1-11;下午02:16:30
 */
public interface LogEntityService {

void save(LogEntity logEntity);
	
	LogEntity get(Long id);
	
	void update(LogEntity logEntity);
	
	void delete(Long id);
	
	List<LogEntity> findByLogLevel(LogLevel logLevel, Page page);
	
	List<LogEntity> findAll();
	
	List<LogEntity> findByExample(LogEntity logEntity, Page page);
	
}
