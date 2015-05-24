package com.yiqin.shop.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.BestProduct;
import com.yiqin.service.ProductManager;
import com.yiqin.shop.bean.ProductView;
import com.yiqin.util.Configuration;
import com.yiqin.util.Page;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class FindBestProductAction extends ActionSupport {
	private static final long serialVersionUID = -8848208662911483212L;

	// 每页显示的条目数目
	public static final int MAXITEMINPAGE = Integer.valueOf(Configuration.getProperty(UtilKeys.SHOP_BEST_PRODUCT_MAX_PAGE_SIZE));
	// 分类ID
	private String paramVal;
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
		response.setContentType("application/json;charset=UTF-8");
		try{
			String userId = Util.getLoginUser(request.getSession()).getId();
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
			
			if(Util.isEmpty(paramVal) || paramVal.length()==1){
				List<BestProduct> bestProducts = productManager.findBestProducts(userId, paramVal);
				if(Util.isNotEmpty(bestProducts)){
					paramVal = String.valueOf(bestProducts.get(0).getCategoryId());
				}
			}
			
			// 查询过滤总数
			List<ProductView> productList = null;
			int count = productManager.findBestProductCount(userId,paramVal);
			if (count > 0) {
				// 查询当前页信息
				productList = productManager.findBestProductInfo(userId,paramVal, pageNo * MAXITEMINPAGE, MAXITEMINPAGE);
			}

			// 分页对象
			page = new Page(MAXITEMINPAGE, count, pageNo + 1);
			page.setResults(productList);
			
			String shop_nav = "top_" + paramVal.substring(0, 1);
			request.getSession().setAttribute(UtilKeys.SE_SHOP_NAV, shop_nav);
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return SUCCESS;
		}
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getParamVal() {
		return paramVal;
	}

	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
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
