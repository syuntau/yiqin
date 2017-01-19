package com.yiqin.shop.action;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.ProductManager;
import com.yiqin.util.Util;

/**
 * 查询产品的所有属性和属性值
 * 
 * @author LiuJun
 * 
 */
public class FindProductDetailAction extends ActionSupport {

	private static final long serialVersionUID = -4074783890526182368L;

	private String productId;

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

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (Util.isEmpty(productId)) {
				result = "1";
				response.getWriter().print(result);
				return null;
			}
			Map<String, Map<String, String>> product = productManager.findProductDetailByIds(productId);
			JSONObject jsObject = JSONObject.fromObject(product);
			result = jsObject.toString();
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
