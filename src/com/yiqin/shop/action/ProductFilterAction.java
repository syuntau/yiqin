package com.yiqin.shop.action;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.User;
import com.yiqin.service.ProductManager;
import com.yiqin.shop.bean.ProductFilter;
import com.yiqin.shop.bean.ProductView;
import com.yiqin.util.CategoryUtil;
import com.yiqin.util.Configuration;
import com.yiqin.util.Page;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

/**
 * 产品检索过滤
 * 
 * @author LiuJun
 * 
 */
public class ProductFilterAction extends ActionSupport {

	private static final long serialVersionUID = -1063703459420329225L;

	// 每页显示的条目数目
	public static final int MAXITEMINPAGE = Integer.valueOf(Configuration.getProperty(UtilKeys.SHOP_FILTER_PRODUCT_MAX_PAGE_SIZE));
	//分类ID
	private String paramVal;
	// 过滤参数集合 attId_value 多个过滤参数，号拼接
	private String filterStr;
	// 选中页号
	private String pageIndex;
	// 分页对象
	private Page page;
	// 当前页号
	private int pageNo = 0;

	private ProductManager productManager;

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = request.getSession();
		response.setContentType("application/json;charset=UTF-8");
		try{
			// 处理当前页号
			if (GenericValidator.isBlankOrNull(pageIndex)
					|| (!GenericValidator.isInt(pageIndex))) {
				pageNo = 0;
			} else {
				pageNo = Integer.parseInt(pageIndex) - 1;
			}
			if (pageNo < 0) {
				pageNo = 0;
			}
			
			if(!Util.isEmpty(filterStr)){
				filterStr = URLDecoder.decode(filterStr, "utf-8");
			}
			
			// 查询过滤总数
			User loginUser = Util.getLoginUser(session);
			String userId = "";
			if (loginUser != null) {
				userId = loginUser.getId();
			}
			ProductFilter productFilter = new ProductFilter(userId, paramVal,filterStr, pageNo * MAXITEMINPAGE, MAXITEMINPAGE);
			List<ProductView> productList = null;
			int count = productManager.findProductCountByFilter(productFilter);
			if (count > 0) {
				// 查询当前页信息
				productList = productManager.findProductInfoByFilter(productFilter);
				for (ProductView pv : productList) {
					pv.setImgUrl(pv.getImgUrl().split(",")[0]);
				}
			}

			// 分页对象
			page = new Page(MAXITEMINPAGE, count, pageNo + 1);
			page.setResults(productList);
			
			String shop_nav = "top_" + paramVal.substring(0, 1);
			session.setAttribute(UtilKeys.SE_SHOP_NAV, shop_nav);

			StringBuilder nav = new StringBuilder();
			if (paramVal.length() == 4) {
				String firstName = CategoryUtil.getCategoryName(Integer.parseInt(paramVal.substring(0, 1)));
				String secondName = CategoryUtil.getCategoryName(Integer.parseInt(paramVal.substring(0, 2)));
				String thirdName = CategoryUtil.getCategoryName(Integer.parseInt(paramVal));
				nav.append(firstName).append(",").append(secondName).append(":").append(thirdName);
			} else if (paramVal.length() == 2) {
				String firstName = CategoryUtil.getCategoryName(Integer.parseInt(paramVal.substring(0, 1)));
				String secondName = CategoryUtil.getCategoryName(Integer.parseInt(paramVal.substring(0, 2)));
				nav.append(firstName).append(":").append(secondName);
			} else if (paramVal.length() == 1) {
				String firstName = CategoryUtil.getCategoryName(Integer.parseInt(paramVal.substring(0, 1)));
				nav.append(firstName);
			}

			session.setAttribute(UtilKeys.SE_PRODUCT_NAV, nav.toString());
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return SUCCESS;
		}
	}
	
	public String getParamVal() {
		return paramVal;
	}
	
	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}

	public String getFilterStr() {
		return filterStr;
	}

	public void setFilterStr(String filterStr) {
		this.filterStr = filterStr;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}
}
