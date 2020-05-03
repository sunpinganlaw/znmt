package org.gxz.znrl.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.gxz.znrl.entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.gxz.znrl.service.SysLogService;
import org.gxz.znrl.shiro.ShiroUser;
import org.gxz.znrl.util.Constant;
import org.gxz.znrl.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * hujy
 */
@Aspect
@Component
public class SysLogAspect {

	protected Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

	@Autowired
	private SysLogService sysLogService;

	@Pointcut("execution(* org.gxz.znrl.controller..*.*(..))")
	public void logPointCut(){

	}

	@After("logPointCut()")
	public void afterMethod(JoinPoint joinPoint){
		try{
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();
			//请求的类名
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = signature.getName();
			//请求的参数
			Object[] args = joinPoint.getArgs();
			//获取request
			HttpServletRequest request = HttpUtil.getHttpServletRequest();

			StringBuilder requestMethod = new StringBuilder();
			String requestMethodStr = requestMethod.append(className).append(".").append(methodName).toString();

			if(Constant.menuMap.containsKey(requestMethodStr)){
				Map<String,Object> params = new HashMap<String,Object>();
				//用户名
				Subject subject = SecurityUtils.getSubject();
				if(subject != null){
					ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
					if(shiroUser != null && shiroUser.getUser() != null ){
						User user = shiroUser.getUser();
						long loginId = user.getId();
						String loginName = user.getUsername();
						params.put("log_op_code",loginId);
						params.put("log_op_name",loginName);
						params.put("host_ip",HttpUtil.getRemoteHost(request));
						String menuValue = Constant.menuMap.get(requestMethodStr);
						String[] memuButtonArr = menuValue.split(",");
						if(memuButtonArr.length == 1){
							params.put("oper_menu",memuButtonArr[0]);
						}else if(memuButtonArr.length == 2){
							params.put("oper_menu",memuButtonArr[0]);
							params.put("oper_button",memuButtonArr[1]);
						}
						String reqParams = "";
						try{
							Enumeration em = request.getParameterNames();
							StringBuilder str = new StringBuilder("{");
							while (em.hasMoreElements()) {
								String name = (String) em.nextElement();
								String value = request.getParameter(name);
								str.append("\"").append(name).append("\"").append(":").append(value).append(",");
							}
							str.append("}");
							params.put("request_params",str.toString());
						}catch (Exception e){
							if(args != null && args.length > 0){
								StringBuilder requestParams = new StringBuilder();
								for(int i = 0 ;i < args.length; i++){
									requestParams.append(args[i].toString()).append(",");
								}
								reqParams = requestParams.toString();
							}
							params.put("request_params", reqParams);
						}



						params.put("request_method",requestMethod.append("()").toString());
						sysLogService.saveSysLog(params);
					}
				}
			}

		}catch (Exception e){
			logger.error(ExceptionUtils.getStackTrace(e));
		}


	}
	
}
