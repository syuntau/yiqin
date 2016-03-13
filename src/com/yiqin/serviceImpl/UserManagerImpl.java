package com.yiqin.serviceImpl;

import java.text.DecimalFormat;
import java.util.List;

import com.yiqin.dao.IShoppingDao;
import com.yiqin.dao.IUserDao;
import com.yiqin.pojo.Cart;
import com.yiqin.pojo.SAUser;
import com.yiqin.pojo.User;
import com.yiqin.pojo.UserConf;
import com.yiqin.service.UserManager;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class UserManagerImpl implements UserManager {

	private IUserDao userDao;
	
	private IShoppingDao shoppingDao;
	
	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	public IShoppingDao getShoppingDao() {
		return shoppingDao;
	}

	public void setShoppingDao(IShoppingDao shoppingDao) {
		this.shoppingDao = shoppingDao;
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
	public List<UserConf> findUserInvoiceList(String userId) {
		return userDao.findUserInvoiceList(userId);
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
		if (Util.isEmpty(userId) || Util.isEmpty(attribute)) {
			return false;
		}
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

	@Override
	public boolean saveOrUpdateUserZheKou(String userId, float zhekou) {
		if (Util.isEmpty(userId)) {
			return false;
		}
		UserConf userConf = findUserConfInfo(userId, UtilKeys.USRE_CONF_ZHE_KOU);
		boolean flag = false;
		if(userConf != null){
			userConf.setValue(String.valueOf(zhekou));
			flag = updateUserConf(userConf);
		}else{
			UserConf temp = new UserConf();
			temp.setUserId(userId);
			temp.setAttribute(UtilKeys.USRE_CONF_ZHE_KOU);
			temp.setValue(String.valueOf(zhekou));
			flag = updateUserConf(temp);
		}
		if(flag){
			List<Cart> cartList = shoppingDao.findCartListInfo(userId);
			if(Util.isNotEmpty(cartList)){
				for(Cart cart : cartList){
					float value = Float.valueOf(cart.getPrice())*zhekou;
					String zkp = new DecimalFormat("#########.00").format(value);
					cart.setZhekouPrice(zkp);
					shoppingDao.updateCart(cart);
				}
			}
		}
		return flag;
	}

	@Override
	public boolean deleteUserZheKou(String userId) {
		boolean flag =  userDao.deleteUserConf(userId, UtilKeys.USRE_CONF_ZHE_KOU);
		if(flag){
			List<Cart> cartList = shoppingDao.findCartListInfo(userId);
			if(Util.isNotEmpty(cartList)){
				for(Cart cart : cartList){
					cart.setZhekouPrice(cart.getPrice());
					shoppingDao.updateCart(cart);
				}
			}
		}
		return flag;
	}

	@Override
	public boolean saveOrUpdateUserConf(String userId, String attribute, String value) {
		if (Util.isEmpty(userId) || Util.isEmpty(attribute) || Util.isEmpty(value)) {
			return false;
		}
		UserConf userConf = findUserConfInfo(userId, attribute);
		boolean flag = false;
		if(userConf != null){
			userConf.setValue(value);
			flag = updateUserConf(userConf);
		}else{
			UserConf temp = new UserConf();
			temp.setUserId(userId);
			temp.setAttribute(attribute);
			temp.setValue(value);
			flag = updateUserConf(temp);
		}
		return flag;
	}

	@Override
	public SAUser findAdminById(String id) {
		try {
			return userDao.findAdminById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
