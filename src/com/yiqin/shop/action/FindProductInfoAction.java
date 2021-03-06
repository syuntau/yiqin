package com.yiqin.shop.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.User;
import com.yiqin.service.ProductManager;
import com.yiqin.shop.bean.ProductView;
import com.yiqin.util.Util;

/**
 * 查询商品信息
 * 
 * @author LiuJun
 * 
 */
public class FindProductInfoAction extends ActionSupport {

	private static final long serialVersionUID = 8020271590033485503L;
	//商品ID
	private String productId;

	private ProductManager productManager;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = ServletActionContext.getRequest().getSession();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (Util.isEmpty(productId)) {
				result = "1";
			} else {
				User loginUser = Util.getLoginUser(session);
				String userId = "";
				if (loginUser != null) {
					userId = loginUser.getId();
				}
				List<ProductView> product = productManager.findProductInfoById(userId, productId);	
				if (Util.isEmpty(product)) {
					result = "1";
				} else {
					JSONArray jsonArray = JSONArray.fromObject(product);
					result = jsonArray.toString();
				}
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
