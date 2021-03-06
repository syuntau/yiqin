package com.yiqin.shop.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Cart;
import com.yiqin.pojo.CommonProduct;
import com.yiqin.pojo.Order;
import com.yiqin.pojo.User;
import com.yiqin.pojo.UserConf;
import com.yiqin.service.ProductManager;
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
	// 发票信息
	private String invoiceAttr;
	// 订单备注
	private String ordernote;
	// 订单所有商品ID
	private String productIds;

	private ShoppingManager shoppingManager;

	private UserManager userManager;

	private ProductManager productManager;

	public void setAddressAttr(String addressAttr) {
		this.addressAttr = addressAttr;
	}

	public void setZhifu(Byte zhifu) {
		this.zhifu = zhifu;
	}

	public void setPeisong(String peisong) {
		this.peisong = peisong;
	}

	public void setInvoiceAttr(String invoiceAttr) {
		this.invoiceAttr = invoiceAttr;
	}

	public void setOrdernote(String ordernote) {
		this.ordernote = ordernote;
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

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			if (Util.isEmpty(addressAttr) || Util.isEmpty(peisong)
					|| Util.isEmpty(invoiceAttr) || Util.isEmpty(productIds)
					|| zhifu == null) {
				request.setAttribute("submitOrderError", "订单必填项不能为空，请填写完整！");
				return ERROR;
			}
			// 登录用户
			User user = Util.getLoginUser(request.getSession());
			String userId = user.getId();

			Order order = new Order();
			order.setUserId(userId);
			order.setEmail(user.getEmail());
			order.setZhifu(zhifu);
			
			// 查询发票信息
			UserConf invoiceConf = userManager.findUserConfInfo(userId,
					invoiceAttr);
			if (invoiceConf != null) {
				String [] invoice = invoiceConf.getValue().split("_invoice_");
				order.setFapiaolx(Util.faPiaoLx(invoice[0]));
				order.setFapiaotaitou(invoice[1]);
				order.setFapiaomingxi(invoice[2]);
			}
			String orderNote = ordernote.replaceAll("\r\n", "<br>");
			orderNote = orderNote.replaceAll("\n", "<br>");
			order.setOrderNote(orderNote);
			order.setPeisongfangshi(Util.peiSongFangShi(peisong));
			order.setStatus((byte) 1);
			order.setCrateDate(new Date());
			order.setUpdateDate(new Date());
			order.setDeleteFlag((byte) 0);

			// 查询订单商品购物车信息
			List<Cart> cartList = shoppingManager.findCartsByProductIds(userId, productIds);
			if (Util.isNotEmpty(cartList)) {
				List<CommonProduct> commonProductList = new ArrayList<CommonProduct>();  // 购买过的产品自动添加至 常用商品列表
				List<CommonProduct> savedCommonProductList = productManager.findCommonProductsByUserId(userId);
				Map<String, String> countMap = new HashMap<String, String>();
				if (Util.isNotEmpty(savedCommonProductList)) {
					for (CommonProduct comProduct : savedCommonProductList) {
						countMap.put(comProduct.getProductId(), comProduct.getId() + "_" + comProduct.getCnt());
					}
				}
				float totalPrice = 0;
				float totalYuanjia = 0;
				for (Cart cart : cartList) {
					totalPrice += cart.getCount() * Float.valueOf(cart.getZhekouPrice());
					totalYuanjia += cart.getCount() * Float.valueOf(cart.getPrice());

					// 购买过的产品自动添加至 常用商品列表，统计 产品列表
					CommonProduct commonProduct = new CommonProduct();
					commonProduct.setUserId(userId);
					String productId = cart.getProductId();
					commonProduct.setCategoryId(Integer.parseInt(productId.substring(0, 4)));
					commonProduct.setProductId(productId);
					int cnt = 0;
					if (countMap.get(productId) != null) {
						String[] arr = countMap.get(productId).split("_");
						commonProduct.setId(Integer.parseInt(arr[0]));
						cnt = Integer.parseInt(arr[1]);
					}
					commonProduct.setCnt(cnt + 1);
					commonProductList.add(commonProduct);
				}
				order.setZongjia(new DecimalFormat("#########.00").format(totalPrice));
				order.setYuanjia(new DecimalFormat("#########.00").format(totalYuanjia));
				UserConf userConf = userManager.findUserConfInfo(userId,"zhekou");
				if (userConf != null) {
					order.setZhekou(Float.valueOf(userConf.getValue()));
				}else{
					order.setZhekou((float) 1);
				}
				order.setProductList(cartList.toString());
				
				try {
					productManager.saveCommonProductList(commonProductList);
				} catch (Exception e) {
					System.out.println("###### SubmitOrderAction saveCommonProductList error cause by : " + e.getMessage());
					e.printStackTrace();
				}
			}else{
				request.setAttribute("submitOrderError", "订单商品不存在或已被删除，请重新选购！");
				return ERROR;
			}

			// 查询配送地址信息
			UserConf userConf = userManager.findUserConfInfo(userId, addressAttr);
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
		String toEmail = Configuration.getProperty(UtilKeys.TO_EMAIL);
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
			content.append("<div>").append(pBeg).append("　订单号：").append(pEnd).append(order.getId()).append(pBeg).append("　客户ID：").append(pEnd).append(order.getUserId());
			content.append(pBeg).append("　联系电话：").append(pEnd).append(order.getMobile()).append(pBeg).append("　订单折扣：").append(pEnd).append(order.getZhekou());
			content.append(pBeg).append("　订单总价：").append(pEnd).append(order.getZongjia()).append("元<br/>");
			content.append(pBeg).append("　送货地址：").append(pEnd).append(order.getAddress()).append("<br/>");
			content.append(pBeg).append("　发票类型：").append(pEnd).append(order.getFapiaolx())
						.append(pBeg).append("　发票抬头：").append(pEnd).append(order.getFapiaotaitou())
						.append(pBeg).append("　发票明细：").append(pEnd).append(order.getFapiaomingxi()).append("<br/>");
			content.append(pBeg).append("　订单备注：").append(pEnd).append(order.getOrderNote()).append("</div><br/>");
			
			if(Util.isNotEmpty(cartList)){
				content.append("<div>所购商品详细信息：<br/>");
				for(Cart cart : cartList){
					content.append(pBeg).append("　商品ID：").append(pEnd).append(cart.getProductId()).append(pBeg).append("　名称：").append(pEnd).append(cart.getProductName());
					content.append(pBeg).append("　数量：").append(pEnd).append(cart.getCount()).append(pBeg).append("　单价：").append(pEnd).append(cart.getZhekouPrice()).append("<br/>");
				}
			}
		}
		return content.toString();
	}
}
