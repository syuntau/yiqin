package com.yiqin.sa.action;

import java.util.Map;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.util.UtilKeys;

public class SANavDispatcherAction extends ActionSupport {

	/** serialVersionUID */
	private static final long serialVersionUID = 2017682791474549212L;
	private String para_nav;

	public String execute() {
		ActionContext actionContext = ActionContext.getContext();
		@SuppressWarnings("unchecked")
		Map<String, Object> request = (Map<String, Object>) actionContext.get("request");
		request.put(UtilKeys.RQ_SA_NAV, para_nav);
		return para_nav;
	}

	public String getPara_nav() {
		return para_nav;
	}

	public void setPara_nav(String para_nav) {
		this.para_nav = para_nav;
	}
}
