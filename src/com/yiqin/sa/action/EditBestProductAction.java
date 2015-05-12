package com.yiqin.sa.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
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
}
