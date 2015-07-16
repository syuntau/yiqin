package com.yiqin.shop.action;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Cart;
import com.yiqin.pojo.Order;
import com.yiqin.pojo.User;
import com.yiqin.pojo.UserConf;
import com.yiqin.service.ShoppingManager;
import com.yiqin.service.UserManager;
import com.yiqin.util.Configuration;
import com.yiqin.util.MailUtil;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

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
	// 发票类型
	private String fapiaolx;
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

	public String getFapiaolx() {
		return fapiaolx;
	}

	public void setFapiaolx(String fapiaolx) {
		this.fapiaolx = fapiaolx;
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
					|| Util.isEmpty(fapiaolx) || Util.isEmpty(fapiaomingxi)
					|| Util.isEmpty(productIds) || zhifu == null) {
				request.setAttribute("submitOrderError", "订单必填项不能为空，请填写完整！");
				return ERROR;
			}
			// 登录用户
			User user = Util.getLoginUser(request.getSession());

			Order order = new Order();
			order.setUserId(user.getId());
			order.setEmail(user.getEmail());
			order.setZhifu(zhifu);
			order.setFapiaolx(Util.faPiaoLx(fapiaolx));
			order.setFapiaotaitou(fapiaotaitou);
			order.setFapiaomingxi(fapiaomingxi);
			order.setPeisongfangshi(Util.peiSongFangShi(peisong));
			order.setStatus((byte) 1);
			order.setCrateDate(new Date());
			order.setUpdateDate(new Date());
			order.setDeleteFlag((byte) 0);

			// 查询订单商品购物车信息
			List<Cart> cartList = shoppingManager.findCartsByProductIds(
					user.getId(), productIds);
			if (Util.isNotEmpty(cartList)) {
				float totalPrice = 0;
				float totalYuanjia = 0;
				for (Cart cart : cartList) {
					totalPrice += cart.getCount() * cart.getZhekouPrice();
					totalYuanjia += cart.getCount() * cart.getPrice();
				}
				order.setZongjia(Float.valueOf(new DecimalFormat("#########.00").format(totalPrice)));
				order.setYuanjia(Float.valueOf(new DecimalFormat("#########.00").format(totalYuanjia)));
				UserConf userConf = userManager.findUserConfInfo(user.getId(),"zhekou");
				if (userConf != null) {
					order.setZhekou(Float.valueOf(userConf.getValue()));
				}else{
					order.setZhekou((float) 1);
				}
				order.setProductList(cartList.toString());
			}

			// 查询配送地址信息
			UserConf userConf = userManager.findUserConfInfo(user.getId(),
					addressAttr);
			if (userConf != null) {
				String [] address = userConf.getValue().split("_receive_");
				order.setName(address[0]);
				order.setMobile(address[1]);
				order.setAddress(address[2]);
			}

			// 保存订单
			boolean flag = shoppingManager.submitOrder(order);
			if (flag) {
				//删除购物车记录
				shoppingManager.deleteCartProduct(cartList);
				// 发送提醒邮件
				toMailOrderInfo(order, cartList);
			} else {
				request.setAttribute("submitOrderError", "订单提交失败，请稍后再试！");
				return ERROR;
			}
			request.getSession().setAttribute(UtilKeys.SE_SHOP_NAV, "top_account");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("submitOrderError", "订单提交失败，请稍后再试！");
			return ERROR;
		}
	}
	
	private void toMailOrderInfo(Order order,List<Cart> cartList) throws Exception{
		// 收件人地址
		String toEmail = Configuration.getProperty(UtilKeys.FROM_EMAIL);
		// 发件人名称
		String senderName = Configuration.getProperty(UtilKeys.SEND_NAME_EMAIL);
		// 发件人地址
		String fromEmail = Configuration.getProperty(UtilKeys.FROM_EMAIL);
		// 发件密码
		String fromPwd = Configuration.getProperty(UtilKeys.FROM_EMAIL_PASSWORD);
		// 邮件服务器
		String smtpServer = Configuration.getProperty(UtilKeys.MAIL_SERVER_HOST);
		// 邮件主题
		String emailTitle = "客户("+order.getId()+")"+Configuration.getProperty(UtilKeys.NEW_ADD_ORDER_TITLE);
		// 邮件内容
		String emailContent = getHtmlString(order, cartList);

		MailUtil mail = new MailUtil(smtpServer, fromEmail, senderName, fromEmail, fromPwd, toEmail, emailTitle, emailContent);
		mail.sendMessage();
	}
	
	private String getHtmlString(Order order,List<Cart> cartList) {
		StringBuilder content = new StringBuilder();
		String pBeg = "<span style=\"font-size:14px;font-weight:bold;\">", pEnd = "</span>";
		content.append("新增订单信息：<br/>");
		if(order != null){
			content.append("<div>").append(pBeg).append("　订单号：").append(pEnd).append(order.getId()).append(pBeg).append("　客服ID：").append(pEnd).append(order.getUserId());
			content.append(pBeg).append("　联系电话：").append(pEnd).append(order.getMobile()).append(pBeg).append("　订单折扣：").append(pEnd).append(order.getZhekou());
			content.append(pBeg).append("　订单总价：").append(pEnd).append(order.getZongjia()).append("元<br/>");
			content.append(pBeg).append("　送货地址：").append(pEnd).append(order.getAddress()).append("</div><br/>");
			
			if(Util.isNotEmpty(cartList)){
				content.append("<div>所购商品详细信息：<br/>");
				for(Cart cart : cartList){
					content.append(pBeg).append("　商品ID：").append(pEnd).append(cart.getProductId()).append(pBeg).append("　名称：").append(pEnd).append(cart.getProductName());
					content.append(pBeg).append("　数量：").append(pEnd).append(cart.getCount()).append(pBeg).append("　总价：").append(pEnd).append(cart.getZhekouPrice()).append("<br/>");
				}
			}
		}
		return content.toString();
	}
}
