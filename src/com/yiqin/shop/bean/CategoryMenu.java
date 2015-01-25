package com.yiqin.shop.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yiqin.util.Util;

public class CategoryMenu implements Serializable {

	private static final long serialVersionUID = 6531351367406306429L;
	private String id;
	private String name;
	private int level;
	private Date createTime;
	private Date updateTime;
	private List<SubCategoryMenu> subMenu;// 子菜单

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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<SubCategoryMenu> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<SubCategoryMenu> subMenu) {
		this.subMenu = subMenu;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
