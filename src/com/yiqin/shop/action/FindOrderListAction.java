package com.yiqin.shop.action;

import java.net.URLDecoder;
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
 * 查询订单列表
 * 
 * @author liujun
 *
 */
public class FindOrderListAction extends ActionSupport {

	private static final long serialVersionUID = 4175224246287224080L;

	// 每页显示的条目数目
	public static final int MAXITEMINPAGE = 5;
	// 搜索关键词
	private String searchName;
	// 订单状态
	private String status;
	// 过滤时间值
	private String filterTime;
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
			Date  currDate =  Util.getCurrentStartTime();
			if (!GenericValidator.isBlankOrNull(filterTime)) {
				filterTime = filterTime.trim();
				//最近3个月
				if(filterTime.equals("1")){
					Date  yyyymm = Util.parse(Util.format(currDate, "yyyy-MM"), "yyyy-MM");
					startDate = Util.addMonth(yyyymm, -2);
					endDate = Util.addMonth(yyyymm, 1);
				//今年内
				}else if(filterTime.equals("2")){
					startDate = Util.parse(Util.format(currDate, "yyyy"), "yyyy");
					endDate = Util.addYear(startDate, 1);
				//3年之前
				}else if(filterTime.equals("3")){
					Date yyyy = Util.parse(Util.format(currDate, "yyyy"), "yyyy");
					endDate = Util.addYear(yyyy, -3);
				}else {
					startDate = Util.parseYear(Integer.valueOf(filterTime));
					endDate = Util.addYear(startDate, 1);
				}
			}else{
				filterTime = "1";
				Date  yyyymm = Util.parse(Util.format(currDate, "yyyy-MM"), "yyyy-MM");
				startDate = Util.addMonth(yyyymm, -2);
				endDate = Util.addMonth(yyyymm, 1);
			}

			// 订单状态
			if (!GenericValidator.isBlankOrNull(status)) {
				status = status.trim();
			} else {
				status = "10";
			}
			
			String productName = "", productId = "", orderId = "0";
			if (!GenericValidator.isBlankOrNull(searchName)) {
				searchName = URLDecoder.decode(searchName, "utf-8");
				searchName = searchName.trim();
				if(searchName.length()==8 && Util.isNumeric(searchName) && searchName.startsWith("10")){
					orderId = searchName;
				}else if(searchName.length()==8 && Util.isNumeric(searchName)){
					productId = searchName;
				}else{
					productName = searchName;
				}
				startDate = null;
				endDate = null;
				status = "10";
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

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFilterTime() {
		return filterTime;
	}

	public void setFilterTime(String filterTime) {
		this.filterTime = filterTime;
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
