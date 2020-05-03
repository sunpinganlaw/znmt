package org.gxz.znrl.service.impl;

import org.gxz.znrl.common.baseservice.BaseService;
import org.gxz.znrl.entity.DeviceBroadEntity;
import org.gxz.znrl.entity.DeviceErrEntity;
import org.gxz.znrl.mapper.MonitorMapper;
import org.gxz.znrl.mapper.UserMapper;
import org.gxz.znrl.service.IExcelService;
import org.gxz.znrl.util.ExcelFileTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Rubbish on 2015/8/14.
 */
@Service("excelService")
@Transactional
@SuppressWarnings("unchecked")
public class ExcelServiceImpl extends BaseService implements IExcelService {

    private static final Logger logger = LoggerFactory.getLogger(ToolServiceImpl.class);

    /**
     * 查询结果信息导出
     * @param map
     * @return Map<String, Object> 是否成功信息
     */
    @Override
    public Map<String, Object> exportToExcel(Map<String, Object> map) {
        Map<String, Object> results = new HashMap<String, Object>();
        try {
            SimpleDateFormat formatter=new SimpleDateFormat( "yyyyMMdd");
            String time =formatter.format(new Date());
            String fileHome = ExcelFileTool.getFileHome();
            String fileName = "EXPORT_"+time+"_"+map.get("userId").toString();

            String separator = System.getProperty("file.separator");
            //存放导出文件的文件夹名
            String relativePath =
                    "exportfile"+separator+map.get("userId").toString()+separator+String.valueOf(System.currentTimeMillis())+ separator;

            String absolutePath = fileHome + relativePath;
            //用来下载文件
            map.put("relativePath",relativePath);
            //用来生成文件
            map.put("absolutePath",absolutePath);
            map.put("fileName",fileName);
            map.put("xlsFile",absolutePath+fileName+".xls");
            map.put("zipFile",absolutePath+fileName+".zip");
            map.put("downloadFile",relativePath+fileName+".zip");

            logger.debug("查询结果信息导出入参:", map);
            results =  exportToExcelTotal(map);
            logger.debug("查询结果信息导出处理结果:", results);
            results.put("filePath",relativePath+fileName+".zip");
            results.put("fileName",fileName+".zip");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导出EXCEL异常", e);
        }
        return results;
    }

    /**
     * 查询结果信息导出
     * @param map
     * @return Map<String, Object> 是否成功信息
     */
    public Map<String,Object> exportToExcelTotal(Map<String, Object> map) {
        Map<String, Object> retJson = new HashMap<String, Object>();
        String[] excelHeader = (String[])map.get("excelHeader");
        String[] columnsName = (String[])map.get("columnsName");
        String rescode = "1";
        String resMsg = "处理失败";
        retJson.put("resCode", rescode);
        retJson.put("resMsg", resMsg);
        //取数据条数
        int totalCount = 0;
        try{
            logger.debug("导出数据信息查询入参{}", map);
            totalCount = baseDao.selectOne(map.get("countSql").toString(), map);
            Map<String,Object> retMap = getDataAndGenerateFile(map,totalCount,excelHeader,columnsName);
            //压缩文件
            if(ExcelFileTool.zipFiles((File)retMap.get("excelRealFile"),new File((String)retMap.get("zipFile")))){
                retJson.put("resCode","0");
                retJson.put("downloadFile",(String)map.get("downloadFile"));
                retJson.put("resMsg","导出文件成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("计算导出EXCEL总数异常", e);
        }
        return retJson;
    }

    /**
     * 查询结果信息导出生成导出文件
     *
     * @param map
     *
     * @return Map<String, Object> 是否成功信息
     */
    @SuppressWarnings("unchecked")
    private Map<String,Object> getDataAndGenerateFile(Map<String,Object> map, int totalCount,String[] excelHeader,String[] columnsName) throws Exception {
        int beginIndex;
        int endIndex;

        int PAGESIZE = 5000;
        //计算页数
        int residue = totalCount % PAGESIZE;
        int division = totalCount / PAGESIZE;
        int pageCount = residue == 0 ? division : division + 1;

        try {
            if (pageCount == 0) {
                pageCount = pageCount + 1;
            }
            //页索引
            for (int pageIndx = 1; pageIndx <= pageCount; pageIndx++) {
                beginIndex = PAGESIZE * (pageIndx - 1) + 1;
                endIndex = (pageIndx == pageCount) ? totalCount : (PAGESIZE * pageIndx);

                map.put("beginRowIndex", String.valueOf(beginIndex));
                map.put("endRowIndex", String.valueOf(endIndex));

                logger.debug("导出数据信息查询结果入参{}", map);

                List<Object> qureyResults = baseDao.selectList(map.get("listSql").toString(), map);
                logger.debug("导出数据信息查询结果数据记录数:", qureyResults.size());

                if (map.get("entityName") != null && !"".equals(map.get("entityName"))) {
                    ExcelFileTool.generateExcelDataFileByList(map, excelHeader, columnsName, qureyResults);
                } else {
                    List mapList = new ArrayList<HashMap>();
                    for (Object d : qureyResults) {
                        mapList.add(ExcelFileTool.bean2Map(d));
                    }
                    ExcelFileTool.generateExcelDataFile(map, excelHeader, columnsName, mapList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("生成创建EXCEL异常", e);
        }
        return map;
    }
}
