package com.yiqin.sa.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.RegisterCode;
import com.yiqin.service.UserManager;
import com.yiqin.util.Util;

/**
 * 查询册邀请码
 * 
 * @author liujun
 * 
 */
public class FindRegCodeAction extends ActionSupport {

	private static final long serialVersionUID = -1657853934880115838L;

	private UserManager userManager;

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {

			List<RegisterCode> rcList = userManager.findRcodeList();
			if (Util.isEmpty(rcList)) {
				result = "1";
			} else {
				JSONArray jsArray = JSONArray.fromObject(rcList);
				result = jsArray.toString();
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

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

}
