package com.yiqin.shop.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Cart;
import com.yiqin.service.ShoppingManager;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

/**
 * 去结算购物车商品
 * 
 * @author liujun
 *
 */
public class ToSettlementOrderAction extends ActionSupport {

	private static final long serialVersionUID = -2858396512919173716L;

	// 商品ID （多个用,分隔）
	private String paramVal;

	private ShoppingManager shoppingManager;

	public String getParamVal() {
		return paramVal;
	}

	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}

	public ShoppingManager getShoppingManager() {
		return shoppingManager;
	}

	public void setShoppingManager(ShoppingManager shoppingManager) {
		this.shoppingManager = shoppingManager;
	}

	public String execute() throws Exception {
		try {
			if (Util.isEmpty(paramVal)) {
				return null;
			}
			HttpServletRequest request = ServletActionContext.getRequest();
			List<Cart> cartList = shoppingManager.findCartsByProductIds(Util
					.getLoginUser(request.getSession()).getId(), paramVal);
			request.setAttribute("settlement_products", cartList);
			request.setAttribute("submit_ProductIds", paramVal);
			request.getSession().setAttribute(UtilKeys.SE_SHOP_NAV, "top_sett");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
