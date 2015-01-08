package com.yiqin.shop.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.bean.User;
import com.yiqin.shop.service.UserManager;
import com.yiqin.util.Util;

public class RegisteredUserAction extends ActionSupport {

	private static final long serialVersionUID = 7088615138788787514L;
	private User user;
	private UserManager userManager;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String result = "1";
		try {
			// 参数判断
			if (Util.isEmpty(user.getName())
					|| Util.isEmpty(user.getPassword())
					|| Util.isEmpty(user.getConfirmPwd())
					|| Util.isEmpty(user.getEmail())
					|| Util.isEmpty(user.getTelephone())) {
				result = "2";
				return SUCCESS;
			}
			
			//格式校验
			
			if(!user.getPassword().equals(user.getConfirmPwd())){
				result = "3";
				return SUCCESS;
			}

			// 判断用户名是否重复
			User existUser = userManager.findUserByName(user.getName());
			if (existUser != null) {
				result = "4";
				return SUCCESS;
			}

			// 添加用户
			user.setRegtime(new Date());
			boolean flag = userManager.registeUser(user);
			if (!flag) {
				result = "5";
				return SUCCESS;
			}
			return SUCCESS;
		} catch (Exception e) {
			result = "5";
			return SUCCESS;
		}
	}
}
