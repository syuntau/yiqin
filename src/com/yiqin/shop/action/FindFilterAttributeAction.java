package com.yiqin.shop.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Attribute;
import com.yiqin.service.ProductManager;
import com.yiqin.util.Util;

/**
 * 查询商品页检索过滤属性
 * 
 * @author liujun
 *
 */
public class FindFilterAttributeAction extends ActionSupport {

	private static final long serialVersionUID = 343860824332165086L;

	// 2级分类ID
	private String categoryId;

	private ProductManager productManager;

	@Override
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (Util.isEmpty(categoryId)) {
				result = "1";
			} else {
				List<Attribute> list = productManager.findFilterAttribute(Integer.valueOf(categoryId));
				if (Util.isEmpty(list)) {
					result = "1";
				} else {
					JSONArray jsonArray = JSONArray.fromObject(list);
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
}
