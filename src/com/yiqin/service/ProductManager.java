package com.yiqin.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.yiqin.pojo.Attribute;
import com.yiqin.pojo.Category;
import com.yiqin.shop.bean.ProductFilter;
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
	 * 查询指定过滤条件下的商品
	 * 
	 * @param productFilter
	 *            过滤条件集合
	 * @return 商品集合
	 */
	public List<ProductView> findProductInfoByFilter(ProductFilter productFilter);

	/**
	 * 查询指定过滤条件下商品总数
	 * 
	 * @param productFilter
	 *            过滤条件集合
	 * @return 总数
	 */
	public int findProductCountByFilter(ProductFilter productFilter);

	/**
	 * 查询所有分类菜单
	 * 
	 * @return 分类集
	 */
	public List<Category> findCategoryInfo();

	/**
	 * 查询顶级分类下分类菜单
	 * 
	 * @param topCateId
	 *            顶级分类ID
	 * 
	 * @return 分类集
	 */
	public List<Category> findCategoryInfo(int topCateId);

	/**
	 * 查询顶级分类
	 * 
	 * @return 分类集
	 */
	public List<Category> findTopCategoryInfo();

	/**
	 * 查询过滤商品的filter属性
	 * 
	 * @param categoryId
	 *            分类id
	 * @return 属性集
	 */
	public List<Attribute> findFilterAttribute(int categoryId);

	public List<Attribute> findAttributeByCategoryId(int categoryId);

	public Attribute findAttributeById(int id);

	public void saveAttribute(List<Attribute> list) throws DataAccessException;

	public Attribute saveAttribute(Attribute attribute)
			throws DataAccessException;

	public void updateAttribute(Attribute attribute) throws DataAccessException;

	public void deleteAttribute(String id) throws DataAccessException;

	public void deleteAllAttribute(String categoryId)
			throws DataAccessException;
}
