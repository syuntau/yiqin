package com.yiqin.shop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.UserManager;
import com.yiqin.util.Util;

/**
 * 删除用户地址信息
 * 
 * @author liujun
 *
 */
public class DeleteUserAddressAction extends ActionSupport {

	private static final long serialVersionUID = 488687196178286422L;

	private UserManager userManager;

	// 地址属性
	private String attribute;

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		String result = "";
		try {
			if (Util.isEmpty(attribute)) {
				result = "1";
				response.getWriter().print(result);
				return null;
			}
			// 删除信息
			boolean flag = userManager.deleteUserConf(
					Util.getLoginUser(request.getSession()).getId(), attribute);
			if (!flag) {
				result = "2";
				response.getWriter().print(result);
				return null;
			}
			result = "3";
			response.getWriter().print(result);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result = "2";
			response.getWriter().print(result);
			return null;
		}
	}
}
