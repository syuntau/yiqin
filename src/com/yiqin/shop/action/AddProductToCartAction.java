package com.yiqin.shop.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Cart;
import com.yiqin.pojo.User;
import com.yiqin.service.ProductManager;
import com.yiqin.service.ShoppingManager;
import com.yiqin.shop.bean.ProductView;
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
	
	//添加数量
	private Integer num;
	
	private ShoppingManager shoppingManager;
	private ProductManager productManager;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
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
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (Util.isEmpty(productId)) {
				result = "none";
				response.getWriter().print(result);
				return null;
			}
			// 查询商品
			List<ProductView> productList = productManager.findProductInfoById(productId);
			if (Util.isEmpty(productList)) {
				result = "none";
				response.getWriter().print(result);
				return null;
			}
			
			// 获取当前用户
			User loninUser = Util.getLoginUser(session);
			
			// 保存购物信息
			ProductView product = productList.get(0);
			Cart cart = new Cart();
			cart.setUseId(loninUser.getId());
			cart.setProductId(product.getProductId());
			cart.setProductName(product.getProductName());
			cart.setPrice(Float.valueOf(product.getPrice()));
			cart.setImgUrl(product.getImgUrl());
			cart.setCount(num);
			cart.setProductInfo("");
			boolean flag = shoppingManager.addProductToCart(cart);
			if(!flag){
				result = "error";
				response.getWriter().print(result);
				return null;
			}
			
			// 最新购物车数量
			int nowCount = shoppingManager.findCartNum(loninUser.getId());
			result = String.valueOf(nowCount);
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
