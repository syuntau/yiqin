package com.yiqin.shop.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yiqin.shop.dao.IShoppingDao;
import com.yiqin.shop.pojo.Cart;
import com.yiqin.shop.pojo.Order;
import com.yiqin.util.Util;

public class ShoppingDao extends HibernateDaoSupport implements IShoppingDao {

	@Override
	public boolean insertCart(Cart cart) {
		try {
			getHibernateTemplate().saveOrUpdate(cart);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteCart(String userId, String productId) {
		try {
			Cart cart = findCartInfo(userId, productId);
			if (cart != null) {
				getHibernateTemplate().delete(cart);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateCartProductsNum(String userId, String productId,
			int pNum) {
		try {
			Cart cart = findCartInfo(userId, productId);
			if (cart != null) {
				cart.setCount(pNum);
				getHibernateTemplate().saveOrUpdate(cart);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Cart> findCartListInfo(String userId) {
		String queryString = "from Cart where useId=?";
		return getHibernateTemplate().find(queryString, userId);
	}

	@Override
	public boolean insertOrder(Order order) {
		try {
			getHibernateTemplate().saveOrUpdate(order);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Cart findCartInfo(String userId, String productId) {
		String queryString = "from Cart where useId=? and productId=?";
		List<?> list = getHibernateTemplate().find(queryString,
				new Object[] { userId, productId });
		if (Util.isNotEmpty(list)) {
			return (Cart) list.get(0);
		}
		return null;
	}

	@Override
	public int findCartNum(String userId) {
		String queryString = "select count(*) from Cart where useId=?";
		Long count = (Long) getHibernateTemplate().iterate(queryString,userId).next();
		return count.intValue();
	}

	@Override
	public List<Cart> findCartsByProductIds(String userId, String productIds) {
		String queryString = "from Cart where useId=? and productId in ("+ productIds + ")";
		return getHibernateTemplate().find(queryString, userId);
	}

	@Override
	public boolean deleteCart(List<Cart> cartList) {
		try {
			getHibernateTemplate().deleteAll(cartList);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Order> findOrderList(final String hql, final int offset,
			final int pageSize) {
		List list = getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List result = session.createQuery(hql)
								.setFirstResult(offset).setMaxResults(pageSize)
								.list();
						return result;
					}
				});
		return list;
	}

	@Override
	public int findOrderCount(String hql) {
		String queryString = "select count(*) " + hql;
		Long count = (Long) getHibernateTemplate().iterate(queryString).next();
		return count.intValue();
	}
}
