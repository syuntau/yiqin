package com.yiqin.shop.service;

import java.util.List;

import com.yiqin.shop.bean.OfficeProductBase;

public interface ProductManager {
	/**
	 * 查询单个商品信息
	 * 
	 * @param pid
	 *            商品ID
	 * @return 对应商品
	 */
	public OfficeProductBase findProductInfoById(String pid);

	/**
	 * 查询所有商品信息
	 * 
	 * @return list
	 */
	public List<OfficeProductBase> findProductInfo();

}
