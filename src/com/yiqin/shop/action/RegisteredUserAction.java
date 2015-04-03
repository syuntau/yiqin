package com.yiqin.shop.action;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.User;
import com.yiqin.pojo.UserConf;
import com.yiqin.service.UserManager;
import com.yiqin.util.Util;

/**
 * 用户注册
 * 
 * @author LiuJun
 * 
 */
public class RegisteredUserAction extends ActionSupport {

	private static final long serialVersionUID = 7088615138788787514L;
	// 用户名
	private String userId;
	// 密码
	private String password;
	// 确认密码
	private String confirmPwd;
	// 邮箱
	private String email;
	// 手机
	private String mobile;

	private UserManager userManager;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			// 参数判断
			if (Util.isEmpty(userId) || Util.isEmpty(password)
					|| Util.isEmpty(confirmPwd) || Util.isEmpty(email)
					|| Util.isEmpty(mobile)) {
				result = "2";
				response.getWriter().print(result);
				return null;
			}

			// 密码校验
			if (!password.equals(confirmPwd)) {
				result = "3";
				response.getWriter().print(result);
				return null;
			}

			// 判断用户名是否重复
			User existUser = userManager.findUserByUserId(userId);
			if (existUser != null) {
				result = "4";
				response.getWriter().print(result);
				return null;
			}

			// 添加用户
			User tempUser = new User();
			tempUser.setId(userId);
			tempUser.setPassword(password);
			tempUser.setEmail(email);
			tempUser.setMobile(mobile);
			tempUser.setFlag((byte) 1);
			tempUser.setRole((byte) 1);
			tempUser.setCreateDate(new Date());
			tempUser.setUpdateDate(new Date());
			boolean flag = userManager.registeUser(tempUser);
			if (!flag) {
				result = "5";
				response.getWriter().print(result);
				return null;
			}
			//添加邮箱验证记录
			UserConf userConf = new UserConf();
			userConf.setAttribute("email_verify");
			userConf.setUserId(userId);
			userConf.setValue("00");
			userManager.updateUserConf(userConf);
			result = "1";
			response.getWriter().print(result);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result = "5";
			response.getWriter().print(result);
			return null;
		}
	}
}
