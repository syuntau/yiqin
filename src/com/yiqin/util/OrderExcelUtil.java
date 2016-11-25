package com.yiqin.util;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.yiqin.pojo.Cart;
import com.yiqin.shop.bean.OrderView;

/**
 * 订单导出Excel
 * 
 * @author liujun
 *
 */
public class OrderExcelUtil {
	
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null; 
	
	public OrderExcelUtil() {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet();
	}

	public HSSFWorkbook getWorkbook() {
		return this.workbook;
	}

	public void addRows(List<OrderView> orderList, List<String> columnKeysList) {
		String[] columnKeysArray = (String[])columnKeysList.toArray(new String[columnKeysList.size()]);
		for (OrderView orderv : orderList) {
			List<Cart> cartlist = orderv.getProductList();
			HSSFRow row = this.sheet.createRow(this.sheet.getLastRowNum() + 1);
			for (int i = 0; i < columnKeysArray.length; i++) {
				if("productId".equals(columnKeysArray[i])){
					for(Cart cart : cartlist){
						row = this.sheet.createRow(this.sheet.getLastRowNum() + 1);
						for (int j = i; j < columnKeysArray.length; j++) {
							addCell(row,  j, HSSFCell.CELL_TYPE_STRING, getExcelCartValues(columnKeysArray[j], cart));
						}
					}
					break;
				}else{
					addCell(row,  i, HSSFCell.CELL_TYPE_STRING, getExcelOrderValues(columnKeysArray[i], orderv));
				}
			}
		}
	}
	private void addCell (HSSFRow row, int cellIndex, int cellType, String value) {
		HSSFCell cellHeadLine = row.createCell(cellIndex);
		cellHeadLine.setCellValue(value);
		cellHeadLine.setCellType(cellType);
	}
	
	public void addHeader(Map<String, String> exportAttrMap,List<String> columnKeysList) {
		String[] columnKeysArray = (String[])columnKeysList.toArray(new String[columnKeysList.size()]);
		HSSFRow row = this.sheet.createRow(0);
		for (int i = 0; i < columnKeysArray.length; i++) {
			HSSFCell cell = row.createCell(i, HSSFCell.CELL_TYPE_STRING);
			String tempValue = exportAttrMap.get(columnKeysArray[i]);
			cell.setCellValue(tempValue);
		}
	}

	private static String getExcelOrderValues(String headCode, OrderView orderv) {
		String value = "";
		Class<OrderView> docClass = OrderView.class;
		try {
			Field field = docClass.getDeclaredField(headCode);
			field.setAccessible(true);
			if (null == field.get(orderv)) {
				value = "";
			} else if ("beizhuzongjia".equals(headCode)) {
				value = String.valueOf(field.get(orderv));
				if ("0".equals(value)) {
					value = "0.00";
				} else {
					value = new DecimalFormat("#########.00").format(Float.valueOf(value)*1);
				}
			} else {
				value = String.valueOf(field.get(orderv));
			}
			if (Util.isEmpty(value)) {
				value = "";
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	private static String getExcelCartValues(String headCode, Cart cart) {
		String value = "";
		Class<Cart> docClass = Cart.class;
		try {
			if("pTotalPrice".equals(headCode)){
				Field fieldzhekoup = docClass.getDeclaredField("zhekouPrice");
				fieldzhekoup.setAccessible(true);
				Field fieldcount = docClass.getDeclaredField("count");
				fieldcount.setAccessible(true);
				String zhekouPrice = (String) fieldzhekoup.get(cart);
				int count = (int) fieldcount.get(cart);
				value = new DecimalFormat("#########.00").format(Float.valueOf(zhekouPrice)*count);
				return value;
			}
			Field field = docClass.getDeclaredField(headCode);
			field.setAccessible(true);
			if (null == field.get(cart)) {
				value = "";
			} else {
				value = String.valueOf(field.get(cart));
			}
			if (Util.isEmpty(value)) {
				value = "";
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	@SuppressWarnings("unused")
	private static String dealOverLength(String content) {
		if (Util.isEmpty(content)) {
			return "";
		}
		//max length allowed typed into  excel cell is 32767
		if (content.length() > 32767) {
			return content.substring(0, 32766);
		}
		return content;
	}
}
