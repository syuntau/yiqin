package com.yiqin.shop.serviceImpl;

import java.util.List;

import com.yiqin.shop.dao.IShoppingDao;
import com.yiqin.shop.pojo.Cart;
import com.yiqin.shop.pojo.Order;
import com.yiqin.shop.service.ShoppingManager;
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
}
