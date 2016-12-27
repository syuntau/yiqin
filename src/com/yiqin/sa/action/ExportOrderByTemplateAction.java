package com.yiqin.sa.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Cart;
import com.yiqin.pojo.User;
import com.yiqin.service.ShoppingManager;
import com.yiqin.service.UserManager;
import com.yiqin.shop.bean.OrderView;
import com.yiqin.util.ExcelExportOrder;
import com.yiqin.util.ExcelExportOrderByXss;
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
	private String type;

	private UserManager userManager;

	private ShoppingManager shoppingManager;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public ShoppingManager getShoppingManager() {
		return shoppingManager;
	}

	public void setShoppingManager(ShoppingManager shoppingManager) {
		this.shoppingManager = shoppingManager;
	}

	public String exportOne() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			System.out.println("###### ids : " + ids);
			if (Util.isEmpty(ids)) {
				System.out.println("###### ids null, return");
				return null;
			}

			List<OrderView> ovList = shoppingManager.findOrderList(ids);
			if (Util.isEmpty(ovList)) {
				System.out.println("###### no order list, return");
				return null;
			}

			System.out.println("###### Excel Export Order start ...");
			String tplFile = getText("tpl.file.11");
			ExcelExportOrder excel = new ExcelExportOrderByXss(tplFile, 0);
			excel.createFooter();
			System.out.println("###### createFooter over ...");

			OrderView order = ovList.get(0);
			User user = userManager.findUserByUserId(order.getUserId());
			String customer = Util.isNotEmpty(user.getCompany()) ? user.getCompany() : order.getUserId();
			// 填充表头
			Map<String, String> map = new HashMap<String, String>();
			map.put("#ORDER_ID#", String.valueOf(order.getId()));
			map.put("#CUSTOMER_NAME#", customer);
			map.put("#ADDRESS#", order.getAddress());
			map.put("#ORDER_DATE#", order.getCrateDate());
			map.put("#CONTACT#", order.getName());
			map.put("#TEL#", order.getMobile());
			excel.replaceExcelData(map);
			System.out.println("###### excel header set over ...");

			List<Cart> cartList = order.getProductList();
			if (Util.isNotEmpty(order.getOrderNote())) {
				Cart cart = new Cart();
				cart.setCount(1);
				cart.setProductName(order.getOrderNote());
				cart.setZhekouPrice(order.getBeizhuzongjia());
				cartList.add(cart);
			}
			int startRow = Integer.parseInt(getText("tpl.start.row.11")); //起始行
			int rows = cartList.size(); //插入行数
			if (rows > 1) {
				excel.insertRows(startRow, rows - 1);
				System.out.println("###### excel start row : " + startRow + " insert : " + (rows - 1) + " rows over ...");
			}

			// 获取单元格样式
			XSSFRow rowCellStyle = excel.getXssSheet().getRow(startRow);
			XSSFCellStyle[] styleArr = new XSSFCellStyle[5];
			for (int i = 0; i < 5; i++) {
				styleArr[i] = rowCellStyle.getCell(i).getCellStyle();
			}
			short height = rowCellStyle.getHeight();

			int rowId = startRow;
			int idx = 1;
			float totalPrice = 0;
			for (Cart cart : cartList) {
				XSSFRow row = excel.getXssSheet().getRow(rowId++);
				row.setHeight(height);
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
				float subTotal = cart.getCount() * Float.valueOf(cart.getZhekouPrice());
				cell.setCellValue(new DecimalFormat("#########.00").format(subTotal)); // 需要小计
				cell.setCellStyle(styleArr[j]);
				totalPrice += subTotal;
			}
			map = new HashMap<String, String>();
			map.put("#total#", new DecimalFormat("#########.00").format(totalPrice));
			excel.replaceFooterData(map);
			System.out.println("###### excel order set over ...");

			String fileName = "order_" + ids + ".xlsx";
			excel.downloadExcel(response, fileName);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String exportOrderTpl() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			System.out.println("###### ids : " + ids + ", type : " + type);
			if (Util.isEmpty(ids) || Util.isEmpty(type)) {
				System.out.println("###### ids or type null, return");
				return null;
			}

			List<OrderView> ovList = shoppingManager.findOrderList(ids);
			if (Util.isEmpty(ovList)) {
				System.out.println("###### no order list, return");
				return null;
			}

			Set<String> userIdSet = new HashSet<String>();
			List<Cart> orderMergeList = new ArrayList<Cart>();
			for (OrderView ov : ovList) {
				userIdSet.add(ov.getUserId());
				for (Cart cart : ov.getProductList()) {
					orderMergeList.add(cart);
				}
				if (Util.isNotEmpty(ov.getOrderNote())) {
					Cart cart = new Cart();
					cart.setCount(1);
					cart.setProductName(ov.getOrderNote());
					cart.setZhekouPrice(ov.getBeizhuzongjia());
					orderMergeList.add(cart);
				}
			}
			if (userIdSet.size() > 1) {
				System.out.println("###### userId non-unique, return");
				return null;
			}

			System.out.println("###### Excel Export Order start ...");
			String tplFile = getText("tpl.file." + type);
			ExcelExportOrder excel = new ExcelExportOrderByXss(tplFile, 0);
			excel.createFooter();
			System.out.println("###### createFooter over ...");

			OrderView order = ovList.get(0);
			String userId = order.getUserId();
			User user = userManager.findUserByUserId(userId);
			String customer = Util.isNotEmpty(user.getCompany()) ? user.getCompany() : userId;
			// 填充表头
			Map<String, String> map = new HashMap<String, String>();
			map.put("#CUSTOMER_NAME#", customer);
			map.put("#ADDRESS#", order.getAddress());
			map.put("#UPDATE_DATE#", order.getCrateDate());
			map.put("#TEL#", order.getMobile());
			excel.replaceExcelData(map);
			System.out.println("###### excel header set over ...");

			int startRow = Integer.parseInt(getText("tpl.start.row." + type)); //起始行
			int rows = orderMergeList.size(); //插入行数
			if (rows > 1) {
				excel.insertRows(startRow, rows - 1);
				System.out.println("###### excel start row : " + startRow + " insert : " + (rows - 1) + " rows over ...");
			}

			// 获取单元格样式
			XSSFRow rowCellStyle = excel.getXssSheet().getRow(startRow);
			XSSFCellStyle[] styleArr = new XSSFCellStyle[5];
			for (int i = 0; i < 5; i++) {
				styleArr[i] = rowCellStyle.getCell(i).getCellStyle();
			}
			short height = rowCellStyle.getHeight();

			int rowId = startRow;
			int idx = 1;
			float totalPrice = 0;
			for (Cart cart : orderMergeList) {
				XSSFRow row = excel.getXssSheet().getRow(rowId++);
				row.setHeight(height);
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
				float subTotal = cart.getCount() * Float.valueOf(cart.getZhekouPrice());
				cell.setCellValue(new DecimalFormat("#########.00").format(subTotal)); // 需要小计
				cell.setCellStyle(styleArr[j]);
				totalPrice += subTotal;
			}
			map = new HashMap<String, String>();
			map.put("#total#", new DecimalFormat("#########.00").format(totalPrice));
			excel.replaceFooterData(map);
			System.out.println("###### excel order set over ...");

			String fileName = userId + "_" + Util.format(new Date()) + ".xlsx";
			excel.downloadExcel(response, fileName);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
