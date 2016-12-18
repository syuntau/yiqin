package com.yiqin.pojo;

import com.yiqin.util.Util;

public class CommonProduct implements java.io.Serializable {

	private static final long serialVersionUID = 8586497460056624483L;
	private Integer id; //自增主键
	private String userId; // 用户名
	private Integer categoryId; // 二级分类ID
	private String productId; // 产品ID
	private int cnt; // 产品购买次数

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
