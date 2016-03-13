package com.yiqin.shop.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.UserConf;
import com.yiqin.service.UserManager;
import com.yiqin.util.Util;

/**
 * 查询用户所有发票信息
 * 
 * @author liujun
 *
 */
public class FindUserInvoiceAction extends ActionSupport {

	private static final long serialVersionUID = -2771594900718191223L;
	
	private UserManager userManager;

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			// 用户ID
			String userId = Util.getLoginUser(request.getSession()).getId();

			//查询发票信息列表
			List<UserConf> confList = userManager.findUserInvoiceList(userId);
			if (Util.isNotEmpty(confList)) {
				JSONArray jsonArray = JSONArray.fromObject(confList);
				result = jsonArray.toString();
			} else {
				result = "1";
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
