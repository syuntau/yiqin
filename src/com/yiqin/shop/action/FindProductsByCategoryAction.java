package com.yiqin.shop.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.bean.ProductView;
import com.yiqin.shop.service.ProductManager;
import com.yiqin.util.Util;

public class FindProductsByCategoryAction extends ActionSupport {

	private static final long serialVersionUID = -6676005768684862992L;

	// 分类ID
	private String categoryId;

	private ProductManager productManager;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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
			if (Util.isEmpty(categoryId)) {
				result = "1";
			} else {
				// 查询对应分类产品
				List<ProductView> product = productManager.findProductInfo(categoryId);
				if (Util.isEmpty(product)) {
					result = "1";
				} else {
					JSONArray jsonArray = JSONArray.fromObject(product);
					result = jsonArray.toString();
				}
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
