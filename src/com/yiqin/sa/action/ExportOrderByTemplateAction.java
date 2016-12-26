package com.yiqin.sa.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Cart;
import com.yiqin.service.ShoppingManager;
import com.yiqin.shop.bean.OrderView;
import com.yiqin.util.ExcelExportOrder;
import com.yiqin.util.ExcelExportOrderByXss;
import com.yiqin.util.ExcelExportOrderUtil;
import com.yiqin.util.Util;

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

	private ShoppingManager shoppingManager;

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

	public ShoppingManager getShoppingManager() {
		return shoppingManager;
	}

	public void setShoppingManager(ShoppingManager shoppingManager) {
		this.shoppingManager = shoppingManager;
	}


	@Override
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			System.out.println("###### ids : " + ids);

			List<OrderView> ovList = shoppingManager.findOrderList(ids);
			if (Util.isEmpty(ovList)) {
				System.out.println("###### no order list, return");
				return null;
			}

			System.out.println("###### Excel Export Order start ...");
			ExcelExportOrder excel = new ExcelExportOrderByXss("com/yiqin/template/test.xlsx", 1);
			excel.createFooter();
			System.out.println("###### createFooter over ...");

			OrderView order = ovList.get(0);
			// 填充表头
			Map<String, String> map = new HashMap<String, String>();
			map.put("#ORDER_ID#", String.valueOf(order.getId()));
			map.put("#CUSTOMER_NAME#", order.getName());
			map.put("#ADDRESS#", order.getAddress());
			map.put("#ORDER_DATE#", order.getCrateDate());
			map.put("#CONTACT#", order.getName());
			map.put("#TEL#", order.getMobile());
			excel.replaceExcelData(map);
			System.out.println("###### excel header set over ...");

			int startRow = 10; //起始行
			int rows = ovList.size(); //插入行数
			if (rows > 1) {
				excel.insertRows(startRow, rows - 1);
			}
			System.out.println("###### excel insert rows over ...");

			// 获取单元格样式
			Row rowCellStyle = excel.getXssSheet().getRow(startRow);
			CellStyle[] styleArr = new CellStyle[5];
			for (int i = 0; i < 5; i++) {
				styleArr[i] = rowCellStyle.getCell(0).getCellStyle();
			}

			int rowId = 10;
			int idx = 1;
			for (OrderView ov : ovList) {
				XSSFRow row = excel.getXssSheet().getRow(rowId++);
				List<Cart> cartList = ov.getProductList();
				for (Cart cart : cartList) {
					int j = 0;
					XSSFCell cell = row.getCell(j);
					cell.setCellValue(idx++);
					cell.setCellStyle(styleArr[j]);

					cell = row.getCell(++j);
					cell.setCellValue(cart.getProductName());
					cell.setCellStyle(styleArr[j]);

					cell = row.getCell(++j);
					cell.setCellValue(cart.getZhekouPrice());
					cell.setCellStyle(styleArr[j]);

					cell = row.getCell(++j);
					cell.setCellValue(cart.getCount());
					cell.setCellStyle(styleArr[j]);

					cell = row.getCell(++j);
					cell.setCellValue(cart.getZhekouPrice()); // 需要小计
					cell.setCellStyle(styleArr[j]);
				}
			}
			System.out.println("###### excel order set over ...");

			ExcelExportOrderUtil.wirteXssExcel(excel);
			System.out.println("###### excel write file over ...");

			String fileName = "export1.xlsx";
			excel.downloadExcel(response, fileName);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
