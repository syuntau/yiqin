package com.yiqin.shop.bean;

import java.util.List;

import com.yiqin.pojo.Cart;
import com.yiqin.util.Util;

public class OrderTempObj {
	private long id;// 订单号
	private List<Cart> productList; // 商品记录
	private String zongjia;// 订单总价
	private String orderNote;// 订单备注
	private String beizhuzongjia;// 备注总价
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<Cart> getProductList() {
		return productList;
	}
	public void setProductList(List<Cart> productList) {
		this.productList = productList;
	}
	public String getZongjia() {
		return zongjia;
	}
	public void setZongjia(String zongjia) {
		this.zongjia = zongjia;
	}
	public String getOrderNote() {
		return orderNote;
	}
	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}
	public String getBeizhuzongjia() {
		return beizhuzongjia;
	}
	public void setBeizhuzongjia(String beizhuzongjia) {
		this.beizhuzongjia = beizhuzongjia;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
