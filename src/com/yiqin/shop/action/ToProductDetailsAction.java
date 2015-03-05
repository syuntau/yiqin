package com.yiqin.shop.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

/**
 * 点击商品详情
 * 
 * @author liujun
 *
 */
public class ToProductDetailsAction extends ActionSupport {

	private static final long serialVersionUID = -4071931669942921993L;

	// 商品ID
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
			request.setAttribute("search_productId", paramVal);
			String shop_nav = "top_" + paramVal.substring(0, 1);
			request.getSession().setAttribute(UtilKeys.SE_SHOP_NAV, shop_nav);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
