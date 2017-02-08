package com.yiqin.shop.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.ProductManager;
import com.yiqin.util.CategoryUtil;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

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
		HttpServletRequest request = ServletActionContext.getRequest();
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

			StringBuilder nav = new StringBuilder();
			String cateId = productId.substring(0, 4);
			String firstName = CategoryUtil.getCategoryName(Integer.parseInt(cateId.substring(0, 1)));
			String secondName = CategoryUtil.getCategoryName(Integer.parseInt(cateId.substring(0, 2)));
			String thirdName = CategoryUtil.getCategoryName(Integer.parseInt(cateId));
			nav.append(firstName).append(",").append(secondName).append(":").append(thirdName);

			request.getSession().setAttribute(UtilKeys.SE_PRODUCT_NAV, nav.toString());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result = "2";
			response.getWriter().print(result);
			return null;
		}
	}
}
