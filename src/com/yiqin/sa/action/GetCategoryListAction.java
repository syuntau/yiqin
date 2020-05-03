package com.yiqin.sa.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.ProductManager;
import com.yiqin.util.CategorySimple;
import com.yiqin.util.CategoryUtil;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class GetCategoryListAction extends ActionSupport {

	private static final long serialVersionUID = -6306668186602136040L;
	private String cId;
	private ProductManager productManager;

	public String getCId() {
		return cId;
	}

	public void setCId(String cId) {
		this.cId = cId;
	}

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
					result = UtilKeys.CODE_NO_RESULT;
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

			HttpServletRequest request = ServletActionContext.getRequest();
			String cId = request.getParameter("cId");

			if (Util.isEmpty(cId) || !Util.isNumeric(cId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				return null;
			}
			List<CategorySimple> list = CategoryUtil.getCategoryListById(cId);
			System.out.println("getList cId list："+list.size());
			if (Util.isEmpty(list)) {
				result = UtilKeys.CODE_NO_RESULT;
				out.print(result);
				return null;
			}

			JSONArray jsArray = JSONArray.fromObject(list);
			result = jsArray.toString();
			out.print(result);
			System.out.println("result："+result);
			return null;
		} catch (IOException e1) {
			System.out.println("error in GetCategoryListAction.getList for make printwriter");
			e1.printStackTrace();
			return null;
		}
	}
}
