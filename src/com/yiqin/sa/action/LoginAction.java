package com.yiqin.sa.action;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.CookiesAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.dao.IUserDao;
import com.yiqin.pojo.SAUser;
import com.yiqin.sa.interceptor.LoginSAInterceptor;

public class LoginAction extends ActionSupport implements ServletResponseAware,
		ServletRequestAware, SessionAware, CookiesAware {

	private static final long serialVersionUID = -471002733193803337L;
	private IUserDao userDao;
	private String userId;
	private String password;
	private boolean rememberMe;
	private HttpServletResponse response;
	private HttpServletRequest request;
	private Map<String, Object> session;
	private Map cookies;
	private String goingToURL;

	public String getGoingToURL() {
		return goingToURL;
	}

	public void setGoingToURL(String goingToURL) {
		this.goingToURL = goingToURL;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setCookiesMap(Map cookies) {
		this.cookies = cookies;
	}

	public String execute() {
		SAUser user = userDao.isLoginSA(userId, password);
		if (user == null) {
			addActionMessage("用户名或密码错误，请重试。");
			return INPUT;
		}
		if (rememberMe) {
			Cookie cookie = new Cookie(
					LoginSAInterceptor.COOKIE_REMEMBERME_KEY,
					new StringBuilder().append(userId).append("||").append(password).toString());
			cookie.setMaxAge(60 * 60 * 24 * 14);
			response.addCookie(cookie);
		}
		session.put(LoginSAInterceptor.USER_SESSION_KEY, user);
		String roles = getText("sa.role." + user.getRole());
		session.put("yiqin_sa_user_roles", roles);
//		String goingToURL = (String) session.get(LoginSAInterceptor.GOING_TO_URL_KEY);
//		if (Util.isNotEmpty(goingToURL)) {
//			setGoingToURL(goingToURL);
//			session.remove(LoginSAInterceptor.GOING_TO_URL_KEY);
//		} else {
//			return "welcome";
//		}
		return SUCCESS;
	}
}
