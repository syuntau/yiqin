package com.yiqin.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.yiqin.pojo.Attribute;
import com.yiqin.pojo.Category;
import com.yiqin.pojo.Product;
import com.yiqin.shop.bean.ProductFilter;

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

	/**
	 * 查询指定过滤条件的商品
	 * 
	 * @param productFilter
	 *            过滤条件集合
	 * @return 对应商品
	 */
	public List<Product> findProductInfoByFilter(ProductFilter productFilter);

	/**
	 * 查询指定分类、属性记录
	 * 
	 * @param attrNameId
	 *            属性名称ID 英文名
	 * @param cateId
	 *            分类ID
	 * @return Attribute
	 */
	public Attribute findProductAttr(String attrNameId, int cateId);

	public List<Attribute> findAttributeByCategoryId(int categoryId);

	public Attribute findAttributeById(int id);

	public void saveAttribute(List<Attribute> list) throws DataAccessException;
}
