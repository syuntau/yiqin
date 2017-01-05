package com.yiqin.shop.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 统计图
 * 
 * @author bai
 * 
 */
public class GetChartsAction extends ActionSupport {

	private static final long serialVersionUID = -7433386428039496408L;

	public String execute() throws Exception {
//		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return SUCCESS;
		}
	}

}
