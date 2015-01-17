package com.yiqin.shop.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.bean.User;
import com.yiqin.shop.service.UserManager;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

/**
 * 用户登录
 * 
 * @author LiuJun
 * 
 */
public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 316466680810245739L;
	// 登录用户名
	private String login_name;
	// 登录密码
	private String login_password;
	private UserManager userManager;

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getLogin_password() {
		return login_password;
	}

	public void setLogin_password(String login_password) {
		this.login_password = login_password;
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
			if (Util.isEmpty(login_name) || Util.isEmpty(login_password)) {
				request.setAttribute("loginError", "用户名或密码不能为空");
				return LOGIN;
			}

			// 查询信息
			User userInfo = userManager.login(login_name, login_password);
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
