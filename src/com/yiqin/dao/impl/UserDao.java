package com.yiqin.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yiqin.dao.IUserDao;
import com.yiqin.pojo.SAUser;
import com.yiqin.pojo.User;
import com.yiqin.pojo.UserConf;
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
	public List<UserConf> findUserAddressList(String userId) {
		String queryString = "from UserConf where userId=? and attribute like 'address%'";
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

	@Override
	public List<User> findAll() {
		String sql = "from User";
		List<?> list = getHibernateTemplate().find(sql);
		if (list != null) {
			return (List<User>) list;
		}
		return null;
	}

	@Override
	public SAUser isLoginSA(String userId, String password) {
		String queryString = "from SAUser where id=? and password=?";
		List<?> list = getHibernateTemplate().find(queryString,
				new Object[] { userId, password });
		if (Util.isNotEmpty(list)) {
			return (SAUser) list.get(0);
		}
		return null;
	}

	@Override
	public String saveSAUser(SAUser user) throws DataAccessException {
		String id = (String) getHibernateTemplate().save(user);
		return id;
	}

	@Override
	public void updateSAUser(SAUser user) throws DataAccessException {
		getHibernateTemplate().update(user);
	}

	@Override
	public void deleteSAUser(String id) throws DataAccessException {
		SAUser user = (SAUser) getHibernateTemplate().get(SAUser.class, id);
		if (user != null) {
			getHibernateTemplate().delete(user);
		}
	}

	@Override
	public List<SAUser> findAdmin(int role) throws DataAccessException {
		String query =  "from SAUser where role=?";
		List<?> list = getHibernateTemplate().find(query, role);
		if (Util.isNotEmpty(list)) {
			return (List<SAUser>) list;
		}
		return null;
	}

	@Override
	public SAUser findAdminById(String id) throws DataAccessException {
		SAUser user = (SAUser) getHibernateTemplate().get(SAUser.class, id);
		if (user != null) {
			return user;
		}
		return null;
	}
}
