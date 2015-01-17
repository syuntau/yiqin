package com.yiqin.shop.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.bean.Cart;
import com.yiqin.shop.bean.OfficeProductBase;
import com.yiqin.shop.bean.User;
import com.yiqin.shop.service.ProductManager;
import com.yiqin.shop.service.ShoppingManager;
import com.yiqin.util.Util;

/**
 * 添加到购物车
 * 
 * @author LiuJun
 * 
 */
public class AddProductToCartAction extends ActionSupport {

	private static final long serialVersionUID = -6193394773099215639L;
	//商品ID
	private String productId;
	
	private ShoppingManager shoppingManager;
	private ProductManager productManager;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

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
		response.setContentType("text/json;charset=UTF-8");
		String result = "";
		try {
			if (Util.isEmpty(productId)) {
				result = "1";
				response.getWriter().print(result);
				return null;
			}
			// 查询商品
			OfficeProductBase oProductBase = productManager.findProductInfoById(productId);
			if (null == oProductBase) {
				result = "1";
				response.getWriter().print(result);
				return null;
			}
			
			// 获取当前用户
			Object userObj = session.getAttribute("userInfo");
			User loninUser = (User) userObj;
			
			// 保存购物信息
			Cart cart = new Cart();
			cart.setCreateDate(new Date());
			cart.setName(loninUser.getName());
			cart.setProductId(oProductBase.getId());
			cart.setProductName(oProductBase.getName());
			cart.setPrice(oProductBase.getPrice());
			cart.setImageUrl(oProductBase.getImageUrl());
			cart.setProductNum(1);
			boolean flag = shoppingManager.addProductToCart(cart);
			if(!flag){
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
