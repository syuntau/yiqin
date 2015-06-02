package com.yiqin.service;

import java.util.List;

import com.yiqin.pojo.SAUser;
import com.yiqin.pojo.User;
import com.yiqin.pojo.UserConf;

public interface UserManager {
	/**
	 * 注册用户
	 * 
	 * @param user
	 *            用户对象
	 * @return 注册成功状态
	 */
	public boolean registeUser(User user);

	/**
	 * 用户登录
	 * 
	 * @param userId
	 *            用户名
	 * @param password
	 *            密码
	 * @return 登录成功用户对象
	 */
	public User login(String userId, String password);

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param userId
	 *            用户名
	 * @return 用户对象
	 */
	public User findUserByUserId(String userId);

	/**
	 * 修改用户
	 * 
	 * @param user
	 *            修改的用户
	 * @return 修改成功状态
	 */
	public boolean modifyUserInfo(User user);

	/**
	 * 删除用户
	 * 
	 * @param userId
	 *            用户名
	 * @return 删除成功状态
	 */
	public boolean deleteUser(String userId);

	/**
	 * 查找用户所有收货地址信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return 收货地址集
	 */
	public List<UserConf> findUserAddressList(String userId);

	/**
	 * 保存更新用户配置
	 * 
	 * @param userConf
	 *            更新对象
	 * @return 成功状态
	 */
	public boolean updateUserConf(UserConf userConf);

	/**
	 * 查询指定配置信息
	 * 
	 * @param userId
	 *            用户ID
	 * @param attribute
	 *            用户配置属性名
	 * @return 配置对象
	 */
	public UserConf findUserConfInfo(String userId, String attribute);

	/**
	 * 删除指定用户配置信息
	 * 
	 * @param userId
	 *            用户ID
	 * @param attribute
	 *            配置属性名
	 * @return 成功状态
	 */
	public boolean deleteUserConf(String userId, String attribute);

	public List<User> findAll();
	public SAUser findSAUser(String id, String password);
	public String saveSAUser(SAUser user);
	public boolean updateSAUser(SAUser user);
	public boolean deleteSAUser(String id);
	public List<SAUser> findAdmin(int role);
	public SAUser findAdminById(String id);
}
