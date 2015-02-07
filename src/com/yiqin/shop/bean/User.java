package com.yiqin.shop.bean;

import java.io.Serializable;
import java.util.Date;

import com.yiqin.util.Util;

/**
 * 用户信息
 * 
 * @author LiuJun
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = -2559753714128278922L;
	private String id;                           // 用户名（唯一）
	private String password;               // 密码
	private String email;                     // 邮箱
	private String name;                    // 用户名称
	private String mobile;                  // 手机
	private String company;              // 公司
	private int role;                           // 用户类型，1：个人用户，2：企业用户
	private Date createDate;             // 创建时间
	private Date updateDate;           // 更新时间
	private int flag;                          // 用户状态，1：正常，2：删除
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
