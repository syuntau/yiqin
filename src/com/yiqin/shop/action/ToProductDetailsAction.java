package com.yiqin.shop.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.ProductManager;
import com.yiqin.shop.bean.ProductView;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

/**
 * 点击商品详情
 * 
 * @author liujun
 *
 */
public class ToProductDetailsAction extends ActionSupport {

	private static final long serialVersionUID = -4071931669942921993L;

	// 商品ID
	private String paramVal;
	
	private ProductManager productManager;

	public String getParamVal() {
		return paramVal;
	}

	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}
	
	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public String execute() throws Exception {
		try {
			if (Util.isEmpty(paramVal)) {
				return null;
			}
			HttpServletRequest request = ServletActionContext.getRequest();
			List<ProductView> product = productManager.findProductInfoById(paramVal);
			request.setAttribute("product_detail", product);
			request.setAttribute("search_productId", paramVal);
			String shop_nav = "top_" + paramVal.substring(0, 1);
			request.getSession().setAttribute(UtilKeys.SE_SHOP_NAV, shop_nav);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
