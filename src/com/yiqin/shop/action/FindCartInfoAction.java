package com.yiqin.shop.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Cart;
import com.yiqin.pojo.User;
import com.yiqin.service.ShoppingManager;
import com.yiqin.util.Util;

/**
 * 我的购物车
 * 
 * @author LiuJun
 * 
 */
public class FindCartInfoAction extends ActionSupport {

	private static final long serialVersionUID = -5662631274249628276L;

	private ShoppingManager shoppingManager;

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
			// 获取当前用户
			User loninUser = Util.getLoginUser(session);

			// 查询用户购物车
			List<Cart> cartList = shoppingManager.findCartListInfo(loninUser.getId());
			if (Util.isEmpty(cartList)) {
				result = "1";
			} else {
				JSONArray jsArray = JSONArray.fromObject(cartList);
				result = jsArray.toString();
			}
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
