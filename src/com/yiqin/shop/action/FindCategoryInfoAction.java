package com.yiqin.shop.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Category;
import com.yiqin.service.ProductManager;
import com.yiqin.util.Util;

/**
 * 查询分类菜单
 * 
 * @author LiuJun
 * 
 */
public class FindCategoryInfoAction extends ActionSupport {

	private static final long serialVersionUID = -5628712416578803631L;
	
	// 顶级分类ID
	private String topCateId;
	
	public String getTopCateId() {
		return topCateId;
	}

	public void setTopCateId(String topCateId) {
		this.topCateId = topCateId;
	}

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
			// 查询分类菜单
			List<Category> category = null;
			if(Util.isEmpty(topCateId)){
				category = productManager.findCategoryInfo();
			}else{
				category = productManager.findCategoryInfo(Integer.valueOf(topCateId));
			}
			if (Util.isEmpty(category)) {
				result = "1";
				response.getWriter().print(result);
				return null;
			}

			JSONArray jsArray = JSONArray.fromObject(category);
			System.out.println(jsArray.size());
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
