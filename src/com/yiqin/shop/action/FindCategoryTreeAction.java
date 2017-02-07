package com.yiqin.shop.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Category;
import com.yiqin.service.ProductManager;
import com.yiqin.util.CategoryUtil;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

/**
 * 查询顶级分类菜单
 * 
 * @author LiuJun
 * 
 */
public class FindCategoryTreeAction extends ActionSupport {

	private static final long serialVersionUID = 8944656464537016876L;
	// 顶级分类ID
	private String topCateId;
	
	public String getTopCateId() {
		return topCateId;
	}

	public void setTopCateId(String topCateId) {
		this.topCateId = topCateId;
	}

	private ProductManager productManager;
	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			List<Category> category = null;

			Object object = session.getAttribute(UtilKeys.SE_SHOP_NAV_BEST_PRODUCT);
			boolean commonProFlag = false;
			if (object != null && "best_product_nav".equals(object)) {
				commonProFlag = true;
			}

			if (commonProFlag) {
				List<Category> comCateTmpList = null;
				if (Util.isEmpty(topCateId)) {
					comCateTmpList = productManager.findCategoryInfoForCommon(Util.getLoginUser(session).getId());
				} else {
					comCateTmpList = productManager.findCategoryInfoForCommon(Integer.valueOf(topCateId), Util.getLoginUser(session).getId());
				}
				if (Util.isNotEmpty(comCateTmpList)) {
					category = new ArrayList<Category>();
					Map<Integer, Category> map = new HashMap<Integer, Category>();
					for (Category cate : comCateTmpList) {
						Category topCate = map.get(cate.getParentId());
						if (topCate == null) {
							topCate = new Category();
							topCate.setId(cate.getParentId());
							topCate.setName(CategoryUtil.getCategoryName(cate.getParentId()));
							topCate.setSubCategoryList(new ArrayList<Category>());
							category.add(topCate);
						}
						
						List<Category> subList = topCate.getSubCategoryList();
						subList.add(cate);
					}
				}
			} else {
				CategoryUtil.init(productManager);
				category = CategoryUtil.getCategoryTree();
			}

			if (Util.isEmpty(category)) {
				result = "1";
				response.getWriter().print(result);
				return null;
			}

			JSONArray jsArray = JSONArray.fromObject(category);
			result = jsArray.toString();
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
