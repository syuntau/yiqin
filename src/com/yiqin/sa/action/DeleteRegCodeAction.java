package com.yiqin.sa.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.UserManager;

/**
 * 删除指定邀请码
 * 
 * @author liujun
 * 
 */
public class DeleteRegCodeAction extends ActionSupport {

	private static final long serialVersionUID = 7507605789567573350L;

	// 邀请码
	private String regCode;

	private UserManager userManager;

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (GenericValidator.isBlankOrNull(regCode)) {
				result = "2";
				response.getWriter().print(result);
				return null;
			}
			// 删除邀请码
			boolean flag = userManager.deleteRegCode(regCode);
			if (!flag) {
				result = "2";
				response.getWriter().print(result);
				return null;
			}
			result = "1";
			response.getWriter().print(result);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result = "3";
			response.getWriter().print(result);
			return null;
		}
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

}
