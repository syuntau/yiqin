package com.yiqin.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ExcelExportOrderUtil {

	public void wirteXssExcel(ExcelExportOrder excel){
		XSSFSheet sheet = excel.getXssSheet();
		List list = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
		XSSFRow row = sheet.createRow(i+3);
    		row.createCell(0).setCellValue(String.valueOf(i + 1));
    		row.createCell(1).setCellValue("111");
    		row.createCell(2).setCellValue("222");
    		row.createCell(3).setCellValue("333");
		}
	}
	
	public void wirteHssExcel(ExcelExportOrder excel){
		HSSFSheet sheet = excel.getHssSheet();
		List list = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
		HSSFRow row = sheet.createRow(i+3);
    		row.createCell(0).setCellValue(String.valueOf(i + 1));
    		row.createCell(1).setCellValue("111");
    		row.createCell(2).setCellValue("222");
    		row.createCell(3).setCellValue("333");
		}
	}
}
