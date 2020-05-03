package org.gxz.znrl.util;

import org.gxz.znrl.service.IMonitorService;
import org.gxz.znrl.service.IToolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Created by wangz on 2016-11-22.
 * 最早在南宁电厂上线使用，远光，定期发起 [3.6.10	样品全量盘点下达(31200)]
 */
@Component
public class YgCabinetAllInfoAutoGetUtil extends SpringBeanAutowiringSupport{

    protected Logger logger = LoggerFactory.getLogger(getClass());
    //每次请求间隔
    private long AUTO_PROCESS_INTERVAL = 10; //默认10分钟

    @Autowired
    private IMonitorService monitorService;

    @Autowired
    public IToolService iToolService;

    //单例模式获取该类的实例
    private static YgCabinetAllInfoAutoGetUtil labUtilSingleton;

    public static YgCabinetAllInfoAutoGetUtil getInstance(){
        if(labUtilSingleton == null){
            labUtilSingleton = new YgCabinetAllInfoAutoGetUtil();
        }
        return labUtilSingleton;
    }

    private YgCabinetAllInfoAutoGetUtil(){
        String labUploadInterval = Constant.getConstVal("YG_CABINET_ALL_INFO_AUTO_GET_INTERVAL");
        if (labUploadInterval != null && !labUploadInterval.equals("")) {
            AUTO_PROCESS_INTERVAL = Long.parseLong(labUploadInterval);
        }
    }


    //处理数据
    private void processSendWebserviceYG() {
        try {
            monitorService.callYGIntfForCode31200();
        } catch(Throwable e) {
            e.printStackTrace();
            logger.error("处理发生异常："+e.getMessage());
        }
    }

    //线程定时调用远光Webservice接口
    class YgCabinetAllInfoAutoGetUtilThread extends Thread {
        public void run() {
            while (true) {
                try {
                    logger.info("开始处理");
                    //logger.debug("YG_CABINET_ALL_INFO_AUTO_GET"+iToolService.getWorkMode("YG_CABINET_ALL_INFO_AUTO_GET")+",AUTO_PROCESS_INTERVAL="+AUTO_PROCESS_INTERVAL);
                    if("Y".equalsIgnoreCase(iToolService.getWorkMode("YG_CABINET_ALL_INFO_AUTO_GET"))){
                        processSendWebserviceYG();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        this.sleep(AUTO_PROCESS_INTERVAL*60*1000);
                        //this.sleep(60*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
