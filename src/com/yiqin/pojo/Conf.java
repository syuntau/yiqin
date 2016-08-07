package com.yiqin.pojo;

public class Conf implements java.io.Serializable {

	private static final long serialVersionUID = 6155551500518398933L;
	private Integer id; // 自增主键
	private String attribute;// 用户配置属性
	private String value;// 用户配置值

	public Conf() {
	}

	public Conf(String attribute, String value) {
		this.attribute = attribute;
		this.value = value;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAttribute() {
		return this.attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
