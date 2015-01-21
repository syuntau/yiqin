package com.yiqin.shop.bean;

import java.io.Serializable;

import com.yiqin.util.Util;

public class Brand implements Serializable {

	private static final long serialVersionUID = 8587193569104000532L;
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
