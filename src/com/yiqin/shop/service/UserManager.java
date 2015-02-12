package com.yiqin.shop.service;

import com.yiqin.shop.pojo.User;

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
}
