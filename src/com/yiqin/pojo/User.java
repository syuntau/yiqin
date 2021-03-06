package com.yiqin.pojo;

// Generated 2015-2-12 14:36:12 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * User generated by hbm2java
 */
public class User implements java.io.Serializable {

	private static final long serialVersionUID = -5949164722592490913L;
	private String id;// 用户名（唯一）
	private String password;// 密码
	private String email; // 邮箱
	private String name;// 用户名称
	private String mobile;// 手机
	private String company; // 公司
	private String registerCode;//邀请码
	private Byte role;// 用户类型，1：个人用户，2：企业用户
	private Date createDate;// 创建时间
	private Date updateDate; // 更新时间
	private Byte flag; // 用户状态，1：正常，2：删除

	public User() {
	}

	public User(String id) {
		this.id = id;
	}

	public User(String id, String password, String email, String name,
			String mobile, String company, String registerCode, Byte role, Date createDate,
			Date updateDate, Byte flag) {
		this.id = id;
		this.password = password;
		this.email = email;
		this.name = name;
		this.mobile = mobile;
		this.company = company;
		this.registerCode = registerCode;
		this.role = role;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.flag = flag;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}

	public Byte getRole() {
		return this.role;
	}

	public void setRole(Byte role) {
		this.role = role;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Byte getFlag() {
		return this.flag;
	}

	public void setFlag(Byte flag) {
		this.flag = flag;
	}

}
