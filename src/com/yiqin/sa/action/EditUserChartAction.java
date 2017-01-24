package com.yiqin.sa.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.UserConf;
import com.yiqin.service.UserManager;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;
/**
 * 更新统计图开启状态
 * @author bai
 *
 */
public class EditUserChartAction extends ActionSupport {

	private static final long serialVersionUID = -4687474259363469414L;
	private String userId;
	private String status;
	private UserManager userManager;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void saveUserChartStatus(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			
			PrintWriter out = response.getWriter();
			String result = "";
			if(Util.isEmpty(userId) || Util.isEmpty(status)){
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return;
			}
			UserConf uf = userManager.findUserConfInfo(userId, "userStatChartStatus");
			if(uf == null){
				uf = new UserConf();
			}
			uf.setUserId(userId);
			uf.setAttribute("userStatChartStatus");
			uf.setValue(status);
			
			userManager.updateUserConf(uf);
			
			result = "true";
			out.print(result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void getUserChartStatus(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			
			PrintWriter out = response.getWriter();
			String result = "";
			if(Util.isEmpty(userId)){
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return;
			}

			UserConf uf = userManager.findUserConfInfo(userId, "userStatChartStatus");
			if(uf == null || uf.getValue().equals("false")){
				result="false";
			}else{
				result = "true";
			}
			out.print(result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}