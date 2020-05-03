package org.gxz.znrl.util;

import org.gxz.znrl.entity.BizLogEntity;
import org.gxz.znrl.service.IToolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 应用启动时候初始化执行的初始化动作
 * Created by xieyt on 15-08-19.
 */
public class InitServlet extends HttpServlet {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    public void init() throws ServletException {
        //存入spring上下文
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());

        //加载常量配置到内存
        //IToolService toolService  = (IToolService) context.getBean("toolService");
        //toolService.loadConstant();
        Constant constant = new Constant();
        constant.loadConst();

        //取用例子： Constant.getConstVal("YG_CABINET_WS_ADDR");
        //根据配置决定是否启动化验室自动上传程序
        if (Constant.getConstVal("LAB_AUTO_UPLOAD") != null && Constant.getConstVal("LAB_AUTO_UPLOAD").equalsIgnoreCase("ON")) {
            Thread labAutoUploadThread = LabUtil.getInstance().new LabAutoUploadThread();
            labAutoUploadThread.start();
        }

        //根据配置决定是否启动(江南试点)，对于0.2mm待取样的化验样，自动发送取样到化验室的指令到远光存查样柜,处理线程
        if (Constant.getConstVal("YG02MM_AUTO_UPLOAD") != null && Constant.getConstVal("YG02MM_AUTO_UPLOAD").equalsIgnoreCase("ON")) {
            Thread yg02mmAutoToLabUtilThread = Yg02mmAutoToLabUtil.getInstance().new Yg02mmAutoToLabUtilThread();
            yg02mmAutoToLabUtilThread.start();
        }

        //logger.debug("YG_CABINET_ALL_INFO_AUTO="+ Constant.getConstVal("YG_CABINET_ALL_INFO_AUTO"));
        //根据配置决定是否启动远光柜子的自动请求全量信息的Webservice接口,处理线程
        if (Constant.getConstVal("YG_CABINET_ALL_INFO_AUTO") != null && Constant.getConstVal("YG_CABINET_ALL_INFO_AUTO").equalsIgnoreCase("ON")) {
            Thread ygCabinetAllInfoAutoGetUtilThread = YgCabinetAllInfoAutoGetUtil.getInstance().new YgCabinetAllInfoAutoGetUtilThread();
            ygCabinetAllInfoAutoGetUtilThread.start();
        }

        //启动异步日志入库线程
        Thread logThread = LogUtil.getInstance().new LogWorkerThread();
        logThread.start();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.init();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
