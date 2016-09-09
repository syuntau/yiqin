package com.yiqin.sa.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Attribute;
import com.yiqin.service.ProductManager;
import com.yiqin.shop.bean.OrderView;
import com.yiqin.util.ExportReportTypeFactory;
import com.yiqin.util.OrderExcelUtil;
import com.yiqin.util.Page;
import com.yiqin.util.ProductExcelUtil;
import com.yiqin.util.Util;

/**
 * 导出订单信息
 * 
 * @author LiuJun
 * 
 */
public class ExportProductExcelAction extends ActionSupport {
	
	private static final long serialVersionUID = 424035033536530973L;
	
	// 用户Id
	private String userId;
	// 开始时间
	private String startTime;
	// 结束时间
	private String endTime;
	
	private ProductManager productManager;
	private String categoryId;

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			Object obj = request.getSession().getAttribute("export_order");
			List<OrderView> orderList = new ArrayList<OrderView>();
			if(obj != null){
				Page page = (Page) obj;
				orderList = page.getResults();
			}
			if (Util.isEmpty(orderList)) {
				return null;
			}
			
			int iCategoryId = -1;
			try {
				iCategoryId = Integer.parseInt(categoryId);
			} catch (Exception e) {
				
			}
			List<Attribute> attrList = productManager.findFilterAttribute(iCategoryId);
			
			ProductExcelUtil util = new ProductExcelUtil();
			util.addHeader(attrList);
			

			
			
			
			
//			OrderExcelUtil util = new OrderExcelUtil();
//			util.addHeader(ExportReportTypeFactory.columnNameMap,
//					ExportReportTypeFactory.columnKeyList);
//			
//			util.addRows(orderList, ExportReportTypeFactory.columnKeyList);
//			StringBuilder sb = new StringBuilder();
//			sb.append("客户：").append(userId).append("于").append(startTime).append("~").append(endTime).append("订单详情");
//			util.download(response, sb.toString());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
