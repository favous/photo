package com.cloudsea.photo.utils.frameutils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.RichTextString;

public class PoiUtil {
	
	//指定转换日期格式
	private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat dateformat;
	
	public static void setDateformat(SimpleDateFormat dateformat) {
		PoiUtil.dateformat = dateformat;
	}
	
	
	
	public static <T> void export(List<T> dataList, HttpServletResponse response, String exportName, String sheetName,
			String[] titles, String[] propertyNames, int... colWidth) throws Exception {
		export(dataList, response, exportName, sheetName, titles, propertyNames, null, colWidth);
	}

	public static <T> void export(List<T> dataList, HttpServletResponse response, String exportName, String sheetName,
			String[] titles, String[] propertyNames, Map<String, ValueFormate> formateMap, int... colWidth) throws Exception {
		if (titles.length != propertyNames.length){
			new Exception("需要导出的列数，与列名称数，数量不一致");
		}
		//创建一个新的Excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		//创建sheet页
		HSSFSheet sheet = workBook.createSheet(sheetName);
		//设置第一行为Header
		HSSFRow row = sheet.createRow(0);
		//插入标题行
		for (int j = 0; j < titles.length; j++){			
			HSSFCell cell = row.createCell(j);
			cell.setCellValue(titles[j]);
			int index = j > colWidth.length - 1 ? colWidth.length - 1 : j;
			sheet.setColumnWidth(j, colWidth[index]);	
		}
		//插入数据行
		for (int i = 0; i < dataList.size(); i++) {
			row = sheet.createRow(i + 1);
			T obj = dataList.get(i);
			for (int j = 0; j < titles.length; j++){			
				Field field =  obj.getClass().getDeclaredField(propertyNames[j]);
				field.setAccessible(true);
				Object value = field.get(obj);
				setValue(row.createCell(j), value, formateMap == null ? null : formateMap.get(propertyNames[j]));
			}
		}
		doExport(response, exportName, workBook);
	}

	public static void exportByJson(List<JSONObject> dataList, HttpServletResponse response, String exportName,
			String sheetName, String[] titles, String[] keys, int... colWidth) throws Exception {
		exportByJson(dataList, response, exportName, sheetName, titles, keys, null, colWidth);
	}

	public static void exportByJson(List<JSONObject> dataList, HttpServletResponse response, String exportName,
			String sheetName, String[] titles, String[] keys, Map<String, ValueFormate> formateMap, int... colWidth) throws Exception {
		if (titles.length != keys.length){
			new Exception("需要导出的列数，与列名称数，数量不一致");
		}
		//创建一个新的Excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		//创建sheet页
		HSSFSheet sheet = workBook.createSheet(sheetName);
		//设置第一行为Header
		HSSFRow row = sheet.createRow(0);
		//插入标题行
		for (int j = 0; j < titles.length; j++){			
			HSSFCell cell = row.createCell(j);
			cell.setCellValue(titles[j]);
			int index = j > colWidth.length - 1 ? colWidth.length - 1 : j;
			sheet.setColumnWidth(j, colWidth[index]);	
		}
		//插入数据行
		for (int i = 0; i < dataList.size(); i++) {
			row = sheet.createRow(i + 1);
			JSONObject obj = dataList.get(i);
			for (int j = 0; j < keys.length; j++){
				Object value = obj.get(keys[j]);
				if (value != null){					
					setValue(row.createCell(j), value, formateMap == null ? null : formateMap.get(keys[j]));
				}
			}
		}
		doExport(response, exportName, workBook);
	}

	public static void exportByMap(List<HashMap<String, ?>> mapList, HttpServletResponse response, String exportName,
			String sheetName, String[] titles, String[] keys, int... colWidth) throws Exception {
		exportByMap(mapList, response, exportName, sheetName, titles, keys, null, colWidth);
	}

