package com.yiqin.shop.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.UserConf;
import com.yiqin.service.UserManager;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class FindYouHuiAction extends ActionSupport {

	private static final long serialVersionUID = -6427876840127942287L;
	private UserManager userManager;
	private String userId;

	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(userId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			}
			UserConf youHuiConf = userManager.findUserConfInfo(userId, UtilKeys.USRE_CONF_YOU_HUI);
			if (youHuiConf == null) {
				result = UtilKeys.CODE_NO_RESULT;
				out.print(result);
				return null;
			}
			JSONObject json = JSONObject.fromObject(youHuiConf);
			result = json.toString();
			out.print(result);
			return null;
		} catch (Exception e1) {
			System.out.println("error in FindYouHuiAction ");
			e1.printStackTrace();
			return null;
		}
	}
}
