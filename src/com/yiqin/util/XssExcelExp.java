package com.yiqin.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * 
 * excel导出类
 * <p>处理.xlsx格式<p>
 *  
 */

public class XssExcelExp extends ExcelExp{
	
	public XssExcelExp() {
		super();
	}

	/**
	 * 构造函数
	 * ExcelExp
	 * @param filePath 文件路径，如com/test/template/test.xlsx
	 * @param sheetNum 要操作的页签，0为第一个页签
	 * @throws IOException
	 */
	public XssExcelExp(String filePath, int sheetNum) throws IOException {
		URL resource = this.getClass().getClassLoader().getResource(filePath);
		InputStream is = new FileInputStream(resource.getFile());
		xssWb = new XSSFWorkbook(is);
		xssSheet = xssWb.getSheetAt(sheetNum);
	}
	
	/**
	 * 设置页脚 
	 */
	public void createFooter(){
		Footer footer = xssSheet.getFooter();
		footer.setRight("第" + HSSFFooter.page() + "页，共" + HSSFFooter.numPages() + "页");
	}
	
	/**
	 * 
	 * 插入行 
	 * @param startRow
	 * @param rows
	 */
	public void insertRows(int startRow, int rows){
		xssSheet.shiftRows(3, xssSheet.getLastRowNum(), rows, true, false);
	}
	
	/**
	 * 
	 * 替换模板中变量
	 * @param map
	 */
	public void replaceExcelData(Map<String, String> map){
		int rowNum = xssSheet.getLastRowNum();
		for(int i = 0;i <= rowNum; i++){
			XSSFRow row = xssSheet.getRow(i);
			if(row == null) continue;
			for(int j = 0;j < row.getPhysicalNumberOfCells();j++){
				XSSFCell cell = row.getCell(j);
				if(cell == null) continue;
				String key = cell.getStringCellValue();
				if(map.containsKey(key)){
					cell.setCellValue(map.get(key));
				}
			}
		}
	}
	
	/**
	 * 下载excel 
	 * @param response
	 * @param filaName
	 * @throws IOException
	 */
	public void downloadExcel(HttpServletResponse response, String filaName) throws IOException{
		String encodeFileName = URLEncoder.encode(filaName,"UTF-8");
		response.addHeader("Content-Disposition","attachment;filename=" +encodeFileName);
		ServletOutputStream out = response.getOutputStream();
		xssWb.write(out);
		out.flush();
		out.close();
	}
	
}
