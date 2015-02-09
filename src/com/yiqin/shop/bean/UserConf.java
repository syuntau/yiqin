package com.yiqin.shop.bean;

import java.io.Serializable;

import com.yiqin.util.Util;

public class UserConf implements Serializable {

	private static final long serialVersionUID = -9143513123208962539L;
	private int id; // 自增主键
	private String user_id; // 用户ID
	private String attribute; // 用户配置属性
	private String value; // 用户配置值

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
