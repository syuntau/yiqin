package com.yiqin.shop.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.yiqin.shop.bean.Category;
import com.yiqin.shop.bean.ProductBase;
import com.yiqin.shop.dao.IProductDao;
import com.yiqin.util.Util;

public class ProductDao extends JdbcDaoSupport implements IProductDao {

	@Override
	public List<Category> findCategoryInfo() {
		String sql = "select * from category";
		return getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<ProductBase> findProductInfoById(String pids) {
		if(Util.isNotEmpty(pids)){
			String sql = "";
			if(pids.contains(",")){
				if(pids.startsWith(",")){
					pids = pids.substring(1);
				}else if(pids.endsWith(",")){
					pids = pids.substring(0, pids.length()-1);
				}
				sql = "select * from product where productId in (?)";
			}else{
				sql = "select * from product where productId = ?";
			}
			return getJdbcTemplate().queryForList(sql, new Object[] { pids });
		}
		return null;
	}
	
}
