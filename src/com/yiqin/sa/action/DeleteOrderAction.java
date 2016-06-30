package com.yiqin.sa.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.ShoppingManager;

/**
 * 删除指定订单
 * 
 * @author liujun
 * 
 */
public class DeleteOrderAction extends ActionSupport {

	private static final long serialVersionUID = 42159396569834548L;

	// 订单Id
	private String orderId;

	private ShoppingManager shoppingManager;

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (GenericValidator.isBlankOrNull(orderId)) {
				result = "2";
				response.getWriter().print(result);
				return null;
			}
			// 修改订单删除状态
			boolean flag = shoppingManager.deleteOrder(Long.valueOf(orderId));
			if (!flag) {
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
}
