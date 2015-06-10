package com.yiqin.sa.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.sa.interceptor.LoginSAInterceptor;

public class LogoutAction extends ActionSupport implements ServletRequestAware,	ServletResponseAware {
	
	private static final long serialVersionUID = 8899556798776026251L;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public String execute() throws Exception {
		HttpSession session = request.getSession(false);
		if (session != null)
			session.removeAttribute(LoginSAInterceptor.USER_SESSION_KEY);

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (LoginSAInterceptor.COOKIE_REMEMBERME_KEY.equals(cookie.getName())) {
					cookie.setValue("");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					return SUCCESS;
				}
			}
		}
		return SUCCESS;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
}
