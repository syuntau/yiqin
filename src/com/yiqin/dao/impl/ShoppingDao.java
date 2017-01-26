package com.yiqin.dao.impl;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yiqin.dao.IShoppingDao;
import com.yiqin.pojo.Cart;
import com.yiqin.pojo.Category;
import com.yiqin.pojo.Order;
import com.yiqin.pojo.Stat;
import com.yiqin.shop.bean.OrderTempObj;
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
	public boolean updateCart(Cart cart) {
		try {
			getHibernateTemplate().saveOrUpdate(cart);
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

	@Override
	public boolean updateOrderStatus(String userId, long orderId, int status) {
		try{
			String queryString = "from Order where id=? and userId=?";
			List<?> list = getHibernateTemplate().find(queryString, new Object[]{orderId,userId});
			if(list != null){
				Order tempOrder = (Order) list.get(0);
				tempOrder.setStatus(Byte.valueOf(String.valueOf(status)));
				getHibernateTemplate().saveOrUpdate(tempOrder);
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateOrder(long orderId, OrderTempObj tmpOrder) {
		try{
			String queryString = "from Order where id=?";
			List<?> list = getHibernateTemplate().find(queryString, orderId);
			if(list != null){
				Order order = (Order) list.get(0);
				if (Util.isNotEmpty(tmpOrder.getOrderNote())) {
					order.setOrderNote(tmpOrder.getOrderNote());
				}
				if (Util.isNotEmpty(tmpOrder.getBeizhuzongjia())) {
					order.setBeizhuzongjia(tmpOrder.getBeizhuzongjia());
				}
				if (Util.isNotEmpty(tmpOrder.getZongjia())) {
					order.setZongjia(tmpOrder.getZongjia());
				}
				order.setProductList(tmpOrder.getProductList().toString());

				float totalYuanjia = 0;
				for (Cart cart : tmpOrder.getProductList()) {
					totalYuanjia += cart.getCount() * Float.valueOf(cart.getPrice());
				}
				totalYuanjia += Float.valueOf(tmpOrder.getBeizhuzongjia());
				order.setYuanjia(new DecimalFormat("#########.00").format(totalYuanjia));

				order.setUpdateDate(new Date());
				getHibernateTemplate().saveOrUpdate(order);
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteOrder(long orderId) {
		String queryString = "from Order where id=?";
		List<?> list = getHibernateTemplate().find(queryString, orderId);
		if (list != null) {
			Order order = (Order) list.get(0);
			order.setDeleteFlag((byte) 1);
			getHibernateTemplate().saveOrUpdate(order);
			return true;
		}
		return false;
	}

	@Override
	public List<Order> findOrders(String ids) {
		String queryString = "from Order where id in (" + ids + ")";
		return (List<Order>) getHibernateTemplate().find(queryString);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findAllOrderList(String userId) {
//		String queryString = "from Order where userId = '"+userId+"' and deleteFlag = 0 and status > 0";
		String queryString = "from Order where userId = '"+userId+"' and deleteFlag = 0";
		List<?> list = getHibernateTemplate().find(queryString);
		return (List<Order>)list;
	}
	
	
	public boolean saveOrUpdateStat(Stat stat){
		try {
			getHibernateTemplate().saveOrUpdate(stat);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Stat> findChartData(String userId,String begin,String end) {
		String queryString = "from Stat where statId.userId = '"+userId+"' and statId.month >= '"+begin+"' and statId.month <= '"+end+"'";
		List<?> list = getHibernateTemplate().find(queryString);
		return (List<Stat>)list;
	}
	@SuppressWarnings("unchecked")
	public List<Stat> findChartData(String userId) {
		String queryString = "from Stat where statId.userId = '"+userId+"'";
		List<?> list = getHibernateTemplate().find(queryString);
		return (List<Stat>)list;
	}

	@Override
	public Map<String, String> findCategory(String parentId) {
		String categoryQueryString = "from Category where parentId = "+parentId;
		List<?> categotyList = getHibernateTemplate().find(categoryQueryString);
		Map<String, String>map = new HashMap<>();
		for (Object object : categotyList) {
			try {
				Category category = (Category)object;
				map.put(category.getId()+"", category.getId()+"");
			} catch (Exception e) {
			}
		}
		return map;
	}
	
	
}
