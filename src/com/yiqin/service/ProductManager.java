package com.yiqin.service;

import java.util.List;

import com.yiqin.pojo.Category;
import com.yiqin.shop.bean.ProductView;

public interface ProductManager {
	/**
	 * 通过ID查询商品信息
	 * 
	 * @param pids
	 *            商品ID 多个用逗号分隔 如（id1,id2,id3,....）
	 * @return 对应商品
	 */
	public List<ProductView> findProductInfoById(String pids);

	/**
	 * 查询指定分类下的商品
	 * 
	 * @param categorys
	 *            分类ID
	 * @return 商品集合
	 */
	public List<ProductView> findProductInfo(String categorys);

	/**
	 * 查询所有分类菜单
	 * 
	 * @return 分类信息
	 */
	public List<Category> findCategoryInfo();

}