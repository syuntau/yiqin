package com.yiqin.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yiqin.dao.IProductDao;
import com.yiqin.pojo.Attribute;
import com.yiqin.pojo.Category;
import com.yiqin.pojo.Product;
import com.yiqin.util.Util;

public class ProductDao extends HibernateDaoSupport implements IProductDao {

	public List<Category> findCategoryInfo() {
		String queryString = "from Category";
		return getHibernateTemplate().find(queryString);
	}

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

}