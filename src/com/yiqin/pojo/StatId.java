package com.yiqin.pojo;

/**
 * 统计id实体类
 */
public class StatId implements java.io.Serializable {
	private static final long serialVersionUID = 615502674314819363L;
	private String userId;
	private String month;
	public StatId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StatId(String userId, String month) {
		super();
		this.userId = userId;
		this.month = month;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
}
