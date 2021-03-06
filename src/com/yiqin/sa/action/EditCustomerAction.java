package com.yiqin.sa.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.User;
import com.yiqin.pojo.UserConf;
import com.yiqin.service.ProductManager;
import com.yiqin.service.ShoppingManager;
import com.yiqin.service.UserManager;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class EditCustomerAction extends ActionSupport {

	private static final long serialVersionUID = -6427876840127942287L;
	private UserManager userManager;
//	private String userId;
//	private String zheKou;
//	private String youHuiZhengCe;
	private ProductManager productManager;
	private ShoppingManager shoppingManager;

	public ShoppingManager getShoppingManager() {
		return shoppingManager;
	}
	public void setShoppingManager(ShoppingManager shoppingManager) {
		this.shoppingManager = shoppingManager;
	}
	public ProductManager getProductManager() {
		return productManager;
	}
	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}
	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String addZheKou() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			HttpServletRequest request = ServletActionContext.getRequest();
			String userId = request.getParameter("userId");
			String zheKou = request.getParameter("zheKou");
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
			HttpServletRequest request = ServletActionContext.getRequest();
			String userId = request.getParameter("userId");
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
			HttpServletRequest request = ServletActionContext.getRequest();
			String userId = request.getParameter("userId");
			String youHuiZhengCe = request.getParameter("youHuiZhengCe");
			if (Util.isEmpty(userId) || Util.isEmpty(youHuiZhengCe)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					if (youHuiZhengCe.equals("empty")) {
						boolean flag = userManager.deleteUserConf(userId, UtilKeys.USRE_CONF_YOU_HUI);
						if(flag){
							result = UtilKeys.CODE_SUCCESS;
						}else{
							result = UtilKeys.CODE_ERR_DB;
						}
					} else {
						youHuiZhengCe = youHuiZhengCe.replaceAll("\r\n", "<br>");
						youHuiZhengCe = youHuiZhengCe.replaceAll("\n", "<br>");
						boolean flag = userManager.saveOrUpdateUserConf(userId, UtilKeys.USRE_CONF_YOU_HUI, youHuiZhengCe);
						if(flag){
							result = UtilKeys.CODE_SUCCESS;
						}else{
							result = UtilKeys.CODE_ERR_DB;
						}
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
			HttpServletRequest request = ServletActionContext.getRequest();
			String userId = request.getParameter("userId");
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

	public String getPwd() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			HttpServletRequest request = ServletActionContext.getRequest();
			String userId = request.getParameter("userId");
			if (Util.isEmpty(userId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			}
			User user = userManager.findUserByUserId(userId);
			if (user == null) {
				result = UtilKeys.CODE_NO_RESULT;
				out.print(result);
				return null;
			}
			JSONObject json = new JSONObject();
			json.accumulate("pwd", user.getPassword());
			result = json.toString();
			out.print(result);
			return null;
		} catch (Exception e1) {
			System.out.println("error in EditCustomerAction.getPwd ");
			e1.printStackTrace();
			return null;
		}
	}

	public String removeUser() throws IOException {

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			HttpServletRequest request = ServletActionContext.getRequest();
			String userId = request.getParameter("userId");
			if (Util.isEmpty(userId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			}

			shoppingManager.deleteCartByUserId(userId);
			System.out.println("### removeUser deleteCartByUserId over");
			shoppingManager.deleteOrdersByUserId(userId);
			System.out.println("### removeUser deleteOrdersByUserId over");
			productManager.deleteAllCommonProduct(userId);
			System.out.println("### removeUser deleteAllCommonProduct over");
			userManager.deleteUserConf(userId);
			System.out.println("### removeUser deleteUserConf over");
			userManager.deleteUser(userId);
			System.out.println("### removeUser deleteUser over");

			result = UtilKeys.CODE_SUCCESS;
			out.print(result);
			return null;
		} catch (Exception e) {
			System.out.println("### removeUser error cause by : " + e.getMessage());
			e.printStackTrace();

			PrintWriter out = response.getWriter();
			String result = UtilKeys.CODE_ERR_EXCEPTION;
			out.print(result);
			return null;
		}
	}
}
