package org.gxz.znrl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 处理JasperReport报表查看功能。
 * Created by sunhf on 2017/6/13.
 */
@Controller
@RequestMapping("/business/rptView")
public class RptViewController extends BaseAction {
    /**
     * url转向报表HTML页面。
     *
     * @param pageName 报表html页面名称
     * @return
     */
    @RequestMapping(value = "/getReportJson/{pageName}", method ={ RequestMethod.GET, RequestMethod.POST})
    public String showJsonRptPage(@PathVariable String pageName) {
        return "/reportJson/" + pageName;
    }

    /**
     * 加载Jasper报表内容。使用Json数据填充
     *
     * @param request
     * @return
     * @Param jasperFile 报表对应的jasper文件名
     */
    @RequestMapping(value = "/loadJasperJson/{jasperFile}", method = {RequestMethod.GET, RequestMethod.POST})
    public void loadJasperJson(@PathVariable String jasperFile,
                               HttpServletRequest request) throws JRException {
        //报表编译之后生成的.jasper文件的存放位置
        String ctxpath = request.getContextPath();
        String classPath = request.getSession().getServletContext().getRealPath("/");
        String FileDirectory = classPath + "reportJasper";
        String jasperFileName = FileDirectory +  File.separator + jasperFile.split(",")[0] + ".jasper";
        File reportFile = new File(jasperFileName);
        String reportPrintPath = FileDirectory +  File.separator + jasperFile.split(",")[0] + ".jrprint";
        // 获取jsonParam参数
        String jsonParam = request.getParameter("jsonParam");
        if (jasperFile.split(",").length == 1) {
            //设置参数
            try {
                HashMap<String, Object> paramsMap = new HashMap<String, Object>();
                paramsMap.put(JsonQueryExecuterFactory.JSON_INPUT_STREAM, new ByteArrayInputStream(jsonParam.getBytes("UTF-8")));
                paramsMap.put(JsonQueryExecuterFactory.JSON_LOCALE, Locale.ENGLISH);
//                paramsMap.put(JsonQueryExecuterFactory.JSON_NUMBER_PATTERN, "0.00");
                paramsMap.put(JRParameter.REPORT_LOCALE, Locale.US);
                //执行报表程序
                JasperFillManager.fillReportToFile(reportFile.getPath(), paramsMap);
                JasperExportManager.exportReportToHtmlFile(reportPrintPath); // 生成html文件
                JasperExportManager.exportReportToPdfFile(reportPrintPath);  // 生成pdf文件
                // 生成excel文件
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setExporterInput(new SimpleExporterInput(reportPrintPath));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(FileDirectory +  File.separator + jasperFile.split(",")[0] + ".xls"));
                exporter.exportReport();
                // 转向报表html页面
                response.sendRedirect(ctxpath + "/reportJasper/" + jasperFile.split(",")[0] + ".html"); //此处的myreport是报表的名称
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        // 存在主子表
        else if (jasperFile.split(",").length > 1) {
            // 获取jsonParam参数
            JSONObject jsonParamObj = JSON.parseObject(jsonParam);
            String mainRptData = JSON.toJSONString(jsonParamObj.get("mainRptData"));
            String subRptData = JSON.toJSONString(jsonParamObj.get("subRptData"));
            //设置参数
            try {
                HashMap<String, Object> paramsMap = new HashMap<String, Object>();
                paramsMap.put(JsonQueryExecuterFactory.JSON_INPUT_STREAM, new ByteArrayInputStream(mainRptData.getBytes("UTF-8")));
                paramsMap.put(JsonQueryExecuterFactory.JSON_LOCALE, Locale.ENGLISH);
//                paramsMap.put(JsonQueryExecuterFactory.JSON_NUMBER_PATTERN, "0.00");
                paramsMap.put(JRParameter.REPORT_LOCALE, Locale.US);
                paramsMap.put("subRptData",subRptData);
                HashMap<String, Object> subParamsMap = new HashMap<String, Object>();
                //执行报表程序
                JasperFillManager.fillReportToFile(reportFile.getPath(), paramsMap);
                JasperExportManager.exportReportToHtmlFile(reportPrintPath); // 生成html文件
                JasperExportManager.exportReportToPdfFile(reportPrintPath);  // 生成pdf文件
                // 生成excel文件
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setExporterInput(new SimpleExporterInput(reportPrintPath));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(FileDirectory + File.separator + jasperFile.split(",")[0] + ".xls"));
                exporter.exportReport();
                // 转向报表html页面
                response.sendRedirect(ctxpath + "/reportJasper/" + jasperFile.split(",")[0] + ".html"); //此处的myreport是报表的名称
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 打印报表。使用JSON数据填充。
     */
    @RequestMapping(value = "/printJasperJson/{jasperFile}", method = {RequestMethod.GET, RequestMethod.POST})
    public void printReportJson(@PathVariable String jasperFile) throws SQLException, JRException {
        //报表编译之后生成的.jasper文件的存放位置
        String ctxpath = request.getContextPath();
        String classPath = request.getSession().getServletContext().getRealPath("/");
        String FileDirectory = classPath + "reportJasper";
        String jasperFileName = FileDirectory +  File.separator + jasperFile.split(",")[0] + ".jasper";
        File reportFile = new File(jasperFileName);
        String reportPrintPath = FileDirectory +  File.separator + jasperFile.split(",")[0] + ".jrprint";
        // 获取jsonParam参数
        String jsonParam = "{}"; //.getParameter("jsonParam");
        //设置参数
        try {
            InputStream pdfStream = new FileInputStream(new File(FileDirectory +  File.separator + jasperFile.split(",")[0] + ".pdf"));
            // 输出流
            PdfReader reader = new PdfReader(pdfStream);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(pdfStream.available());
            try {
                // 给pdf加上脚本实现自动打印
                StringBuffer script = new StringBuffer();
                script.append("this.print({bUI:false,bSilent:true,bShrinkToFit:false});");

                PdfStamper stamp = new PdfStamper(reader, bos);
                stamp.setViewerPreferences(PdfWriter.HideMenubar
                        | PdfWriter.HideToolbar | PdfWriter.HideWindowUI);
                stamp.addJavaScript(script.toString());
                stamp.close();
            } catch (DocumentException e) {
                logger.error(e.getMessage(), e.getCause());
            }
            // 输出pdf
            byte[] bytes = bos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control",
                        "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                ServletOutputStream ouputStream = response.getOutputStream();
                try {
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                } finally {
                    if (ouputStream != null) {
                        try {
                            ouputStream.close();
                        } catch (IOException ex) {
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出excel。使用Json数据源填充。
     */
    @RequestMapping(value = "/exportJasperJson/{jasperFile}", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportExcel(@PathVariable String jasperFile) {
        //报表查询时生成的Excel文件的存放位置
        String classPath = request.getSession().getServletContext().getRealPath("/");
        String FileDirectory = classPath + "reportJasper";
        try {
            // 设置下载文件头信息
            String exportFileName = "";
            response.setContentType("application/vnd.ms-excel");
            if (jasperFile.trim() != null && jasperFile != null) {
                exportFileName = jasperFile.split(",")[0] + ".xls";
            } else {
                exportFileName = "export.xls";
            }
            String fileName = new String(exportFileName.getBytes("gbk"), "utf-8");
            response.setHeader("Content-disposition", "attachment; filename="
                    + exportFileName);
            ServletOutputStream outputStream = response.getOutputStream();
            String excelFileName = FileDirectory +  File.separator + jasperFile.split(",")[0] + ".xls";
            // 开始导出
            InputStream in = null;
            try {
                in = new BufferedInputStream(new FileInputStream(excelFileName), 1024);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != outputStream) {
                    outputStream.flush();
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
