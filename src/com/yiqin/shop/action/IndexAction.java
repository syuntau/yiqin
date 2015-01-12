package com.yiqin.shop.action;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.util.UtilKeys;

public class IndexAction extends ActionSupport {

	private static final long serialVersionUID = -8369155314223762864L;
	private String home;

	public String setHome(String home) {
		return this.home = home;
	}

	public String execute() {
		ServletActionContext.getRequest().setAttribute(UtilKeys.REQ_SHOP_NAV, home);
		return SUCCESS;
	}
}
