package com.yiqin.shop.serviceImpl;

import com.yiqin.shop.dao.IUserDao;
import com.yiqin.shop.pojo.User;
import com.yiqin.shop.service.UserManager;
import com.yiqin.util.Util;

public class UserManagerImpl implements UserManager {

	private IUserDao userDao;

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean registeUser(User user) {
		if (Util.isEmpty(user.getId())) {
			return false;
		}
		return userDao.insertUser(user);
	}

	@Override
	public User login(String userId, String password) {
		User user = userDao.findUserInfo(userId, password);
		return user;
	}

	@Override
	public User findUserByUserId(String userId) {
		User user = userDao.findUserByUserId(userId);
		return user;
	}

	@Override
	public boolean modifyUserInfo(User user) {
		return userDao.modifyUserInfo(user);
	}

	@Override
	public boolean deleteUser(String userId) {
		return userDao.deleteUser(userId);
	}
}
