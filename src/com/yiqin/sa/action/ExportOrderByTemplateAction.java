package com.yiqin.sa.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.util.ExcelExportOrder;
import com.yiqin.util.ExcelExportOrderByXss;
import com.yiqin.util.ExcelExportOrderUtil;

/**
 * 选定模板，导出订单信息
 * 
 */
public class ExportOrderByTemplateAction extends ActionSupport {
	
	private static final long serialVersionUID = 5617607939328804382L;
	// 订单Id列表
	private String ids;
	// 导出类型
	private int type;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	@Override
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			ExcelExportOrder excel = new ExcelExportOrderByXss("com/yiqin/template/test.xlsx", 0);
			excel.createFooter();
			int startRow = 2;//起始行
			int rows = 5;//插入行数
			excel.insertRows(startRow, rows);
			ExcelExportOrderUtil.wirteXssExcel(excel);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("xlsx_a", "2016-06-07 12:00:00");
			map.put("xlsx_b", "测试");
			map.put("xlsx_c", "12345");
			excel.replaceExcelData(map);

			String fileName = "export1.xlsx";
			excel.downloadExcel(response, fileName);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
