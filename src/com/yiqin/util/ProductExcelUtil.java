package com.yiqin.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.yiqin.pojo.Attribute;

public class ProductExcelUtil {
	
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null; 
	
	public ProductExcelUtil() {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet();
	}
	
	public HSSFWorkbook getWorkbook() {
		return this.workbook;
	}
	
	public void addRows(Map<String, Map<String, String>> productMap, List<String> attrIdList) {
		List<String> pIdList = new ArrayList<String>();
		for (String pId : productMap.keySet()) {
			pIdList.add(pId);
		}
		Collections.sort(pIdList);
		
		for (String pId : pIdList) {
			HSSFRow row = this.sheet.createRow(this.sheet.getLastRowNum() + 1);

			int i = 0;
			addCell(row, i++, HSSFCell.CELL_TYPE_STRING, pId);

			Map<String, String> pInfoMap = productMap.get(pId);
			for (String attrId : attrIdList) {
				String val = pInfoMap.get(attrId);
				if (Util.isNotEmpty(val)) {
					addCell(row, i++, HSSFCell.CELL_TYPE_STRING, val);
				} else {
					addCell(row, i++, HSSFCell.CELL_TYPE_STRING, "#");
				}
			}
		}
	}
	private void addCell (HSSFRow row, int cellIndex, int cellType, String value) {
		HSSFCell cellHeadLine = row.createCell(cellIndex);
		cellHeadLine.setCellValue(value);
		cellHeadLine.setCellType(cellType);
	}
	
	public void addHeader(List<Attribute> attrList) {
		HSSFRow row0 = this.sheet.createRow(0);
		HSSFRow row1 = this.sheet.createRow(1);
		int i = 0;

		HSSFCell cell00 = row0.createCell(i, HSSFCell.CELL_TYPE_STRING);
		HSSFCell cell10 = row1.createCell(i++, HSSFCell.CELL_TYPE_STRING);
		cell00.setCellValue("#");
		cell10.setCellValue(attrList.get(0).getCategoryId() + "");
		
		for (Attribute attr : attrList) {
			HSSFCell cell0 = row0.createCell(i, HSSFCell.CELL_TYPE_STRING);
			HSSFCell cell1 = row1.createCell(i++, HSSFCell.CELL_TYPE_STRING);
			cell0.setCellValue(attr.getName());
			cell1.setCellValue(attr.getId() + "");
		}
	}

}
