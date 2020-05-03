package org.gxz.znrl.util;

import org.gxz.znrl.entity.LabDataUploadEntity;
import org.gxz.znrl.entity.SamplingRptEntity;
import org.gxz.znrl.service.ILaboryService;
import org.gxz.znrl.service.IMonitorService;
import org.gxz.znrl.service.IRptService;
import org.gxz.znrl.service.IToolService;
import org.gxz.znrl.shiro.ShiroUser;
import org.gxz.znrl.viewModel.GridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by wangz on 2016-9-27.
 * 江南，对于0.2mm待取样的化验样，自动发送取样到化验室的指令到远光存查样柜,处理线程
 */
@Component
public class Yg02mmAutoToLabUtil extends SpringBeanAutowiringSupport{

    protected Logger logger = LoggerFactory.getLogger(getClass());
    //每次之间的上传间隔，每次对所有设备进行轮询上传，默认60s来一次，如果配置了，则按配置的来
    private long AUTO_PROCESS_INTERVAL = 10; //默认10分钟

    @Autowired
    private IMonitorService monitorService;

    @Autowired
    public IToolService iToolService;

    //单例模式获取该类的实例
    private static Yg02mmAutoToLabUtil labUtilSingleton;

    public static Yg02mmAutoToLabUtil getInstance(){
        if(labUtilSingleton == null){
            labUtilSingleton = new Yg02mmAutoToLabUtil();
        }
        return labUtilSingleton;
    }

    private Yg02mmAutoToLabUtil(){
        String labUploadInterval = Constant.getConstVal("YG02MM_AUTO_TO_LAB_INTERVAL");
        if (labUploadInterval != null && !labUploadInterval.equals("")) {
            AUTO_PROCESS_INTERVAL = Long.parseLong(labUploadInterval);
        }
    }


    //处理数据
    private void processSendYG() {
        try {
            monitorService.saveYg02mmAutoToLab();
        } catch(Throwable e) {
            e.printStackTrace();
            logger.error("处理发生异常："+e.getMessage());
        }
    }

    //线程定时调用远光取样接口
    class Yg02mmAutoToLabUtilThread extends Thread {
        public void run() {
            while (true) {
                try {
                    logger.info("开始处理");
                    if("Y".equalsIgnoreCase(iToolService.getWorkMode("YG02MM_AUTO_UPLOAD"))){
                        processSendYG();
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
