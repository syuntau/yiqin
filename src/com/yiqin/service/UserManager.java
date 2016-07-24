package com.yiqin.service;

import java.util.List;

import com.yiqin.pojo.RegisterCode;
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
	 * 查找用户所有发票信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return 发票信息集
	 */
	public List<UserConf> findUserInvoiceList(String userId);

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
	
	/**
	 * 插入注册邀请码信息
	 * 
	 * @param regCodeBean
	 *            邀请码信息
	 * @return 成功状态
	 */
	public boolean insertRegisterCode(RegisterCode regCodeBean);
	
	/**
	 * 获取注册邀请码，看是否存在
	 * 
	 * @param rCode
	 *            邀请码
	 * @return RegisterCode
	 */
	public RegisterCode findRCodeByCode(String rCode);
	
	/**
	 * 查询所有注册邀请码
	 * 
	 * @return List<RegisterCode>
	 */
	public List<RegisterCode> findRcodeList();
	
	/**
	 * 删除注册邀请码
	 * 
	 * @param rCode
	 *            邀请码
	 * @return 成功状态
	 */
	public boolean deleteRegCode(String rCode);

	public List<User> findAll();
	public SAUser findSAUser(String id, String password);
	public String saveSAUser(SAUser user);
	public boolean updateSAUser(SAUser user);
	public boolean deleteSAUser(String id);
	public List<SAUser> findAdmin(int role);
	
	public boolean saveOrUpdateUserZheKou(String userId, float zhekou);
	public boolean deleteUserZheKou(String userId);
	public boolean saveOrUpdateUserConf(String userId, String attribute, String value);

	public SAUser findAdminById(String id);

}
