package com.yiqin.shop.dao;

import java.util.List;

import com.yiqin.shop.pojo.Category;
import com.yiqin.shop.pojo.Product;

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
	public List<Product> findProductInfoById(String pids);

	/**
	 * 查询指定分类商品
	 * 
	 * @param cateId
	 *            产品ID前缀,根据分类ID而来
	 * @return 对应商品
	 */
	public List<Product> findProductInfoByCategorys(String cateId);

}
