package com.yiqin.shop.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.User;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

/**
 * 退出登录
 * 
 * @author LiuJun
 * 
 */
public class LogoutAction extends ActionSupport {

	private static final long serialVersionUID = -8320063752515184933L;

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		// 获取当前用户
		User loninUser = Util.getLoginUser(session);
		if (loninUser != null) {
			session.removeAttribute("userInfo");
		}

		// 清空session
		Enumeration<?> em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		request.getSession().setAttribute(UtilKeys.SE_SHOP_NAV, "top_login");
		return LOGIN;
	}
}
