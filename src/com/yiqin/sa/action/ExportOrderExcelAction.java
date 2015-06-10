package com.yiqin.sa.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.bean.OrderView;
import com.yiqin.util.ExportReportTypeFactory;
import com.yiqin.util.OrderExcelUtil;
import com.yiqin.util.Page;

public class ExportOrderExcelAction extends ActionSupport {
	
	private static final long serialVersionUID = 424035033536530973L;
	
	// 当前下载的页
	private Page page;
	// 用户Id
	private String userId;
	// 开始时间
	private String startTime;
	// 结束时间
	private String endTime;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
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

	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			OrderExcelUtil util = new OrderExcelUtil();
			util.addHeader(ExportReportTypeFactory.columnNameMap,
					ExportReportTypeFactory.columnKeyList);

			List<OrderView> orderList = new ArrayList<OrderView>();
			if (page != null) {
				orderList = page.getResults();
			}
			util.addRows(orderList, ExportReportTypeFactory.columnKeyList);
			StringBuilder sb = new StringBuilder();
			sb.append("客户：").append(userId).append("	").append(startTime).append("~").append(endTime).append("订单详情");
			util.download(response, sb.toString());
			return "suc";
		} catch (Exception e) {
			e.printStackTrace();
			return "err";
		}
	}
}
