package com.yiqin.shop.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.bean.OrderView;
import com.yiqin.shop.service.ShoppingManager;
import com.yiqin.util.Page;
import com.yiqin.util.Util;

public class FindOrderListAction extends ActionSupport {
	
	private static final long serialVersionUID = 4175224246287224080L;

	// 每页显示的条目数目
	public static final int MAXITEMINPAGE = 5;
	// 商品名称
	private String productName;
	// 商品编号
	private String productId;
	// 订单编号
	private String orderId;
	// 订单状态
	private String status;
	// 开始时间
	private String startTime;
	// 结束时间
	private String endTime;
	// 选中页号
	private String pageIndex;
	// 分页对象
	private Page page;
	// 当前页号
	private int pageNo = 0;

	private ShoppingManager shoppingManager;

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			Date startDate = null;
			Date endDate = null;

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

			// 开始时间、结束时间
			if (!GenericValidator.isBlankOrNull(startTime)
					&& !GenericValidator.isBlankOrNull(endTime)) {
				startTime = startTime.trim();
				endTime = endTime.trim();

				if (!GenericValidator.isDate(startTime, "yyyy-MM-dd", false)
						|| !GenericValidator.isDate(endTime, "yyyy-MM-dd",
								false)) {
					return SUCCESS;
				}
				startDate = Util.parse(startTime);
				endDate = Util.parse(endTime);
				endDate = Util.addDay(endDate, 1);

				if (!startDate.before(endDate)) {
					return SUCCESS;
				}
			}

			if (!GenericValidator.isBlankOrNull(status)) {
				status = status.trim();
			}else{
				status = "10";
			}
			if (!GenericValidator.isBlankOrNull(productName)) {
				productName = productName.trim();
			}
			if (!GenericValidator.isBlankOrNull(productId)) {
				productId = productId.trim();
			}
			if (!GenericValidator.isBlankOrNull(orderId)) {
				orderId = orderId.trim();
			}else{
				orderId = "0";
			}
			// 查询过滤总数
			List<OrderView> orderList = null;
			int count = shoppingManager.findOrderCount(
					Util.getLoginUser(request.getSession()).getId(), startDate,
					endDate, Integer.valueOf(status), Long.valueOf(orderId),
					productName, productId);
			if (count > 0) {
				// 查询当前页信息
				orderList = shoppingManager.findOrderList(
						Util.getLoginUser(request.getSession()).getId(),
						startDate, endDate, Integer.valueOf(status),
						Long.valueOf(orderId), productName, productId, pageNo
								* MAXITEMINPAGE, MAXITEMINPAGE);
			}

			// 分页对象
			page = new Page(MAXITEMINPAGE, count, pageNo + 1);
			page.setResults(orderList);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return SUCCESS;
		}
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public ShoppingManager getShoppingManager() {
		return shoppingManager;
	}

	public void setShoppingManager(ShoppingManager shoppingManager) {
		this.shoppingManager = shoppingManager;
	}
}
