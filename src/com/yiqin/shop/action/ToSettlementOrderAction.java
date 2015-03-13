package com.yiqin.shop.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.util.Util;

public class ToSettlementOrderAction extends ActionSupport {

	private static final long serialVersionUID = -2858396512919173716L;

	private String productIds;

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public String execute() throws Exception {
		try {
			if (Util.isEmpty(productIds)) {
				return null;
			}

			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
