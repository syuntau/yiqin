package com.yiqin.shop.action;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.bean.User;
import com.yiqin.shop.service.UserManager;
import com.yiqin.util.Util;

public class RegisteredUserAction extends ActionSupport {

	private static final long serialVersionUID = 7088615138788787514L;
	private String name;
	private String password;
	private String confirmPwd;
	private String email;
	private String telephone;
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

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

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

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=UTF-8");
		String result = "";
		try {
			// 参数判断
			if (Util.isEmpty(name) || Util.isEmpty(password)
					|| Util.isEmpty(confirmPwd) || Util.isEmpty(email)
					|| Util.isEmpty(telephone)) {
				result = "2";
				response.getWriter().print(result);
				return null;
			}

			// 密码校验
			if (!password.equals(confirmPwd)) {
				result = "3";
				response.getWriter().print(result);
				return null;
			}

			// 判断用户名是否重复
			User existUser = userManager.findUserByName(name);
			if (existUser != null) {
				result = "4";
				response.getWriter().print(result);
				return null;
			}

			// 添加用户
			User tempUser = new User();
			tempUser.setName(name);
			tempUser.setPassword(password);
			tempUser.setEmail(email);
			tempUser.setTelephone(telephone);
			tempUser.setRegtime(new Date());
			boolean flag = userManager.registeUser(tempUser);
			if (!flag) {
				result = "5";
				response.getWriter().print(result);
				return null;
			}
			result = "1";
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
