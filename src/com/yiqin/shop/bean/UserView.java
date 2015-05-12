package com.yiqin.shop.bean;

import com.yiqin.pojo.User;
import com.yiqin.util.Util;

public class UserView {
	private String id;// 用户名（唯一）
	private String name;// 用户名称

	public UserView() {}
	public UserView(User user) {
		this.id = user.getId();
		if (Util.isEmpty(user.getName())) {
			this.name = user.getId();
		} else {
			this.name = user.getName();
		}
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
