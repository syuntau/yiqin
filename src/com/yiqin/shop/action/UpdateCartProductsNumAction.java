package com.yiqin.shop.action;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.User;
import com.yiqin.service.ShoppingManager;
import com.yiqin.util.Util;

/**
 * 修改购物车商品数量
 * 
 * @author LiuJun
 * 
 */
public class UpdateCartProductsNumAction extends ActionSupport {

	private static final long serialVersionUID = -5966255583359874907L;
	// 商品ID
	private String productId;
	// 商品数量
	private int productNum;

	private ShoppingManager shoppingManager;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	public ShoppingManager getShoppingManager() {
		return shoppingManager;
	}

	public void setShoppingManager(ShoppingManager shoppingManager) {
		this.shoppingManager = shoppingManager;
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

			// 获取当前用户
			User loninUser = Util.getLoginUser(session);

			// 更新数量
			boolean flag = shoppingManager.updateCartProductsNum(
					loninUser.getId(), productId, productNum);
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
