package com.yiqin.shop.bean;

import java.io.Serializable;

import com.yiqin.util.Util;

public class UserConf implements Serializable {

	private static final long serialVersionUID = -9143513123208962539L;
	private int id;                                  // 自增主键
	private String userId;                       // 用户ID
	private String attribute;                   // 用户配置属性
	private String value;                        // 用户配置值
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
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
}
