package com.yiqin.shop.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

/**
 * 点击分类查询
 * 
 * @author liujun
 *
 */
public class ToCategorySearchAction extends ActionSupport {

	private static final long serialVersionUID = 7485249569971109214L;
	// 分类ID
	private String paramVal;

	public String getParamVal() {
		return paramVal;
	}

	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}

	public String execute() throws Exception {
		try {
			if (Util.isEmpty(paramVal)) {
				return null;
			}
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("search_categoryId", paramVal);
			String shop_nav = "top_" + paramVal.substring(0, 1);
			request.getSession().setAttribute(UtilKeys.SE_SHOP_NAV, shop_nav);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
