package com.yiqin.shop.dao;

import java.util.List;

import com.yiqin.shop.bean.Category;
import com.yiqin.shop.bean.ProductBase;

/**
 * 产品DAO
 * 
 * @author LiuJun
 * 
 */
public interface IProductDao {
	/**
	 * 查询所有分类菜单
	 * 
	 * @return 分类集
	 */
	public List<Category> findCategoryInfo();
	
	/**
	 * 通过ID查询商品信息
	 * 
	 * @param pids
	 *            商品ID 多个用逗号分隔 如（id1,id2,id3,....）
	 * @return 对应商品
	 */
	public List<ProductBase> findProductInfoById(String pids);

}
