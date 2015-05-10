package com.yiqin.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yiqin.dao.IProductDao;
import com.yiqin.pojo.Attribute;
import com.yiqin.pojo.BestProduct;
import com.yiqin.pojo.Category;
import com.yiqin.pojo.Product;
import com.yiqin.util.Util;

public class ProductDao extends HibernateDaoSupport implements IProductDao {

	@Override
	public List<Category> findCategoryInfo() {
		String queryString = "from Category";
		return getHibernateTemplate().find(queryString);
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
		String queryString = "from Attribute where categoryId=?";
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
					queryString = "from Product where attributeId = ? and value between ? and ?";
					param = new Object[]{attrId, prices[0], prices[1]};
				}else{
					queryString = "from Product where attributeId = ? and value >= ?";
					param = new Object[]{attrId, prices[0]};
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
	public BestProduct findBestProductByUserId(String userId) {
		if(Util.isEmpty(userId)){
			return null;
		}
		String queryString = "from BestProduct where userId = ?";
		List<?> list = getHibernateTemplate().find(queryString, userId);
		if (Util.isNotEmpty(list)) {
			return (BestProduct) list.get(0);
		}
		return null;
	}
}
