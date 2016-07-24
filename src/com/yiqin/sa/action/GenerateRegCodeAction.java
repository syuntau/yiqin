package com.yiqin.sa.action;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.RegisterCode;
import com.yiqin.service.UserManager;
import com.yiqin.util.Util;

/**
 * 生成注册邀请码
 * 
 * @author liujun
 * 
 */
public class GenerateRegCodeAction extends ActionSupport {

	private static final long serialVersionUID = 2183716821575185095L;

	// 别名
	private String name;

	// 折扣
	private String zhekou;

	private UserManager userManager;

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (GenericValidator.isBlankOrNull(name)
					|| GenericValidator.isBlankOrNull(zhekou)) {
				result = "2";
				response.getWriter().print(result);
				return null;
			}

			// 生成8位唯一不重复的邀请码
			String regCode = Util.generateRegCode();

			RegisterCode tempRc = new RegisterCode();
			tempRc.setName(name);
			tempRc.setRegisterCode(regCode);
			tempRc.setZhekou(Float.parseFloat(zhekou));
			tempRc.setCreateDate(new Date());
			boolean flag = userManager.insertRegisterCode(tempRc);
			if (!flag) {
				result = "3";
			} else {
				result = "1";
			}
			response.getWriter().print(result);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result = "3";
			response.getWriter().print(result);
			return null;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZhekou() {
		return zhekou;
	}

	public void setZhekou(String zhekou) {
		this.zhekou = zhekou;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

}
