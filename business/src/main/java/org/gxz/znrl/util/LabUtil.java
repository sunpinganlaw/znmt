package org.gxz.znrl.util;

import org.gxz.znrl.entity.LabDataUploadEntity;
import org.gxz.znrl.entity.SamplingRptEntity;
import org.gxz.znrl.service.ILaboryService;
import org.gxz.znrl.service.IRptService;
import org.gxz.znrl.viewModel.GridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xieyt on 2016-7-6.
 * 化验室自动上传数据处理线程
 */
@Component
public class LabUtil extends SpringBeanAutowiringSupport{
    protected Logger logger = LoggerFactory.getLogger(getClass());
    //每次之间的上传间隔，每次对所有设备进行轮询上传，默认60s来一次，如果配置了，则按配置的来
    private long UPLOAD_INTERVAL = 60;

    @Autowired
    private ILaboryService laboryService;

    @Autowired
    public IRptService rptService;

    //单例模式获取该类的实例
    private static LabUtil labUtilSingleton;

    public static LabUtil getInstance(){
        if(labUtilSingleton == null){
            labUtilSingleton = new LabUtil();
        }
        return labUtilSingleton;
    }

    private LabUtil(){
        String labUploadInterval = Constant.getConstVal("LAB_UPLOAD_INTERVAL");
        if (labUploadInterval != null && !labUploadInterval.equals("")) {
            UPLOAD_INTERVAL = Long.parseLong(labUploadInterval);
        }
    }


    //处理化验数据上传
    private void processLabs() {
        try {
            int preDays = 4;//默认抓取4天前到当天的化验数据

            if (Constant.getConstVal("LAB_UPLOAD_PREDAYS") != null && !Constant.getConstVal("LAB_UPLOAD_PREDAYS").equals("")) {
                preDays = Integer.parseInt(Constant.getConstVal("LAB_UPLOAD_PREDAYS"));
            }

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -preDays);
            String startTime = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
            System.out.println("######获取的化验编码开始时间######："+startTime);

            SamplingRptEntity samplingRptEntity = new SamplingRptEntity();
            samplingRptEntity.setStartTime(startTime);

            //设置分页信息并计算记录开始和结束值
            samplingRptEntity.setPageRowIndex(1, 100);

            GridModel gm = laboryService.qryLabDataUploadCfg(new LabDataUploadEntity());
            GridModel gridModel = rptService.qrySamplingRptList(samplingRptEntity);

            List<LabDataUploadEntity> list = gm.getRows();
            List<SamplingRptEntity> listLaborCode = gridModel.getRows();

            //把近几天的化验编码都捞出来走一把
            for (SamplingRptEntity s : listLaborCode) {
                for (LabDataUploadEntity entity : list) {
                    if(isServerActive(entity.getIp())){
                        entity.setLaborCode(s.getLaborCode());
                        try {
                            //开始执行上传
                            laboryService.uploadLabData(entity);
                            //结果
                            if (entity.getResCode() != null && entity.getResCode().equals("0")) {
                                System.out.println("化验室上传数据完成: ["+entity.getConfigName()+","+s.getLaborCode()+"]");
                            } else {
                                System.out.println("化验室上传数据未完成["+entity.getConfigName()+","+s.getLaborCode()+"]:"+entity.getResMsg());
                                logger.error("化验室上传数据未完成["+entity.getConfigName()+","+s.getLaborCode()+"]:"+entity.getResMsg());
                            }
                            Thread.sleep(500);
                        } catch (Exception e) {
                            logger.error("上传化验数据失败："+e.getMessage()+","+entity.getConfigName()+","+entity.getIp()+","+s.getLaborCode());
                        }
                    } else {
                        logger.error("化验设备网络连接不正常："+entity.getConfigName()+","+entity.getIp()+","+s.getLaborCode());
                    }
                }
            }

        } catch(Exception e) {
            logger.error("处理上传化验数据发生异常："+e.getMessage());
        }
    }


    //线程定时扫描各个化验设备并上传数据
    class LabAutoUploadThread extends Thread {
        public void run() {
            while (true) {
                try {
                    logger.info("开始上传化验数据");
                    processLabs();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        this.sleep(UPLOAD_INTERVAL*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //判断主机是否开机能ping通，在端口 7 上ping
    private boolean isServerActive(String ip){
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(3000);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
