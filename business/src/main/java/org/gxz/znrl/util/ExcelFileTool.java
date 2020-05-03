package org.gxz.znrl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;

import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.gxz.znrl.entity.SampleRptEntity;

/**
 * excel数据处理辅助类
 * @author
 * @version 2009-10-12
 */
public class ExcelFileTool {

	public static final int MSG_EXCEL_MAX_ROWS = 60000;//excel每页最大行数

	/**
	 * 定制EXCEL表格样式
	 *
	 * @param
	 * @return
	 */
	public static WritableCellFormat createStyle() throws Exception{
		//定制字体样式 20:代表字体大小 arial:字体 bold:是否加粗
		WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

		//定制EXCEL表格样式
		WritableCellFormat style = new WritableCellFormat(font);
		style = new WritableCellFormat(font);
		style.setAlignment(Alignment.CENTRE);
		style.setVerticalAlignment(VerticalAlignment.CENTRE);
		style.setBorder(Border.ALL, BorderLineStyle.THIN);
		style.setWrap(true);
		return style;
	}

	/**
	 * 报表标题样式
	 *
	 * @param
	 * @return
	 */
	public static WritableCellFormat createReportHeadStyle() throws Exception{
		//定制字体样式 20:代表字体大小 arial:字体 bold:是否加粗
		WritableFont font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		//定制EXCEL表格样式
		WritableCellFormat style = new WritableCellFormat(font);
		style = new WritableCellFormat(font);
		style.setAlignment(Alignment.CENTRE);
		style.setVerticalAlignment(VerticalAlignment.CENTRE);
		style.setBorder(Border.NONE, BorderLineStyle.NONE);
		style.setBackground(Colour.TAN);
		style.setWrap(true);
		return style;
	}

	/**
	 * 报表奇数行样式
	 *
	 * @param
	 * @return
	 */
	public static WritableCellFormat createRptOddLineStyle() throws Exception{
		//定制字体样式 20:代表字体大小 arial:字体 bold:是否加粗
		WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

		//定制EXCEL表格样式
		WritableCellFormat style = new WritableCellFormat(font);
		style = new WritableCellFormat(font);
		style.setAlignment(Alignment.CENTRE);
		style.setVerticalAlignment(VerticalAlignment.CENTRE);
		//style.setBorder(Border.ALL, BorderLineStyle.THIN);
		style.setBorder(Border.NONE, BorderLineStyle.NONE);
		style.setBackground(Colour.LIGHT_GREEN);
		style.setWrap(true);
		return style;
	}

	/**
	 * 报表偶数行样式
	 *
	 * @param
	 * @return
	 */
	public static WritableCellFormat createRptEvenLineStyle() throws Exception{
		//定制字体样式 20:代表字体大小 arial:字体 bold:是否加粗
		WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

		//定制EXCEL表格样式
		WritableCellFormat style = new WritableCellFormat(font);
		style = new WritableCellFormat(font);
		style.setAlignment(Alignment.CENTRE);
		style.setVerticalAlignment(VerticalAlignment.CENTRE);
		//style.setBorder(Border.ALL, BorderLineStyle.THIN);
		style.setBorder(Border.NONE, BorderLineStyle.NONE);
		style.setBackground(Colour.ICE_BLUE);
		style.setWrap(true);
		return style;
	}

