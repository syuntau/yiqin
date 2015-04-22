package com.yiqin.shop.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.ProductManager;
import com.yiqin.shop.bean.ProductFilter;
import com.yiqin.shop.bean.ProductView;
import com.yiqin.util.Page;
import com.yiqin.util.UtilKeys;

/**
 * 产品检索过滤
 * 
 * @author LiuJun
 * 
 */
public class ProductFilterAction extends ActionSupport {

	private static final long serialVersionUID = -1063703459420329225L;

	//分类ID
	private String paramVal;
	// 过滤品牌 attId_brand
	private String brand;
	// 过滤价格 attId_price
	private String price;
	// 过滤颜色 attId_color
	private String color;
	// 选中页号
	private String pageIndex;
	// 分页对象
	private Page page;
	// 当前页号
	private int pageNo = 0;
	// 每页显示的条目数目
	public static final int MAXITEMINPAGE = 12;

	private ProductManager productManager;

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
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
			
			// 查询过滤总数
			ProductFilter productFilter = new ProductFilter(paramVal, brand,
					price, color, pageNo * MAXITEMINPAGE, MAXITEMINPAGE);
			List<ProductView> productList = null;
			int count = productManager.findProductCountByFilter(productFilter);
			if (count > 0) {
				// 查询当前页信息
				productList = productManager.findProductInfoByFilter(productFilter);
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
	
	public String getParamVal() {
		return paramVal;
	}
	
	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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
