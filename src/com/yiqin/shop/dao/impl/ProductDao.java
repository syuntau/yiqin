package com.yiqin.shop.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yiqin.shop.dao.IProductDao;
import com.yiqin.shop.pojo.Category;
import com.yiqin.shop.pojo.Product;
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
				} else if (pids.endsWith(",")) {
					pids = pids.substring(0, pids.length() - 1);
				}
				queryString = "from Product where productId in (?)";
			} else {
				queryString = "from Product where productId = ?";
			}
			return getHibernateTemplate().find(queryString,
					new Object[] { pids });
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

}
