package com.yiqin.shop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.ShoppingManager;
import com.yiqin.util.Util;

/**
 * 查询购物车数量
 * 
 * @author liujun
 *
 */
public class FindCartNumberAction extends ActionSupport {

	private static final long serialVersionUID = 1690431205134101920L;

	private ShoppingManager shoppingManager;

	public ShoppingManager getShoppingManager() {
		return shoppingManager;
	}

	public void setShoppingManager(ShoppingManager shoppingManager) {
		this.shoppingManager = shoppingManager;
	}

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			// 查询购物车数量
			int count = shoppingManager.findCartNum(Util.getLoginUser(session).getId());
			result = String.valueOf(count);
			response.getWriter().print(result);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
			response.getWriter().print(result);
			return null;
		}
	}
}
