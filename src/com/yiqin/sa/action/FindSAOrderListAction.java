package com.yiqin.sa.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.service.ShoppingManager;
import com.yiqin.shop.bean.OrderView;
import com.yiqin.util.Page;
import com.yiqin.util.Util;

/**
 * 查询SA订单列表
 * 
 * @author liujun
 *
 */
public class FindSAOrderListAction extends ActionSupport {

	private static final long serialVersionUID = 1765062470663848589L;
	// 每页显示的条目数目
	public static final int MAXITEMINPAGE = 100000;//Integer.valueOf(Configuration.getProperty(UtilKeys.SHOP_ORDER_MAX_PAGE_SIZE));
	//用户Id
	private String userId;
	// 开始时间
	private String startTime;
	// 结束时间
	private String endTime;
	// 订单状态
	private String status;
	// 选中页号
	private String pageIndex;
	// 分页对象
	private Page page;
	// 当前页号
	private int pageNo = 0;

	private ShoppingManager shoppingManager;

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("application/json;charset=UTF-8");
		try {
			Date startDate = null;
			Date endDate = null;
			if(GenericValidator.isBlankOrNull(userId)){
				return SUCCESS;
			}

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
			
			// 订单状态
			if (!GenericValidator.isBlankOrNull(status)) {
				status = status.trim();
			} else {
				status = "10";
			}

			// 开始时间、结束时间
			Date  currDate =  Util.getCurrentStartTime();
			if (!GenericValidator.isBlankOrNull(startTime) && !GenericValidator.isBlankOrNull(endTime)) {
				startDate = Util.parse(startTime, "yyyy-MM-dd");
				endDate = Util.parse(endTime, "yyyy-MM-dd");
				endDate = Util.addDay(endDate, 1);
			}else{
				Date  yyyymmdd = Util.parse(Util.format(currDate, "yyyy-MM-dd"), "yyyy-MM-dd");
				startDate = Util.addMonth(yyyymmdd, -1);
				endDate = Util.addDay(yyyymmdd, 1);
				startTime = Util.format(startDate, "yyyy-MM-dd");
				endTime = Util.format(yyyymmdd, "yyyy-MM-dd");
			}
			if(startDate.after(endDate)){
				Date  yyyymmdd = Util.parse(Util.format(currDate, "yyyy-MM-dd"), "yyyy-MM-dd");
				startDate = Util.addMonth(yyyymmdd, -1);
				endDate = Util.addDay(yyyymmdd, 1);
				startTime = Util.format(startDate, "yyyy-MM-dd");
				endTime = Util.format(yyyymmdd, "yyyy-MM-dd");
			}
			
			// 查询过滤总数
			List<OrderView> orderList = null;
			int count = shoppingManager.findOrderCount(userId, startDate,
					endDate, Integer.valueOf(status), 0, "", "");
			if (count > 0) {
				// 查询当前页信息
				orderList = shoppingManager.findOrderList(userId, startDate,
						endDate, Integer.valueOf(status), 0, "", "", pageNo * MAXITEMINPAGE,
						MAXITEMINPAGE);
			}

			// 分页对象
			page = new Page(MAXITEMINPAGE, count, pageNo + 1);
			page.setResults(orderList);
			request.getSession().setAttribute("export_order", page);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return SUCCESS;
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
