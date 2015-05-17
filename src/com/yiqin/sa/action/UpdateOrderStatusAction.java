package com.yiqin.sa.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.ShoppingManager;

/**
 * 修改订单状态
 * 
 * @author liujun
 * 
 */
public class UpdateOrderStatusAction extends ActionSupport {

	private static final long serialVersionUID = -2068469707878529683L;
	// 用户Id
	private String userId;
	// 订单Id
	private String orderId;
	// 订单状态
	private String status;

	private ShoppingManager shoppingManager;

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (GenericValidator.isBlankOrNull(userId)
					|| GenericValidator.isBlankOrNull(orderId)
					|| GenericValidator.isBlankOrNull(status)) {
				result = "2";
				response.getWriter().print(result);
				return null;
			}
			// 修改订单状态
			boolean flag = shoppingManager.updateOrderStatus(userId,
					Long.valueOf(orderId), Integer.valueOf(status));
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ShoppingManager getShoppingManager() {
		return shoppingManager;
	}

	public void setShoppingManager(ShoppingManager shoppingManager) {
		this.shoppingManager = shoppingManager;
	}
}
