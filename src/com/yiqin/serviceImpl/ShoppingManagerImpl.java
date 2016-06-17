package com.yiqin.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yiqin.dao.IShoppingDao;
import com.yiqin.pojo.Cart;
import com.yiqin.pojo.Order;
import com.yiqin.service.ShoppingManager;
import com.yiqin.shop.bean.OrderTempObj;
import com.yiqin.shop.bean.OrderView;
import com.yiqin.util.Util;

public class ShoppingManagerImpl implements ShoppingManager {
	private IShoppingDao shoppingDao;

	public IShoppingDao getShoppingDao() {
		return shoppingDao;
	}

	public void setShoppingDao(IShoppingDao shoppingDao) {
		this.shoppingDao = shoppingDao;
	}

	@Override
	public boolean addProductToCart(Cart cart) {
		if (cart != null) {
			Cart saveCart = findCartInfo(cart.getUseId(), cart.getProductId());
			if (saveCart != null) {
				return updateCartProductsNum(saveCart.getUseId(),
						saveCart.getProductId(), saveCart.getCount() + cart.getCount());
			} else {
				return shoppingDao.insertCart(cart);
			}
		}
		return false;
	}

	@Override
	public boolean updateCartProductsNum(String userName, String productId,
			int pNum) {
		return shoppingDao.updateCartProductsNum(userName, productId, pNum);
	}

	@Override
	public boolean submitOrder(Order order) {
		if (order != null) {
			return shoppingDao.insertOrder(order);
		}
		return false;
	}

	@Override
	public List<Cart> findCartListInfo(String userName) {
		return shoppingDao.findCartListInfo(userName);
	}

	@Override
	public boolean deleteCartProduct(String userName, String productId) {
		return shoppingDao.deleteCart(userName, productId);
	}

	@Override
	public Cart findCartInfo(String userName, String productId) {
		return shoppingDao.findCartInfo(userName, productId);
	}

	@Override
	public int findCartNum(String userName) {
		return shoppingDao.findCartNum(userName);
	}

	@Override
	public List<Cart> findCartsByProductIds(String userName, String productIds) {
		if (Util.isNotEmpty(productIds)) {
			if (productIds.startsWith(",")) {
				productIds = productIds.substring(1);
			}
			if (productIds.endsWith(",")) {
				productIds = productIds.substring(0, productIds.length() - 1);
			}
		} else {
			return null;
		}
		return shoppingDao.findCartsByProductIds(userName, productIds);
	}

	@Override
	public boolean deleteCartProduct(List<Cart> cartList) {
		return shoppingDao.deleteCart(cartList);
	}

	@Override
	public List<OrderView> findOrderList(String userId, Date startTime,
			Date endTime, int status, long orderId, String productName,
			String productId, int offset, int pageSize) {
		StringBuilder hql = new StringBuilder();
		hql.append("from Order where 1=1");
		if (status != 10) {
			hql.append(" and status=").append(status);
		}
		if (startTime != null) {
			hql.append(" and crateDate>='").append(Util.format(startTime,"yyyy-MM-dd HH:mm:ss")).append("'");
		}
		if (endTime != null) {
			hql.append(" and crateDate<'").append(Util.format(endTime,"yyyy-MM-dd HH:mm:ss")).append("'");
		}
		if (Util.isNotEmpty(userId) && !"all".equalsIgnoreCase(userId)) {
			hql.append(" and userId='").append(userId).append("'");
		}
		if (orderId != 0) {
			hql.append(" and id=").append(orderId);
		}
		if (Util.isNotEmpty(productName)) {
			hql.append(" and productList like '%").append(productName).append("%'");
		} else if (Util.isNotEmpty(productId)) {
			hql.append(" and productList like '%").append(productId).append("%'");
		}
		hql.append(" order by crateDate desc");
		
		List<Order> listOrder = shoppingDao.findOrderList(hql.toString(),offset, pageSize);
		List<OrderView> ovList = new ArrayList<OrderView>();
		if (Util.isNotEmpty(listOrder)) {
			for (Order order : listOrder) {
				ovList.add(orderToOrderView(order));
			}
		}
		return ovList;
	}
	
	private OrderView orderToOrderView(Order order) {
		if (order != null) {
			OrderView ov = new OrderView();
			ov.setAddress(order.getAddress());
			ov.setCrateDate(Util.format(order.getCrateDate(), "yyyy-MM-dd HH:mm:ss"));
			ov.setDeleteFlag(order.getDeleteFlag());
			ov.setEmail(order.getEmail());
			ov.setFapiaolx(order.getFapiaolx());
			ov.setFapiaomingxi(order.getFapiaomingxi());
			ov.setFapiaotaitou(order.getFapiaotaitou());
			ov.setId(order.getId());
			ov.setMobile(order.getMobile());
			ov.setName(order.getName());
			ov.setPeisongfangshi(order.getPeisongfangshi());
			ov.setSonghuoriqi(order.getSonghuoriqi());
			ov.setStatus(Util.orderStatusStr(order.getStatus()));
			ov.setUpdateDate(Util.format(order.getUpdateDate(), "yyyy-MM-dd HH:mm:ss"));
			ov.setUserId(order.getUserId());
			ov.setYuanjia(order.getYuanjia());
			ov.setYunfei(order.getYunfei());
			ov.setZhekou(order.getZhekou());
			ov.setZhifu(order.getZhifu());
			ov.setZongjia(order.getZongjia());
			ov.setProductList(Util.getDTOList(order.getProductList(),Cart.class));
			ov.setOrderNote(order.getOrderNote());
			return ov;
		}
		return null;
	}

	@Override
	public int findOrderCount(String userId, Date startTime, Date endTime,
			int status, long orderId, String productName, String productId) {
		StringBuilder hql = new StringBuilder();
		hql.append("from Order where 1=1");
		if (status != 10) {
			hql.append(" and status=").append(status);
		}
		if (startTime != null) {
			hql.append(" and crateDate>='").append(Util.format(startTime,"yyyy-MM-dd HH:mm:ss")).append("'");
		}
		if (endTime != null) {
			hql.append(" and crateDate<'").append(Util.format(endTime,"yyyy-MM-dd HH:mm:ss")).append("'");
		}
		if (Util.isNotEmpty(userId) && !"all".equalsIgnoreCase(userId)) {
			hql.append(" and userId='").append(userId).append("'");
		}
		if (orderId != 0) {
			hql.append(" and id=").append(orderId);
		}
		if (Util.isNotEmpty(productName)) {
			hql.append(" and productList like '%").append(productName).append("%'");
		} else if (Util.isNotEmpty(productId)) {
			hql.append(" and productList like '%").append(productId).append("%'");
		}
		return shoppingDao.findOrderCount(hql.toString());
	}

	@Override
	public boolean updateOrderStatus(String userId, long orderId, int status) {
		return shoppingDao.updateOrderStatus(userId, orderId, status);
	}

	@Override
	public boolean updateOrder(long orderId, OrderTempObj tmpOrder) {
		return shoppingDao.updateOrder(orderId, tmpOrder);
	}
}
