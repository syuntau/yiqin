package com.yiqin.serviceImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;

import com.yiqin.dao.IShoppingDao;
import com.yiqin.pojo.Cart;
import com.yiqin.pojo.Category;
import com.yiqin.pojo.Order;
import com.yiqin.pojo.Stat;
import com.yiqin.pojo.StatId;
import com.yiqin.service.ShoppingManager;
import com.yiqin.shop.bean.OrderTempObj;
import com.yiqin.shop.bean.OrderView;
import com.yiqin.util.Util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
	public boolean deleteCartByUserId(String userId) {
		return shoppingDao.deleteCart(userId);
	}

	@Override
	public List<OrderView> findOrderList(String userId, Date startTime,
			Date endTime, int status, long orderId, String productName,
			String productId, int offset, int pageSize) {
		StringBuilder hql = new StringBuilder();
		hql.append("from Order where deleteFlag=0");
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
	
	@SuppressWarnings("unchecked")
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
			ov.setBeizhuzongjia(order.getBeizhuzongjia());
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
		hql.append("from Order where deleteFlag=0");
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

	@Override
	public boolean deleteOrder(long orderId) {
		return shoppingDao.deleteOrder(orderId);
	}

	@Override
	public boolean deleteOrdersByUserId(String userId) {
		return shoppingDao.deleteOrdersByUserId(userId);
	}

	@Override
	public List<OrderView> findOrderList(String ids) {
		List<OrderView> result = new ArrayList<OrderView>();
		List<Order> orderList = shoppingDao.findOrders(ids);
		if (Util.isNotEmpty(orderList)) {
			for (Order order : orderList) {
				result.add(orderToOrderView(order));
			}
		}
		return result;
	}
	
	public boolean startStat(String userId){
		List<Order> listOrder = shoppingDao.findAllOrderList(userId);
		Map<String, List<Order>> orderMap =  getMonthOrder(listOrder);
		List<Stat> statList = new ArrayList<>();
		for (String key : orderMap.keySet()) {
			Stat stat = getStat4Month(orderMap.get(key) ,userId,key);
			stat.setZongjia(getPrice4Month(orderMap.get(key))+"");
			statList.add(stat);
		}
		
		try {
			for (Stat stat : statList) {
				shoppingDao.saveOrUpdateStat(stat);
			}
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * 根据月份分类order
	 * @param listOrder
	 * @return
	 */
	private Map<String, List<Order>> getMonthOrder(List<Order> listOrder){
		Map<String, List<Order>> orderMap = new HashMap<>();
		for (Order order : listOrder) {
			String data = DateFormatUtils.format(order.getCrateDate(), "yyyyMM");
			if(orderMap.get(data)==null){
				orderMap.put(data, new ArrayList<Order>());
			}
			orderMap.get(data).add(order);
		}
		return orderMap;
	}
	
	/**
	 * 处理单月统计数据
	 * @param listOrder
	 * @param userId
	 * @param month
	 */
	private Stat getStat4Month(List<Order> listOrder , String userId,String month){
		Stat stat = new Stat();
		stat.setStatId(new StatId(userId, month));
		Map<String, Double> map = new HashMap<>();
		
		List<JSONObject> productList =  getProduct4Order(listOrder);
		
		for (JSONObject jsonObject : productList) {
			String productId = jsonObject.getString("productId");
			String cid = null;
			try {
				if(productId.equals("-1000")){
					cid = "-1000";
				}else{
					cid = productId.substring(0,2);
				}
			} catch (Exception e) {
			}
			if(cid != null){
				if(map.get(cid)==null){
					map.put(cid, 0.0);
				}
				try {
					map.put(cid, map.get(cid) + Double.parseDouble(jsonObject.getString("zhekouPrice")));
				} catch (Exception e) {
				}
			}
		}
		JSONArray ja = new JSONArray();
		for (String key : map.keySet()) {
			Double val = map.get(key);
			JSONObject jo = new JSONObject();
			jo.accumulate("cid", key);
			jo.accumulate("price", val);
			ja.add(jo);
		}
		stat.setMingxi(ja.toString());
		return stat;
	}
	
	/**
	 * 获取所有order 对应product对象
	 * @param listOrder
	 */
	private List<JSONObject> getProduct4Order(List<Order> listOrder){
		
		List<JSONObject> list = new ArrayList<>();
		
		for (Order order : listOrder) {
			if(order.getBeizhuzongjia()!=null){
				JSONObject jo = new JSONObject();
				jo.accumulate("productId", "-1000");
				jo.accumulate("zhekouPrice", order.getBeizhuzongjia());
				list.add(jo);
			}
			JSONArray ja = JSONArray.fromObject(order.getProductList());
			for (Object object : ja) {
				list.add(JSONObject.fromObject(object));
			}
		}
		return list;
	}
	
	
	/**
	 * 计算单月总价
	 * @param listOrder
	 * @return
	 */
	private double getPrice4Month(List<Order> listOrder){
		double price = 0.0;
		for (Order order : listOrder) {
			JSONArray ja = JSONArray.fromObject(order.getProductList());
			if(order.getBeizhuzongjia()!=null){
				try {
					price+=Double.parseDouble(order.getBeizhuzongjia());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			for (Object object : ja) {
				try {
					System.out.println(object.toString());
					JSONObject jo = JSONObject.fromObject(object);
					String zhekouPrice = jo.getString("zhekouPrice");
					int count = jo.getInt("count");
					double d = Double.parseDouble(zhekouPrice);
					price+=(d*count);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return price;
	}
	
	public JSONArray getChartData(String userId,String begin,String end,String category){
		
		JSONArray ja = new JSONArray();
		List<Stat> list = shoppingDao.findChartData(userId, begin, end);
		DecimalFormat df=new java.text.DecimalFormat("#.##");   
		Map<String, String> categoryMap = shoppingDao.findCategory(category);
		
		if(category.equals("1")){
			categoryMap.put("-1000", "-1000");//备注分类
		}
		
		
		if(list!=null && begin.equals(end)){
			Stat stat = list.get(0);
			JSONArray mingxi = JSONArray.fromObject(stat.getMingxi());
			for (Object object : mingxi) {
				JSONObject mingxiJo = JSONObject.fromObject(object);
				String cid = mingxiJo.getString("cid");
				Double price = mingxiJo.getDouble("price");
				if(categoryMap.get(cid)!=null){
					JSONObject jo = new JSONObject();
					jo.accumulate("id", cid);
					jo.accumulate("value", df.format(price));
					ja.add(jo);
				}
			}
		}else{
			for (Stat stat : list) {
				JSONObject jo  = new JSONObject();
				jo.accumulate("value", df.format(Double.parseDouble(stat.getZongjia())));
				jo.accumulate("name", stat.getStatId().getMonth());
				ja.add(jo);
			}
		}
		return ja;
		
	}
	
}
