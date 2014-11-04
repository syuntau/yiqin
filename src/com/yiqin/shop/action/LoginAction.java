package com.yiqin.shop.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.bean.UserForm;
import com.yiqin.shop.service.UserManager;
import com.yiqin.shop.serviceImpl.UserManagerImpl;

public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 316466680810245739L;
	private UserForm user;
	private UserManager userManager;

	public UserForm getUser() {
		return user;
	}
	public void setUser(UserForm user) {
		this.user = user;
	}
	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String execute() {
		try {
//			this.setUserManager(new UserManagerImpl());
//			userManager.registeUser(user);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
}
