package com.yiqin.shop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.ProductManager;
import com.yiqin.util.Page;

/**
 * 产品检索过滤
 * 
 * @author LiuJun
 * 
 */
public class ProductFilterAction extends ActionSupport {

	private static final long serialVersionUID = -1063703459420329225L;

	//分类ID
	private String categorys;
	// 过滤品牌
	private String brand;
	// 过滤价格
	private String price;
	// 过滤颜色
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
			
			
//			// 查询过滤总数
//			List<OrderView> orderList = null;
//			int count = productManager.
//			if (count > 0) {
//				// 查询当前页信息
//				orderList = shoppingManager.findOrderList(
//						Util.getLoginUser(request.getSession()).getId(),
//						startDate, endDate, Integer.valueOf(status),
//						Long.valueOf(orderId), productName, productId, pageNo
//								* MAXITEMINPAGE, MAXITEMINPAGE);
//			}

			// 分页对象
			page = new Page(MAXITEMINPAGE, 1, pageNo + 1);
			page.setResults(null);
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return SUCCESS;
		}
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
