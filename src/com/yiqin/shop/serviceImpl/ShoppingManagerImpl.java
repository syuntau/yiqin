package com.yiqin.shop.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.yiqin.shop.dao.IShoppingDao;
import com.yiqin.shop.pojo.Cart;
import com.yiqin.shop.pojo.Order;
import com.yiqin.shop.service.ShoppingManager;

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCartProductsNum(String userName, String productId,
			int pNum) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean submitOrder(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Cart> findCartListInfo(String userName) {
		List<Cart> cartList = new ArrayList<>();
		
		return cartList;
	}

	@Override
	public boolean deleteCartProduct(String userName, String productId) {
		// TODO Auto-generated method stub
		return true;
	}
}
