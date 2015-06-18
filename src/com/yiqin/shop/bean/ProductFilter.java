package com.yiqin.shop.bean;

/**
 * 商品过滤参数类
 * 
 * @author LiuJun
 * 
 */
public class ProductFilter {
	// 分类ID
	private String categorys;
	// 过滤参数集合 attId_value 多个过滤参数，号拼接
	private String filterStr;
	// 第一条记录索引
	private int offset;
	// 每页显示的条目数目
	private int pageSize;
	// 用户ID
	private String userId;

	public ProductFilter(String userId, String categorys, String filterStr,
			int offset, int pageSize) {
		super();
		this.categorys = categorys;
		this.filterStr = filterStr;
		this.offset = offset;
		this.pageSize = pageSize;
		this.userId = userId;
	}

	public String getCategorys() {
		return categorys;
	}

	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}

	public String getFilterStr() {
		return filterStr;
	}

	public void setFilterStr(String filterStr) {
		this.filterStr = filterStr;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
