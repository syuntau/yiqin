package com.yiqin.shop.action;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.service.ShoppingManager;
import com.yiqin.util.Util;

public class DeleteCartProductAction extends ActionSupport {

	private static final long serialVersionUID = -6595442375209215925L;

	// 商品ID
	private String productId;

	private ShoppingManager shoppingManager;

	public ShoppingManager getShoppingManager() {
		return shoppingManager;
	}

	public void setShoppingManager(ShoppingManager shoppingManager) {
		this.shoppingManager = shoppingManager;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = ServletActionContext.getRequest().getSession();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (Util.isEmpty(productId)) {
				result = "1";
				response.getWriter().print(result);
				return null;
			}

			// 删除购物车指定商品
			Boolean flag = shoppingManager.deleteCartProduct(
					Util.getLoginUser(session).getName(), productId);
			if (!flag) {
				result = "2";
				response.getWriter().print(result);
				return null;
			}

			result = "success";
			response.getWriter().print(result);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result = "2";
			response.getWriter().print(result);
			return null;
		}
	}
}