	public static void exportByMap(List<HashMap<String, ?>> mapList, HttpServletResponse response, String exportName,
			String sheetName, String[] titles, String[] keys, Map<String, ValueFormate> formateMap, int... colWidth) throws Exception {
		if (titles.length != keys.length){
			new Exception("需要导出的列数，与列名称数，数量不一致");
		}
		//创建一个新的Excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		//创建sheet页
		HSSFSheet sheet = workBook.createSheet(sheetName);
		//设置第一行为Header
		HSSFRow row = sheet.createRow(0);
		//插入标题行
		for (int j = 0; j < titles.length; j++){			
			HSSFCell cell = row.createCell(j);
			cell.setCellValue(titles[j]);
			int index = j > colWidth.length - 1 ? colWidth.length - 1 : j;
			sheet.setColumnWidth(j, colWidth[index]);	
		}
		//插入数据行
		for (int i = 0; i < mapList.size(); i++) {
			row = sheet.createRow(i + 1);
			HashMap<String, ?> map = mapList.get(i);
			for (int j = 0; j < keys.length; j++){			
				Object value = map.get(keys[j]);
				if (value != null){					
					setValue(row.createCell(j), value, formateMap == null ? null : formateMap.get(keys[j]));
				}
			}
		}
		doExport(response, exportName, workBook);
	}

	public static void exportByArray(List<Object[]> arrayList, HttpServletResponse response, String exportName,
			String sheetName, String[] titles, int... colWidth) throws Exception {
		exportByArray(arrayList, response, exportName, sheetName, titles, null, colWidth);
	}

	public static void exportByArray(List<Object[]> arrayList, HttpServletResponse response, String exportName,
			String sheetName, String[] titles, ValueFormate[] formateArray, int... colWidth) throws Exception {
		//创建一个新的Excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		//创建sheet页
		HSSFSheet sheet = workBook.createSheet(sheetName);
		//设置第一行为Header
		HSSFRow row = sheet.createRow(0);
		//插入标题行
		for (int j = 0; j < titles.length; j++){			
			HSSFCell cell = row.createCell(j);
			cell.setCellValue(titles[j]);
			int index = j > colWidth.length - 1 ? colWidth.length - 1 : j;
			sheet.setColumnWidth(j, colWidth[index]);	
		}
		//插入数据行
		for (int i = 0; i < arrayList.size(); i++) {
			row = sheet.createRow(i + 1);
			Object[] array = arrayList.get(i);
			for (int j = 0; j < array.length; j++){			
				if (array[j] != null && !"".equals(array[j])){					
					setValue(row.createCell(j), array[j], formateArray == null ? null : formateArray[j]);
				}
			}
		}
		doExport(response, exportName, workBook);
	}
	
	
	
	public static interface ValueFormate{
		Object formate(Object obj) throws Exception;
	}

	
	
	private static void doExport(HttpServletResponse response, String exportName, HSSFWorkbook workBook)
			throws IOException {
		response.reset();
		response.setContentType("application/msexcel;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + exportName);
		OutputStream out = response.getOutputStream();
		workBook.write(out);
		out.flush();
		out.close();
	}
	
	private static void setValue(HSSFCell cell, Object value, ValueFormate formate) {
		if (value == null){
			return;
		} else if (formate != null){
			try {
				value = formate.formate(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		if (String.class == value.getClass()){
			cell.setCellValue((String) value);
		} else if ("java.lang.boolean".equalsIgnoreCase(value.getClass().getName())){
			cell.setCellValue((boolean) value);
		} else if (Calendar.class == value.getClass()){
			cell.setCellValue((Calendar) value);
		} else if (RichTextString.class == value.getClass()){
			cell.setCellValue((RichTextString) value);
		} else if (Date.class.isAssignableFrom(value.getClass())){
			dateformat = dateformat == null ? DATEFORMAT : dateformat;
			cell.setCellValue(dateformat.format(value));
		} else if (Number.class.isAssignableFrom(value.getClass())
				&& value.getClass().getName().startsWith("java.lang")) {
			cell.setCellValue((double) value);
		} 
	}
}
