package com.yiqin.shop.service;

import java.util.Date;
import java.util.List;

import com.yiqin.shop.pojo.Cart;
import com.yiqin.shop.pojo.Order;

public interface ShoppingManager {
	/**
	 * 添加到购物车
	 * 
	 * @param cart
	 *            单个购物商品记录
	 * @return 添加成功状态
	 */
	public boolean addProductToCart(Cart cart);

	/**
	 * 更新购物车购买商品数量
	 * 
	 * @param userName
	 *            登录用户名
	 * @param productId
	 *            商品Id
	 * @param pNum
	 *            购买数量
	 * @return 更新成功状态
	 */
	public boolean updateCartProductsNum(String userName, String productId,
			int pNum);

	/**
	 * 查询购物车信息
	 * 
	 * @param userName
	 *            登录用户名
	 * @return 购物车信息
	 */
	public List<Cart> findCartListInfo(String userName);

	/**
	 * 查询购物车数量
	 * 
	 * @param userName
	 *            登录用户名
	 * @return 数量
	 */
	public int findCartNum(String userName);

	/**
	 * 查询单个购物车信息
	 * 
	 * @param userName
	 *            登录用户名
	 * @param productId
	 *            商品ID
	 * @return 购物车信息
	 */
	public Cart findCartInfo(String userName, String productId);

	/**
	 * 查询指定productIds的购物车信息
	 * 
	 * @param userName
	 *            登录用户名
	 * @param productIds
	 *            商品ID 多个用，分隔
	 * @return 购物车信息
	 */
	public List<Cart> findCartsByProductIds(String userName, String productIds);

	/**
	 * 删除购物车商品
	 * 
	 * @param userName
	 *            登录用户名
	 * @param productId
	 *            商品IDS
	 * @return 删除成功状态
	 */
	public boolean deleteCartProduct(String userName, String productId);

	/**
	 * 删除购物车指定商品
	 * 
	 * @param cartList
	 *            删除cart集合
	 * @return 删除成功状态
	 */
	public boolean deleteCartProduct(List<Cart> cartList);

	/**
	 * 提交最終订单
	 * 
	 * @param order
	 *            订单记录
	 * @return 提交成功状态
	 */
	public boolean submitOrder(Order order);

	public List<Order> findOrderList(String userId, Date startTime,
			Date endTime, int status, long orderId, String productName,
			String productId);
}
