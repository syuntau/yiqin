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
					UserConf zhekou = userManager.findUserConfInfo(userId, UtilKeys.USRE_CONF_ZHE_KOU);
					if (zhekou == null) {
						zhekou = new UserConf();
						zhekou.setUserId(userId);
						zhekou.setAttribute(UtilKeys.USRE_CONF_ZHE_KOU);
						zhekou.setValue(zheKou);
					} else {
						zhekou.setValue(zheKou);
					}
					userManager.updateUserConf(zhekou);
					result = UtilKeys.CODE_SUCCESS;
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
}
