package com.yiqin.sa.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Cart;
import com.yiqin.service.ShoppingManager;
import com.yiqin.shop.bean.OrderTempObj;
import com.yiqin.util.Util;

/** 修改订单 */
public class UpdateOrderAction extends ActionSupport {
	private static final long serialVersionUID = 1834817239052250543L;
	// 订单
	private String order;

	private ShoppingManager shoppingManager;

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (GenericValidator.isBlankOrNull(order)) {
				result = "2";
				response.getWriter().print(result);
				return null;
			}
			System.out.println("###### UpdateOrderAction order : " + order);

			Map<String, Class> classMap = new HashMap<String, Class>();
			classMap.put("productList", Cart.class);
			order = order.replaceAll(":NNN:", "&");
			System.out.println("###### UpdateOrderAction replaced order : " + order);
			OrderTempObj obj = (OrderTempObj) Util.getBeanByJson(order, OrderTempObj.class, classMap);
			String orderNote = obj.getOrderNote().replaceAll("\r\n", "<br>");
			orderNote = orderNote.replaceAll("\n", "<br>");
			obj.setOrderNote(orderNote);
			System.out.println("###### UpdateOrderAction obj : " + obj);

			// 修改订单
			boolean flag = shoppingManager.updateOrder(obj.getId(), obj);
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

	public ShoppingManager getShoppingManager() {
		return shoppingManager;
	}

	public void setShoppingManager(ShoppingManager shoppingManager) {
		this.shoppingManager = shoppingManager;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
