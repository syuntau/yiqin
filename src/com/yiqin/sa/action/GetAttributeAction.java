package com.yiqin.sa.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Attribute;
import com.yiqin.service.ProductManager;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class GetAttributeAction extends ActionSupport {

	private static final long serialVersionUID = 1287552853932187171L;
	private String cId;
	private String aId;
	private ProductManager productManager;

	public String getCId() {
		return cId;
	}

	public void setCId(String cId) {
		this.cId = cId;
	}

	public String getAId() {
		return aId;
	}

	public void setAId(String aId) {
		this.aId = aId;
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public String getList() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(cId) || !Util.isNumeric(cId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				int categoryId = Integer.parseInt(cId);
				List<Attribute> attributeList = productManager.findAttributeByCategoryId(categoryId);
				if (Util.isEmpty(attributeList)) {
					result = UtilKeys.CODE_NO_RESULT;
					out.print(result);
					return null;
				}

				JSONArray jsArray = JSONArray.fromObject(attributeList);
				result = jsArray.toString();
				out.print(result);
				return null;
			}
		} catch (IOException e1) {
			System.out.println("error in GetAttributeAction.getList for make printwriter");
			e1.printStackTrace();
			return null;
		}
	}

	public String getAttr() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(aId) || !Util.isNumeric(aId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				int id = Integer.parseInt(aId);
				Attribute attribute = productManager.findAttributeById(id);
				if (attribute == null) {
					result = UtilKeys.CODE_NO_RESULT;
					out.print(result);
					return null;
				}

				JSONArray jsArray = JSONArray.fromObject(attribute);
				result = jsArray.toString();
				out.print(result);
				return null;
			}
		} catch (IOException e1) {
			System.out.println("error in GetAttributeAction.getAttr for make printwriter");
			e1.printStackTrace();
			return null;
		}
	}
}