	/**
	 * 生成excel文件
	 *
	 * @param absolutePath
	 * @return WritableWorkbook
	 */
	public static WritableWorkbook createExcelFile(Map<String,Object> map) throws Exception{
		//String finalFilePath = null;
		String filePath = (String)map.get("absolutePath");//绝对路径
		//String fileName = (String)map.get("fileName");
		File file = new File(filePath);

		WritableWorkbook book = null;
		try {
			if (!file.exists()){
				file.mkdirs();
			}

			//finalFilePath = String.valueOf(filePath + fileName + ".xls");
			File excelRealFile = new File((String)map.get("xlsFile"));

			Workbook wb = null;

			if (excelRealFile.exists()){
				// Excel获得文件,打开一个文件的副本，并且指定数据写回到原文件
				wb = Workbook.getWorkbook(excelRealFile);
				book = Workbook.createWorkbook(excelRealFile, wb);
			} else{
				book = Workbook.createWorkbook(excelRealFile);
			}
			map.put("excelRealFile",excelRealFile);
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return book;
	}



	/**
	 * 压缩文件
	 *
	 * @param
	 * @return
	 */
	public static boolean zipFiles(File srcfile, File zipfile){
		boolean zipRes = false;
		byte[] buf = new byte[2048];
		try{
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
			FileInputStream in = new FileInputStream(srcfile);
			out.putNextEntry(new ZipEntry(srcfile.getName()));
			int len;
			while ((len = in.read(buf)) > 0){
				out.write(buf, 0, len);
			}
			out.closeEntry();
			in.close();

			out.close();
			zipRes = true;
		} catch (IOException e){
			zipRes = false;
			e.printStackTrace();
		}
		return zipRes;
	}

	/**
	 * excel文件写入数据
	 *
	 * @param
	 * @return
	 */
	public static void writeExcelFile(List<Map<String , Object>> parNodeList,
									  String[] columnsName,
									  String[] excelHeader,
									  WritableWorkbook book) throws Exception{
		Label label = null;
		int columnsNum = columnsName.length;
		String elementText = null;
		String sheetNamePre = "sheet";
		String sheetName = null;
		WritableSheet sheet = null;
		int startRow = 1;
		WritableCellFormat style = createStyle();

		if (book.getNumberOfSheets() == 0){
			sheetName = sheetNamePre + (1 + book.getNumberOfSheets());
			sheet = book.createSheet(sheetName, book.getNumberOfSheets());
			for (int ii=0;ii<excelHeader.length;ii++){// 创建表头
				sheet.setColumnView(ii, 20);
				sheet.addCell(new Label(ii, 0, excelHeader[ii], style));
			}
		} else {
			sheet = book.getSheet(book.getNumberOfSheets()-1);
		}

		startRow = sheet.getRows();

		for (int i = 0; i < parNodeList.size(); i++){
			Map<String , Object> ele =  (HashMap<String , Object>)parNodeList.get(i);
			//如果大于6W行，则新生成一个sheet
			if(startRow >= MSG_EXCEL_MAX_ROWS + 1){
				sheet = book.createSheet(sheetNamePre + (1 + book.getNumberOfSheets()), book.getNumberOfSheets());
				for (int ii=0;ii<excelHeader.length;ii++){// 创建表头
					sheet.setColumnView(ii, 20);
					sheet.addCell(new Label(ii, 0, excelHeader[ii], style));
				}
				startRow = 1;
			}
			//根据指定的表头为每列赋值
			for(int ii=0;ii<columnsNum;ii++){
				//elementText = ele.get(columnsName[ii]).toString();
				label = new Label(ii, startRow, (ele.get(columnsName[ii])!=null)?(ele.get(columnsName[ii]).toString()):(""), style);
				sheet.addCell(label);
			}

			startRow++;
		}

		book.write();
		book.close();
	}

	/**
	 * excel文件写入数据
	 *
	 * @param
	 * @return
	 */
	public static void writeQueryToExcelFile(List rowsList,
											 String[] columnArray,
											 String[] headArray,
											 WritableWorkbook book) throws Exception{
		Label label = null;
		int columnsNum = columnArray.length;
		String sheetName = null;
		WritableSheet sheet = null;
		int startRow = 1;
		WritableCellFormat headStyle = createReportHeadStyle();
		WritableCellFormat oddStyle = createRptOddLineStyle();
		WritableCellFormat evenStyle = createRptEvenLineStyle();
		WritableCellFormat lineStyle =null;

		if (book.getNumberOfSheets() == 0){
			sheetName = "sheet" + (1 + book.getNumberOfSheets());
			sheet = book.createSheet(sheetName, book.getNumberOfSheets());
			for (int ii=0;ii<headArray.length;ii++){// 创建表头
				sheet.setColumnView(ii, 20);
				sheet.addCell(new Label(ii, 0, headArray[ii], headStyle));
			}
		} else {
			//注意:报表模板只能保留一个sheet cvs.report_detail.report_excle_model
			sheet = book.getSheet(book.getNumberOfSheets()-1);
		}

		startRow = sheet.getRows();

		for (int i = 0; i < rowsList.size(); i++){
			Map rowMap = (Map) rowsList.get(i);
			//如果大于6W行，则新生成一个sheet
			if(startRow >= MSG_EXCEL_MAX_ROWS + 1){
				sheet = book.createSheet("sheet" + (1 + book.getNumberOfSheets()), book.getNumberOfSheets());
				for (int ii=0;ii<headArray.length;ii++){// 创建表头
					sheet.setColumnView(ii, 20);
					sheet.addCell(new Label(ii, 0, headArray[ii], headStyle));
				}
				startRow = 1;
			}
			//写每行记录
			lineStyle=(startRow%2==0)?evenStyle:oddStyle;
			for(int j=0;j<columnsNum;j++){
				sheet.addCell(new Label(j, startRow,(rowMap.get(columnArray[j])!=null)?(rowMap.get(columnArray[j]).toString()):(""), lineStyle));
			}
			startRow++;
		}
		book.write();
		book.close();	//最后再关闭
	}

	/**
	 * excel文件写入数据
	 *
	 * @param
	 * @return
	 */
	public static void writeQueryToExcelFileByList(List rowsList,
											 String[] columnArray,
											 String[] headArray,
											 WritableWorkbook book,
											 String entityName) throws Exception{
		Label label = null;
		int columnsNum = columnArray.length;
		String sheetName = null;
		WritableSheet sheet = null;
		int startRow = 1;
//		WritableCellFormat headStyle = createReportHeadStyle();
//		WritableCellFormat oddStyle = createRptOddLineStyle();
//		WritableCellFormat evenStyle = createRptEvenLineStyle();
		WritableCellFormat lineStyle =createStyle();
		Class clazz = Class.forName(entityName);
		Object object = clazz.newInstance();
		Method addMethod = null;
		Object result = null;

		if (book.getNumberOfSheets() == 0){
			sheetName = "sheet" + (1 + book.getNumberOfSheets());
			sheet = book.createSheet(sheetName, book.getNumberOfSheets());
			for (int ii=0;ii<headArray.length;ii++){// 创建表头
				sheet.setColumnView(ii, 20);
				sheet.addCell(new Label(ii, 0, headArray[ii], lineStyle));
			}
		} else {
			//注意:报表模板只能保留一个sheet cvs.report_detail.report_excle_model
			sheet = book.getSheet(book.getNumberOfSheets()-1);
		}

		startRow = sheet.getRows();

		for (int i = 0; i < rowsList.size(); i++){
			object = rowsList.get(i);
			//如果大于6W行，则新生成一个sheet
			if(startRow >= MSG_EXCEL_MAX_ROWS + 1){
				sheet = book.createSheet("sheet" + (1 + book.getNumberOfSheets()), book.getNumberOfSheets());
				for (int ii=0;ii<headArray.length;ii++){// 创建表头
					sheet.setColumnView(ii, 20);
					sheet.addCell(new Label(ii, 0, headArray[ii], lineStyle));
				}
				startRow = 1;
			}
			for(int j=0;j<columnsNum;j++){
				addMethod = clazz.getMethod(getMethodName(columnArray[j]));
				result = addMethod.invoke(object);
				//lineStyle=(startRow%2==0)?evenStyle:oddStyle;
				sheet.addCell(new Label(j, startRow,result==null?"":result.toString(), lineStyle));
			}
			startRow++;
		}
		book.write();
		book.close();	//最后再关闭
	}

	/**
	 * 反射前通过属性名得到方法名字
	 *
	 * @param
	 * @return map
	 */

	public static String getMethodName(String columnName) {
		return ("get"+Character.toUpperCase(columnName.charAt(0)) + columnName.substring(1, columnName.length()));
	}

	/**
	 * 生成excel文件
	 *
	 * @param excelHeader columnsName qureyResults
	 * @return map
	 */
	public static Map<String,Object> generateExcelDataFile(Map<String,Object> map, String[] excelHeader,String[] columnsName,List<Map<String , Object>> qureyResults) throws Exception{
		try{
			WritableWorkbook book = ExcelFileTool.createExcelFile(map);
			ExcelFileTool.writeExcelFile(qureyResults,columnsName,excelHeader,book);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return map;
	}

	/**
	 * 生成excel文件
	 *
	 * @param excelHeader columnsName qureyResults
	 * @return map
	 */
	public static Map<String,Object> generateExcelDataFileByList(Map<String,Object> map, String[] excelHeader,String[] columnsName,List list) throws Exception{
		try{
			WritableWorkbook book = ExcelFileTool.createExcelFile(map);
				ExcelFileTool.writeQueryToExcelFileByList(list, columnsName, excelHeader, book,map.get("entityName").toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return map;
	}


	/**
	 * 上传文件获取服务器文件夹地址
	 *
	 * @param
	 * @return fileHome
	 */
	public static String getFileHome(){
		String fileHome="";
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("windows") != -1){
			fileHome="C:\\code_work\\nept\\fileHome\\";
		}else {
			fileHome="/IBM/WebSphere/AppServer/attachFile/";
		}
		return fileHome;
	}

	/**
	 * Bean2Map
	 *
	 * @param
	 * @return fileHome
	 */
	public static <K, V> Map<K, V> bean2Map(Object javaBean) {
		Map<K, V> ret = new HashMap<K, V>();
		try {
			Method[] methods = javaBean.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					Object value = method.invoke(javaBean, (Object[]) null);
					ret.put((K) field, (V) (null == value ? "" : value));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}





}
