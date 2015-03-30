package com.yiqin.shop.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.UserConf;
import com.yiqin.service.UserManager;
import com.yiqin.util.Util;

/**
 * 查询用户配置信息
 * 
 * @author liujun
 *
 */
public class FindUserConfAction extends ActionSupport {

	private static final long serialVersionUID = 1827185626304609342L;

	private UserManager userManager;

	// 用户配置属性(参数为空，查询当前用户所有)
	private String attribute;

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
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
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			// 用户ID
			String userId = Util.getLoginUser(request.getSession()).getId();

			// 查询配置信息
			if (Util.isNotEmpty(attribute)) {
				UserConf userConf = userManager.findUserConfInfo(userId,
						attribute);
				if (userConf != null) {
					JSONObject jsonObj = JSONObject.fromObject(userConf);
					result = jsonObj.toString();
				} else {
					result = "1";
				}
			} else {
				List<UserConf> confList = userManager.findUserAddressList(userId);
				if (Util.isNotEmpty(confList)) {
					JSONArray jsonArray = JSONArray.fromObject(confList);
					result = jsonArray.toString();
				} else {
					result = "1";
				}
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
