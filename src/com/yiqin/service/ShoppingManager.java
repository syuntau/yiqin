package com.yiqin.service;

import java.util.Date;
import java.util.List;

import com.yiqin.pojo.Cart;
import com.yiqin.pojo.Order;
import com.yiqin.shop.bean.OrderTempObj;
import com.yiqin.shop.bean.OrderView;

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
	
	/**
	 * 修改订单状态
	 * 
	 * @param userId
	 *            用户ID
	 * @param orderId
	 *            订单ID
	 * @param status
	 *            订单状态
	 * @return 成功状态
	 */
	public boolean updateOrderStatus(String userId, long orderId, int status);

	/**
	 * 订单信息查询
	 * 
	 * @param userId
	 *            用户ID
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param status
	 *            订单状态
	 * @param orderId
	 *            订单号
	 * @param productName
	 *            商品名称
	 * @param productId
	 *            商品ID
	 * @param offset
	 *            第一条记录索引
	 * @param pageSize
	 *            每页数量
	 * @return
	 */
	public List<OrderView> findOrderList(String userId, Date startTime,
			Date endTime, int status, long orderId, String productName,
			String productId, int offset, int pageSize);

	/**
	 * 订单数量查询
	 * 
	 * @param userId
	 *            用户ID
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param status
	 *            订单状态
	 * @param orderId
	 *            订单号
	 * @param productName
	 *            商品名称
	 * @param productId
	 *            商品ID
	 * @return
	 */
	public int findOrderCount(String userId, Date startTime, Date endTime,
			int status, long orderId, String productName, String productId);
	
	/**
	 * 删除指定订单
	 * 
	 * @param orderId
	 *            订单ID
	 * @return 成功状态
	 */
	public boolean deleteOrder(long orderId);

	public boolean updateOrder(long orderId, OrderTempObj tmpOrder);
}
