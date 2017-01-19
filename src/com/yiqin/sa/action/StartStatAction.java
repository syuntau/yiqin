package com.yiqin.sa.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.ShoppingManager;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class StartStatAction extends ActionSupport {
	private static final long serialVersionUID = -2169418659676188269L;
	
	private String userId;
	private ShoppingManager shoppingManager;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public ShoppingManager getShoppingManager() {
		return shoppingManager;
	}

	public void setShoppingManager(ShoppingManager shoppingManager) {
		this.shoppingManager = shoppingManager;
	}

	public void begin4stat(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(userId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
			} 
			
			boolean req = shoppingManager.startStat(userId);
			out.print(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
