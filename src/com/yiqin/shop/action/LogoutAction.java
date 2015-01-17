package com.yiqin.shop.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

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
		Object userObj = session.getAttribute("userInfo");
		if (userObj != null) {
			session.removeAttribute("userInfo");
		}

		// 清空session
		Enumeration<?> em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		return LOGIN;
	}
}
