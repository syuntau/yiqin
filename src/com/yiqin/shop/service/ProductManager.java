package com.yiqin.shop.service;

import java.util.List;

import com.yiqin.shop.pojo.Category;
import com.yiqin.shop.pojo.Product;

public interface ProductManager {
	/**
	 * 通过ID查询商品信息
	 * 
	 * @param pids
	 *            商品ID 多个用逗号分隔 如（id1,id2,id3,....）
	 * @return 对应商品
	 */
	public List<Product> findProductInfoById(String pids);

	/**
	 * 查询指定分类下的商品
	 * 
	 * @param categorys
	 *            多级分类按序用逗号分隔 如（category,category1,category2,category3,.....）
	 * @return 商品集合
	 */
	public List<Product> findProductInfo(String categorys);

	/**
	 * 查询所有分类菜单
	 * 
	 * @return 分类信息
	 */
	public List<Category> findCategoryInfo();

}
