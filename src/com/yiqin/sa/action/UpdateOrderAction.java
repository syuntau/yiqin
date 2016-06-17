package com.yiqin.sa.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.ShoppingManager;
import com.yiqin.shop.bean.OrderTempObj;

/** 修改订单 */
public class UpdateOrderAction extends ActionSupport {
	private static final long serialVersionUID = 1834817239052250543L;
	// 订单Id
	private String orderId;
	// 订单内容
	private String orderContent;
	// 备份详情
	private String orderNote;
	// 备份总价
	private String beifenzongjia;

	private ShoppingManager shoppingManager;

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (GenericValidator.isBlankOrNull(orderId) || GenericValidator.isBlankOrNull(orderContent)) {
				result = "2";
				response.getWriter().print(result);
				return null;
			}
			OrderTempObj obj = new OrderTempObj();
			// todo : set OrderTempObj
			// 修改订单
			boolean flag = shoppingManager.updateOrder(Long.valueOf(orderId), obj);
			if(!flag){
				result = "2";
				response.getWriter().print(result);
				return null;
			}
			result = "1";
			response.getWriter().print(result);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result = "3";
			response.getWriter().print(result);
			return null;
		}
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public ShoppingManager getShoppingManager() {
		return shoppingManager;
	}

	public void setShoppingManager(ShoppingManager shoppingManager) {
		this.shoppingManager = shoppingManager;
	}

	public String getOrderContent() {
		return orderContent;
	}

	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
	}

	public String getOrderNote() {
		return orderNote;
	}

	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}

	public String getBeifenzongjia() {
		return beifenzongjia;
	}

	public void setBeifenzongjia(String beifenzongjia) {
		this.beifenzongjia = beifenzongjia;
	}
}
