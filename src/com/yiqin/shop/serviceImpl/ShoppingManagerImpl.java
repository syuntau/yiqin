package com.yiqin.shop.serviceImpl;

import java.util.List;

import com.yiqin.shop.bean.Cart;
import com.yiqin.shop.bean.Order;
import com.yiqin.shop.service.ShoppingManager;

public class ShoppingManagerImpl implements ShoppingManager {
	// private ShoppingDao shoppingDao;
	//
	// public ShoppingDao getShoppingDao() {
	// return shoppingDao;
	// }
	//
	// public void setShoppingDao(ShoppingDao shoppingDao) {
	// this.shoppingDao = shoppingDao;
	// }

	@Override
	public boolean addProductToCart(Cart cart) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCartProductsNum(String userName, String productId,
			int pNum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean submitOrder(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Cart> findCartListInfo(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteCartProduct(String userName, String productId) {
		// TODO Auto-generated method stub
		return false;
	}
}
