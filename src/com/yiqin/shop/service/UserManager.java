package com.yiqin.shop.service;

import com.yiqin.shop.bean.User;

public interface UserManager {

	public boolean registeUser(User user);

	public User login(String name, String password);

	public User findUserByName(String name);

	public boolean modifyUserInfo(User user);
}
