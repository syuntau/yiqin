package com.yiqin.shop.bean;

import java.util.Date;

/**
 * 用户信息
 * 
 * @author LiuJun
 * 
 */
public class User {
	// 用户名（唯一）
	private String name;
	// 密码
	private String password;
	// 邮箱
	private String email;
	// 手机
	private String telephone;
	// 注册时间
	private Date regtime;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getRegtime() {
		return regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}
}
