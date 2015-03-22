package com.yiqin.util;

import com.yiqin.shop.pojo.Category;

public class CategorySimple {

	private String id;
	private String name;

	public CategorySimple(Category category) {
		this.id = String.valueOf(category.getId());
		this.name = category.getName();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
