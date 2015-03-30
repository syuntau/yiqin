package com.yiqin.dao;

import java.util.List;

import com.yiqin.pojo.Cart;
import com.yiqin.pojo.Order;

/**
 * 购物DAO
 * 
 * @author LiuJun
 * 
 */
public interface IShoppingDao {
	/**
	 * 插入购物车信息
	 * 
	 * @param cart
	 *            购物车对象
	 * @return 成功状态
	 */
	public boolean insertCart(Cart cart);

	/**
	 * 删除购物车记录
	 * 
	 * @param userId
	 *            用户名
	 * @param productId
	 *            产品ID
	 * @return 成功状态
	 */
	public boolean deleteCart(String userId, String productId);

	/**
	 * 删除购物车记录
	 * 
	 * @param cartList
	 *            删除cart集合
	 * @return 成功状态
	 */
	public boolean deleteCart(List<Cart> cartList);

	/**
	 * 更新购物车购买商品数量
	 * 
	 * @param userId
	 *            用户名
	 * @param productId
	 *            产品ID
	 * @param pNum
	 *            购买数量
	 * @return 成功状态
	 */
	public boolean updateCartProductsNum(String userId, String productId,
			int pNum);

	/**
	 * 查询用户购物车信息
	 * 
	 * @param userId
	 *            用户名
	 * @return 购物车信息
	 */
	public List<Cart> findCartListInfo(String userId);

	/**
	 * 查询单个购物车商品记录
	 * 
	 * @param userId
	 *            用户名
	 * @param productId
	 *            产品ID
	 * @return 信息记录
	 */
	public Cart findCartInfo(String userId, String productId);

	/**
	 * 查询指定productIds的购物车信息
	 * 
	 * @param userId
	 *            用户名
	 * @param productIds
	 *            商品ID 多个用，分隔
	 * @return 购物车信息
	 */
	public List<Cart> findCartsByProductIds(String userId, String productIds);

	/**
	 * 查询购物车数量
	 * 
	 * @param userId
	 *            用户名
	 * @return 数量
	 */
	public int findCartNum(String userId);

	/**
	 * 插入订单信息
	 * 
	 * @param order
	 *            订单信息对象
	 * @return 成功状态
	 */
	public boolean insertOrder(Order order);

	/**
	 * 过滤查询订单
	 * 
	 * @param hql
	 *            执行的HQL语句
	 * @param offset
	 *            第一条记录索引
	 * @param pageSize
	 *            每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	public List<Order> findOrderList(final String hql, final int offset,
			final int pageSize);

	/**
	 * 过滤查询订单总数
	 * 
	 * @param hql
	 *            执行的HQL语句
	 * @return 符合条件总数
	 */
	public int findOrderCount(final String hql);
}