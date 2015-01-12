package com.yiqin.shop.serviceImpl;

import com.yiqin.shop.bean.User;
import com.yiqin.shop.service.UserManager;

public class UserManagerImpl implements UserManager {

	@Override
	public boolean registeUser(User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public User login(String name, String password) {
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		return user;
	}

	@Override
	public User findUserByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
