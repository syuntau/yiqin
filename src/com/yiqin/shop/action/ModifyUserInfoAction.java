package com.yiqin.shop.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.User;
import com.yiqin.pojo.UserConf;
import com.yiqin.service.UserManager;
import com.yiqin.util.Util;

/**
 * 修改用户信息
 * 
 * @author LiuJun
 * 
 */
public class ModifyUserInfoAction extends ActionSupport {

	private static final long serialVersionUID = 4384288723004399437L;
	// 邮箱
	private String email;
	// 手机
	private String telephone;
	// 密码
	private String password;
	// 确认密码
	private String confirmPwd;
	// 邮箱验证码
	private String verification_code;
	// 真实姓名
	private String name;
	// 用户类型
	private String role_type;
	// 公司地址
	private String company;
	// 修改类型(email、password、(telephone\name\company\role_type))
	private String modifyType;

	private UserManager userManager;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getModifyType() {
		return modifyType;
	}

	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String getVerification_code() {
		return verification_code;
	}

	public void setVerification_code(String verification_code) {
		this.verification_code = verification_code;
	}

	public String getRole_type() {
		return role_type;
	}

	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			// 获取当前用户
			User loninUser = Util.getLoginUser(session);
			
			// 邮箱是否已经验证
			boolean verifyFlag = false;
			UserConf userConf = userManager.findUserConfInfo(loninUser.getId(),"email_verify");
			if(userConf != null){
				if("11".equals(userConf.getValue())){
					verifyFlag = true;
				}
			}
			
			//获取存储验证码 和 过期时间
			String vcodekey = loninUser.getId() + "_code_" + verification_code;
			Object expetTime = session.getAttribute(vcodekey);
			boolean expetFlag = false;
			boolean errorCode = true;
			if (expetTime != null) {
				errorCode = false;
				Date expetDate = (Date) expetTime;
				if (expetDate.before(new Date())) {
					expetFlag = true;
				}
			}

			// 设置信息
			if ("mem".equals(modifyType)) {
				if(!verifyFlag){
					result = "1";
					response.getWriter().print(result);
					return null;
				}
				if (Util.isEmpty(email)) {
					result = "2";
					response.getWriter().print(result);
					return null;
				} else {
					if(errorCode){
						result = "3";
						response.getWriter().print(result);
						return null;
					}
					if(expetFlag){
						result = "4";
						response.getWriter().print(result);
						return null;
					}
					loninUser.setEmail(email);
				}
			} else if ("normal".equals(modifyType)) {
				if (Util.isEmpty(telephone)) {
					result = "1";
					response.getWriter().print(result);
					return null;
				}
				if("2".equals(role_type) && Util.isEmpty(company)){
					result = "2";
					response.getWriter().print(result);
					return null;
				}
				loninUser.setMobile(telephone);
				loninUser.setCompany(company);
				loninUser.setName(name);
				loninUser.setRole(Byte.valueOf(role_type));
			} else if ("mpw".equals(modifyType)) {
				if(!verifyFlag){
					result = "1";
					response.getWriter().print(result);
					return null;
				}
				if (Util.isEmpty(password)) {
					result = "2";
					response.getWriter().print(result);
					return null;
				}
				if (!password.equals(confirmPwd)) {
					result = "5";
					response.getWriter().print(result);
					return null;
				} else {
					if(errorCode){
						result = "3";
						response.getWriter().print(result);
						return null;
					}
					if(expetFlag){
						result = "4";
						response.getWriter().print(result);
						return null;
					}
					loninUser.setPassword(password);
				}
			}else if("vem".equals(modifyType)){
				if(verifyFlag){
					result = "1";
					response.getWriter().print(result);
					return null;
				}
				if(errorCode){
					result = "3";
					response.getWriter().print(result);
					return null;
				}
				if(expetFlag){
					result = "4";
					response.getWriter().print(result);
					return null;
				}
				if (userConf != null) {
					userConf.setValue("11");
				}else{
					userConf = new UserConf();
					userConf.setAttribute("email_verify");
					userConf.setValue("11");
					userConf.setUserId(loninUser.getId());
				}
				boolean flag = userManager.updateUserConf(userConf);
				if(!flag){
					result = "9";
				}
				response.getWriter().print(result);
				return null;
			}
			// 修改用户
			loninUser.setUpdateDate(new Date());
			boolean flag = userManager.modifyUserInfo(loninUser);
			if (!flag) {
				result = "9";
			} else {
				session.setAttribute("userInfo", loninUser);
			}
			response.getWriter().print(result);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result = "9";
			response.getWriter().print(result);
			return null;
		}
	}
}
