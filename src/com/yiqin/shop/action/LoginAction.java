package com.yiqin.shop.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.bean.User;
import com.yiqin.shop.service.UserManager;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 316466680810245739L;
	private String name;
	private String password;
	private UserManager userManager;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String execute() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			// 参数判断
			if (Util.isEmpty(name) || Util.isEmpty(password)) {
				request.setAttribute("loginError", "用户名或密码不能为空");
				return LOGIN;
			}

			// 查询信息
			User userInfo = userManager.login(name, password);
			if (null == userInfo) {
				request.setAttribute("loginError", "用户名或密码错误，请重新输入");
				return LOGIN;
			}

			// 储存登录信息
			request.getSession().setAttribute("userInfo", userInfo);
			request.setAttribute(UtilKeys.REQ_SHOP_NAV, request.getSession().getAttribute(UtilKeys.SE_SHOP_ORIGNAL_NAV));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return LOGIN;
		}
	}
}
