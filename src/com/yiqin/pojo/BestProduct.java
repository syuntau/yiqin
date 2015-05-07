package com.yiqin.pojo;

import com.yiqin.util.Util;

public class BestProduct implements java.io.Serializable {

	private static final long serialVersionUID = -7902123023933462277L;
	private Integer id; //自增主键
	private String userId; // 用户名
	private String productId; // 产品ID
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
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
