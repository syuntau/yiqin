package com.yiqin.sa.interceptor;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yiqin.dao.impl.UserDao;
import com.yiqin.pojo.SAUser;
import com.yiqin.util.Util;

public class LoginSAInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 7568121306987759238L;
	public static final String USER_SESSION_KEY = "yiqin.sa.user";
	public static final String COOKIE_REMEMBERME_KEY = "yiqin.sa.cookie.rememberme";
	public static final String GOING_TO_URL_KEY = "GOING_TO";
	public UserDao userDao;

    public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);

		Map session = actionContext.getSession();
		if (session != null && session.get(USER_SESSION_KEY) != null) {
			return invocation.invoke();
		}

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (COOKIE_REMEMBERME_KEY.equals(cookie.getName())) {
					String value = cookie.getValue();
					if (Util.isNotEmpty(value)) {
						String[] split = value.split("||");
						String userName = split[0];
						String password = split[1];
						
						SAUser user = userDao.isLoginSA(userName, password);
						if (user != null) {
							session.put(USER_SESSION_KEY, user);
						} else {
							setGoingToURL(session, invocation);
							return Action.LOGIN;
						}
					} else {
						setGoingToURL(session, invocation);
						return Action.LOGIN;
					}
					return invocation.invoke();
				}
			}
		}
		setGoingToURL(session, invocation);
		return Action.LOGIN;
	}

	private void setGoingToURL(Map session, ActionInvocation invocation) {
		String url = "";
		String namespace = invocation.getProxy().getNamespace();
		if (Util.isNotEmpty(namespace) && !namespace.equals("/")) {
			url = url + namespace;
		}
		String actionName = invocation.getProxy().getActionName();
		if (Util.isNotEmpty(actionName)) {
			url = url + "/" + actionName;
		}
		session.put(GOING_TO_URL_KEY, url);
	}

}
