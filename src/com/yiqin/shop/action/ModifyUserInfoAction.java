package com.yiqin.shop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.bean.User;
import com.yiqin.shop.service.UserManager;
import com.yiqin.util.Util;

/**
 * 修改用户信息
 * 
 * @author LiuJun
 * 
 */
public class ModifyUserInfoAction extends ActionSupport {

	private static final long serialVersionUID = 4384288723004399437L;
	// 邮箱
	private String email;
	// 手机
	private String telephone;
	// 密码
	private String password;
	// 确认密码
	private String confirmPwd;
	// 修改类型(email、telephone、password)
	private String modifyType;

	private UserManager userManager;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getModifyType() {
		return modifyType;
	}

	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "success";
		try {
			// 获取当前用户
			Object userObj = session.getAttribute("userInfo");
			User loninUser = (User) userObj;

			// 设置信息
			if ("email".equals(modifyType)) {
				if (Util.isEmpty(email)) {
					result = "1";
				} else {
					loninUser.setEmail(email);
				}
			} else if ("telephone".equals(modifyType)) {
				if (Util.isEmpty(telephone)) {
					result = "2";
				} else {
					loninUser.setTelephone(telephone);
				}
			} else if ("password".equals(modifyType)) {
				if (Util.isEmpty(password)) {
					result = "3";
				} else if (!password.equals(confirmPwd)) {
					result = "4";
				} else {
					loninUser.setPassword(password);
				}
			}
			// 修改用户
			boolean flag = userManager.modifyUserInfo(loninUser);
			if (!flag) {
				result = "5";
			} else {
				session.setAttribute("userInfo", loninUser);
			}
			response.getWriter().print(result);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result = "5";
			response.getWriter().print(result);
			return null;
		}
	}
}
