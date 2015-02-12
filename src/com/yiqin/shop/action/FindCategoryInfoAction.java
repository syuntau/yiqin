package com.yiqin.shop.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.pojo.Category;
import com.yiqin.shop.service.ProductManager;
import com.yiqin.util.Util;

/**
 * 查询分类菜单
 * 
 * @author LiuJun
 * 
 */
public class FindCategoryInfoAction extends ActionSupport {

	private static final long serialVersionUID = -5628712416578803631L;

	private ProductManager productManager;

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
			List<Category> category = productManager.findCategoryInfo();
			if (Util.isEmpty(category)) {
				result = "1";
				response.getWriter().print(result);
				return null;
			}

			JSONArray jsArray = JSONArray.fromObject(category);
			result = jsArray.toString();
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
