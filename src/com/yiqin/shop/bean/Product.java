package com.yiqin.shop.bean;

import java.io.Serializable;
import com.yiqin.util.Util;

public class Product implements Serializable {

	private static final long serialVersionUID = 8713123401386764517L;
	private int id;                                    // 自增主键
	private String productId;                   // 产品ID
	private int attributeId;                      // 属性ID
	private String value;                         // 属性值

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return Util.objToString(this);
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
}
