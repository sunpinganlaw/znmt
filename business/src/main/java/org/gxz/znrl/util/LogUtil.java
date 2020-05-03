package org.gxz.znrl.util;

import org.gxz.znrl.entity.BizLogEntity;
import org.gxz.znrl.service.IToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by xieyt on 15-5-15.
 * 异步入库日志管理工具
 */
@Component
public class LogUtil extends SpringBeanAutowiringSupport{

    @Autowired
    private IToolService toolService;

    //日志存放队列-线程同步安全
    public static ConcurrentLinkedQueue<BizLogEntity> logQueue = new ConcurrentLinkedQueue<BizLogEntity>();

    //单例模式获取该类的实例
    private static LogUtil logUtilSingleton;

    public static LogUtil getInstance(){
        if(logUtilSingleton == null){
            logUtilSingleton = new LogUtil();
        }
        return logUtilSingleton;
    }

    private LogUtil(){}

    //写日志实例到队列
    public static void recordLog(BizLogEntity logEntity) {
        logQueue.add(logEntity);
    }


    private void insertIntfLog(BizLogEntity logEntity) throws Exception{
        try {
            toolService.insertIntfLog(logEntity);
        } catch (Exception e) {
            throw e;
        }
    }


    //处理日志入库
    private void recordToDb() {
        try {
            while(!logQueue.isEmpty()){
                insertIntfLog(logQueue.poll());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    //异步入库线程
    class LogWorkerThread extends Thread {
        public void run() {
            while (true) {
                try {
                    recordToDb();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        this.sleep(5*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
