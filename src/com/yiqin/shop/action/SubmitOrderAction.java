package com.yiqin.shop.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.pojo.Cart;
import com.yiqin.shop.pojo.Order;
import com.yiqin.shop.pojo.User;
import com.yiqin.shop.pojo.UserConf;
import com.yiqin.shop.service.ShoppingManager;
import com.yiqin.shop.service.UserManager;
import com.yiqin.util.Util;

/**
 * 提交订单
 * 
 * @author liujun
 *
 */
public class SubmitOrderAction extends ActionSupport {

	private static final long serialVersionUID = -1455133316814630220L;
	// 送货地址信息
	private String addressAttr;
	// 支付方式
	private Byte zhifu;
	// 配送方式
	private String peisong;
	// 发票抬头
	private String fapiaotaitou;
	// 发票明细
	private String fapiaomingxi;
	// 订单所有商品ID
	private String productIds;

	private ShoppingManager shoppingManager;

	private UserManager userManager;

	public void setAddressAttr(String addressAttr) {
		this.addressAttr = addressAttr;
	}

	public void setZhifu(Byte zhifu) {
		this.zhifu = zhifu;
	}

	public void setPeisong(String peisong) {
		this.peisong = peisong;
	}

	public void setFapiaotaitou(String fapiaotaitou) {
		this.fapiaotaitou = fapiaotaitou;
	}

	public void setFapiaomingxi(String fapiaomingxi) {
		this.fapiaomingxi = fapiaomingxi;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public void setShoppingManager(ShoppingManager shoppingManager) {
		this.shoppingManager = shoppingManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			if (Util.isEmpty(addressAttr) || Util.isEmpty(peisong)
					|| Util.isEmpty(fapiaotaitou) || Util.isEmpty(fapiaomingxi)
					|| Util.isEmpty(productIds) || zhifu == null) {
				request.setAttribute("submit_order_error", 1);
				return SUCCESS;
			}
			// 登录用户
			User user = Util.getLoginUser(request.getSession());

			Order order = new Order();
			order.setUserId(user.getId());
			order.setEmail(user.getEmail());
			order.setMobile(user.getMobile());
			order.setName(user.getName());
			order.setZhifu(zhifu);
			order.setFapiaotaitou(fapiaotaitou);
			order.setFapiaomingxi(fapiaomingxi);
			order.setPeisongfangshi(Util.peiSongFangShi(peisong));
			order.setStatus((byte) 0);
			order.setCrateDate(new Date());
			order.setUpdateDate(new Date());
			order.setDeleteFlag((byte) 0);

			// 查询订单商品购物车信息
			List<Cart> cartList = shoppingManager.findCartsByProductIds(
					user.getId(), productIds);
			if (Util.isNotEmpty(cartList)) {
				int totalPrice = 0;
				for (Cart cart : cartList) {
					totalPrice += cart.getCount() * cart.getPrice();
				}
				order.setZongjia((float) totalPrice);
				order.setYuanjia((float) totalPrice);
				order.setZhekou((float) 0);
				order.setProductList(cartList.toString());
			}

			// 查询配送地址信息
			UserConf userConf = userManager.findUserConfInfo(user.getId(),
					addressAttr);
			if (userConf != null) {
				order.setAddress(userConf.getValue());
			}

			// 保存订单
			boolean flag = shoppingManager.submitOrder(order);
			if (flag) {
				//删除购物车记录
				shoppingManager.deleteCartProduct(cartList);
				request.setAttribute("submit_order_error", 3);
				return SUCCESS;
			} else {
				request.setAttribute("submit_order_error", 2);
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("submit_order_error", 2);
			return SUCCESS;
		}
	}
}
