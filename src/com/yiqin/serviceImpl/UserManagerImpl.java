package com.yiqin.serviceImpl;

import java.util.List;

import com.yiqin.dao.IUserDao;
import com.yiqin.pojo.SAUser;
import com.yiqin.pojo.User;
import com.yiqin.pojo.UserConf;
import com.yiqin.service.UserManager;
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
		if (user == null || Util.isEmpty(user.getId())) {
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
		if (user == null) {
			return false;
		}
		return userDao.modifyUserInfo(user);
	}

	@Override
	public boolean deleteUser(String userId) {
		return userDao.deleteUser(userId);
	}

	@Override
	public List<UserConf> findUserAddressList(String userId) {
		return userDao.findUserAddressList(userId);
	}

	@Override
	public boolean updateUserConf(UserConf userConf) {
		if (userConf == null) {
			return false;
		}
		return userDao.updateUserConf(userConf);
	}

	@Override
	public UserConf findUserConfInfo(String userId, String attribute) {
		return userDao.findUserConfInfo(userId, attribute);
	}

	@Override
	public boolean deleteUserConf(String userId, String attribute) {
		return userDao.deleteUserConf(userId, attribute);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public String saveSAUser(SAUser user) {
		try {
			String id = userDao.saveSAUser(user);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateSAUser(SAUser user) {
		try {
			userDao.updateSAUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteSAUser(String id) {
		try {
			userDao.deleteSAUser(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<SAUser> findAdmin(int role) {
		try {
			return userDao.findAdmin(role);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public SAUser findSAUser(String id, String password) {
		return userDao.isLoginSA(id, password);
	}

}
