package org.gxz.znrl.util;

import org.gxz.znrl.entity.ConstantEntity;
import org.gxz.znrl.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xieyt on 14-12-10.
 * wz 10-27：开发客户签名功能需要在系统管理中读取常量，故从business移动到admin工程中，原类删除掉，同时修改InitServlet.java代码
 */
@Component
public class Constant extends SpringBeanAutowiringSupport {
    @Autowired
    private CommonService commonService;

    //websocket空闲会话保持时长（毫秒）
    public static long KEEP_IDLE_WS_SESSION_TIMEOUT = 36000*1000;

    //socket 监听端口
    public static int SOCKET_LISTEN_PORT = 2012;

    /*
    //系统常量配置存放容器
    public static HashMap<String, Object> GLOBAL_CONSTANT_MAP = new HashMap<>();

    //获取配置常量
    public static String getConstVal(String constKey) {
        Object o = GLOBAL_CONSTANT_MAP.get(constKey);
        return o == null ? "" : (String)o;
    }
    */
    //获取配置常量
    public static String getConstVal(String constKey) {
        return constantMap.get(constKey);
    }

    //系统静态参数内存存放
    public static HashMap<String, String> constantMap = new HashMap<String, String>();

    public void loadConst(){
        try {
            List<ConstantEntity> list = commonService.qryConstantCfgData();
            for (ConstantEntity c : list) {
                Constant.constantMap.put(c.getKey(),c.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<ConstantEntity> menuList = commonService.qryLogMenuButtonInfo();
            for (ConstantEntity c : menuList) {
                menuMap.put(c.getKey(),c.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String,String> menuMap = new HashMap<String,String>();

}

