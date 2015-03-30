package com.yiqin.sa.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.ProductManager;
import com.yiqin.util.CategorySimple;
import com.yiqin.util.CategoryUtil;
import com.yiqin.util.Util;

public class GetCategoryListAction extends ActionSupport {

	private static final long serialVersionUID = -6306668186602136040L;
	private String categoryId;
	private ProductManager productManager;

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public String getFirst() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			CategoryUtil.init(productManager);
			List<CategorySimple> list = CategoryUtil.getFirstCategory();
				if (Util.isEmpty(list)) {
					result = "1";
					out.print(result);
					return null;
				}

				JSONArray jsArray = JSONArray.fromObject(list);
				result = jsArray.toString();
				out.print(result);
				return null;
		} catch (Exception e1) {
			System.out.println("error in GetCategoryListAction.getFirst for make printwriter");
			e1.printStackTrace();
			return null;
		}
	}

	public String getList() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(categoryId)) {
				result = "1";
				return null;
			}
			List<CategorySimple> list = CategoryUtil.getCategoryListById(categoryId);
			if (Util.isEmpty(list)) {
				result = "2";
				out.print(result);
				return null;
			}

			JSONArray jsArray = JSONArray.fromObject(list);
			result = jsArray.toString();
			out.print(result);
			return null;
		} catch (IOException e1) {
			System.out.println("error in GetCategoryListAction.getList for make printwriter");
			e1.printStackTrace();
			return null;
		}
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
