package com.yiqin.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.yiqin.pojo.Attribute;
import com.yiqin.pojo.BestProduct;
import com.yiqin.pojo.Brand;
import com.yiqin.pojo.Category;
import com.yiqin.pojo.Product;

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
	 * 查询分类信息
	 * 
	 * @param categoryIds
	 *            分类ID ,号拼接
	 * @return Category
	 */
	public List<Category> findCategoryInfoByCategoryId(String categoryIds);
	
	/**
	 * 查询顶级分类下分类菜单
	 * 
	 * @param topCateId 顶级分类ID
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
	 * 查询指定属性、值的产品
	 * 
	 * @param attrId
	 *            属性ID
	 * @param value
	 *            属性值
	 * @return 对应商品
	 */
	public List<Product> findProductInfo(int attrId, String value);

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
	
	public List<Brand> findAllBrand();
	
	public List<Attribute> findAttributeByCategoryId(int categoryId);

	public Attribute findAttributeById(int id);

	public void saveAttribute(List<Attribute> list) throws DataAccessException;
	public int saveAttribute(Attribute attribute) throws DataAccessException;
	public void editAttribute(Attribute attribute) throws DataAccessException;
	public void deleteAttributeById(String id) throws DataAccessException;
	public void deleteAttributeByCategoryId(String categoryId) throws DataAccessException;
	public void deleteProductByCategoryId(String categoryId) throws DataAccessException;
	public void deleteProductById(String id) throws DataAccessException;
	public void deleteProductByIds(String pids) throws DataAccessException;
	public void saveProduct(List<Product> list) throws DataAccessException;
	public void deleteProductByAttributeId(String attributeId) throws DataAccessException;
	public void saveBestProduct(BestProduct bestProduct) throws DataAccessException;
	public void deleteBestProductByUserId(String userId) throws DataAccessException;
	public void deleteBestProductBycategoryId(String userId, String categoryId) throws DataAccessException;
	public List<BestProduct> findBestProductByUserId(String userId);
	public List<BestProduct> findBestProductByTopCateId(String userId, String topCategoryId);
	public BestProduct findBestProductByCategoryId(String userId, String categoryId);
	public Brand findBrandById(Integer id);
	public void deleteBrandById(String id) throws DataAccessException;
	public void editBrand(Brand brand) throws DataAccessException;
	public Integer saveBrand(Brand brand) throws DataAccessException;

	public void editCategory(Category category) throws DataAccessException;
	public Integer saveCategory(Category category) throws DataAccessException;
}
