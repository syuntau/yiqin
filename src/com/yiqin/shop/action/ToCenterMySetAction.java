package com.yiqin.shop.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.UserManager;
import com.yiqin.util.Util;

/**
 * 个人设置项跳转
 * 
 * @author liujun
 * 
 */
public class ToCenterMySetAction extends ActionSupport {

	private static final long serialVersionUID = -4071931669942921993L;

	// 参数
	private String paramVal;

	private UserManager userManager;

	public String getParamVal() {
		return paramVal;
	}

	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String execute() throws Exception {
		try {
			if (Util.isEmpty(paramVal)) {
				return null;
			}
			if (paramVal.equals("info")) {
				return "userset";
			} else if (paramVal.equals("address")) {
				return "address";
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
