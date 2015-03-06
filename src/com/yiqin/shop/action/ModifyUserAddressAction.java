package com.yiqin.shop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.pojo.UserConf;
import com.yiqin.shop.service.UserManager;
import com.yiqin.util.Util;

/**
 * 更新或新增收货地址
 * 
 * @author liujun
 *
 */
public class ModifyUserAddressAction extends ActionSupport {

	private static final long serialVersionUID = 782422695301557740L;

	private UserManager userManager;

	// 地址信息
	private String address;

	// 地址属性
	private String attribute;

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		String result = "";
		try {
			if (Util.isEmpty(address) || Util.isEmpty(attribute)) {
				result = "1";
				response.getWriter().print(result);
				return null;
			}
			// 用户ID
			String userId = Util.getLoginUser(request.getSession()).getId();

			// 查询信息
			UserConf userConf = userManager.findUserConfInfo(userId, attribute);
			if (userConf == null) {
				UserConf confNew = new UserConf();
				confNew.setUserId(userId);
				confNew.setAttribute(attribute);
				confNew.setValue(address);
				userConf = confNew;
			} else {
				userConf.setAttribute(attribute);
				userConf.setValue(address);
			}

			// 更新信息
			boolean flag = userManager.updateUserConf(userConf);
			if (!flag) {
				result = "2";
			} else {
				result = "3";
			}
			response.getWriter().print(result);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result = "2";
			response.getWriter().print(result);
			return null;
		}
	}
}
