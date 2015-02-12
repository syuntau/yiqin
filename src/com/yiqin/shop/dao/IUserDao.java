package com.yiqin.shop.dao;

import com.yiqin.shop.pojo.User;

/**
 * 用户DAO
 * 
 * @author LiuJun
 * 
 */
public interface IUserDao {
	/**
	 * 插入用户信息
	 * 
	 * @param user
	 *            用户对象
	 * @return 成功状态
	 */
	public boolean insertUser(User user);

	/**
	 * 根据用户名、密码查询信息
	 * 
	 * @param userId
	 *            用户名
	 * @param password
	 *            密码
	 * @return 用户对象
	 */
	public User findUserInfo(String userId, String password);

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
	 * @return 成功状态
	 */
	public boolean modifyUserInfo(User user);

	/**
	 * 删除用户
	 * 
	 * @param userId
	 *            用户名
	 * @return 成功状态
	 */
	public boolean deleteUser(String userId);

}
