package com.yiqin.sa.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.UserConf;
import com.yiqin.service.UserManager;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class EditCustomerAction extends ActionSupport {

	private static final long serialVersionUID = -6427876840127942287L;
	private UserManager userManager;
	private String userId;
	private String zheKou;
	private String youHuiZhengCe;

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
	public String getZheKou() {
		return this.zheKou;
	}
	public void setZheKou(String zheKou) {
		this.zheKou = zheKou;
	}
	public String getYouHuiZhengCe() {
		return youHuiZhengCe;
	}
	public void setYouHuiZhengCe(String youHuiZhengCe) {
		this.youHuiZhengCe = youHuiZhengCe;
	}

	public String addZheKou() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(userId) || Util.isEmpty(zheKou)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					float tmp = Float.parseFloat(zheKou);
					if (tmp <= 0 || tmp > 1) {
						result = UtilKeys.CODE_ERR_PARAM;
						out.print(result);
						return null;
					}
				} catch (NumberFormatException ne) {
					System.out.println("error in EditCustomerAction.addZheKou for parseFloat");
					ne.printStackTrace();
					result = UtilKeys.CODE_ERR_PARAM;
					out.print(result);
					return null;
				}
				try {
					boolean flag = userManager.saveOrUpdateUserZheKou(userId, Float.parseFloat(zheKou));
					if(flag){
						result = UtilKeys.CODE_SUCCESS;
					}else{
						result = UtilKeys.CODE_ERR_PARAM;
					}
					out.print(result);
				} catch(DataAccessException dbe) {
					System.out.println("error in EditCustomerAction.addZheKou for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}
			}
		} catch (IOException e1) {
			System.out.println("error in EditCustomerAction.addZheKou for io exception");
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getZK() {
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
			UserConf zhekouConf = userManager.findUserConfInfo(userId, "zhekou");
			if (zhekouConf == null) {
				result = UtilKeys.CODE_NO_RESULT;
				out.print(result);
				return null;
			}
			JSONObject json = JSONObject.fromObject(zhekouConf);
			result = json.toString();
			out.print(result);
			return null;
		} catch (Exception e1) {
			System.out.println("error in EditCustomerAction.getZK ");
			e1.printStackTrace();
			return null;
		}
	}

	public String addYouHui() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(userId) || Util.isEmpty(youHuiZhengCe)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					boolean flag = userManager.saveOrUpdateUserConf(userId, UtilKeys.USRE_CONF_YOU_HUI, youHuiZhengCe);
					if(flag){
						result = UtilKeys.CODE_SUCCESS;
					}else{
						result = UtilKeys.CODE_ERR_DB;
					}
					out.print(result);
				} catch(DataAccessException dbe) {
					System.out.println("error in EditCustomerAction.addYouHui for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}
			}
		} catch (IOException e1) {
			System.out.println("error in EditCustomerAction.addYouHui for io exception");
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getYouHui() {
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
			System.out.println("error in EditCustomerAction.getYouHui ");
			e1.printStackTrace();
			return null;
		}
	}
}
