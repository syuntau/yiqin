package com.yiqin.shop.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.User;
import com.yiqin.service.ProductManager;
import com.yiqin.shop.bean.ProductFilter;
import com.yiqin.shop.bean.ProductView;
import com.yiqin.util.Configuration;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

/**
 * 根据分类ID查询商品
 * 
 * @author liujun
 *
 */
public class FindProductsByCategoryAction extends ActionSupport {

	private static final long serialVersionUID = -6676005768684862992L;
	// 每页显示的条目数目
	public static final int MAXITEMINPAGE = Integer.valueOf(Configuration.getProperty(UtilKeys.SHOP_FILTER_PRODUCT_MAX_PAGE_SIZE));
	// 当前页号
	private int pageNo = 0;
	// 分类ID
	private String categoryId;

	private ProductManager productManager;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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
			if (Util.isEmpty(categoryId)) {
				result = "1";
			} else {
				// 查询对应分类产品
				User loginUser = Util.getLoginUser(session);
				String userId = "";
				if (loginUser != null) {
					userId = loginUser.getId();
				}
				
				List<ProductView> product = null;
				String productIds = Configuration.getProperty(UtilKeys.SHOP_INDEX_PAGE_SHOW_PRODUCT_IDS);
				if(Util.isNotEmpty(productIds)){
					if(productIds.endsWith(",")){
						productIds = productIds.substring(0, productIds.length()-1);
					}
					product = productManager.findProductInfoById(userId, productIds);
				}else{
					// 查询过滤总数
					ProductFilter productFilter = new ProductFilter(userId, categoryId, null, pageNo * MAXITEMINPAGE, MAXITEMINPAGE);
					int count = productManager.findProductCountByFilter(productFilter);
					if (count > 0) {
						// 查询当前页信息
						product = productManager.findProductInfoByFilter(productFilter);
					}
				}
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
