package com.yiqin.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yiqin.dao.IProductDao;
import com.yiqin.pojo.Attribute;
import com.yiqin.pojo.BestProduct;
import com.yiqin.pojo.Brand;
import com.yiqin.pojo.Category;
import com.yiqin.pojo.CommonProduct;
import com.yiqin.pojo.Product;
import com.yiqin.util.Util;

public class ProductDao extends HibernateDaoSupport implements IProductDao {

	@Override
	public List<Category> findCategoryInfo() {
		String queryString = "from Category";
		return getHibernateTemplate().find(queryString);
	}
	
	@Override
	public List<Category> findCategoryInfoByCategoryId(String categoryIds) {
		if (Util.isNotEmpty(categoryIds)) {
			if (categoryIds.contains(",")) {
				if (categoryIds.startsWith(",")) {
					categoryIds = categoryIds.substring(1);
				}
				if (categoryIds.endsWith(",")) {
					categoryIds = categoryIds.substring(0,categoryIds.length() - 1);
				}
			}
			String queryString = "from Category where id in (" + categoryIds + ")";
			return getHibernateTemplate().find(queryString);
		}
		return null;
	}
	
	@Override
	public List<Category> findCategoryInfo(int topCateId) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("from Category where parentId like '");
		queryString.append(topCateId).append("%'");
		return getHibernateTemplate().find(queryString.toString());
	}
	
	@Override
	public List<Category> findTopCategoryInfo() {
		String queryString = "from Category where parentId=0";
		return getHibernateTemplate().find(queryString);
	}

	@Override
	public List<Product> findProductInfoById(String pids) {
		if (Util.isNotEmpty(pids)) {
			String queryString = "";
			if (pids.contains(",")) {
				if (pids.startsWith(",")) {
					pids = pids.substring(1);
				}
				if (pids.endsWith(",")) {
					pids = pids.substring(0, pids.length() - 1);
				}
				queryString = "from Product where productId in ("+ pids +")";
				return getHibernateTemplate().find(queryString);
			} else {
				queryString = "from Product where productId = ?";
				return getHibernateTemplate().find(queryString, pids);
			}
		}
		return null;
	}

	@Override
	public List<Product> findProductInfoByCategorys(String cateId) {
		if (null == cateId) {
			return null;
		}
		StringBuilder queryString = new StringBuilder();
		queryString.append("from Product where productId like '");
		queryString.append(cateId).append("%'");
		return getHibernateTemplate().find(queryString.toString());
	}

	@Override
	public Attribute findProductAttr(String attrNameId, int cateId) {
		String queryString = "from Attribute where nameId=? and categoryId=?";
		List<?> list = getHibernateTemplate().find(queryString,
				new Object[] { attrNameId, cateId });
		if (Util.isNotEmpty(list)) {
			return (Attribute) list.get(0);
		}
		return null;
	}

	@Override
	public List<Attribute> findAttributeByCategoryId(int categoryId) {
		String queryString = "from Attribute where categoryId=? order by id";
		List<?> list = getHibernateTemplate().find(queryString, categoryId);
		if (Util.isNotEmpty(list)) {
			return (List<Attribute>) list;
		}
		return null;
	}

	@Override
	public Attribute findAttributeById(int id) {
		String queryString = "from Attribute where id=?";
		List<?> list = getHibernateTemplate().find(queryString, id);
		if (Util.isNotEmpty(list)) {
			return (Attribute) list.get(0);
		}
		return null;
	}

	public void deleteAttributeByCategoryId(String categoryId) throws DataAccessException {
		List<Attribute> list = findAttributeByCategoryId(Integer.valueOf(categoryId));
		if (Util.isNotEmpty(list)) {
			getHibernateTemplate().deleteAll(list);
		}
	}

	public void deleteAttributeById(String id) throws DataAccessException {
		Attribute attribute = findAttributeById(Integer.valueOf(id));
		if (attribute != null) {
			getHibernateTemplate().delete(attribute);
		}
	}

	@Override
	public int saveAttribute(Attribute attribute) throws DataAccessException {
		return (Integer) getHibernateTemplate().save(attribute);
	}

	public void editAttribute(Attribute attribute) throws DataAccessException {
		getHibernateTemplate().update(attribute);
	}

	@Override
	public void saveAttribute(List<Attribute> list) throws DataAccessException {
		getHibernateTemplate().saveOrUpdateAll(list);
	}

	public List<Product> findProductByCategorys(String cateId) {
		if (null == cateId) {
			return null;
		}
		StringBuilder queryString = new StringBuilder();
		queryString.append("from Product where productId like '");
		queryString.append(cateId).append("%'");
		List<?> list = getHibernateTemplate().find(queryString.toString());
		if (Util.isNotEmpty(list)) {
			return (List<Product>) list;
		}
		return null;
	}
	@Override
	public void deleteProductByCategoryId(String categoryId) throws DataAccessException {
		List<Product> list = findProductByCategorys(categoryId);
		if (Util.isNotEmpty(list)) {
			getHibernateTemplate().deleteAll(list);
		}
	}

	@Override
	public void deleteProductByIds(String pids) throws DataAccessException {
		List<Product> list = findProductInfoById(pids);
		if (Util.isNotEmpty(list)) {
			getHibernateTemplate().deleteAll(list);
		}
	}

	@Override
	public void deleteProductById(String id) throws DataAccessException {
		List<Product> list = findProductInfoById(id);
		if (Util.isNotEmpty(list)) {
			getHibernateTemplate().deleteAll(list);
		}
	}

	@Override
	public void saveProduct(List<Product> list) throws DataAccessException {
		getHibernateTemplate().saveOrUpdateAll(list);
	}

	@Override
	public void deleteProductByAttributeId(String attributeId) throws DataAccessException {
		List<Product> list = findProductByAttributeId(attributeId);
		if (Util.isNotEmpty(list)) {
			getHibernateTemplate().deleteAll(list);
		}
	}

	private List<Product> findProductByAttributeId(String attributeId) {
		String queryString = "from Product where attributeId = ?";
		List<?> list = getHibernateTemplate().find(queryString, Integer.parseInt(attributeId));
		if (Util.isNotEmpty(list)) {
			return (List<Product>) list;
		}
		return null;
	}

	@Override
	public List<Product> findProductInfo(int attrId, String value) {
		String queryString = "from Product where attributeId = ? and value = ?";
		Object[] param = new Object[]{attrId, value};
		if(!Util.isEmpty(value)){
			if(value.contains("-")){
				String[] prices = value.split("-");
				if(prices.length==2){
					queryString = "from Product where attributeId = ? and value >= "+prices[0]+" and value <= "+prices[1];
					param = new Object[]{attrId};
				}else{
					queryString = "from Product where attributeId = ? and value >= "+prices[0];
					param = new Object[]{attrId};
				}
			}
		}
		List<?> list = getHibernateTemplate().find(queryString, param);
		if (Util.isNotEmpty(list)) {
			return (List<Product>) list;
		}
		return null;
	}

	@Override
	public void saveBestProduct(BestProduct bestProduct) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(bestProduct);
	}

	@Override
	public void deleteBestProductByUserId(String userId)
			throws DataAccessException {
		List<BestProduct> list = findBestProductByUserId(userId);
		if (Util.isNotEmpty(list)) {
			getHibernateTemplate().deleteAll(list);
		}
	}

	@Override
	public void deleteBestProductBycategoryId(String userId, String categoryId) throws DataAccessException {
		BestProduct bestProduct = findBestProductByCategoryId(userId, categoryId);
		if (bestProduct != null) {
			getHibernateTemplate().delete(bestProduct);
		}
	}
	
	@Override
	public List<BestProduct> findBestProductByTopCateId(String userId,
			String topCategoryId) {
		if (Util.isEmpty(userId)) {
			return null;
		}
		StringBuilder queryString = new StringBuilder();
		queryString.append("from BestProduct where userId = '");
		queryString.append(userId).append("'");
		if(Util.isNotEmpty(topCategoryId)){
			queryString.append(" and categoryId like '");
			queryString.append(topCategoryId).append("%'");
		}
		queryString.append(" order by categoryId");
		List<?> list = getHibernateTemplate().find(queryString.toString());
		if (Util.isNotEmpty(list)) {
			return (List<BestProduct>) list;
		}
		return null;
	}

	@Override
	public List<BestProduct> findBestProductByUserId(String userId) {
		String queryString = "from BestProduct where userId = ? order by categoryId";
		List<?> list = getHibernateTemplate().find(queryString, userId);
		if (Util.isNotEmpty(list)) {
			return (List<BestProduct>) list;
		}
		return null;
	}

	@Override
	public BestProduct findBestProductByCategoryId(String userId, String categoryId) {
		String queryString = "from BestProduct where userId = ? and categoryId = ?";
		List<?> list = getHibernateTemplate().find(queryString, new Object[]{userId, Integer.parseInt(categoryId)});
		if (Util.isNotEmpty(list)) {
			return (BestProduct) list.get(0);
		}
		return null;
	}

	@Override
	public List<Brand> findAllBrand() {
		String queryString = "from Brand";
		return getHibernateTemplate().find(queryString);
	}

	@Override
	public Brand findBrandById(Integer id) {
		String queryString = "from Brand where id=?";
		List<?> list = getHibernateTemplate().find(queryString, id);
		if (Util.isNotEmpty(list)) {
			return (Brand) list.get(0);
		}
		return null;
	}

	@Override
	public void deleteBrandById(String id) throws DataAccessException {
		Brand brand = findBrandById(Integer.valueOf(id));
		if (brand != null) {
			getHibernateTemplate().delete(brand);
		}
	}

	@Override
	public void editBrand(Brand brand) throws DataAccessException {
		getHibernateTemplate().update(brand);
	}

	@Override
	public Integer saveBrand(Brand brand) throws DataAccessException {
		return (Integer) getHibernateTemplate().save(brand);
	}

	public void editCategory(Category category) throws DataAccessException {
		getHibernateTemplate().update(category);
	}

	public Integer saveCategory(Category category) throws DataAccessException {
		return (Integer) getHibernateTemplate().save(category);
	}


	@Override
	public void saveCommonProduct(CommonProduct commonProduct) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(commonProduct);
	}

	@Override
	public void saveCommonProductList(List<CommonProduct> commonProductList) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(commonProductList);
	}

	@Override
	public void deleteCommonProductByUserId(String userId) throws DataAccessException {
		List<CommonProduct> list = findCommonProductByUserId(userId);
		if (Util.isNotEmpty(list)) {
			getHibernateTemplate().deleteAll(list);
		}
	}

	@Override
	public void deleteCommonProductBycategoryId(String userId, String categoryId) throws DataAccessException {
		List<CommonProduct> list = findCommonProductByCategoryId(userId, categoryId);
		if (Util.isNotEmpty(list)) {
			getHibernateTemplate().deleteAll(list);
		}
	}

	@Override
	public void deleteCommonProductByProductIds(String userId, String productIds) throws DataAccessException {
		List<CommonProduct> list = findCommonProductByUIdNPId(userId, productIds);
		if (Util.isNotEmpty(list)) {
			getHibernateTemplate().deleteAll(list);
		}
	}
	
	@Override
	public List<CommonProduct> findCommonProductByTopCateId(String userId, String topCategoryId) {
		if (Util.isEmpty(userId)) {
			return null;
		}
		StringBuilder queryString = new StringBuilder();
		queryString.append("from CommonProduct where userId = '");
		queryString.append(userId).append("'");
		if(Util.isNotEmpty(topCategoryId)){
			queryString.append(" and categoryId like '");
			queryString.append(topCategoryId).append("%'");
		}
		queryString.append(" order by count desc");
		List<?> list = getHibernateTemplate().find(queryString.toString());
		if (Util.isNotEmpty(list)) {
			return (List<CommonProduct>) list;
		}
		return null;
	}

	@Override
	public List<CommonProduct> findCommonProductByUserId(String userId) {
		String queryString = "from CommonProduct where userId = ? order by categoryId, count desc";
		List<?> list = getHibernateTemplate().find(queryString, userId);
		if (Util.isNotEmpty(list)) {
			return (List<CommonProduct>) list;
		}
		return null;
	}

	@Override
	public List<CommonProduct> findCommonProductByCategoryId(String userId, String categoryId) {
		String queryString = "from CommonProduct where userId = ? and categoryId = ?";
		List<?> list = getHibernateTemplate().find(queryString, new Object[]{userId, Integer.parseInt(categoryId)});
		if (Util.isNotEmpty(list)) {
			return (List<CommonProduct>) list;
		}
		return null;
	}

	@Override
	public List<CommonProduct> findCommonProductByUIdNPId(String userId, String productIds) {
		String queryString = "from CommonProduct where userId = " + userId + " and productId in ( " + productIds + " )";
		List<?> list = getHibernateTemplate().find(queryString);
		if (Util.isNotEmpty(list)) {
			return (List<CommonProduct>) list;
		}
		return null;
	}
}
