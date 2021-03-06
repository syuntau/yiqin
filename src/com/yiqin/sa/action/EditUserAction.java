package com.yiqin.sa.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.SAUser;
import com.yiqin.service.UserManager;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class EditUserAction extends ActionSupport {

	private static final long serialVersionUID = -8673099200921975147L;
//	private String id;
//	private String oldPass;
//	private String newPass;
	private SAUser admin;
	private UserManager userManager;
	public SAUser getAdmin() {
		return admin;
	}
	public void setAdmin(SAUser admin) {
		this.admin = admin;
	}
	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public String modifyPass() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			HttpServletRequest request = ServletActionContext.getRequest();
			String id = request.getParameter("id");
			String oldPass = request.getParameter("oldPass");
			String newPass = request.getParameter("newPass");
			if (Util.isEmpty(id) || Util.isEmpty(oldPass) || Util.isEmpty(newPass)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
			} else {
				SAUser user = userManager.findSAUser(id, oldPass);
				if (user == null) {
					result = UtilKeys.CODE_ERR_OLD_PASS;
				} else {
					user.setPassword(newPass);
					user.setUpdateDate(new Date());
					boolean flag = userManager.updateSAUser(user);
					if (flag) {
						result = UtilKeys.CODE_SUCCESS;
					} else {
						result = UtilKeys.CODE_ERR_EXCEPTION;
					}
				}
				out.print(result);
			}
		} catch (Exception e) {
			System.out.println("error in EditUserAction.modifyPass");
			e.printStackTrace();
		}
		return null;
	}
	
	public String save() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (admin == null) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
			} else {
				Date now = new Date();
				admin.setCreateDate(now);
				admin.setUpdateDate(now);
				admin.setRole(100);
				String id = userManager.saveSAUser(admin);
				if (Util.isEmpty(id)) {
					result = UtilKeys.CODE_ERR_EXCEPTION;
				} else {
					admin.setId(id);
					JSONObject json = JSONObject.fromObject(admin);
					result = json.toString();
				}
				out.print(result);
			}
		} catch (Exception e) {
			System.out.println("error in EditUserAction.save");
			e.printStackTrace();
		}
		return null;
	}
	
	public String update() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (admin == null) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
			} else {
				SAUser user = userManager.findAdminById(admin.getId());
				if (user == null) {
					result = UtilKeys.CODE_ERR_EXCEPTION;
				} else {
					Date now = new Date();
					user.setName(admin.getName());
					user.setPassword(admin.getPassword());
					user.setUpdateDate(now);
					boolean flag = userManager.updateSAUser(user);
					if (flag) {
						JSONObject json = JSONObject.fromObject(user);
						result = json.toString();
					} else {
						result = UtilKeys.CODE_ERR_EXCEPTION;
					}
				}
				out.print(result);
			}
		} catch (Exception e) {
			System.out.println("error in EditUserAction.update");
			e.printStackTrace();
		}
		return null;
	}
	
	public String delete() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			HttpServletRequest request = ServletActionContext.getRequest();
			String id = request.getParameter("id");
			if (Util.isEmpty(id)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
			} else {
				boolean flag = userManager.deleteSAUser(id);
				if (flag) {
					result = UtilKeys.CODE_SUCCESS;
				} else {
					result = UtilKeys.CODE_ERR_EXCEPTION;
				}
				out.print(result);
			}
		} catch (Exception e) {
			System.out.println("error in EditUserAction.delete");
			e.printStackTrace();
		}
		return null;
	}
	
	public String findAdmin() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			List<SAUser> list = userManager.findAdmin(100);
			if (Util.isEmpty(list)) {
				result = UtilKeys.CODE_NO_RESULT;
			} else {
				JSONArray jsArray = JSONArray.fromObject(list);
				result = jsArray.toString();
			}
			out.print(result);
		} catch (Exception e) {
			System.out.println("error in EditUserAction.findAdmin");
			e.printStackTrace();
		}
		return null;
	}
}
