package com.yiqin.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.yiqin.pojo.Attribute;
import com.yiqin.pojo.BestProduct;
import com.yiqin.pojo.Brand;
import com.yiqin.pojo.Category;
import com.yiqin.pojo.CommonProduct;
import com.yiqin.pojo.Product;
import com.yiqin.shop.bean.ProductFilter;
import com.yiqin.shop.bean.ProductView;

public interface ProductManager {
	/**
	 * 通过ID查询商品列表展示信息
	 * 
	 * @param pids
	 *            商品ID 多个用逗号分隔 如（id1,id2,id3,....）
	 * @return 对应商品
	 */
	public List<ProductView> findProductInfoById(String pids);
	public List<ProductView> findProductInfoById(String userId, String pids);
	
	/**
	 * 通过ID查询商品所有信息
	 * 
	 * @param pids
	 *            商品ID 多个用逗号分隔 如（id1,id2,id3,....）
	 * @return 对应商品 key=productId value={key=nameid value=pvalue}
	 */
	public Map<String, Map<String, String>> findProductAllInfoByIds(String pids);

	public Map<String, Map<String, List<String>>> findProductByIds(String pid);
	
	/**
	 * 查询商品详细的属性和属性值
	 * 
	 * @param pids
	 *            商品ID
	 * @return 对应商品 key=productId value={key=name value=pvalue}
	 */
	public Map<String, Map<String, String>> findProductDetailByIds(String pids);

	/**
	 * 查询指定分类下的列表展示商品信息
	 * 
	 * @param categorys
	 *            分类ID
	 * @return 商品集合
	 */
	public List<ProductView> findProductInfo(String categorys);
	public List<ProductView> findProductInfo(String userId, String categorys);
	
	/**
	 * 查询指定分类查询商品所有信息
	 * 
	 * @param categorys
	 *            分类ID
	 * @return 对应商品 key=productId value={key=nameid value=pvalue}
	 */
	public Map<String, Map<String, String>> findProductAllInfo(String categorys);

	public Map<String,Map<String,String>> findProductAllInfo(String categorys, boolean changeBrandFlag);

	/**
	 * 查询指定过滤条件下的列表展示商品信息
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
	
//	public List<Category> findCategoryInfoForBest(String userId);

	/**
	 * 查询顶级分类下分类菜单
	 * 
	 * @param topCateId
	 *            顶级分类ID
	 * 
	 * @return 分类集
	 */
	public List<Category> findCategoryInfo(int topCateId);
	
//	public List<Category> findCategoryInfoForBest(int topCateId, String userId);
	
	public List<Category> findCategoryList(String categoryIds);
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
	
	/**
	 * 查询品牌
	 * 
	 * @return Brand
	 */
	public Brand findProductBrandByBrandId(int brandId);
	
	/**
	 * 查询所有品牌
	 * 
	 * @return List<Brand>
	 */
	public List<Brand> findAllBrand();

	public List<Attribute> findAttributeByCategoryId(int categoryId);

	public Attribute findAttributeById(int id);

	public void saveAttribute(List<Attribute> list) throws DataAccessException;

	public int saveAttribute(Attribute attribute) throws DataAccessException;

	public void updateAttribute(Attribute attribute) throws DataAccessException;

	public void deleteAttribute(String id) throws DataAccessException;

	public void deleteAllAttribute(String categoryId) throws DataAccessException;

	public void deleteAllProduct(String categoryId) throws DataAccessException;

	public void deleteProduct(String productId) throws DataAccessException;
	public void deleteProducts(String pids) throws DataAccessException;

	public void saveProduct(List<Product> list) throws DataAccessException;

	public void deleteProductByAttributeId(String attributeId) throws DataAccessException;

//	public Map<String, List<String>> findBestProductByUserId(String userId);
	public void deleteBrandById(String id) throws DataAccessException;
	public void editBrand(Brand brand) throws DataAccessException;
	public Integer saveBrand(Brand brand) throws DataAccessException;
	public void editCategory(Category category) throws DataAccessException;
	public Integer saveCategory(Category category) throws DataAccessException;


//	/**
//	 * 查询快速购物商品
//	 * 
//	 * @param userId
//	 *            用户ID
//	 * @param categoryId
//	 *            分类ID
//	 * @param offset
//	 *            第一条记录索引
//	 * @param pageSize
//	 *            每页显示的数量
//	 * @return 商品集合
//	 */
//	public List<ProductView> findBestProductInfo(String userId, String categoryId, int offset,
//			int pageSize);
	
//	/**
//	 * 查询快速购物商品总数
//	 * 
//	 * @param userId
//	 *            用户ID
//	 * @param categoryId
//	 *            分类ID
//	 * @return 总数
//	 */
//	public int findBestProductCount(String userId, String categoryId);
//	public BestProduct findBestProductByCategoryId(String userId, String categoryId);
//	public void saveBestProduct(BestProduct bestProduct) throws DataAccessException;
//	public void deleteAllBestProduct(String userId) throws DataAccessException;
//	public void deleteBestProduct(String userId, String categoryId) throws DataAccessException;

	public List<CommonProduct> findCommonProductByCategoryId(String userId, String categoryId);
	public void saveCommonProductList(List<CommonProduct> commonProductList) throws DataAccessException;
	public void deleteAllCommonProduct(String userId) throws DataAccessException;
	public void deleteCommonProduct(String userId, String categoryId) throws DataAccessException;
	public void deleteCommonProductByProductIds(String userId, String productIds);
	public List<ProductView> findCommonProductInfo(String userId, String categoryId, int offset, int pageSize);
	public int findCommonProductCount(String userId, String categoryId);
	public List<Category> findCategoryInfoForCommon(String userId);
	public List<Category> findCategoryInfoForCommon(int topCateId, String userId);
	public List<CommonProduct> findCommonProductsByUserId(String userId);
	public Map<String, List<String>> findCommonProductMapByUserId(String userId);
	
	public void transformerCommonProduct(String userId);
}
