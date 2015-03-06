package com.yiqin.shop.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yiqin.shop.dao.IUserDao;
import com.yiqin.shop.pojo.User;
import com.yiqin.shop.pojo.UserConf;
import com.yiqin.util.Util;

public class UserDao extends HibernateDaoSupport implements IUserDao {

	@Override
	public boolean insertUser(User user) {
		try {
			getHibernateTemplate().saveOrUpdate(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User findUserInfo(String userId, String password) {
		String queryString = "from User where id=? and password=?";
		List<?> list = getHibernateTemplate().find(queryString,
				new Object[] { userId, password });
		if (Util.isNotEmpty(list)) {
			return (User) list.get(0);
		}
		return null;
	}

	@Override
	public User findUserByUserId(String userId) {
		Object object = getHibernateTemplate().get(User.class, userId);
		if (object != null) {
			return (User) object;
		}
		return null;
	}

	@Override
	public boolean modifyUserInfo(User user) {
		try {
			getHibernateTemplate().saveOrUpdate(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteUser(String userId) {
		try {
			User user = findUserByUserId(userId);
			if (user != null) {
				getHibernateTemplate().delete(user);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<UserConf> findUserConfList(String userId) {
		String queryString = "from UserConf where userId=?";
		return getHibernateTemplate().find(queryString, userId);
	}

	@Override
	public boolean updateUserConf(UserConf userConf) {
		try {
			getHibernateTemplate().saveOrUpdate(userConf);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public UserConf findUserConfInfo(String userId, String attribute) {
		String queryString = "from UserConf where userId=? and attribute=?";
		List<?> list = getHibernateTemplate().find(queryString,
				new Object[] { userId, attribute });
		if (Util.isNotEmpty(list)) {
			return (UserConf) list.get(0);
		}
		return null;
	}

	@Override
	public boolean deleteUserConf(String userId, String attribute) {
		try {
			UserConf userConf = findUserConfInfo(userId, attribute);
			if (userConf != null) {
				getHibernateTemplate().delete(userConf);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
