package com.yiqin.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
	
	public void download(HttpServletResponse response, String fileName) {
		String name = generateFileName(fileName);
		setHeader(response, name);
		try {
			OutputStream output = response.getOutputStream();
			this.workbook.write(output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String generateFileName(String fileName) {
		StringBuffer namebuf = new StringBuffer();
		if (fileName != null) {
			namebuf.append(fileName);
		}
//		Date date = new Date(System.currentTimeMillis());
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//		namebuf.append(format.format(date));
		namebuf.append(".xls");
		return namebuf.toString();
	}
	
	private static void setHeader(HttpServletResponse response, String fileName) {
		response.setContentType("applicaiton/octet-stream");
		StringBuffer str = new StringBuffer(100);
        str.append("attachment ; filename = \"");
        try {
        	str.append(URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        str.append("\"");
        response.setHeader("Content-Disposition", str.toString());
	}

	private static String getExcelOrderValues(String headCode, OrderView orderv) {
		String value = "";
		Class<OrderView> docClass = OrderView.class;
		try {
			Field field = docClass.getDeclaredField(headCode);
			field.setAccessible(true);
			if (null == field.get(orderv)) {
				value = "";
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
				Float zhekouPrice = (Float) fieldzhekoup.get(cart);
				int count = (int) fieldcount.get(cart);
				int totalPrice = (int) (zhekouPrice*count);
				value = String.valueOf(totalPrice);
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
