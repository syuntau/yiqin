package com.yiqin.sa.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;
import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.BestProduct;
import com.yiqin.pojo.User;
import com.yiqin.service.ProductManager;
import com.yiqin.service.UserManager;
import com.yiqin.shop.bean.UserView;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class EditBestProductAction extends ActionSupport {
	private static final long serialVersionUID = 7163225413110248267L;
	private UserManager userManager;
	private ProductManager productManager;
	private String cId;
	private String pIds;
	private String userId;

	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	public ProductManager getProductManager() {
		return productManager;
	}
	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}
	public String getCId() {
		return cId;
	}
	public void setCId(String cId) {
		this.cId = cId;
	}
	public String getPIds() {
		return pIds;
	}
	public void setPIds(String pIds) {
		this.pIds = pIds;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserList() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			List<User> userList = userManager.findAll();
			if (Util.isEmpty(userList)) {
				result = UtilKeys.CODE_NO_RESULT;
				return null;
			}
			List<UserView> showUserList = new ArrayList<UserView>();
			for (User user : userList) {
				UserView userView = new UserView(user);
				showUserList.add(userView);
			}
			JSONArray jsArray = JSONArray.fromObject(showUserList);
			result = jsArray.toString();
			out.print(result);
			return null;
		} catch (Exception e1) {
			System.out.println("error in EditBestProductAction.getUserList for make printwriter");
			e1.printStackTrace();
			return null;
		}
	}

	public String saveBestProduct() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(cId) || Util.isEmpty(pIds)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					BestProduct bestProduct = productManager.findBestProductByCategoryId(userId, cId);
					if (bestProduct == null) {
						bestProduct = new BestProduct();
						bestProduct.setUserId(userId);
						bestProduct.setCategoryId(Integer.parseInt(cId));
					}
					bestProduct.setProductId(pIds);
					productManager.saveBestProduct(bestProduct);
					result = UtilKeys.CODE_SUCCESS;
					out.print(result);
				} catch(DataAccessException dbe) {
					System.out.println("error in EditBestProductAction.saveBestProduct for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}
			}
		} catch (IOException e1) {
			System.out.println("error in EditBestProductAction.saveBestProduct for io exception");
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
