package com.yiqin.shop.bean;

import java.io.Serializable;

public class CategoryConf implements Serializable {

	private static final long serialVersionUID = 6936901954526826683L;
	private String catagoryId;
	private String key;
	private String value;
	public String getCatagoryId() {
		return catagoryId;
	}
	public void setCatagoryId(String catagoryId) {
		this.catagoryId = catagoryId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	
}
