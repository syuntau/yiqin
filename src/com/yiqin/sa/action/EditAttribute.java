package com.yiqin.sa.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Attribute;
import com.yiqin.service.ProductManager;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class EditAttribute extends ActionSupport {

	private static final long serialVersionUID = 6703674908055877022L;
	private ProductManager productManager;
	private String attrId;
	private String categoryId;
	private Attribute attr;

	public Attribute getAttr() {
		return attr;
	}
	public void setAttr(Attribute attr) {
		this.attr = attr;
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
	public String getAttrId() {
		return attrId;
	}
	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String removeAll() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(categoryId) || !Util.isNumeric(categoryId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					productManager.deleteAllAttribute(categoryId);
				} catch(DataAccessException dbe) {
					System.out.println("error in RemoveAttribute.removeAll for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}

				result = UtilKeys.CODE_SUCCESS;
				out.print(result);
			}
		} catch (IOException e1) {
			System.out.println("error in RemoveAttribute.removeAll for io exception");
			e1.printStackTrace();
		}
		return null;
	}

	public String removeAttr() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(attrId) || !Util.isNumeric(attrId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					productManager.deleteAttribute(attrId);
				} catch(DataAccessException dbe) {
					System.out.println("error in RemoveAttribute.removeAttr for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}

				result = UtilKeys.CODE_SUCCESS;
				out.print(result);
			}
		} catch (IOException e1) {
			System.out.println("error in RemoveAttribute.removeAttr for io exception");
			e1.printStackTrace();
		}
		return null;
	}

	public String updateAttr() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (attr == null) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					productManager.updateAttribute(attr);
				} catch(DataAccessException dbe) {
					System.out.println("error in RemoveAttribute.editAttr for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}

				JSONObject json = JSONObject.fromObject(attr);
				result = json.toString();
				out.print(result);
			}
		} catch (IOException e1) {
			System.out.println("error in RemoveAttribute.editAttr for io exception");
			e1.printStackTrace();
		}
		return null;
	}

	public String saveAttr() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (attr == null) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					int attrId = productManager.saveAttribute(attr);
					attr.setId(attrId);
					JSONObject json = JSONObject.fromObject(attr);
					result = json.toString();
					out.print(result);
				} catch(DataAccessException dbe) {
					System.out.println("error in RemoveAttribute.saveAttr for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}
			}
		} catch (IOException e1) {
			System.out.println("error in RemoveAttribute.saveAttr for io exception");
			e1.printStackTrace();
		}
		return null;
	}
}
