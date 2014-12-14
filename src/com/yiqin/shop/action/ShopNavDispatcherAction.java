package com.yiqin.shop.action;

import java.util.Map;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.util.UtilKeys;

public class ShopNavDispatcherAction extends ActionSupport {

	/** serialVersionUID */
	private static final long serialVersionUID = 139372407845465715L;

	public String execute() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		String dispatcher = (String) session.get(UtilKeys.SE_SHOP_NAV);
		return dispatcher;
	}
}
