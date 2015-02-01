package com.yiqin.shop.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.yiqin.shop.bean.Cart;
import com.yiqin.shop.bean.Order;
import com.yiqin.shop.dao.IShoppingDao;
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
		Cart cart = new Cart();
		cart.setImageUrl("/shop/images/cart/one.png");
		cart.setPrice(98);
		cart.setProductId("12347213947");
		cart.setProductName("高级圆珠笔");
		cart.setProductNum(1);
		cartList.add(cart);
		Cart cart1 = new Cart();
		cart1.setImageUrl("/shop/images/cart/two.png");
		cart1.setPrice(15);
		cart1.setProductId("43543252345");
		cart1.setProductName("办公用帖纸");
		cart1.setProductNum(3);
		cartList.add(cart1);
		return cartList;
	}

	@Override
	public boolean deleteCartProduct(String userName, String productId) {
		// TODO Auto-generated method stub
		return true;
	}
}
