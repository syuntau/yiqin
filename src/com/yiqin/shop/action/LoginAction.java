package com.yiqin.shop.action;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.bean.User;
import com.yiqin.shop.service.UserManager;
import com.yiqin.util.Util;

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
			// 参数判断
			if (Util.isEmpty(name) || Util.isEmpty(password)) {
				ServletActionContext.getRequest().setAttribute("errorType",
						"用户名或密码不能为空");
				return LOGIN;
			}

			// 查询信息
			User userInfo = userManager.login(name, password);
			if (null == userInfo) {
				ServletActionContext.getRequest().setAttribute("errorType",
						"用户名或密码错误，请重新输入");
				return LOGIN;
			}

			// 储存登录信息
			ServletActionContext.getRequest().getSession()
					.setAttribute("userInfo", userInfo);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return LOGIN;
		}
	}
}
